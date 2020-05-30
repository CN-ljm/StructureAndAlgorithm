package com.learn.algorithm.leetcode;

/**
 * 求两数之和
 * 题目：给定一个整数数组nums和一个目标值target,找出数据中和为目标值的那个两个整数，并返回下标
 */
public class TwoNumSum {

    //找出两数下标
    public static String find(int[] nums, int target){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<nums.length-1; i++){
            for (int j=i+1; j<nums.length; j++){
                if (nums[i] + nums[j] == target){
                   sb.append(String.format("[%s,%s] ",i,j));
                }
            }
        }
        return sb.toString();
    }

}
