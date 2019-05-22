package ch.genevajug.github.services

import ch.genevajub.MyException
import ch.genevajug.App
import ch.genevajug.model.GithubConfig
import ch.genevajug.github.model.PagesBuildRes
import ch.genevajug.github.model.UserRes
import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.json.Json
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.client.WebClientSession
import org.slf4j.LoggerFactory

open class GithubTools(
        private val githubConf: GithubConfig,
        vertx: Vertx
) {
    companion object {
        @JvmStatic
        private val LOG = LoggerFactory.getLogger(App::javaClass.name)
    }

    private var mapper: ObjectMapper
    val session: WebClientSession

    init {
        val webOptions = WebClientOptions()
        webOptions.setSsl(true)
        val client = WebClient.create(vertx, webOptions)
        session = WebClientSession.create(client)
        configSession(session, githubConf.token)
        mapper = Json.mapper
    }

    private fun configSession(session: WebClientSession, token: String) {
        session.addHeader("Accept", "application/vnd.github.v3+json")
        session.addHeader("Authorization", "token $token")
    }


    fun getUser(): Future<UserRes> {
        val apiPath = "/user"
        return getObject(apiPath, UserRes::class.java)

    }

    fun buildPagesStatus(): Future<Array<PagesBuildRes>> {
        val apiPath = "/repos/${githubConf.owner}/${githubConf.repo}/pages/builds"

        return getAndMap(apiPath, { body ->
            mapper.readValue(body.bodyAsString(), Array<PagesBuildRes>::class.java)
        })
    }



    private fun <T> getObject(apiPath: String, clazz: Class<T>): Future<T> {
        return getAndMap(apiPath, { it.bodyAsJsonObject().mapTo(clazz) })
    }


    private fun <T> getAndMap(apiPath: String, mapper: (HttpResponse<Buffer>) -> (T)): Future<T> {
        var resFuture = Future.future<T>()
        if (githubConf.disableConnexion) {
            resFuture.fail(MyException("Disable connexion set to true in the configuration"))
        } else {
            session.get(githubConf.port, githubConf.host, apiPath)
                    .ssl(true)
                    .send { ar ->
                        if (ar.succeeded()) {
                            // Obtain response
                            var response = ar.result()
                            LOG.debug("Received response with status code ${response.statusCode()}")
                            val body = response.bodyAsString()
                            LOG.debug("Received response body $body")

                            if (response.statusCode() != 200) {
                                val ex = MyException("Can't get result with api ${apiPath}. Status code was ${response.statusCode()})")
                                resFuture.fail(ex)
                            } else {
                                try {
                                    val httpResult = ar.result()
                                    val res = mapper(httpResult)

                                    resFuture.complete(res)
                                } catch (ex: Exception) {
                                    resFuture.fail(ex)
                                }
                            }
                        } else {
                            LOG.debug("Something went wrong ${ar.cause().message}")
                            resFuture.fail(ar.cause())
                        }
                    }
        }
        return resFuture;
    }

}