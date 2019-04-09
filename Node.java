import java.util.ArrayList;
import java.util.List;

/**
* Node-luokka, joka m‰‰ritt‰‰ graafin yksitt‰isen solmun tietorakenteen. 
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi
*/
public class Node {
    public int key;                 // solmun yksilˆiv‰ avain
    public double x;                // solmun datapisteen x-koordinaatti
    public double y;                // solmun datapisteen y-koordinaatti
    public List<Node> neighbors;    // lista solmun naapureista
    public boolean visited;         // graafin kulkua varten tieto onko solmussa k‰yty
    public int degree;              // kaarten kokonaism‰‰r‰
    public int marker;


    /**
     * Konstruktori luo uuden instanssin solmusta
     * @param key   avain
     * @param x     x-koordinaatti
     * @param y     y-koordinaatti
     */
    public Node(int key, double x, double y) {
        this.x = x;
        this.y = y;
        this.key = key;
        this.degree = 0;
        this.neighbors = new ArrayList<>();
        this.visited = false;
    }

    /**
     * Metodi laskee solmun et‰isyyden parametrina annettuun solmuun v.
     * @param v     Kohdesolmu, suhteessa johon et‰isyytt‰ lasketaan
     * @return      Euklidinen et‰isyys solmujen v‰lill‰ (double)
     */
    public double distanceTo(Node v) {
        return Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
    }

    /**
     * Lis‰‰ parametrina annetun solmun v solmun naapurilistalle.
     * @param v     Naapurisolmun viite
     */
    public void addNeighbor(Node v) {
        this.neighbors.add(v);
    }

    /**
     * Tarkistaa onko parametrina saatu solmu v solmun naapuri
     * @param v     Solmu jonka naapurisuhde halutaan selvitt‰‰
     * @return      true/false
     */
    public boolean isNeighbor(Node v) {
        return this.neighbors.contains(v);
    }

    public void removeNeighbor(Node v) {
        if (this.neighbors.contains(v)) {
            this.neighbors.remove(v);
        }
    }

    // aksessoreja
    public boolean visited() { return this.visited; }
    public void setVisited() { this.visited = true; }
    public void clearVisited() { this.visited = false; }

    // solmun muotoiltu tulostus
    @Override
    public String toString() {
        return "Node " + key + " x:" + this.x + ",y:" + this.y + " vis.: " + this.visited; 
    }

    // tulostaa solmun naapurilistat
    public void printNeighbors() {
        System.out.print("n " + key);
        for (Node v : this.neighbors) {
            System.out.print("\t--> " + v.key);
        }
        System.out.println();
    }

    public List<Node> getNeighbors() {
        return this.neighbors;
    }
    
    // tulostaa solmun asteet
    public void printDegrees() {
        System.out.println(getDegree());
    }
    
    // palauttaa solmun asteet String-muodossa
    public String getDegree() {
        return "Node: " + this.key + " degree: " + this.degree;
    }
}