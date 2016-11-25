package data;

public class Node {
    private Object info;
    private Node next;

    public Node(Object info, Node next) {
        this.info = info;
        this.next = next;
    }

    public Object getInfo() {
        return info;
    }

    public Node getNext() {
        return next;
    }

}
