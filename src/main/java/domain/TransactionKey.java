package domain;

/**
 * Created by demontf on 17/3/26.
 */
public class TransactionKey {

    private int id;
    private String transactionHash;
    private int vin;
    private int vout;
    private long times;


    public TransactionKey(String transactionHash, int vin, int vout, long times) {
        this.transactionHash = transactionHash;
        this.vin = vin;
        this.vout = vout;
        this.times = times;
    }

    public int getId() {
        return id;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVin() {
        return vin;
    }

    public int getVout() {
        return vout;
    }

    public long getTimes() {
        return times;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public void setVout(int vout) {
        this.vout = vout;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
}
