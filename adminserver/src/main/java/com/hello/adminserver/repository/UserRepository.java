package com.hello.adminserver.repository;

import com.hello.common.entity.system.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Hzh on 2018/6/1.
 */
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor {
    List<User> findAllByRoleId(Long id);
    List<User> findAll();
    User findByUsername(String name);
    List<User> findAllByUsername(String name);

    User findByUsernameAndSystemType(String s, String s1);

    List<User> findAllByUsernameAndSystemType(String value, String systemType);

    List<User> findBySystemType(String systemType);
}
