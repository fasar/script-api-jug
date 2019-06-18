package ch.genevajug.http

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.TemplateHandler
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine
import org.slf4j.LoggerFactory
import org.thymeleaf.IEngineConfiguration
import org.thymeleaf.templateresolver.FileTemplateResolver
import org.thymeleaf.templateresource.ITemplateResource


class HttpVerticle : AbstractVerticle() {
    private val LOGGER = LoggerFactory.getLogger(HttpVerticle::class.java)

    @Throws(Exception::class)
    override fun start() {
        val engine = ThymeleafTemplateEngine.create(vertx)

        // To avoid recompiling to check new html files
        engine.thymeleafTemplateEngine.setTemplateResolver(object: FileTemplateResolver() {
            init {
                isCacheable = false
            }
            override fun computeTemplateResource(configuration: IEngineConfiguration?, ownerTemplate: String?, template: String?, resourceName: String?, characterEncoding: String?, templateResolutionAttributes: MutableMap<String, Any>?): ITemplateResource {
                val resourceName2 = "src/main/resources/${resourceName}"
                return super.computeTemplateResource(configuration, ownerTemplate, template, resourceName2, characterEncoding, templateResolutionAttributes)
            }

        })

        val thymeleafRender = TemplateHandler.create(engine)

        var server = vertx.createHttpServer()
        val eventBus = vertx.eventBus()

        val router = Router.router(vertx)
        HttpFaillure.register(router, engine)
        GithubWebApi.register(router, eventBus)


        router.route("/error").handler {
            throw RuntimeException("Remove Me")
        }

        router.get("/assets/*").handler(StaticHandler.create("META-INF/resources/webjars"))
        router.get("/*")
                .handler(StaticHandler.create("static"))
                .handler(thymeleafRender)

        server.requestHandler(router)
        server.listen(80)
    }


}