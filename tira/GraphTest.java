import java.util.ArrayList;

public class GraphTest {
    public static void main(String[] args) {
        Graph g = new Graph();

        g.addNode(1.0, 1.0);
        g.addNode(1.0, 9.0);
        g.addNode(9.0, 1.0);
        g.addNode(9.0, 9.0);
        g.addNode(5.0, 5.0);

        g.connectAllNodes();
        g.printGraph();
        g.printNeighbors();

        /* Let's make this graph with edges weights 1 */
		/* 
	              1--2--6   
	              |\ |
	              | \|
	              3--4
	              |   \
	              |    \
	              5     7
            */
        /*g.addNode(1.0, 1.0);
        g.addNode(2.0, 1.0);
        g.addNode(1.0, 2.0);
        g.addNode(2.0, 2.0);
        g.addNode(1.0, 3.0);
        g.addNode(3.0, 1.0);
        g.addNode(3.0, 3.0);

        g.connectNodes(1, 2);
        g.connectNodes(1, 3);
        g.connectNodes(1, 4);
        //g.connectNodes(2, 4);
        g.connectNodes(2, 6);
        //g.connectNodes(3, 4);
        g.connectNodes(3, 5);
        g.connectNodes(4, 7);

        g.printGraph();
        g.printNeighbors();

        System.out.println("LEVEYSHAKU:\n");
        ArrayList<Node> nodeList = g.breadthFirstSearchAll();
        for (Node n : nodeList) {
            System.out.println(n.key);
        }

        System.out.println("\nSYVYYSHAKU:\n");
        ArrayList<Node> nodeList2 = g.depthFirstSearchAll();
        for (Node n : nodeList2) {
            System.out.println(n.key);
        }

        System.out.println("is connected : " + g.isConnected());*/
    }
}