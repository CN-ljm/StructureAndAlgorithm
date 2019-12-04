package com.learn.algorithm;

/**
 * 冒泡排序算法
 */
public class BubbleSort {

    // 排序
    public static int sort(int[] array){
        int change = 0;
        if(array.length <= 1){
            return -1;
        }
        //优化一下，如果一旦有一轮没有发生交换说明该数组已经有序
        boolean flag = true;
        for (int i=0; i < array.length; i++){
            flag = true;
            for (int j=0; j < array.length - 1 - i; j++){
                int temp;
                if (array[j] > array[j+1]){
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    change ++;
                    flag = false;
                }
            }
            if (flag){
                break;
            }
        }
        return change;
    }


}
