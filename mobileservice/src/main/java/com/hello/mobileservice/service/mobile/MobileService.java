package com.hello.mobileservice.service.mobile;

import com.hello.common.dto.olis.*;

import com.hello.common.util.Result;
import com.hello.mobileservice.service.brand.BrandService;
import com.hello.mobileservice.service.device.DeviceService;
import com.hello.mobileservice.service.region.RegionService;
import com.hello.mobileservice.service.saesc.SaeDescService;
import com.hello.mobileservice.service.threeLevelWithYear.ThreeLevelWithYearService;
import com.hello.mobileservice.service.year.YearService;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/21  10:17
 */
@Service
public class MobileService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    YearService yearService;
    @Autowired
    ThreeLevelWithYearService threeLevelWithYearService;
    @Autowired
    SaeDescService saeDescService;
    @Autowired
    RegionService regionService;

    @Autowired
    private BrandService brandService;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RedisTemplate redisTemplate;

    public  List<Region> findAllRegion(HttpServletRequest httpServletRequest) {
        String systemType=httpServletRequest.getHeader("systemType");
        List<Region> regionList=null;
        String key="region_"+systemType;
        try {
            if (redisTemplate.hasKey(key)){
                regionList= (List<Region>) redisTemplate.opsForValue().get(key);
            }else {
                regionList =  regionService.findAllBySystemType(systemType).getData();
               // redisTemplate.opsForValue().set(key,regionList,1, TimeUnit.DAYS);
            }
        }catch (Exception e){
            regionList =  regionService.findAllBySystemType(systemType).getData();
        }
        return regionList;
    }

    public Map<String,List<Brand>> brands(String systemType) {
        String key="brand_"+systemType;
        List<Brand> brandList=null;
        if (redisTemplate.hasKey(key)){
            brandList= (List<Brand>) redisTemplate.opsForValue().get(key);
        }else {
            brandList=  brandService.findAllBrandBySystemType(systemType).getData();
            //redisTemplate.opsForValue().set(key,brandList,1,TimeUnit.DAYS);
        }
        Map<String,List<Brand>> map=new HashMap<>();
        brandList.forEach(x->{
            if (map.get(x.getBrandFistName())!=null){
                List<Brand> brandList1=map.get(x.getBrandFistName());
                brandList1.add(x);
            }else {
                List<Brand> brandList1=new ArrayList<>();
                brandList1.add(x);
                map.put(x.getBrandFistName(),brandList1);
            }

        });
        return map;
    }

    public Object findCarTypes(Long id) {
        HibernateEntityManager hEntityManager = (HibernateEntityManager)entityManager;
        Session session = hEntityManager.getSession();
        List<OneLevelCarType> oneLevelCarTypeList= brandService.searchOneCarAllByBrandId(id).getData();
        oneLevelCarTypeList.forEach(x->{
            session.evict(x);//把对象从session中清除，避免更改时自动更新到数据库
            List<TwoLevelCarType> twoLevelCarTypeList= brandService.searchTwoCarAllByOneId(x.getId()).getData();
           x.setTwoLevelCarTypeList(twoLevelCarTypeList);
           x.setBrand(null);
           twoLevelCarTypeList.forEach(xx->{
               session.evict(xx);
               xx.setOne(null);
               List<ThreeLevelCarType> threeLevelCarTypeList=brandService.searchThreeCarAllByTwoId(xx.getId()).getData();
               xx.setThreeLevelCarTypeList(threeLevelCarTypeList);
               threeLevelCarTypeList.forEach(xxx->{
                   session.evict(xxx);
                   xxx.setTwo(null);
               });
           });
        });
        return oneLevelCarTypeList;
    }

    public List<Year> yearList(Long id) {
       List<ThreeLevelWithYear> threeLevelWithYear= threeLevelWithYearService.findByThreeId(id).getData();
       List<Long> yearIdList=new ArrayList<>();
       threeLevelWithYear.forEach(x->{
           yearIdList.add(x.getYear().getId());
       });
        return yearService.findAllById(yearIdList).getData();
    }

    public List<ThreeLevelWithYear> engineTypeList(Long id,Long threeId) {
       return threeLevelWithYearService.findAllByYearIdAndThreeId(id,threeId).getData();
    }

    public List<SaeDesc>  saeList(Long threeId, Long engineTypeId, Long yearId) {
        List<SaeDesc> saeDescList=saeDescService.findAllByEngineTypeIdAndThreeId(engineTypeId,threeId).getData();
        return saeDescList;
    }

    public Object deviceValidata( HttpServletRequest httpServletRequest) {
        String deviceId=httpServletRequest.getHeader("deviceId");
        String systemType=httpServletRequest.getHeader("systemType");
        String token=httpServletRequest.getHeader("token");
        return deviceService.isValiDataDevice(deviceId,systemType,token);
    }

    public Result<Device> saveDevice(Device device) {
        return deviceService.save(device);

    }
}
