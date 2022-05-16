package org.asmeta.runtime_container;

import java.util.List;
import java.util.Map;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 */
public interface IModelAdaptation {
	 /**
	 * Add one invariant to the model.
	 *
	 * @param id the simulation id
	 * @param invariant_to_add complete string of the new invariant to add
	 * @return id of the simulation if the operation is successful, negative representing error code if it doesn't
	 */
	int addInvariant(int id,String invariant_to_add);
	 /**
	 * Gets an object with all model variables and all invariants.
	 *
	 * @param id the simulation id
	 * @return invariant data
	 */
	InvariantData viewListInvariant(int id);
	 /**
	 * Modify one invariant in the model.
	 *
	 * @param id the simulation id
	 * @param new_invariant complete string of the new invariant
	 * @param old_invariant complete string of the invariant to change
	 * @return id of the simulation if the operation is successful, negative representing error code if it doesn't
	 */
	int updateInvariant(int id, String new_invariant, String old_invariant);
	 /**
	 * Delete one invariant in the model.
	 *
	 * @param id the simulation id
	 * @param remove_invariant complete string of the invariant to be deleted
	 * @return true if the simulation successfully restarts, false if there was an error restarting
	 */
	boolean removeInvariant(int id, String remove_invariant);
	 /**
	 * Acquire map of instantiated simulations.
	 *
	 * @return map of all the usable ids and corresponding model paths currently loaded, key: simulation id value: modelpath
	 */
	public Map<Integer, String> getLoadedIDs();
}
