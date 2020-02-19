package com.itdr.pojo;

import java.math.BigDecimal;

public class Fare {
    private Integer fid;

    private String fareType;

    private BigDecimal farePrice;

    private String fareRole;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType == null ? null : fareType.trim();
    }

    public BigDecimal getFarePrice() {
        return farePrice;
    }

    public void setFarePrice(BigDecimal farePrice) {
        this.farePrice = farePrice;
    }

    public String getFareRole() {
        return fareRole;
    }

    public void setFareRole(String fareRole) {
        this.fareRole = fareRole == null ? null : fareRole.trim();
    }
}