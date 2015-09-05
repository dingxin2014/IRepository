package com.fateking.optimize.performance.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class DataHolder {

	private StackData stackData;
	
	public StackData get(){
		return stackData;
	}
	
	public void set(StackData stackData){
		this.stackData = stackData;
	}
}
