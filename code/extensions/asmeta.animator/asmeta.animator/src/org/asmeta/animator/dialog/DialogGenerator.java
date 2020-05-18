package org.asmeta.animator.dialog;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.util.DomainsSwitch;

public class DialogGenerator extends DomainsSwitch<MyDialog>{
	
	private Shell shell;
	private String locationName;
	private String domainInfo;



	public DialogGenerator(Shell s, String domainInfo, String location){
		shell = s;
		this.locationName = location;
		this.domainInfo = domainInfo;
	}

	@Override
	public MyDialog caseEnumTd(EnumTd dom) {
		ArrayList<String> enumValues = extractEnumValues(dom.getElement());
		MyDialogEnumInsert dialog = new MyDialogEnumInsert(shell,enumValues);
		dialog.setMessage(" Insert a " + domainInfo + " for " + locationName + ":");
		return dialog;
	}
	 
	@Override
	public MyDialog caseBooleanDomain(BooleanDomain object) {
		MyDialogBooleanInsert dialog = new MyDialogBooleanInsert(shell);
		dialog.setMessage(" Insert a " + domainInfo + " for " + locationName + ":");
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
	public MyDialog doSwitch(EObject eObject) {
		MyDialog result = super.doSwitch(eObject);
		if (result == null) {
			MyDialogInsert dialog = new MyDialogInsert(shell);
			dialog.setMessage(" Insert a " + domainInfo + " for " + locationName + ":");
			return dialog;
		}
		return result;
	}
	
}
