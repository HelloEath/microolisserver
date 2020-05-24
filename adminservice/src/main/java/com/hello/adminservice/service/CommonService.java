package com.hello.adminservice.service;

import com.hello.adminservice.config.AppConfig;

import com.hello.adminservice.repository.LoggingEventExceptionRepository;
import com.hello.adminservice.repository.SystemLogRepository;
import com.hello.adminservice.repository.SystemParamsRepository;
import com.hello.adminservice.repository.olis.RegionRepository;
import com.hello.adminservice.repository.year.YearRepository;
import com.hello.adminservice.util.PasswordUtils;
import com.hello.common.dto.olis.Region;
import com.hello.common.dto.olis.Year;
import com.hello.common.entity.system.LoggingEventException;
import com.hello.common.entity.system.SystemLog;
import com.hello.common.entity.system.SystemParams;
import com.hello.common.entity.system.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommonService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RegionRepository regionRepository ;

	@Autowired
	private SystemLogRepository systemLogRepository;
	@Autowired
    LoggingEventExceptionRepository loggingEventExceptionRepository;
	@Autowired
	SystemParamsRepository systemParamsRepository;
	@Resource
    AppConfig appConfig;
	@Autowired
    YearRepository yearRepository;
	
	//@Autowired
    //TriggerRuleConfigRepository triggerRuleConfigRepository;
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	/**
	 * 查找所有地区
	 * @return
	 */
	public List<Region> findAllRegion(){
        User user= PasswordUtils.currentUser();
        List<Region> regionList =  regionRepository.findAllBySystemType(user.getSystemType());
		return regionList;
	}



	/**
     * 搜索系统日志
     *
     * @param pageNo
     * @param startDate
     * @param endDate
     * @param user
     * @param url
     * @param args
     * @return
     */
    public Page<SystemLog> searchSystemLog(PageRequest pageRequest, final Long startDate, final Long endDate, final String user, final String url, final String args, final String levelString) {
    	Specification<SystemLog> specification = new Specification<SystemLog>() {
            @Override
            public Predicate toPredicate(Root<SystemLog> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (null != startDate && null != endDate) {
                    Predicate p2 = cb.between(root.get("timestmp").as(Long.class), startDate, endDate);
                    list.add(p2);
                }
                if (!StringUtils.isEmpty(user)) {
                    Predicate p3 = cb.like(root.get("arg1").as(String.class), "%" + user + "%");
                    list.add(p3);
                }
                if (!StringUtils.isEmpty(url)) {
                    Predicate p4 = cb.like(root.get("arg2").as(String.class), "%" + url + "%");
                    list.add(p4);
                }
                if (!StringUtils.isEmpty(args)) {
                    Predicate p5 = cb.like(root.get("arg3").as(String.class), "%" + args + "%");
                    list.add(p5);
                }
                if (!StringUtils.isEmpty(levelString)) {
                    Predicate p6 = cb.equal(root.get("levelString").as(String.class), levelString);
                    list.add(p6);
                }
                //Predicate p6 = cb.
                //query.orderBy(o);
//              构建组合的Predicate：
//                Predicate p = cb.and(p3, cb.or(p1, p2));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<SystemLog> page = systemLogRepository.findAll(specification, pageRequest);
        return page;
    }
    
	/**
     *根据查询条件删除系统日志
     * @param startDate
     * @param endDate
     * @param user
     * @param url
     * @param args
     * @return
     */
    public String delSystemLog(final Long startDate,final Long endDate,final String user,final String url,final String args,final String levelString){
    	Specification<SystemLog> specification = new Specification<SystemLog>() {
            @Override
            public Predicate toPredicate(Root<SystemLog> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (null != startDate && null != endDate) {
                    Predicate p2 = cb.between(root.get("timestmp").as(Long.class), startDate, endDate);
                    list.add(p2);
                }
                if (!StringUtils.isEmpty(user)) {
                    Predicate p3 = cb.like(root.get("arg1").as(String.class), "%" + user + "%");
                    list.add(p3);
                }
                if (!StringUtils.isEmpty(url)) {
                    Predicate p4 = cb.like(root.get("arg2").as(String.class), "%" + url + "%");
                    list.add(p4);
                }
                if (!StringUtils.isEmpty(args)) {
                    Predicate p5 = cb.like(root.get("arg3").as(String.class), "%" + args + "%");
                    list.add(p5);
                }
                if (!StringUtils.isEmpty(levelString)) {
                    Predicate p6 = cb.equal(root.get("levelString").as(String.class), levelString);
                    list.add(p6);
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        if (startDate == null && endDate == null && StringUtils.isEmpty(user) && StringUtils.isEmpty(url) && StringUtils.isEmpty(args) && StringUtils.isEmpty(levelString)) {
        	//triggerRuleConfigRepository.truncateLoggingProperty();
        	//triggerRuleConfigRepository.truncateLoggingException();
        	//triggerRuleConfigRepository.truncateSysLogg();
        	return "false";
        } else {
			List<SystemLog> sysloglist = systemLogRepository.findAll(specification);
			System.out.println("logList====" + sysloglist.size());
			if (sysloglist == null || sysloglist.size() == 0) {
				return "true";
			}
			List<Long> eventidlist = new ArrayList<Long>();
			int listSize = sysloglist.size();
			int count = 0;
			for (int i = 0; i < listSize; i++) {
				eventidlist.add(sysloglist.get(i).getEventId());
				if (i % 1000 == 0) {
					//triggerRuleConfigRepository.delBatchLoggingPropertyByEventId(eventidlist);
					//triggerRuleConfigRepository.delBatchLoggingExceptionByEventId(eventidlist);
					//triggerRuleConfigRepository.delBatchSysLoggByEventId(eventidlist);
					count = count + eventidlist.size();
					eventidlist.clear();
				}
			}
			//triggerRuleConfigRepository.delBatchLoggingPropertyByEventId(eventidlist);
			//triggerRuleConfigRepository.delBatchLoggingExceptionByEventId(eventidlist);
			//triggerRuleConfigRepository.delBatchSysLoggByEventId(eventidlist);
			count = count + eventidlist.size();
			System.out.println("count====" + count);
        }
        return "true";
    }

    /**
     * 根据eventId获取日志异常信息
     * @param eventId
     * @return
     */
	public List<LoggingEventException> getLoggingEventException(Long eventId) {
		List<LoggingEventException> list = loggingEventExceptionRepository.findAllByEventId(eventId);
        return list;
	}
	
	/**
	 * 获取所有的系统参数
	 * @param
	 * @return
	 */
	public List<SystemParams> findSystemParams(){
		List<SystemParams> systemParamsList = systemParamsRepository.findAll();
		return systemParamsList;
	}
	
	/**
	 * 保存/更新系统参数
	 * @param systemParams
	 * @return
	 */
	public SystemParams saveSystemParam(SystemParams systemParams){
		return systemParamsRepository.save(systemParams);
	}


    public List<Year> findAllYear() {
        User user= PasswordUtils.currentUser();
        List<Year> yearList = (List<Year>) yearRepository.findAllBySystemType(user.getSystemType());
        return yearList;
    }
}
