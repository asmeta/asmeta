package org.asmeta.simulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class SetUpdateSet implements Iterable<UpdateSet> {
	//private Set<UpdateSet> setUpdateSets;
	private ArrayList<UpdateSet> setUpdateSets;

	public SetUpdateSet() {
		//setUpdateSets = new HashSet<UpdateSet>();
		setUpdateSets = new ArrayList<UpdateSet>();
	}

	public SetUpdateSet(UpdateSet updateSet) {
		this();
		add(updateSet);
	}

	public void add(UpdateSet updateSet) {
		for(UpdateSet us: setUpdateSets) {
			//just new update set can be added
			if(us.equals(updateSet)) {
				return;
			}
		}
		//if the updateset is different from the other update sets, it can be added
		//to the set of update sets
		setUpdateSets.add(updateSet);
	}

	void add(SetUpdateSet updateSets) {
		setUpdateSets.addAll(updateSets.getAsList());
		/*for(UpdateSet us: updateSets.getAsList()) {
			add(us);
		}*/
	}

	void addAll(Set<UpdateSet> updateSets) {
		//setUpdateSets.addAll(updateSets);
		for(UpdateSet us: updateSets) {
			add(us);
		}
	}

	void union(UpdateSet updateSet) {
		for(UpdateSet us: setUpdateSets) {
			us.union(updateSet);
		}
	}

	public int size() {
		return setUpdateSets.size();
	}

	public UpdateSet get(int index) {
		return setUpdateSets.get(index);
	}

	/*Set<UpdateSet>*/ ArrayList<UpdateSet> getAsList() {
		return setUpdateSets;
	}

	@Override
	public Iterator<UpdateSet> iterator() {
		return setUpdateSets.iterator();
	}

	public void merge(SetUpdateSet otherUpdateSets) {
		SetUpdateSet newSetUpdateSet = new SetUpdateSet();
		UpdateSet us;
		for(UpdateSet updateSet: setUpdateSets) {
			for(UpdateSet otherUpdateSet: otherUpdateSets) {
				us = new UpdateSet();
				us.union(updateSet);
				us.union(otherUpdateSet);
				newSetUpdateSet.add(us);
			}
		}
		setUpdateSets = newSetUpdateSet.getAsList();
	}
}