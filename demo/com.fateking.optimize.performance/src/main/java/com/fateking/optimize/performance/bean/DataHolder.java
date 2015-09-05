package com.fateking.optimize.performance.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class DataHolder {
 

	private static final ThreadLocal<StackData> threadLocal = new ThreadLocal<StackData>(); 
	
	public StackData get(){
		return threadLocal.get();
	}
	
	public void set(StackData stackData){
		threadLocal.set(stackData);
	}

	public String getThreadId() {
		return Thread.currentThread().toString();
	}

	
	
}
