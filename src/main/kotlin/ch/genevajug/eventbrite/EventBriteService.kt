package ch.genevajug.eventbrite

import feign.Body
import feign.Headers
import feign.Param
import feign.RequestLine
import org.springframework.cache.annotation.Cacheable

@Headers("Content-Type: application/json")
interface EventBriteService {

    @Cacheable("eventbriteCat")
    @RequestLine("GET /v3/categories/")
    fun categoriesList(): CategoriesList


    @Cacheable("eventbriteList")
    @RequestLine("GET /v3/organizations/{organisationId}/events/")
    fun listEvent(@Param("organisationId") organisationId: String): EventsList

    @Cacheable("eventbriteEvents")
    @RequestLine("GET /v3/events/{eventId}/")
    fun event(@Param("eventId") eventId: String): Event?

    @Headers("Content-Type: application/json")
    @Body("{event}")
    @RequestLine("POST /v3/events/{eventId}/")
    fun editEvent(@Param("eventId") eventId:String, @Param("event")  event: String)


    @RequestLine("GET /v3/events/{eventId}/copy/")
    fun copyEvent(@Param("eventId") eventId:String, copy: EventCopy): Event

    @RequestLine("POST /v3/events/{eventId}/publish/")
    fun publish(@Param("eventId") eventId:String): PublishRes

    @RequestLine("POST /v3/events/{eventId}/unpublish/")
    fun unpublish(@Param("eventId") eventId:String): PublishRes

}

