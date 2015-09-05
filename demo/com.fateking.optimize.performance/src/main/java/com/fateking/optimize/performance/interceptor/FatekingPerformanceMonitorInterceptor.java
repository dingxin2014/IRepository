package com.fateking.optimize.performance.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.fateking.optimize.performance.bean.DataHolder;
import com.fateking.optimize.performance.bean.StackData;
import com.fateking.optimize.performance.bean.StackEntry;

public class FatekingPerformanceMonitorInterceptor implements com.fateking.optimize.performance.interceptor.PerformanceMonitorInterceptor {

	@Autowired
	private DataHolder dataHolder;
	
	private static Logger logger = Logger.getLogger(FatekingPerformanceMonitorInterceptor.class.getName());
	
	public void before() {
	}

	public void after() {
	}

	public void afterReturning() {
	}

	public void afterThrowing() {
	}

	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		

		//before
		StackData data = dataHolder.get();   
        StackEntry currentEntry = new StackEntry(pjp.getSignature().toString(),System.currentTimeMillis());      
        if (dataHolder.get() == null) {      
            data = new StackData();      
            data.root = currentEntry;      
            data.level = 1;    
            dataHolder.set(data);      
        } else{
        	StackEntry parent = data.currentEntry;      
            currentEntry.parent=parent;      
            parent.child.add(currentEntry);      
        }
        data.currentEntry = currentEntry;      
        currentEntry.level=data.level;    
        data.level++;
		
		
        Object retVal = pjp.proceed(pjp.getArgs());  
        
        //after      
        StackEntry self = data.currentEntry;    
        self.endTime = System.currentTimeMillis();    
        data.currentEntry = self.parent;    
        data.level--;    
        printStack(data);   
        
        return retVal;   
	}
	
	private void aroundStart(String path){
		StackData data = dataHolder.get();   
        StackEntry currentEntry = new StackEntry(path,System.currentTimeMillis());      
        if (dataHolder.get() == null) {      
            data = new StackData();      
            data.root = currentEntry;      
            data.level = 1;    
            dataHolder.set(data);      
        } else{
        	StackEntry parent = data.currentEntry;      
            currentEntry.parent=parent;      
            parent.child.add(currentEntry);      
        }
        data.currentEntry = currentEntry;      
        currentEntry.level=data.level;    
        data.level++;     
	}
	
	private void aroundEnd(String path){
		 	StackData data = dataHolder.get();      
	        StackEntry self = data.currentEntry;    
	        self.endTime = System.currentTimeMillis();    
	        data.currentEntry = self.parent;    
	        data.level--;    
	        printStack(data);    
	}

    private static void printStack(StackData data) {    
        if(logger.isInfoEnabled()){    
            StringBuilder sb = new StringBuilder("\r\n");    
            StackEntry root = data.root;    
            appendNode(root,sb);    
            logger.warn(sb.toString());    
        }    
    }    
    
    private static void appendNode(StackEntry entry, StringBuilder sb) {    
        long totalTime = entry.endTime-entry.beginTime ;    
        if(entry.level ==1){    
            sb.append("|-");    
        }    
        sb.append(totalTime);    
        sb.append(" ms; [");    
        sb.append(entry.path);    
        sb.append("]");    
                
        for(StackEntry cnode : entry.child){    
            sb.append("\r\n|");    
            for(int i=0,l=entry.level;i<l;i++){    
                sb.append("+---");    
            }    
            appendNode(cnode,sb);    
        }    
            
    }  
    
}
