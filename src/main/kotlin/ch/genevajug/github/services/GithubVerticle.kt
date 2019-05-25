package ch.genevajug.github.services

import ch.genevajug.model.MyConfig
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.Json
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import org.slf4j.LoggerFactory
import java.lang.RuntimeException

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
            GithubToolsMock(it.github, vertx)
        }.map {
            registerOnEventBus(eventBus, it)
            it
        }

    }

    private fun registerOnEventBus(eventBus: EventBus, githubTools: GithubToolsMock) {
        eventBus.consumer<JsonObject>("github.user", {
            answereFun(it) { githubTools.getUser()}
        })
        eventBus.consumer<Any>("github.build-pages-status", {
            answereFun(it) { githubTools.buildPagesStatus()}
        })
    }

    fun answereFun(message: Message<*>, mapper: () -> Future<*>)  {
        val myFuture = mapper()
        myFuture.map {
            mapSuccess(it, message)
        }.recover {
            mapFail(message, it)
        }
    }

    private fun mapFail(message: Message<*>, it: Throwable): Future<Future<Any>?>? {
        message.fail(1, it.message)
        return Future.failedFuture(it)
    }

    private fun mapSuccess(it: Any?, message: Message<*>): Future<Any>? {
        val res = Json.encodeToBuffer(it)
        message.reply(res)
        return Future.succeededFuture(it)
    }


}