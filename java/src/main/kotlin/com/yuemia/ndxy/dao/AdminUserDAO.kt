package com.yuemia.ndxy.dao

import com.google.inject.Inject
import com.yuemia.ndxy.config.Table
import com.yuemia.ndxy.core.MySQLCore
import com.yuemia.ndxy.data.AdminUserDO
import io.vertx.core.json.JsonObject
import io.vertx.ext.sql.SQLConnection

class AdminUserDAO @Inject constructor(private val sqlCore: MySQLCore) {
    suspend fun getByAccount(account: String, transaction: SQLConnection? = null): AdminUserDO? {
        return sqlCore.queryFirst(
            """
                    SELECT * FROM system_admin_user WHERE account = ?
                """, arrayListOf(account), AdminUserDO::class.java, transaction
        )
    }

    suspend fun updateToken(id: String, token: String, transaction: SQLConnection? = null): Boolean {
        return sqlCore.update(
            """
                    UPDATE system_admin_user SET token = ? WHERE id = ?
                """, arrayListOf(token, id), transaction
        )
    }

    suspend fun updatePassword(id: String, password: String, transaction: SQLConnection? = null): Boolean {
        return sqlCore.update(
            """
                    UPDATE system_admin_user SET password = ? WHERE id = ?
                """, arrayListOf(password, id), transaction
        )
    }

    suspend fun getUserListCount(args: JsonObject, transaction: SQLConnection? = null): Int {
        val parameter = ArrayList<Any>()
        var sql = """
                    SELECT COUNT(*) FROM system_admin_user yau WHERE  is_delete = 0
                """
        if ((args.getString("name") ?: "").isNotEmpty()) {
            sql += " AND LOCATE(?, yau.name) > 0"
            parameter.add(args.getString("name"))
        }
        if ((args.getString("phone") ?: "").isNotEmpty()) {
            sql += " AND LOCATE(?, yau.name) > 0"
            parameter.add(args.getString("phone"))
        }
        return sqlCore.queryResult(sql, parameter, Int::class.java, transaction) ?: 0
    }

    suspend fun getUserList(table: Table?, args: JsonObject, transaction: SQLConnection? = null): List<AdminUserDO> {
        val parameter = ArrayList<Any>()
        var sql = """
            SELECT yau.*, t.name AS "role", t.id AS "roleId", yaug.group_id AS "groupId" FROM system_admin_user yau
            LEFT JOIN (
                SELECT yaur.user_id, yar.`name`,yar.id FROM system_admin_user_role yaur
                LEFT JOIN system_admin_role yar ON yaur.role_id = yar.id
                GROUP BY yaur.user_id
            ) t ON t.user_id = yau.id
            LEFT JOIN system_admin_user_group yaug ON yaug.user_id = yau.id
            WHERE is_delete = 0
        """
        if ((args.getString("name") ?: "").isNotEmpty()) {
            sql += " AND LOCATE(#{args.name}, yau.name) > 0 "
        }
        if ((args.getString("phone") ?: "").isNotEmpty()) {
            sql += " AND LOCATE(#{args.phone}, yau.phone) > 0 "
        }
        sql += " ORDER BY create_time DESC "
        if (table != null) {
            sql += """
                LIMIT ?, ?
            """
            parameter.add(table.limit)
            parameter.add(table.pageSize)
        }
        return sqlCore.query(sql, parameter, AdminUserDO::class.java, transaction)
    }

    suspend fun updateStatusById(id: String, status: Int, transaction: SQLConnection? = null): Boolean {
        return sqlCore.update(
            """
                    UPDATE system_admin_user SET status = ? WHERE id = ?
                """, arrayListOf(status, id), transaction
        )
    }

    suspend fun insert(user: AdminUserDO, transaction: SQLConnection? = null): Boolean {
        return sqlCore.insert(
            """
                    INSERT INTO `system_admin_user`( `name`, `phone`,  `email`, `account`, `password`, `remark`,  `token` )
                    VALUES (?, ?, ?, ?, ?, ?, CONCAT('ADMIN_', UUID()));
                """,
            arrayListOf(user.name, user.phone, user.email, user.account, user.password, user.remark), transaction
        )
    }

    suspend fun remove(id: String, transaction: SQLConnection? = null): Boolean {
        return sqlCore.delete(
        """
                UPDATE system_admin_user SET is_delete = 1 WHERE id = ?
            """,
            arrayListOf(id),
            transaction
        )
    }


}