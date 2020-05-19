package org.asmeta.runtime_container;

import java.util.List;

import asmeta.definitions.Invariant;

public interface IModelAdaptation {
	//add
	boolean addInvariant(int id,String invariant_to_add);
	//view list
	void viewListInvariant(int id);
	//update
	boolean updateInvariant(int id, String new_invariant, String old_invariant);
	//remove
	boolean removeInvariant(int id, String remove_invariant);
}
