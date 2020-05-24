package com.hello.adminservice.service.device;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hello.adminservice.repository.device.DeviceRepository;
import com.hello.adminservice.util.DateUtil;
import com.hello.adminservice.util.PasswordUtils;
import com.hello.adminservice.util.Result;
import com.hello.adminservice.util.ResultUtil;
import com.hello.adminservice.util.enumeration.DevicePerssionCode;
import com.hello.adminservice.util.enumeration.ReturnCode;
import com.hello.common.dto.olis.Device;
import com.hello.common.entity.system.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
@Service
@Transactional
public class DeviceService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    HttpServletRequest httpServletRequest;

    public Result<Device> save(Device device) {
        User currentUser = PasswordUtils.currentUser();
        if (currentUser != null){
            device.setSystemType(currentUser.getSystemType());

        }else {
           Device device2=deviceRepository.findByDeviceCodeAndSystemType(device.getDeviceCode(),device.getSystemType());
            if (device2!=null){
                return ResultUtil.failed(DevicePerssionCode.EXIST.getValue(), DevicePerssionCode.EXIST.getName());
            }
        }
        try {
            device = deviceRepository.save(device);
        } catch (Exception e) {
            logger.error("保存设备信息失败");
            return ResultUtil.failed(ReturnCode.FAILED);
        }
        String token = JWT.create().withAudience(device.getDeviceCode()).sign(Algorithm.HMAC256(device.getDeviceCode() + device.getSystemType()));
        try {
            Calendar c = Calendar.getInstance();
            String endDate=c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH)+" 23:00";
            redisTemplate.opsForValue().set(token, device, DateUtil.getDiffMins(new Date(),new Date(endDate)), TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error("保存设备信息到redis失败");

        }
        return ResultUtil.success(token);
    }

    public Page<Device> search(String deviceCode, String deviceProxy, String deviceProxyNumber, Integer deviceRegionId, PageRequest pageRequest) {
        User currentUser = PasswordUtils.currentUser();
        Specification<Device> specification = new Specification<Device>() {
            @Override
            public Predicate toPredicate(Root<Device> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(deviceCode)) {
                    Predicate p1 = cb.like(root.get("deviceCode").as(String.class), "%" + deviceCode.trim() + "%");
                    list.add(p1);
                }
                if (!StringUtils.isEmpty(deviceProxy)) {
                    Predicate p2 = cb.like(root.get("deviceProxy").as(String.class), "%" + deviceProxy.trim() + "%");
                    list.add(p2);
                }
                if (!StringUtils.isEmpty(deviceProxyNumber)) {
                    Predicate p3 = cb.like(root.get("deviceProxyNumber").as(String.class), "%" + deviceProxyNumber.trim() + "%");
                    list.add(p3);
                }

                if (!StringUtils.isEmpty(deviceRegionId)) {
                    Predicate p4 = cb.equal(root.join("region").get("id").as(Long.class), deviceRegionId);
                    list.add(p4);
                }
                Predicate p5 = cb.equal(root.get("systemType").as(String.class), currentUser.getSystemType());
                list.add(p5);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<Device> page = deviceRepository.findAll(specification, pageRequest);
        return page;

    }

    public void delDevice(Long id) {
        String key=httpServletRequest.getHeader("token")==null? "null":httpServletRequest.getHeader("token");
        if ("null".equals(key)){
           Device device= deviceRepository.findById(id).get();
            key= JWT.create().withAudience(device.getDeviceCode()).sign(Algorithm.HMAC256(device.getDeviceCode() + device.getSystemType()));
        }
        deviceRepository.deleteById(id);
        if (redisTemplate.hasKey(key))
        redisTemplate.delete(key);

    }

    public Device getDevicePermission(String deviceCode) {
        User currentUser = PasswordUtils.currentUser();
        return deviceRepository.findByDeviceCodeAndSystemType(deviceCode, currentUser.getSystemType());
    }

    public Object isValiDataDevice(String deviceId,String systemType,String token) {
        Device device;
        if ("null".equals(deviceId) || "null".equals(systemType)) {
            return ResultUtil.error(ReturnCode.TIMEOUT.getValue(), "请求缺少必要参数,请重新注册");
        }
        //String token = TokenUtil.generateTokenKey(deviceId, systemType);
        if (!"null".equals(token)&&redisTemplate.hasKey(token)){
            device= (Device) redisTemplate.opsForValue().get(token);
        }else {
            device = deviceRepository.findByDeviceCodeAndSystemType(deviceId, systemType);
            if (device!=null){
                 token = JWT.create().withAudience(device.getDeviceCode()).sign(Algorithm.HMAC256(device.getDeviceCode() + device.getSystemType()));
                Calendar c = Calendar.getInstance();
                String endDate=c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH)+" 23:00";
                int sd=DateUtil.getDiffMins(new Date(),new Date(endDate));
                redisTemplate.opsForValue().set(token, device, sd, TimeUnit.MINUTES);
            }
        }
        if (device == null) {
            return ResultUtil.success(DevicePerssionCode.UNKOWN.getName(), DevicePerssionCode.UNKOWN.getValue());//设备不存在
        }

        if (device.getDevicePermission() == 0) {
            return ResultUtil.success(DevicePerssionCode.AUTHENTICATED.getName(), DevicePerssionCode.AUTHENTICATED.getValue(), token);//设备存在并已授权
        } else {
            return ResultUtil.success(DevicePerssionCode.UNAUTHENTICATIED.getName(), DevicePerssionCode.UNAUTHENTICATIED.getValue(),token);//设备未授权
        }
    }

    /*
        每天23:59分重置允许请求次数
    */
    @Scheduled(cron = "00 27 14 * * *")
    public void schedulUpdateDevice() {
        logger.info("device信息更新任务开始执行");
        int deviceSearchCount = 30;//每天查询次数
        deviceRepository.updateDeviceSearchCount(deviceSearchCount);
        logger.info("device信息更新任务结束");
    }



}
