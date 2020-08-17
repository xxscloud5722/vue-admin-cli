package com.yuemia.ndxy.module



import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.yuemia.ndxy.config.USession
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory


abstract class BaseModule {
    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()
        private val log = LoggerFactory.getLogger(BaseModule::class.java)
        private val debug = (System.getProperties()["active"] ?: System.getenv("active") ?: "").toString().isEmpty()

        init {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }


    fun addRouter(route: Route): Route {
        return route.handler { c ->
            GlobalScope.launch(c.vertx().dispatcher()) {
                c.response().headers().add("content-type", "application/json; charset=utf-8")
                c.next()
            }
        }
    }

    fun <T> getBody(content: RoutingContext, clazz: Class<T>): T {
        val result = objectMapper.readValue(content.bodyAsString, clazz)
        return result ?: clazz.newInstance()
    }

    fun getBody(content: RoutingContext): JsonObject {
        return content.bodyAsJson
    }

    fun Route.coroutineHandler(time: Long = 5000L, fn: suspend (RoutingContext) -> Unit): Route {
        return handler { ctx ->
            GlobalScope.launch(ctx.vertx().dispatcher()) {
                val startTime = System.currentTimeMillis()
                try {
                    withTimeout(if (debug) Long.MAX_VALUE else time) {
                        fn(ctx)
                    }
                } catch (e: Exception) {
                    ctx.fail(e)
                } catch (e: Throwable) {
                    ctx.fail(e)
                }
                log.info("[request] ${ctx.request().path()} time: ${System.currentTimeMillis() - startTime}")
            }
        }
    }

    fun Route.coroutineHandler(time: Long = 5000L, fn: suspend (RoutingContext, USession) -> Unit): Route {
        return handler { ctx ->
            GlobalScope.launch(ctx.vertx().dispatcher()) {
                val startTime = System.currentTimeMillis()
                withTimeout(if (debug) Long.MAX_VALUE else time) {
                    try {
                        fn(ctx, USession())
                    } catch (e: Exception) {
                        ctx.fail(e)
                    } catch (e: Throwable) {
                        ctx.fail(e)
                    }
                }
                log.info("[request] ${ctx.request().path()} time: ${System.currentTimeMillis() - startTime}")
            }
        }
    }


}