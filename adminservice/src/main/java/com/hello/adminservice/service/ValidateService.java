package com.hello.adminservice.service;


import com.hello.adminservice.repository.UserRepository;
import com.hello.adminservice.repository.brand.BrandRepository;
import com.hello.adminservice.repository.engine.EngineTypeRepository;
import com.hello.adminservice.repository.olis.RegionRepository;
import com.hello.adminservice.repository.year.YearRepository;
import com.hello.adminservice.util.PasswordUtils;
import com.hello.common.dto.olis.Brand;
import com.hello.common.dto.olis.EngineType;
import com.hello.common.dto.olis.Region;
import com.hello.common.dto.olis.Year;
import com.hello.common.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hzh on 2018/6/1.
 */
@Service
@Transactional
public class ValidateService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RegionRepository regionRepository;

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    EngineTypeRepository engineTypeRepository;

    @Autowired
    YearRepository yearRepository;

    /**
     * 验证用户名是否唯一
     * @param value
     * @param oldId
     * @return
     */
    public Boolean isUserNameUnique(String value, Long oldId) {
        User currentUser= PasswordUtils.currentUser();
        List<User> list = userRepository.findAllByUsernameAndSystemType(value,currentUser.getSystemType());
        for (User user : list) {
            if (user.getId().equals(oldId)) return true;
        }
        return list.size() == 0;
    }

    /**
     * 验证地区名是否唯一
     * @param value
     * @param oldId
     * @return
     */
    public Boolean isRegionUnique(String regionName, Long oldId) {
        User currentUser= PasswordUtils.currentUser();
        List<Region> list = regionRepository.findAllByRegionNameAndSystemType(regionName,currentUser.getSystemType());
        for (Region region : list) {
            if (region.getId().equals(oldId)) return true;
        }
        return list.size() == 0;
    }

    public Boolean isBrandUnique(String brandName, Long oldId) {
        User currentUser= PasswordUtils.currentUser();
        List<Brand> list = brandRepository.findAllByBrandNameAndSystemType(brandName,currentUser.getSystemType());
        for (Brand brand : list) {
            if (brand.getId().equals(oldId)) return true;
        }
        return list.size() == 0;
    }

    public Object isEngineTypeUnique(String engineTypeName, Long yearId) {
        User currentUser= PasswordUtils.currentUser();
        List<EngineType> list = engineTypeRepository.findAllByEngineTypeNameAndSystemTypeAndYearId(engineTypeName,currentUser.getSystemType(),yearId);
        return list.size() == 0;
    }

    public Object isYearUnique(String keyYearWords) {
        User currentUser= PasswordUtils.currentUser();
        Year year = yearRepository.findBySystemTypeAndKeyYearWords(currentUser.getSystemType(),keyYearWords);
        return year==null;
    }
}
