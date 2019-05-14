package ch.genevajug

import ch.genevajug.model.ErrorBuildRes
import ch.genevajug.model.PagesBuildRes
import io.vertx.core.json.Json
import org.junit.jupiter.api.Test

internal class GithubToolsTest {

    @Test
    fun testPagesStatus() {
        masterInit()
        val mapper = Json.mapper

        val inputArray = """[{"url":"https://api.github.com/repos/fasar/fasar.github.io/pages/builds/129650668","status":"built","error":{"message":null},"pusher":{"login":"fasar","id":1728013,"node_id":"MDQ6VXNlcjE3MjgwMTM=","avatar_url":"https://avatars0.githubusercontent.com/u/1728013?v=4","gravatar_id":"","url":"https://api.github.com/users/fasar","html_url":"https://github.com/fasar","followers_url":"https://api.github.com/users/fasar/followers","following_url":"https://api.github.com/users/fasar/following{/other_user}","gists_url":"https://api.github.com/users/fasar/gists{/gist_id}","starred_url":"https://api.github.com/users/fasar/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/fasar/subscriptions","organizations_url":"https://api.github.com/users/fasar/orgs","repos_url":"https://api.github.com/users/fasar/repos","events_url":"https://api.github.com/users/fasar/events{/privacy}","received_events_url":"https://api.github.com/users/fasar/received_events","type":"User","site_admin":false},"commit":"e4219b5d18bdb1bdb021f6f11a9ae30f3401e000","duration":16384,"created_at":"2019-05-08T13:14:44Z","updated_at":"2019-05-08T13:15:00Z"},{"url":"https://api.github.com/repos/fasar/fasar.github.io/pages/builds/129563051","status":"built","error":{"message":null},"pusher":{"login":"fasar","id":1728013,"node_id":"MDQ6VXNlcjE3MjgwMTM=","avatar_url":"https://avatars0.githubusercontent.com/u/1728013?v=4","gravatar_id":"","url":"https://api.github.com/users/fasar","html_url":"https://github.com/fasar","followers_url":"https://api.github.com/users/fasar/followers","following_url":"https://api.github.com/users/fasar/following{/other_user}","gists_url":"https://api.github.com/users/fasar/gists{/gist_id}","starred_url":"https://api.github.com/users/fasar/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/fasar/subscriptions","organizations_url":"https://api.github.com/users/fasar/orgs","repos_url":"https://api.github.com/users/fasar/repos","events_url":"https://api.github.com/users/fasar/events{/privacy}","received_events_url":"https://api.github.com/users/fasar/received_events","type":"User","site_admin":false},"commit":"8a4646bdc657ad739310b8124b5e2aa93e423c70","duration":17432,"created_at":"2019-05-07T20:00:10Z","updated_at":"2019-05-07T20:00:27Z"}]"""
        val readValue = mapper.readValue(inputArray, Array<PagesBuildRes>::class.java)
        println("Get the object ${readValue}")

    }

}


