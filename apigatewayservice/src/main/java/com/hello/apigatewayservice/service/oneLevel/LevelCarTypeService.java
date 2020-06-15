package com.hello.apigatewayservice.service.oneLevel;


import com.hello.apigatewayservice.repository.olis.OneLevelCarTypeRepository;
import com.hello.apigatewayservice.repository.olis.ThreeLevelCarTypeRepository;
import com.hello.apigatewayservice.repository.olis.TwoLevelCarTypeRepository;
import com.hello.common.dto.olis.OneLevelCarType;
import com.hello.common.dto.olis.ThreeLevelCarType;
import com.hello.common.dto.olis.TwoLevelCarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  15:19
 */
@Service
@Transactional
public class LevelCarTypeService {
    @Autowired
    OneLevelCarTypeRepository oneLevelCarTypeRepository;
    @Autowired
    TwoLevelCarTypeRepository twoLevelCarTypeRepository;
    @Autowired
    ThreeLevelCarTypeRepository threeLevelCarTypeRepository;

    public List<OneLevelCarType> findAllByBrandId(Long id){
        return oneLevelCarTypeRepository.findAllByBrandId(id);
    }

    public void deleteOneAll(List<OneLevelCarType> oneLevelCarTypeList ){
         oneLevelCarTypeRepository.deleteAll(oneLevelCarTypeList);
    }

    public void saveOne(OneLevelCarType oneLevelCarTypeList){
         oneLevelCarTypeRepository.save(oneLevelCarTypeList);
    }

    public Page<OneLevelCarType>  findOneAll( Specification<OneLevelCarType> specification,PageRequest build){
        return oneLevelCarTypeRepository.findAll(specification, build);
    }
    public List<TwoLevelCarType>  findAllByOneId(Long id){
        return twoLevelCarTypeRepository.findAllByOneId(id);
    }

    public void deleteTwoAll(List<TwoLevelCarType> twoLevelCarTypes ){
        twoLevelCarTypeRepository.deleteAll(twoLevelCarTypes);
    }
    public void  saveTwo(TwoLevelCarType twoLevelCarType){
        twoLevelCarTypeRepository.save(twoLevelCarType);
    }
    public Page<TwoLevelCarType>  findTwoAll( Specification<TwoLevelCarType> specification,PageRequest build){
        return twoLevelCarTypeRepository.findAll(specification, build);
    }

    public void deleteThreeAll(List<ThreeLevelCarType> threeLevelCarTypes ){
        threeLevelCarTypeRepository.deleteAll(threeLevelCarTypes);
    }

    public void saveThree(ThreeLevelCarType threeLevelCarType){
        threeLevelCarTypeRepository.save(threeLevelCarType);
    }
    public Page<ThreeLevelCarType>  findThreeAll( Specification<ThreeLevelCarType> specification,PageRequest build){
        return threeLevelCarTypeRepository.findAll(specification, build);
    }

    public List<ThreeLevelCarType> findAllByTwoId(Long id) {
        return threeLevelCarTypeRepository.findAllByTwoId(id);
    }
}
