package com.ylxx.qt.utils;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;


public class UrlCheckFilter extends GenericFilterBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlCheckFilter.class);
	
	protected FilterConfig filterConfig = null;
	private String redirectURL = null;
	private String notCheckURLList = null;

	
	@Override
	public void destroy() {
		notCheckURLList = "";
	} 

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			HttpSession session = request.getSession();

			String uri = request.getServletPath()
					+ (request.getPathInfo() == null ? "" : request
							.getPathInfo());
			LOGGER.info(notCheckURLList + " " + uri + " " +notCheckURLList.indexOf(uri));
						
			if (uri.contains(".do") && !checkRequestURIInNotFilterList(uri)) {
				Map<String, String> permissionMap = (Map<String, String>) request
						.getSession().getAttribute("loginUserPermission");
				LOGGER.info("user:{}",permissionMap);
				if (permissionMap == null || permissionMap.keySet().size() == 0) {
					response.sendRedirect(request.getContextPath()
							+ redirectURL);
					return;
				}
			}
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkRequestURIInNotFilterList(String uri) {
		if (!uri.contains(".do"))
			return true;
		return notCheckURLList.indexOf(uri)!=-1;
	}

	private boolean isHavePermission(String uri,
			Map<String, String> permissionMap) {
		boolean isHavePermission = false;
		for (String key : permissionMap.keySet()) {
			String permission = permissionMap.get(key);
			if (permission.contains(uri))
				isHavePermission = true;
		}
		return isHavePermission;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getNotCheckURLList() {
		return notCheckURLList;
	}

	public void setNotCheckURLList(String notCheckURLList) {
		this.notCheckURLList = notCheckURLList;
	}
}
