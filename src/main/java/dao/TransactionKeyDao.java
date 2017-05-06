package dao;

import domain.TransactionKey;

import java.sql.*;
import java.util.List;

/**
 * Created by demontf on 17/3/28.
 */
public class TransactionKeyDao extends BaseDao{

    private Connection conn;

    public TransactionKeyDao(){
        conn = super.getConnection();
    }

    public int[] add(List<TransactionKey> lists){
        String sql = "INSERT INTO transaction_key (transaction_hash,vin,vout,times) VALUES (?,?,?,?)";
        int[] res = null;
        try {
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement(sql);
            for (TransactionKey tk:lists) {
                pst.setString(1, tk.getTransactionHash());
                pst.setInt(2, tk.getVin());
                pst.setInt(3, tk.getVout());
                pst.setLong(4, tk.getTimes());
                pst.addBatch();
            }
            res = pst.executeBatch();
            conn.commit();
            pst.close();

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return res;
        }

    }

    public int add(TransactionKey tk){
        String sql = "INSERT INTO transaction_key (transaction_hash,vin,vout,times) VALUES (?,?,?,?)";
        try {

            PreparedStatement pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, tk.getTransactionHash());
            pst.setInt(2, tk.getVin());
            pst.setInt(3, tk.getVout());
            pst.setLong(4, tk.getTimes());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next())return rs.getInt(1);
            pst.close();


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public int getTransactionId(String transaction_hash){
        String sql = "SELECT id FROM transaction_key where transaction_hash='"+transaction_hash+"'";
        ResultSet rs = null;
        int i = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next())i=rs.getInt(1);
            pst.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;

    }



}
