package util;

import org.junit.Test;

/**
 * Created by demontf on 17/3/29.
 */
public class MyToolTest {

    //@Test
    public void testGetPkHashFromScript(){
        String s = "OP_DUP OP_HASH160 45192cf218bfb648fd5622033035e49a4662719e OP_EQUALVERIFY OP_CHECKSIG";

        System.out.println(MyTools.getPkHashFromScript(s));
    }

    @Test
    public void testSha256(){
        String pk = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";
        System.out.println(MyTools.SHA256(pk));
        System.out.println(MyTools.SHA256(MyTools.SHA256(pk)));
    }

    @Test
    public void testRIPEMD160(){
        String s = "600FFE422B4E00731A59557A5CCA46CC183944191006324A447BDB2D98D4B408";
        //String s2 = "32511e82d56dcea68eb774094e25bab0f8bdd9bc1eca1ceeda38c7a43aceddce";
        System.out.println(MyTools.RIPEMD160(s));

    }

    @Test
    public void test(){
        String s = "304402200c90198b592c8eadd4c5f51ec84304d4447de157cb2c81c8d4ed7e08ec12ce1f02206e20b35b3a6ed87d1b50b874aca5da9ae97dd7cd663bd472a67743f97f1603b801 02735fd4197dad181e46e3341e59e36be05739d43ecb17711b5d579207fe2380a4";

        System.out.println(s.indexOf("04"));
    }
}
