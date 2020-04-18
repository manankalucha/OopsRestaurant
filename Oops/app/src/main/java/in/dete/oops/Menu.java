package in.dete.oops;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Menu implements Parcelable {

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

    public Menu(Parcel in) {
        n = in.readString();
        v = in.readString();
        p = in.readString();
        url = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(n);
        parcel.writeString(p);
        parcel.writeString(v);
        parcel.writeString(url);

    }
    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
