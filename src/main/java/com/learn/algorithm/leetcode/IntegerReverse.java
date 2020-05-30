package com.learn.algorithm.leetcode;

/**
 * 整数反转
 */
public class IntegerReverse {

    public static int reverse(int src){
        //正负数判断
        boolean flag = String.valueOf(src).startsWith("-");
        //求绝对值
        int abs = Math.abs(src);
        int digit = String.valueOf(abs).length();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<digit; i++){
            int t = ((int) (abs/Math.pow(10,i)))%10;
            sb.append(t);
        }
        Integer mid = Integer.valueOf(sb.toString());
        return src = flag ? -mid : mid;

    }

}
