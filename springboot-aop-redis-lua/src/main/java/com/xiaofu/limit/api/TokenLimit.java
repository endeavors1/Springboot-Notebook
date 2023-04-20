package com.xiaofu.limit.api;

/**
 * @Title: TokenLimit
 * @Package com.xiaofu.limit.api
 * @Description: 令牌桶算法 非线性的 取令牌时没有限制 可以一次性处理高峰请求
 * @Author: yangy
 * @Date: 2023/4/18 18:14
 **/
public class TokenLimit {

    /**
     * @Description: _capacity:容量  _rate:速率 每秒几个  越大代表qps越高
     * @Author: yangy
     * @Date: 2023/4/18 18:24
     * @Params:[_capacity, _rate]
     * @Returns:* @return: null
     **/
    public TokenLimit(int _capacity,int _rate){
        capacity = _capacity;
        rate = _rate;
    }

    private int capacity;//容量
    private int rate;//令牌放入速度
    private static long timeStamp = getNowTime();//起始时间
    private static int tokens = 0;//当前令牌数量

    //令牌桶算法 按照一定速率rate往桶内放入令牌 请求到来时 先从桶内获取令牌 如果能获取到 才能执行
    public synchronized boolean tryAcquire(){
        long now = getNowTime();
        tokens = (int) Math.min(capacity,tokens + (now - timeStamp)/1000 * rate);//当前桶里令牌数量 1s一个
        System.out.println("now - timeStamp:"+(now - timeStamp)+"-----tokens:"+tokens);
        timeStamp = now;
        if(tokens < 1){
            //没有令牌 拒绝
            return false;
        }else{
            //还有令牌
            tokens -= 1;
            return true;
        }
    }

    public static Long getNowTime(){
        return System.currentTimeMillis();
    }

}
