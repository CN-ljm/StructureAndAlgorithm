package com.learn.algorithm.sort;

import java.util.Stack;

/**
 * 基数排序（桶排序）
 * 平均时间复杂度：O(logRB)，B是真数（0-9），R是基数（个、十、百）
 * 空间复杂度：O(n)
 * 稳定性：稳定
 */
public class RadixSort {

    public static int sort(int[] arr){
        int count = 0;
        //先来是十个桶
        //Stack<Integer> bucket = new Stack<>();
        int[][] bucket = new int[10][arr.length];
        int[] pointer = new int[10];
        //求数据最大值
        int max = arr[0];
        for (int i=0; i < arr.length; i++){
            if (arr[i] > max){
                max = arr[i];
            }
        }
        int digit = String.valueOf(max).length();
        //开始循环求余放入桶中
        for (int k=0; k<digit; k++){
            //将桶指针归零
            for(int f=0; f<10; f++){
                pointer[f] = 0;
            }
            //先放入桶中
            for (int i=0; i<arr.length; i++){
                int index = ((int)(arr[i]/Math.pow(10,k)))%10;
                bucket[index][pointer[index]] = arr[i];
                //这个桶下个数存放的位置
                pointer[index] +=1;
            }
            //然后在顺序取出
            int src = 0;
            for (int z=0; z<10; z++){
                //判断桶里面有数据
                if(pointer[z] == 0){
                    continue;
                }
                for (int a=0; a<pointer[z]; a++){
                    arr[src] = bucket[z][a];
                    src++;
                    count++;
                }
            }

        }
        return count;

    }


}
