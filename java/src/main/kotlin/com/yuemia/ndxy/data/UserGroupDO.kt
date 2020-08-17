package com.yuemia.ndxy.data

import java.util.*

data class UserGroupDO(
    var id: String = "",
    var name: String = "",
    var createTime: Date? = null,
    var userList: List<AdminUserDO>? = null,
)