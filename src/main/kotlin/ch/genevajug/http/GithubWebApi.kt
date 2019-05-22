package ch.genevajug.http

import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.slf4j.LoggerFactory
import java.lang.RuntimeException

object GithubWebApi {
    private val LOGGER = LoggerFactory.getLogger(GithubWebApi::class.java)

    fun register(router: Router, eventBus: EventBus) {

        router.route("/api/github/user").handler{ function(eventBus, it) }
    }

    private fun function(eventBus: EventBus, ctx: RoutingContext): Unit {
        eventBus.send<JsonObject>("github.user", null) {
            if (it.succeeded()) {
                val body = it.result().body()
                ctx.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(body))
            } else {
                LOGGER.info("Can't get the user: {}", it.cause().message, it.cause())
                throw RuntimeException("Can't fetch user")
            }
        }

    }


}