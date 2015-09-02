package com.fateking.optimize.performance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanHelper {

	private BeanHelper() {
	}

	private static ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");

	public static Object getBean(String beanName) {
		return ac.getBean(beanName);
	}

}
