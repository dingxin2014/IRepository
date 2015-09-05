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
        if (data == null) {      
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
		
        System.out.println("Before > currentEntry level is " +data.currentEntry.level+ "  Run ID:"+data.currentEntry.id);
        
        Object retVal = pjp.proceed(pjp.getArgs());  
        
        System.out.println("After > currentEntry level is " +data.currentEntry.level+ "  Run ID:"+data.currentEntry.id);
        //after      
        StackEntry self = data.currentEntry;    
        self.endTime = System.currentTimeMillis();    
        data.currentEntry = self.parent;    
        data.level--;  
        

		if(dataHolder.get().isReturn()){
			printStack(data);
		}
		
        return retVal;   
	}
	
    private void printStack(StackData data) {    
        if(logger.isInfoEnabled()){    
            StringBuilder sb = new StringBuilder("\r\n");    
            StackEntry root = data.root;    
            appendNode(root,sb);    
            logger.warn(sb.toString());    
        }    
    }    
    
    private void appendNode(StackEntry entry, StringBuilder sb) {    
        long totalTime = entry.endTime-entry.beginTime ;    
        if(entry.level ==1){   
        	sb.append(dataHolder.getThreadId()+"\n");
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
