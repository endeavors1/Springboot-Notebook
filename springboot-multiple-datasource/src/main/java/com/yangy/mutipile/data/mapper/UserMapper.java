package com.yangy.mutipile.data.mapper;

import com.yangy.mutipile.data.dto.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: UserMapper
 * @Package com.yangy.mutipile.data.mapper
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/27 11:02
 **/


@Mapper
public interface UserMapper {

    List<UserVo> searchUserList(String userName);

    int insertUser(UserVo userVo);

    int deleteUserByUserId(String userId);


}
