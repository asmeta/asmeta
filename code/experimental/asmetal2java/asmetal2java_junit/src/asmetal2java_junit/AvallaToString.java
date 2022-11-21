package asmetal2java_junit;


import java.io.IOException;
import java.lang.Enum.EnumDesc;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.asmeta.asm2java.compiler.CompilatoreJava;
import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.avallaxt.avalla.Check;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Exec;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.avallaxt.avalla.Step;
import org.asmeta.avallaxt.avalla.StepUntil;
import org.asmeta.avallaxt.avalla.util.AvallaSwitch;
import org.asmeta.simulator.value.EnumValue;
//import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.TypeOfDependentExpression;
import org.junit.BeforeClass;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.impl.EnumTermImpl;
import asmeta.definitions.domains.EnumElement;

public class AvallaToString extends AvallaSwitch<String> {

	public Scenario scenario;
	public String nameSce = "";

	public AvallaToString(Scenario s, String nameSce) {
		this.scenario = s;
		this.nameSce = nameSce;
	}
	

	// Get location for set/exec
	public String s_Gl = "";

	// In fase di test viene invocata questa funzione che cicla sugli elementi
	public String parseCommands(Scenario s, int i) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		sb.append("import static org.junit.Assert.*;\n");
		sb.append("import org.junit.Test;\n");
		sb.append(caseScenario(s, i));

		// Ciclo sugli elementi
		for (Element s1 : s.getElements()) {
			
			if (s1 instanceof Check) {
				sb.append(caseCheck((Check) s1));
			} else if (s1 instanceof Step) {
				sb.append(caseStep((Step) s1));
			} else if (s1 instanceof Set) {
				sb.append(caseSet((Set) s1));
			} else if (s1 instanceof Exec) {
				sb.append(caseExec((Exec) s1));	
			} else if (s1 instanceof StepUntil) {
				sb.append(caseStepUntil((StepUntil) s1));
			}
		}
			sb.append(" }\n}");
		return sb.toString();
	}

	///////////////////////////////////////////
	// Crea intestazione file + call construct
	///////////////////////////////////////////
	//@Override
	public String caseScenario(Scenario s, int i) {
		return "public class " + nameSce + "_Test_" + i + "{\n" + "@Test\n" + "public void " + nameSce + "_Test(){\n"
				+ createConstr(s);
	}

	public String createConstr(Scenario s) {
		//Variabile che contiene i primi 3 caratteri del nome dello scenario
		String nameSce_lc = nameSce.substring(0, 3).toLowerCase();
		String createN = createNotNull(s);
		return nameSce + " " + nameSce_lc + " = new " + nameSce + "();\n" + createN;
	}

	public String createNotNull(Scenario s) {
		String nameSce_lc = nameSce.substring(0, 3).toLowerCase();
		return "assertNotNull(" + nameSce_lc + ");\n";
	}
	///////////////////////////////////////////
	///////////////////////////////////////////
	///////////////////////////////////////////

	@Override
	public String caseCheck(Check s1) {
		String beforeEqu = s1.getExpression().split("\\=")[0].trim();
		String afterEqu = s1.getExpression().split("\\=")[1].trim();
		String spec_lc = nameSce.substring(0, 3).toLowerCase();
		
		if (!isInteger(afterEqu)) {
			if ((afterEqu.equals("false"))) {
				return "//Check\n" + "assertFalse(" + spec_lc + "." + beforeEqu + ".oldValue);\n";
			} else if (afterEqu.equals("true")) {
				return "//Check\n" + "assertTrue(" + spec_lc + "." + beforeEqu + ".oldValue);\n";	
			} 
			//Contains space || Lower case
			else if(afterEqu.matches(".*\\s.*") || afterEqu.matches("[a-z]*")) { 
				return "//Check\n" + "assertEquals(" + spec_lc + "." + beforeEqu + ".oldValue," +" \"" + afterEqu + "\");\n";
			}
			else {					
				return "//Check\n" + "assertEquals(" + spec_lc + "." + beforeEqu + ".oldValue," + spec_lc + "."
						+ beforeEqu + ".oldValue." + afterEqu +");\n";
			}
		} else {
			return "//Check\n" + "assertEquals(" + spec_lc + "." + beforeEqu + ".oldValue.value ," + "Integer.valueOf("
					+ afterEqu + "));\n";
		}
	}

	@Override
	public String caseSet(Set s1) {
		String spec_lc = nameSce.substring(0, 3).toLowerCase();
		String spec_UC = nameSce.substring(0, 3).toUpperCase();
		String leftAssign = spec_lc + "." + s1.getLocation() + "." + "Value";
		String s1_V = s1.getValue();
		String s_Gl = StringUtils.capitalize(s1.getLocation());

		if (!isInteger(s1_V)) {
			if (s1.getValue().equals("false") || s1.getValue().equals("true")) {
				return "//Set\n" + leftAssign + " = " + s1_V + ";\n";
			} else {
				return "//Set\n" + leftAssign + " = " + leftAssign + "." + s1_V + ";\n";
			}
		} else {
			//Set un valore intero 
			return "//Set\n" + leftAssign + " = new " + spec_UC + "_sig." + s_Gl + "();\n" + leftAssign + ".value = "
					+ s1_V + ";\n";
		}
	}

	@Override
	public String caseExec(Exec s1) {
		String beforeEqu = s1.getRule().split("\\:=")[0].trim();
		String afterEqu = s1.getRule().split("\\:=")[1].trim();
		String spec_lc = nameSce.substring(0, 3).toLowerCase();
		String leftAssign = spec_lc + "." + beforeEqu + "." + "newValue";
		
		if (!isInteger(afterEqu)) {
			if (afterEqu.equals("false") || afterEqu.equals("true")) {
				return "//Exec\n" + leftAssign + "=" + afterEqu + ";\n" + spec_lc + ".fireUpdateSet();\n";
			} else {
				return "//Exec\n" + leftAssign + "=" + leftAssign + "." + afterEqu + ";\n" + spec_lc
						+ ".fireUpdateSet();\n";
			}
		} else {
			return "//Exec\n" + spec_lc + "." + beforeEqu + ".newValue = " + afterEqu + ";\n" + spec_lc
					+ ".fireUpdateSet();\n";
		}
	}

	@Override
	public String caseStep(Step s1) {
		String spec_lc = nameSce.substring(0, 3).toLowerCase();
		return "//Step\n" + spec_lc + ".UpdateASM();\n";
	}
	
	
	@Override
	public String caseStepUntil(StepUntil s1) {
		String beforeEqu = s1.getExpression().split("\\=")[0].trim();
		String afterEqu = s1.getExpression().split("\\=")[1].trim();
		String spec_lc = nameSce.substring(0, 3).toLowerCase();
		
		
		return "//Step\n" + "while(" + spec_lc + "." + beforeEqu + ".oldValue"
				+ " != " + spec_lc + "." + afterEqu + ".oldValue){\n" 
				+ spec_lc + ".UpdateASM();\n " + "}\n";
	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

}
