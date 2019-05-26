package ch.genevajug.http

import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.Json
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.slf4j.LoggerFactory

object GithubWebApi {
    private val LOGGER = LoggerFactory.getLogger(GithubWebApi::class.java)

    fun register(router: Router, eventBus: EventBus) {
        router.route("/api/github/user").handler{ serveJson("github.user", eventBus, it) }
        router.route("/api/github/pages").handler{ serveJson("github.build-pages-status", eventBus, it) }
        router.route("/github/user.html").handler{
            setContextVar("github.user", "guser", eventBus, it)
            it.next()
        }
        router.route("/index.html").handler{ ctx ->
            val f1 = setContextVar("github.user", "guser", eventBus, ctx)
            val f2 = setContextVar("github.build-pages-status", "gpages", eventBus, ctx)
            CompositeFuture.join(f1, f2).setHandler {
                if(it.succeeded()) {
                    ctx.next()
                } else {
                    ctx.fail(it.cause())
                }
            }
        }
    }

    private fun serveJson(requestService: String, eventBus: EventBus, ctx: RoutingContext) {
        eventBus.send<Buffer>(requestService, null) {
            if (it.succeeded()) {
                val body = it.result().body()
                val decodeValue : JsonArray  = Json.decodeValue(body) as JsonArray

                ctx.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(decodeValue))
            } else {
                LOGGER.info("Can't get the pages: {}", it.cause().message, it.cause())
                throw RuntimeException("Can't fetch pages")
            }
        }
    }

    private fun setContextVar(requestService: String, thymeParam: String, eventBus: EventBus, ctx: RoutingContext): Future<Unit> {
        val future = Future.future<Unit>()

        eventBus.send<Buffer>(requestService, null) {
            if (it.succeeded()) {
                val body : Buffer = it.result().body()
                val decodeValue = Json.decodeValue(body)
                ctx.put(thymeParam, decodeValue)
                future.complete()
            } else {
                LOGGER.info("Can't get the service {} : {}", requestService, it.cause().message, it.cause())
                val exception = RuntimeException("Can't fetch pages")
                future.fail(exception)
                throw exception
            }
        }

        return future
    }


}