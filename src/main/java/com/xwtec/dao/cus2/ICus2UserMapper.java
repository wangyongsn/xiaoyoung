package com.xwtec.dao.cus2;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ICus2UserMapper {
    List<Map<String, Object>> qryAllCustom();
}
