package LinkList;

public class Node {

    protected Node next;
    public int data;

    public Node(int data) {
        this.data = data;
    }

    public void display(){
        System.out.println(data + " ");
    }
}
