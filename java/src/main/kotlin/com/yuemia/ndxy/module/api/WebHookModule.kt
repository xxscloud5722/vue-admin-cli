package com.yuemia.ndxy.module.api

import com.google.inject.Inject
import com.yuemia.ndxy.module.BaseModule
import com.yuemia.ndxy.service.api.WebHookService
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class WebHookModule @Inject constructor(router: Router, private val webHookService: WebHookService) : BaseModule() {
    init {
        addRouter(router.post("/webHook/changeDoctorsInfo")).coroutineHandler(5000L, ::changeDoctorsInfo)
    }

    private suspend fun changeDoctorsInfo(content: RoutingContext) {

    }
}