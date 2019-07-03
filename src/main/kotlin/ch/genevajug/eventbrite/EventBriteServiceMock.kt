package ch.genevajug.eventbrite

import com.fasterxml.jackson.databind.ObjectMapper

class EventBriteServiceMock(
        private var mapper: ObjectMapper
) : EventBriteService {

    private var eventList:EventsList

    init {
        eventList = mapper.readValue(events, EventsList::class.java)
    }

    override fun categoriesList(): CategoriesList {
        return mapper.readValue(categories, CategoriesList::class.java)
    }

    override fun listEvent(organisationId: String): EventsList {
        return eventList
    }

    override fun event(eventId: String): Event? {
        for (event in eventList.events) {
            if (event.id == eventId) {
                return event
            }
        }
        return null
    }

    override fun editEvent(eventId: String, event: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun copyEvent(eventId: String, copy: EventCopy): Event {
        val currentEvent = event(eventId)
        val json = mapper.writeValueAsString(currentEvent)
        val copyedEvent = mapper.readValue(json, Event::class.java)
        copyedEvent.id = copyedEvent.id + 1000
        copyedEvent.summary = copy.summary
        copyedEvent.name.text = copy.name
        copyedEvent.name.html = copy.name
        if(copyedEvent.description==null)
            copyedEvent.description = MultipartText("", "")
        copyedEvent.description!!.html = copy.summary

        copyedEvent.start.local = copy.start_date
        copyedEvent.end!!.local = copy.end_date
        copyedEvent.status = StatusEnunm.draft
        eventList.events.add(copyedEvent)
        return copyedEvent
    }

    override fun publish(eventId: String): PublishRes {
        val currentEvent = event(eventId)
        currentEvent!!.status = StatusEnunm.live
        return PublishRes(true, null, null, null)
    }

    override fun unpublish(eventId: String): PublishRes {
        val currentEvent = event(eventId)
        currentEvent!!.status = StatusEnunm.draft
        return PublishRes(null, true, null, null)
    }

}