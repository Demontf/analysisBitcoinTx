import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import com.neemre.btcdcli4j.core.domain.*;
import org.apache.http.impl.client.CloseableHttpClient;
import util.ResourceUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by demontf on 17/2/6.
 */
public class T {
    public static void main(String[] args){

        List<Block> allList = new LinkedList<Block>();
        try {
            //建立连接 获取btcdclient
            CloseableHttpClient httpProvider = ResourceUtils.getHttpProvider();
            Properties nodeConfig = ResourceUtils.getNodeConfig();
            BtcdClient client = new BtcdClientImpl(httpProvider, nodeConfig);


//            MiningInfo miningInfo = client.getMiningInfo();
//            System.out.println(miningInfo);
            //获取最新的区块
            String bestBlockHash = client.getBestBlockHash();

            Block block = client.getBlock(bestBlockHash);

            allList.add(block);



            Block block_pre = client.getBlock(block.getPreviousBlockHash());
            //获取区块中交易列表
            List<String> txs = block_pre.getTx();
            //Transaction tx = client.getTransaction(txs.get(0));
            //获取解析后的某条交易明细 0表示不解析返回Raw码
           // RawTransaction tx_sample = (RawTransaction)client.getRawTransaction(txs.get(""),1);
            RawTransaction tx_sample = (RawTransaction)client.getRawTransaction("6593e61ecf91cee5914c1a29c441460f37025f34c504cb448628733e3436b878",1);
//            RawTransaction tx_sample = (RawTransaction)client.getRawTransaction("cca7507897abc89628f450e8b1e0c6fca4ec3f7b34cccf55f3f531c659ff4d79",1);

            //获得所有的outs
            List<RawOutput> outs = tx_sample.getVOut();
            RawOutput output = outs.get(1);
            RawInput input = (RawInput)tx_sample.getVIn().get(0);
            PubKeyScript ps=output.getScriptPubKey();
            String sig = input.getScriptSig().getAsm();
            System.out.println("txid: "+tx_sample.getTxId());
            String pkhash = output.getScriptPubKey().getAsm();

            String pk = sig.substring(sig.length()-131,sig.length()).trim();
            System.out.println("sig: "+sig);
            System.out.println("pk: "+pk);
            System.out.println(pkhash);
            System.out.println(output.getScriptPubKey().getAsm());
            //Object txString2 = client.getRawTransaction(txs.get(0),0);

            //从最新的区块开始  向前循环获取每一个区块
//          int i = 2;
//          while(block.getPreviousBlockHash()!=null){
//                System.out.println("第"+i+++"个区块,高度："+block.getHeight());
//                block = client.getBlock(block.getPreviousBlockHash());
//                allList.add(block);
//            }

            //Transaction tx0 = client.getTransaction();
//            System.out.println(bestBlock.getTx().get(0));
//            String rawTransaction = client.getRawTransaction(bestBlock.getTx().get(0));
//           Object s = client.getRawTransaction(bestBlock.getTx().get(0),new Integer(1));

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }


    }
}
