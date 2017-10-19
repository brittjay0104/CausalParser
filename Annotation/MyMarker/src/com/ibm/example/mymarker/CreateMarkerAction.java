package com.ibm.example.mymarker;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import edu.umass.code_parser.Parser;

public class CreateMarkerAction implements IEditorActionDelegate {

	public CreateMarkerAction() {
		super();
	}
	
	@Override
	public void setActiveEditor(IAction action, IEditorPart editor) {
	}

	/*
	 * This action creates a new marker for the given IFile 
	 */
	@Override
	public void run(IAction action) {
		try {
			TextSelection selection = MyMarkerFactory.getTextSelection();
			IFile file = (IFile) MyMarkerPlugin.getEditor().getEditorInput().getAdapter(IFile.class);
			IMarker mymarker = MyMarkerFactory.createMarker(file);
			
			Parser parser = new Parser();
			
			parser.runTestCase();
			
			//MyMarkerFactory.addAnnotation(mymarker, selection, MyMarkerPlugin.getEditor());
			
			//IDocument doc = MyMarkerPlugin.getEditor().getDocumentProvider().getDocument(mymarker);
			
			// TODO Create compilation unit, etc. for parsing 
			// TODO feed in line of code to parser to get critical pieces
			
		} catch (CoreException e) {
			System.out.println("CoreException caught!");
			e.printStackTrace();
		} catch (MavenInvocationException e) {
			System.out.println("MavenInvocationException caught!");
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
