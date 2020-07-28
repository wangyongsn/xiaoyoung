package com.xwtec.service.admin.impl;

import com.xwtec.dao.admin.IUserMapper;
import com.xwtec.service.admin.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserService
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/24 10:03
 */
@Service
@Slf4j
public class UserService implements IUserService {
    @Autowired
    IUserMapper userMapper;

    @Override
    public List<Map<String, Object>> qryAllUser() {
        return userMapper.qryAllUserList();
    }

    @Transactional(value = "maseterDataSourceTransactionManager")
    @Override
    public int insertUser(Map<String, Object> paraMap) {
//        int insertUser = userMapper.insertUser(paraMap);
//        log.error("数据回滚，开始");
//        int a = 1/0;
//        log.error("数据回滚！");
        return 0;
    }
}
