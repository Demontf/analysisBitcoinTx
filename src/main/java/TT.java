import analysis.TransactionParser;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import org.apache.http.impl.client.CloseableHttpClient;
import util.MyThread;
import util.ResourceUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by demontf on 2017/4/11.
 */

/**
 * @// TODO: 2017/4/19 识别手续费，需要存储block第一笔交易coinbase地址 
 */
public class TT {

    public static void main(String[] args){


        try {
            CloseableHttpClient httpProvider = ResourceUtils.getHttpProvider();
            Properties nodeConfig = ResourceUtils.getNodeConfig();
            BtcdClient client = new BtcdClientImpl(httpProvider, nodeConfig);
            TransactionParser parser = new TransactionParser(client);
            System.setProperty("https.protocols", "TLSv1.1");
            int number = Integer.parseInt((String)nodeConfig.getProperty("block.number"));
            //ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
            for(int i=number;i>1;i--){
                fixedThreadPool.execute(new MyThread(i,nodeConfig));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }


    }
}
