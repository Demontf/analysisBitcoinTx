package dao;

import domain.ThePublicKey;
import org.junit.Test;

/**
 * Created by demontf on 17/3/29.
 */
public class ThePublicKeyDaoTest {

    @Test
    public  void testAdd(){
//        ThePublicKey pk = new ThePublicKey();
//        pk.setPublicKey("pk001");
//        pk.setPublicHash160("pkhash001");
//        List<ThePublicKey> lists = new ArrayList<ThePublicKey>();
//        lists.add(pk);
//        ThePublicKey pk2 = new ThePublicKey();
//        pk2.setPublicKey("pk002");
//        pk2.setPublicHash160("pkhash002");
//        lists.add(pk2);
//        ThePublicKeyDao pkDao = new ThePublicKeyDao();
//        pkDao.add(lists);
    }

    @Test
    public  void testGetId(){

        ThePublicKeyDao pkDao = new ThePublicKeyDao();
        pkDao.getPublicKeyId("pkhash001");
    }

    @Test
    public void testgetPublicKeyByHash(){
        ThePublicKeyDao pkDao = new ThePublicKeyDao();
        ThePublicKey t = pkDao.getPublicKeyByHash("494f689d834aa3acaa592580017abe320c747d84");
        System.out.println("S");
    }
}
