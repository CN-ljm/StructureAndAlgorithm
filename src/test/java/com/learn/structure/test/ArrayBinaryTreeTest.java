package com.learn.structure.test;

import com.learn.structure.tree.ArrayBinaryTree;
import org.junit.Test;

/**
 * 顺序存储二叉树测试
 */
public class ArrayBinaryTreeTest {

    @Test
    public void arrayTreeTest(){
        int[] arr = new int[]{1,2,3,4,5,6,7};

        System.out.println("前序遍历：");
        ArrayBinaryTree.preTraversal(arr,0);
        System.out.println("\n中序遍历：");
        ArrayBinaryTree.midTraversal(arr,0);
        System.out.println("\n后序遍历：");
        ArrayBinaryTree.postTraversal(arr,0);
    }

}
