package com.hello.mobileservice.service.brand;


import com.hello.common.dto.olis.Brand;
import com.hello.common.dto.olis.OneLevelCarType;
import com.hello.common.dto.olis.ThreeLevelCarType;
import com.hello.common.dto.olis.TwoLevelCarType;
import com.hello.common.util.Result;
import com.hello.mobileservice.service.year.YearServerHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/31  14:53
 */
@FeignClient(value = "brand-server",path = "/brand", fallbackFactory = BrandServerHandler.class)
public interface BrandService {

    @RequestMapping(value = "/findAllBrandBySystemType",method = RequestMethod.GET)
    Result<List<Brand>> findAllBrandBySystemType(@RequestParam(value = "systemType") String systemType);

    @RequestMapping(value = "/searchOneCarAllByBrandId/{id}",method = RequestMethod.GET)
    Result<List<OneLevelCarType>> searchOneCarAllByBrandId(@PathVariable("id") Long id);

    @RequestMapping(value = "/searchTwoCarAllByOneId/{id}",method = RequestMethod.GET)
    Result<List<TwoLevelCarType>> searchTwoCarAllByOneId(@PathVariable("id") Long id);

    @RequestMapping(value = "/searchThreeCarAllByTwoId/{id}",method = RequestMethod.GET)
    Result<List<ThreeLevelCarType>> searchThreeCarAllByTwoId(@PathVariable("id") Long id);
}
