package com.learn.algorithm;

import com.learn.structure.tree.BinaryTree;
import com.learn.structure.tree.Node;
import org.junit.Test;

/**
 * 二叉树遍历测试
 */
public class BinaryTreeTest {

    @Test
    public void traversalTreeTest(){

        Node root = new Node(new Person("大娃",1));
        Node n2 = new Node(new Person("二娃",2));
        Node n3 = new Node(new Person("三娃",3));
        Node n4 = new Node(new Person("四娃",4));
        Node n5 = new Node(new Person("五娃",5));
        Node n6 = new Node(new Person("六娃",6));
        Node n7 = new Node(new Person("七娃",7));

        root.setLeft(n2);
        root.setRight(n3);
        n2.setLeft(n4);
        n2.setRight(n5);
        n3.setLeft(n6);
        n3.setRight(n7);

        //前序遍历
        System.out.println("前序遍历：");
        BinaryTree.preTraversal(root);

        //中序遍历
        System.out.println("中序遍历：");
        BinaryTree.midTraversal(root);

        //后序遍历
        System.out.println("后序遍历：");
        BinaryTree.postTraversal(root);

    }

}

class Person{

    private String name;

    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
