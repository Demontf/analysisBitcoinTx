import org.junit.Test;

/**
 * Created by demontf on 2017/4/10.
 */
public class TransactionParserTest {

    @Test
    public void getPkhashFormAsm(){
        //String s = TransactionParser.getPkhashFormAsm("OP_DUP OP_HASH160 e3426546acd919d87bc7a5c2697bd8aee8579753 OP_EQUALVERIFY OP_CHECKSIG");
        //System.out.println(s);
        String s ="1234556l123456";
        s.length();
        System.out.println(s.substring( s.length()-6, s.length()));
    }
}
