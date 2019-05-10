package ch.genevajug

import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.client.WebClientSession
import java.lang.RuntimeException

class GithubTools(
        private val githubConf: GithubConfig,
        vertx: Vertx
) {

    val session: WebClientSession

    init {
        val webOptions = WebClientOptions()
        webOptions.setSsl(true)
        val client = WebClient.create(vertx, webOptions)
        session = WebClientSession.create(client)
        configSession(session, githubConf.token)
    }

    private fun configSession(session: WebClientSession, token: String) {
        session.addHeader("Accept", "application/vnd.github.v3+json")
        session.addHeader("Authorization", "token $token")
    }


    fun getUser(): Future<JsonObject> {
        var configFuture = Future.future<JsonObject>()
        if(githubConf.noConnect) {
            configFuture.fail(RuntimeException("No connexion asked in the configuration"))
        } else {
            session.get(githubConf.port, githubConf.host, "/user")
                    .ssl(true)
                    .send { ar ->
                        if (ar.succeeded()) {
                            // Obtain response
                            var response = ar.result()

                            println("Received response with status code ${response.statusCode()}")
                            configFuture.complete(ar.result().bodyAsJsonObject())
                        } else {
                            println("Something went wrong ${ar.cause().message}")
                            configFuture.fail(ar.cause())
                        }
                    }
        }
        return configFuture;
    }

}