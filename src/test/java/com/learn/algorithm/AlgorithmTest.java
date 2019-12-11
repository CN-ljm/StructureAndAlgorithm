package com.learn.algorithm;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 算法测试S
 */
public class AlgorithmTest {


    @Test
    public void bubbleSortTest(){
        int[] array = createArray(80000, 10000000);
        //printlnArray(array);
        long start = System.currentTimeMillis();
        int changes = BubbleSort.sort(array);
        long end = System.currentTimeMillis();
        System.out.println("time:" + (end - start));
        System.out.println(changes);
        //printlnArray(array);
    }


    @Test
    public void selectSortTest(){
        int[] array = createArray(80000, 10000000);
        //printlnArray(array);
        long start = System.currentTimeMillis();
        int changes = SelectSort.sort(array);
        long end = System.currentTimeMillis();
        System.out.println("time:" + (end - start));
        System.out.println(changes);
        //printlnArray(array);
    }


    //打印数组
    private static void printlnArray(int[] array){
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + ",");
        }
        System.out.println();
    }


    // 产生随机数
    private static int[] createArray(int lenght, int bound){
        int[] array = new int[lenght];
        for (int i=0; i < lenght; i++){
            int num;
            if(bound != -1){
                num = ThreadLocalRandom.current().nextInt(bound);
            }else{
                num = ThreadLocalRandom.current().nextInt();
            }
            array[i] = num;
        }
        return array;
    }

}
