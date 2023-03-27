package com.yangy.mutipile.data.service;

import com.yangy.mutipile.data.aspect.TargetDataSource;
import com.yangy.mutipile.data.dto.UserVo;
import com.yangy.mutipile.data.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Title: UserServiceImpl
 * @Package com.yangy.mutipile.data.service
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/27 14:23
 **/

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    @Lazy
    private UserMapper userMapper;

/*
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
*/

    @Override
    @TargetDataSource("db1")//从主库搜索
    public List<UserVo> searchUserList(String userName) {
        return userMapper.searchUserList(userName);
        //return null;
    }

    @Override
    @TargetDataSource("db2")//从库插入
    public int insertUser(UserVo userVo) {
        return userMapper.insertUser(userVo);
        //return 1;
    }

    @Override
    public int deleteUserByUserId(String userId) {
        System.out.println(userMapper.deleteUserByUserId(userId));
        //return userMapper.deleteUserByUserId(userId);
        return 1;
    }
}
