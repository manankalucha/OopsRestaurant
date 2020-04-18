package in.dete.oops;

import java.io.Serializable;

public class User implements Serializable {

    public String name, email, phone, password, uid, birth, address;
    private int selection;

    public User()
    {

    }

    public User(String name, String phone,String email, String uid, String birth, String password, int selection, String address) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.selection = selection;
        this.uid = uid;
        this.birth = birth;
        this.address = address;
    }

    public int getSelection() {
        return selection;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
