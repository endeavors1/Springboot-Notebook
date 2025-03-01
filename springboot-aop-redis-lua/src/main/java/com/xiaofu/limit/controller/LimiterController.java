package com.xiaofu.limit.controller;

import com.xiaofu.limit.api.LeakBucketLimit;
import com.xiaofu.limit.api.Limit;
import com.xiaofu.limit.api.TokenLimit;
import com.xiaofu.limit.enmu.LimitType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: xiaofu
 * @Description:
 */
@RestController
public class LimiterController {

    private static final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_2 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_3 = new AtomicInteger();

    /**
     * @author xiaofu
     * @description
     * @date 2020/4/8 13:42
     */
    @Limit(key = "limitTest", period = 10, count = 3)
    @GetMapping("/limitTest1")
    public int testLimiter1() {

        return ATOMIC_INTEGER_1.incrementAndGet();
    }

    /**
     * @author xiaofu
     * @description
     * @date 2020/4/8 13:42
     */
    @Limit(key = "customer_limit_test", period = 10, count = 3, limitType = LimitType.CUSTOMER)
    @GetMapping("/limitTest2")
    public int testLimiter2() {

        return ATOMIC_INTEGER_2.incrementAndGet();
    }

    /**
     * @author xiaofu
     * @description
     * @date 2020/4/8 13:42
     */
    @Limit(name = "ipkey10s3次",key = "ip_limit_test", period = 10, count = 3, limitType = LimitType.IP)
    @GetMapping("/limitTest3")
    public int testLimiter3() {

        return ATOMIC_INTEGER_3.incrementAndGet();
    }


    @GetMapping("/limitTest4")
    public int tokenLimit() {
        TokenLimit tokenLimit = new TokenLimit(10,1);
        if(tokenLimit.tryAcquire()){
            System.out.println("获取到token");
            System.out.println("执行逻辑");
        }else{
            System.out.println("获取token令牌失败!!");
        }
        return ATOMIC_INTEGER_3.incrementAndGet();
    }


    @GetMapping("/limitTest5")
    public int leakBucketLimit() {
        LeakBucketLimit leakBucketLimit = new LeakBucketLimit(10,1);
        if(leakBucketLimit.tryAcquire()){
            System.out.println("获取到token");
            System.out.println("执行逻辑");
        }else{
            System.out.println("获取token令牌失败!!");
        }
        return ATOMIC_INTEGER_3.incrementAndGet();
    }

}