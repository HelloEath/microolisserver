package com.hello.adminserver.repository;

import com.hello.common.entity.system.LoggingEventException;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by lxw on 2018/03/26.
 */
public interface LoggingEventExceptionRepository extends CrudRepository<LoggingEventException,Long>, JpaSpecificationExecutor<LoggingEventException> {
	List<LoggingEventException> findAllByEventId(Long eventId);


}
