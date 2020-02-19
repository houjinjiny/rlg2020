package com.itdr.pojo;

public class Friend {
    private Integer friendId;

    private String friendIdCard;

    private String friendName;

    private String friendPhone;

    private String friendRole;

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getFriendIdCard() {
        return friendIdCard;
    }

    public void setFriendIdCard(String friendIdCard) {
        this.friendIdCard = friendIdCard == null ? null : friendIdCard.trim();
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName == null ? null : friendName.trim();
    }

    public String getFriendPhone() {
        return friendPhone;
    }

    public void setFriendPhone(String friendPhone) {
        this.friendPhone = friendPhone == null ? null : friendPhone.trim();
    }

    public String getFriendRole() {
        return friendRole;
    }

    public void setFriendRole(String friendRole) {
        this.friendRole = friendRole == null ? null : friendRole.trim();
    }
}