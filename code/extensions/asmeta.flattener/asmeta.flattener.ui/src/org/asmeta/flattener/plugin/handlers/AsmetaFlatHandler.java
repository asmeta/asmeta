package org.asmeta.flattener.plugin.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.eclipse.AsmetaActionHandler;
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
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AsmetaFlatHandler extends AsmetaActionHandler {

	public AsmetaFlatHandler() {
		super(AsmetaFlatConsole.class, "flattening", false);
	}

	/**
	 * run the flattener
	 */
	protected void executeAction(File path, ExecutionEvent env) throws Exception {
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
		String refactoredAsm = AsmetaMultipleFlattener.flattenAsStr(path.getAbsolutePath(), flatteners);
		// write the flatten version on the console
		console.writeMessage(refactoredAsm);
	}

	@Override
	protected void setUpLoggers() {
		// do nothing???
	}
}
