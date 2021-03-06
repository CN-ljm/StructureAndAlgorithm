package com.learn.algorithm.sort;

/**
 * 插值排序算法
 * 平均时间复杂度：O(n*n)
 * 空间复杂度：O(1)
 * 稳定性：稳定
 */
public class insertSort {

    /**
     * 插值查找算法
     * @param array
     * @return
     */
    public static int sort(int[] array){
        int change = 0;
        if (array.length <= 1){
            return change;
        }

        for(int i=1; i < array.length; i++){
            int temp = 0;
            for (int j=i-1; j>=0; j--){
                if (array[j] > array[j+1]){
                    temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                    change ++;
                }else{
                    break;
                }
            }
        }

        return change;
    }

}
