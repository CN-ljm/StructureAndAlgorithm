package com.learn.algorithm.huffmantree;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by liangjiaming on 2020/7/6
 * @title 赫夫曼树算法工具类
 * @Desc 赫夫曼压缩算法如下：
 * 1.统计好字符串个数，形成数组
 * 2.根据数组构造赫夫曼树
 * 3.形成赫夫曼编码表
 * 4.将原来字符串进行赫夫曼编码
 * 5.将编码后的字符串写入新文件
 */
public class HuffmanUtil {

    public static Map<Byte, Integer> byteMap = null;

    public static void main(String[] args) {
        String aa = "abva aav v ";
        Map<Byte, Integer> byteCountMap = getByteCountMap(aa);
        System.out.println(byteCountMap.toString());

    }



    /**
     * @param str 原文
     * @return
     */
    public static Map<Byte, Integer> getByteCountMap(String str) {
        byteMap = new HashMap<>();
        byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
        for (byte b: bytes) {
            byteMap.put(b, byteMap.getOrDefault(b, 0) + 1);
        }
        return byteMap;
    }

}
