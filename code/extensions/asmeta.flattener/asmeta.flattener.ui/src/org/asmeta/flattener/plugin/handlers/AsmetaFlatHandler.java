package org.asmeta.flattener.plugin.handlers;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.flattener.RemoveArgumentsFlattener;
import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.plugin.AsmetaFlatActivator;
import org.asmeta.flattener.plugin.AsmetaFlatConsole;
import org.asmeta.flattener.plugin.AsmetaFlatPreferencePage;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AsmetaFlatHandler extends AsmetaActionHandler {

	/**
	 * the command has been executed, so extract extract the needed information from
	 * the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String path = org.asmeta.eclipse.AsmetaUtility.getEditorPath(window);
		IConsoleView view = null;
		try {
			view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		AsmetaFlatConsole myConsole = AsmetaUtility.findConsole(AsmetaFlatConsole.class);
		view.display(myConsole);
		myConsole.activate();
		OutputStream out = myConsole.newOutputStream();
		PrintStream printOut = new PrintStream(out);
		System.setOut(printOut);
		System.setErr(printOut);
		try {
			IPreferenceStore store = AsmetaFlatActivator.getDefault().getPreferenceStore();
			List<Class<? extends AsmetaFlattener>> flatteners = new ArrayList<>();
			if (store.getBoolean(AsmetaFlatPreferencePage.MACRO_CALL)) {
				flatteners.add(MacroCallRuleFlattener.class);
			}
			if (store.getBoolean(AsmetaFlatPreferencePage.FORALL)) {
				flatteners.add(ForallRuleFlattener.class);
			}
			if (store.getBoolean(AsmetaFlatPreferencePage.CHOOSE)) {
				flatteners.add(ChooseRuleFlattener.class);
			}
			if (store.getBoolean(AsmetaFlatPreferencePage.ARGS)) {
				flatteners.add(RemoveArgumentsFlattener.class);
			}
			if (store.getBoolean(AsmetaFlatPreferencePage.LET)) {
				flatteners.add(LetRuleFlattener.class);
			}
			if (store.getBoolean(AsmetaFlatPreferencePage.CASE)) {
				flatteners.add(CaseRuleFlattener.class);
			}
			if (store.getBoolean(AsmetaFlatPreferencePage.CONDITIONAL)) {
				flatteners.add(RemoveNestingFlattener.class);
			}
			String refactoredAsm = AsmetaMultipleFlattener.flattenAsStr(path, flatteners);
			System.out.println(refactoredAsm);
		} catch (Exception e) {
			Display d = Display.getDefault();
			Shell shell = new Shell(d);
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error while parsing " + path + "\n\n" + e.getLocalizedMessage());
			message.setText("Parsing error");
			message.open();
			return null;
		}
		try {
			// run flattener
		} catch (Exception e) {
			e.printStackTrace();
			Display d = Display.getDefault();
			Shell shell = new Shell(d);
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error during the execution of the model advisor");
			message.setText("Execution error");
			message.open();
		}
		return null;
	}
}
