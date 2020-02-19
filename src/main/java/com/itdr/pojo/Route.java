package com.itdr.pojo;

public class Route {
    private Integer rid;

    private String tid;

    private Integer sid;

    private String arriveTime;

    private String leaveTime;

    private Integer upDistance;

    private String waitTime;

    private Integer routeSeq;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime == null ? null : arriveTime.trim();
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime == null ? null : leaveTime.trim();
    }

    public Integer getUpDistance() {
        return upDistance;
    }

    public void setUpDistance(Integer upDistance) {
        this.upDistance = upDistance;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime == null ? null : waitTime.trim();
    }

    public Integer getRouteSeq() {
        return routeSeq;
    }

    public void setRouteSeq(Integer routeSeq) {
        this.routeSeq = routeSeq;
    }
}