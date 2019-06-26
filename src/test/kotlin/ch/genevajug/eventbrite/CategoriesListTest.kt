package ch.genevajug.eventbrite

import ch.genevajub.eventbrite.model.Organizer
import ch.genevajug.sconfig.JMapperSConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Component
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Files
import java.nio.file.Paths

@RunWith(SpringRunner::class)
@Import(JMapperSConfig::class)
@Component
class CategoriesListTest(
) {
    @Autowired
    lateinit var mapper:ObjectMapper

    lateinit var decoder: EventBritreJsonDeserializer

    @Before
    fun setUp() {
        decoder = EventBritreJsonDeserializer(mapper)
    }


    @Test
    fun readAttendeeReaderTest() {
        val resource = this.javaClass.getResource("attendee.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        var text = br.readText()
        val type = mapper.constructType(Attendee::class.java)
        val res = decoder.decode(text, type )
        Assert.assertNotNull(res)
        println("Res is : $res")
    }


    @Test
    fun readCategoriesListReaderTest() {
        val resource = this.javaClass.getResource("categoriesList.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        var text = br.readText()
        val type = mapper.constructType(CategoriesList::class.java)
        val res = decoder.decode(text, type )
        Assert.assertNotNull(res)
        println("Res is : $res")
    }

    @Test
    fun readEventReaderTest() {
        val resource = this.javaClass.getResource("event.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        var text = br.readText()
        val type = mapper.constructType(Event::class.java)
        val res = decoder.decode(text, type )
        Assert.assertNotNull(res)
        println("Res is : $res")
    }


    @Test
    fun readEventListReaderTest() {
        val resource = this.javaClass.getResource("eventList.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        var text = br.readText()
        val type = mapper.constructType(EventsList::class.java)
        val res = decoder.decode(text, type )
        Assert.assertNotNull(res)
        println("Res is : $res")
    }


    @Test
    @Ignore
    fun readEventReportReaderTest() {
        val resource = this.javaClass.getResource("organization.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        var text = br.readText()
        val type = mapper.constructType(Organizer::class.java)
        val res = decoder.decode(text, type )
        Assert.assertNotNull(res)
        println("Res is : $res")
    }


    @Test
    fun readUserReaderTest() {
        val resource = this.javaClass.getResource("user.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        var text = br.readText()
        val type = mapper.constructType(User::class.java)
        val res = decoder.decode(text, type )
        Assert.assertNotNull(res)
        println("Res is : $res")
    }


}