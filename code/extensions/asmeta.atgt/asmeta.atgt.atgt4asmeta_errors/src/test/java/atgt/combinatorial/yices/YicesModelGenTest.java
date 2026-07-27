package atgt.combinatorial.yices;

//import org.apache.log4j.Category;

import org.apache.log4j.Level;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.combinatorial.CombinatorialTestsGeneratorTest;
import atgt.project.AsmProject;

/** Classe che testa Yices con un caso reale: CruiseControl.
 * 
 * @author garganti
 */
public class YicesModelGenTest extends CombinatorialTestsGeneratorTest {
	
	/**Metodo per il settaggio dei valori iniziali delle variabili che saranno utilizzate
	 * nei diversi test.*/
	@BeforeClass
	public static void activateLog() {
		YicesModelGen.logger.setLevel(Level.DEBUG);
		YicesModelGenExec.logger.setLevel(Level.DEBUG);
		YicesModelGen.class.getClassLoader().setDefaultAssertionStatus(true);
		
	}		
	
	/**Metodo che testa il funzionamento di YicesTSSuitegenForTC per il caso
	 * CruiseControl, richiamando il metodo testCCwith della classe padre
	 * CombinatorialTestsGeneratorTest.*/
	@Test
	public void testCC(){
		//String file = atgt.parser.ParseGoferFiles.CruiseControl.toString();
		super.testCCwith(new YicesTSSuitegenForTC(new AsmProject(CruiseControl, null)));
	}

	/**Metodo che genera un insieme di test condition per l'esempio CC.
	 */
	@Test
	//public void testATestConditionCC_Collect() {
	public void testATestConditionCCCollect() {
		//String curDir = System.getProperty("user.dir");
		//System.out.println("curDir: " + curDir);
		//System.out.println("CC: " + atgt.parser.ParseGoferFiles.CruiseControl);
		YicesTSSuitegenForTC stgen = new YicesTSSuitegenForTC(new AsmProject(ThreePowerFour, null));
		super.testWithCollect(stgen);
	}


}
