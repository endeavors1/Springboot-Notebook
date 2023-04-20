package com.yangy.mutipile.data.dto;

/**
 * @Title: UserVo
 * @Package com.yangy.mutipile.data.dto
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/27 11:07
 **/
public class UserVo {

    private String userId;
    private String UserName;
    private String phone;

    public UserVo(String _userId, String _userName, String _phone) {
        this.userId = _userId;
        this.UserName = _userName;
        this.phone = _phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "userId='" + userId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
