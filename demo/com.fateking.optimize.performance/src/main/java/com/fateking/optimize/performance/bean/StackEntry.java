package com.fateking.optimize.performance.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * <p>AOP方法性能统计实体  </p>
 * @author DingXin
 *
 */
public class StackEntry {  
	 public String path;
     public long beginTime;    
     public long endTime;    
     
     public String id = UUID.randomUUID().toString();
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
     
     public StackEntry(){}
     
     public StackEntry(String path,long currentTimeMillis) {   
    	 this.path = path;
         this.beginTime = currentTimeMillis;    
         this.child = new ArrayList<StackEntry>();    
     }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + level;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StackEntry other = (StackEntry) obj;
		if (level != other.level)
			return false;
		return true;
	}    
     
     
}
