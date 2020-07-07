package com.learn.algorithm.huffmantree;

/**
 * @author Created by liangjiaming on 2020/7/7
 * @title 赫夫曼树节点
 * @Desc
 */
public class Node implements Comparable<Node> {

    /**
     * 字节码
     */
    private byte b;

    /**
     * 权重
     */
    private int weight;

    /**
     * 左节点
     */
    public Node leftNode;

    /**
     * 右节点
     */
    public Node rightNode;

    public Node() {
    }

    public Node(int weight) {
        this.weight = weight;
    }

    public Node(byte b, int weight) {
        this.weight = weight;
        this.b = b;
    }

    public int getWeight() {
        return weight;
    }

    public byte getB() {
        return b;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "b=" + b +
                ", weight=" + weight +
                '}';
    }
}
