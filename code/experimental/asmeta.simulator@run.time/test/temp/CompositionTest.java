package temp;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.main.Simulator;
import org.junit.Test;

public class CompositionTest {

	String path = "examples/testUnbound/";
	String path2 = "examples/MVM/";
	String pathTrafficLight = "examples/trafficLightCoSim/";
	String pathTrafficLightCrossManager = "examples/trafficLightCoSimCross/";
	String pathFAC = "examples/exampleFAC2023/";

	@Test
	public void test3() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeHalfDup asm2 = new BiPipeHalfDup(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		BiPipeHalfDup b1 = new BiPipeHalfDup(new LeafAsm(path + "asmH.asm"), asm2);
		b1.eval();
		System.out.println(" ===== new step =====");
		asm2.eval();
	}

	@Test
	public void test2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeHalfDup asm2 = new BiPipeHalfDup(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		asm2.eval();
		System.out.println(" ===== new step =====");
		asm2.eval();
	}

	@Test
	public void testTrafficLight() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		ParN asm2 = new ParN(new LeafAsm(pathTrafficLight + "trafficlightA.asm"),
				new LeafAsm(pathTrafficLight + "trafficlightB.asm"));
		PipeN b1 = new PipeN(new LeafAsm(pathTrafficLight + "controller.asm"), asm2);
		while (true) {
			b1.eval();
			System.out.println(" ===== new step =====");
		}
	}

	@Test
	public void testTrafficLight2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		ParN asm1 = new ParN(new LeafAsm(pathTrafficLight + "trafficlightB.asm"),
				new LeafAsm(pathTrafficLight + "trafficlightB.asm"));
		ParN asm2 = new ParN(new LeafAsm(pathTrafficLight + "trafficlightA.asm"), asm1);
		ParN asm3 = new ParN(new LeafAsm(pathTrafficLight + "trafficlightA.asm"), asm2);
		PipeN b1 = new PipeN(new LeafAsm(pathTrafficLight + "controller.asm"), asm3);
		while (true) {
			b1.eval();
			System.out.println(" ===== new step =====");
		}
	}

	@Test
	public void testTrafficLightCrossManager() throws Exception {
		ParN tram = new ParN(new LeafAsm(pathTrafficLightCrossManager + "pedestrian.asm"),
				new LeafAsm(pathTrafficLightCrossManager + "controllerTram.asm"));
		BiPipeHalfDup manager = new BiPipeHalfDup(tram, new LeafAsm(pathTrafficLightCrossManager + "crossManager.asm"));
		ParN trafficLights = new ParN(new LeafAsm(pathTrafficLightCrossManager + "trafficlightB.asm"),
				new LeafAsm(pathTrafficLightCrossManager + "trafficlightB.asm"),
				new LeafAsm(pathTrafficLightCrossManager + "trafficlightA.asm"),
				new LeafAsm(pathTrafficLightCrossManager + "trafficlightA.asm"));
		PipeN trafficlightcross = new PipeN(new LeafAsm(pathTrafficLightCrossManager + "controller.asm"),
				trafficLights);
		BiPipeFullDup cross = new BiPipeFullDup(manager, trafficlightcross);
		while (true) {
			cross.eval();
			System.out.println(" ===== new step =====");
		}
	}

	@Test
	public void testMVM() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeHalfDup asm2 = new BiPipeHalfDup(new LeafAsm(path2 + "MVMcontroller04.asm"),
				new LeafAsm(path2 + "supervisor03.asm"));
		BiPipeHalfDup b1 = new BiPipeHalfDup(new LeafAsm(path2 + "MVMHardware.asm"), asm2);
		int count = 1;
		while (true) {
			System.out.println(" ===== I/O  ASM  assembly " + count + " =====");
			b1.eval();
			count++;
		}
	}

	// nuovo Codice asmC <|> asmS con BiCompo
	@Test
	public void testHalfDup() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeHalfDup asm2 = new BiPipeHalfDup(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		asm2.eval();
		System.out.println(" ===== new step =====");
		asm2.eval();
	}

	/*
	 * @Test// asmC <|> asmS <|> asmH //ok, ma nel tornare indietro rimangono i
	 * copyMonitored dell'andata: problema? public void testHalfDupN() throws
	 * Exception { Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
	 * NPipeHalfDup asm = new NPipeHalfDup(new LeafAsm(path + "asmC.asm"), new
	 * LeafAsm(path + "asmS.asm"), new LeafAsm(path + "asmH.asm")); asm.evalbis(); }
	 */

	@Test // asmC <||> asmS
	public void testFullDup() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeFullDup asmTest = new BiPipeFullDup(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		asmTest.eval();
		asmTest.eval();
	}

	@Test // asmC <||> asmS <||> asmH giustamente non funziona
	public void testFullDup2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeFullDup asmTest1 = new BiPipeFullDup(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		BiPipeFullDup asmTest2 = new BiPipeFullDup(asmTest1, new LeafAsm(path + "asmH.asm"));
		asmTest2.eval();
	}

	@Test // asmH <||> asmC <||> asmS giustamente non funziona
	public void testFullDup3() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeFullDup asmTest1 = new BiPipeFullDup(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		BiPipeFullDup asmTest2 = new BiPipeFullDup(new LeafAsm(path + "asmH.asm"), asmTest1);
		asmTest2.eval();
	}

	@Test
	public void testParN() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		ParN asm = new ParN(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"),
				new LeafAsm(path + "asmH.asm"));
		asm.eval();
	}

	// Per quanto riguarda l'uso libero come operatore n-ario ovvero C|S|H abbiamo
	// deciso di (come per il cout in C++) implementare l'associatività a sinistra.
	// Quindi C|S|H deve essere eseguita come se avessimo (C|S)|H.

	@Test // asmC | asmS | asmH
	public void testPipeN() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		PipeN asm = new PipeN(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"),
				new LeafAsm(path + "asmH.asm"));
		asm.eval();
	}

	/*
	 * @Test public void testPipe() throws Exception {
	 * Logger.getLogger(Simulator.class).setLevel(Level.DEBUG); Pipe asm = new
	 * Pipe(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
	 * asm.eval(); }
	 */

	/*
	 * @Test // C|(S|H) public void testPipe1() throws Exception {
	 * Logger.getLogger(Simulator.class).setLevel(Level.DEBUG); Pipe asm1 = new
	 * Pipe(new LeafAsm(path + "asmS.asm"),new LeafAsm(path + "asmH.asm")); Pipe
	 * asm2 = new Pipe(new LeafAsm(path + "asmC.asm"),asm1); asm2.eval(); }
	 */
	/*
	 * @Test // (C|S)|H //problema: viene ritornato 2 volte funcC durante
	 * l'esecuzione public void testPipe2() throws Exception {
	 * Logger.getLogger(Simulator.class).setLevel(Level.DEBUG); Pipe asm1 = new
	 * Pipe(new LeafAsm(path + "asmC.asm"),new LeafAsm(path + "asmS.asm")); Pipe
	 * asm2 = new Pipe(asm1,new LeafAsm(path + "asmH.asm")); asm2.eval(); }
	 */

	@Test // ( asmC | asmS | asmH )
	public void testParsePipeN() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" | ").concat(path + "asmS.asm").concat(" | ")
				.concat(path + "asmH.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC <|> asmS )
	public void testParseHalfDup() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" <|> ").concat(path + "asmS.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC <|> asmS <|> asmH )
	public void testParseHalfDupBis() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" <|> ").concat(path + "asmS.asm").concat(" <|> ")
				.concat(path + "asmH.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC <||> asmS )
	public void testParseFullDup() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" <||> ").concat(path + "asmS.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC <||> ( asmS <||> asmH ) )
			// ricontrolla funzionamento
	public void testParseFullDupBis() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" <||> ").concat("( ").concat(path + "asmS.asm")
				.concat(" <||> ").concat(path + "asmH.asm").concat(" )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC <|> ( asmS <||> asmH ) )
			// test non superato
	public void testParseDuplex1() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" <|> ").concat("( ").concat(path + "asmS.asm")
				.concat(" <||> ").concat(path + "asmH.asm").concat(" )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmS <||> asmH ) <|> asmC )
			// test non superato
	public void testParseDuplex2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmS.asm").concat(" <||> ").concat(path + "asmH.asm").concat(" )")
				.concat(" <|> ").concat(path + "asmC.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC <||> ( asmS <|> asmH ) )
			// giusto doppio updateSet finale?
	public void testParseDuplex3() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" <||> ").concat("( ").concat(path + "asmS.asm")
				.concat(" <|> ").concat(path + "asmH.asm").concat(" )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmS <|> asmH ) <||> asmC )
			// funziona ma doppio updateSet finale
	public void testParseDuplex4() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmS.asm").concat(" <|> ").concat(path + "asmH.asm").concat(" )")
				.concat(" <||> ").concat(path + "asmC.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC || asmS || asmH )
			// ok
	public void testParseParN1() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmS || asmS || asmS || asmS || asmS )
			// se ASM equivalenti hanno input diversi, errore: giusto?
	public void testParseParN2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmS.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmS.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmS.asm").concat(" )");
		// .concat(" || ").concat(path+"asmC.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC || asmS || asmH ) || ( asmC || asmS || asmH ) )
			// problema? paralleli esterni alle parentesi interne non "definiti"
			// paralleli esterni alle parentesi interne hanno significato?
	public void testParseParN3() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" || ").concat("( ").concat(path + "asmC.asm")
				.concat(" || ").concat(path + "asmS.asm").concat(" || ").concat(path + "asmH.asm").concat(" )")
				.concat(" )");
		// concat(" || ").concat("( ").concat(path+"asmC.asm").concat(" ||
		// ").concat(path+"asmS.asm").concat(" || ").concat(path+"asmH.asm").concat("
		// )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC || ( asmC || asmS || asmH ) )
			// problema? paralleli esterni alle parentesi interne hanno significato?
			// parelleli non diretti tra 2 asm hanno senso?
	public void testParseParN4() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" || ").concat("( ").concat(path + "asmC.asm")
				.concat(" || ").concat(path + "asmS.asm").concat(" || ").concat(path + "asmH.asm").concat(" )")
				.concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC <||> ( asmS || asmH ) )
			// ok but double funcC updateSet
	public void testParse1() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" <||> ").concat("( ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC <||> asmS ) | asmH ) )
			// ok
	public void testParse1Bis() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" <||> ").concat(path + "asmS.asm").concat(" )")
				.concat(" | ").concat(path + "asmH.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC <|> asmS ) || asmH ) )
			// ok
	public void testParse2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" <|> ").concat(path + "asmS.asm").concat(" )")
				.concat(" || ").concat(path + "asmH.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( asmC | ( asmC || asmS || asmH ) | asmH )
			// ok
	public void testParse3() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat(path + "asmC.asm").concat(" | ").concat("( ").concat(path + "asmC.asm").concat(" || ")
				.concat(path + "asmS.asm").concat(" || ").concat(path + "asmH.asm").concat(" )").concat(" | ")
				.concat(path + "asmH.asm").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC || asmS || asmH ) | ( asmC || asmS || asmH ) )
			// ok
	public void testParse4() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" | ").concat("( ").concat(path + "asmC.asm")
				.concat(" || ").concat(path + "asmS.asm").concat(" || ").concat(path + "asmH.asm").concat(" )")
				.concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC || asmS || asmH ) <|> ( asmC || asmS || asmH ) )
			// ok
			// 1 updateSet from halfdup, 3 updateSet from parN
	public void testParse5() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" <|> ").concat("( ").concat(path + "asmC.asm")
				.concat(" || ").concat(path + "asmS.asm").concat(" || ").concat(path + "asmH.asm").concat(" )")
				.concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC || asmS || asmH ) | ( asmC || asmS || asmH ) | ( asmC || asmS ||
			// asmH ) )
			// ok
	public void testParse6() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" | ").concat("( ").concat(path + "asmC.asm")
				.concat(" || ").concat(path + "asmS.asm").concat(" || ").concat(path + "asmH.asm").concat(" )")
				.concat(" | ").concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm")
				.concat(" || ").concat(path + "asmH.asm").concat(" )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC || asmS || asmH ) <|> ( asmC || asmS || asmH ) <|> ( asmC || asmS ||
			// asmH ) )
			// ricontrolla
	public void testParse7() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" <|> ").concat("( ").concat(path + "asmC.asm")
				.concat(" || ").concat(path + "asmS.asm").concat(" || ").concat(path + "asmH.asm").concat(" )")
				.concat(" <|> ").concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm")
				.concat(" || ").concat(path + "asmH.asm").concat(" )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC || asmS || asmH ) | ( asmS || asmC || asmH ) <|> ( asmC || asmH ||
			// asmS ) )
			// senza parentesi, pipe attraversata anche al ritorno (riguarda)
	public void testParse8() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" | ").concat("( ").concat(path + "asmS.asm")
				.concat(" || ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmH.asm").concat(" )")
				.concat(" <|> ").concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmH.asm")
				.concat(" || ").concat(path + "asmS.asm").concat(" )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test // ( ( asmC || asmS || asmH ) | ( ( asmS || asmC || asmH ) <|> ( asmC || asmH ||
			// asmS ) ) )
			// con parentesi comportamento desiderato
	public void testParse8Bis() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = "( ".concat("( ").concat(path + "asmC.asm").concat(" || ").concat(path + "asmS.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" | ").concat("( ").concat("( ")
				.concat(path + "asmS.asm").concat(" || ").concat(path + "asmC.asm").concat(" || ")
				.concat(path + "asmH.asm").concat(" )").concat(" <|> ").concat("( ").concat(path + "asmC.asm")
				.concat(" || ").concat(path + "asmH.asm").concat(" || ").concat(path + "asmS.asm").concat(" )")
				.concat(" )").concat(" )");
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmI = asm.toComposition();
		asmI.eval();
	}

	@Test
	public void testFAC() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
//		String comp = pathFAC + "asmMulti.asm | (" + pathFAC + "asmInc.asm <|> " + pathFAC + "asmDec.asm)";
//		System.out.println(comp);
//		Parser asm = new Parser(comp);
//		Composition asmI = asm.toComposition();
//		asmI.eval();		
		BiPipeHalfDup asm2 = new BiPipeHalfDup(new LeafAsm(pathFAC + "asmInc.asm"),
				new LeafAsm(pathFAC + "asmDec.asm"));
		PipeN asmF = new PipeN(new LeafAsm(pathFAC + "asmMulti.asm"), asm2);
		asmF.eval();
		System.out.println(" ===== new step =====");
		asmF.eval();

	}

}
