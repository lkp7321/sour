package com.ylxx.qt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file")
public class FileController {

	@RequestMapping(value = "/downpc.do")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath("DownloadFile");
		String filename = "EQTrader.exe";
		File file = new File(path, filename);
		try {
			InputStream in = new FileInputStream(file);
			// 设置响应头，对文件进行url编码
			filename = URLEncoder.encode(filename, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			response.setContentLength(in.available());

			// 第三步：老套路，开始copy
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/downapk.do")
	public void downloadApk(HttpServletRequest request,HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath("DownloadFile");
		System.out.println(path);
		String filename = "EQTrader.apk";
		File file = new File(path, filename);
		try {
			InputStream in = new FileInputStream(file);
			// 设置响应头，对文件进行url编码
			filename = URLEncoder.encode(filename, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			response.setContentLength(in.available());

			// 第三步：老套路，开始copy
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/downsoft.do")
	public void downloadSoftWord(HttpServletRequest request,HttpServletResponse response) {
		byte[] body = null;
		String path = request.getSession().getServletContext().getRealPath("DownloadFile");
		String filename = "NET Framework 4.5.2.exe";
		File file = new File(path, filename);
		try {
			InputStream in = new FileInputStream(file);
			// 设置响应头，对文件进行url编码
			filename = URLEncoder.encode(filename, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			response.setContentLength(in.available());

			// 第三步：老套路，开始copy
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
