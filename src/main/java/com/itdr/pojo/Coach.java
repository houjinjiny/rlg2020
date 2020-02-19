package com.itdr.pojo;

public class Coach {
    private Integer cid;

    private String tid;

    private Integer coachSeq;

    private String cType;

    private Integer cnum;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public Integer getCoachSeq() {
        return coachSeq;
    }

    public void setCoachSeq(Integer coachSeq) {
        this.coachSeq = coachSeq;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType == null ? null : cType.trim();
    }

    public Integer getCnum() {
        return cnum;
    }

    public void setCnum(Integer cnum) {
        this.cnum = cnum;
    }
}