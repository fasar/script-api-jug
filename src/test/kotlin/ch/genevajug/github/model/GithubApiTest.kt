package ch.genevajug.github.model

import ch.genevajug.sconfig.JMapperSConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Files
import java.nio.file.Paths

@RunWith(SpringRunner::class)
@Import(JMapperSConfig::class)
class GithubApiTest {
    val user = """{"login":"fasar","id":1728013,"node_id":"MDQ6VXNlcjE3MjgwMTM=","avatar_url":"https://avatars0.githubusercontent.com/u/1728013?v=4","gravatar_id":"","url":"https://api.github.com/users/fasar","html_url":"https://github.com/fasar","followers_url":"https://api.github.com/users/fasar/followers","following_url":"https://api.github.com/users/fasar/following{/other_user}","gists_url":"https://api.github.com/users/fasar/gists{/gist_id}","starred_url":"https://api.github.com/users/fasar/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/fasar/subscriptions","organizations_url":"https://api.github.com/users/fasar/orgs","repos_url":"https://api.github.com/users/fasar/repos","events_url":"https://api.github.com/users/fasar/events{/privacy}","received_events_url":"https://api.github.com/users/fasar/received_events","type":"User","site_admin":false,"name":"Fasar","company":null,"blog":"","location":null,"email":null,"hireable":null,"bio":null,"public_repos":21,"public_gists":0,"followers":1,"following":0,"created_at":"2012-05-10T21:17:36Z","updated_at":"2019-05-10T15:29:05Z","private_gists":0,"total_private_repos":0,"owned_private_repos":0,"disk_usage":4283,"collaborators":0,"two_factor_authentication":false,"plan":{"name":"free","space":976562499,"collaborators":0,"private_repos":10000}}"""

    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    fun readUserTest() {
        val user = mapper.readValue(user, UserRes::class.java)
        Assert.assertNotNull(user)
        println("User is : $user")
    }


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