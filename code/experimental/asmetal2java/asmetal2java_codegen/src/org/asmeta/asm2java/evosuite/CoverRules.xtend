package org.asmeta.asm2java.evosuite

class CoverRules {
	
	static def coverRulesFunction(Rules rules){
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
	
	static def coverAllRules(Rules rules){
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