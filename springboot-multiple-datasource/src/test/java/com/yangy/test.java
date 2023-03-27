package com.yangy;

import com.yangy.mutipile.data.core.DataSourceContextHolder;
import com.yangy.mutipile.data.dto.UserVo;
import com.yangy.mutipile.data.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Title: TestDb
 * @Package com.yangy
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/27 11:18
 **/
//@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void save(){
        UserVo userVo = new UserVo("zhangsan","张三","19888889999");
        System.out.println("保存db1结果:"+userMapper.insertUser(userVo));


        //切换db2
        DataSourceContextHolder.setDb("db2");
        System.out.println("保存db2结果:"+userMapper.insertUser(userVo));
    }

}
