package com.hello.adminservice.controller.levelCarType;

import com.hello.adminservice.service.oneLevel.LevelCarTypeService;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import com.hello.common.dto.olis.OneLevelCarType;
import com.hello.common.dto.olis.ThreeLevelCarType;
import com.hello.common.dto.olis.TwoLevelCarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  15:40
 */
@RestController
public class LevelCarTypeControllerImpl implements LevelCarTypeController {

    @Autowired
    LevelCarTypeService levelCarTypeService;
    @Override
    public Result<List<OneLevelCarType>> findAllByBrandId(Long id) {
        return ResultUtil.success(levelCarTypeService.findAllByBrandId(id));
    }

    @Override
    public Result<Object> deleteOneAll(List<OneLevelCarType> oneLevelCarTypeList) {
        levelCarTypeService.deleteOneAll(oneLevelCarTypeList);
        return ResultUtil.success();

    }

    @Override
    public Result<Object> saveOne(OneLevelCarType oneLevelCarTypeList) {
        levelCarTypeService.saveOne(oneLevelCarTypeList);
        return ResultUtil.success();

    }

    @Override
    public Result<Page<OneLevelCarType>> findOneAll(Object[] objects) {
        Specification<OneLevelCarType> specification= (Specification<OneLevelCarType>) objects[0];
        PageRequest build= (PageRequest) objects[1];
        return ResultUtil.success(levelCarTypeService.findOneAll(specification,build));
    }

    @Override
    public Result<List<TwoLevelCarType>> findAllByOneId(Long id) {
        return ResultUtil.success(levelCarTypeService.findAllByOneId(id));
    }

    @Override
    public Result<Object> deleteTwoAll(List<TwoLevelCarType> twoLevelCarTypes) {
        levelCarTypeService.deleteTwoAll(twoLevelCarTypes);
        return ResultUtil.success();

    }

    @Override
    public Result<Object> saveTwo(TwoLevelCarType twoLevelCarType) {
        levelCarTypeService.saveTwo(twoLevelCarType);
        return ResultUtil.success();

    }

    @Override
    public Result<Page<TwoLevelCarType>> findTwoAll(Object[] objects) {
        Specification<TwoLevelCarType> specification= (Specification<TwoLevelCarType>) objects[0];
        PageRequest build= (PageRequest) objects[1];
        return ResultUtil.success(levelCarTypeService.findTwoAll(specification,build));
    }

    @Override
    public Result<Object>  deleteThreeAll(List<ThreeLevelCarType> threeLevelCarTypes) {
        levelCarTypeService.deleteThreeAll(threeLevelCarTypes);
        return ResultUtil.success();

    }

    @Override
    public Result<Object> saveThree(ThreeLevelCarType threeLevelCarType) {
        levelCarTypeService.saveThree(threeLevelCarType);
        return  ResultUtil.success();

    }

    @Override
    public Result<Page<ThreeLevelCarType>> findThreeAll(Object[] objects) {
        Specification<ThreeLevelCarType> specification= (Specification<ThreeLevelCarType>) objects[0];
        PageRequest build= (PageRequest) objects[1];
        return ResultUtil.success(levelCarTypeService.findThreeAll(specification, build));
    }

    @Override
    public Result<List<ThreeLevelCarType>> findAllByTwoId(Long id) {
        return ResultUtil.success(levelCarTypeService.findAllByTwoId(id));
    }
}
