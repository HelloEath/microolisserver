package com.hello.adminservice.service;

import com.hello.adminservice.repository.RoleRepository;
import com.hello.adminservice.repository.UserRepository;
import com.hello.common.Exception.ApplicationException;
import com.hello.common.util.PasswordUtils;
import com.hello.common.entity.system.Role;
import com.hello.common.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author DELL
 *
 */
@Service
@Transactional
public class RoleService {
   
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
    UserRepository userRepository;
	/**
	 * 保存/更新角色
	 * @param role
	 * @return
	 */
	public Role save(Role role){
		User currentUser= PasswordUtils.currentUser();
		role.setSystemType(currentUser.getSystemType());
		return roleRepository.save(role);
	}
	/**
	 * 根据id查找角色
	 * @param id
	 * @return
	 */
	public Role findRoleById(Long id){
		return roleRepository.findById(id).get();
	}
	/**
	 * 根据角色id删除角色
	 * @param id
	 */
	public void delRoleById(Long id){
		List<User> list = userRepository.findAllByRoleId(id);
		if (list!=null&&list.size()>0){
			throw new ApplicationException("该角色下有用户,无法删除");
		}
		roleRepository.deleteById(id);
	}
	/**
	 * 返回所有角色列表
	 * @return
	 */
	public List<Role> findAllRole(){
		User currentUser= PasswordUtils.currentUser();
		List<Role> roleList = roleRepository.findAllBySystemType(currentUser.getSystemType());
		return roleList;
	}
	
}
