package in.example.oopsrestaurant;

public class User {

    String n;
    String p;
    String d;
    String vn;

    public User(String n, String p, String d, String vn) {
        this.n = n;
        this.p = p;
        this.d = d;
        this.vn = vn;
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

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }
}
