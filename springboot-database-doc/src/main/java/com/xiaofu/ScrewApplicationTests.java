package com.xiaofu;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.xiaofu.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: yangy
 * @Description:测试类 生成数据库表word文档
 */
@SpringBootTest(classes = SpringbootDatasourcesRollbackApplication.class)
@RunWith(SpringRunner.class)
public class ScrewApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserService userService;


    @Test
    public void contextLoads() {
        DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);
        // 生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir("D:/")
                // 打开目录
                .openOutputDir(false)
                // 文件类型
                .fileType(EngineFileType.WORD)  //可生成word html md文件
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker).build();
        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        Configuration config = Configuration.builder()
                .version("1.0.3")
                .description("生成文档信息描述")
                .dataSource(dataSourceMysql)
                .engineConfig(engineConfig)
                .produceConfig(getProcessConfig())
                .build();
        // 执行生成
        new DocumentationExecute(config).execute();
    }

    /**
     * 配置想要生成的表+ 配置想要忽略的表
     *
     * @return 生成表配置
     */
    public static ProcessConfig getProcessConfig() {
        // 忽略表名
        List<String> ignoreTableName = Arrays.asList("a", "test_group");
        // 忽略表前缀，如忽略a开头的数据库表
        List<String> ignorePrefix = Arrays.asList("a", "t");
        // 忽略表后缀
        List<String> ignoreSuffix = Arrays.asList("_test", "czb_");

        List<String> designTablePrefix = Arrays.asList("flowtask", "flow_tagent");
        return ProcessConfig.builder()
                //根据名称指定表生成
                //.designatedTableName(Arrays.asList("flowtask"))
                //根据表前缀生成
                .designatedTablePrefix(designTablePrefix)
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
    }


/*    @Test
    public void test(){
        userService.searchUser();
        System.out.println("test");
    }*/
}
