package org.asmeta.test;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.runtime_container.SimulationContainer;

public class TestMonitorateNarie {

	public static void main(String[] args) {
		/*String model = "examples/ferrymanSimulator_raff1.asm";
		try {
			Files.copy(Paths.get(model+".original"), Paths.get(model), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("java files good");
		}
		SimulationContainer i=SimulationContainer.getInstance();
		i.init(2);
		int id=i.startExecution(model);
		id=i.startExecution("examples/test_insertAt_Sequence.asm");
		Map<String,String> m=new HashMap<String, String>(); //prova per invalid invariant su aggiunta
		m.put("carry", "GOAT");
		System.out.println(i.runStep(3,m));*/
		SimulationContainer i=new SimulationContainer();
		i.init(5);
		int id;
		//id=i.startExecution(model);
		id=i.startExecution("examples/ferrymanSimulator_raff1.asm");
		id=i.startExecution("examples/lift3.asm");
		id=i.startExecution("examples/LIFT.asm");
		
		Map<String,String> mLIFT=new HashMap<String, String>(); 
		mLIFT.put("hasToDeliverAt", "lift(1, 4) = true");
		
		Map<String,String> mFERRY=new HashMap<String, String>(); 
		mFERRY.put("carry", "GOAT");
		
		//System.out.println(i.runStep(1,mFERRY));
		System.out.println(i.runStep(2,mFERRY));
		
		//LIFT
		System.out.println("LIFT");
		Map<String,String> mLIFTREAL=new HashMap<String, String>(); //mappa per modello LIFT (non ha senso)
		{
			mLIFTREAL.put("insideCall", "(lift1, 0) = false");
			mLIFTREAL.put("insideCall", "(lift1, 1) = true");
			mLIFTREAL.put("insideCall", "(lift1, 2) = false");
			mLIFTREAL.put("outsideCall", "(0, UP) = false");
			mLIFTREAL.put("outsideCall", "(1, UP) = true");
			mLIFTREAL.put("outsideCall", "(2, UP) = false");
			mLIFTREAL.put("outsideCall", "(0, DOWN) = false");
			mLIFTREAL.put("outsideCall", "(1, DOWN) = false");
			mLIFTREAL.put("outsideCall", "(2, DOWN) = false");
		}
		Map<String,String> mLIFTREAL2=new HashMap<String, String>(); //mappa 2 per modello LIFT (checksafety non prende le parentesi, errore solo fino a lì?)
		{								
			mLIFTREAL2.put("insideCall(lift1, 0)", "false");
			mLIFTREAL2.put("insideCall(lift1, 1)", "true");
			mLIFTREAL2.put("insideCall(lift1, 2)", "false");
			mLIFTREAL2.put("outsideCall(0, UP)", "false");
			mLIFTREAL2.put("outsideCall(1, UP)", "true");
			mLIFTREAL2.put("outsideCall(2, UP)", "false");
			mLIFTREAL2.put("outsideCall(0, DOWN)", "false");
			mLIFTREAL2.put("outsideCall(1, DOWN)", "false");
			mLIFTREAL2.put("outsideCall(2, DOWN)", "false");
		}
		System.out.println(i.runStep(3,mLIFTREAL));
	}

}
