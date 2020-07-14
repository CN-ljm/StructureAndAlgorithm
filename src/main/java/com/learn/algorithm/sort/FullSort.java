package com.learn.algorithm.sort;

/**
 * 全排序
 */
public class FullSort {


    //每个数据的全排序就是依次将数组的每个元素放到头部，然后再对剩下的元素（可以看作是新数组）进行全排序
    //元素个数为1的数组的全排序就是数组本身
    public static void sort(int[] arr, int k, int l){
        if (k == l-1) {
            for (int i=0; i<l; i++){
                System.out.print(arr[i]);
            }
            System.out.println();
            return;
        }
        for (int i=k; i < l; i++){
            int temp = arr[k];
            arr[k] = arr[i];
            arr[i] = temp;
            sort(arr,k+1, l);
            //为了不影响顺序，每次把后边递归完成后的位置调换回来
            int temp2 = arr[k];
            arr[k] = arr[i];
            arr[i] = temp2;
        }
    }


}
