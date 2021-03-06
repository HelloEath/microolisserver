package com.hello.brandserver.service.brand;


import com.hello.brandserver.repository.brand.BrandRepository;
import com.hello.common.config.AppConfig;
import com.hello.common.dto.olis.Brand;
import com.hello.common.dto.olis.OneLevelCarType;
import com.hello.common.dto.olis.ThreeLevelCarType;
import com.hello.common.dto.olis.TwoLevelCarType;
import com.hello.common.entity.system.UploadFile;
import com.hello.common.entity.system.User;
import com.hello.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    @Autowired
    FileService fileService;

    @Resource
    AppConfig appConfig;

    @Autowired
    UserService userService;

    public void save(Brand brand) {
        User currentUser=userService.currentUser().getData();
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
        User currentUser=userService.currentUser().getData();
        if (currentUser!=null){
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
        return null;
    }

    public void del(Long id) {
        List<OneLevelCarType> oneLevelCarTypeList=levelCarTypeService.findAllByBrandId(id).getData();
        oneLevelCarTypeList.forEach(x->{
            List<TwoLevelCarType> twoLevelCarTypeList=levelCarTypeService.findAllByOneId(x.getId()).getData();
            twoLevelCarTypeList.forEach(xx->{
                List<ThreeLevelCarType>threeLevelCarTypeList=levelCarTypeService.findAllByTwoId(xx.getId()).getData();
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
        User currentUser= new User();
        String key="brand_"+currentUser.getSystemType();
        if (redisTemplate.hasKey(key)){
           List<Brand> brandList= (List<Brand>) redisTemplate.opsForValue().get(key);
           brandList.remove(brand);
            redisTemplate.opsForValue().set(key,brandList,2,TimeUnit.MINUTES);
        }
    }

    public void saveOne(OneLevelCarType oneLevelCarType) {
        User currentUser=userService.currentUser().getData();
        oneLevelCarType.setSystemType(currentUser.getSystemType());
        levelCarTypeService.saveOne(oneLevelCarType);
    }

    public Object searchOneCar(PageRequest build, String carTypeName) {
        User currentUser=userService.currentUser().getData();
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
        Object []paramsObject=new Object[]{specification,build};
        Page<OneLevelCarType> page = levelCarTypeService.findOneAll(paramsObject).getData();
        return page;
    }

    public void saveTwo(TwoLevelCarType twoLevelCarType) {
        User currentUser=userService.currentUser().getData();
        twoLevelCarType.setSystemType(currentUser.getSystemType());
        levelCarTypeService.saveTwo(twoLevelCarType);
    }

    public Object searchTwoCar(PageRequest build, String carTypeName) {
        User currentUser=userService.currentUser().getData();
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
        Object []paramsObject=new Object[]{specification,build};
        Page<TwoLevelCarType> page = levelCarTypeService.findTwoAll(paramsObject).getData();
        return page;
    }

    public List<OneLevelCarType> searchOneCarAllByBrandId(Long id) {
        return levelCarTypeService.findAllByBrandId(id).getData();
    }

    public List<Brand> findAllBrand() {
        User currentUser=userService.currentUser().getData();
        return brandRepository.findAllBySystemType(currentUser.getSystemType());
    }

    public void saveThree(ThreeLevelCarType threeLevelCarType) {
        User currentUser=userService.currentUser().getData();
        threeLevelCarType.setSystemType(currentUser.getSystemType());
        levelCarTypeService.saveThree(threeLevelCarType);
    }

    public Object searchThreeCar(PageRequest build, String carTypeName) {
        User currentUser=userService.currentUser().getData();
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
        Object []paramsObject=new Object[]{specification,build};

        Page<ThreeLevelCarType> page = levelCarTypeService.findThreeAll(paramsObject).getData();
        return page;
    }

    public List<TwoLevelCarType> searchTwoCarAllByOneId(Long id) {
        return levelCarTypeService.findAllByOneId(id).getData();
    }

    public List<ThreeLevelCarType> searchThreeCarAllByTwoId(Long id) {
        return levelCarTypeService.findAllByTwoId(id).getData();
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

    public Result<UploadFile> uploadBrandImage(MultipartFile file) {
        String path = appConfig.getBrand();
        return fileService.upload(file);
    }

    public static void main(String[] args) {
        String s1 = "HelloWorld";

        String s2 = new String("HelloWorld");

        String s3 = "Hello";
        String s4 = "World";
        String s5 = "Hello" + "World";
        String s6 = s3 + s4;

        System.out.println(s1 == s2);
        System.out.println(s1 == s5);
        System.out.println(s1 == s6);
        System.out.println(s1 == s6.intern());
        System.out.println(s2 == s2.intern());

    }

}
