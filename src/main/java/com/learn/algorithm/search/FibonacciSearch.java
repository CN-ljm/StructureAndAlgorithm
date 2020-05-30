package com.learn.algorithm.search;

import java.util.Arrays;

/**
 * 斐波那契查找算法
 */
public class FibonacciSearch {


    public static int SIZE = 20;

    //构造一个斐波那契数列
    public static int[] fabonacci(){
        int[] f = new int[SIZE];
        f[0] = 1;
        f[1] = 1;
        for (int i=2; i<SIZE; i++){
            f[i] = f[i-1] + f[i-2];
        }
        return f;
    }

    public static int search(int[] arr, int target){
        int low = 0;
        int high = arr.length -1;
        int k = 0;

        //等到斐波那契数列,找到k值
        int[] f = fabonacci();
        while (high > f[k]-1){
            k++;
        }
        //创建临时数组，补齐到斐波那契数值长度
        int[] temp = Arrays.copyOf(arr, f[k]);
        for(int j=high+1; j<temp.length; j++){
            temp[j] = temp[high];
        }

        while (low <= high){
            //利用斐波那契数列特点寻找中间下标
            //分段原理：f[k]-1=(f[k-1]-1) + (f[k-2]-1) + 1
            //上面公式多出来的1是中间值
            int mid = low + f[k-1]-1;
            if (target < temp[mid]){
                high = mid-1;
                k--;
            }
            else if (target > temp[mid]){
                low = mid+1;
                k -=2;
            }
            else {
                if (mid <= high){
                    return mid;
                }else{
                    return high;
                }
            }
        }

        return -1;
    }

}
