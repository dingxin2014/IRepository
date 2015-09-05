package com.fateking.optimize.performance;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class App 
{
//	public static SinaStock sinaStock;
	
    public static void main( String[] args )
    {
		//Logger logger = Logger.getLogger("");
		
		PropertyConfigurator.configure(System.getProperty("user.dir") + "\\log4j.properties"); 
    	//logger.warn("123xxx");
		
		Thread t = new Thread(new Runnable(){

			public void run() {
		    	SleepService sleep = (SleepService)BeanHelper.getBean("sleep");
		    	sleep.sleep();				
			}
			
		});
		t.start();
		
		Thread t2 = new Thread(new Runnable(){

			public void run() {
		    	SleepService sleep = (SleepService)BeanHelper.getBean("sleep");
		    	sleep.sleep();				
			}
			
		});
		t2.start();
		
    	SleepService sleep = (SleepService)BeanHelper.getBean("sleep");
    	sleep.sleep();
    	
//    	SleepService2 sleep2 = (SleepService2)BeanHelper.getBean("sleep2");
//    	sleep2.sleep(1200);
//    	System.out.println(sleep2.toString());
    }
}
