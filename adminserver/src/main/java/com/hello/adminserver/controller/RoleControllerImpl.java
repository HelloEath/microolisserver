package com.hello.adminserver.controller;

import com.hello.adminserver.service.RoleService;
import com.hello.common.entity.system.Role;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleControllerImpl implements RoleController {

	@Autowired
	private RoleService roleService;

	@Override
	public Result<List> getAllRole() {
		List<Role> roleList = roleService.findAllRole();
		return ResultUtil.success(roleList);
	}

	@Override
	public Result<Role> get(@PathVariable Long id) {
		Role role = roleService.findRoleById(id);
		return ResultUtil.success(role);
	}

	@Override
	public Result<Role> save(@RequestBody Role role) {
		roleService.save(role);
		return ResultUtil.success();
	}

	@Override
	public Result del(@PathVariable Long id) {
		roleService.delRoleById(id);
		return ResultUtil.success();
	}

}
