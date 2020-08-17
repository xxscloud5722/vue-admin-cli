package com.yuemia.ndxy.config

import io.vertx.core.http.ServerWebSocket

data class USession(
    var id: String = "1",
    var account: String = "",
    var token: String = "",
    var userType: String = "",
    var agent: String = "",
    var ip: String = "",
    var role: List<String> = ArrayList(),
    var permissions: List<String> = ArrayList(),
    var weChatInfo: WeChatInfo = WeChatInfo(),
    var webSocket: ServerWebSocket? = null
) {

    data class WeChatInfo(
        var openId: String = "",
        var nickName: String = "",
        var avatarUrl: String = ""
    )
}