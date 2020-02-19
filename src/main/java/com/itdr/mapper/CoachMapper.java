package com.itdr.mapper;

import com.itdr.pojo.Coach;

public interface CoachMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Coach record);

    int insertSelective(Coach record);

    Coach selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Coach record);

    int updateByPrimaryKey(Coach record);
}