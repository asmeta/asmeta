package org.asmeta.visualizer.graphViewer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import asmeta.definitions.RuleDeclaration;

/** In order to handle double click on macro call rule nodes */
public class AsmGraphListener implements IDoubleClickListener {
	final Composite parent;
	/**
	 * it keeps track of the created shells (in order to avoid to create
	 * multiple shells for the same rule)
	 */
	private static Map<RuleDeclaration, Shell> macroShells = new HashMap<RuleDeclaration, Shell>();
	public static Map<Shell, Set<Shell>> calledShells = new HashMap<Shell, Set<Shell>>();
	public static Map<Shell, Set<Shell>> calledByShells = new HashMap<Shell, Set<Shell>>();

	AsmGraphListener(Composite comp) {
		parent = comp;
		//this.macroShells = macroShells;
	}

	@Override
	public void doubleClick(DoubleClickEvent arg0) {
		org.eclipse.jface.viewers.StructuredSelection s = (StructuredSelection) arg0.getSelection();
		if (s.getFirstElement() instanceof MacroCallNode) {
			MacroCallNode macro = (MacroCallNode) s.getFirstElement();
			Shell oldShell = macroShells.get(macro.calledMacro);
			if (oldShell != null && !oldShell.isDisposed()) {
				oldShell.setActive();
				parent.moveBelow(oldShell);
				calledShells.get(parent.getShell()).add(oldShell);
				calledByShells.get(oldShell).add(parent.getShell());
			} else {
				// shell = new Shell(parent.getShell(), SWT.SHELL_TRIM);
				final Shell shell = new Shell(SWT.SHELL_TRIM);// if it is not a child, it is
													// easier to move between
													// open shells
				shell.setText("RULE " + macro.label);
				shell.setLayout(new FillLayout(SWT.VERTICAL));
				shell.setForeground((Display.getDefault().getSystemColor(SWT.COLOR_BLACK)));
				calledShells.put(shell, new HashSet<Shell>());
				calledByShells.put(shell, new HashSet<Shell>());
				macroShells.put(macro.calledMacro, shell);
				calledShells.get(parent.getShell()).add(shell);
				calledByShells.get(shell).add(parent.getShell());
				shell.addListener(SWT.Close, new Listener() {
					@Override
					public void handleEvent(Event event) {
						//this closes only the shells that are called (directly or indirectly) by the current shell
						//it does not work correctly
						/*System.out.println("Current shell: " + shell);
						System.out.println(calledShells);
						assert calledShells.get(shell) != null;
						for(Shell s: calledShells.get(shell)) {
							s.close();
						}*/
						//this closes all the shells
						/*for(Shell s: macroShells.values()) {
							if(s != shell) {
								shell.addListener(SWT.Close, new Listener() {

									@Override
									public void handleEvent(Event event) {
									}});
								s.close();
							}
						}*/
					}
				});
				AsmGraphViewerVisualizer.showGraph(macro.calledMacroBody, shell);
			}
		}
	}
}
