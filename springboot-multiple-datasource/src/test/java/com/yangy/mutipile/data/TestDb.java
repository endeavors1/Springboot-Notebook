package com.yangy.mutipile.data;

import com.yangy.mutipile.data.DemoApplication;
import com.yangy.mutipile.data.aspect.TargetDataSource;
import com.yangy.mutipile.data.core.DataSourceContextHolder;
import com.yangy.mutipile.data.dto.UserVo;
import com.yangy.mutipile.data.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Title: TestDb
 * @Package com.yangy
 * @Description: 测试数据源切换 两种方式
 * 1.通过DataSourceContextHolder手动切换
 * 2.通过注解 aop识别注解 做相关数据源切换
 * @Author: yangy
 * @Date: 2023/3/27 11:18
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDb {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void save() {
        UserVo userVo = new UserVo("zhangsan", "张三", "19888889999");
        System.out.println("保存db1结果:" + userMapper.insertUser(userVo));


        //切换db2
        DataSourceContextHolder.setDb("db2");
        System.out.println("保存db2结果:" + userMapper.insertUser(userVo));
        //这里切换完需要清除  否则无法恢复默认
        DataSourceContextHolder.clearDB();
    }

    @Test
    //@TargetDataSource("db1")
    public void searchOne() {
        System.out.println("--------db1--------");
        System.out.println(userMapper.searchUserList("wb20210729001"));
    }


    @Test
    @TargetDataSource("db2")
    public void searchTwo() {
        System.out.println("--------db2--------");
        System.out.println(userMapper.searchUserList("ITSMDEMO"));
    }


    @Test
    //@TargetDataSource("db1")
    public void searchThree() {
        System.out.println("--------db1--------");
        System.out.println(userMapper.searchUserList("wb20210729001"));
    }

}
