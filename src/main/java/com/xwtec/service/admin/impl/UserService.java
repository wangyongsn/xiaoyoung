package com.xwtec.service.admin.impl;

import com.xwtec.dao.admin.IUserMapper;
import com.xwtec.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserService
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/24 10:03
 */
@Service
public class UserService implements IUserService {
    @Autowired
    IUserMapper userMapper;

    @Override
    public List<Map<String, Object>> qryAllUser() {
        return userMapper.qryAllUserList();
    }
}
