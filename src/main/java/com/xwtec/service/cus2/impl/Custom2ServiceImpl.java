package com.xwtec.service.cus2.impl;

import com.xwtec.dao.cus2.ICus2UserMapper;
import com.xwtec.service.cus2.ICustom2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Custom2ServiceImpl
 * @Description TODO
 * @Auth Administrator
 * @Date 2020/7/27 15:22
 */
@Service
public class Custom2ServiceImpl implements ICustom2Service {
    @Autowired
    private ICus2UserMapper cus2UserMapper;

    @Override
    public List<Map<String, Object>> qryAllCustom() {
        return cus2UserMapper.qryAllCustom();
    }
}
