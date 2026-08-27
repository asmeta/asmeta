package atgt.combinatorial.yices;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.combinatorial.NWiseEqTestCondition;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.TestCondition;
import atgt.specification.location.Variable;
import extgt.coverage.combinatorial.MonitoredData;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.MCExecutionResultReader;
import tgtlib.specification.Axiom;

/**Classe che testa Yices con una specifica data.
 * 
 * @author garganti
 */
public class TestYicesComplete {
		
	/*Logger for this class.*/
	static final Logger logger = Logger.getLogger(TestYicesComplete.class);
	static SimpleLayout layout = new SimpleLayout();
	static FileAppender appender;
	
	/*monitoredData --> contiene le variabili da controllare.*/
	private static MonitoredData monitoredData;
	/*tp --> contiene i test predicate.*/
	private static NWiseEqTestCondition tp;
	/*cont --> contiene i constraint.*/
	private static Collection<Axiom> cont;
	private static Axiom assioma;

	/**Metodo per il settaggio dei valori iniziali delle variabili che saranno utilizzate
	 * nei diversi test.*/
	@BeforeClass
	public static void setup() {
		
		/*Inizializzazione del logger.*/
		try {
		       appender = new FileAppender(layout,"output1.txt",false);
		    } catch(Exception e) {}

	    logger.addAppender(appender);
	    logger.setLevel(Level.DEBUG);
		
		/*Dichiarazione di accessType, cioè di un tipo enumerativo.*/
		EnumType accessType = new EnumType("AccessType");
		/*Aggiunta degli elementi dell'enumerativo accessType.*/
		accessType.addElement("LOOP");
		accessType.addElement("ISDN");
		accessType.addElement("PBX");
		
		EnumType billingType = new EnumType("BillingType");
		billingType.addElement("CALLER");
		billingType.addElement("COLLECT");
		billingType.addElement("EIGHT_HUNDRED");
		
		EnumType CallTypeType = new EnumType("CallTypeType");
		CallTypeType.addElement("LOCALCALL");
		CallTypeType.addElement("LONGDISTANCE");
		CallTypeType.addElement("INTERNATIONAL");
		
		ElementsType StatoType = new EnumType("StatoType");
		StatoType.addElement("SUCCESS");
		StatoType.addElement("BUSY");
		StatoType.addElement("BLOCKED");
		
		IdExpressionCreator ecc = new IdExpressionCreator();
		Variable access = new Variable(ecc.createIdExpression("access", null), accessType, null);
		Variable calltype = new Variable(ecc.createIdExpression("calltype", null), CallTypeType, null);
		Variable billing = new Variable(ecc.createIdExpression("billing", null), billingType, null);
		Variable stato = new Variable(ecc.createIdExpression("stato", null), StatoType, null);

		/*Dichiarazione delle variabili, con: nome, tipo, valore iniziale.*/
		//Variable access = new Variable("access", accessType, null);
		//Variable calltype = new Variable("calltype", CallTypeType, null);
		//Variable billing = new Variable("billing", billingType, null);
		//Variable stato = new Variable("stato", StatoType, null);

		/*Creazione di monitoredData e inserimento delle variabili monitorate.*/
		monitoredData = new MonitoredData();
		monitoredData.add(access);
		monitoredData.add(calltype);
		monitoredData.add(billing);
		monitoredData.add(stato);

		logger.debug(monitoredData.toString());

		/*Creazione del test predicate.*/
		/*tp: (assert (and (= access LOOP) (= billing COLLECT)))*/
		/*Dichiarazione di vars, che deve contenere le variabili contenute nel test predicate.*/
		Variable[] vars = { access, billing };
		/*Dichiarazione dei valori che le variabili assumono.*/
		EnumConst[] vals = { accessType.getEnumConst("LOOP"), billingType.getEnumConst("COLLECT") };
		/*Assegno a tp il test predicate che si sta creando. Assegno un nome, le variabili
		 * e i valori delle variabili.*/
		tp = new NWiseEqTestCondition("prova", Arrays.asList(vars), Arrays
				.asList(vals));

		/*Costruzione di un constraint.*/
		/*(assert (=> (= billing COLLECT) (/= callType INTERNATIONAL)))*/
		
		/*e1 conterrà: billing = COLLECT*/
		EqualsExpression e1 = new EqualsExpression(billing.getIdExpression(),
				billingType.getEnumConst("COLLECT"));
		/*e2 conterrà: callType = INTERNATIONAL*/
		EqualsExpression e2 = new EqualsExpression(calltype.getIdExpression(),
				CallTypeType.getEnumConst("INTERNATIONAL"));
		/*e3 conterrà: billing != COLLECT*/
		NotEqualsExpression e3 = new NotEqualsExpression(billing.getIdExpression(),
				billingType.getEnumConst("COLLECT"));
		
		cont = new ArrayList<Axiom>();
		/*Aggiunta dei constraint.
		 * billing = COLLECT --> callType = INTERNATIONAL*/
		cont.add(new Axiom("assioma", new ImpliesExpression(e1, e2)));
		
		assioma = new Axiom("unSat", new ImpliesExpression(e2, e3));
		
	}

	/**Metodo che testa la classe YicesModelGenExec e controlla che il risultato
	 * restituito dal programma chiamato da linea di comando sia corretto e l'analisi
	 * eseguita sia corretta.*/
	@Test
	public void testModel() {
		
		YicesModelGen yices = new YicesModelGenExec(new MonitoredData().getVars(), Collections.EMPTY_LIST,AsmTestSequence.factory);
		
		/*cmds conterrà i comandi di yices per il modello (monitoredData, cont, tp) appena
		 * creato.
		 * buildComands costruisce la traduzione in Yices del modello del sistema.*/
		//List<String> cmds = yices.buildComands(monitoredData, cont, tp);
		List<String> cmds = YicesModelGen.buildComands(monitoredData.getVars(), cont, tp);
		
		logger.debug("Comando: " + cmds);
		/*Chiama Yices vero e proprio e ne prende il risultato calcolato.*/
		StringBuffer res1 = yices.getModelFor(cmds);
		logger.debug("res: " + res1);
		StringReader input = new StringReader(res1.toString());
		AsmTestCondition tp2 = new AsmTestCondition(tp.getName(), tp.getCondition());
		AsmTestSequence test = new AsmTestSequence(tp2);
		/*Esecuzione dell'analisi del modello restituito da Yices.*/
		MCAnalysisResult res2 = yices.analyses(new MCExecutionResultReader(input), test);

		Assert.assertTrue(res2.isTestFound());
		Assert.assertEquals(true, test.allInstructions().toString().contains("access=LOOP"));
	}
	
	/**Metodo che testa la funzionalità del metodo analysis. Viene testata la lettura dell'output
	 * di Yices. In questo test viene fornito un modello non soddisfacibile, quindi il metodo
	 * di analisi deve restituire che non è stato trovata alcuna assegnazione possibile
	 * alle variabili monitorate.*/
	@Test
	public void testAnalysesUnSat () {
		
		/*Viene aggiunto un constraint che rende insoddisfacibile il sistema,
		 * per poter testare la capactà del programma a rilevare
		 * un modello non soddisfacibile.*/
		
		cont.add(assioma);
		
		YicesModelGen<?, AsmTestSequence> yices = new YicesModelGenExec<TestCondition<AsmTestSequence>, AsmTestSequence>(Collections.EMPTY_LIST, Collections.EMPTY_LIST,AsmTestSequence.factory);
		
		/*cmds conterrà i comandi di yices per il modello (monitoredData, cont, tp) appena
		 * creato.
		 * buildComands costruisce la traduzione in Yices del modello del sistema.*/
		//List<String> cmds = yices.buildComands(monitoredData, cont, tp);
		List<String> cmds = YicesModelGen.buildComands(monitoredData.getVars(), cont, tp);
		
		/*Chiama Yices vero e proprio e ne prende il risultato calcolato.*/
		StringBuffer res1 = yices.getModelFor(cmds);
		logger.debug("res: " + res1);
		StringReader input = new StringReader(res1.toString());
		
		AsmTestSequence test = new AsmTestSequence(tp);
		/*Esecuzione dell'analisi del modello restituito da Yices.*/
		MCAnalysisResult res2 = yices.analyses(new MCExecutionResultReader(input), test);

		Assert.assertFalse(res2.isTestFound());
		
	}
}