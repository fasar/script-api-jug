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
                    LOG.info("someone ask the configuration ${it.result()}")
                    val mapFrom = JsonObject.mapFrom(myConf)
                    message.reply(mapFrom)
                }
                eventBus.publish("init.config", JsonObject().put("result", true))
            } else {
                LOG.info("Can't get the configuration {}", it.cause().message, it.cause())
            }
        }
    }


}