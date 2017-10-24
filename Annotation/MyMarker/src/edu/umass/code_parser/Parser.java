package edu.umass.code_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.IMethodCoverage;
import org.jacoco.core.analysis.ISourceFileCoverage;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.ExecutionDataWriter;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;

@Aspect
public class Parser {
	
	// hashmap of variables and values
	//HashMap<String, List<Object>> importantVarValues;
	
	JUnitCore jUnitCore;
	ParallelComputer computer;
	File pluginDir;
	Runtime currentApp;
	
	public Parser(){
		jUnitCore = new JUnitCore();
		computer = new ParallelComputer(true, true);
		
		currentApp = Runtime.getRuntime();
		currentApp.traceMethodCalls(true);
	
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
	
	public void runTestCase() throws CoreException{
		IProject project_broken = ResourcesPlugin.getWorkspace().getRoot().getProject("math_22_buggy");
		//IType classFile = (IType) project.findMember("RealDistributionAbstractTest");
	
		IJavaProject jProject_broken = (IJavaProject) project_broken.getNature(JavaCore.NATURE_ID);
		IType classFile_broken = (IType) jProject_broken.findType("org.apache.commons.math3.distribution.RealDistributionAbstractTest");
		IResource resource_broken = classFile_broken.getResource();
		Class<?> resourceClass_broken = resource_broken.getClass();
		

		IProject project_fixed = ResourcesPlugin.getWorkspace().getRoot().getProject("math_22_fixed");
		IJavaProject jProject_fixed = (IJavaProject) project_fixed.getNature(JavaCore.NATURE_ID);
		IType classFile_fixed = (IType) jProject_fixed.findType("org.apache.commons.math3.distribution.RealDistributionAbstractTest");
		IResource resource_fixed = classFile_fixed.getResource();
		Class<?> resourceClass_fixed = resource_fixed.getClass();
		
		ExecutionDataStore dataStore = new ExecutionDataStore();
		CoverageBuilder builder = new CoverageBuilder();
		
		Analyzer analyzer = new Analyzer(dataStore, builder);
		
		File classFile = new File("/Users/bjohnson/eclipse-workspace/math_22_buggy/target/test-classes/org/apache/commons/math3/distribution");

		
		try {
//			OutputStream output = new FileOutputStream("/Users/bjohnson//Users/bjohnson/Documents/Research_2017-2018/causal_testing/CausalParser");
//			ExecutionDataWriter writer = new ExecutionDataWriter(output); 
			
			
			int numFiles = analyzer.analyzeAll(classFile);	
			System.out.println("Number of files: " + numFiles);
			
			Collection<IClassCoverage> classes = builder.getClasses();
			// each class has id (getId()) and sourcefile name (getSourceFileName)
			// can also get methods (getMethods(), returns IMethodCoverage)
			
			for (IClassCoverage coverage: classes){
				//System.out.println("File: " + coverage.getSourceFileName() + " ID: " + coverage.getId());
				
				if (coverage.getSourceFileName().equals("RealDistributionAbstractTest.java")){
					for (IMethodCoverage methCover: coverage.getMethods()){
						ICounter count = methCover.getMethodCounter();
						
						if (count.getStatus() == ICounter.FULLY_COVERED){
							System.out.println("Method Info: ");
							System.out.println(methCover.getDesc());
							System.out.println(methCover.getName());
						}
					}					
				}
				
				//builder.visitCoverage(coverage);
			}
			
			// from here can also access getMethodCounter, getLineCounter, getInstructionCounter
			
			Collection<ISourceFileCoverage> sourceFiles = builder.getSourceFiles();

			//writer.visitClassExecution(executionData);
			//dataStore.put(executionData);
			
			
		} catch (IOException e) {
			System.out.println("Cannot find file!");
			e.printStackTrace();
		} 
		
		
		
		
		if (!dataStore.getContents().isEmpty()){
			dataStore.getContents();				
		} else {
			System.out.println("Data store is empty!");
		}
		
		// TODO try with import (Real...Test.class)? https://howtodoinjava.com/junit/how-to-execute-junit-testcases-with-junitcore/
		// TODO try something that will detect junit is running (listener?) and provide methods for monitoring execution
		
//		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
//		ILaunchConfigurationType launchType = manager.getLaunchConfigurationType("org.eclipse.jdt.junit.junitLaunchConfigs");
//		
//		ILaunchConfigurationWorkingCopy workingCopy = launchType.newInstance(null, project_broken.getName());
		
		
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
		
		// TestNG (advantage = can work with JUnit with minimal/no changes; pom.xml not needed)
//		Class<?>[] classes = {resourceClass_broken, resourceClass_fixed};
//		
//		TestListenerAdapter adapter = new TestListenerAdapter();
//		TestNG testng = new TestNG();
//		testng.setTestClasses(new Class<?>[] {resourceClass_broken});
		//System.out.println(new File (testng.getOutputDirectory()));
		
//		testng.addExecutionListener(arg0);
//		testng.addInvokedMethodListener(arg0);
		//IInvokedMethodListener
		//IExecutionListener
		//ITestListener
		//IResultListener/IResultListener2
//		testng.addListener(adapter);
//		
//		testng.setJUnit(true);
//		testng.run();
		
//		for (ITestResult result : adapter.getFailedTests()){
//			if (result.isSuccess()){
//				System.out.println("Test passed!");
//			} else{
//				System.out.println("Test failed!");
//			}
//		}
		
		// TODO use XML method to specify method??
		
		
		// code below not working (says no constructor but there is; says no runnable methods but there are)	
		
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
