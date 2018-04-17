package com.ylxx.fx.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SocketLocal {
	private ReadStringCodeConfig  readString=new ReadStringCodeConfig();
	private String stringCode="";
	private static final Logger log = LoggerFactory.getLogger(SocketLocal.class);
	public String sendPutPrice(String tempstrIP, int tempintPort,
			String sendString) {
		
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		Socket client = null;
		DataOutputStream out = null;
		BufferedReader in = null;
		try {
			// logger.info("strIP="+tempstrIP+"	intPort=:"+tempintPort);
			client = new Socket(tempstrIP, tempintPort);
			client.setSoTimeout(10000);
			in = new BufferedReader(new InputStreamReader(client
					.getInputStream(),stringCode));
			out = new DataOutputStream((client.getOutputStream()));
			out.write(sendString.getBytes(stringCode));

			out.flush();
			client.shutdownOutput();

			String temp = "";
			while (true) {
				temp = in.readLine();
				if(null==temp)
					break;
				if (temp.endsWith("end")) {
					break;
				}
				temp += temp;
			}
			return temp;

		} catch (UnknownHostException e1) {
			 log.error(e1.getMessage(),e1);
			return "false";
		} catch (IOException e1) {
			 log.error(e1.getMessage(),e1);
			return "false";
		} catch (Exception e) {
			 log.error(e.getMessage(),e);
			return "false";
		} finally {
			if (null != out)
				try {
					out.close();
					out=null;
				} catch (IOException e1) {
					 log.error(e1.getMessage(),e1);
				}
			if (null != in)
				try {
					in.close();
					in=null;
				} catch (IOException e1) {
					 log.error(e1.getMessage(),e1);
				}
			if (null != client)
				try {
					client.close();
					client=null;
				} catch (IOException e) {
					 log.error(e.getMessage(),e);
				}
		}
	}
}
