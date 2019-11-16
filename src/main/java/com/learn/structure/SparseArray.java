package com.learn.structure;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.CharBuffer;

/**
 * 稀疏数组
 */
public class SparseArray
{
    public static void main(String[] args)
    {
        // 原数组
        System.out.println("--原数组输出---------------");
        int[][] src = new int[8][9];
        src[3][2] = 10;
        src[4][5] = 11;
        src[4][7] = 12;
        for (int[] aa: src){
            for (int v: aa){
                System.out.printf("%d\t", v);
            }
            System.out.println();
        }

        // 转为稀疏数组
        System.out.println("--原数组转为稀疏数组输出---------------");
        int sum = 0;
        for (int i=0; i < src.length; i++){
            for (int j=0; j < src[i].length; j++){
                if (src[i][j] != 0){
                    sum ++;
                }
            }
        }
        int[][] sparse = new  int[sum + 1][3];
        sparse[0][0] = src.length;
        sparse[0][1] = src[0].length;
        sparse[0][2] = sum;
        int row = 1;
        for (int i=0; i < src.length; i++){
            for (int j=0; j < src[i].length; j++){
                if (src[i][j] != 0){
                    sparse[row][0] = i;
                    sparse[row][1] = j;
                    sparse[row][2] = src[i][j];
                    row++;
                }
            }
        }
        for (int i=0; i < sparse.length; i++){
            for (int j=0; j < sparse[i].length; j++){
                System.out.printf("%d\t", sparse[i][j]);
            }
            System.out.println();
        }
//        writeToFile(sparse);

        sparse = ReadFromFile();

        // 还原数组
        System.out.println("--稀疏数组还原输出---------------");
        int[][] tar = new int[sparse[0][0]][sparse[0][1]];
        for (int i = 1; i < sparse.length; i++){
            tar[sparse[i][0]][sparse[i][1]] = sparse[i][2];
        }
        for (int[] aa: tar){
            for (int v: aa){
                System.out.printf("%d\t", v);
            }
            System.out.println();
        }
    }

    public static boolean writeToFile(int[][] aa){
        String path = "D:/sparse.data";
        try
        {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            for (int[] bb : aa){
                for (int cc : bb){
                    fw.write(String.valueOf(cc) + "\t");
                }
                fw.write("\n");
            }
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        return false;
    }

    public static int[][] ReadFromFile() {
        String path = "D:/sparse.data";
        String sparseStr = "";
        try
        {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            int cd = 0;
            StringBuffer sb = new StringBuffer();
            while ((cd = fr.read()) != -1){
                sb.append((char)cd);
            }
            sparseStr = sb.toString();
            fr.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        String[] split = sparseStr.split("\n");
        int[][] sparse = new int[split.length][3];
        int row = 0;
        for (String s: split){
            String[] line = s.split("\t");
            sparse[row][0] = Integer.valueOf(line[0]);
            sparse[row][1] = Integer.valueOf(line[1]);
            sparse[row][2] = Integer.valueOf(line[2]);
            row ++;
        }

        return sparse;
    }
}
