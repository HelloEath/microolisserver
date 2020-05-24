package com.hello.adminservice.controller.region;

import com.hello.adminservice.controller.common.BaseController;
import com.hello.common.dto.olis.Region;
import com.hello.common.dto.olis.RegionPrizeManage;
import com.hello.adminservice.service.UserService;
import com.hello.adminservice.service.olis.RegionService;
import com.hello.adminservice.util.PageRequestUtil;
import com.hello.adminservice.util.Result;
import com.hello.adminservice.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lxw on 2019/08/27.
 */
@RestController
public class RegionControllerImpl extends BaseController implements RegionController {
    @Autowired
    RegionService regionService;
    @Autowired
    UserService userService;

    /**
     * 获取地区
     * @param id
     * @return
     */
    @Override
    public Result<Region> getRegion(@PathVariable Long id) {
        return ResultUtil.success(regionService.get(id));
    }

    @Override
    public Result<RegionPrizeManage> getRegionOlis(@PathVariable Long id) {
        return ResultUtil.success(regionService.findAllByRegionId(id));
    }

    /**
     * 保存/更新地区
     * @param Region
     * @return
     */
    @Override
    public Result<Region> save(@RequestBody Region Region) {
        return ResultUtil.success(regionService.save(Region));
    }


    /**
     * 分页获取地区集合
     * @param name
     * @param vCode
     * @return
     */
    @Override
    public Result<Page<Region>> regions(String regionName, String vCode, Integer pageNo) {
        Page<Region> page = regionService.search(PageRequestUtil.build(pageNo), regionName, vCode);
        return ResultUtil.success(page);
    }

    /**
     * 删除地区
     */
	@Override
	public Result<String> del(@PathVariable Long id) {
		String result = regionService.del(id);
		return ResultUtil.success(result);
	}

    @Override
    public Result<String> delPrize(@PathVariable Long id) {
	    regionService.delRegionPrizeById(id);
        return ResultUtil.success();
    }

    /**
     * 保存/更新地区价格
     * @param Region
     * @return
     */
    @Override
    public Result<RegionPrizeManage> save(@RequestBody RegionPrizeManage regionPrizeManage) {
        regionService.saveRegionPrize(regionPrizeManage);
        return ResultUtil.success();
    }

    @Override
    public Result<List> getAllRegions() {
        List<Region> regionList = regionService.findAllRegion();
        Result<List> result = new Result<>();
        result.setData(regionList);
        result.setMsg("查询结果条目数:"+regionList.size());
        return result;
    }

    @Override
    public Result<List<Region>> findAllBySystemType(String systemType) {
        List<Region> regionList =  regionService.findAllBySystemType(systemType);
        return ResultUtil.success(regionList);
    }

}
