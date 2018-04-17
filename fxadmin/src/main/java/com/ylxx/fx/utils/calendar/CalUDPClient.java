package com.ylxx.fx.utils.calendar;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylxx.fx.utils.ReadStringCodeConfig;

public class CalUDPClient {
	private static final Logger log = LoggerFactory.getLogger(CalUDPClient.class);
	private ReadStringCodeConfig  readString=new ReadStringCodeConfig();
	private String  stringCode="";
	public void SendSocket() {
	}

	public void SendSocket1() {
		ReadCalConfig readsockt = new ReadCalConfig();
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		readsockt.readWainingProperties("priceIp", "pricePort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "Message!";
		log.info("ip:" + strip + " port:" + strport);
		byte[] buffer;
		
		try {
			buffer = content.getBytes(stringCode);
			DatagramSocket datagramSocket = new DatagramSocket();

			InetAddress address = InetAddress.getByName(strip);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					address, strport);
			datagramSocket.send(packet);
			datagramSocket.close();

		}catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void SendSocketB() {
	}

	public void SendSocketB1() {
		
		ReadCalConfig readsockt = new ReadCalConfig();
		
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		readsockt.readWainingProperties("priceBIp", "priceBPort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "update";
		log.info("ipB:" + strip + " portB:" + strport);
		byte[] buffer;
		try {
			buffer = content.getBytes(stringCode);
			DatagramSocket datagramSocket = new DatagramSocket();

			InetAddress address = InetAddress.getByName(strip);

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					address, strport);
			datagramSocket.send(packet);
			datagramSocket.close();

		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	// 本地产品配置服务
	public void SendSocketPdtInfo() {

		ReadCalConfig readsockt = new ReadCalConfig();

		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		readsockt.readWainingProperties("fxipIP", "fxipport");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "update";
		log.info("fxipIP:" + strip + " fxipport:" + strport+" 字符串:"+content);
		
		byte[] buffer ;
		try {
			buffer = content.getBytes(stringCode);
			DatagramSocket datagramSocket = new DatagramSocket();
			
			InetAddress address = InetAddress.getByName(strip);
            
			log.info("主机为："+address);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					address, strport);
			datagramSocket.send(packet);
			datagramSocket.close();

		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public static void main(String[] args) {

	}
}
