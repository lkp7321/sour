package com.ylxx.fx.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDPClient {
	private static final Logger log = LoggerFactory.getLogger(UDPClient.class);
	private  ReadStringCodeConfig  readString=new ReadStringCodeConfig();
	private  String  stringCode="";
	
	public void SendSocket() {
	}

	public void SendSocket1() {
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		ReadSocketConfig readsockt = new ReadSocketConfig();

		readsockt.readWainingProperties("priceIp", "pricePort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "Message!";
		log.info("ip:" + strip + " port:" + strport);
		byte[] buffer ;
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

	public void SendSocketB() {
	}

	public void SendSocketB1() {
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		ReadSocketConfig readsockt = new ReadSocketConfig();

		readsockt.readWainingProperties("priceBIp", "priceBPort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "update";
		log.info("ipB:" + strip + " portB:" + strport);
		byte[] buffer ;
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
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		ReadSocketConfig readsockt = new ReadSocketConfig();

		readsockt.readWainingProperties("PdtinfoIp", "PdtinfoPort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "PdtinfoMessage!";
		log.info("Pdtinfoip:" + strip + " Pdtinfoport:" + strport);
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
	public void SendSocketHandPrice(String prod) {
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		ReadSocketConfig readsockt = new ReadSocketConfig();

		readsockt.readWainingProperties("handPriceIp", "handPricePort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "UPDATE|"+prod;
		log.info("ipB:" + strip + " portB:" + strport);
		byte[] buffer ;
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
	// 保证金前置服务
	public void SendSocketBailInfo(String date) {
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		ReadSocketConfig readsockt = new ReadSocketConfig();

		readsockt.readWainingProperties("bailIp", "bailPort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "<Transaction>" +
				"<Transaction_Header>" +
				"<TRSN>52011201209040000000025520000002</TRSN>" +
				"<TRID>M5710</TRID>" +
				"<BHID>9001</BHID>" +
				"<CHNL>1101</CHNL>" +
				"<RQDT>"+date+"</RQDT>" +
				"<RQTM>09:34:00</RQTM>" +
				"<TRTL>90010001</TRTL>" +
				"<TTYN>00005</TTYN>" +
				"<AUTL>11111111</AUTL>" +
				"</Transaction_Header>" +
				"<Transaction_Body>" +
				"<request>" +
				"<OUDT>4155997702065675</OUDT>" +
				"</request>" +
				"</Transaction_Body>" +
				"</Transaction>";
		log.info("bailIp:" + strip + " bailPort:" + strport);
		log.info("content========"+content);
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
	public static void main(String[] args) {

	}

	public void SendAccExPdtRparaSocket() {
		readString.readStringCodeProperties("strcode1");
		stringCode=readString.getStringCode();
		
		ReadSocketConfig readsockt = new ReadSocketConfig();

		//readsockt.readWainingProperties("accExPdtRParaIp", "accExPdtRParaPort");
		readsockt.readWainingProperties("pdtRParaConstForAccIp", "pdtRParaConstForAccPort");
		String strip = readsockt.getSocketip();
		int strport = readsockt.getSocketport();
		String content = "<RELOAD>RATE</RELOAD>";
		log.info("ipB:" + strip + " portB:" + strport);
		Socket socket= null;
		PrintWriter out = null;
		BufferedReader in= null;
		try {
			socket = new Socket(strip,strport);
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), stringCode)), true);
			out.println(content);
			out.flush();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(out!=null){
				out.close();
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
	}
}
