package org.asmeta.runtime_container;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import asmeta.definitions.Invariant;

public interface IModelAdaptation {
	//add
	int addInvariant(int id,String invariant_to_add);
	//view list
	List<String> viewListInvariant(int id);
	//update
	int updateInvariant(int id, String new_invariant, String old_invariant);
	//remove
	boolean removeInvariant(int id, String remove_invariant);
}
