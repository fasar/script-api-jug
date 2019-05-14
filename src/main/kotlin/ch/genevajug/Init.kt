package ch.genevajug

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.json.Json

fun masterInit() {
    Json.mapper.registerKotlinModule()
    Json.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

}