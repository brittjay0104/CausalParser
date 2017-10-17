package edu.umass.code_parser;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class NodeVisitor {
	
	// failed test command (e.g. assertEqual)
	// called methods
	// hashmap of methods and important variables
	// hashmap of variables and values
	
	// this includes AssertStatements (https://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2Fdom%2FStatement.html)
	List<Statement> testCommands;
	
	
}
