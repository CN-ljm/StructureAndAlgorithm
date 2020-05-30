package com.learn.algorithm;

import com.learn.algorithm.search.BinarySearch;
import com.learn.algorithm.search.FibonacciSearch;
import com.learn.algorithm.search.InsertValueSearch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找算法测试类
 */
public class SearchTest {

    @Test
    public void binarySearchTest(){
        int target = 20;
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,17,19,21,23};
        List<Integer> res = new ArrayList<>();
        res = BinarySearch.search(arr, target, 0, arr.length - 1, res);
        System.out.println("找到下标是：" + res.toString());
    }

    @Test
    public void insertValueSearch(){
        int target = 16;
        int[] arr = new int[]{1,3,4,5,6,7,8,9,10,11,12,13,14,15,17,19,21,23};
        List<Integer> res = new ArrayList<>();
        res = InsertValueSearch.search(arr, target, 0, arr.length - 1, res);
        System.out.println("找到下标是：" + res.toString());
    }

    @Test
    public void fabonacciSearch(){
        int target = 2;
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
        int index = FibonacciSearch.search(arr, target);
        System.out.println(index);
    }

}
