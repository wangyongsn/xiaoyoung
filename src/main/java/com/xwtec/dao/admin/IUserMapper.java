package com.xwtec.dao.admin;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IUserMapper {
    List<Map<String, Object>> qryAllUserList();

    int insertUser(Map<String, Object> user);
}
