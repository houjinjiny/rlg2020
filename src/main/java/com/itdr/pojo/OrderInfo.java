package com.itdr.pojo;

public class OrderInfo {
    private Integer orderId;

    private String id;

    private String orderDate;

    private Boolean orderState;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public Boolean getOrderState() {
        return orderState;
    }

    public void setOrderState(Boolean orderState) {
        this.orderState = orderState;
    }
}