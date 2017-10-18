package edu.umass.code_parser;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class NodeVisitor {
	
	
	// TODO these should probably become strings if being stored
	
	// failed test command (e.g. assertEqual)
	// this includes AssertStatements (https://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2Fdom%2FStatement.html)
	List<Statement> testCommands;
	
	// Should this be a hash map? 
	List<MethodInvocation> calledMethods;
	
	HashMap<MethodInvocation, List<SingleVariableDeclaration>> importantVariables;
	
	// hashmap of variables and values
	//HashMap<SingleVariableDeclaration, List<>>
	
	
	
	// TODO IMethodBinding (resolveMethodBinding) for MethodInvocation --> getDeclaringClass (ITypeBinding)
	// https://stackoverflow.com/questions/18939857/how-to-get-a-class-name-of-a-method-by-using-eclipse-jdt-astparser
	
	// TODO visit Assignments (and VariableDeclarationExpression if necessary) that involve importantVariable parameters; Assignment is Expression 
	// 
	
	
	
}
