package com.fateking.optimize.performance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;



@Service("sleep")
public class SleepServiceImpl implements SleepService {
	private static Object o = new Object();
	private List<User> list = new ArrayList<User>();

	public void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
    	SleepService2 sleep2 = (SleepService2)BeanHelper.getBean("sleep2");
    	sleep2.sleep();
    	
    	sleep2.sleep();
	}

	public void consumeMemery() {
		for (int i = 0; i < 1000; i++) {
			list.add(new User());
		}
	}

	public void synchronizedMork() {
		synchronized (this) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
