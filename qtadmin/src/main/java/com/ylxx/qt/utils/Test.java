package com.ylxx.qt.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;



public class Test {
	 public static void main(String[] args) {  
	        try {  
	            System.out.println(recognizeText(new File("E:/testyzm/gg/test1.png"), "png"));  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	
	
	public static synchronized String recognizeText(File imageFile, String imageFormat) throws Exception {  
       String tessPath="E:/Tesseract/Tesseract-OCR";  
       File outputFile = new File(imageFile.getParentFile(), "output");  
       StringBuffer strB = new StringBuffer();  
       String[] cm=new String[]{tessPath+"/tesseract",imageFile.getAbsolutePath(),outputFile.getAbsolutePath(),"-psm","10","digits"};  
       Process pb = Runtime.getRuntime().exec(cm);  
       int w = pb.waitFor();  
       if (w == 0) {  
           BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+ ".txt"), "UTF-8"));  
           String str;  
           while ((str = in.readLine()) != null) {  
              strB.append(str);  
           }  
           System.out.println("读取完成 结果是 "+strB.toString());  
           in.close();  
       } else {  
           String msg;  
           switch (w) {  
           case 1:  
              msg = "Errors accessing files. There may be spaces in your image's filename.";  
              break;  
           case 29:  
              msg = "Cannot recognize the image or its selected region.";  
              break;  
           case 31:  
              msg = "Unsupported image format.";  
              break;  
           default:  
              msg = "Errors occurred.";  
           }  
//         tempImage.delete();  
           throw new RuntimeException(msg);  
       }  
//     new File(outputFile.getAbsolutePath() + ".txt").delete();  
       return strB.toString();  
    }  
   
}
