import java.util.ArrayList;

/**
* Edge-luokka, joka m��ritt�� graafin suunnatun kaaren tietorakenteen.  
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi
*/
public class Edge {
    public Node start;          // kaaren alkusolmu
    public Node end;            // kaaren p��tesolmu
    public double weight;       // kaaren paino = alku- ja p��tesolmun v�linen et�isyys
    public boolean found;       // onko kaari l�ydetty BFS/DFS

    /**
     * Konstruktori luo uuden instanssin kaaresta
     * @param s alkusolmu
     * @param e p��tesolmu
     */
    public Edge(Node s, Node e) {
        this.start = s;
        this.end = e;
        this.found = false;
        // et�isyys lasketaan solmun apumetodia k�ytt�en
        this.weight = s.distanceTo(e);
    }

    // palauttaa s�ili�n jossa kaareen liittyv�t nodet
    public ArrayList<Node> returnNodes() {
        ArrayList<Node> ret = new ArrayList<>();
        ret.add(start);
        ret.add(end);
        return ret;
    }

    // aksessorit
    public void setFound() { this.found = true; }
    public void clearFound() { this.found = false; }
    public boolean found() { return this.found; }

    // kaaren muotoiltu tulostus
    @Override
    public String toString() {
        return "From " + start.key + " to " + end.key + " weight: " + String.format("%.2f", this.weight);
    }
}