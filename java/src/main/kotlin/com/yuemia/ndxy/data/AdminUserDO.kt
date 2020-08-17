package com.yuemia.ndxy.data

data class AdminUserDO(
    var id: String = "",
    var name: String = "",
    var phone: String = "",
    var email: String = "",
    var avatarUrl: String = "",
    var account: String = "",
    var password: String = "",
    var description: String = "",
    var token: String = "",
    var remark: String = "",
    var status: Int = 0
)