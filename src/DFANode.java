import java.util.ArrayList;

/**
 * Created by Spencer on 10/6/2015.
 */
public class DFANode {
    private boolean accept;
    private boolean start;
    private String alphabet;
    private int next_nodes;
    private int num_transitions;
    private int id;

    public DFANode(){
        this.accept = false;
        this.start = false;
        this.next_nodes = 0;
        this.alphabet = "";
        this.id = -1;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setDest(int dest) {
        this.next_nodes = dest;
    }

    public void setAlphabet(String a) {
        this.alphabet = a;
    }

    public void setStart(boolean b) {
        this.start = b;
    }

    public void setAccept(boolean b) {
        this.accept = b;
    }

    public String getAlphabet() {
        return this.alphabet;
    }

    public int getDest() {
        return this.next_nodes;
    }

    public boolean getStart() {
        return this.start;
    }

    public boolean getAccept() {
        return this.accept;
    }

    public int getID() {
        return this.id;
    }
}
