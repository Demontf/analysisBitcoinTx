package util;


import domain.ThePublicKey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by demontf on 17/3/26.
 */

/**
 * 实现将任意类写入文件
 */
public class WriteToFile {

    private List lists = new ArrayList();
    private String pathAndname;
    File file;
    String[] fieldNames;
    WriteToFile(List lists, String pathAndname){
        this.lists = lists;
        this.pathAndname = pathAndname;
        file = new File(pathAndname);

    }

    public void write(){
        try{
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(Object o:lists){
                bw.write(getFiledValues(o));
                bw.newLine();
            }
            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     * */
    public String getFiledValues(Object o){
        if(fieldNames==null || fieldNames.length==0){
            fieldNames=this.getFiledName(o);
        }
        //Object[] value=new Object[fieldNames.length];
        StringBuffer str = new StringBuffer();
        str.append(this.getFieldValueByName(fieldNames[0], o));
        for(int i=1;i<fieldNames.length;i++){
            str.append(","+this.getFieldValueByName(fieldNames[i], o));
        }
        return str.toString();
    }

    /**
     * 获取属性名数组
     * */
    private String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 根据属性名获取属性值
     * */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String fileName = "/Users/demontf/Documents/demontf/PHD@BUPT/blockchain/data/test.txt";

        List<ThePublicKey> lists = new ArrayList<ThePublicKey>();
        ThePublicKey tpk = new ThePublicKey("publickey1","hash160");
        tpk.setAddress("address1");
        lists.add(tpk);
//        ThePublicKey tpk2 = new ThePublicKey();
//        tpk2.setPublicHash160("hash160");
//        tpk2.setPublicKey("publickey2");
//        tpk2.setAddress("address2");
//        lists.add(tpk2);
        WriteToFile w = new WriteToFile(lists,fileName);
        w.write();

    }
}
