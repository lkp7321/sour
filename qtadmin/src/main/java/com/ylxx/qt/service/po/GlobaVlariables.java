package com.ylxx.qt.service.po;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GlobaVlariables {
	public static Socket socket;
	public static BufferedOutputStream bos;
	public static BufferedInputStream bis;
	public static Map<String,Double> instruNowPrice = new HashMap<String,Double>();
	private static int QUEUE_LENGTH = 10000*10;
	//基于内存的阻塞队列  
    public static BlockingQueue<InstrumentPtrice> queue = new LinkedBlockingQueue<InstrumentPtrice>(QUEUE_LENGTH);  
}
