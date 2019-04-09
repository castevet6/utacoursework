public class NodeTest {
    public static void main(String[] args) {
        Queue q = new Queue();
        q.enqueue(3);
        q.enqueue(2);
        System.out.println(q.isEmpty());
        q.dequeue();
        q.dequeue();
        System.out.println(q.isEmpty());
    }
}   