package com.itdr.mapper;

import com.itdr.pojo.Friend;

public interface FriendMapper {
    int deleteByPrimaryKey(Integer friendId);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(Integer friendId);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);
}