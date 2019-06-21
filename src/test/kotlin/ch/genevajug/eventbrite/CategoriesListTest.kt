package ch.genevajug.eventbrite

import ch.genevajug.sconfig.JMapperSConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Files
import java.nio.file.Paths

@RunWith(SpringRunner::class)
@Import(JMapperSConfig::class)
class CategoriesListTest {
    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    fun readCategoriesListReaderTest() {
        val resource = this.javaClass.getResource("categoriesList.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        val res = mapper.readValue(br, CategoriesList::class.java)
        Assert.assertNotNull(res)
        println("Res is : $res")
    }


    @Test
    fun readEventListReaderTest() {
        val resource = this.javaClass.getResource("eventList.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        val res = mapper.readValue(br, EventList::class.java)
        Assert.assertNotNull(res)
        println("Res is : $res")
    }

}