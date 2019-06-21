package ch.genevajug.eventbrite

import feign.RequestLine
import org.springframework.cache.annotation.Cacheable

interface EventBriteService {

    @Cacheable("eventbrite")
    @RequestLine("GET /v3/categories/")
    fun categoriesList(): CategoriesList



}