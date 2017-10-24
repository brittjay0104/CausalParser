package edu.umass.code_parser;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;

@Aspect
public class Tracer {
	
	public Tracer(){
		
	}
	
	@Pointcut("")
	public void traceMethods() {
		
	}
	
	@Before("traceMethods()")
	public void beforeTraceMethods(){
		
	}
	
	@After("traceMethods()")
	public void afterTraceMethods(){
		
	}
	
	@Pointcut("")
	public void traceStatements(){
		
	}

	
}
