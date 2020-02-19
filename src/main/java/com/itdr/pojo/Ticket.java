package com.itdr.pojo;

import java.math.BigDecimal;

public class Ticket {
    private Integer ticketId;

    private Integer orderId;

    private String tid;

    private Integer cid;

    private Integer seatId;

    private String customerName;

    private String customerIdCard;

    private String ticketStartStation;

    private String ticketEndStation;

    private String ticketStartDate;

    private BigDecimal ticketPrice;

    private String ticketState;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerIdCard() {
        return customerIdCard;
    }

    public void setCustomerIdCard(String customerIdCard) {
        this.customerIdCard = customerIdCard == null ? null : customerIdCard.trim();
    }

    public String getTicketStartStation() {
        return ticketStartStation;
    }

    public void setTicketStartStation(String ticketStartStation) {
        this.ticketStartStation = ticketStartStation == null ? null : ticketStartStation.trim();
    }

    public String getTicketEndStation() {
        return ticketEndStation;
    }

    public void setTicketEndStation(String ticketEndStation) {
        this.ticketEndStation = ticketEndStation == null ? null : ticketEndStation.trim();
    }

    public String getTicketStartDate() {
        return ticketStartDate;
    }

    public void setTicketStartDate(String ticketStartDate) {
        this.ticketStartDate = ticketStartDate == null ? null : ticketStartDate.trim();
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState == null ? null : ticketState.trim();
    }
}