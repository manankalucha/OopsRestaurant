package in.dete.oops;
import java.io.Serializable;

public class Cart implements Serializable {

    String n;
    String v;
    String p;

    public Cart(String n, String v, String p) {
        this.n = n;
        this.v = v;
        this.p = p;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}

