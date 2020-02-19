package com.itdr.mapper;

import com.itdr.pojo.Ticket;

public interface TicketMapper {
    int deleteByPrimaryKey(Integer ticketId);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    Ticket selectByPrimaryKey(Integer ticketId);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);
}