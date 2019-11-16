package com.learn.structure;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

public class CyclicArrayQueue
{
    // 队列大小
    private int size;
    // 定义个数组长度
    private int[] array;
    // 前
    private int front = 0;
    // 后
    private int rear = 0;

    public CyclicArrayQueue(int size){
        this.size = size;
        array = new int[size];
    }

    // 队列空判断
    public boolean isEmpty(){
        return front == rear;
    }

    // 队列满判断
    public boolean isFull(){
       return (rear + 1)%size == front;
    }

    // 入队
    public void add(int v){
       if(isFull()){
           throw new RuntimeException("队列已经满了，不能添加新元素");
       }
       array[rear] = v;
       rear = (rear + 1)%size;
    }

    // 出队
    public int get(){
        if (isEmpty()){
            throw new RuntimeException("队列为空");
        }
        int value = array[front];
        front = (front + 1)%size;
        return value;
    }

    // 有效值个数
    public int variableCount(){
        if(isEmpty()){
            return 0;
        }
        return (rear + size - front)%size;
    }

    // 展示元素
    public void showQueue(){
        for(int i=front; i < front + ((rear + size - front)%size); i ++){
            System.out.printf("array[%d]=%d\n", i%size, array[i%size]);
        }
    }

}
