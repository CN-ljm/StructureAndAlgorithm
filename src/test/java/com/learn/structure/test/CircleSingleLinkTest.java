package com.learn.structure.test;

import com.learn.structure.CircleSingleLink;
import org.junit.Test;

/**
 * 单向环形链表测试（解决约瑟夫问题）
 */
public class CircleSingleLinkTest
{

    @Test
    public void JosephTest(){
        CircleSingleLink csl = new CircleSingleLink();
        csl.createCircle(25);
        csl.show();
        csl.outOfCircle(5,5);
    }

}
