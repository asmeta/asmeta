/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcerDefault;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.*;

public class FeedbackLoopDefault extends FeedbackLoop{
		
	public FeedbackLoopDefault(Probe probe, Effector effector, Knowledge k){
		super(probe, effector, k);
	}

	//@Override
	public void monitor() {
		//knowledge.sensor = probe.getSomething();

		// perform analysis
		analysis();
	}

	//@Override 
	public void analysis() {

		// analyze all knowledge settings
		Knowledge k = this.getKnowledge();
		k.adaptationRequired = analyzeKnowledge();

		// if adaptation required invoke the planner
		if (k.adaptationRequired) {
			planning();
		}
	}
	
  
	private boolean analyzeKnowledge() {
		
		return false;
	}
	
	//@Override
	public void planning() {

		execution();
	}
	
	//@Override
	public void execution() {
		//effector.setSomething();
	
	}
}
