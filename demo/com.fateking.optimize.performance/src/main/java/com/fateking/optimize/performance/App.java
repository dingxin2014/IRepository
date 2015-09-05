package com.fateking.optimize.performance;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class App 
{
//	public static SinaStock sinaStock;
	
    public static void main( String[] args )
    {
		Logger logger = Logger.getLogger("");
		
		PropertyConfigurator.configure(System.getProperty("user.dir") + "\\log4j.properties"); 
    	//logger.warn("123xxx");
    	SleepService sleep = (SleepService)BeanHelper.getBean("sleep");
    	sleep.sleep();
    	
//    	SleepService2 sleep2 = (SleepService2)BeanHelper.getBean("sleep2");
//    	sleep2.sleep(1200);
//    	System.out.println(sleep2.toString());
    }
}
