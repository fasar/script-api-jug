package io.fasar.rest

import io.vertx.core.json.JsonObject
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class JsonProxy (
        val json: JsonObject
) : InvocationHandler {

    companion object {
        fun <T> of(json: JsonObject, clazz: Class<T>): T {
            return Proxy.newProxyInstance(
                    json::class.java.classLoader,
                    arrayOf( clazz ),
                    JsonProxy(json)) as T
        }
    }

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
        if (method.name.startsWith("get")) {

        } else if (method.name.startsWith("put")) {

        }
    }

}