package com.hello.adminservice.controller.brand;

import com.hello.common.dto.olis.Brand;
import com.hello.common.dto.olis.OneLevelCarType;
import com.hello.common.dto.olis.ThreeLevelCarType;
import com.hello.common.dto.olis.TwoLevelCarType;
import com.hello.adminservice.service.FileService;
import com.hello.adminservice.service.brand.BrandService;
import com.hello.common.util.PageRequestUtil;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import com.hello.common.dto.olis.ThreeLevelWithYear;
import com.hello.common.entity.system.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/11/10  11:05
 */
@RestController
public class BrandControllerImpl implements BrandController {

    @Autowired
    BrandService brandService;
    @Autowired
    FileService fileService;
    @Override
    public Result<Brand> save(@RequestBody Brand brand) {
        brandService.save(brand);
        return ResultUtil.success();
    }

    @Override
    public Result<OneLevelCarType> saveOne(@RequestBody OneLevelCarType oneLevelCarType) {
        brandService.saveOne(oneLevelCarType);
        return ResultUtil.success();
    }

    @Override
    public Result<TwoLevelCarType> saveTwo(@RequestBody TwoLevelCarType twoLevelCarType) {
        brandService.saveTwo(twoLevelCarType);
        return ResultUtil.success();
    }

    @Override
    public Result<ThreeLevelCarType> saveThree(@RequestBody ThreeLevelCarType threeLevelCarType) {
        brandService.saveThree(threeLevelCarType);
        return ResultUtil.success();
    }

    @Override
    public Result<UploadFile> uploadBrandImage(MultipartFile file) {
        return ResultUtil.success(fileService.uploadBrandImage(file));
    }

    @Override
    public Result<Page<Brand>> brands(String brandName, String vCode, Integer pageNo) {
        return ResultUtil.success(brandService.search(PageRequestUtil.build(pageNo),brandName));
    }

    @Override
    public Result<List<Brand>> brands() {
        return ResultUtil.success(brandService.findAllBrand());
    }

    @Override
    public Result<Page<OneLevelCarType>> oneCars(String carTypeName, Integer pageNo) {
        return ResultUtil.success(brandService.searchOneCar(PageRequestUtil.build(pageNo),carTypeName));
    }

    @Override
    public Result<Page<TwoLevelCarType>> twoCars(String carTypeName, Integer pageNo) {
        return ResultUtil.success(brandService.searchTwoCar(PageRequestUtil.build(pageNo),carTypeName));
    }

    @Override
    public Result<Page<ThreeLevelCarType>> threeCars(String carTypeName, Integer pageNo) {
        return ResultUtil.success(brandService.searchThreeCar(PageRequestUtil.build(pageNo),carTypeName));
    }

    @Override
    public Result<List<OneLevelCarType>> oneCars(@PathVariable Long id) {
        return ResultUtil.success(brandService.searchOneCarAllByBrandId(id));
    }

    @Override
    public Result<List<TwoLevelCarType>> twoCars(@PathVariable Long id) {
        return ResultUtil.success(brandService.searchTwoCarAllByOneId(id));
    }

    @Override
    public Result<List<ThreeLevelCarType>> threeCars(@PathVariable Long id) {
        return ResultUtil.success(brandService.searchThreeCarAllByTwoId(id));
    }

    @Override
    public Result<String> del(@PathVariable Long id) {
        brandService.del(id);
        return ResultUtil.success();
    }

    @Override
    public  Result<List<Brand>> findAllBrandBySystemType(@RequestParam String systemType) {
        return ResultUtil.success(brandService.findAllBrandBySystemType(systemType));
    }
    @Override
    public Result<List<OneLevelCarType>> searchOneCarAllByBrandId(@PathVariable Long id) {
        return ResultUtil.success(brandService.searchOneCarAllByBrandId(id));
    }

    @Override
    public Result<List<TwoLevelCarType>> searchTwoCarAllByOneId(@PathVariable Long id) {
        return ResultUtil.success(brandService.searchTwoCarAllByOneId(id));
    }
    @Override
    public Result<List<ThreeLevelCarType>> searchThreeCarAllByTwoId(@PathVariable Long id) {
        return ResultUtil.success(brandService.searchThreeCarAllByTwoId(id));
    }

    @Override
    public  Result<List<ThreeLevelWithYear>> findByThreeId(@PathVariable Long id) {
        return ResultUtil.success(brandService.findByThreeId(id));

    }
    @Override
   public  Result<List<ThreeLevelWithYear>> findAllByYearIdAndThreeId(Long yearId, Long threeId){
            return ResultUtil.success(brandService.findAllByYearIdAndThreeId(yearId,threeId));
    }
}
