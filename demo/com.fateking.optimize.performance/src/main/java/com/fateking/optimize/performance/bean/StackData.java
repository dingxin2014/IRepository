package com.fateking.optimize.performance.bean;


/**
 * <p>保存监控信息变量 </p>
 * @author DingXin
 *
 */
public class StackData {
	/**  
     * 记录根根节点  
     */    
    public StackEntry root;    
    /**  
     * 当前正在调用方法节点  
     */    
    public StackEntry currentEntry;    
    /**  
     * 堆栈树高度  
     */    
    public int level;    
    
    public boolean isRoot(){
    	return root.equals(currentEntry);
    }
}
