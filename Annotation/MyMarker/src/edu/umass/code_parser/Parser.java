package edu.umass.code_parser;

import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.core.internal.content.Util;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class Parser {
	
	// hashmap of variables and values
	//HashMap<String, List<Object>> importantVarValues;
	
	JUnitCore jUnitCore;
	ParallelComputer computer;
	
	public Parser(){
		jUnitCore = new JUnitCore();
		computer = new ParallelComputer(true, true);
	}
	
	
	
	//Request request = Request.method(clazz, methodName)	
	// TODO launch new JVM before running JUnitCore? Attach to each other? Attach profiler to get information on execution?

	
	public void runTestCase() throws CoreException, MavenInvocationException{
		IProject project_broken = ResourcesPlugin.getWorkspace().getRoot().getProject("math_22_buggy");
		//IType classFile = (IType) project.findMember("RealDistributionAbstractTest");
	
		IJavaProject jProject_broken = (IJavaProject) project_broken.getNature(JavaCore.NATURE_ID);
		IType classFile_broken = (IType) jProject_broken.findType("org.apache.commons.math3.distribution.FDistributionTest");
		IResource resource_broken = classFile_broken.getResource();
		Class<?> resourceClass_broken = resource_broken.getClass();
		

		IProject project_fixed = ResourcesPlugin.getWorkspace().getRoot().getProject("math_22_fixed");
		IJavaProject jProject_fixed = (IJavaProject) project_fixed.getNature(JavaCore.NATURE_ID);
		IType classFile_fixed = (IType) jProject_fixed.findType("org.apache.commons.math3.distribution.FDistributionTest");
		IResource resource_fixed = classFile_fixed.getResource();
		Class<?> resourceClass_fixed = resource_fixed.getClass();
		
		// try running test(s) using Maven -- not working atm (not sure why, build failing)
		
//		InvocationRequest request = new DefaultInvocationRequest();
//		request.setPomFile(new File("/Users/bjohnson/eclipse-workspace/math_22_buggy/pom.xml"));
//		request.setGoals(Collections.singletonList("test"));
//		
//		Invoker invoker = new DefaultInvoker();
//		invoker.setMavenHome(new File("/Users/bjohnson/Documents/Research_2017-2018/causal_testing/CausalParser/Annotation/MyMarker/lib/apache-maven-3.0.5"));
//		
//		InvocationResult result = invoker.execute(request);
//		
//		if (result.getExitCode() != 0){
//			throw new IllegalStateException("Build failed!");
//		} else {
//			System.out.println("Build successful! Tests ran.");
//		}
		
		// TODO next option = TestNG (advantage = can work with JUnit with minimal/no changes; pom.xml not needed)
		Class<?>[] classes = {resourceClass_broken, resourceClass_fixed};
		
		TestListenerAdapter adapter = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(classes);
		
//		testng.addExecutionListener(arg0);
//		testng.addInvokedMethodListener(arg0);
		testng.addListener(adapter);
		
		testng.run();
		
		// TODO use XML method to specify method??
		
		
		
		
		// code below not working (says no constructor but there is; says no runnable methods but there are)
		
//		
		
		//Parallel among classes
//		Result result1 = JUnitCore.runClasses(ParallelComputer.classes(), classes);
//		
//		//Parallel among methods in classes
//		Result result2 = JUnitCore.runClasses(ParallelComputer.methods(), classes);
//		
//		//Parallel all methods in all classes
//		Result result3 = JUnitCore.runClasses(new ParallelComputer(true, true), classes);
//		
//		
//		result1.getFailures();
//		result2.getFailures();
//		result3.getFailures();
//		
//		
//		Request broken_request = Request.method(resourceClass_broken, "testIsSupportLowerBoundInclusive");
//		
//		Result broken_result = jUnitCore.run(resourceClass_broken);
//		
//		if (!broken_result.getFailures().isEmpty()){
//			for (Failure failure:broken_result.getFailures()){
//				System.out.println(failure.getDescription());
//			}			
//		}
//		
//		
//		Request fixed_request = Request.method(resourceClass_fixed, "testIsSupportLowerBoundInclusive");
//		
//		Result fixed_result = jUnitCore.run(resourceClass_fixed);
//		
//		if (!fixed_result.getFailures().isEmpty()){
//			for (Failure failure:fixed_result.getFailures()){
//				System.out.println(failure.getDescription());
//			}
//		} else {
//			System.out.println("Your test passed!");
//		}
		
				

	}

}
