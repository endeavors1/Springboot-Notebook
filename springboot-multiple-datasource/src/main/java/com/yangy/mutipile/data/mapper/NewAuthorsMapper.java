package com.yangy.mutipile.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.mutipile.data.dto.Authors;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;

/**
 * @Title: NewAuthorsMapper
 * @Package com.yangy.mutipile.data.mapper
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/29 15:07
 **/

@Mapper
public interface NewAuthorsMapper extends BaseMapper<Authors> {

    @Select("select * from authors")
    @ResultType(Authors.class)
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
    void streamExport(Authors example, ResultHandler<Authors> handler);

    /*    @ResultType(Authors.class)
        @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)*/
    //这里必须是void 否则无法识别mybatis是流式输出 从而无法写入数据
    void streamExportNew(Authors example, ResultHandler<Authors> handler);

}
