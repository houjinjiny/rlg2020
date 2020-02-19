package com.itdr.mapper;

import com.itdr.pojo.Fare;

public interface FareMapper {
    int deleteByPrimaryKey(Integer fid);

    int insert(Fare record);

    int insertSelective(Fare record);

    Fare selectByPrimaryKey(Integer fid);

    int updateByPrimaryKeySelective(Fare record);

    int updateByPrimaryKey(Fare record);
}