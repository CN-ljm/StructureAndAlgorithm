package com.learn.structure;

/**
 * 单向循环链表（约瑟夫问题）
 */
public class CircleSingleLink
{
    private Boy first = new Boy(-1);
    private Boy current;

    /**
     * 创建一个N个节点的环形链表
     * @param count 节点数
     */
    public void createCircle(int count){
        if(count < 2){
            System.out.println("需要两个以上的小孩组成圈");
        }
        for (int i=1; i <= count; i++){
            if (i == 1){
                first = new Boy(1);
                current = first;
            }else{
                current.next = new Boy(i);
                current = current.next;
            }
        }
        current.next = first;
    }

    /**
     * 展示环形单向链表
     */
    public void show(){
        Boy curr = first;
        while (true){
            if (curr.next == first){
                break;
            }
            System.out.println(curr.toString());
            curr = curr.next;
        }
        // 最后一个人
        System.out.println(curr.toString());
    }

    /**
     * 出圈
     * @param starter
     * @param num
     */
    public void outOfCircle(int starter, int num){
        // 辅助指针，指向出队前一个
        Boy pre = first;
        while (true){
            if (pre.next == first){
                break;
            }
            pre = pre.next;
        }
        // 找到开始位置
        for(int i=1; i<starter; i++){
            first = first.next;
            pre = pre.next;
        }
        // 开始出队
        while (true){
            // 最后一个人
            if(pre == first){
                break;
            }
            for (int i=1; i<num; i++){
                first = first.next;
                pre = pre.next;
            }
            System.out.println("出队男孩：" + first.toString());
            pre.next = first.next;
            first = first.next;
        }
        System.out.println("最后出队的男孩: " + pre.toString());
    }

}

class Boy{
    // 编号
    private int no;
    // 后一个男孩
    public Boy next;

    public Boy(int no)
    {
        this.no = no;
    }

    public int getNo()
    {
        return no;
    }

    public void setNo(int no)
    {
        this.no = no;
    }

    @Override
    public String toString()
    {
        return "Boy{" + "男孩编号no=" + no + '}';
    }
}