package com.hello.adminserver.controller;

import com.hello.common.entity.system.Permission;
import com.hello.common.util.Result;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
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
@Api(value = "权限接口",description = "PermissionController")
@RequestMapping(path = "/permission")
public interface PermissionController {

	@ApiOperation(value="获取所有权限", notes="返回所有权限列表")
    @RequestMapping(path = "/getpermissions",method = RequestMethod.GET)
    Result<List> getAllPermission();

    @ApiOperation(value="获取权限", notes="根据Permission的id来获取权限")
    @ApiImplicitParam(name = "id", value = "权限id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/Permission/{id}",method = RequestMethod.GET)
    Result<Permission> get(@PathVariable Long id);

    @ApiOperation(value="保存/更新权限", notes="保存/更新权限")
    @RequestMapping(path = "/Permission",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Permission> save(@RequestBody @ApiParam(name = "权限Permission", value = "传入json格式", required = true) Permission permission);

    @ApiOperation(value="删除权限", notes="根据权限的id来删除权限")
    @ApiImplicitParam(name = "id", value = "权限id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/Permission/{id}",method = RequestMethod.DELETE)
    Result<String> del(@PathVariable Long id);

    /**
     *分页获取权限菜单集合
     */
    @ApiOperation(value = "分页获取权限菜单集合(完成)", notes = "根据查询条件来分页获取权限菜单集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "权限名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(path = "/Permissions", method = RequestMethod.GET)
    Result<Page<Permission>> permissions(String name, Integer pageNo);
}
