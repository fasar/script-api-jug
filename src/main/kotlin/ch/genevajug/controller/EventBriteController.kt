package ch.genevajug.controller

import ch.genevajub.config.model.MyConfig
import ch.genevajug.eventbrite.Event
import ch.genevajug.eventbrite.EventBriteService
import ch.genevajug.eventbrite.EventCopy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Controller
class EventBriteController {
    @Autowired
    lateinit var eventBriteService: EventBriteService

    @Autowired
    lateinit var myConfig: MyConfig

    @GetMapping(path = ["/eventbrite"])
    fun eventbrite(model: Model): String {
        model.addAttribute("eevents", eventBriteService.listEvent(myConfig.eventbrite.organizationId))
        return "eventbrite"
    }


    @CacheEvict(value = ["eventbriteEvents", "eventbriteList"], allEntries = true)
    @GetMapping(path = ["/eventbrite/event/{eventId}/copy"])
    fun copyEvent(@PathVariable("eventId") eventId: String): String {
        val start = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(18).plusMinutes(30)
        val end = start.plusHours(2)
        val tz = "Europe/Zurich"
        val eventCopy = EventCopy("To be filled", start.toInstant().toString(), end.toInstant().toString(), tz, "Summary to be filled")
        eventBriteService.copyEvent(eventId, eventCopy)

        return "redirect:/eventbrite"
    }


    @CacheEvict(value = ["eventbriteEvents", "eventbriteList"], allEntries = true)
    @GetMapping(path = ["/eventbrite/event/{eventId}"])
    fun editEvent(@PathVariable("eventId") eventId: String, model: Model): String {
        val eventOpt = eventBriteService.event(eventId)
        if (eventOpt == null) {
            throw RuntimeException("Event id $eventId is not found")
        }

        val editEvent = EditEventWebEntity.fromEvent(eventOpt)
        model.addAttribute("editEventWebEntity", editEvent)
        return "eb-edit"
    }

    @CacheEvict(value = ["eventbriteEvents", "eventbriteList"], allEntries = true)
    @PostMapping(path = ["/eventbrite/event/{eventId}"])
    fun postEditedEvent(@PathVariable("eventId") eventId: String, @Valid editedEventWE: EditEventWebEntity, result: BindingResult, model: Model): String {
        if (result.hasErrors()) {
            return "eb-edit"
        }

        val zoneId = ZoneId.systemDefault()
        val timezone = zoneId.toString()
        val startDate = LocalDateTime.parse(editedEventWE.startDate).atZone(zoneId).toInstant().toString()
        val endDate = LocalDateTime.parse(editedEventWE.endDate).atZone(zoneId).toInstant().toString()


        val json = """
            {
                "event": {
                    "name": {
                        "html": "${editedEventWE.name}"
                    },
                    "description": {
                        "html": "${editedEventWE.description}"
                    },
                    "start": {
                        "timezone": "$timezone",
                        "utc": "$startDate"
                    },
                    "end": {
                        "timezone": "$timezone",
                        "utc": "$endDate"
                    }
                }
            }
        """.trimIndent()

        eventBriteService.editEvent(editedEventWE.id, json)
        return "redirect:/eventbrite"
    }


    @CacheEvict(value = ["eventbriteEvents", "eventbriteList"], allEntries = true)
    @GetMapping(path = ["/eventbrite/event/{eventId}/publish"])
    fun publish(@PathVariable("eventId") eventId: String): String {
        val publish = eventBriteService.publish(eventId)
        if (publish.error != null) {
            throw java.lang.RuntimeException(publish.error_description)
        }

        return "redirect:/eventbrite"
    }

    @CacheEvict(value = ["eventbriteEvents", "eventbriteList"], allEntries = true)
    @GetMapping(path = ["/eventbrite/event/{eventId}/unpublish"])
    fun unpublish(@PathVariable("eventId") eventId: String): String {
        val publish = eventBriteService.unpublish(eventId)
        if (publish.error != null) {
            throw java.lang.RuntimeException(publish.error_description)
        }

        return "redirect:/eventbrite"
    }

}

data class EditEventWebEntity(
        @field:NotNull
        var id: String,

        @field:[NotNull Size(min = 5)]
        var name: String,

        // Start Date should be locale to Zurich (hard coded)
        @field:[NotNull Pattern(regexp = """\d{4}-\d{2}-\d{2}T\d\d:\d\d:\d\d""")]
        var startDate: String,

        // End Date should be locale to Zurich (hard coded)
        @field:[NotNull Pattern(regexp = """\d{4}-\d{2}-\d{2}T\d\d:\d\d:\d\d""")]
        var endDate: String,

        @field:[NotNull Size(min = 20, message = "Description must be a string with a min of 20 characters")]
        var description: String
) {

    companion object {
        fun fromEvent(event: Event): EditEventWebEntity {
            return EditEventWebEntity(event.id,
                    event.name.html ?: event.name.text ?: "",
                    event.start.local ?: "",
                    event.end?.local ?: "",
                    event.description?.html ?: event.description?.text ?: ""
            )
        }
    }
}