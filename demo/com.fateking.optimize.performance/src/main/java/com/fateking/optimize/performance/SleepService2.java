package com.fateking.optimize.performance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;



@Service("sleep2")
public class SleepService2  {
	private static Object o = new Object();
	private List<User> list = new ArrayList<User>();

	public void sleep(int x) {
		try {
			Thread.sleep(2000+x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
