package ch.genevajug.api

import ch.genevajug.App
import ch.genevajug.model.MyConfig
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import org.slf4j.LoggerFactory

class GithubVerticle : AbstractVerticle() {
    companion object {
        @JvmStatic
        private val LOG = LoggerFactory.getLogger(GithubVerticle::javaClass.name)
    }

    // Called when verticle is deployed
    override fun start() {
        val eventBus = vertx.eventBus()
        val configuration = Future.future<MyConfig>()
        eventBus.send<JsonObject>("config", JsonObject()) {
            if (it.succeeded()) {
                val myConfig = it.result()
                val json = myConfig.body()
                val myConf = json.mapTo(MyConfig::class.java)
                configuration.complete(myConf)
            } else {
                LOG.error("Can't get the configuration {}", it.cause().message, it.cause())
                configuration.fail(it.cause())
            }
        }
        configuration.map {
            val githToken = it.github.token
            LOG.info("Got token: $githToken")
            GithubTools(it.github, vertx)
        }.map {
            eventBus.consumer<Any>("github.user", { message ->
                message.reply(it.getUser())
            })
            eventBus.consumer<Any>("github.build-pages-status", { message ->
                message.reply(it.buildPagesStatus())
            })

            it
        }



    }


}