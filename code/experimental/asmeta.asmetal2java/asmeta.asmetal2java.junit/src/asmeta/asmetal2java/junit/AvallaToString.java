package asmeta.asmetal2java.junit;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.asmeta.avallaxt.avalla.Check;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Exec;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.avallaxt.avalla.Step;
import org.asmeta.avallaxt.avalla.StepUntil;
import org.asmeta.avallaxt.avalla.util.AvallaSwitch;


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
		return "public class " + nameSce + "_Test_" + i + "{\n" + "@Test\n" + "public void "
				+ nameSce + "_Test(){\n"
					+ createConstr(s);
	}

	public String createConstr(Scenario s) {
		String createN = createNotNull(s);
		return nameSce + " " + nameSce.toLowerCase() + " = new " + nameSce + "();\n" 
				+ createN;
	}

	public String createNotNull(Scenario s) {
		return "assertNotNull(" + nameSce.toLowerCase() + ");\n";
	}
	
	///////////////////////////////////////////
	///////////////////////////////////////////
	///////////////////////////////////////////

	@Override
	public String caseCheck(Check s1) {
		String beforeEqu = s1.getExpression().split("\\=")[0].trim();
		String afterEqu = s1.getExpression().split("\\=")[1].trim();
		if (!isInteger(afterEqu)) {
			if ((afterEqu.equals("false"))) {
				return "//Check\n" + "assertFalse(" + nameSce.toLowerCase() + "." 
						+ beforeEqu + ".oldValue);\n";
			} else if (afterEqu.equals("true")) {
				return "//Check\n" + "assertTrue(" + nameSce.toLowerCase() + "." 
						+ beforeEqu + ".oldValue);\n";	
			} 
			else if(afterEqu.matches(".*\".*")) {
				return "//Check\n" + "assertEquals(" + nameSce.toLowerCase() 
					+ "." + beforeEqu + ".oldValue," + afterEqu + ");\n";
			}
			else {					
				return "//Check\n" + "assertEquals(" + nameSce.toLowerCase() + "." 
						+ beforeEqu + ".oldValue," + nameSce.toLowerCase() + "."
						+ beforeEqu + ".oldValue." + afterEqu +");\n";
			}
		} else {
			return "//Check\n" + "assertEquals(" + nameSce.toLowerCase() + "." 
						+ beforeEqu + ".oldValue.value ," + "Integer.valueOf("
						+ afterEqu + "));\n";
		}
	}

	@Override
	public String caseSet(Set s1) {
		String leftAssign = nameSce.toLowerCase() + "." + s1.getLocation() + "." + "Value";
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
			return "//Set\n" + leftAssign + " = new " + nameSce.toUpperCase() + "_sig." + s_Gl 
					+ "();\n" + leftAssign + ".value = "
					+ s1_V + ";\n";
		}
	}

	@Override
	public String caseExec(Exec s1) {
		String beforeEqu = s1.getRule().split("\\:=")[0].trim();
		String afterEqu = s1.getRule().split("\\:=")[1].trim();
		String leftAssign = nameSce.toLowerCase() + "." + beforeEqu + "." + "newValue";
		
		if (!isInteger(afterEqu)) {
			if (afterEqu.equals("false") || afterEqu.equals("true")) {
				return "//Exec\n" + leftAssign + "=" + afterEqu + ";\n" 
						+ nameSce.toLowerCase() + ".fireUpdateSet();\n";
			} else {
				return "//Exec\n" + leftAssign + "=" + leftAssign + "." 
						+ afterEqu + ";\n" + nameSce.toLowerCase()
						+ ".fireUpdateSet();\n";
			}
		} else {
			return "//Exec\n" + nameSce.toLowerCase() + "." + beforeEqu 
					+ ".newValue = " + afterEqu + ";\n" + 
					nameSce.toLowerCase() + ".fireUpdateSet();\n";
		}
	}

	@Override
	public String caseStep(Step s1) {
		return "//Step\n" + nameSce.toLowerCase() + ".UpdateASM();\n";
	}
	
	
	@Override
	public String caseStepUntil(StepUntil s1) {
		String beforeEqu = s1.getExpression().split("\\=")[0].trim();
		String afterEqu = s1.getExpression().split("\\=")[1].trim();
		if (!isInteger(afterEqu)) {
			if (afterEqu.equals("false") || afterEqu.equals("true")) {
				return "//Step Until\n" + "while(" + nameSce.toLowerCase() 
						+ "." + beforeEqu + ".oldValue"
						+ " != " + afterEqu + "){\n" 
						+ nameSce.toLowerCase() + ".UpdateASM();\n " + "}\n";
			}else {
				return "//Step Until\n" + "while(" + nameSce.toLowerCase() 
						+ "." + beforeEqu + ".oldValue"
						+ " != " + nameSce.toLowerCase() + "." + afterEqu + ".newValue" + "){\n" 
						+ nameSce.toLowerCase() + ".UpdateASM();\n " + "}\n";
			}
		}
		else {
			return "//Step Until\n" + "while(" + nameSce.toLowerCase() + "." 
					+ beforeEqu + ".newValue"
					+ " != " + afterEqu + "){\n" 
					+ nameSce.toLowerCase() + ".UpdateASM();\n " + "}\n";
			
			
				
		}
	}

	//Verifica se la stringa è un integer
	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
