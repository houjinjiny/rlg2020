package com.itdr.mapper;

import com.itdr.pojo.UserFriendKey;

public interface UserFriendMapper {
    int deleteByPrimaryKey(UserFriendKey key);

    int insert(UserFriendKey record);

    int insertSelective(UserFriendKey record);
}