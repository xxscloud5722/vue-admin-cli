package com.yuemia.ndxy.config


import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import io.vertx.core.json.JsonObject
import java.text.SimpleDateFormat
import java.util.*


data class ApiResponse(
    val success: Boolean,
    val data: Any? = null,
    val code: String? = null,
    val message: String? = null,
    val other: Any? = null
) {


//    class DoubleTypeAdapter : TypeAdapter<Double>() {
//        override fun write(out: JsonWriter, value: Double?) {
//            if (value == null) {
//                out.nullValue()
//                return
//            }
//            out.value(value)
//        }
//
//        override fun read(value: JsonReader): Double? {
//            if (value.peek() == JsonToken.NULL) {
//                return null
//            }
//            return value.nextDouble()
//        }
//
//    }
//
//
//    class BigDecimalTypeAdapter : TypeAdapter<BigDecimal>() {
//        override fun write(out: JsonWriter, value: BigDecimal?) {
//            if (value == null) {
//                out.nullValue()
//                return
//            }
//            out.value(value.setScale(2, BigDecimal.ROUND_DOWN))
//        }
//
//        override fun read(value: JsonReader): BigDecimal? {
//            if (value.peek() == JsonToken.NULL) {
//                return null
//            }
//            return BigDecimal.valueOf(value.nextDouble())
//        }
//
//    }
//
//    class StringTypeAdapter : TypeAdapter<String>() {
//        override fun write(out: JsonWriter, value: String?) {
//            if (value.isNullOrEmpty()) {
//                out.nullValue()
//                return
//            }
//            out.value(value)
//        }
//
//        override fun read(value: JsonReader): String? {
//            if (value.peek() == JsonToken.NULL) {
//                return null
//            }
//            return value.nextString()
//        }
//
//    }


    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()

        init {
            val module = SimpleModule()
            module.addSerializer(JsonObject::class.java, object : JsonSerializer<JsonObject>() {
                override fun serialize(value: JsonObject?, gen: JsonGenerator?, serializers: SerializerProvider?) {
                    if (value?.map?.size ?: 0 > 0) {
                        gen?.writeObject(value?.map)
                    } else {
                        gen?.writeNull()
                    }
                }
            })
            module.addSerializer(Date::class.java, object : JsonSerializer<Date>() {
                override fun serialize(value: Date?, gen: JsonGenerator?, serializers: SerializerProvider?) {
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    gen?.writeObject(formatter.format(value))
                }
            })
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            objectMapper.registerModule(module)
        }


        fun error(message: String): ApiResponse {
            return ApiResponse(false, null, "9999", message)
        }

        fun error(code: String, message: String?): ApiResponse {
            return ApiResponse(false, null, code, message)
        }

        fun success(): ApiResponse {
            return ApiResponse(true, null, "200", null)
        }

        fun success(data: Any?): ApiResponse {
            return ApiResponse(true, data, "200", null)
        }

        fun success(data: Any?, other: Any?): ApiResponse {
            return ApiResponse(true, data, "200", null, other)
        }

        fun success(data: Any?, message: String?): ApiResponse {
            return ApiResponse(true, data, "200", message)
        }


    }

    override fun toString(): String {
        return objectMapper.writeValueAsString(this)
    }

}