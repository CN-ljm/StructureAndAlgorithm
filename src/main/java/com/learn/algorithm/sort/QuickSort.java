package com.learn.algorithm.sort;
/**
 * 快速排序
 * 平均时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 * 稳定性：不稳定
 */
public class QuickSort {

    private static int count = 0;

    /**
     * 排序
     * @param array
     * @return
     */
    public static int sort(int[] array){
        fastSort(array,0,array.length-1);
        return count;
    }

    private static void fastSort(int[] array, int left, int right){
        if (left > right){
            return;
        }
        int i = left;
        int j = right;
        // 找一个参考值(这里取中间值)半角
        int pivot = array[(left + right)/2];
        while (i < j){
            //从左边开始找一个大于或等于参考值的数
            while (array[i] < pivot){
                i++;
            }
            //从右边开始找一个小于等于参考值的数
            while (array[j] > pivot){
                j--;
            }
            //是否符合交换条件
            if (i >= j){
                break;
            }
            //交换
            count++;
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            //如果恰好等于中间值,需要让程序继续往下走
            if(array[i] == pivot){
                j--;
            }
            if (array[j] == pivot){
                i++;
            }
        }
//        printlnArray(array);
        //向左递归
        fastSort(array,left,i-1);
        //向右递归
        fastSort(array,j+1,right);
    }

    //打印数组
    private static void printlnArray(int[] array){
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + ",");
        }
        System.out.println();
    }
}
