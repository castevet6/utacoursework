/*
  Queue.java
  ==========

  Simple queue implementation as a linked list containing Objects.

  Operations:

  clear():       empties the queue
  front():       returns first element in the queue, causes exception if empty
  isEmpty():     returns true, if queue is empty
  enqueue(x):    inserts x into the queue
  x = dequeue(): removes an element from the queue, 
                 throws Queue.QueueEmptyException if the queue is empty
*/

class Queue {
    private static class QueueNode {
		public Object datum;
		public QueueNode   next;

		public QueueNode (Object datum) {
		    this.datum = datum;
		    this.next = null;
		}
    }
    
    public static class QueueEmptyException extends RuntimeException {}

    QueueNode head, tail;

    public Queue () {
		this.head = null;
		this.tail = null;
    }

    public void clear () {
		head = tail = null;
    }

    public Object front () {
		Object result = null;
		if (head == null)
		    throw new QueueEmptyException();
		return head.datum;
    }
  
    public boolean isEmpty () {
		return head == null;
    }

    public void enqueue (Object o) {
		if (head == null)
		    head = tail = new QueueNode (o);
		else {
			tail.next = new QueueNode (o);
			tail = tail.next;
		}
    }

    public Object dequeue () {
		Object result = null;
		if (head == null)
		    throw new QueueEmptyException();
		else {
			result = head.datum;
			head = head.next;
			if (head == null)
			    tail = null;
		}
		return result;
    }

    public static void main (String[] args) {
		Queue q = new Queue ();
		for (int i = 0; i < 10; ++i) {
			System.out.println ("ENQUEUE("+i+")");
			q.enqueue (new Integer (i));
		}
		System.out.println ("FRONT => " + q.front());
		while (! q.isEmpty ()) {
			System.out.println ("DEQUEUE => " + q.dequeue());
		}
    }
}
