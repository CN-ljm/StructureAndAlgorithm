package com.learn.algorithm.huffmantree;

import com.learn.help.utils.FileUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
    public static HuffmanHelper huffmanHelper = null;

    private static Map<Byte, String> huffmanCodingMap = null;

    private static Map<String, Byte> huffmanDecodingMap = null;

    // 一次性读取文件大小先来 20M
    private static int ONCE_READ_SIZE = 1024 * 1024 * 10;

    public static void main(String[] args) {
//        huffmanZip("E:/test/hello.txt", "E:/test/hello.zip");
//        System.out.println(huffmanCodingMap.toString());
        huffmanUnzip("E:/test/hello.zip", "E:/test/hello-src.txt");
    }

    /**
     * 赫夫曼压缩
     * @param srcPath 源路径
     * @param tarPath 目标路径
     */
    public static void huffmanZip(String srcPath, String tarPath) {
        // 先读完整个文件得到文件对应的赫夫曼编码表
        beforeEnCodingByteWithHuffman(srcPath);

        // 进行赫夫曼压缩
        File inFile = new File(srcPath);
        if (!inFile.exists()) {
            return;
        }
        //先建好文件
        String prefix = tarPath.substring(0, tarPath.lastIndexOf("/"));
        String[] tmp = tarPath.split("/");
        if (tmp.length < 2) {
            throw new RuntimeException("文件路径错误");
        }
        File file = new File(prefix);
        if (!file.exists()) {
            file.mkdirs();
        }
        File outFile = new File(tarPath);
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            fin = new FileInputStream(inFile);
            fout = new FileOutputStream(outFile);
            inChannel = fin.getChannel();
            outChannel = fout.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(ONCE_READ_SIZE);
            while (inChannel.read(bb) > 0) {
                // 得到对应的赫夫曼编码
                //这样分批编码的话，最后一个不满八位怎么算，，，计入下一次来计算
                bb.flip();
                byte[] srcByte = new byte[bb.limit()];
                bb.get(srcByte);
                byte[] bytes = enCodingByteWithHuffman(srcByte);
                ByteBuffer wrap = ByteBuffer.wrap(bytes);
                outChannel.write(wrap);

                bb.clear();
            }
            // 处理最后的不满8位的字符串
            String lastStr = huffmanHelper.getLastByteStr();
            if (lastStr != null && !"".equals(lastStr)) {
                //判断是不是 0 开头
                byte lastByte = (byte) Integer.parseInt(lastStr, 2);
                if (lastStr.startsWith("0")) {
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
                        huffmanHelper.setFillZeroCount(count);
                    }
                }
                outChannel.write(ByteBuffer.wrap(new byte[]{lastByte}));
            }


            outChannel.close();
            inChannel.close();
            fout.close();
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(outChannel);
            close(inChannel);
            close(fout);
            close(fin);
        }

        /*byte[] bytes = FileUtil.readFileWithNIO(srcPath);
        byte[] zipBytes = enCodingByteWithHuffman(bytes);
        FileUtil.writeFileWithNIO(zipBytes, tarPath);*/

        // 将赫夫曼编码表写进压缩目录中
        saveHuffmanCodingMap(tarPath + ".huf");
    }

    /**
     * 赫夫曼解压
     * @param srcPath 源路径
     * @param tarPath 目标路径
     */
    public static void huffmanUnzip(String srcPath, String tarPath) {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return;
        }
        // 读取赫夫曼编码表
        huffmanHelper = readHuffmanCodingMap(srcPath + ".huf");
        huffmanCodingMap = huffmanHelper.getHuffmanCodingMap();
        // 将哈夫曼编码表反转一下
        huffmanDecodingMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry: huffmanCodingMap.entrySet()) {
            huffmanDecodingMap.put(entry.getValue(), entry.getKey());
        }

        //先建好文件
        String prefix = tarPath.substring(0, tarPath.lastIndexOf("/"));
        String[] tmp = tarPath.split("/");
        if (tmp.length < 2) {
            throw new RuntimeException("文件路径错误");
        }
        File file = new File(prefix);
        if (!file.exists()) {
            file.mkdirs();
        }

        File outFile = new File(tarPath);
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            fin = new FileInputStream(srcFile);
            fout = new FileOutputStream(outFile);
            inChannel = fin.getChannel();
            outChannel = fout.getChannel();
            long sum = srcFile.length() / ONCE_READ_SIZE;
            // 用来计算是不是最后一次读取
            int count = 0;
            ByteBuffer bb = ByteBuffer.allocate(ONCE_READ_SIZE);
            while (inChannel.read(bb) > 0) {
                count += 1;
                // 得到对应的赫夫曼编码
                //这样分批编码的话，最后一个不满八位怎么算，，，计入下一次来计算
                bb.flip();
                byte[] srcByte = new byte[bb.limit()];
                bb.get(srcByte);
                boolean needFillZero = false;
                if (sum == count - 1) {
                    needFillZero = true;
                }
                byte[] bytes = deCodingWithHuffman(srcByte, needFillZero);
                outChannel.write(ByteBuffer.wrap(bytes));

                bb.clear();
            }
            outChannel.close();
            inChannel.close();
            fout.close();
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(outChannel);
            close(inChannel);
            close(fout);
            close(fin);
        }
    }

    /**
     * 根据原文件得到赫夫曼编码帮助对象
     * @param srcPath 原文件路径
     * @return
     */
    public static HuffmanHelper beforeEnCodingByteWithHuffman(String srcPath) {
        HuffmanHelper helper = new HuffmanHelper();
        // 构造哈夫曼树
        Node root = buildHuffmanTree(srcPath);
        // 得到哈夫曼编码表
        Map<Byte, String> byteStringMap = buildHuffmanCodingMap(root);
        helper.setHuffmanCodingMap(byteStringMap);

        // 复制两个给全局对象
        huffmanCodingMap = byteStringMap;
        huffmanHelper = helper;

        return helper;
    }

    /**
     * 使用哈夫曼树对字符串进行编码
     * @param bytes
     * @return
     */
    public static byte[] enCodingByteWithHuffman(byte[] bytes) {
        // 对字符串进行哈夫曼编码
        byte[] resBytes = getHuffmanByte(bytes);
        return resBytes;
    }

    /**
     * 构建赫夫曼树
     * @param srcPath 输入原文
     * @return
     */
    public static Node buildHuffmanTree(String srcPath) {
        // 先得到字节集合
        Map<Byte, Integer> byteMap = countByteByReadFile(srcPath);

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
        Map<Byte, String> huffmanMap = new HashMap<>();
        /**
         * 从根节点到叶子节点路径：假设通往左节点路径用0表示，通往右节点路径用1表示。
         * 遍历所以叶子节点得到对应的路径编码
         */
        traverseHuffmanTree(huffmanMap, "", root);

        return huffmanMap;
    }

    /**
     * 使用哈夫曼编码表对赫夫曼字节数组进行解码
     * @param huffmanBytes
     * @return
     */
    public static byte[] deCodingWithHuffman(byte[] huffmanBytes, boolean needFillZero) {

        StringBuilder sb = new StringBuilder();
        for (int i=0; i < huffmanBytes.length; i++) {
            // 赫夫曼编码得到二进制字符串最后一个编码可能不足八位，不需要用进行补位
            boolean flag = (i == huffmanBytes.length-1) && needFillZero;
            if (flag) {
                String s = byteToBinaryString(false, huffmanBytes[i]);
                // 说明赫夫曼编码构造最后byte时，构造的二级制字符串是0开头，byte不能处理0开头，所以要记录
                if (huffmanHelper.getFillZeroCount() > 0) {
                    StringBuilder tmp = new StringBuilder();
                    for (int k = 0; k < huffmanHelper.getFillZeroCount(); k++) {
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
//        System.out.println("哈夫曼编码：" + sb.toString());
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
    private static byte[] getHuffmanByte(byte[] src) {
        StringBuilder sb = new StringBuilder();
        // 先看下之前有没有剩下的
        if (huffmanHelper.getLastByteStr() != null && !"".equals(huffmanHelper.getLastByteStr())) {
            sb.append(huffmanHelper.getLastByteStr());
        }
        for (byte b: src) {
            sb.append(huffmanCodingMap.get(b));
        }
//        System.out.println("编码后的字符串:" + sb.toString());
        // 进行转码
        int len = sb.length()/8;
        byte[] target = new byte[len];
        int index = 0;
        for (int i=0; i < sb.length(); i+=8) {
            if (i + 8 > sb.length()) {
                String lastStr = sb.substring(i);
                // 存起来组成最后一个字节的字符串
                huffmanHelper.setLastByteStr(lastStr);
                /*target[index] = (byte)Integer.parseInt(lastStr, 2);
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
                }*/

            } else {
                huffmanHelper.setLastByteStr(null);
                target[index] = (byte)Integer.parseInt(sb.substring(i, i+8), 2);
            }
            index++;
        }
        return target;
    }

    // 读取文件，统计各个字节数量，支持大文件读取
    private static Map<Byte, Integer> countByteByReadFile(String path) {
        Map<Byte, Integer> countBytes = new HashMap<>();
        File file = new File(path);
        FileInputStream fin = null;
        FileChannel channel = null;
        try {
            fin = new FileInputStream(file);
            channel = fin.getChannel();
            // 一次读取20m
            ByteBuffer byteBuffer = ByteBuffer.allocate(ONCE_READ_SIZE);
            while (channel.read(byteBuffer) > 0) {
//                byte[] bytes = byteBuffer.array();
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.limit()];
                byteBuffer.get(bytes);
                for (byte b: bytes) {
                    countBytes.put(b, countBytes.getOrDefault(b, 0) + 1);
                }
                byteBuffer.clear();
            }
            channel.close();
            fin.close();

            return countBytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(channel);
            close(fin);
        }

        return countBytes;
    }

    // 读取赫夫曼编码表
    private static HuffmanHelper readHuffmanCodingMap(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        HuffmanHelper helper = null;
        try {
            fin = new FileInputStream(path);
            ois = new ObjectInputStream(fin);
            helper = (HuffmanHelper) ois.readObject();

            ois.close();
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(ois);
            close(fin);
        }
        return helper;
    }

    // 保存赫夫曼编码表
    private static void saveHuffmanCodingMap(String path) {
        if (huffmanHelper.getHuffmanCodingMap() == null) {
            return;
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(huffmanHelper);

            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(oos);
            close(fos);
        }

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
            return str;
        }
    }

    // 关闭流
    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
