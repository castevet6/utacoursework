import java.util.ArrayList;

/**
* Edge-luokka, joka määrittää graafin suunnatun kaaren tietorakenteen.  
* @author Antti Manninen, 431734, Manninen.Antti.E@student.uta.fi
*/
public class Edge {
    public Node start;          // kaaren alkusolmu
    public Node end;            // kaaren päätesolmu
    public double weight;       // kaaren paino = alku- ja päätesolmun välinen etäisyys
    public boolean found;       // onko kaari löydetty BFS/DFS

    /**
     * Konstruktori luo uuden instanssin kaaresta
     * @param s alkusolmu
     * @param e päätesolmu
     */
    public Edge(Node s, Node e) {
        this.start = s;
        this.end = e;
        this.found = false;
        // etäisyys lasketaan solmun apumetodia käyttäen
        this.weight = s.distanceTo(e);
    }

    // palauttaa säiliön jossa kaareen liittyvät nodet
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