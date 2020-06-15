package com.hello.userservice.controller;

import com.hello.common.controller.common.BaseController;
import com.hello.common.entity.system.Permission;
import com.hello.common.entity.system.User;
import com.hello.common.util.PageRequestUtil;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import com.hello.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by hzh on 2018/6/22.
 */
@RestController
public class UserControllerImpl extends BaseController implements UserController {

	@Autowired
	private UserService userService;

    @Override
    public Result<Boolean> uniqueUserName(String userName, Long oldId) {
        return ResultUtil.success(userService.isUserNameUnique(userName,oldId));
    }

    @Override
    public Result<User> get(@PathVariable Long id) {
    	User user = userService.findUserById(id);
        return ResultUtil.success(user);
    }

    @Override
    public Result<User> save(@RequestBody User user) {
    	userService.save(user);
        return ResultUtil.success();
    }

    @Override
    public Result del(@PathVariable Long id) {
    	userService.delUserById(id);
        return ResultUtil.success();
    }

    @Override
    public Result<User> currentUser() {
        return ResultUtil.success(userService.currentUser());
    }

	@Override
	public Result<List> getUsers(String name, String username, Long departmentId,Long roleId, Integer status, Integer userType, Integer pageNo) {
		Page<User> userList = userService.findUserByCondition(PageRequestUtil.build(pageNo), name, username, departmentId, roleId, status, userType);
		return ResultUtil.success(userList);
	}

    @Override
    public Result lock(@RequestBody Long id) {
        userService.userLock(id);
        return ResultUtil.success();
    }

    @Override
    public Result resetpassword(@RequestBody Long id) {
        userService.passwordReset(id);
        return ResultUtil.success();
    }

    /**
     * 获取用户权限
     * @return
     */
    @Override
    public Result<Permission> menus() {
        return ResultUtil.success(userService.menus());
    }

    @Override
    public Result<User> loadUserByUsername(String s) {
        return ResultUtil.success(userService.loadUserByUsername(s));
    }

    @Override
    public String init(String systemType) {
        return userService.init(systemType);
    }


    /**
     * 根据岗位code来获取用户集合
     * @return
     */
	@Override
	public Result<List<User>> getUsersByRolecode(String departmentId, String rolecode, Integer status) {
		return ResultUtil.success(userService.getUsersByRolecode(departmentId,rolecode,status));
	}


}
