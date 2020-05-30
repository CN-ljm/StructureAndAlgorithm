package com.learn.algorithm.leetcode;

/**
 * 求面积最大的岛屿
 * 题目：给定一个包含了一些 0 和 1的非空二维数组 grid , 一个 岛屿 是由四个方向 (水平或垂直) 的 1 (代表土地) 构成的组合。你可以假设二维矩阵的四个边缘都被水包围着。
 * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为0。)
 *
 * 示例：
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 *
 * 对于上面这个给定矩阵应返回 6。注意答案不应该是11，因为岛屿只能包含水平或垂直的四个方向的‘1’。
 *
 */
public class AreaOfIsland {

    /**
     * 给定数组，找到结果
     * 分析：找到数组中水平和垂直方向相连再一起的最多的1的个数。
     * @param arr 数组
     * @return 最大面积
     */
    public int solve(int[][] arr){
        // 最大面积
        int maxArea = 0;
        //以二维数组中的每个1为起点，找到水平和垂直方向相连的1的个数
        for (int i=0; i<arr.length; i++){
            for (int j=0; j<arr[0].length; j++){
                //如果是0则跳过
                if(arr[i][j] == 0){
                    continue;
                }
                //是1则开始算面积
                //定义一个临时变量来算小岛面积
                Integer area = 0;
                int[][] temp = new int[arr.length][arr[0].length];
                area = calculateArea(arr,i,j,area,temp);
                //System.out.println(area);
                //找到面积更大的则替换面积小的
                if (area > maxArea){
                    maxArea = area;
                }
            }

        }

        return maxArea;
    }

    /**
     * 以某个1为起点统计1的个数
     * @param arr 原二维数组
     * @param i 起点行
     * @param j 起点列
     * @param area 累计个数
     * @param temp 临时二维数组存放已经计算过的1
     */
    private int calculateArea(int[][] arr, int i, int j, int area ,int[][] temp){
        //数组越界了也直接返回
        if (i<0 || i>arr.length-1 || j<0 || j>arr[0].length-1){
            return 0;
        }
        //如果是0的话就无需往下再找了,
        if (arr[i][j] == 0){
            return 0;
        }
        //如果当前点已经被算上了，不能重复再算
        if (temp[i][j] == 1){
            return 0;
        }
        //加入到临时数组中，方便判断是否已经加上
        temp[i][j]=1;
        //System.out.println(i + "," +j);
        //面积加1
        area += 1;
        //采用递归的方式按上下左右的策略开始寻找1，依次递归
        area += calculateArea(arr,i-1,j,0,temp);
        area += calculateArea(arr,i+1,j,0,temp);
        area += calculateArea(arr,i,j-1,0,temp);
        area += calculateArea(arr,i,j+1,0,temp);
        return area;
    }
}
