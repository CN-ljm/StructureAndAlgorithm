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
    public static HuffmanHelper huffmanHelper = new HuffmanHelper();

    private static Map<Byte, String> huffmanCodingMap = null;

    public static void main(String[] args) {
        String aa = "everything is possible, Do you believe it";
//        String aa = "abbcccdddd";
        System.out.println("压缩前：" + aa);
        System.out.println("压缩前大小：" + aa.length());
        byte[] result = enCodingStrWithHuffman(aa);

        System.out.println("哈夫曼编码表：" + huffmanCodingMap.toString());

        System.out.println("压缩后大小：" + result.length);

        String res = deCodingStrWithHuffman(result);

        System.out.println("解压后：" + res);

    }

    /**
     * 解码哈夫曼编码字符串
     * @param huffmanByte
     * @return
     */
    public static String deCodingStrWithHuffman(byte[] huffmanByte) {

        byte[] bytes = deCodingWithHuffman(huffmanByte);

        return new String(bytes);
    }

    /**
     * 使用哈夫曼树对字符串进行编码
     * @param str
     * @return
     */
    public static byte[] enCodingStrWithHuffman(String str) {
        // 构造哈夫曼树
        Node root = buildHuffmanTree(str);
        // 得到哈夫曼编码表
        buildHuffmanCodingMap(root);
        // 对字符串进行哈夫曼编码
        byte[] bytes = enCodingWithHuffman(str.getBytes(Charset.forName("UTF-8")));

        return bytes;
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
        huffmanCodingMap = huffmanHelper.getHuffmanCodingMap();
        /**
         * 从根节点到叶子节点路径：假设通往左节点路径用0表示，通往右节点路径用1表示。
         * 遍历所以叶子节点得到对应的路径编码
         */
        traverseHuffmanTree(huffmanCodingMap, "", root);

        return huffmanCodingMap;
    }

    /**
     * 使用哈夫曼编码表对赫夫曼字节数组进行解码
     * @param huffmanBytes
     * @return
     */
    public static byte[] deCodingWithHuffman(byte[] huffmanBytes) {
        // 将哈夫曼编码表反转一下
        Map<String, Byte> huffmanDecodingMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry: huffmanCodingMap.entrySet()) {
            huffmanDecodingMap.put(entry.getValue(), entry.getKey());
        }

        StringBuilder sb = new StringBuilder();
        for (int i=0; i < huffmanBytes.length; i++) {
            // 赫夫曼编码得到二进制字符串最后一个编码可能不足八位，不需要用进行补位
            boolean flag = (i == huffmanBytes.length-1);
            if (flag) {
                String s = byteToBinaryString(false, huffmanBytes[i]);
                // 说明赫夫曼编码构造最后byte时，构造的二级制字符串是0开头，byte不能处理0开头，所以要记录
                if (huffmanHelper.getLastBitLen() > 0) {
                    StringBuilder tmp = new StringBuilder();
                    for (int k = 0; k < huffmanHelper.getLastBitLen(); k++) {
                        tmp.append("0");
                    }
                    sb.append(tmp.append(s).toString());
                } else {
                    sb.append(s);
                }
            } else {
                sb.append(byteToBinaryString(true, huffmanBytes[i]));
            }
        }
        System.out.println("哈夫曼编码：" + sb.toString());
        String hfmStr = sb.toString();

        //在这里进行解码
        List<Byte> byteList = new ArrayList<>();
        for (int i=0; i < hfmStr.length(); ) {
            // 用来计算配置的字符串的个数
            int count = 0;
            String s = null;
            while (true){
                s = hfmStr.substring(i, i + count);
                Byte b = huffmanDecodingMap.get(s);
                if (b != null) {
                    byteList.add(b);
                    i += count;
                    break;
                }
                count++;
            }
        }

        byte[] bytes = new byte[byteList.size()];
        int index = 0;
        for (Byte b: byteList) {
            bytes[index] = b;
            index ++;
        }

        return bytes;
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
        System.out.println("哈夫曼编码：" + sb.toString());
        // 进行转码
        int len = (sb.length() + 7)/8;
        byte[] target = new byte[len];
        int index = 0;
        for (int i=0; i < sb.length(); i+=8) {
            if (i + 8 > sb.length()) {
                String lastStr = sb.substring(i);
                target[index] = (byte)Integer.parseInt(lastStr, 2);
                // 计算0的个数，直到下一个是1
                int count = 0;
                boolean containOne = false;
                if (lastStr.startsWith("0")) {
                    String[] split = lastStr.split("");
                    for (String s: split) {
                        if ("0".equals(s)) {
                            count++;
                        }
                        else {
                            containOne = true;
                            break;
                        }
                    }
                    //全部是0的话，由于byte本身也包含一个0，所以解码的时候就少补一个
                    if (!containOne) {
                        count -= 1;
                    }
                    huffmanHelper.setLastBitLen(count);
                }

            } else {
                target[index] = (byte)Integer.parseInt(sb.substring(i, i+8), 2);
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

    // 字节数组转二进制字符串，为啥这么写，需要等待深入学习Java二进制运算的时候才能正确解答
    private static String byteToBinaryString(boolean flag, byte b) {
        int tmp = b;
        // 看下是否需要进行补位
        if (flag) {
            tmp |= 256;
        }
        String str = Integer.toBinaryString(tmp); //返回的是补码
        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            System.out.println(str);
            return str;
        }
    }

}
