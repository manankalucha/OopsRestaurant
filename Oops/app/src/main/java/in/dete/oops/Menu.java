package in.dete.oops;

import java.io.Serializable;

public class Menu implements Serializable {

    String n;
    String p;
    String v;
    String url;

    public Menu(String n, String p, String v, String url) {
        this.n = n;
        this.p = p;
        this.v = v;
        this.url = url;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
