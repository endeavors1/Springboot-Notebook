package com.xiaofu.limit.api;

/**
 * @Title: LeakBucketLimit
 * @Package com.xiaofu.limit.api
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/4/18 18:29
 **/
public class LeakBucketLimit {


    /**
     * @Description: _capacity:桶能承载的最大请求数，超过这个数就开始限流  _rate:速率 每秒几个  越大代表qps越高
     * @Author: yangy
     * @Date: 2023/4/18 18:24
     * @Params:[_capacity, _rate]
     * @Returns:* @return: null
     **/
    public LeakBucketLimit(int _capacity,int _rate){
        this.capacity = _capacity;
        this.rate = _rate;
    }

    private int capacity;//容量
    private int rate;//令牌放入速度
    private static long lastTime = getNowTime();//起始时间
    private static int cur = 0;//当前桶中累计的请求数

    //令牌桶算法 按照一定速率rate往桶内放入令牌 请求到来时 先从桶内获取令牌 如果能获取到 才能执行
    public synchronized boolean tryAcquire(){
        long now = getNowTime();
        if(cur == 0){
            lastTime = now;
            cur = 1;
            return true;

        }

        cur = (int) Math.max(0,cur - (now - lastTime)/1000 * rate);//请求处理完一个就往外漏（匀速漏出被处理的请求）
        System.out.println("now - timeStamp:"+(now - lastTime)+"-----cur:"+cur);
        lastTime = now;
        if(cur < capacity){
            //桶还未满 继续加 可以处理请求
            cur++;
            return true;
        }else{
            return false;
        }
    }

    public static Long getNowTime(){
        return System.currentTimeMillis();
    }

}
