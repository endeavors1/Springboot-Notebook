package com.xiaofu.service;

import org.springframework.stereotype.Service;

/**
 * @Title: UserServiceImpl
 * @Package com.xiaofu.service
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/22 15:24
 **/

@Service
public class UserServiceImpl implements UserService{
    @Override
    public void searchUser() {
        System.out.println("method exec!!!!");
    }
}
