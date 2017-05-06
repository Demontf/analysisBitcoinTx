package analysis;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import com.neemre.btcdcli4j.core.domain.RawInput;
import com.neemre.btcdcli4j.core.domain.RawOutput;
import com.neemre.btcdcli4j.core.domain.RawTransaction;
import domain.Edges;
import domain.TransactionKey;
import org.apache.http.impl.client.CloseableHttpClient;
import util.MyTools;
import util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by demontf on 17/3/29.
 */
public class ReadTransaction {

    BtcdClient client = null;

    public ReadTransaction(){
        CloseableHttpClient httpProvider = ResourceUtils.getHttpProvider();
        try {
            Properties nodeConfig = ResourceUtils.getNodeConfig();
            BtcdClient client = new BtcdClientImpl(httpProvider, nodeConfig);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }

    }

    public List<Edges> FindEdges(TransactionKey tk){
        RawTransaction raw_tx;
        List<Edges> edges_lists = new ArrayList<Edges>();
        try {
            raw_tx = (RawTransaction)client.getRawTransaction(tk.getTransactionHash(),1);
            List<RawInput> inputs = raw_tx.getVIn();
            List<RawOutput> outputs = raw_tx.getVOut();

        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }

        return edges_lists;
    }



    public Edges analysisInput(RawInput Ri){
        if(Ri.getCoinbase()!=null){
            String asm = Ri.getScriptSig().getAsm();
            //为什么 144character 应该是130 65byte啊
            String pk = asm.split(" ")[0];
            String sig = asm.split(" ")[1];
        }else{

        }
        //获取金额来源的hash
        try {

            RawTransaction rt =(RawTransaction) client.getRawTransaction(Ri.getTxId(),1);
            RawOutput output = rt.getVOut().get(Ri.getVOut());
            //sender 的hash

            String pk_hash = MyTools.getPkHashFromScript(output.getScriptPubKey().getAsm());

        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }
        return null;

    }

}
