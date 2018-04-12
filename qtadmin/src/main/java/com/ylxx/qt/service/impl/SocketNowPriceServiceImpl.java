package com.ylxx.qt.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.service.AnalysisPriceInfoService;
import com.ylxx.qt.service.IPriceSheetService;
import com.ylxx.qt.service.SocketNowPriceService;
import com.ylxx.qt.service.po.InstrumentPtrice;

@Service("socketnowpriceservice")
public class SocketNowPriceServiceImpl implements SocketNowPriceService{
	@Override
	public Socket requestNowPrice() {
		try {
			Socket socket = new Socket("221.122.75.230",18084);
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            //读取数据
            int len;
            byte[] inByte = new byte[1024];
            if (bis != null) {
                len = bis.read(inByte);
                if (len <= 0) {
                    //return null;
                }
                String result = new String(inByte, 0, len, "utf-8");
                while (!result.substring(result.length() - 3, result.length()).contains("###")) {
                    len = bis.read(inByte);
                    result = result + new String(inByte, 0, len, "utf-8");
                }
                System.out.println("99999999999999999999999999"+ "---" + result.getBytes()[0] + "--" + result.getBytes()[1] + result);
            }
            
            //登录
            String longin_str = "{\"Account\": \"admin\", \"PassWord\": \"123456\",\"CanSendTradeField\":\"0\"}###";
            byte[] new_login = longin_str.getBytes();
            byte[] newByte_login = new byte[new_login.length + 2];
            newByte_login[0] = 1;
            newByte_login[1] = 0;
            for (int i = 0; i < new_login.length; i++) {
            	newByte_login[i + 2] = new_login[i];
            }
            if (bos != null) {
                bos.write(newByte_login);
                bos.flush();
            }   
            //读取数据
            int len2;
            byte[] inByte2 = new byte[1024];
            if (bis != null) {
                len2 = bis.read(inByte2);
                if (len2 <= 0) {
                    //return null;
                }
                String result = new String(inByte2, 0, len2, "utf-8");
                while (!result.substring(result.length() - 3, result.length()).contains("###")) {
                    len = bis.read(inByte2);
                    result = result + new String(inByte2, 0, len, "utf-8");
                }
                System.out.println("888888888888888888888888888"+ "---" + result.getBytes()[0] + "--" + result.getBytes()[1] + result);
                String[] split = result.split("###");
                for (int i = 0; i < split.length; i++) {
                    byte[] bytes = split[i].getBytes();
                    byte b1 = bytes[0];
                    final byte b2 = bytes[1];
                    String newStr = new String(bytes, 2, bytes.length - 2, "utf-8");
                    System.out.println("---------------------------------------------------------"+newStr);
                }
            }
            
            bis.close();
            bos.close();
            return socket;
            //socket.close();
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		} 
	}

}
