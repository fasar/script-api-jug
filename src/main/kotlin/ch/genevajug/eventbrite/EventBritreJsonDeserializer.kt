package ch.genevajug.eventbrite

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.RuntimeJsonMappingException
import feign.Response
import feign.Util
import feign.codec.Decoder
import org.apache.commons.lang3.StringUtils
import java.io.IOException
import java.lang.reflect.Type

class EventBritreJsonDeserializer(var mapper: ObjectMapper) : Decoder {

    @Throws(IOException::class)
    override fun decode(response: Response, type: Type): Any? {
        if (response.status() == 404)
            return Util.emptyValueOf(type)
        if (response.body() == null)
            return null
        var reader = response.body().asReader()
        var text = reader.readText()
        if (StringUtils.isEmpty(text)){
            return null
        }
        val constructType = mapper.constructType(type)
        return decode(text, constructType)

    }

    fun decode(json: String, constructType: JavaType): Any {

        val text = json.replace(": {}", ": null")

        try {
            // Read the first byte to see if we have any data
            return mapper.readValue<Any>(text, constructType)
        } catch (e: RuntimeJsonMappingException) {
            if (e.cause != null && e.cause is IOException) {
                throw IOException::class.java.cast(e.cause)
            }
            throw e
        }
    }
}