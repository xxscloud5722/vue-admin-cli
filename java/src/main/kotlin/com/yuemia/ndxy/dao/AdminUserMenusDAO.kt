package com.yuemia.ndxy.dao

import com.google.inject.Inject
import com.yuemia.ndxy.core.MySQLCore
import io.vertx.core.json.JsonObject
import io.vertx.ext.sql.SQLConnection

class AdminUserMenusDAO @Inject constructor(private val sqlCore: MySQLCore) {
    suspend fun getMenusList(userId: String, transaction: SQLConnection? = null): List<JsonObject> {
        return sqlCore.query(
            """
                    SELECT * FROM system_admin_user_menus WHERE id IN (
                            SELECT saupm.menus_id FROM system_admin_user_role saur
                            LEFT JOIN system_admin_user_role_permission saurp
                            ON saurp.role_id = saur.role_id
                            LEFT JOIN system_admin_user_permission_menus saupm
                            ON saupm.permission_id = saurp.permission_id
                            WHERE saur.user_id = ?
                     )
                    UNION ALL
                    SELECT * FROM system_admin_user_menus WHERE id IN (
                         SELECT pid FROM system_admin_user_menus WHERE id IN (
                            SELECT saupm.menus_id FROM system_admin_user_role saur
                            LEFT JOIN system_admin_user_role_permission saurp
                            ON saurp.role_id = saur.role_id
                            LEFT JOIN system_admin_user_permission_menus saupm
                            ON saupm.permission_id = saurp.permission_id
                            WHERE saur.user_id = ?
                    ))
                  ORDER BY sort ASC
                """, arrayListOf(userId, userId), JsonObject::class.java, transaction
        )
    }
}