package io.fasar.rest

import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.core.json.Json.mapper
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.client.WebClientSession
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class RestProxy(
        val vertx: Vertx,
        val baseUrl: String,
        val headers: Map<String, String>
) : InvocationHandler {

    private var session: WebClientSession

    companion object {
        fun <T> of(vertx: Vertx, clazz: Class<T>): T {
            val emels = arrayOfNulls<String>(10)

            return Proxy.newProxyInstance(clazz.getClassLoader(),
                    arrayOf( clazz ),
                    RestProxy(vertx)) as T
        }
    }

    init {
        val webOptions = WebClientOptions()
        webOptions.setSsl(true)
        val client = WebClient.create(vertx, webOptions)
        session = WebClientSession.create(client)
        configSession()
        mapper = Json.mapper
    }

    private fun configSession() {
        for ((key, value) in headers) {
            session.addHeader(key, value)
        }
    }

    fun <T> tt(clazz: Class<T>): Class<T> {
        return clazz
    }

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
        try {
            return method.invoke(proxy, args)
        } catch (e: IllegalAccessException ) {
            // if this Method object is enforcing Java language access control and the underlying method is inaccessible
        } catch (e: IllegalAccessException ) {
            // if the method is an instance method and the specified object argument is not an instance of the class or
            // interface declaring the underlying method (or of a subclass or implementor thereof); if the number of
            // actual and formal parameters differ; if an unwrapping conversion for primitive arguments fails; or if,
            // after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter
            // type by a method invocation conversion
        } catch (e: NoSuchMethodException  ) {
            // ignore
        }

        var getAnnotation = method.getAnnotation(Get::class.java)
        if (getAnnotation != null) {
            var path = getAnnotation.path
        }
    }

}

