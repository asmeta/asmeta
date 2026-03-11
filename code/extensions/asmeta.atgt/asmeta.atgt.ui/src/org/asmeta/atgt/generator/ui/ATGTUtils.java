package org.asmeta.atgt.generator.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ATGTUtils {

	static IFile getCurrentFileFromWorkbench() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		if (win == null) {
			return null;
		}

		// 1) Try current selection
		ISelection sel = win.getSelectionService().getSelection();
		IFile file = ATGTUtils.toIFile(sel);
		if (file != null) {
			return file;
		}

		// 2) Fallback: active editor
		IWorkbenchPage page = win.getActivePage();
		if (page == null) {
			return null;
		}

		IEditorPart editor = page.getActiveEditor();
		if (editor == null) {
			return null;
		}

		IEditorInput input = editor.getEditorInput();
		IFile editorFile = input.getAdapter(IFile.class);
		return editorFile;
	}

	static IFile toIFile(ISelection selection) {
		ATGTActivator.log.debug("getting the file from the selection");
		if (!(selection instanceof IStructuredSelection ss)) {
			return null;
		}
		Object first = ss.getFirstElement();
		if (first == null) {
			return null;
		}

		if (first instanceof IFile f) {
			return f;
		}
		if (first instanceof IAdaptable a) {
			return a.getAdapter(IFile.class);
		}
		return null;
	}

//	static IPath setFilePath(ISelection selection) {
//		if (selection instanceof TreeSelection) {
//			TreeSelection treeSelections = (TreeSelection) selection;
//			Object select = treeSelections.getFirstElement();
//			// System.out.println(select.getClass().getName());
//			// for now it works only with single files
//			if (select instanceof org.eclipse.core.internal.resources.File) {
//				// add the path of the project
//				// file path is only the last part
//				// IProject prj = ((org.eclipse.core.internal.resources.File)
//				// select).getProject();
//				IPath filePath = ((File) select).getLocation();
//				return filePath;
//			}
//		}
//		ATGTActivator.log.error("not a tree selection");
//		return null;
//	}


}
