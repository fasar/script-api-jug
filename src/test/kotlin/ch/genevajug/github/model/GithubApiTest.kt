package ch.genevajug.github.model

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
class GithubApiTest {

    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    fun readUserInReaderTest() {
        val resource = this.javaClass.getResource("user.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        val user = mapper.readValue(br, UserRes::class.java)
        Assert.assertNotNull(user)
        println("User is : $user")
    }

    @Test
    fun readPageBuildReaderTest() {
        val resource = this.javaClass.getResource("pageBuild.json")
        val br = Files.newBufferedReader(Paths.get(resource.toURI()))
        val pb = mapper.readValue(br, PagesBuildRes::class.java)
        Assert.assertNotNull(pb)
        println("Page Build is : $pb")
    }

}