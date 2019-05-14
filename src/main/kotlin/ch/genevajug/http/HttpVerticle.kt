package ch.genevajug.http

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.TemplateHandler
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine
import org.slf4j.LoggerFactory


class HttpVerticle() : AbstractVerticle() {
    private val LOGGER = LoggerFactory.getLogger(HttpVerticle::class.java)

    @Throws(Exception::class)
    override fun start() {
        val engine = ThymeleafTemplateEngine.create(vertx)

        val render = TemplateHandler.create(engine)

        var server = vertx.createHttpServer()

        val router = Router.router(vertx)
        router.route("/*")
                .handler(StaticHandler.create("static"))
                .handler(render)

        server.requestHandler(router)
        server.listen(80)
    }


}