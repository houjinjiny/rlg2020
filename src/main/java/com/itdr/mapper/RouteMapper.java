package com.itdr.mapper;

import com.itdr.pojo.Route;

public interface RouteMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Route record);

    int insertSelective(Route record);

    Route selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Route record);

    int updateByPrimaryKey(Route record);
}