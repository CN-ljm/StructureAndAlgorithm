package com.learn.structure.tree;

/**
 * 树节点对象
 * @param <T>
 */
public class Node<T> {

    //数据区
    private T data;

    //指针区
    private Node<T> left;

    private Node<T> right;

    public Node(T data) {
        this.data = data;
    }


    public T getData() {
        return data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}
