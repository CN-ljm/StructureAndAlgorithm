package com.learn.structure.tree;

/**
 * 遍历二叉树（前序、种序、后序）
 */
public class BinaryTree {

    //前序遍历
    public static void preTraversal(Node node){
        if (node == null){
            return;
        }

        System.out.println(node.getData().toString());

        preTraversal(node.getLeft());
        preTraversal(node.getRight());

    }

    //中序遍历
    public static void midTraversal(Node node){
        if (node == null){
            return;
        }

        midTraversal(node.getLeft());
        System.out.println(node.getData().toString());
        midTraversal(node.getRight());
    }

    //后序遍历
    public static void postTraversal(Node node){
        if (node == null){
            return;
        }

        postTraversal(node.getLeft());
        postTraversal(node.getRight());
        System.out.println(node.getData().toString());
    }

}
