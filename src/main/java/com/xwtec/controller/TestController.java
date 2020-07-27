package com.xwtec.controller;

import com.xwtec.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TestController
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/24 10:05
 */
@Controller
public class TestController {
    @Autowired
    IUserService userService;

    @ResponseBody
    @RequestMapping("/testMaster")
    public List<Map<String, Object>> testMasterDataSource() {
        List<Map<String, Object>> list = userService.qryAllUser();
        return list;
    }
}
