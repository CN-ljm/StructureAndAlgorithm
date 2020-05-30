package com.learn.algorithm.leetcode;

/**
 * 给定数组求最长上升子序列
 */
public class LongestRiseSubSequence {

    // 最长上升子序列长度
    private int len = 0;

    // 求解最长连续上升子序列
    public int solve(int[] arr){
        // {5,2,3,6,10,8,9,7,1};
        int length = arr.length;
        boolean found = false;
        for(int i=0; i<length; i++){
            // 定义一个变量存储以第i个数开始的最大上升子序列的长度
            int temp = 1;
            for (int j=i; j<length-1; j++){
                if (arr[j] < arr[j+1]){
                    temp++;
                }else{
                    //优化一下：如果某个数开始已经匹配到的长度 > 剩下数组的长度，则说明已经找到
                    if (temp > length-1-(j+1)){
                        found = true;
                    }
                    break;
                }
            }
            System.out.println("one");

            // 保存更长的子序列长度
            if (found){
                len = temp;
                break;
            }
        }

        return len;
    }

    // 求解最长子序列，不连续
    public int lengthOfLIS(int[] nums) {
        // {10,9,2,5,3,7,101,18,1}
        int length = nums.length;
        int begin = nums[0];
        for(int i=0; i<length; i++){
            //优化一：因为是上升序列，确定起点数后序列就已经确定,如果后一个起点数比前一个起点数大，那可以直接跳过这个起点数
            //直接将起点往后移，直到找到更小的起点数
            if (nums[i] > begin){
                continue;
            }
            begin = nums[i];
            //优化二：如果起点数到数组最后的长度比已找到的子序列长度短，就无需往后再找了
            if (length-1-i < len){
                break;
            }

            // 定义一个变量存储以第i个数开始的最大上升子序列的长度
            int l = 1;
            // 记录一个临时数
            int temp = nums[i];
            for (int j=i+1; j<length; j++){
                if (temp < nums[j]){
                    l++;
                    temp = nums[j];
                }
            }
            //记录一下执行次数
            System.out.println("one");

            // 保存更长的子序列长度
            if (l > len){
                len = l;
            }
        }

        return len;
    }

}
