package com.learn.algorithm.huffmantree;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by liangjiaming on 2020/7/13
 * @title 赫夫曼编码辅助类
 * @Desc
 */

public class HuffmanHelper implements Serializable {

    /**
     * 赫夫曼编码表
     */
    private Map<Byte, String> huffmanCodingMap = new HashMap<>();

    /**
     * 赫夫曼编码最后不满8位的位数，最大是7位。因为byte不能区分0和00，所以当构造最后一个byte时候，如果是以0开头的，要记录下前面的0位数，解码的时候进行补上
     */
    private int fillZeroCount = 0;

    private String enLastByteStr;

    private String deLastByteStr;

    public Map<Byte, String> getHuffmanCodingMap() {
        return huffmanCodingMap;
    }

    public void setHuffmanCodingMap(Map<Byte, String> huffmanCodingMap) {
        this.huffmanCodingMap = huffmanCodingMap;
    }

    public int getFillZeroCount() {
        return fillZeroCount;
    }

    public void setFillZeroCount(int fillZeroCount) {
        this.fillZeroCount = fillZeroCount;
    }

    public String getEnLastByteStr() {
        return enLastByteStr;
    }

    public void setEnLastByteStr(String enLastByteStr) {
        this.enLastByteStr = enLastByteStr;
    }

    public String getDeLastByteStr() {
        return deLastByteStr;
    }

    public void setDeLastByteStr(String deLastByteStr) {
        this.deLastByteStr = deLastByteStr;
    }
}
