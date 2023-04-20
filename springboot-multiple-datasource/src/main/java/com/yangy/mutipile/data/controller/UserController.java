package com.yangy.mutipile.data.controller;

import com.yangy.mutipile.data.dto.UserVo;
import com.yangy.mutipile.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Title: UserController
 * @Package com.yangy.mutipile.data.controller
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/27 14:19
 **/

@RestController
public class UserController {


    @Autowired
    UserService userService;

    @RequestMapping("/searchUser")
    public List<UserVo> searchUserList(String userName) {
        return userService.searchUserList(userName);
    }


    @RequestMapping("/insertUser")
    public void insertUser(UserVo userVo) {
        userService.insertUser(userVo);
        System.out.println("插入用户：" + userVo + " success!");
    }


    @RequestMapping("/deleteUser")
    public void deleteUser(String userId) {
        userService.deleteUserByUserId(userId);
        System.out.println("删除用户：" + userId + " success!");
    }


}
