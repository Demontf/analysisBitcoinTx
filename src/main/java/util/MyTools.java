package util;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demontf on 17/3/29.
 */
public class MyTools {

    public static String getPkHashFromScript(String s) {

        String regex = "[0-9a-f]{40}";//40个16进制数
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(s);
        if (matcher.find()) {
            return matcher.group();
        }
        return "error";
    }

    public static String SHA256(final String strText)
    {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(Hex.decode(strText));
//            for(byte b:md.digest())
//                System.out.format("%02X",b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String res = new String(Hex.encode(md.digest()));
        return res;
    }


    public static String RIPEMD160(final String strText ){
        byte[] r = Hex.decode(strText);
        RIPEMD160Digest d = new RIPEMD160Digest();
        d.update (r, 0, r.length);
        byte[] o = new byte[d.getDigestSize()];
        d.doFinal (o, 0);
        String res = new String(Hex.encode(o));
        return res;
    }

}
