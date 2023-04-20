package com.xiaofu.doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringbootDatabaseDocApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SpringbootDatabaseDocApplication.class, args);

        ApplicationContext context = SpringApplication.run(SpringbootDatabaseDocApplication.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        System.out.println("mblog started at http://localhost:" + serverPort);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        //打印spring容器当中所有bean的bd
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

}
