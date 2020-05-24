package com.hello.mobileservice.service.device;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hello.common.dto.olis.Device;
import com.hello.common.util.DateUtil;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import com.hello.common.util.enumeration.DevicePerssionCode;
import com.hello.common.util.enumeration.ReturnCode;
import com.hello.mobileservice.service.year.YearServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/30  10:18
 */
@FeignClient(value = "olis-server",path = "/device", fallbackFactory = DeviceServiceHandler.class)
public interface DeviceService {

    @RequestMapping(value = "/device",method = RequestMethod.GET)
    Result<Device> save(@RequestBody Device device);


    @RequestMapping(value = "/isValiDataDevice",method = RequestMethod.GET)
     Object isValiDataDevice(@RequestParam("deviceId") String deviceId,@RequestParam("systemType") String systemType,@RequestParam("token") String token);

}
