package com.hello.adminservice.repository;

import com.hello.common.entity.system.SystemLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lxw on 2018/03/20.
 */
public interface SystemLogRepository extends CrudRepository<SystemLog,Long>, JpaSpecificationExecutor<SystemLog> {

	//@Override
	//SystemLog findOne(Long id);
	
	//@Override
	//List<SystemLog> findAll();
}
