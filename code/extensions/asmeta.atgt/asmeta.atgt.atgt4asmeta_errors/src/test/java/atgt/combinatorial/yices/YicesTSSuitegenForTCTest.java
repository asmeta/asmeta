package atgt.combinatorial.yices;

import static org.junit.Assert.assertFalse;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.combinatorial.AsmCombCovBuilder;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.CoverageInfo;
import atgt.coverage.CoverageInfoBuilder;
import atgt.coverage.SkipCoveredTCFilter;
import atgt.coverage.TestCondition;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.specification.Axiom;

/**Classe utilizzata per testare la correttezza della generazione della
 * test suite con Yices.*/
public class YicesTSSuitegenForTCTest {

	/* Logger for this class. */
	static final Logger logger = Logger.getLogger(YicesTSSuitegenForTCTest.class);
	static final SimpleLayout layout = new SimpleLayout();
	static FileAppender appender;
	
	/*asm --> contiene le specifiche di ASM.*/
	private static ASMSpecification spec = new ASMSpecification();
	
	/**Metodo per il settaggio dei valori iniziali delle variabili che saranno utilizzate
	 * nel test.*/
	@BeforeClass
	public static void setUp() {
		
		/*Inizializzazione del logger.*/
		try {
		       appender = new FileAppender(layout,"output1.txt",false);
		    } catch(Exception e) {}

	    logger.addAppender(appender);
	    logger.setLevel(Level.DEBUG);
		
		/*Dichiarazione di accessType, cioè di un tipo enumerativo.*/
		EnumType accessType = new EnumType("AccessType");
		/*Aggiunta degli elementi dell'enumerativo.*/
		accessType.addElement("LOOP");
		accessType.addElement("ISDN");
		accessType.addElement("PBX");
		
		EnumType billingType = new EnumType("BillingType");
		billingType.addElement("CALLER");
		billingType.addElement("COLLECT");
		billingType.addElement("EIGHT_HUNDRED");
		
		ElementsType CallTypeType = new EnumType("CallTypeType");
		CallTypeType.addElement("LOCALCALL");
		CallTypeType.addElement("LONGDISTANCE");
		CallTypeType.addElement("INTERNATIONAL");
		
		ElementsType StatoType = new EnumType("StatoType");
		StatoType.addElement("SUCCESS");
		StatoType.addElement("BUSY");
		StatoType.addElement("BLOCKED");
		
		/*Dichiarazione delle variabili, con: nome, tipo, valore iniziale.*/
		//Variable access = new Variable("access", accessType, null);
		//Variable calltype = new Variable("calltype", CallTypeType, null);
		//Variable billing = new Variable("billing", billingType, null);
		//Variable stato = new Variable("stato", StatoType, null);
		
		IdExpressionCreator ecc = new IdExpressionCreator();
		Variable access = new Variable(ecc.createIdExpression("access", null), accessType, null);
		Variable calltype = new Variable(ecc.createIdExpression("calltype", null), CallTypeType, null);
		Variable billing = new Variable(ecc.createIdExpression("billing", null), billingType, null);
		Variable stato = new Variable(ecc.createIdExpression("stato", null), StatoType, null);
		
		spec.addType(StatoType);
		spec.addType(CallTypeType);
		spec.addType(billingType);
		spec.addType(accessType);
		
		/*Inserimento delle variabili monitorate nella specificha ASM.*/
		spec.addVariable(access);
		spec.addVariable(calltype);
		spec.addVariable(billing);
		spec.addVariable(stato);
		
		Expression e1 = new EqualsExpression(access.getIdExpression(), accessType.getEnumConst("LOOP"));
		Expression e2 = new EqualsExpression(billing.getIdExpression(), billingType.getEnumConst("CALLER"));
		
		//spec.addAxiom(new Axiom("assioma", new ImpliesExpression(access.getIdExpression(), accessType.getEnumConst("LOOP"))));
		spec.addAxiom(new Axiom ("assioma", new ImpliesExpression(e1, e2)));
	}
	
	//private void testGen(ASMSpecification spec) {
	/**Metodo per l'inizializzazione dell'albero di copertura. Viene poi chiamato
	 * il metodo generate per la creazione della test suite.*/
	@Test
	public void testGen() {
		/*NWiseCovBuilder --> mette insieme tutti i test predicate.
		 * spec vuol dire che gli do una specifica.
		 * Coverage --> è un albero con tutti i test predicate. 2: combinazione a due, si
		 * potrebbe fare a 3, etc. Contiene tutti i test predicate.*/
		AsmCoverage ct = AsmCombCovBuilder.makePairwiseCovBuilder().getTPTree(spec);

		//AsmCoverage ct = AsmCombCovBuilder.createNWiseCovBuilder(2).getTPTree(spec);
		
		/*Setto a true tutti i tc, cioè, dico che tutti i tp sono da verificare.*/
		for (TestCondition tc : ct.allTPs()) {
			tc.setToVerify(true);
		}
		
		/*Per questo coverage genero la specifica.*/
		AsmTestSuite result = generate(ct, spec);
		assertFalse(result.isEmpty());
		/*Data una specifica ricavo tutit i casi di test (risultato finale).*/
	}

	/**Metodo per la creazione della test suite con Yices.
	 * 
	 * @return result che contiene il risultato della test suite, tutti i vari
	 * casi di test che devono essere eseguiti per la copertura di test selezionata
	 * (pair-wise, 3-wise, etc.)*/
	protected static AsmTestSuite generate(AsmCoverage ct, ASMSpecification spec) {
		
		/*Creo l'albero di copertura.*/
		AsmCoverageTree ctree = new AsmCoverageTree("ROOT");
		/*Aggiungo tutti i test predicate.*/
		ctree.addCoverage(ct);		
		
		YicesTSSuitegenForTC stgen = new YicesTSSuitegenForTC(new AsmProject(spec, ctree));
		
		stgen.setSearchCommonCoverage(true);
		stgen.setTestConditionFilter(SkipCoveredTCFilter.SkipCoveredTCFilter);
		
		AsmTestSuite result = stgen.forCoverageTree(ctree);
		
		/*Stampa dei risultati.*/
		
		for (TestCondition otc : ctree.allTPs()) {
			logger.debug(" TEST : " + otc.getName() + "-->" + otc.getStatusDescription());
			
			/*Memorizzo i risultati di un caso di test.*/
			AsmTestSequence tr = (AsmTestSequence) otc.getTestResult();
			
			if (tr != null && !tr.tpCovered().isEmpty()) {
				String covered = " covered by";
				for (TestCondition tc : tr.tpCovered())
					covered += tc.getName();
			}
			
		}
		
		CoverageInfo r = ctree.accept(CoverageInfoBuilder.INSTANCE);
		
		logger.debug(r);
		
		return result;
	}

}