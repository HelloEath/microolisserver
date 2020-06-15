package com.hello.adminserver.controller.bugs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/22  17:21
 */
@Api(value = "爬虫接口",description = "BugsController")
@RequestMapping(path = "/bugs")
public interface BugsController {

    @ApiOperation(value="爬虫", notes="爬取页面信息")
    @RequestMapping(path = "/bug",method = RequestMethod.GET)
    void bugs();
}
