package ch.genevajug.github.services

import ch.genevajug.App
import ch.genevajug.github.model.ErrorBuildRes
import ch.genevajug.model.GithubConfig
import ch.genevajug.github.model.PagesBuildRes
import ch.genevajug.github.model.StatusRes
import ch.genevajug.github.model.UserRes
import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.client.WebClientSession
import org.slf4j.LoggerFactory
import java.time.Instant

class GithubToolsMock(
        private val githubConf: GithubConfig?,
        vertx: Vertx?
)  {
    companion object {
        @JvmStatic
        private val LOG = LoggerFactory.getLogger(App::javaClass.name)
    }


    private fun configSession(session: WebClientSession, token: String) {
        session.addHeader("Accept", "application/vnd.github.v3+json")
        session.addHeader("Authorization", "token $token")
    }


    fun getUser(): Future<UserRes> {
        val user = UserRes("Fasar", 23, "nodeId",
                "http://avatar", "http://gravatar", "http://myurl",
                "http://htmlurl", "user",
                "http://followingUrl", false
        )
        return Future.succeededFuture(user)
    }


    fun buildPagesStatus(): Future<Array<PagesBuildRes>> {
        var pages = emptyArray<PagesBuildRes>()
        pages = pages.plus(PagesBuildRes("http://url", StatusRes.queued, ErrorBuildRes(""), "02315", 200, Instant.now(), Instant.now(), getUser().result()))
        return Future.succeededFuture(pages)

    }


}