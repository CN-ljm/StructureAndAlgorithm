package com.learn.algorithm.sort;

/**
 * 选择排序法
 * 平均时间复杂度：O(n*n)
 * 空间复杂度：O(1)
 * 稳定性：不稳定
 */
public class SelectSort {

    /**
     * 开始排序
     * @param array
     */
    public static int sort(int[] array){
        if (array.length == 0){
            return -1;
        }
        int change = 0;
        for (int i=0; i < array.length-1; i++){
            int temp;
            for (int j=i+1; j < array.length; j++){
                if (array[i] > array[j]){
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    change ++;
                }
            }
        }
        return change;
    }

}
