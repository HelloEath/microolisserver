package com.hello.adminservice.controller;

import com.hello.adminservice.service.PermissionService;
import com.hello.common.util.PageRequestUtil;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import com.hello.common.entity.system.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionControllerImpl implements PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@Override
	public Result<List> getAllPermission() {
		List<Permission> permissionList = permissionService.findAllPermission();
		return ResultUtil.success(permissionList);
	}

	@Override
	public Result<Permission> get(@PathVariable Long id) {
		Permission permission = permissionService.findPermissionById(id);
		return ResultUtil.success(permission);
	}

	@Override
	public Result<String> del(@PathVariable Long id) {
		return ResultUtil.success(permissionService.delPermissionById(id));
	}

	@Override
	public Result<Permission> save(@RequestBody Permission permission) {
		return ResultUtil.success(permissionService.save(permission));
	}

	/**
     * 查询权限菜单集合
     * @param name
     * @param pageNo
     * @return
     */
    @Override
    public Result<Page<Permission>> permissions(String name, Integer pageNo) {
        return ResultUtil.success(permissionService.searchPermission(PageRequestUtil.build(pageNo, 10, "asc", "sorting"),name));
    }
}
