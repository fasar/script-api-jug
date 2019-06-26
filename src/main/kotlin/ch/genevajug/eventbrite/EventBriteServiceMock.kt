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
}