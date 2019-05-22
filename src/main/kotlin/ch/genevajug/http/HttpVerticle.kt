package ch.genevajug.http

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.TemplateHandler
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine
import org.slf4j.LoggerFactory
import java.lang.RuntimeException


class HttpVerticle() : AbstractVerticle() {
    private val LOGGER = LoggerFactory.getLogger(HttpVerticle::class.java)

    @Throws(Exception::class)
    override fun start() {
        val engine = ThymeleafTemplateEngine.create(vertx)
        val thymeleafRender = TemplateHandler.create(engine)

        var server = vertx.createHttpServer()
        val eventBus = vertx.eventBus()

        val router = Router.router(vertx)
        router.errorHandler(500){ routingCtx ->
            engine.render(JsonObject(routingCtx.data()), "templates/error.html") {
                if (it.succeeded()) {
                    val response = routingCtx.response()
                    response.putHeader(HttpHeaders.CONTENT_TYPE, "text/html")
                    response.statusCode = 500
                    response.end(it.result())
                } else {
                    LOGGER.error(it.cause().message, it.cause())
                }
            }
        }
        router.route("/github/user.html")
                .handler { routingCtx ->
                    eventBus.send<JsonObject>("github.user", null) {
                        if (it.succeeded()) {
                            val body = it.result().body()
                            routingCtx.put("githubUser", body)
                            routingCtx.put("name2", "oo2")
                            LOGGER.info("Github user : {}", body)
                            routingCtx.next()
                        } else {
                            LOGGER.info("Can't get the user: {}", it.cause().message, it.cause())
                            throw RuntimeException("Can't fetch user")
                        }
                    }
                }
        router.route("/github/user.error").handler {
            throw RuntimeException("Remove Me")
        }
        router.route("/*")
                .handler {
                    it.put("name", "iii2")
                    it.next()
                }
                .handler(StaticHandler.create("static"))
                .handler(thymeleafRender)

        server.requestHandler(router)
        server.listen(80)
    }


}