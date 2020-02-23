package com.learn.algorithm.leetcode;

/**
 * 整数反转
 */
public class IntergetReverse {

    public static void reverse(int src){
        //正负数判断
        boolean flag = String.valueOf(src).startsWith("-");
        //求绝对值
        int abs = Math.abs(src);
        int digit = String.valueOf(abs).length();
        StringBuilder sb = new StringBuilder();
        for (int i=digit-1; i>=0; i--){
            int t = ((int) (src/Math.pow(10,i)))%10;
            sb.append(t);
        }
        Integer mid = Integer.valueOf(sb.toString());
        src = flag ? -mid : mid;

    }

}
