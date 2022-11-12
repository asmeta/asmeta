package asmetal2java_junit;
import static asmetal2java_junit.Formatter.formatCode;

import java.io.FileWriter;
import java.io.IOException;
//import java.util.Formatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
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
		//StringBuffer sb = new StringBuffer();
		//Formatter form = new Formatter();

		
		
		//sb.append(formatCode("}\n"));
		
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
			String import1 = formatCode("import static org.junit.Assert.*;\n");
			String import2 = formatCode("import org.junit.Test;\n");
			file.write(import1 + "\n");
			file.write(import2 + "\n");
			
			String pubClass = formatCode(caseScenario(s,i));
			file.write(pubClass + "\n");
			
			
		//Ciclo sugli elementi
		for (Element s1: s.getElements()){
			
			if(s1 instanceof Check){
				String checkForm = formatCode(caseCheck((Check) s1));
				file.write(checkForm + "\n");

			}
			else if(s1 instanceof Step) {
				String stepForm = formatCode(caseStep((Step) s1));
				file.write(stepForm + "\n");

			}
			else if(s1 instanceof Set){
				String setForm = formatCode(caseSet((Set) s1));
				file.write(setForm + "\n");

				
			}else if(s1 instanceof Exec) {
				String execForm = formatCode(caseExec((Exec) s1));
				file.write(execForm);
			}
			
		}
		String endDoc = formatCode(" }\n}");
		file.write(endDoc);
		
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
		// + 
		//formatCode("@Test\n") + formatCode("public void" + temp3 + "_Test(){\n\n")
 //+  createConstr(s);
		return "public class " + temp3 + "_Test_" + i + "{\n" + "@Test\n" + 
				"public void " + temp3 + "_Test(){\n" + createConstr(s);

	}
	
	public String createConstr(Scenario s) {
		String temp = temp3;
		String temp3_lc = temp3.substring(0,3).toLowerCase();
		String createN = createNotNull(s);
		return temp + " " +  temp3_lc +  " = new " + temp + "();\n" + createN;
	}
	
	public String createNotNull(Scenario s) {
		String temp = temp3.substring(0,3).toLowerCase();
		return "assertNotNull(" + temp + ");\n";
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
		
		if(!isInteger(afterEqu)){
			if((afterEqu.equals("false"))){
				return "//Check\n" + "assertFalse("+ nameSce + "." + beforeEqu + ".oldValue);\n";
				
			}else if(afterEqu.equals("true")){
				
				return "//Check\n" + "assertTrue("+ nameSce + "." + beforeEqu + ".oldValue);\n";
			}
			else {
				return "//Check\n" + "assertEquals("+ nameSce + "." + beforeEqu + ".oldValue," + nameSce + "." + beforeEqu +
						".newValue);\n";
			}
		}
		else {
			//int temp = Integer.valueOf(afterEqu);
			//assertTrue(sis.waterpressure.get().value == 3);
			return "//Check\n" + "assertTrue(" + nameSce + "." + beforeEqu + ".get().value == " + afterEqu + ");\n";
			//Integer.parseInt(afterEqu) + ");\n";
		}
	}
	

	
	@Override
	public String caseSet(Set s1) {
		String leftAssign = nameSce + "." + s1.getLocation() + "." +  "Value" ;
		String s1_V = s1.getValue();
		String s1_GetLoc = StringUtils.capitalize(s1.getLocation());
		
		
		if(!isInteger(s1_V)){
			if(s1.getValue().contains("false") || s1.getValue().contains("true")) {
				return "//Set\n" + leftAssign + "=" + s1_V + ";\n";
			}
			else {
				return "//Set\n" + leftAssign + "=" + leftAssign + "." + s1_V + ";\n";
			}
		}
		else {
			
			return "//Set\n" + leftAssign + "= new " + temp3 +"_sig." + s1_GetLoc + "();\n" +
					leftAssign + ".value = " + s1_V + ";\n";
			
		}
		
		
		//sis.delta.Value = new SIS_sig.Delta();
		//sis.delta.Value.value = -1;
	}
	
	@Override
	public String caseExec(Exec s1) {
		
		String beforeEqu = s1.getRule().split("\\:=")[0].trim();
		String afterEqu = s1.getRule().split("\\:=")[1].trim();
		
		return "//Exec\n" + nameSce + "." + beforeEqu + ".set("+ nameSce + "." + beforeEqu +".newValue."+ afterEqu + ");\n" +
				nameSce + ".fireUpdateSet();\n";
	}
	
	@Override
	public String caseStep(Step s1) {
		return "//Step\n" + nameSce + ".UpdateASM();\n";
		
	}
	
//	@Override
//	public String caseStepUntil(StepUntil object) {
//		// TODO Auto-generated method stub
//		return super.caseStepUntil(object);
//	}
	
	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	
	
}
