package com.hello.adminserver.controller.levelCarType;


import com.hello.common.dto.olis.OneLevelCarType;
import com.hello.common.dto.olis.ThreeLevelCarType;
import com.hello.common.dto.olis.TwoLevelCarType;
import com.hello.common.util.Result;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Hzh on 2018/6/1.
 */
@Api(value = "子车型接口",description = "LevelCarTypeController,")
@RequestMapping(path = "/levelCarType")
public interface LevelCarTypeController {

	@RequestMapping(path = "/findAllByBrandId", method = RequestMethod.GET)
	 Result<List<OneLevelCarType>> findAllByBrandId(Long id);

	@RequestMapping(path = "/deleteOneAll", method = RequestMethod.GET)
	Result<Object>  deleteOneAll(List<OneLevelCarType> oneLevelCarTypeList);

	@RequestMapping(path = "/saveOne", method = RequestMethod.POST)
	Result<Object>  saveOne(OneLevelCarType oneLevelCarTypeList);

	@RequestMapping(path = "/findOneAll", method = RequestMethod.GET)
	 Result<Page<OneLevelCarType>>  findOneAll(Object[] objects);

	@RequestMapping(path = "/findAllByOneId", method = RequestMethod.GET)
	 Result<List<TwoLevelCarType>>  findAllByOneId(Long id);

	@RequestMapping(path = "/deleteTwoAll", method = RequestMethod.GET)
	Result<Object>  deleteTwoAll(List<TwoLevelCarType> twoLevelCarTypes);

	@RequestMapping(path = "/saveTwo", method = RequestMethod.POST)
	Result<Object>   saveTwo(TwoLevelCarType twoLevelCarType);

	@RequestMapping(path = "/findTwoAll", method = RequestMethod.GET)
	 Result<Page<TwoLevelCarType>>  findTwoAll(Object[] objects);

	@RequestMapping(path = "/deleteThreeAll", method = RequestMethod.GET)
	Result<Object>  deleteThreeAll(List<ThreeLevelCarType> threeLevelCarTypes);

	@RequestMapping(path = "/saveThree", method = RequestMethod.POST)
	Result<Object> saveThree(ThreeLevelCarType threeLevelCarType);

	@RequestMapping(path = "/findThreeAll", method = RequestMethod.GET)
	 Result<Page<ThreeLevelCarType>>  findThreeAll(Object[] objects);

	@RequestMapping(path = "/findAllByTwoId", method = RequestMethod.GET)
	 Result<List<ThreeLevelCarType>> findAllByTwoId(Long id) ;


}
