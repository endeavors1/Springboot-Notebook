package com.xiaofu.limit.api;

import com.google.common.util.concurrent.RateLimiter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Title: RateLimitTest
 * @Package com.xiaofu.limit.api
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/4/19 10:26
 **/
public class RateLimitTest {

    //每秒10个
   private static RateLimiter rateLimit = RateLimiter.create(10);

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
/*        String aa = "12哈\r\n";
        //aa = aa.replace("\r\n","<br>");
        System.out.println(Charset.defaultCharset());
        System.out.println("11111:"+aa.length());
        System.out.println("22222:"+aa.getBytes("GBK").length);
        System.out.println("33333:"+aa.getBytes().length);
        String newaa = new String(aa.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1);
        System.out.println("44444:"+newaa.length());
        System.out.println("55555:"+newaa.getBytes().length);*/


        for (int i = 0; i < 100; i++) {
            Thread.sleep(80);
             System.out.println("-------time:" + new Date() + " -------ifLock:" + rateLimit.tryAcquire());
        }
    }

}
