package com.hello.adminserver.repository;

import com.hello.common.entity.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author DELL
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByCode(String code);

    List<Role> findAllBySystemType(String systemType);
}
