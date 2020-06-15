package com.hello.apigatewayservice.controller.year;

import com.hello.apigatewayservice.controller.common.BaseController;
import com.hello.common.dto.olis.Region;
import com.hello.common.dto.olis.RegionPrizeManage;
import com.hello.common.dto.olis.Year;
import com.hello.apigatewayservice.service.year.YearService;
import com.hello.apigatewayservice.util.PageRequestUtil;
import com.hello.apigatewayservice.util.Result;
import com.hello.apigatewayservice.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/30  20:56
 */
@RestController
public class YearControllerImpl extends BaseController implements YearController {
    @Autowired
    YearService yearService;
    @Override
    public Result<Region> getRegion(Long id) {
        return null;
    }

    @Override
    public Result<Year> save(@RequestBody Year year) {
        yearService.save(year);
        return ResultUtil.success();
    }

    @Override
    public Result<Page<Year>> years(String keyYearWords, Integer pageNo) {
        return ResultUtil.success(yearService.serach(keyYearWords, PageRequestUtil.build(pageNo)));
    }

    @Override
    public Result<List<Year>> years() {
        return ResultUtil.success(yearService.findAllYear());
    }

    @Override
    public Result<String> del(Long id) {
        return null;
    }

    @Override
    public Result<RegionPrizeManage> save(RegionPrizeManage regionPrizeManage) {
        return null;
    }

    @Override
    public Result<List<Year>> findAllById(List<Long> yearIdList) {
        return ResultUtil.success(yearService.findAllById(yearIdList));
    }
}
