package com.itdr.utils;

import com.itdr.pojo.User;
import com.itdr.pojo.bo.UserVo;

public class ObjectToVoUtil {
    public static UserVo userToUserVo(User u){
        UserVo uv=new UserVo();
        uv.setId(u.getId());
        uv.setUsername(u.getUsername());
        uv.setUname(u.getUname());
        uv.setSex(u.getSex());
        uv.setEmail(u.getEmail());
        uv.setPhone(u.getPhone());
        uv.setCreateTime(u.getCreateTime());
        uv.setUpdateTime(u.getUpdateTime());
        return uv;
    }
}
