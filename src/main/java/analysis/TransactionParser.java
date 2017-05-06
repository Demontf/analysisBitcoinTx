package analysis;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.domain.Block;
import com.neemre.btcdcli4j.core.domain.RawInput;
import com.neemre.btcdcli4j.core.domain.RawOutput;
import com.neemre.btcdcli4j.core.domain.RawTransaction;
import dao.EdgesDao;
import dao.ThePublicKeyDao;
import dao.TransactionKeyDao;
import domain.Edges;
import domain.ThePublicKey;
import domain.TransactionKey;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demontf on 17/2/8.
 */
public class TransactionParser {

    private BtcdClient client;

    //
    public TransactionParser(BtcdClient client){
         this.client = client;
    }

    public void parserByBlock(Block block){
        List<String> txs = block.getTx();
        TransactionKeyDao tkDao = new TransactionKeyDao();
        EdgesDao eDao = new EdgesDao();
        ThePublicKeyDao tpDao = new ThePublicKeyDao();
        try {
                for(String s:txs) {

                    RawTransaction tx_sample = (RawTransaction) client.getRawTransaction(s, 1);
                    List<RawInput> rawInputs = tx_sample.getVIn();
                    List<RawOutput> rawOutputs = tx_sample.getVOut();
                    //#1 save transaction
                    TransactionKey transactionKey = new TransactionKey(s,rawInputs.size()
                        ,rawOutputs.size(),tx_sample.getBlockTime());
                    //the tx exist, have been saved, continue
                    int tk_id=tkDao.getTransactionId(s);
                    if(tk_id!=0){
                        continue;
                    }
                    tk_id = tkDao.add(transactionKey);
                    //if(tk_id==0)continue;

                    //save edges
                    //#2 save output type=2
                    for(RawOutput r:rawOutputs){
                        // save the pk
                        String hash = getPkhashFormAsm(r.getScriptPubKey().getAsm());
                        ThePublicKey pk = new ThePublicKey("",hash);
                        int return_id = tpDao.getPublicKeyId(hash);
                        if( return_id == 0){
                            return_id = tpDao.add(pk);
                        }
                        Edges edge = new Edges(tk_id,return_id,2,r.getValue().doubleValue());
                        eDao.add(edge);
                    }

                    //#3 save Input type = 1
                    for(RawInput i:rawInputs){
                        // deal with coinbase
                        if(i.getCoinbase()!=null || i.getTxId()==null || i.getScriptSig() ==null){

                            Edges edge = new Edges(tk_id,0,1,-1);
                            eDao.add(edge);
                            // todo output save miners address
                        }else{

                            RawOutput rout = getVout(i.getTxId(),i.getVOut());

                            if(rout!=null){
                                //save thepk
                                String hash = getPkhashFormAsm(rout.getScriptPubKey().getAsm());
                                ThePublicKey pk = tpDao.getPublicKeyByHash(hash);
                                int pk_id=0;
                                if(pk==null){
                                    pk = new ThePublicKey("",hash);
                                    String str=i.getScriptSig().getAsm();
                                    pk.setPublicKey(str.substring(str.length()-130,str.length()));
                                    pk_id=tpDao.add(pk);
                                }else{
                                    String str=i.getScriptSig().getAsm();
                                    pk.setPublicKey(str.substring(str.length()-130,str.length()));
                                    tpDao.update(pk);
                                    pk_id = pk.getId();
                                }


                                //save edges input //type 1
                                if(pk_id!=0){
                                    Edges edge = new Edges(tk_id,pk_id,1,rout.getValue().doubleValue());
                                    eDao.add(edge);
                                }

                            }
                        }

                    }

                 }
              System.out.println("transaction:"+txs.size()+" been deal with");
            } catch (BitcoindException e) {
                e.printStackTrace();
            } catch (CommunicationException e) {
                e.printStackTrace();
            }

            //关闭数据库连接
            eDao.closeConnect();
            tkDao.closeConnect();
            tpDao.closeConnect();

    }

    public void parserByHeight(int height){

        TransactionKeyDao tkDao = new TransactionKeyDao();
        EdgesDao eDao = new EdgesDao();
        ThePublicKeyDao tpDao = new ThePublicKeyDao();
        try {
            Block block = getBlock(client.getBlockHash(height));
            List<String> txs = block.getTx();
            for(String s:txs) {

                RawTransaction tx_sample = (RawTransaction) client.getRawTransaction(s, 1);
                List<RawInput> rawInputs = tx_sample.getVIn();
                List<RawOutput> rawOutputs = tx_sample.getVOut();
                //#1 save transaction
                TransactionKey transactionKey = new TransactionKey(s,rawInputs.size()
                        ,rawOutputs.size(),tx_sample.getBlockTime());
                //the tx exist, have been saved, continue
                int tk_id=tkDao.getTransactionId(s);
                if(tk_id!=0){
                    continue;
                }
                tk_id = tkDao.add(transactionKey);
                //if(tk_id==0)continue;

                //save edges
                //#2 save output type=2
                for(RawOutput r:rawOutputs){
                    // save the pk
                    String hash = getPkhashFormAsm(r.getScriptPubKey().getAsm());
                    ThePublicKey pk = new ThePublicKey("",hash);
                    int return_id = tpDao.getPublicKeyId(hash);
                    if( return_id == 0){
                        return_id = tpDao.add(pk);
                    }
                    Edges edge = new Edges(tk_id,return_id,2,r.getValue().doubleValue());
                    eDao.add(edge);
                }

                //#3 save Input type = 1
                for(RawInput i:rawInputs){
                    // deal with coinbase
                    if(i.getCoinbase()!=null || i.getTxId()==null || i.getScriptSig() ==null){

                        Edges edge = new Edges(tk_id,0,1,-1);
                        eDao.add(edge);
                        // todo output save miners address
                    }else{

                        RawOutput rout = getVout(i.getTxId(),i.getVOut());

                        if(rout!=null){
                            //save thepk
                            String hash = getPkhashFormAsm(rout.getScriptPubKey().getAsm());
                            ThePublicKey pk = tpDao.getPublicKeyByHash(hash);
                            int pk_id=0;
                            if(pk==null){
                                pk = new ThePublicKey("",hash);
                                String str=i.getScriptSig().getAsm();
                                pk.setPublicKey(str.substring(str.length()-130,str.length()));
                                pk_id=tpDao.add(pk);
                            }else{
                                pk.setPublicKey(i.getScriptSig().getAsm());
                                tpDao.update(pk);
                                pk_id = pk.getId();
                            }


                            //save edges input //type 1
                            if(pk_id!=0){
                                Edges edge = new Edges(tk_id,pk_id,1,rout.getValue().doubleValue());
                                eDao.add(edge);
                            }

                        }
                    }

                }

            }
        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }

        //关闭数据库连接
        eDao.closeConnect();
        tkDao.closeConnect();
        tpDao.closeConnect();

    }


    /**
     * according txid & index find the output
     * @param txid
     * @param index
     * @return
     */
    public RawOutput getVout(String txid,int index){
        try {
            RawTransaction tx = (RawTransaction) client.getRawTransaction(txid,1);
           // System.out.println("txid: "+txid+" txsize: "+tx.getVOut().size()+" index: "+index);
            return tx.getVOut().get(index);
        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * use regex get the pkhash from the output script asm
     * @param asm
     * @return
     */
    public  String getPkhashFormAsm(String asm){
        Pattern p = Pattern.compile("[a-z0-9]{40}");
        Matcher m = p.matcher(asm);
        if(m.find())return m.group(0);
        else return "";
    }

    /**
     * Get the block of given hash
     * @param blockHash Default is the latest hash
     * @return Block
     */
    public Block getBlock(String blockHash){
        Block block = null;
        try {
            if(blockHash==null || blockHash.equals("")){
                blockHash = client.getBestBlockHash();
            }
            block = client.getBlock(blockHash);
        } catch (BitcoindException e) {
            e.printStackTrace();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }

        return block;
    }




}
