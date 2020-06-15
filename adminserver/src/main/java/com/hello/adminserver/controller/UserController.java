package com.hello.adminserver.controller;

import com.hello.common.entity.system.Permission;
import com.hello.common.entity.system.User;
import com.hello.common.util.Result;
import io.swagger.annotations.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Hzh on 2018/6/1.
 */
@Api(value = "用户接口",description = "UserController")
@RequestMapping(path = "/user")
public interface UserController {

	@ApiOperation(value="获取用户集合", notes="根据查询条件来获取用户集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "username", value = "登录名称",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "departmentId", value = "所属部门",  dataType = "Long",paramType = "query"),
            @ApiImplicitParam(name = "rolecode", value = "角色类型",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "status", value = "账号状态", dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "userType", value = "账号类型", dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "分页码", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(path = "/getusers",method = RequestMethod.GET)
	Result<List> getUsers(String name, String username, Long departmentId, Long roleId, Integer status, Integer userType, Integer pageNo);

	@ApiOperation(value="获取用户集合", notes="根据岗位code来获取用户集合")
    @ApiImplicitParams({
    		@ApiImplicitParam(name = "departmentId", value = "所属部门",  dataType = "Long",paramType = "query"),
            @ApiImplicitParam(name = "rolecode", value = "角色类型",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "status", value = "账号状态", dataType = "int",paramType = "query"),
    })
	@RequestMapping(path = "/getusers-by-rolecode",method = RequestMethod.GET)
	Result<List<User>> getUsersByRolecode(String departmentId, String rolecode, Integer status);


    @ApiOperation(value="获取用户", notes="根据User的id来获取用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/user/{id}",method = RequestMethod.GET)
    Result<User> get(@PathVariable Long id);

    @ApiOperation(value="保存/更新用户", notes="保存/更新用户")
    @RequestMapping(path = "/user",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<User> save(@RequestBody @ApiParam(name = "用户User", value = "传入json格式", required = true) User user);

    @ApiOperation(value="删除用户", notes="根据用户的id来删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/user/{id}",method = RequestMethod.DELETE)
    Result del(@PathVariable Long id);

    @ApiOperation(value="获取当前登录用户User", notes="获取当前登录用户User")
    @RequestMapping(path = "/current-user",method = RequestMethod.GET)
    Result<User> currentUser();

    @ApiOperation(value="禁用账户", notes="禁用账户")
    @ApiImplicitParam(name = "id", value = "id", required = true,dataType = "Long", paramType = "body")
    @RequestMapping(path = "/user-lock",method = RequestMethod.POST)
    Result lock(@RequestBody Long id);

    @ApiOperation(value="重置密码", notes="重置密码")
    @ApiImplicitParam(name = "id", value = "id", required = true,dataType = "Long", paramType = "body")
    @RequestMapping(path = "/password-reset",method = RequestMethod.POST)
    Result resetpassword(@RequestBody Long id);

    @ApiOperation(value="获取用户(权限)", notes="获取用户(权限)")
    @RequestMapping(path = "/menus",method = RequestMethod.GET)
    Result<Permission> menus();

    @RequestMapping(value = "/loadUserByUsername",method = RequestMethod.GET)
    Result<User> loadUserByUsername(@RequestParam String s);

}
