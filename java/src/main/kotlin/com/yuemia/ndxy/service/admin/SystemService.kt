package com.yuemia.ndxy.service.admin

import com.google.inject.Inject
import com.yuemia.ndxy.config.Table
import com.yuemia.ndxy.dao.*
import com.yuemia.ndxy.data.*
import com.yuemia.ndxy.exception.ExceptionMessageEnum
import com.yuemia.ndxy.exception.ServiceException
import io.vertx.core.json.JsonObject
import org.apache.commons.codec.digest.DigestUtils
import java.util.*

class SystemService @Inject constructor(
    private val adminUserMenusDAO: AdminUserMenusDAO,
    private val adminUserDAO: AdminUserDAO,
    private val adminUserRoleDAO: AdminUserRoleDAO,
    private val adminUserGroupDAO: AdminUserGroupDAO,
    private val adminPermissionDAO: AdminPermissionDAO
) {
    suspend fun getMenusList(userId: String): List<JsonObject> {
        return adminUserMenusDAO.getMenusList(userId)
    }

    suspend fun login(account: String, password: String): AdminUserDO? {
        val adminUserDO = adminUserDAO.getByAccount(account)
        adminUserDO?.let {
            if (it.password != DigestUtils.md5Hex(password)) {
                throw ServiceException(ExceptionMessageEnum.ACCOUNT_OR_PASSWORD_ERROR)
            }
            adminUserDO.token = UUID.randomUUID().toString().replace("-", "")
            adminUserDAO.updateToken(adminUserDO.id, adminUserDO.token)
            return adminUserDO
        }
        throw ServiceException(ExceptionMessageEnum.ACCOUNT_OR_PASSWORD_ERROR)
    }

    suspend fun updatePassword(id: String, password: String): Boolean {
        return adminUserDAO.updatePassword(id, password)
    }

    suspend fun getUserList(table: Table): Table {
        return Table(
            table.currentIndex, adminUserDAO.getUserListCount(table.data),
            adminUserDAO.getUserList(table, table.data)
        )
    }

    suspend fun getRoleList(): List<UserRoleDO> {
        return adminUserRoleDAO.getRoleList()
    }

    suspend fun getGroupList(): List<AdminPermissionDO> {
        return adminUserGroupDAO.getGroupList()
    }

    suspend fun saveGroup(userGroup: UserGroupDO): Boolean {
        return adminUserGroupDAO.insert(userGroup)
    }

    suspend fun saveRole(userRole: UserRoleDO): Boolean {
        return adminUserRoleDAO.insert(userRole)
    }

    suspend fun removeRole(id: String): Boolean {
        return adminUserRoleDAO.removeRole(id)
    }

    suspend fun saveUser(user: AdminUserDO): Boolean {
        return adminUserDAO.insert(user)
    }

    suspend fun setUserStatus(id: String, status: Int): Boolean {
        return adminUserDAO.updateStatusById(id, status)
    }

    suspend fun removeGroup(id: String): Boolean {
        return adminUserGroupDAO.removeGroup(id)
    }

    suspend fun getPermissionList(): List<AdminPermissionDO> {
        return adminPermissionDAO.getPermissionList()
    }

    suspend fun getRolePermissionList(id: String): List<UserPermissionDO> {
        return adminUserRoleDAO.getRolePermissionList(id)
    }

    suspend fun removeUser(id: String): Boolean {
        return adminUserDAO.remove(id)
    }
}