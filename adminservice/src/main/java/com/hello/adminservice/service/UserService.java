package com.hello.adminservice.service;

import com.hello.adminservice.Exception.ApplicationException;
import com.hello.adminservice.dto.Menu;
import com.hello.adminservice.repository.PermissionRepository;
import com.hello.adminservice.repository.UserRepository;
import com.hello.adminservice.util.PasswordUtils;
import com.hello.adminservice.util.enumeration.Commons;
import com.hello.common.entity.system.Permission;
import com.hello.common.entity.system.Role;
import com.hello.common.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Hzh on 2018/6/1.
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PermissionRepository permissionRepository;


    /**
     * 保存/更新用户
     *
     * @param user
     * @return
     */
    public User save(User user) {
        User currentUser=PasswordUtils.currentUser();
        user.setPassword(PasswordUtils.encoder(user.getPassword()));
        user.setSystemType(currentUser.getSystemType());
        return userRepository.save(user);
    }

    /**
     * 根据用户id查找用户
     *
     * @param id
     * @return
     */
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * 根据用户id删除用户
     *
     * @param id
     */
    public void delUserById(Long id) {
        User user = userRepository.findById(id).get();
        if (user.getOp() == Commons.YES.getIntegerValue()) throw new ApplicationException("无法删除超级管理员");
//    	user.setDel(Commons.YES.getIntegerValue());
//    	userRepository.save(user);
        userRepository.deleteById(id);
    }

    /**
     * 根据条件查找用户
     *
     * @param name
     * @param username
     * @param departmentId
     * @param rolecode
     * @param status
     * @return
     */
    public Page<User> findUserByCondition(final PageRequest pageRequest, final String name, final String username, final Long departmentId, final Long rolecode, final Integer status, final Integer userType) {
        User user=PasswordUtils.currentUser();
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();



                if (!StringUtils.isEmpty(name)) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    list.add(p1);
                }
                if (!StringUtils.isEmpty(username)) {
                    Predicate p2 = cb.like(root.get("username").as(String.class), "%" + username + "%");
                    list.add(p2);
                }
                if (null != departmentId) {
                    Predicate p3 = cb.equal(root.join("department").get("id").as(Long.class), departmentId);
                    list.add(p3);
                }
                if (null != rolecode) {
                    Predicate p4 = cb.equal(root.join("role").get("id").as(Long.class), rolecode);
                    list.add(p4);
                }
                if (null != status) {
                    Predicate p5 = cb.equal(root.get("status").as(Integer.class), status);
                    list.add(p5);
                }
                if (null != userType) {
                    Predicate p6 = cb.equal(root.get("userType").as(Integer.class), userType);
                    list.add(p6);
                }

                Predicate p7 = cb.equal(root.get("systemType").as(String.class), user.getSystemType());
                list.add(p7);
//				Predicate p5 = cb.equal(root.get("del").as(Integer.class), Commons.NO.getIntegerValue());
//				list.add(p5);

//              构建组合的Predicate：
//                Predicate p = cb.and(p3, cb.or(p1, p2));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<User> page = userRepository.findAll(specification, pageRequest);

        return page;
    }
    
   public List<User> getUsersByRolecode(final String departmentId,final String rolecode,final Integer status){
       User user=PasswordUtils.currentUser();
       Specification<User> specification = new Specification<User>() {

		@Override
		public Predicate toPredicate(Root<User> root,
                                     CriteriaQuery<?> cq,
                                     CriteriaBuilder cb) {
			List<Predicate> list = new ArrayList<>();
			 if (null != rolecode) {
                 Predicate p1 = cb.equal(root.join("role").get("code").as(String.class), rolecode);
                 list.add(p1);
             }
			 if (null != status) {
                 Predicate p2 = cb.equal(root.get("status").as(Integer.class), status);
                 list.add(p2);
             }
			 if (null != departmentId) {
                 Predicate p3 = cb.equal(root.join("department").get("id").as(Long.class), departmentId);
                 list.add(p3);
             }

            Predicate p4 = cb.equal(root.get("systemType").as(String.class), user.getSystemType());
            list.add(p4);
			 Predicate[] p = new Predicate[list.size()];
             return cb.and(list.toArray(p));
		}
    	   
       };
       List<User> list= userRepository.findAll(specification);
	   return list;
   }
    
    /**
     * 获取当前登录用户
     *
     * @return
     */
    public User currentUser() {
        return PasswordUtils.currentUser();
    }



    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    public User passwordReset(Long id) {
        User u = userRepository.findById(id).get();
       // u.setPassword(PasswordUtils.encoder("888888"));
        u.setPassword("888888");
        userRepository.save(u);
        return u;
    }

    /**
     * 锁定用户
     *
     * @param id
     * @return
     */
    public User userLock(Long id) {
        User u = userRepository.findById(id).get();
        if (u.getOp() == Commons.YES.getIntegerValue()) throw new ApplicationException("无法锁定超级管理员");
        u.setStatus(Commons.DISABLED.getIntegerValue());
        userRepository.save(u);
        return u;
    }

    /**
     * 获取当前用户菜单
     *
     * @return
     */
    public List<Menu> menus() {
        User user = PasswordUtils.currentUser();
        Role role = user.getRole();
        List<Menu> list = new ArrayList<>();
        List<Permission> already = new ArrayList<>();
        for (Permission p : role.getPermissions()) {
            if (p.getParentId() == null&&!already.contains(p)) {
                Menu menu = new Menu(p);
                list.add(menu);
                already.add(p);
            }
        }
        for (Permission p : role.getPermissions()) {
            if (p.getParentId() != null) {
                for (Menu m :list) {
                    if(m.getId().equals(p.getParentId())){
                        m.getChildren().add(new Menu(p));
                    }
                }
            }
        }

        for (Permission p : role.getPermissions()) {
            if (p.getParentId() != null) {
                for (Permission p1 : p.getChildren()) {

                    if (p1.getParentId() != null) {

                        for (Menu m : list) {

                            for (Menu m1 : m.getChildren()) {
                                if (m1.getId().equals(p1.getParentId())) {
                                    m1.getChildren().add(new Menu(p1));
                                }
                            }

                        }

                    }
                }


            }
        }

        Collections.sort(list, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                //Long i = o1.getId() - o2.getId();
//                if(i == 0){
//                    return o1.getAge() - o2.getAge();
//                }
            	return o1.getSorting() - o2.getSorting();
                //return i.intValue();
            }
        });
        
        for (Menu menu : list) {
        	if (menu.getChildren() != null && menu.getChildren().size() > 0) {
        		Collections.sort(menu.getChildren(), new Comparator<Menu>() {
                    @Override
                    public int compare(Menu o1, Menu o2) {
                    	return o1.getSorting() - o2.getSorting();
                    }
                });
        	}
        }

        return list;
    }


}
