/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcer;



public class KnowledgeSingleton {
	private static KnowledgeSingleton knowledge = null;	

	
	//All you need to extend a singleton class is a constructor with protected or package-default 
	//in the singleton class. If there are only private constructors you simply won't be able to extend it. 
	//If there are public constructors then it's not a singleton class.
	protected KnowledgeSingleton() {
	}


	/** flag showing whether adaptation is required or not*/
	public boolean adaptationRequired = false;
    
	
	public static KnowledgeSingleton getInstance(){
		if (knowledge == null)
			knowledge = new KnowledgeSingleton();
		return knowledge;
	}
	
	
	public boolean systemStateChanged() { return true; }
	
	
}


