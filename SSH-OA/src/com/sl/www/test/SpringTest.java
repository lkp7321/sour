package com.sl.www.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	
	@Test
	public void testSpring() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloAction hello=(HelloAction) ac.getBean("helloAction");
	    System.out.println(hello);
	}
	
	@Test
	public void testSessionFactory(){
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	    SessionFactory sf=(SessionFactory) ac.getBean("sessionFactory");
	    System.out.println(sf);
	}
	
	@Test
	public void testService(){
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	    TestService testService=(TestService) ac.getBean("testService");
	    testService.saveTwoUser();
	}

}
