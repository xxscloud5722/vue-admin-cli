package com.yuemia.ndxy.dao

import com.google.inject.Inject
import com.yuemia.ndxy.core.MySQLCore
import com.yuemia.ndxy.data.AdminPermissionDO
import com.yuemia.ndxy.data.UserGroupDO
import io.vertx.ext.sql.SQLConnection

class AdminUserGroupDAO @Inject constructor(private val sqlCore: MySQLCore) {
    suspend fun getGroupList(transaction: SQLConnection? = null): List<AdminPermissionDO> {
        val sql = """ SELECT * FROM system_admin_user_group_info """
        return sqlCore.query(
            sql,
            arrayListOf(),
            AdminPermissionDO::class.java,
            transaction
        )
    }


    suspend fun insert(userGroup: UserGroupDO, transaction: SQLConnection? = null): Boolean {
        val sql = """
            INSERT INTO `system_admin_user_group_info`(`name`)
            VALUES (?);
        """
        return sqlCore.insert(
            sql,
            arrayListOf(userGroup.name),
            transaction
        )
    }

    suspend fun removeGroup(id: String, transaction: SQLConnection? = null): Boolean {
        val sql = """
            DELETE FROM system_admin_user_group_info WHERE id = ?;
            DELETE FROM system_admin_user_group WHERE group_id = ?;
        """
        return sqlCore.insert(
            sql,
            arrayListOf(id, id),
            transaction
        )
    }
}