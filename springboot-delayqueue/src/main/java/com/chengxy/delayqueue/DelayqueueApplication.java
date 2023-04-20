package com.chengxy.delayqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableScheduling
@SpringBootApplication
public class DelayqueueApplication {


    public volatile static boolean flag = true;


    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DelayqueueApplication.class, args);

/*
        //使用interrupt中断线程 判断当前线程是否是中断状态 是就终止线程
        Thread thread = new Thread(()-> {
            while (true){
                if(!Thread.currentThread().isInterrupted()){
                    System.out.println("线程继续执行");
                }else{
                    System.out.println("线程中断");
                    break;
                }
            }
        });

        thread.start();
        Thread.sleep(5000);//主线程睡5s后 停止线程
        thread.interrupt();



        //通过判断标志位中断（退出循环）
        new Thread(()-> {
            while (flag){
                try {
                    System.out.println("线程继续执行");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(3000L);
        flag = false;
        System.out.println("线程结束");
*/


    }

}
