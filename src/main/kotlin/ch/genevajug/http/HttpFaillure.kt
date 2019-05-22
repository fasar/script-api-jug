package ch.genevajug.http

import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine
import org.slf4j.LoggerFactory

object HttpFaillure {
    private val LOGGER = LoggerFactory.getLogger(HttpFaillure::class.java)


    fun register(router: Router, engine: ThymeleafTemplateEngine) {
        router.errorHandler(500) { function(engine, it) }
    }

    private fun function(engine: ThymeleafTemplateEngine, routingCtx: RoutingContext): Unit {
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


}