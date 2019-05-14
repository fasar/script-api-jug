package ch.genevajug.http

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine
import org.slf4j.LoggerFactory


class HttpVerticle() : AbstractVerticle() {
    private val LOGGER = LoggerFactory.getLogger(HttpVerticle::class.java)

    private val engine: ThymeleafTemplateEngine

    init {
        this.engine = ThymeleafTemplateEngine.create(vertx)
    }

    @Throws(Exception::class)
    override fun start() {
        var server = vertx.createHttpServer()

        val router = Router.router(vertx)
        router.route("/*").handler(StaticHandler.create("static"))

        server.requestHandler(router)
        server.listen(80)
    }


}