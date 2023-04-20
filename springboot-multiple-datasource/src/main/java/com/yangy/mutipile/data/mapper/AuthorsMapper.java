package com.yangy.mutipile.data.mapper;

import com.yangy.mutipile.data.dto.Authors;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @Title: AuthorsMapper
 * @Package com.yangy.mutipile.data.mapper
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/29 11:23
 **/

@Mapper
@Lazy
public interface AuthorsMapper {

    List<Authors> selectByExample(Authors example);

    List<Authors> streamByExample(Authors example); //以stream形式从mysql获取数据

}
