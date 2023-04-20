package com.chengxy.delayqueue.delayQueue;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaofu
 * @Description:
 */
public class Order implements Delayed {

    /**
     * 延迟时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private long time;

    /**
     * 订单号
     */
    String name;

    private long delayTime;

    public Order(String name, long time, TimeUnit unit) {
        this.name = name;
        this.delayTime = unit.toMillis(time);
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Order Order = (Order) o;
        long diff = this.time - Order.time;
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }


    @Override
    public String toString() {
        return "[name="+this.name+",delayTime:"+this.time+",delayTime:"+getDelayTime()+"]";
    }


    public String getDelayTime(){
        return String.valueOf(this.delayTime);
    }
}