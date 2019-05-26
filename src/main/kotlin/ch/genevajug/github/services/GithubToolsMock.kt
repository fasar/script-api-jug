package ch.genevajug.github.services

import ch.genevajug.App
import ch.genevajug.github.model.ErrorBuildRes
import ch.genevajug.github.model.PagesBuildRes
import ch.genevajug.github.model.StatusRes
import ch.genevajug.github.model.UserRes
import ch.genevajug.model.GithubConfig
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClientSession
import org.slf4j.LoggerFactory
import java.time.Instant

class GithubToolsMock(
        private val githubConf: GithubConfig?,
        private val vertx: Vertx?
) {

    private var userBuffer: Future<UserRes>? = null


    companion object {
        @JvmStatic
        private val LOG = LoggerFactory.getLogger(App::javaClass.name)
    }


    private fun configSession(session: WebClientSession, token: String) {
        session.addHeader("Accept", "application/vnd.github.v3+json")
        session.addHeader("Authorization", "token $token")
    }


    fun getUser(): Future<UserRes> {
        val future = Future.future<UserRes>()

        if (userBuffer == null) {
            val user = getUserPriv()
            vertx!!.setTimer(2000) { future.complete(user) }
        }
        return future
    }


    fun getUserPriv(): UserRes {
        val user = UserRes("Fasar", 23, "nodeId",
                "http://avatar", "http://gravatar", "http://myurl",
                "http://htmlurl", "user",
                "http://followingUrl",
                false
        )

        return user
    }


    fun buildPagesStatus(): Future<Array<PagesBuildRes>> {
        var pages = emptyArray<PagesBuildRes>()
        pages = pages.plus(PagesBuildRes("http://url", StatusRes.queued, ErrorBuildRes(""), "02315", 200, Instant.now(), Instant.now(), getUserPriv()))
        pages = pages.plus(PagesBuildRes("http://url", StatusRes.built, ErrorBuildRes("no error"), "021354", 300, Instant.now(), Instant.now(), getUserPriv()))
        pages = pages.plus(PagesBuildRes("http://url", StatusRes.errored, ErrorBuildRes("Last error here"), "021354", 300, Instant.now(), Instant.now(), getUserPriv()))

        val future = Future.future<Array<PagesBuildRes>>()
        vertx!!.setTimer(500) { future.complete(pages) }
        return future


    }


}