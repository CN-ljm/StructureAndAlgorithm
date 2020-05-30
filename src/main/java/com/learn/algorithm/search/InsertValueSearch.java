package com.learn.algorithm.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 插值查找算法（类似二分查找，只是优化中间参考值，更适合部长固定的数组）
 */
public class InsertValueSearch {

    /**
     * 使用递归算法
     * @param arr 必须是有序数组
     * @param left
     * @param right
     * @return
     */
    public static List<Integer> search(int[] arr, int target, int left, int right, List<Integer> res){
        System.out.println("haha");
        // 没找到
        if(left > right || arr[0] > target || arr[arr.length-1] < target){
            return new ArrayList<>();
        }
        if(left == right){
            return arr[left] == target ? Arrays.asList(left) : Arrays.asList();

        }
        //int mid = (left + right)/2;
        int mid = left + (right - left) * (target - arr[left])/(arr[right] - arr[left]);
        //向左递归
        if (arr[mid] > target){
            return search(arr,target,left-1,mid,res);
        }
        //向右递归
        else if (arr[mid] < target){
            return search(arr,target,mid+1,right,res);
        }
        //找到了
        else {
            // 向左、向右遍历看下有没有
            res.add(mid);
            int goLeft = mid-1;
            int goRight = mid +1;
            while (goLeft >=0 && arr[goLeft] == target){
                res.add(goLeft);
                goLeft--;
            }
            while (goRight < arr.length && arr[goRight] == target){
                res.add(goRight);
                goRight++;
            }
            return res;
        }
    }

}
