package com.learn.algorithm;


/**
 * 归并排序
 */
public class MergeSort {

    public static int count = 0;

    // 排序算法
    public static int sort(int[] array){
        mergeSort(array,0,array.length-1);
        return count;
    }

    private static void mergeSort(int[] array, int left, int right){
        int[] temp = new int[array.length];
        int mid = (left + right)/2;
        if(left < right){
            //向左
            mergeSort(array,left,mid);
            //向右
            mergeSort(array,mid+1,right);
            //合并
            mergeArray(array,left,right,mid,temp);
        }

    }

    private static void mergeArray(int[] array, int left, int right, int mid, int[] temp) {
        count++;
        int i = left;
        int j = mid + 1;
        int index = 0;
        //1.把左右两边有序数组合并到临时数组中
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[index] = array[i];
                i++;
                index++;
            } else {
                temp[index] = array[j];
                j++;
                index++;
            }
        }
        //2.把剩下一边的数组合并到临时数组中
        while (i <= mid) {
            temp[index] = array[i];
            index++;
            i++;
        }
        while (j <= right) {
            temp[index] = array[j];
            index++;
            j++;
        }

        //3.把临时数组中数据复制到原数组中
        int k = 0;
        int tl = left;
        while (k < index){
            array[tl] = temp[k];
            tl++;
            k++;
        }

    }


    //打印数组
    private static void printlnArray(int[] array){
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + ",");
        }
        System.out.println();
    }

}
