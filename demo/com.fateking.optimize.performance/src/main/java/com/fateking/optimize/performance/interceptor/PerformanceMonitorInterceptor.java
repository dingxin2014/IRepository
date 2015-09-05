package com.fateking.optimize.performance.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

public interface PerformanceMonitorInterceptor {

	public void before();
      
    public void after();
      
    public void afterReturning();
    
    public void afterThrowing();
      
    public Object around(ProceedingJoinPoint pjp) throws Throwable;
}
