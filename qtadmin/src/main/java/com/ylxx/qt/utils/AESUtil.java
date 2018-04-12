package com.ylxx.qt.utils;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil {
	  /** 
     * 加密--把加密后的byte数组先进行二进制转16进制在进行base64编码 
     * @param sSrc 
     * @param sKey 
     * @return 
     * @throws Exception 
     */  
    public static String encrypt(String sSrc, String sKey) throws Exception {  
        if (sKey == null) {  
            throw new IllegalArgumentException("Argument sKey is null.");  
        }  
        
        byte[] raw = sKey.getBytes("ASCII");  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);  
  
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));  
        String tempStr = parseByte2HexStr(encrypted);  
  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(tempStr.getBytes("UTF-8"));  
    }  
  
	
    /** 
     *解密--先 进行base64解码，在进行16进制转为2进制然后再解码 
     * @param sSrc 
     * @param sKey 
     * @return 
     * @throws Exception 
     */  
    public static String decrypt(String sSrc, String sKey) throws Exception {  
  
        if (sKey == null) {  
            throw new IllegalArgumentException("499");  
        }  
  
        byte[] raw = sKey.getBytes("ASCII"); 
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);  
        BASE64Decoder dc=new BASE64Decoder();
        byte[] encrypted1 = dc.decodeBuffer(sSrc);
  
        String tempStr = new String(encrypted1, "utf-8");  
        encrypted1 = parseHexStr2Byte(tempStr);  
        byte[] original = cipher.doFinal(encrypted1);  
        String originalString = new String(original, "utf-8");  
        return originalString;  
    }  
  
    /** 
     * 将二进制转换成16进制 
     *  
     * @param buf 
     * @return 
     */  
    public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }  
	
    /** 
     * 将16进制转换为二进制 
     *  
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length() / 2];  
        for (int i = 0; i < hexStr.length() / 2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);  
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),  
                    16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }
	
	public static String getGUID(){
		UUID uuid = UUID.randomUUID();
		String s = uuid.toString().toUpperCase();
		return s.replaceAll("-", "");
	}
	
	 public static void main(String[] args) throws Exception {
			String sSrc = "client1";
			String sKey = "5BA63226092A1661E0534E0E130ACFF7";
			String s0 = encrypt(sSrc,sKey);
			String s1 = decrypt(s0, sKey);
			System.out.println("原来的："+sSrc);
			System.out.println("加密后："+s0);
			System.out.println("解密后："+s1);
		}
}
