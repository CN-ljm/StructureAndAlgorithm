package com.learn.structure.test;

import com.learn.structure.CyclicArrayQueue;
import org.junit.Test;

import java.util.concurrent.Executors;

public class CyclicArrayQueueTest
{
    @Test
    public void myArrayQueueTest(){
        CyclicArrayQueue caq = new CyclicArrayQueue(3);
        caq.add(1);
        caq.add(2);
//        caq.get();
        caq.add(3);
        caq.get();
        caq.get();
        caq.get();
        caq.add(4);
        caq.add(5);
        System.out.println(caq.variableCount());
        caq.showQueue();
//        caq.showQueue();
//        caq.get();
//        caq.showQueue();

        Executors.newSingleThreadExecutor();


    }
}
