package com.fateking.optimize.performance.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>AOP方法性能统计实体  </p>
 * @author DingXin
 *
 */
public class StackEntry {  
	 public String path;
     public long beginTime;    
     public long endTime;    
     
     /**  
      * 节点所处高度  
      */    
     public int level;    
     
     /**  
      * 调用的子方法  
      */    
     public List<StackEntry> child;    
     
     /**  
      * 上级节点  
      */    
     public StackEntry parent ;    
     
     public StackEntry(String path,long currentTimeMillis) {   
    	 this.path = path;
         this.beginTime = currentTimeMillis;    
         this.child = new ArrayList<StackEntry>();    
     }    
}
