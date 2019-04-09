import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Graph {
    private HashMap<Integer, Node> nodes;   // sanakirja johon talletetaan avain-solmu -parit
    private ArrayList<Edge> edges;          // lista kaarista
    private int index;                      // indeksi solmujen avaimista (arvo = seuraava vapaa)

    // alustetaan luokkamuuttujat konstruktorissa
    public Graph() {
        this.index = 1;
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Luo (x,y)-pisteest� uuden solmun graafiin
     * @param x     Pisteen x-koordinaatti
     * @param y     Pisteen y-koordinaatti
     */
    public void addNode(double x, double y) {
        this.nodes.putIfAbsent(this.index, new Node(this.index, x, y));
        // kasvatetaan indeksi�
        index++;
    }

    /**
     * Yhdist�� kaksi solmua. Luo solmjen v�lille kaaren.
     * @param key1  Alkusolmu
     * @param key2  P��tesolmu
     */
    public void connectNodes(int key1, int key2) {
        Node v = this.nodes.get(key1);
        Node w = this.nodes.get(key2);
        // lis�t��n solmut toistensa naapurilistalle jos ne eiv�t ole jo siell�
        if (w.neighbors.contains(v) || v.neighbors.contains(w)) {
            return;
        }
        v.addNeighbor(w);
        w.addNeighbor(v);
        // luodaan uusi kaari;
        this.edges.add(new Edge(v, w));
        
        // lis�t��n molempien solmujen asteita
        v.degree++;
        w.degree++;
    }

    /**
     * Etsii l�himm�n naapurisolmun solmulle jonka avain annetaan parametrina.
     * Varsinainen haun toteutus eri signaturella olevassa samannimisess� metodissa.
     * @param key   Avain solmulle jolle etsit��n l�hin naapurisolmu
     * @return      Viite l�himp��n naapurisolmuun
     */
    public Node closestNode(int key) {
        return closestNode(this.nodes.get(key));
    }

    /**
     * Etsii l�himm�n naapurisolmun solmulle jonka viite annetaan parametrina.
     * @param v Viite solmuun v jolle l�hint� naapuria etsit��n
     * @return  Viite l�himp��n naapurisolmuun
     */
    public Node closestNode(Node v) {
        if (v == null) {
            return null;
        }
        // haetaan apumetodin avulla kaikkien solmujen avaimet poislukien solmun v avain
        int[] keys = getKeysExcluding(v.key);
        // asetetaan alkuarvaus pienimm�st� et�isyydest� taulukon indeksi� 0 vastaavaan solmuun
        Node minNode = this.nodes.get(keys[0]);
        // lasketaan alkuarvausta vastaava pienin et�isyys solmuun v, jota p�ivitet��n jos pienempi
        // et�isyys l�ytyy
        double minDist = v.distanceTo(minNode);

        // iteroidaan kaikki solmut l�pi
        for (int i : keys) {
            Node w = this.nodes.get(i);
            double distvw = v.distanceTo(w);
            // vertailu onko l�ydetty solmu joka on l�hemp�n� ja joka ei ole jo naapurilistalla
            if (v != w && distvw > 0 && distvw < minDist && !v.isNeighbor(w)) {
                // pienempi l�ytynyt, p�ivitet��n et�isyys ja pienimm�n solmun viite
                minNode = w;
                minDist = distvw;
            }
        }
        // palautetaan pienimm�n solmun viite
        return minNode;
    }

    /**
     * Palauttaa kaikkien solmujen avaimet kokonaislukutaulukkon, poislukien parametrina annettu
     * avainarvo. Hy�dynnet��n l�himm�n solmun etsimismetodissa.
     * @param key   Avainarvo joka j�tet��n pois avaintaulukosta.
     * @return      int[]-taulukko avainarvoista.
     */
    private int[] getKeysExcluding(int key) {
        Set<Integer> keys = this.nodes.keySet();
        Set<Integer> keysExcl = new HashSet<Integer>(keys);
        keysExcl.remove(key);
        int[] keyarray = new int[keysExcl.size()];
        int index = 0;
        for (Integer i : keysExcl) {
            keyarray[index++] = i.intValue();
        }
        return keyarray;
    }

    public ArrayList<Node> breadthFirstSearchAll() {
        this.clearVisitedMarkers();
        Set<Integer> keys = this.nodes.keySet();
        ArrayList<Node> BFSNodesAll = new ArrayList<>();
        for (Integer i : keys) {
            // jos k�ym�tt�mi� nodeja, aloitetaan uusi leveyshaku
            if (!(getNode(i).visited())) {
                ArrayList<Node> BFSList = breadthFirstSearch(i);
                BFSNodesAll.addAll(BFSList);
            }
        }
        return BFSNodesAll;
    }
    
    public ArrayList<Node> breadthFirstSearch(int v) {
        ArrayList<Node> BFSList = new ArrayList<>();
        Queue q = new Queue();
        q.enqueue(v);
        getNode(v).setVisited();
        BFSList.add(getNode(v));
        while (!q.isEmpty()) {
            int key = (int) q.dequeue();
            Node n = getNode(key);
            for (Node nb : n.getNeighbors()) {
                if (!nb.visited()) {
                    nb.setVisited();
                    BFSList.add(nb);
                    q.enqueue(nb.key);
                } 
            }
        }
        return BFSList;
    }
    
    public ArrayList<Node> depthFirstSearchAll() {
        this.clearVisitedMarkers();
        Set<Integer> keys = this.nodes.keySet();
        ArrayList<Node> DFSNodesAll = new ArrayList<>();
        for (Integer i : keys) {
            if (!(getNode(i).visited())) {
                ArrayList<Node> DFSList = depthFirstSearch(i);
                DFSNodesAll.addAll(DFSList);
            }
        }

        return DFSNodesAll;
    }

    public ArrayList<Node> depthFirstSearch(int v) {
        ArrayList<Node> DFSList = new ArrayList<>();
        Node n = this.getNode(v);
        DFSList.add(n);
        n.setVisited();
        for (Node nb : n.getNeighbors()) {
            if (!nb.visited()) {
                nb.setVisited();
                DFSList.addAll(depthFirstSearch(nb.key));
            }
        }
        return DFSList;
    }

    
    /**
     * Yhdist�� graafin jokaisen solmun l�himp��n vapaaseen naapuriinsa.
     */
    public void connectAllNodes() {
        // iteroidaan kaikki graafin solmut
        for (int i = 1; i < this.index; i++) {
            // haetaan solmulle i l�hin solmu joka ei ole jo naapurilistalla
            Node w = closestNode(i);
            // yhdistet��n solmut jos l�hin solmu l�ytyy
            if (w != null) {
                connectNodes(i, w.key);
            }
        }
    }

    /**
     * Poistaa solmun graafista. K�y l�pi kaikkien solmujen naapurilistat ja poistaa solmun v
     * niist� mik�li esiintym� l�ytyy. Poistetaan my�s solmuun v liittv�t kaaret.
     * @param v poistettavan noden avain
     */
    public void removeNode(int v) {
        Node poistettava = this.getNode(v);
        // k�yd��n l�pi kaikkin nodet, ja poistetaan noden esiintym�t naapurilistoilta
        for (Integer i : this.nodes.keySet()) {
            Node cur = this.getNode(i);
            cur.removeNeighbor(poistettava);
        }
        // k�yd��n l�pi kaikki kaaret, ja poistetaan kaikki kaaret miss� node v on 
        // alku tai p��tesolmuna
        ArrayList<Edge> poistettavatKaaret = new ArrayList<>();
        for (Edge e : this.edges) {
            if (e.start.key == v || e.end.key == v) {
                poistettavatKaaret.add(e);
            }
        }
        this.edges.removeAll(poistettavatKaaret);
        // poistetaan solmu
        this.nodes.remove(v);
    }

    /**
     * Merkit��n kaikki solmut k�ym�tt�miksi
     */
    public void clearVisitedMarkers() {
        for (int i : this.nodes.keySet()) {
            this.nodes.get(i).clearVisited();
        }
    }


    public boolean isConnected() {
        this.clearVisitedMarkers();
        boolean connected = true;
        ArrayList<Node> dfs = depthFirstSearch(1);
        for (Integer i : this.nodes.keySet()) {
            if (!this.getNode(i).visited()) {
                connected = false;
            }
        }
        return connected;
    }

    public String[] getDegreesFormatted() {
        int nodes = this.numNodes();
        String[] lines = new String[nodes];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = this.getNode(i+1).getDegree();
        }
        return lines;
    }

    public void printDegrees() {
        String[] degrees = getDegreesFormatted();
        for (String s : degrees) {
            System.out.println(s);
        }
    }

    public Node getNode(int key) {
        return this.nodes.get(key);
    }

    public Edge getEdgeBetween(int v, int w) {
        for (Edge e : this.edges) {
            if (e.start.key == v && e.end.key == w) {
                return e;
            }
        }
        return null;
    }


    public void setVisited(int key) {
        this.nodes.get(key).setVisited();
    }

    public int numNodes() { return this.nodes.size(); }
    public int numEdges() { return this.edges.size(); }

    public void printNodes() {
        for (int i = 1; i < this.index; i++) {
            System.out.println(this.nodes.get(i));
        }
    }

    public void printNeighbors() {
        for (int i = 1; i < this.index; i++) {
            this.nodes.get(i).printNeighbors();
        }
    }

    public void printGraph() {
        System.out.println("NODES:");
        ArrayList<Node> nodelist = new ArrayList<>(this.nodes.values());
        for (Node v : nodelist) {
            System.out.println(v);
        }
        System.out.println("EDGES:");
        for (Edge e : edges) {
            System.out.println(e);
        }
    }

}