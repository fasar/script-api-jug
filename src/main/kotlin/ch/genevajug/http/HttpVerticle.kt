package ch.genevajug.http

import io.vertx.core.AbstractVerticle
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
        HttpFaillure.register(router, engine)
        GithubWebApi.register(router, eventBus)


        router.route("/github/user.error").handler {
            throw RuntimeException("Remove Me")
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

        router.get("/*")
                .handler(StaticHandler.create("static"))
                .handler(thymeleafRender)

        server.requestHandler(router)
        server.listen(80)
    }



}