package domain;

/**
 * Created by demontf on 17/3/26.
 */
public class Edges {

    private int id;
    private int transactionKey;//成int 存数据库
    private int pkHash;
    private int type;  //1 input 2 output
    private double value;


    public Edges(int transactionKey, int pkHash, int type, double value) {
        this.transactionKey = transactionKey;
        this.pkHash = pkHash;
        this.type = type;
        this.value = value;

    }

    public int getTransactionKey() {
        return transactionKey;
    }

    public int getPkHash() {
        return pkHash;
    }

    public int getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public void setTransactionKey(int transactionKey) {
        this.transactionKey = transactionKey;
    }

    public void setPkHash(int pkHash) {
        this.pkHash = pkHash;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }
}
