package com.yuemia.ndxy.dao

import com.google.inject.Inject
import com.yuemia.ndxy.core.MySQLCore
import com.yuemia.ndxy.data.AdminPermissionDO
import io.vertx.ext.sql.SQLConnection

class AdminPermissionDAO @Inject constructor(private val sqlCore: MySQLCore) {
    suspend fun getPermissionList(transaction: SQLConnection? = null): List<AdminPermissionDO> {
        return sqlCore.query(
            """ SELECT `id`,`name` FROM system_admin_permission""",
            arrayListOf(),
            AdminPermissionDO::class.java,
            transaction
        )
    }
}