package com.hello.mobileservice.service.year;

import com.hello.common.dto.olis.Year;
import com.hello.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/30  20:54
 */

@FeignClient(value = "olis-server",path = "/year", fallbackFactory = YearServerHandler.class)
public interface YearService {
     @RequestMapping(value = "/findAllById",method = RequestMethod.GET)
     Result<List<Year>> findAllById(@RequestBody List<Long> yearIdList) ;
}
