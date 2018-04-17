package com.ylxx.fx.utils.price;

import java.util.Random;

public class GetMac {
	
	public String buildRandomID() {
		String randStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 写入所希望的所有的字母A-Z,a-z,0-9
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int randStrLength = 5; // 生成的随机数的长度
		generateRandStr.append("FX");
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(62);
			generateRandStr.append(randStr.substring(randNum, randNum + 1));
		}
		return new String(generateRandStr);
	}

	public String buildRandomPass() {
		String randStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 写入所希望的所有的字母A-Z,a-z,0-9
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int randStrLength = 8; // 生成的随机数的长度
		for (int i = 0; i < randStrLength; i++) {
			int randNum = rand.nextInt(62);

			generateRandStr.append(randStr.substring(randNum, randNum + 1));
		}
		return new String(generateRandStr);
	}
}
