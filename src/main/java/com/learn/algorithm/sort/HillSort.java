package com.learn.algorithm.sort;

import java.awt.image.AreaAveragingScaleFilter;

/**
 * 希尔排序（按一定增量进行分组，然后对每组进行插入排序）
 * 平均时间复杂度：O(nlogn)
 * 空间复杂度：O（1）
 * 稳定性：不稳定
 */
public class HillSort {

    //排序--(交换)
    public static int sort(int[] array){
        int change = 0;
        if(array.length <= 1){
            return 0;
        }

        int step = array.length/2;
        //按步长分组做插值排序
        for (int i=step; i > 0; i/=2){
            int temp = 0;
            for (int j=i; j < array.length; j++){
                for (int k= j-i; k >= 0; k=k-i){
                    if (array[k] > array[k+i]){
                        temp = array[k];
                        array[k] = array[k+i];
                        array[k+i] = temp;
                        change++;
                    }
                }

            }
//            printlnArray(array);
        }
        return change;
    }

    //排序--(移位)
    public static int sort2(int[] array){
        int change = 0;
        if(array.length <= 1){
            return 0;
        }

        int step = array.length/2;
        //按步长分组做插值排序
        for (int i=step; i > 0; i/=2){
            int temp = 0;
            for (int j=i; j < array.length; j++){
                int k = j;
                temp =array[k];
                if (temp < array[k-i]){
                    while (k-i >=0 && temp < array[k-i]){
                        //后移
                        array[k] = array[k-i];
                        k -= i;
                    }
                    array[k] = temp;
                }

            }
//            printlnArray(array);
        }
        return change;
    }



    //打印数组
    private static void printlnArray(int[] array){
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + ",");
        }
        System.out.println();
    }

}
