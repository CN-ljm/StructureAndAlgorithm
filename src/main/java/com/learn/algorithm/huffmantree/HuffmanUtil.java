package com.learn.algorithm.huffmantree;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by liangjiaming on 2020/7/6
 * @title 哈夫曼树算法工具类
 * @Desc 哈夫曼压缩算法如下：
 * 1.统计好字符串个数，形成数组
 * 2.根据数组构造哈夫曼树
 * 3.形成哈夫曼编码表
 * 4.将原来字符串进行哈夫曼编码
 * 5.将编码后的字符串写入新文件
 */
public class HuffmanUtil {

    /**
     * 哈夫曼编码表
     */
    public static Map<Byte, String> huffmanCodingMap = null;

    public static void main(String[] args) {
        String aa = "abbccc";
        /*String result = enCodingStrWithHuffman(aa);

        System.out.println(result);*/

        // 000101111

        int i = Integer.valueOf("00010111", 2);
        System.out.println(i);

    }

    /**
     * 使用哈夫曼树对字符串进行编码
     * @param str
     * @return
     */
    public static String enCodingStrWithHuffman(String str) {
        // 构造哈夫曼树
        Node root = buildHuffmanTree(str);
        // 得到哈夫曼编码表
        buildHuffmanCodingMap(root);
        // 对字符串进行哈夫曼编码
        byte[] bytes = enCodingWithHuffman(str.getBytes(Charset.forName("UTF-8")));
        StringBuilder res = new StringBuilder();
        for (byte b: bytes) {
            res.append(b + ",");
        }


        return res.toString();
    }

    /**
     * 构建赫夫曼树
     * @param str 输入原文
     * @return
     */
    public static Node buildHuffmanTree(String str) {
        // 先得到字节集合
        Map<Byte, Integer> byteMap = new HashMap<>();
        byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
        for (byte b: bytes) {
            byteMap.put(b, byteMap.getOrDefault(b, 0) + 1);
        }

        // 将得到的字节统计结果组装成一个个哈夫曼树节点
        List<Node> nodeList = new ArrayList<>();
        for (Map.Entry<Byte, Integer> b: byteMap.entrySet()) {
            nodeList.add(new Node(b.getKey(), b.getValue()));
        }

        /**
         * 将每个节点看成一颗树，所有节点组成一个森林
         * 1.从森林中选出权值最小的两棵树组成一个新的树，这两棵树分别为左右节点（权值相同按字节大小从左到右排序），新树的根节点的权重为左右节点权重之和。
         * 2.从森立中删除这两颗树，将新树（根节点）加入到森林中。
         * 3.重复1、2两个步骤，直到森林中只剩一棵树
         */
        while (nodeList.size() > 1) {
            nodeList.sort(Node::compareTo);
            Node left = nodeList.get(0);
            Node right = nodeList.get(1);
            if (left.getWeight() == right.getWeight() && left.getB() > right.getB()) {
                left = nodeList.get(1);
                right = nodeList.get(0);
            }
            Node parent = new Node(left.getWeight() + right.getWeight());
            parent.leftNode = left;
            parent.rightNode = right;

            nodeList.remove(right);
            nodeList.remove(left);
            nodeList.add(parent);
        }

        return nodeList.get(0);
    }

    /**
     * 得到赫夫曼编码表
     * @param root 赫夫曼树根节点
     * @return
     */
    public static Map<Byte, String> buildHuffmanCodingMap(Node root) {
        huffmanCodingMap = new HashMap<>();
        /**
         * 从根节点到叶子节点路径：假设通往左节点路径用0表示，通往右节点路径用1表示。
         * 遍历所以叶子节点得到对应的路径编码
         */
        traverseHuffmanTree(huffmanCodingMap, "", root);

        return huffmanCodingMap;
    }

    /**
     * 使用哈夫曼编码表对源字节数组进行编码
     * @param src
     * @return
     */
    public static byte[] enCodingWithHuffman(byte[] src) {
        StringBuilder sb = new StringBuilder();
        for (byte b: src) {
            sb.append(huffmanCodingMap.get(b));
        }
        System.out.println(sb.toString());
        // 进行转码
        int len = (sb.length() + 7)/8;
        byte[] target = new byte[len];
        int index = 0;
        for (int i=0; i < sb.length(); i+=8) {
            if (i + 8 >= sb.length()) {
                target[index] = (byte)Integer.parseInt(sb.substring(i, sb.length()));
            } else {
                target[index] = (byte)Integer.parseInt(sb.substring(i, i+8));
            }
            index++;
        }

        return target;
    }

    // 遍历哈夫曼树
    private static void traverseHuffmanTree(Map<Byte, String> codingMap, String sb, Node node) {
        StringBuilder lcsb = new StringBuilder();
        lcsb.append(sb);
        // 向左
        if (node.leftNode != null) {
            traverseHuffmanTree(codingMap, lcsb + "0", node.leftNode);
        }
        // 向右
        if (node.rightNode != null) {
            traverseHuffmanTree(codingMap, lcsb + "1", node.rightNode);
        }
        // 说明是叶子节点了
        if (node.leftNode == null && node.rightNode == null) {
            codingMap.put(node.getB(), lcsb.toString());
        }
    }

}
