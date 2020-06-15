package com.hello.adminserver.controller;

import com.hello.common.entity.system.Role;
import com.hello.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 *
 * @author DELL
 *
 */
@Api(value = "角色接口",description = "RoleController")
@RequestMapping(path = "/role")
public interface RoleController {

	@ApiOperation(value="获取所有角色", notes="返回所有角色列表")
    @RequestMapping(path = "/getroles",method = RequestMethod.GET)
    Result<List> getAllRole();

    @ApiOperation(value="获取角色", notes="根据Role的id来获取角色")
    @ApiImplicitParam(name = "id", value = "权限id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/role/{id}",method = RequestMethod.GET)
    Result<Role> get(@PathVariable Long id);

    @ApiOperation(value="保存/更新角色", notes="保存/更新角色")
    @RequestMapping(path = "/role",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Role> save(@RequestBody @ApiParam(name = "角色Role", value = "传入json格式", required = true) Role role);

    @ApiOperation(value="删除角色", notes="根据角色的id来删除角色")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/role/{id}",method = RequestMethod.DELETE)
    Result del(@PathVariable Long id);

}
