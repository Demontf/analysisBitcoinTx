package dao;

import domain.Edges;

import java.sql.*;
import java.util.List;

/**
 * Created by demontf on 17/3/28.
 */
public class EdgesDao extends BaseDao{
    private Connection conn;

    public EdgesDao(){
        conn = super.getConnection();
    }

    public void add(List<Edges> lists){
        String sql = "INSERT INTO edges (transaction_id,pk_hash,type,value) VALUES (?,?,?,?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement(sql);
            for (Edges ed:lists) {
                pst.setInt(1, ed.getTransactionKey());
                pst.setInt(2, ed.getPkHash());
                pst.setInt(3, ed.getType());
                pst.setDouble(4, ed.getValue());
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int add(Edges ed){
        String sql = "INSERT INTO edges (transaction_id,pk_hash,type,value) VALUES (?,?,?,?)";
        try {

            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, ed.getTransactionKey());
            pst.setInt(2, ed.getPkHash());
            pst.setInt(3, ed.getType());
            pst.setDouble(4, ed.getValue());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next())return rs.getInt(1);
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
