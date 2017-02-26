package com.b2c.util;

import java.util.Random;

public class IDUtils {
    public static String genImageName(){
        long time = System.nanoTime();
        Random random = new Random();
        int end3 = random.nextInt(999);
        //不足三位的用0补齐
        return time + String.format("%03d", end3);
    }
    
    /**
      * 商品id生成
    */
    public static long genItemId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }
}
