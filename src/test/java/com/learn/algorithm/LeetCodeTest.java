package com.learn.algorithm;

import com.learn.algorithm.leetcode.AreaOfIsland;
import com.learn.algorithm.leetcode.IntegerReverse;
import com.learn.algorithm.leetcode.LongestRiseSubSequence;
import com.learn.algorithm.leetcode.TwoNumSum;
import org.junit.Test;

/**
 * LeetCode算法题测试
 */
public class LeetCodeTest {

    @Test
    public void twoNumSumTest(){
        int[] nums = new int[]{0,1,3,4,6,8,9,10,2,7,11};
        int target = 11;
        String res = TwoNumSum.find(nums, target);
        System.out.println(res);
    }

    @Test
    public void integetReverseTest(){
        int src = 12000;
        int res = IntegerReverse.reverse(src);
        System.out.println(res);
    }

    @Test
    public void longestRiseSequenceTest(){
        int[] arr = new int[]{10,9,2,5,3,7,101,18,1};
        LongestRiseSubSequence lrss = new LongestRiseSubSequence();
        int result = lrss.lengthOfLIS(arr);
        System.out.println(result);
    }

    @Test
    public void areaOfIslandTest(){
        int[][] arr = new int[][]{{0,0,1,0,0,0,0,1,0,0,0,0,0},
                                  {0,0,0,0,0,0,0,1,1,1,0,0,0},
                                  {0,1,1,0,1,0,0,0,0,0,0,0,0},
                                  {0,1,0,0,1,1,0,0,1,0,1,0,0},
                                  {0,1,0,0,1,1,0,0,1,1,1,0,0},
                                  {0,0,0,0,0,0,0,0,0,0,1,0,0},
                                  {0,0,0,0,0,0,0,1,1,1,0,0,0},
                                  {0,0,0,0,0,0,0,1,1,0,0,0,0}};
        AreaOfIsland island = new AreaOfIsland();
        int area = island.solve(arr);
        System.out.println(area);
    }

}
