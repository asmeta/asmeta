package org.asmeta.dbc_composer;

import java.util.Map;

import org.asmeta.runtime_container.RunOutput;

public class CompositionManager implements IModelComposition{

	@Override
	public Composition getCompositionTreeRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RunOutput getLastOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void runStep(int id, Map<String, String> locationValue) throws CompositionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runStep(int id, Map<String, String> locationValue, boolean dbc) throws CompositionException {
		// TODO Auto-generated method stub
		
	}
	
		@Override
	public void compositionRollback() throws CompositionRollbackException {
		// TODO Auto-generated method stub
		
	}


}
