package ch.genevajug.http

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.slf4j.LoggerFactory
import java.lang.RuntimeException

object GithubWebApi {
    private val LOGGER = LoggerFactory.getLogger(GithubWebApi::class.java)

    fun register(router: Router, eventBus: EventBus) {
        router.route("/api/github/user").handler{ serveJson("github.user", eventBus, it) }
        router.route("/api/github/pages").handler{ serveJson("github.build-pages-status", eventBus, it) }
        router.route("/github/user.html").handler{ getUserWeb(eventBus, it) }
    }

    private fun serveJson(requestService: String, eventBus: EventBus, ctx: RoutingContext) {
        eventBus.send<Buffer>(requestService, null) {
            if (it.succeeded()) {
                val body = it.result().body()
                val decodeValue = Json.decodeValue(body)

                ctx.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(decodeValue))
            } else {
                LOGGER.info("Can't get the pages: {}", it.cause().message, it.cause())
                throw RuntimeException("Can't fetch pages")
            }
        }
    }

    private fun getUserWeb(eventBus: EventBus, ctx: RoutingContext): Unit {
        eventBus.send<Buffer>("github.user", null) {
            if (it.succeeded()) {
                val body : Buffer = it.result().body()
                val decodeValue = Json.decodeValue(body)
                ctx.put("guser", decodeValue)
                ctx.next()
            } else {
                LOGGER.info("Can't get the user: {}", it.cause().message, it.cause())
                throw RuntimeException("Can't fetch user")
            }
        }
    }
}