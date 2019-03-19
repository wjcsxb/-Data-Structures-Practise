package LinkList;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The type Link list.
 * 单链表的创建 插入 删除
 */
public class LinkList {

    public Node first;
    private int index = 0;

    public LinkList() {
        this.first = null;
    }

    /*
    * 带头指针
    * */
    public LinkList(int data){
        Node headNode = new Node(data);
        headNode.next = first;
        this.first = headNode;
    }
    /*
    * 插入一个头结点
    * */
    public void addFirstNode(int data){
        Node node = new Node(data);
        node.next = first;
        first = node;
    }

    /*
    * 删除一个头结点,并返回当前头结点
    * */
    public Node deleteFirstNode(){
        Node deleteNode = first;
        first = deleteNode.next;
        return deleteNode;
    }
    /*
     * 删除任意位置的节点
     * */
    public Node deleteByIndex(int pos){
        Node currentNode = first;
        Node previousNode = first;
        while (index != pos) {
            previousNode = currentNode;
            currentNode = previousNode.next;
            index++;
        }
        if (currentNode == first) {
            first = first.next;
        } else {
            index = 0;
            previousNode.next = currentNode.next;
        }
        return currentNode;
    }
    /*
     * 插入一个头结点(带头指针)
     * */
    public void addFirstNodeWithHeadNode(int data){
        Node node = new Node(data);
        node.next = first.next;
        first.next = node;
    }

    /*
    * 在任意位置插入节点 在index的后面插入
    * */
    public void add(int pos,int data){
        Node currentNode = first;
        Node newNode = new Node(data);
        Node previousNode = first;
        while (index != pos) {
            previousNode = currentNode;
            currentNode = currentNode.next;
            index++;
        }
        previousNode.next = newNode;
        newNode.next = currentNode;
        index = 0;
    }
    /*
    * 根据位置查找节点信息
    * */
    public Node findByIndex(int pos) {
        Node currentNode = first;
        while (pos != index) {
            currentNode = currentNode.next;
            index++;
        }
        index = 0;
        return currentNode;
    }

    /*
     * 根据数据查找节点信息
     * */
    public Node findByData(int data) {
        Node currentNode = first;
        while (currentNode.data != data) {
            if (currentNode.next == null) {
                return null;
            }
            currentNode = currentNode.next;
        }
        return currentNode;
    }
    /*
    * 显示所有节点信息
    * */
    public void displayAllNodes(){
        Node currentNode = first;
        while (currentNode != null) {
            currentNode.display();
            currentNode = currentNode.next;
        }

    }
    /*
    * 合并两个有序的单链表，递归
    * */
    public Node mergerList(Node listA,Node listB){
        if (listA == null && listB == null) {
            return null;
        }
        if (listA == null) {
            return listB;
        }
        if (listB == null) {
            return listA;
        }

        Node newNode = null;
        if (listA.data < listB.data) {
            newNode = listA;
            newNode.next = mergerList(listA.next, listB);
        } else {
            newNode = listB;
            newNode.next = mergerList(listA, listB.next);
        }

        return newNode;
    }
    public static void main1(String[] args){
        LinkList linkList = new LinkList();
//        LinkList linkList = new LinkList(0);
        linkList.addFirstNode(20);
        linkList.addFirstNode(21);
        linkList.addFirstNode(19);
        linkList.add(1,22);
//        linkList.addFirstNodeWithHeadNode(20);
//        linkList.addFirstNodeWithHeadNode(21);
//        linkList.addFirstNode(21);
        linkList.displayAllNodes();
    }

    public static void main(String[] args){
        LinkList linkList1 = new LinkList();
        LinkList linkList2 = new LinkList();
        linkList1.addFirstNode(6);
        linkList1.addFirstNode(4);
        linkList1.addFirstNode(2);

        linkList2.addFirstNode(5);
        linkList2.addFirstNode(3);
        linkList2.addFirstNode(1);

        linkList1.displayAllNodes();
        linkList2.displayAllNodes();

        LinkList newList = new LinkList();
        Node newNode = new Node(0);
        newList.first = linkList1.mergerList(linkList1.first,linkList2.first);
        newList.displayAllNodes();

    }
}
