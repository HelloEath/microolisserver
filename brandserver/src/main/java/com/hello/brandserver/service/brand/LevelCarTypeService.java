package com.hello.brandserver.service.brand;

import com.hello.common.dto.olis.OneLevelCarType;
import com.hello.common.dto.olis.ThreeLevelCarType;
import com.hello.common.dto.olis.TwoLevelCarType;
import com.hello.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/23  18:39
 */

@FeignClient(value = "olis-server",path = "/levelCarType", fallbackFactory = LevelCarTypeServiceHandler.class)
public interface LevelCarTypeService {
    @RequestMapping(path = "/findAllByBrandId", method = RequestMethod.GET)
    Result<List<OneLevelCarType>> findAllByBrandId(@RequestParam("id") Long id);

    @RequestMapping(path = "/deleteOneAll", method = RequestMethod.GET)
    Result<Object>  deleteOneAll(@RequestBody List<OneLevelCarType> oneLevelCarTypeList );

    @RequestMapping(path = "/saveOne", method = RequestMethod.POST)
    Result<Object>  saveOne(@RequestBody OneLevelCarType oneLevelCarTypeList);


    @RequestMapping(path = "/findAllByOneId", method = RequestMethod.GET)
    Result<List<TwoLevelCarType>>  findAllByOneId(@RequestParam("id") Long id);

    @RequestMapping(path = "/deleteTwoAll", method = RequestMethod.GET)
    Result<Object>  deleteTwoAll(@RequestBody List<TwoLevelCarType> twoLevelCarTypes );

    @RequestMapping(path = "/saveTwo", method = RequestMethod.POST)
    Result<Object>   saveTwo(@RequestBody TwoLevelCarType twoLevelCarType);


    @RequestMapping(path = "/deleteThreeAll", method = RequestMethod.GET)
    Result<Object>  deleteThreeAll(@RequestBody List<ThreeLevelCarType> threeLevelCarTypes );

    @RequestMapping(path = "/saveThree", method = RequestMethod.POST)
    Result<Object> saveThree(@RequestBody ThreeLevelCarType threeLevelCarType);

    @RequestMapping(path = "/findAllByTwoId", method = RequestMethod.GET)
    Result<List<ThreeLevelCarType>> findAllByTwoId(@RequestParam("id")Long id) ;

    @RequestMapping(path = "/findTwoAll", method = RequestMethod.GET)
    Result<Page<TwoLevelCarType>> findTwoAll(@RequestBody Object[] paramsObject);

    @RequestMapping(path = "/findOneAll", method = RequestMethod.GET)
    Result<Page<OneLevelCarType>> findOneAll(@RequestBody Object[] paramsObject);

    @RequestMapping(path = "/findThreeAll", method = RequestMethod.GET)
    Result<Page<ThreeLevelCarType>> findThreeAll(@RequestBody Object[] paramsObject);
}
