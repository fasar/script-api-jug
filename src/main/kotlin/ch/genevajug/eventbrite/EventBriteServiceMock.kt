package ch.genevajug.eventbrite

import com.fasterxml.jackson.databind.ObjectMapper

class EventBriteServiceMock(
        var mapper: ObjectMapper
) : EventBriteService {

    override fun categoriesList(): CategoriesList {
        return mapper.readValue(categories, CategoriesList::class.java)
    }

    override fun listEvent(organisationId: String): EventsList {
        return mapper.readValue(events, EventsList::class.java)
    }

    override fun event(eventId: String): Event {
        return listEvent("123").events.get(1)
    }

    override fun editEvent(eventId: String, event: Event) {
        println("My object is : ${event}" )
    }
}