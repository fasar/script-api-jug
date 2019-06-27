package ch.genevajug.eventbrite

import feign.RequestLine
import org.springframework.cache.annotation.Cacheable

interface EventBriteService {

    @Cacheable("eventbrite")
    @RequestLine("GET /v3/categories/")
    fun categoriesList(): CategoriesList


    @Cacheable("eventbrite")
    @RequestLine("GET /v3/organizations/{organizationId}/events/")
    fun listEvent(organisationId: String): EventsList

    @Cacheable("eventbrite")
    @RequestLine("GET /v3/events/{eventId}/")
    fun event(eventId: String): Event

    @RequestLine("GET /v3/events/{eventId}")
    fun editEvent(eventId:String, event: Event)
}