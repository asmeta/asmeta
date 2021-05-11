package org.asmeta.simulationUI;

import org.asmeta.runtime_container.RunOutput;

public class Composition {
	private final int[] compositionID = new int[2]; // CompositionID = [senderID, receiverID]
	String senderModel;
	String receiverModel;
	RunOutput output;
	
	public Composition(int senderID, int receiverID) {
		compositionID[0] = senderID;
		compositionID[1] = receiverID;
		senderModel = CompositionContainer.containerInstance.getLoadedIDs().get(compositionID[0]);
		receiverModel = CompositionContainer.containerInstance.getLoadedIDs().get(compositionID[1]);
		output = null;
	}
	
	public Composition(int[] compositionID) {
		if(compositionID.length == 2) {
			this.compositionID[0] = compositionID[0];
			this.compositionID[1] = compositionID[1];
			senderModel = CompositionContainer.containerInstance.getLoadedIDs().get(this.compositionID[0]);
			receiverModel = CompositionContainer.containerInstance.getLoadedIDs().get(this.compositionID[1]);
			output = null;
		}
	}
	
	public int[] getCompositionID() {
		return compositionID;
	}

	public int getSenderID() {
		return compositionID[0];
	}

	public int getReceiverID() {
		return compositionID[1];
	}

	public String getSenderModel() {
		return senderModel;
	}

	public String getReceiverModel() {
		return receiverModel;
	}
	
	public RunOutput getOutput() {
		return output;
	}
}
