package com.learn.algorithm;

/**
 * @author Created by liangjiaming on 2020/8/22
 * @title 算法复杂举例
 * @Desc
 */
public class ComplexityExample {

    /**
     * 指数阶时间复杂度。例如：2的n次方(n2)
     * @param n
     * @param count
     * @return
     */
    public int pow(int n, int count){
        if (n == 1) {
            for (int i=0; i<2; i++) {
                count++;
            }
            return count;
        }
        for (int i=0; i<2; i++) {
            count += pow(n-1, count);
        }
        return count;
    }

}
