package util;

import analysis.TransactionParser;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Properties;

/**
 * Created by demontf on 2017/5/3.
 */
public class MyThread implements Runnable{

    private  int index;
    CloseableHttpClient httpProvider;
    Properties nodeConfig;

    public MyThread(int index,Properties nodeConfig){
        this.index = index;
        this.nodeConfig = nodeConfig;
        this.httpProvider =  ResourceUtils.getHttpProvider();
    }
    public void run() {
        try {
            System.out.println("height:"+index+" start");
            BtcdClient client = new BtcdClientImpl(httpProvider, nodeConfig);
            TransactionParser parser = new TransactionParser(client);
            parser.parserByHeight(index);
            System.out.println("height:"+index+" finish");
        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }
    }
}
