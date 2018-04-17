package com.ylxx.fx.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class EncodingFilter extends OncePerRequestFilter {

	private String encode = "UTF-8";// 默认UTF-8编码

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 设置request编码
		request.setCharacterEncoding(encode);
		// 设置相应信息
		response.setContentType("text/html;charset=" + encode);
		response.setCharacterEncoding(encode);
		filterChain.doFilter(new CharacterEncodingRequest(request), response);

	}

}

/**
 * 对Get方式传递的请求参数进行编码
 */
class CharacterEncodingRequest extends HttpServletRequestWrapper {
	private HttpServletRequest request = null;

	public CharacterEncodingRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	/**
	 * 对参数重新编码
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value == null)
			return null;
		String method = request.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			try {
				value = new String(value.getBytes("ISO8859-1"), request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

}
