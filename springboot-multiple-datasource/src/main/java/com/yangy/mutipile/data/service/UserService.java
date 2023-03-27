package com.yangy.mutipile.data.service;

import com.yangy.mutipile.data.dto.UserVo;

import java.util.List;

/**
 * @Title: UserService
 * @Package com.yangy.mutipile.data.service
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/27 14:22
 **/
public interface UserService {

    List<UserVo> searchUserList(String userName);

    int insertUser(UserVo userVo);

    int deleteUserByUserId(String userId);

}
