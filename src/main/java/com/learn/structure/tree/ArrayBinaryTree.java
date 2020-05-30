package com.learn.structure.tree;

/**
 * 顺序存储二叉树（数组），完全二叉树与数据可以相互转换
 */
public class ArrayBinaryTree {

    //前序遍历顺序二叉树
    public static void preTraversal(int[] arr, int index){
        if (index > arr.length-1){
            return;
        }
        System.out.print(arr[index]+",");
        //左节点
        preTraversal(arr, 2*index+1);
        //右节点
        preTraversal(arr, 2*index+2);
    }

    //中序遍历顺序二叉树
    public static void midTraversal(int[] arr, int index){
        if (index > arr.length-1){
            return;
        }
        //左节点
        midTraversal(arr, 2*index+1);
        System.out.print(arr[index]+",");
        //右节点
        midTraversal(arr, 2*index+2);

    }

    //后序遍历顺序二叉树
    public static void postTraversal(int[] arr, int index){
        if (index > arr.length-1){
            return;
        }
        //左节点
        postTraversal(arr, 2*index+1);
        //右节点
        postTraversal(arr, 2*index+2);
        System.out.print(arr[index]+",");

    }
}
