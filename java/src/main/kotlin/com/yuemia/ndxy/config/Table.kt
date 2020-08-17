package com.yuemia.ndxy.config

import io.vertx.core.json.JsonObject

data class Table(
    val currentIndex: Int = 1,
    val totalCount: Int = 0,
    val rows: Any? = null,
    val pageSize: Int = 50,
    val limit: Int = (currentIndex - 1) * pageSize,
    val data: JsonObject = JsonObject()
)