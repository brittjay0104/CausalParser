package edu.umass.code_parser;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.junit.runner.Request;

public class Parser {
	
	// hashmap of variables and values
	//HashMap<String, List<Object>> importantVarValues;
	
	public Parser(){
		
		JUnitCore jUnitCore = new JUnitCore();
		Result result; // stores result of test execution		
	}
	
	
	
	//Request request = Request.method(clazz, methodName)	
	
	public void setTestClass() throws CoreException{
		IProject project_broken = ResourcesPlugin.getWorkspace().getRoot().getProject("math_22_buggy");
		//IType classFile = (IType) project.findMember("RealDistributionAbstractTest");
	
		IJavaProject jProject_broken = (IJavaProject) project_broken.getNature(JavaCore.NATURE_ID);
		IType classFile_broken = (IType) jProject_broken.findType("org.apache.commons.math3.distribution.RealDistributionAbstractTest");
		IResource resource_broken = classFile_broken.getResource();
		Class<?> resourceClass_broken = resource_broken.getClass();
		
		IProject project_fixed = ResourcesPlugin.getWorkspace().getRoot().getProject("math_22_buggy");
		IJavaProject jProject_fixed = (IJavaProject) project_fixed.getNature(JavaCore.NATURE_ID);
		IType classFile_fixed = (IType) jProject_fixed.findType("org.apache.commons.math3.distribution.RealDistributionAbstractTest");
		IResource resource_fixed = classFile_fixed.getResource();
		Class<?> resourceClass_fixed = resource_fixed.getClass();
		
		
		
		// TODO launch new JVM before running JUnitCore? Attach to each other? Attach profiler to get information on execution?
		
		//System.out.println(resourceClass_broken.getName());
			

	}

}
