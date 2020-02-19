package com.itdr.mapper;

import com.itdr.pojo.Train;

public interface TrainMapper {
    int deleteByPrimaryKey(String tid);

    int insert(Train record);

    int insertSelective(Train record);

    Train selectByPrimaryKey(String tid);

    int updateByPrimaryKeySelective(Train record);

    int updateByPrimaryKey(Train record);
}