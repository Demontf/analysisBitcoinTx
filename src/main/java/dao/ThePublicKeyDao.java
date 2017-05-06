package dao;

import domain.ThePublicKey;

import java.sql.*;
import java.util.List;

/**
 * Created by demontf on 17/3/28.
 */
public class ThePublicKeyDao extends BaseDao{


    private Connection conn;

    public ThePublicKeyDao(){
        conn = super.getConnection();
    }

    public void add(List<ThePublicKey> lists){
        String sql = "INSERT INTO public_key (public_key, public_key_hash160) VALUES (?,?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement(sql);
            for (ThePublicKey pk:lists) {
                pst.setString(1, pk.getPublicKey());
                pst.setString(2, pk.getPublicHash160());
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int add(ThePublicKey pk){
        String sql = "INSERT INTO public_key (public_key, public_key_hash160) VALUES (?,?)";
        try {

            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, pk.getPublicKey());
            pst.setString(2, pk.getPublicHash160());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next())return rs.getInt(1);
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(ThePublicKey pk){
        String sql = "UPDATE public_key set public_key=? where id=?";
        try {

            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, pk.getPublicKey());
            pst.setInt(2, pk.getId());
            int i=pst.executeUpdate();
            pst.close();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPublicKeyId(String public_key_hash){
        String sql = "SELECT id FROM public_key where public_key_hash160='"+public_key_hash+"'";
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

    public ThePublicKey getPublicKeyByHash(String public_key_hash){
        String sql = "SELECT * FROM public_key where public_key_hash160='"+public_key_hash+"'";
        ResultSet rs = null;
        int i = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            if(rs.next()){
                ThePublicKey tpk = new ThePublicKey(rs.getString(2),rs.getString(3));
                tpk.setId(rs.getInt(1));
                tpk.setAddress(rs.getString(4));
                return tpk;
            }
            pst.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


}
