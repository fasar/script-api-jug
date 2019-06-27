package ch.genevajug.controller

import ch.genevajub.config.model.MyConfig
import ch.genevajug.eventbrite.DateTimeTz
import ch.genevajug.eventbrite.Event
import ch.genevajug.eventbrite.EventBriteService
import ch.genevajug.eventbrite.MultipartText
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid
import javax.validation.Validator
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Controller
class EventBriteController {
    @Autowired
    lateinit var eventBriteService: EventBriteService

    @Autowired
    lateinit var myConfig: MyConfig


    @Autowired
    lateinit var validator: Validator

    @GetMapping(path = ["/eventbrite"])
    fun eventbrite(): ModelAndView {
        val res = ModelAndView("eventbrite")
        res.model.put("eevents", eventBriteService.listEvent(myConfig.eventbrite.organizationId))
        return res
    }


    @GetMapping(path = ["/eventbrite/event/{eventId}"])
    fun editEvent(@PathVariable("eventId") eventId: String): ModelAndView {
        val res = ModelAndView("eb-edit")
        val event = eventBriteService.event(eventId)
        val editEvent = EditEventWebEntity.fromEvent(event)
        res.model.put("editEventWebEntity", editEvent)
        return res
    }

    @PostMapping(path = ["/eventbrite/event/{eventId}"])
    fun postEvent(@Valid editEventWebEntity: EditEventWebEntity, result: BindingResult, model: Model): String {
        println(model.asMap())

        val res = validator.validate(editEventWebEntity)
        if (result.hasErrors()) {
            return "eb-edit"
        }

        val event = eventBriteService.event(editEventWebEntity.id!!)
        if (event.description == null) {
            event.description = MultipartText("", "")
        }
        event.description!!.html = editEventWebEntity.description

        if (event.start == null) {
            event.start = DateTimeTz("ETC", "", "")
        }
        event.start!!.local = editEventWebEntity.startDate!!

        if (event.end == null) {
            event.end = DateTimeTz("ETC", "", "")
        }
        event.end!!.local = editEventWebEntity.endDate!!

        eventBriteService.editEvent(event.id, event)
        return "redirect:/eventbrite"
    }

}

data class EditEventWebEntity(
        @field:NotNull
        var id: String?,
        @field:[NotNull Size(min = 5)]
        var name: String?,
        @field:[NotNull Pattern(regexp = """\d{4}-\d{2}-\d{2}T\d\d:\d\d:\d\d""")]
        var startDate: String?,
        @field:[NotNull Pattern(regexp = """\d{4}-\d{2}-\d{2}T\d\d:\d\d:\d\d""")]
        var endDate: String?,
        @field:[NotNull Size(min = 20, message = "Description must be a string with a min of 20 characters")]
        var description: String?
) {

    companion object {
        fun fromEvent(event: Event): EditEventWebEntity {
            return EditEventWebEntity(event.id, event.name.html, event.start.local, event.end!!.local, event.description!!.html)
        }
    }
}