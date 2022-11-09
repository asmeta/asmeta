package asmetal2java_junit;



import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

import org.asmeta.avallaxt.avalla.Check;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Exec;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.avallaxt.avalla.Step;
import org.asmeta.avallaxt.avalla.StepUntil;


public class AvallaToString extends org.asmeta.avallaxt.avalla.util.AvallaSwitch<String> {
	

	public Scenario scenario; 
	public String nameSce;

	public AvallaToString(Scenario s, String nameSce){
		this.scenario = s;
		if(s.getSpec().trim().contains("./..")) {
			String temp1 = s.getSpec().replace("./..","");
			String temp2 = temp1.substring(1);
			this.nameSce = temp2.replace(".asm","").substring(0,3).toLowerCase().trim();
		}
		else {
			this.nameSce = s.getSpec().substring(0,3).toLowerCase().trim();
		} 
	}
	
	//pathTF - path test Folder
	public static final String pathTF = "src-gen/";
	//estensione file JUnit
	public static final String JUnit_EXT = ".java";
	
	public String temp3 = "";
	public FileWriter file;

	
	//In fase di test viene invocata questa funzione che cicla sugli elementi
	public void parseCommands(Scenario s,int i) throws IOException{
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.US);
		
		//Creo il file
		if(s.getSpec().trim().contains("./..")) {
			String temp1 = s.getSpec().replace("./..","");
			String temp2 = temp1.substring(1);
			temp3 = temp2.replace(".asm","");
			
		}
		else {
			temp3 = s.getSpec().replace(".asm","");
		}
		
		FileWriter file = null;
		try {
			file = new FileWriter(pathTF + temp3 + "_Test" + "_" + i + JUnit_EXT);
			file.write(String.format("import static org.junit.Assert.*;\n"));
			file.write(String.format("import org.junit.Test;\n\n"));
			
			file.write(caseScenario(s,i));
		
			
		//Ciclo sugli elementi
		for (Element s1: s.getElements()){
			
			if(s1 instanceof Check){
				file.write(String.format("\t\t //Check\n"));
				file.write(caseCheck((Check) s1));

			}
			else if(s1 instanceof Step) {
				
				file.write(String.format("\t\t //Step\n"));
				file.write(caseStep((Step) s1));

			}
			else if(s1 instanceof Set){
				
				file.write(String.format("\t\t //Set\n"));
				file.write(caseSet((Set) s1));

				
			}else if(s1 instanceof Exec) {
				
				file.write(String.format("\t\t //Exec\n"));
				file.write(caseExec((Exec) s1));
			}
			
		}
		
		file.write(String.format("\n\t }\n}"));	

	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			if (file != null) {
				file.flush();
				file.close();					
			}
		} catch (IOException e) {
			e.printStackTrace();
			}
		}	 
	}
	
	
	///////////////////////////////////////////
	// Crea intestazione file  + call construct
	///////////////////////////////////////////
	//@Override
	public String caseScenario(Scenario s,int i){
		return String.format("public class %s_Test_%d {\n\n", temp3,i) + 
				String.format("\t @Test\n") + String.format("\t public void %s_Test(){\n\n", temp3)
		 +  createConstr(s);
	}
	
	public String createConstr(Scenario s) {
		String temp = temp3;
		String temp3_lc = temp3.substring(0,3).toLowerCase();
		String createN = createNotNull(s);
		return String.format("\t\t %s %s = new %s();\n%s\n", temp,temp3_lc,temp,createN);
	}
	
	public String createNotNull(Scenario s) {
		String temp = temp3.substring(0,3).toLowerCase();
		return String.format("\t\t assertNotNull(%s);\n",temp);
	}
	///////////////////////////////////////////
	///////////////////////////////////////////
	///////////////////////////////////////////
	 
	@Override
	public String caseCheck(Check s1) {
//		if(s1.toString().contains("outmex") || s1.toString().contains("outMess")) {
//			return "\n";
//		}
		//suppongo ci sia check
		String beforeEqu = s1.getExpression().split("\\=")[0].trim();
		String afterEqu = s1.getExpression().split("\\=")[1].trim();
//		char[] charNum = afterEqu.toCharArray();
//	    StringBuilder sb = new StringBuilder();
//	      for(char c : charNum){
//	         if(Character.isDigit(c)){
//	            sb.append(c);
//	         }
//	      }
	    if((afterEqu.contains("false") || afterEqu.contains("true"))) {
	    	return String.format("\t\t assertEquals(%s.%s.oldValue,%s);\n",nameSce,beforeEqu,afterEqu);
	    }
	    else if(sb.isEmpty()) {
	    	return String.format("\t\t assertEquals(%s.%s.oldValue,%s.%s.newValue.%s);\n",nameSce,beforeEqu,nameSce,beforeEqu,afterEqu);
	    }
	    else {
	    	return String.format("\t\t assertEquals(%s.%s.oldValue.value,%s);\n",nameSce,beforeEqu,sb);
		}		
	}
	
	
	
	@Override
	public String caseSet(Set s1) {
		String leftAssign = nameSce + "." + s1.getLocation() + "." +  "Value" ;
		String s1_V = s1.getValue();
		//equals non contains!!
		if(s1.getValue().contains("false") || s1.getValue().contains("true")) {
			return String.format("\t\t %s = %s;\n",leftAssign,s1_V);
		}
		else {
			return String.format("\t\t %s = %s.%s;\n",leftAssign,leftAssign,s1_V);
		}
	}
	
	@Override
	public String caseExec(Exec s1) {
		
		String beforeEqu = s1.getRule().split("\\:=")[0].trim();
		String afterEqu = s1.getRule().split("\\:=")[1].trim();

		return String.format("\t\t %s.%s.set(%s.%s.newValue.%s);\n",nameSce,beforeEqu,nameSce,beforeEqu,afterEqu) + String.format("\t\t %s.fireUpdateSet();\n",nameSce);
		
	}
	
	@Override
	public String caseStep(Step s1) {
		return String.format("\t\t %s.UpdateASM();\n",nameSce);
		
	}
	
	@Override
	public String caseStepUntil(StepUntil object) {
		// TODO Auto-generated method stub
		return super.caseStepUntil(object);
	}
}
