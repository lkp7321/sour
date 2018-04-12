package com.ylxx.qt.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TestRecognize {
	
	/*public static void main(String[] args){
		 File file = new File("E:/testyzm/gg");
		 File[] fileList = file.listFiles();
		 InputStream is;
		 for(int i=0;i<fileList.length;i++){
			try {
				is = new FileInputStream(fileList[i]);
				BufferedImage bi=ImageIO.read(is);
				//BufferedImage gg = reline(bi);
				BufferedImage tt = removeLine(bi,3);
				ImageIO.write(tt, "png", new File("E:/testyzm/gg/test1.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		 }
		
	}*/

	
	
	/*public static BufferedImage reline(BufferedImage curImg) {
        if (curImg != null) {
            int width = curImg.getWidth();
            int height = curImg.getHeight();
            int px = 3;
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int argb = curImg.getRGB(x, y);
                    int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
                    int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
                    int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
                    int sum = r + g + b;
                    if (!map.containsKey(sum)) {
                        map.put(sum, 1);
                    } else {
                        int num = map.get(sum);
                        map.remove(sum);
                        map.put(sum, num + 1);
                    }
                }
            }
            List<Integer> list = new ArrayList<Integer>();
            for (Integer in : map.keySet()) {
                // map.keySet()返回的是所有key的值
                Integer n = map.get(in);// 得到每个key多对用value的值
                list.add(n);
            }
            Collections.sort(list);
            // 四种颜色的rgb
            int num1 = 0;
            int num2 = 0;
            int num3 = 0;
            int num4 = 0;
            if (list.size() > 4) {
                num1 = list.get(list.size() - 5);
                num2 = list.get(list.size() - 4);
                num3 = list.get(list.size() - 3);
                num4 = list.get(list.size() - 2);
            }
            List<Integer> keylist = new ArrayList<Integer>();
            //四中颜色
            for (Integer key : map.keySet()) {
                if (map.get(key) == num1 || map.get(key) == num2 || map.get(key) == num3 || map.get(key) == num4) {
                    keylist.add(key);
                }
            }
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int argb = curImg.getRGB(x, y);
                    int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
                    int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
                    int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
                    int sum = r + g + b;
                    int sum1 = 0;
                    int sum2 = 0;
                    int sum3 = 0;
                    int sum4 = 0;
                    int sum5 = 0;
                    int sum6 = 0;
                    boolean flag = true;
                    for (int i = 1; i <= px && y + i < height && y - i > 0 && x - i > 0 && x + i < width; i++) {
                        int upargb = curImg.getRGB(x, y - i);
                        int endargb = curImg.getRGB(x, y + i);
                        int rightupargb = curImg.getRGB(x + i, y + i);
                        int leftupargb = curImg.getRGB(x - i, y + i);
                        int leftdownargb = curImg.getRGB(x - i, y - i);
                        int rightdownargb = curImg.getRGB(x + i, y - i);
                        int r1 = (int) (((upargb >> 16) & 0xFF) * 1.1 + 30);
                        int g1 = (int) (((upargb >> 8) & 0xFF) * 1.1 + 30);
                        int b1 = (int) (((upargb >> 0) & 0xFF) * 1.1 + 30);
                        sum1 = r1 + g1 + b1;
                        int r2 = (int) (((endargb >> 16) & 0xFF) * 1.1 + 30);
                        int g2 = (int) (((endargb >> 8) & 0xFF) * 1.1 + 30);
                        int b2 = (int) (((endargb >> 0) & 0xFF) * 1.1 + 30);
                        sum2 = r2 + g2 + b2;
                        int r3 = (int) (((rightupargb >> 16) & 0xFF) * 1.1 + 30);
                        int g3 = (int) (((rightupargb >> 8) & 0xFF) * 1.1 + 30);
                        int b3 = (int) (((rightupargb >> 0) & 0xFF) * 1.1 + 30);
                        sum3 = r3 + g3 + b3;
                        int r4 = (int) (((leftupargb >> 16) & 0xFF) * 1.1 + 30);
                        int g4 = (int) (((leftupargb >> 8) & 0xFF) * 1.1 + 30);
                        int b4 = (int) (((leftupargb >> 0) & 0xFF) * 1.1 + 30);
                        sum4 = r4 + g4 + b4;
                        int r5 = (int) (((leftdownargb >> 16) & 0xFF) * 1.1 + 30);
                        int g5 = (int) (((leftdownargb >> 8) & 0xFF) * 1.1 + 30);
                        int b5 = (int) (((leftdownargb >> 0) & 0xFF) * 1.1 + 30);
                        sum5 = r5 + g5 + b5;
                        int r6 = (int) (((rightdownargb >> 16) & 0xFF) * 1.1 + 30);
                        int g6 = (int) (((rightdownargb >> 8) & 0xFF) * 1.1 + 30);
                        int b6 = (int) (((rightdownargb >> 0) & 0xFF) * 1.1 + 30);
                        sum6 = r6 + g6 + b6;

                        if (keylist.contains(sum1) || keylist.contains(sum2) || keylist.contains(sum3)
                                || keylist.contains(sum4) || keylist.contains(sum5) || keylist.contains(sum6)) {
                            flag = false;
                        }
                    }
                    if (!(keylist.contains(sum)) && flag) {
                        curImg.setRGB(x, y, Color.white.getRGB());
                    }
                }
            }

        }
        return curImg;
    }*/
	
	
	
	public static BufferedImage removeLine(BufferedImage img, int px) {
        if (img != null) {
            int width = img.getWidth();
            int height = img.getHeight();

            for (int x = 0; x < width; x++) {
                List<Integer> list = new ArrayList<Integer>();
                for (int y = 0; y < height; y++) {
                	if(isWhite(img.getRGB(x, y))){
                		img.setRGB(x, y, Color.black.getRGB());
                	}else{
                		img.setRGB(x, y, Color.white.getRGB());
                	}
                }
            }
        }
        return img;
    }
	public static boolean isWhite(int rgb){
	    Color c = new Color(rgb);
	    int b = c.getBlue();
	    int r = c.getRed();
	    int g = c.getGreen();
	    int sum = r+g+b;
	    if(sum>650){
	        return true;
	    }
	    return false;
	    }

    public static boolean isBlack(int rgb){
    Color c = new Color(rgb);
    int b = c.getBlue();
    int r = c.getRed();
    int g = c.getGreen();
    int sum = r+g+b;
    if(b>240 && r>240 && g>240){
        return true;
    }
    return false;
    //sum的值越小（最小为零，黑色）颜色越重，
    //sum的值越大（最大值是225*3）颜色越浅，
    //sum的值小于10就算是黑色了.
    }
}
