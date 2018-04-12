package com.ylxx.qt.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHelper implements ApplicationContextAware {

	private static ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}

	/**
	 * 根据类从spring上下文获取bean。
	 */
	public static <T> T getBean(Class<T> cls) {
		return ctx.getBean(cls);
	}

	public static Object getBean(String id) {
		if (null == ctx) {
			throw new NullPointerException("ApplicationContext is null");
		}
		return ctx.getBean(id);
	}

}
