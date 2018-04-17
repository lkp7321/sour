package com.ylxx.fx.utils;
import java.io.InputStream;
import java.util.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ReadHisPrice {
	
	private InputStream in = null;

	private Document doc = null;
	static final Logger log = LoggerFactory.getLogger(ReadHisPrice.class);
	private static volatile ReadHisPrice instance;

	private HashMap<String, List<HisPriceExnm>> productExnmMap = null;
	private List<HisPriceProduct> productList = null;

	private ReadHisPrice() {

	}
	public synchronized static ReadHisPrice getReadHisPriceInstance() {
		if (null == instance) {
			instance = new ReadHisPrice();
			instance.init();
		}
		return instance;
	}
	private void init() {
		in = ReadHisPrice.class.getClassLoader().getResourceAsStream(
				getAPP_HOME() + "props/HisPrice.xml");

		SAXReader reader = new SAXReader();

		try {
			doc = reader.read(in);

			getConfigTemplet();
		} catch (DocumentException e) {
			 log.error(e.getMessage(),e);
		}
	}

	public HashMap<String, List<HisPriceExnm>> getProductExnmMap() {
		
		return productExnmMap;
	}

	public List<HisPriceProduct> getProductList() {
		log.info("产品");
		return productList;
	}

	/**
	 * 读取所有节点名称
	 * 
	 * @param node
	 *            节点名
	 * @return list *
	 * 
	 * **/
	private void getConfigTemplet() {
		List<HisPriceExnm> exnmList = null;

		HisPriceProduct product = null;
		HisPriceExnm exnm = null;

		productExnmMap = new HashMap<String, List<HisPriceExnm>>();
		productList = new ArrayList<HisPriceProduct>();

		Element tradeConfig = (Element) doc.getRootElement().selectSingleNode(
				"//product");
		List list = tradeConfig.elements();
		if (list.size() <= 0)
			return;
		for (Object obj : list) {
			Element objElement = (Element) obj;
			product = new HisPriceProduct();
			if (objElement.elements().size() > 0) {
				String productID = objElement.getName();
				String productName = objElement.selectSingleNode("@name")
						.getText();
				product.setId(productID);
				product.setName(productName);

				productList.add(product);
				exnmList = new ArrayList<HisPriceExnm>();
				List listChild = objElement.elements();
				for (Object objChild : listChild) {
					Element objElementChild = (Element) objChild;
					String exnmName = objElementChild.getText();
					exnm = new HisPriceExnm();
					exnm.setExnmId(exnmName);
					exnm.setExnmName(exnmName);

					exnmList.add(exnm);
				}
				productExnmMap.put(productID, exnmList);
			}
		}

	}

	private static String getAPP_HOME() {

		String appHomePath = "";
		String path = "";
		path = System.getProperty("APP_HOME");
		if (path != null) {
			return path;
		} else
			return appHomePath;

	}

	public static void main(String[] args) {
		// getConfigTemplet();
		
	}
}
