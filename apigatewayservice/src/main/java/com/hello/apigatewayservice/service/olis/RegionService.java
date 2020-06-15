package com.hello.apigatewayservice.service.olis;

import com.hello.apigatewayservice.repository.olis.RegionPrizeManageRepository;
import com.hello.apigatewayservice.repository.olis.RegionRepository;
import com.hello.apigatewayservice.util.PasswordUtils;
import com.hello.common.dto.olis.Region;
import com.hello.common.dto.olis.RegionPrizeManage;
import com.hello.common.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/28  16:50
 */
@Service
@Transactional
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private RegionPrizeManageRepository regionPrizeManageRepository;

    /**
     * 保存/更新
     * @param list
     * @return
     */
    public Region save(Region region){
        User currentUser= PasswordUtils.currentUser();
        region.setSystemType(currentUser.getSystemType());
        int c = regionRepository.findByRegionNameAndSystemType(region.getRegionName(),currentUser.getSystemType());
        Region region1=null;
        String key="region_"+currentUser.getSystemType();
        try {
            if (redisTemplate.hasKey(key)){
               // regionList= (List<Region>) redisTemplate.opsForValue().get(key);
            }else {
               // regionList =  regionRepository.findAllBySystemType(systemType);
                //redisTemplate.opsForValue().set(key,regionList,1, TimeUnit.DAYS);
            }
        }catch (Exception e){
           // regionList =  regionRepository.findAllBySystemType(systemType);
        }
        if (c==0) {
             region1 = regionRepository.save(region);

        }
        return region1;
    }
    /**
     * 根据id查找
     * @param id
     * @return
     */
    public Region get(Long id){
        return regionRepository.findById(id).get();
    }

    /**
     * 根据id删除
     * @param id
     */
    public String del(Long id){
        regionRepository.deleteById(id);
        return "true";
    }

    /**
     * 返回所有
     * @return
     */
    public List<Region> findAll(){
        User currentUser= PasswordUtils.currentUser();
        List<Region> list = regionRepository.findAllBySystemType(currentUser.getSystemType());
        return list;
    }

    /**
     * @param pageRequest
     * @param name
     * @return
     */
    public Page<Region> search(final PageRequest pageRequest, final String name, final String vCode) {
        User currentUser= PasswordUtils.currentUser();
        Specification<Region> specification = new Specification<Region>() {
            @Override
            public Predicate toPredicate(Root<Region> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(name)) {
                    Predicate p1 = cb.like(root.get("regionName").as(String.class), "%" + name.trim() + "%");
                    list.add(p1);
                }
                Predicate p2 = cb.equal(root.get("systemType").as(String.class), currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<Region> page = regionRepository.findAll(specification, pageRequest);
        return page;
    }

    public void saveRegionPrize(RegionPrizeManage regionPrizeManage) {
        User currentUser= PasswordUtils.currentUser();
        regionPrizeManage.setSystemType(currentUser.getSystemType());
        RegionPrizeManage regionPrizeManage1=regionPrizeManageRepository.findByRegionIdAndImgBaseId(regionPrizeManage.getRegion().getId(),regionPrizeManage.getImgBase().getId());
        if (regionPrizeManage1!=null){
            regionPrizeManage.setId(regionPrizeManage1.getId());
        }
        regionPrizeManageRepository.save(regionPrizeManage);
    }

    public Object findAllByRegionId(Long id) {
        return regionPrizeManageRepository.findAllByRegionId(id);
    }

    public void delRegionPrizeById(Long id) {
        regionPrizeManageRepository.deleteById(id);
    }

    public List<Region> findAllRegion() {
        User user= PasswordUtils.currentUser();
        List<Region> regionList =  regionRepository.findAllBySystemType(user.getSystemType());
        return regionList;
    }

    public List<Region> findAllBySystemType(String systemType) {
        List<Region> regionList =  regionRepository.findAllBySystemType(systemType);
        return regionList;
    }
}
