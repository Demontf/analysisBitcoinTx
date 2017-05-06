package dao;

import domain.TransactionKey;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by demontf on 17/3/29.
 */
public class TransactionDaoTest {

    @Test
    public  void testAdd(){
        TransactionKey tk = new TransactionKey("127",1,1,11);

        TransactionKey tk2 = new TransactionKey("1200",2,2,22);
        TransactionKey tk3 = new TransactionKey("129",2,2,22);

        List tks = new ArrayList();
        tks.add(tk3);
        tks.add(tk2);
        tks.add(tk);
        TransactionKeyDao tkDao = new TransactionKeyDao();

        for(int i :tkDao.add(tks))
            System.out.print(i+" ");
    }
}
