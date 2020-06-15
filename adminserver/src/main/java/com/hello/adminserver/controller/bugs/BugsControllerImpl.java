package com.hello.adminserver.controller.bugs;

import com.hello.adminserver.service.bug.BugsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/22  17:22
 */
@RestController
public class BugsControllerImpl implements BugsController {

    @Autowired
    BugsService bugsService;
    @Override
    public void bugs() {
        bugsService.bugs();

    }

}
