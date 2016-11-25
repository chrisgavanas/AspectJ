package data;

public class List {
    private Node head;

    public List() {
        this.head = null;
    }

    public void prepend(Object info) {
        this.head = new Node(info, head);
    }

    public Object pop() {
        Object rv = null;
        if (head != null) {
            rv = head.getInfo();
            this.head = head.getNext();
        }
        return rv;
    }

    public Object head() {
        Object rv = null;
        if (head != null)
            rv = head.getInfo();
        return rv;
    }

}
