package ch.genevajug.config

import ch.genevajug.App
import ch.genevajug.model.MyConfig
import ch.genevajug.retrieverOptions
import io.vertx.config.ConfigRetriever
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import org.slf4j.LoggerFactory

class ConfigVerticle : AbstractVerticle() {

    companion object {
        @JvmStatic
        private val LOG = LoggerFactory.getLogger(ConfigVerticle::javaClass.name)
    }

    // Called when verticle is deployed
    override fun start() {
        val eventBus = vertx.eventBus()


        var configFuture = Future.future<JsonObject>()
        var retriever = ConfigRetriever.create(vertx, retrieverOptions)
        retriever.getConfig { ar ->
            if (ar.succeeded()) {
                configFuture.complete(ar.result())
            } else {
                configFuture.fail(ar.cause())
            }
        }

        configFuture.setHandler {
            if (it.succeeded()) {
                val myConf = it.result().getJsonObject("config").mapTo(MyConfig::class.java)

                eventBus.consumer<Any>("config") { message ->
                    LOG.info("someone get the configuration ${it.result()}")
                    message.reply(myConf)
                }
            } else {
                LOG.info("Can't get the configuration {}", it.cause().message, it.cause())
            }
        }
    }


}