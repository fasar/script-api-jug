package ch.genevajug.eventbrite

import feign.RequestLine
import org.springframework.cache.annotation.Cacheable

interface EventBriteService {

    @Cacheable("eventbrite")
    @RequestLine("GET /v3/categories/")
    fun categoriesList(): CategoriesList


    @Cacheable("eventbrite")
    @RequestLine("GET /v3/organizations/{organization_id}/events/")
    fun listEvent(organisationId: String): EventsList

}