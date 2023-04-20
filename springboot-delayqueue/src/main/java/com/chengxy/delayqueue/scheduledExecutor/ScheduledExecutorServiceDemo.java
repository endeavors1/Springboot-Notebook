package com.chengxy.delayqueue.scheduledExecutor;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaofu
 * @Description:
 */
public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) {


        Map<String, Long> map = new HashMap<>();
        map.put("key-5", System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5));
        map.put("key-10", System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));
        map.put("key-20", System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(20));


        //ScheduledExecutorServiceTest();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            //System.out.println("nowMap:"+map);
            //自旋 不断轮询
            if (!map.isEmpty()) {
                System.out.println("继续轮询");
                Iterator<Map.Entry<String, Long>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Long> entry = iterator.next();
                    String s = entry.getKey();
                    Long delayTime = map.get(s);
                    long now = System.currentTimeMillis();
                    if (now < delayTime) {
                        System.out.println(s + " 还未过期 继续执行;" + "time:" + sdf.format(now));
                    } else {
                        System.out.println(s + " 过期了 不再执行 删除任务" + "time:" + sdf.format(now));
                        iterator.remove();
                        System.out.println("-------map:" + map);
                    }
                }
            } else {
                //map empty 关掉轮询
                executor.shutdown();
                System.out.println("关闭轮询");
            }

        }, 0, 2, TimeUnit.SECONDS);


        System.out.println("-----------------------------------------------------");


    }

}
