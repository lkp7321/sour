package com.ylxx.fx.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtil {
    private String fileName;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    public OutputStream getOut(){
        try {
            return resp.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public ServletUtil(HttpServletResponse resp){
        this.resp = resp;
    }
    public ServletUtil(String fileName,
            HttpServletRequest req,
            HttpServletResponse resp){
        this.fileName = fileName;
        this.req = req;
        this.resp = resp;
    }
    public void poiExcelServlet(){
        resp.setContentType("application/vnd.ms-excel");
        String contentDisposition = "";
        try {
        	if(fileName!=null && !fileName.equals("")){
        		if (req.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                    contentDisposition = "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1")+ "\".xls";// firefox浏览器
                } else {
                    contentDisposition = "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\".xls";// IE浏览器
                }
        	}
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        resp.setHeader("Content-Disposition", contentDisposition);
        resp.setCharacterEncoding("UTF-8");
    }
    public void zipExcelServlet() {
    	String filename = null;
		try {
			filename = URLEncoder.encode(fileName+".zip", "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        resp.setHeader("Content-Disposition", "attachment; filename =" + filename + ";filename*=utf-8''" + filename);
    }
}
