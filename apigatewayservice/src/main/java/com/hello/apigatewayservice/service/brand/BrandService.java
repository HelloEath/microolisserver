package com.hello.apigatewayservice.service.brand;

import com.hello.common.dto.olis.Brand;
import com.hello.common.dto.olis.OneLevelCarType;
import com.hello.common.dto.olis.ThreeLevelCarType;
import com.hello.common.dto.olis.TwoLevelCarType;
import com.hello.apigatewayservice.repository.ThreeLevelWithYearRepository;
import com.hello.apigatewayservice.repository.brand.BrandRepository;
import com.hello.apigatewayservice.service.oneLevel.LevelCarTypeService;
import com.hello.apigatewayservice.util.PasswordUtils;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/31  14:53
 */
@Service
@Transactional
public class BrandService {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    LevelCarTypeService levelCarTypeService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ThreeLevelWithYearRepository threeLevelWithYearRepository;

    public void save(Brand brand) {
        User currentUser= PasswordUtils.currentUser();
        brand.setSystemType(currentUser.getSystemType());
        brandRepository.save(brand);
        String key="brand_"+currentUser.getSystemType();
        if (redisTemplate.hasKey(key)){
            List<Brand> brandList= (List<Brand>) redisTemplate.opsForValue().get(key);
            brandList.add(brand);
           brandList=brandList.stream().distinct().collect(Collectors.toList());
            redisTemplate.opsForValue().set(key,brandList,2, TimeUnit.MINUTES);
        }
    }

    public Object search(PageRequest pageRequest, String brandEngineType) {
        User currentUser= PasswordUtils.currentUser();
        Specification<Brand> specification = new Specification<Brand>() {
            @Override
            public Predicate toPredicate(Root<Brand> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(brandEngineType)) {
                    Predicate p1 = cb.like(root.get("brandName").as(String.class), "%" + brandEngineType.trim() + "%");
                    list.add(p1);
                }
                Predicate p2 = cb.equal(root.get("systemType").as(String.class), currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<Brand> page = brandRepository.findAll(specification, pageRequest);
        return page;
    }

    public void del(Long id) {
        List<OneLevelCarType> oneLevelCarTypeList=levelCarTypeService.findAllByBrandId(id);
        oneLevelCarTypeList.forEach(x->{
            List<TwoLevelCarType> twoLevelCarTypeList=levelCarTypeService.findAllByOneId(x.getId());
            twoLevelCarTypeList.forEach(xx->{
                List<ThreeLevelCarType>threeLevelCarTypeList=levelCarTypeService.findAllByTwoId(xx.getId());
                levelCarTypeService.deleteThreeAll(threeLevelCarTypeList);
            });
            levelCarTypeService.deleteTwoAll(twoLevelCarTypeList);
        });
        Brand brand=null;
        if (oneLevelCarTypeList.size()!=0){
            brand=oneLevelCarTypeList.get(0).getBrand();
            levelCarTypeService.deleteOneAll(oneLevelCarTypeList);
        }else {
            brand=brandRepository.findById(id).get();
        }
        brandRepository.deleteById(id);
        User currentUser= PasswordUtils.currentUser();
        String key="brand_"+currentUser.getSystemType();
        if (redisTemplate.hasKey(key)){
           List<Brand> brandList= (List<Brand>) redisTemplate.opsForValue().get(key);
           brandList.remove(brand);
            redisTemplate.opsForValue().set(key,brandList,2,TimeUnit.MINUTES);
        }
    }

    public void saveOne(OneLevelCarType oneLevelCarType) {
        User currentUser= PasswordUtils.currentUser();
        oneLevelCarType.setSystemType(currentUser.getSystemType());
        levelCarTypeService.saveOne(oneLevelCarType);
    }

    public Object searchOneCar(PageRequest build, String carTypeName) {
        User currentUser= PasswordUtils.currentUser();
        Specification<OneLevelCarType> specification = new Specification<OneLevelCarType>() {
            @Override
            public Predicate toPredicate(Root<OneLevelCarType> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(carTypeName)) {
                    Predicate p1 = cb.like(root.get("carTypeName").as(String.class), "%" + carTypeName.trim() + "%");
                    list.add(p1);
                }
                Predicate p2 = cb.equal(root.get("systemType").as(String.class), currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<OneLevelCarType> page = levelCarTypeService.findOneAll(specification, build);
        return page;
    }

    public void saveTwo(TwoLevelCarType twoLevelCarType) {
        User currentUser= PasswordUtils.currentUser();
        twoLevelCarType.setSystemType(currentUser.getSystemType());
        levelCarTypeService.saveTwo(twoLevelCarType);
    }

    public Object searchTwoCar(PageRequest build, String carTypeName) {
        User currentUser= PasswordUtils.currentUser();
        Specification<TwoLevelCarType> specification = new Specification<TwoLevelCarType>() {
            @Override
            public Predicate toPredicate(Root<TwoLevelCarType> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(carTypeName)) {
                    Predicate p1 = cb.like(root.get("carTypeName").as(String.class), "%" + carTypeName.trim() + "%");
                    list.add(p1);
                }
                Predicate p2 = cb.equal(root.get("systemType").as(String.class), currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<TwoLevelCarType> page = levelCarTypeService.findTwoAll(specification, build);
        return page;
    }

    public List<OneLevelCarType> searchOneCarAllByBrandId(Long id) {
        return levelCarTypeService.findAllByBrandId(id);
    }

    public List<Brand> findAllBrand() {
        User currentUser= PasswordUtils.currentUser();
        return brandRepository.findAllBySystemType(currentUser.getSystemType());
    }

    public void saveThree(ThreeLevelCarType threeLevelCarType) {
        User currentUser= PasswordUtils.currentUser();
        threeLevelCarType.setSystemType(currentUser.getSystemType());
        levelCarTypeService.saveThree(threeLevelCarType);
    }

    public Object searchThreeCar(PageRequest build, String carTypeName) {
        User currentUser= PasswordUtils.currentUser();
        Specification<ThreeLevelCarType> specification = new Specification<ThreeLevelCarType>() {
            @Override
            public Predicate toPredicate(Root<ThreeLevelCarType> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(carTypeName)) {
                    Predicate p1 = cb.like(root.get("carTypeName").as(String.class), "%" + carTypeName.trim() + "%");
                    list.add(p1);
                }
                Predicate p2 = cb.equal(root.get("systemType").as(String.class), currentUser.getSystemType());
                list.add(p2);
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<ThreeLevelCarType> page = levelCarTypeService.findThreeAll(specification, build);
        return page;
    }

    public List<TwoLevelCarType> searchTwoCarAllByOneId(Long id) {
        return levelCarTypeService.findAllByOneId(id);
    }

    public List<ThreeLevelCarType> searchThreeCarAllByTwoId(Long id) {
        return levelCarTypeService.findAllByTwoId(id);
    }

    public List<Brand> findAllBrandBySystemType(String systemType) {
        return brandRepository.findAllBySystemType(systemType);
    }

    public Object findByThreeId(Long id) {
        return threeLevelWithYearRepository.findByThreeId(id);
    }

    public Object findAllByYearIdAndThreeId(Long yearId,Long threeId) {
        return threeLevelWithYearRepository.findAllByYearIdAndThreeId(yearId,threeId);
    }
}
