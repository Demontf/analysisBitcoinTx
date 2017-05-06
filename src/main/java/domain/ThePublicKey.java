package domain;

/**
 * Created by demontf on 17/3/26.
 */

public class ThePublicKey {

    private int id;
    private String publicKey;
    private String publicHash160;
    private String address;


    public void pkToAddress() {
        if (this.publicKey != null && !this.publicKey.equals("")) {
            //this.address = pkToAddress(this.publicKey);
        }
    }

    public ThePublicKey(String publicKey, String publicHash160) {
        this.publicKey = publicKey;
        this.publicHash160 = publicHash160;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPublicHash160() {
        return publicHash160;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setPublicHash160(String publicHash160) {
        this.publicHash160 = publicHash160;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
