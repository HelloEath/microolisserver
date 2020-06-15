package com.hello.adminserver.repository;

import com.hello.common.entity.system.SystemParams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author lxw
 *
 */
public interface SystemParamsRepository extends JpaRepository<SystemParams, Long> {

	List<SystemParams> findAll();
}
