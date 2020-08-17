package com.yuemia.ndxy.module.admin

import com.google.inject.Inject
import com.yuemia.ndxy.config.ApiResponse
import com.yuemia.ndxy.config.Table
import com.yuemia.ndxy.config.USession
import com.yuemia.ndxy.data.AdminUserDO
import com.yuemia.ndxy.data.UserGroupDO
import com.yuemia.ndxy.data.UserRoleDO
import com.yuemia.ndxy.exception.ExceptionMessageEnum
import com.yuemia.ndxy.exception.ParameterException
import com.yuemia.ndxy.module.BaseModule
import com.yuemia.ndxy.service.admin.SystemService
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class SystemModule @Inject constructor(router: Router, private val systemService: SystemService) : BaseModule() {
    init {
        addRouter(router.post("/admin/system/login")).coroutineHandler(5000L, ::login)
        addRouter(router.post("/admin/system/getMenusList")).coroutineHandler(5000L, ::getMenusList)

        addRouter(router.post("/admin/system/updatePassword")).coroutineHandler(5000L, ::updatePassword)
        addRouter(router.post("/admin/system/getUserList")).coroutineHandler(5000L, ::getUserList)
        addRouter(router.post("/admin/system/getRoleList")).coroutineHandler(5000L, ::getRoleList)
        addRouter(router.post("/admin/system/getGroupList")).coroutineHandler(5000L, ::getGroupList)
        addRouter(router.post("/admin/system/saveGroup")).coroutineHandler(5000L, ::saveGroup)
        addRouter(router.post("/admin/system/saveRole")).coroutineHandler(5000L, ::saveRole)
        addRouter(router.post("/admin/system/removeRole")).coroutineHandler(5000L, ::removeRole)
        addRouter(router.post("/admin/system/saveUser")).coroutineHandler(5000L, ::saveUser)
        addRouter(router.post("/admin/system/setUserStatus")).coroutineHandler(5000L, ::setUserStatus)
        addRouter(router.post("/admin/system/removeUser")).coroutineHandler(5000L, ::removeUser)
        addRouter(router.post("/admin/system/removeGroup")).coroutineHandler(5000L, ::removeGroup)
        addRouter(router.post("/admin/system/getPermissionList")).coroutineHandler(5000L, ::getPermissionList)
        addRouter(router.post("/admin/system/getRolePermissionList")).coroutineHandler(5000L, ::getRolePermissionList)
    }

    private suspend fun login(content: RoutingContext) {
        val user = getBody(content, AdminUserDO::class.java)
        if (user.account.isEmpty() || user.password.isEmpty()) {
            throw ParameterException(ExceptionMessageEnum.PARAMETER_ERROR)
        }
        content.response().end(ApiResponse.success(systemService.login(user.account, user.password)).toString())
    }

    private suspend fun getMenusList(content: RoutingContext, session: USession) {
        content.response().end(ApiResponse.success(systemService.getMenusList(session.id)).toString())
    }

    private suspend fun updatePassword(content: RoutingContext, session: USession) {
        val user = getBody(content, AdminUserDO::class.java)
        content.response().end(ApiResponse.success(systemService.updatePassword(session.id, user.password)).toString())
    }

    private suspend fun getUserList(content: RoutingContext) {
        val table = getBody(content, Table::class.java)
        content.response().end(ApiResponse.success(systemService.getUserList(table)).toString())
    }

    private suspend fun getRoleList(content: RoutingContext) {
        content.response().end(ApiResponse.success(systemService.getRoleList()).toString())
    }

    private suspend fun getGroupList(content: RoutingContext) {
        content.response().end(ApiResponse.success(systemService.getGroupList()).toString())
    }

    private suspend fun saveGroup(content: RoutingContext) {
        val userGroup = getBody(content, UserGroupDO::class.java)
        content.response().end(ApiResponse.success(systemService.saveGroup(userGroup)).toString())
    }

    private suspend fun saveRole(content: RoutingContext) {
        val userRole = getBody(content, UserRoleDO::class.java)
        content.response().end(ApiResponse.success(systemService.saveRole(userRole)).toString())
    }

    private suspend fun removeRole(content: RoutingContext) {
        val userRole = getBody(content, UserRoleDO::class.java)
        content.response().end(ApiResponse.success(systemService.removeRole(userRole.id)).toString())
    }

    private suspend fun saveUser(content: RoutingContext) {
        val user = getBody(content, AdminUserDO::class.java)
        content.response().end(ApiResponse.success(systemService.saveUser(user)).toString())
    }

    private suspend fun setUserStatus(content: RoutingContext) {
        val user = getBody(content, AdminUserDO::class.java)
        content.response().end(ApiResponse.success(systemService.setUserStatus(user.id, user.status)).toString())
    }

    private suspend fun removeUser(content: RoutingContext) {
        val user = getBody(content, AdminUserDO::class.java)
        content.response().end(ApiResponse.success(systemService.removeUser(user.id)).toString())
    }

    private suspend fun removeGroup(content: RoutingContext) {
        val userGroup = getBody(content, UserGroupDO::class.java)
        content.response().end(ApiResponse.success(systemService.removeGroup(userGroup.id)).toString())
    }

    private suspend fun getPermissionList(content: RoutingContext) {
        content.response().end(ApiResponse.success(systemService.getPermissionList()).toString())
    }

    private suspend fun getRolePermissionList(content: RoutingContext) {
        val userRole = getBody(content, UserRoleDO::class.java)
        content.response().end(ApiResponse.success(systemService.getRolePermissionList(userRole.id)).toString())
    }

}