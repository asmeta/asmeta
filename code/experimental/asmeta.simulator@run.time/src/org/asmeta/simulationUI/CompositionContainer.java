package org.asmeta.simulationUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.IModelAdaptation;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public class CompositionContainer implements IModelCompositionContainer {
	private List<Composition> compositionList;
	private RunOutput lastOutput;
	public final CompositionType compType;
	static SimulationContainer containerInstance;
	
	public CompositionContainer(IModelAdaptation contInstance, CompositionType compType) {
		compositionList = new ArrayList<>();
		containerInstance = (SimulationContainer) contInstance;
		this.compType = compType;
		lastOutput = null;
	}
	
	@Override
	public void runStep(RunOutput originalOutput) throws EmptyCompositionListException {
		if(compType == CompositionType.PIPE) {
			lastOutput = new RunOutput(Esit.UNSAFE, "rout not intialized");
			if(!isEmpty()) {
				for(Composition comp: compositionList) {
					if(comp == getFirstComposition()) {
						if(originalOutput.getEsit() == Esit.SAFE) {
							Map<String, String> senderOutput = originalOutput.getControlledvalues();
							// TODO: filtrare con funzioni "out"
							lastOutput = containerInstance.runStep(comp.getReceiverID(), senderOutput);
							comp.output = lastOutput;
						} else {
							System.out.println("Initial model rollback!\n");
						}
					} else {
						if(lastOutput.getEsit() == Esit.SAFE) {
							Map<String, String> senderOutput = lastOutput.getControlledvalues();
							// TODO: filtrare con funzioni "out"
							lastOutput = containerInstance.runStep(comp.getReceiverID(), senderOutput);
							comp.output = lastOutput;
						} else {
							System.out.println("Previous model rollback!\n");
						}
					}
				}
			} else {
				throw new EmptyCompositionListException("The composition list is empty!");
			}
		}
		// TODO: implementare anche altri CompositionType
	}
	
	// TODO: implementare anche runUntilEmpty, runStepTimeout e runUntilEmptyTimeout per la composizione
	
	@Override
	public List<Composition> getCompositionList() {
		return compositionList;
	}

	@Override
	public void addComposition(Composition composition) {
		if(containerInstance != null && 
		   composition != null &&
		   containerInstance.getLoadedIDs().containsKey(composition.getSenderID()) && 
		   containerInstance.getLoadedIDs().containsKey(composition.getReceiverID())) {
			compositionList.add(composition);
		}
	}

	@Override
	public void addComposition(int senderID, int receiverID) {
		if(containerInstance != null && 
		   containerInstance.getLoadedIDs().containsKey(senderID) && 
		   containerInstance.getLoadedIDs().containsKey(receiverID)) {
			compositionList.add(new Composition(senderID, receiverID));
		}
	}

	@Override
	public void addComposition(int[] compositionID) {
		if(containerInstance != null && 
		   containerInstance.getLoadedIDs().containsKey(compositionID[0]) && 
		   containerInstance.getLoadedIDs().containsKey(compositionID[1]) &&
		   compositionID.length == 2) {
			compositionList.add(new Composition(compositionID));
		}
	}

	@Override
	public Composition getComposition(int senderID, int receiverID) {
		if(compositionList != null && !compositionList.isEmpty()) {
			for(Composition c: compositionList) {
				if(c.getSenderID() == senderID && c.getReceiverID() == receiverID) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public Composition getComposition(int[] compositionID) {
		if(compositionList != null && !compositionList.isEmpty() && compositionID.length == 2) {
			for(Composition c: compositionList) {
				if(c.getCompositionID() == compositionID) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public Composition getFirstComposition() {
		if(compositionList != null && compositionList.size() > 0) {
			return compositionList.get(0);
		}
		return null;
	}

	@Override
	public Composition getLastComposition() {
		if(compositionList != null && compositionList.size() > 0) {
			return compositionList.get(compositionList.size() - 1);
		}
		return null;
	}

	@Override
	public void removeComposition(Composition composition) {
		if(compositionList != null && !compositionList.isEmpty()) {
			compositionList.remove(composition);
		}
	}

	@Override
	public void removeComposition(int senderID, int receiverID) {
		if(compositionList != null && !compositionList.isEmpty()) {
			for(Composition c: compositionList) {
				if(c.getSenderID() == senderID && c.getReceiverID() == receiverID) {
					compositionList.remove(c);
					return;
				}
			}
		}
	}

	@Override
	public void removeComposition(int[] compositionID) {
		if(compositionList != null && !compositionList.isEmpty() && compositionID.length == 2) {
			for(Composition c: compositionList) {
				if(c.getCompositionID() == compositionID) {
					compositionList.remove(c);
					return;
				}
			}
		}
	}

	@Override
	public boolean isEmpty() {
		if(compositionList != null) {
			return compositionList.isEmpty();
		}
		return true;
	}

	@Override
	public int size() {
		if(compositionList != null) {
			return compositionList.size();
		}
		return -1;
	}

}
