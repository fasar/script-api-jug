package ch.genevajug

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.json.JsonObject

var fileStore = ConfigStoreOptions()
        .setConfig(JsonObject("""{ "path" : "conf/application.json"}"""))
        .setType("file")
        .setOptional(false)

var internalFileStore = ConfigStoreOptions()
        .setConfig(JsonObject("""{ "path" : "conf/config.json"}"""))
        .setType("file")
        .setOptional(false)

var sysPropsStore = ConfigStoreOptions()
        .setType("sys")

var envPropsStore = ConfigStoreOptions()
        .setType("env")

var retrieverOptions = ConfigRetrieverOptions().setStores(listOf(internalFileStore, fileStore, sysPropsStore, envPropsStore))

