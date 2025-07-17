package org.asmeta.animator.dialog;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.util.DomainsSwitch;

public class DialogGenerator extends DomainsSwitch<AskMonDialog>{
	
	private Shell shell;
	private String locationName;
	private String domainInfo;

	public DialogGenerator(Shell s, String domainInfo, String location){
		shell = s;
		this.locationName = location;
		this.domainInfo = domainInfo;
	}

	@Override
	public AskMonDialog caseEnumTd(EnumTd dom) {
		ArrayList<String> enumValues = extractEnumValues(dom.getElement());
		String msg = " Insert a " + domainInfo + " for " + locationName + ":";
		AskMonDialogEnum dialog = new AskMonDialogEnum(shell,enumValues,msg);
		return dialog;
	}
	 
	@Override
	public AskMonDialog caseBooleanDomain(BooleanDomain object) {
		String msg = " Insert a " + domainInfo + " for " + locationName + ":";
		AskMonDialogBoolean dialog = new AskMonDialogBoolean(shell,msg);
		return dialog;
	}
	
	// extract the list of string for the enumerative 
	// in alphabetic order
	private ArrayList<String> extractEnumValues(EList<EnumElement> element) {
		ArrayList<String> listElements = new ArrayList<>();
		for(EnumElement e:element) {
			listElements.add(e.getSymbol());
		}
		Collections.sort(listElements);
		return listElements;
	}
	
	@Override
	public AskMonDialog doSwitch(EObject eObject) {
		AskMonDialog result = super.doSwitch(eObject);
		if (result == null) {
			String msg = " Insert a " + domainInfo + " for " + locationName + ":";
			AskMonDialogGeneric dialog = new AskMonDialogGeneric(shell, msg);
			return dialog;
		}
		return result;
	}
	
}
