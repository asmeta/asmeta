package org.asmeta.asm2java.evosuite

/**
 * Contains the methods to cover the rules of the abstract state machine (ASM).
 */
class CoverRules {
	
	/** 
	 * Create a method that calls all the cover rules functions
	 * 
	 * @param rules RulesGetter interface with which request the 
	 * 		rule to examine the branches.
	 */
	static def coverRulesFunction(RulesGetter rules){
		val sb = new StringBuffer();
		
		sb.append(
		'''/**
		* Calls all rules covering functions.
		*/''')
		sb.append(System.lineSeparator)
		
		sb.append("\t").append('''private void coverRules(){''');
		sb.append(System.lineSeparator)
		
		for(String rule : rules.getRulesName()){
			sb.append("\t\t").append('''cover_«rule»_branches();''');
			sb.append(System.lineSeparator)
		}
		
		sb.append("\t").append('''}''');
		sb.append(System.lineSeparator)
		
		return sb.toString;
	}
	
	/** 
	 * Create a method for the rule to cover all its branches
	 * 
	 * @param rules RulesGetter interface with which request the 
	 * 		rule to examine the branches.
	 */
	static def coverAllRules(RulesGetter rules){
				val sb = new StringBuffer();
		
		for(String rule : rules.getRulesName()){
			sb.append('''
			/**
			* Cover all the branches of the rule «rule».
			*/''');
			sb.append(System.lineSeparator);
			sb.append("\t").append('''private void cover_«rule»_branches(){''');
			sb.append(System.lineSeparator);
			
			for(String branch : rules.getRuleBranches(rule)){
				sb.append("\t\t").append('''if( this.execution.«branch» ){''');
				sb.append(System.lineSeparator);
				sb.append("\t\t\t").append('''System.out.println("«branch» covered"); ''');
				sb.append(System.lineSeparator);
				sb.append("\t\t").append('''}''');
				sb.append(System.lineSeparator);
			}
			
			sb.append("\t").append('''}''');
			sb.append(System.lineSeparator);
		}
		
		return sb.toString;
		
	}
	
}