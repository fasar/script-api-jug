package ch.genevajug.sconfig

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class JMapperSConfig {

    @Bean
    @Primary
    fun mapper() : ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .registerModule(Jdk8Module())

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper
    }
}