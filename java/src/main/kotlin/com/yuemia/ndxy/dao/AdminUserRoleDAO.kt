package com.yuemia.ndxy.dao

import com.google.inject.Inject
import com.yuemia.ndxy.core.MySQLCore
import com.yuemia.ndxy.data.UserPermissionDO
import com.yuemia.ndxy.data.UserRoleDO
import io.vertx.ext.sql.SQLConnection

class AdminUserRoleDAO @Inject constructor(private val sqlCore: MySQLCore) {


    suspend fun getRoleList(transaction: SQLConnection? = null): List<UserRoleDO> {
        val sql = """
            SELECT * FROM system_admin_role
        """
        return sqlCore.query(
            sql,
            arrayListOf(),
            UserRoleDO::class.java,
            transaction
        )
    }

    suspend fun removeRole(id: String, transaction: SQLConnection? = null): Boolean {
        val sql = """
            DELETE FROM system_admin_role WHERE id = ?;
            DELETE FROM system_admin_user_role_permission WHERE role_id = ?;
        """
        return sqlCore.delete(
            sql,
            arrayListOf(id, id),
            transaction
        )
    }

    suspend fun insert(userRole: UserRoleDO, transaction: SQLConnection? = null): Boolean {
        val sql = """
            INSERT INTO `system_admin_role`( `name`) VALUES (?)
        """
        return sqlCore.insert(
            sql,
            arrayListOf(userRole.name),
            transaction
        )
    }

    suspend fun getRolePermissionList(id: String, transaction: SQLConnection? = null): List<UserPermissionDO> {
        val sql = """
            SELECT yap.* FROM system_admin_user_role_permission yaurp
            LEFT JOIN system_admin_permission yap ON yap.id = yaurp.permission_id
            WHERE yaurp.role_id = ?;
        """
        return sqlCore.query(
            sql,
            arrayListOf(id, id),
            UserPermissionDO::class.java,
            transaction
        )
    }
}