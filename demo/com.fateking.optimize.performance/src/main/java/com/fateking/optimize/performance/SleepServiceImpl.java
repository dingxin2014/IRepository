package com.fateking.optimize.performance;

import java.util.ArrayList;
import java.util.List;

public class SleepServiceImpl implements SleepService {
	private static Object o = new Object();
	private List<User> list = new ArrayList<User>();

	public void sleep() {
		try {
			Thread.sleep(5000);
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
