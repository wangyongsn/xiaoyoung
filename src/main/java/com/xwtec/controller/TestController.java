package com.xwtec.controller;

import com.xwtec.service.admin.IUserService;
import com.xwtec.service.cus2.ICustom2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestController
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/24 10:05
 */
@Controller
@Slf4j
public class TestController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICustom2Service custom2Service;

    @ResponseBody
    @RequestMapping("/testMaster")
    public List<Map<String, Object>> testMasterDataSource() {
        List<Map<String, Object>> list = userService.qryAllUser();
        //一个亿数据源查询数据
        List<Map<String, Object>> list1 = custom2Service.qryAllCustom();
        list.addAll(list1);
        //插入数据
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("username", "testInsert");
        paraMap.put("password", "testInsert");
        paraMap.put("roleId", 3);
        paraMap.put("id", 2);
        log.info("paraMap:" + paraMap);
        int result = userService.insertUser(paraMap);
        log.info("插入数据成功：" + result);
        return list;
    }
}
