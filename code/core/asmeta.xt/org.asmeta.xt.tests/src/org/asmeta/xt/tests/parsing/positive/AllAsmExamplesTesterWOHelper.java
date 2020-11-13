package org.asmeta.xt.tests.parsing.positive;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.asmeta.xt.AsmetaLStandaloneSetup;
import org.asmeta.xt.parser.AsmetaLParserWOHelper;
import org.asmeta.xt.parser.ParseAndValidateResult;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Injector;

/**
 * test for all the examples in asm_examples
 * 
 */
public class AllAsmExamplesTesterWOHelper {

	@SuppressWarnings("serial")
	static ArrayList<String> check = new ArrayList<String>() {
		{
			// ---------- RULE OVERLOADING WAS NOT RECOGNIZED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\ABZ2016\\Hemodialysis_ref1_par.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\ABZ2016\\Hemodialysis_ref2_par.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\ABZ2016\\Hemodialysis_ref3_par.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\ABZ2016\\Hemodialysis_ref1_forMC.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\ABZ2016\\Hemodialysis_ref2_forMC.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\ABZ2016\\Hemodialysis_ref3_forMC.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\ABZ2016\\Hemodialysis_ref4_forMC.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\ABZ2016\\Hemodialysis_ref4.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\SCP2017\\HemodialysisRef1.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\SCP2017\\HemodialysisRef2.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\SCP2017\\HemodialysisRef3.asm");

			// ---------- LTL, CTL
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\flashProtocol\\CTLlibrary.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\SCP2017\\CTLlibrary.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\flashProtocol\\LTLlibrary.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\SCP2017\\LTLlibrary.asm");

			// ---------- ERROR BECAUSE OF UNDEF DOMAIN
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\agents\\FileSystem.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\agents\\producerConsumerGround.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\agents\\producerConsumerRaff.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\philosophers\\philosophers1.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\philosophers\\philosophers2.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\philosophers\\philosophers3.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\smartHome\\smartHome.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\smartHome\\smartHomeNoMultiChannel.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\Yahalom\\Yahalom.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\Yahalom\\FlawedYahalom.asm");

			// ---------- VARIABLES WITH NAME SIMILAR TO THE DEFINITION OF COMPLEX NUMBERS
			// NOT RECOGNIZED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\GilbreathCardTrick\\GilbreathCardTrick.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\GilbreathCardTrick\\GilbreathCardTrickFasterShuffleForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\GilbreathCardTrick\\GilbreathCardTrickForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\GilbreathCardTrick\\GilbreathCardTrickSimpl.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\GilbreathCardTrick\\workingButNotElegant\\GilbreathCardTrickWithAuxFunctionSimple.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\GilbreathCardTrick\\workingButNotElegant\\GilbreathCardTrickWithAuxFunctionSimpleForAsmetaSMV.asm");

			// ---------- NAME IS KEYWORD
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\simple_example\\while.asm");

			// ---------- THE PATH WAS WRONGLY WRITTEN
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\simple_example\\SwapSort.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\validation\\old\\modelsRenamedFunctions\\SelfHealingSubsystem.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\validation\\old\\modelsRenamedFunctions\\OrganizationMiddleware.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\validation\\old\\modelsRenamedFunctions\\Knowledge.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\validation\\old\\modelsRenamedFunctions\\CameraAgentSubsystem.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\validation\\old\\mainForValidation.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\OrganizationMiddleware.asm");

			// ---------- DOUBLE CR AFTER "//"
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\models\\wiper_abs2.asm");

			// ---------- THE SYMBOL '+' OR '-' MUST NOT BE PLACED INSIDE THE REAL PART OF
			// COMPLEX NUMBER (IT IS ALREADY AN UNARY OPERATION)
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\simple_example\\AdvancedClock2.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\simple_example\\fibonacci.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\sultanSuccessors\\sultanGreedy.asm");

			// ---------- COMPATIBILITY BETWEEN DOMAINS IN PRODUCT NOT IMPLEMENTED AND
			// FUNCTION FROM STDL NOT RECOGNIZED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\parking.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\emptyCellAs0\\RushHour.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\emptyCellAs0\\RushHourForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\emptyCellAs0\\RushHourMon.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\emptyCellAs0\\RushHourMonForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\emptyCellAs0\\RushHourMonSimplForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\emptyCellAs0\\RushHourSimplForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\RushHour.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\RushHourForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\RushHourMon.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\RushHourMonForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\RushHourMonSimplForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\RushHour\\RushHourSimplForAsmetaSMV.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\swapBoard.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\models\\OrderSystem2.asm");

			// ---------- FUNCTION BODY WITH POWERSET WAS NOT IMPLEMENTED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\ticTacToe\\ticTacToe_simulator_with_inv.asm");

			// ---------- LETTERS WITH THE ACCENT WAS NOT DEFINED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\sultanSuccessors\\sultan.asm");

			// ---------- PROBLEMS WITH EMPTY SEQUENCES
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\simple_example\\swapSortOnSeq.asm");

			// ---------- PROBLEMS WITH FUNCTIONS RETURNING POWERSET USED AS RANGE IN CHOOSE
			// RULE OR LET RULE
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\models\\ordersystem.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\models\\ordersystem_april2010.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\simple_example\\QuickSort.asm");
			add("..\\..\\..\\..\\asm_examples\\SystemCUMLProfile_metahooking\\chan.asm");

			// ---------- PROBLEMS WITH STANDARD LIBRARY
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_HC_refined_with_arbiter_forMC.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_HC_refined_with_arbiter_forMC_old.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_refined.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_refined_forMC.asm");
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\CommonBehaviour\\Scheduler.asm");
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\Pattern\\SwingingBuffer.asm");

			// ---------- ENUMERATION ELEMENT NOT DEFINED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\PillBox\\Level2\\pillbox_2_for_RefProv.asm");

			// ---------- GE BETWEEN NATURAL AND INTEGER NOT DEFINED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\PillBox\\Level3\\pillbox_FULL.asm");

			// ---------- OTHER ERRORS
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\rpns\\r\\BibliotecaASM.asm");
			add("..\\..\\..\\..\\asm_examples\\unibgstudents\\VideotecaASM.asm");

			// ---------- UNDERSCORE WAS NOT ALLOWED IN STRING CONSTANTS
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\MySmartHomeAQ_HC_refined.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\MySmartHomeAQ_HC_refined_with_arbiter.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\MySmartHomeFD_refined.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\MySmartHomeHCplus_refined.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\MySmartHomeHC_refined.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_HC_refined_with_arbiter.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_HC_refined_with_arbiter2.asm");
			add("..\\..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_HC_refined.asm");

			// ---------- ENUMERATION ELEMENT NOT DEFINED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\Test\\Test_Sem_Async.asm");
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\Test\\Test_Sem_Sync.asm");
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\Pattern\\Semaphore.asm");

			// ASM CANNOT BE IMPORTED: EXPORT WAS NOT MANAGED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\SystemCUMLProfile_metahooking\\main.asm");

			// FUNCTION WAS NOT IMPORTED IF THE IMPORT WAS WRITTEN AS "./AsmName"
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\Application\\CompleteApplication.asm");
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\Application\\RobotController.asm");
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\Application\\Test_SB.asm");
			add("..\\..\\..\\..\\asm_examples\\RTPatternLibrary\\Application\\VisualServoing.asm");

			// UNDEF WAS NOT COMPATIBLE WITH CONCRETE DOMAINS
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\journal\\old\\mainForModelChecking.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\journal\\old\\mainForModelChecking.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\journal\\trafficMonitoring.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\main.asm");

			// SPECIAL CHAR WAS USED AND NOT RECOGNIZED BY THE PARSER
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\SystemCUMLProfile_metahooking\\ruleSM.asm");

			// ALLOWED THE IMPORTING OF ASM EXPORTING ONLY A DOMAIN
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\systemc\\simple_bus\\master_blocking.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\simple_bus\\master_non_blocking.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\simple_bus\\memory.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\simple_bus\\request.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\simple_bus\\types.asm");

			// EXPORT ALL WAS NOT MANAGED IN THE CASE OF RULE
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\main.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\validation\\old\\modelsRenamedFunctions\\main.asm");

			// NULL GUARD IN EXISTS TERM WAS NOT DEFINED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\SeqRuleATD.asm");

			// EXISTS TERM AND FORALL TERM
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\ExistTerm01.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\ForallTerm01.asm");

			// USE ALSO FUNCTION DEFINED FOR SUPER-TYPES
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\example_for_nuXmv\\FunctionInt2.asm");

			// LET TERMS WITH EMPTY SEQUENCES
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\term_subst\\termsubst07.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\free_vars\\free09.asm");

			// SEQUENCES IN CASE RULE WERE NOT MANAGER
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\CaseRule02.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\CaseRule01.asm");

			// DUPLICATES WERE NOT ALLOWED IN SEQUENCES
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\terms\\AppendSeqTerm.asm");

			// TURBORETURNRULES WERE NOT MANAGED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\rules\\FLIP_FLOP_3.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\turbo\\turbo01.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\turbo\\fibonacci.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\rules\\FLIP_FLOP_4.asm");

			// BINARY EXPRESSION AND TERM
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\rpns\\r\\CaseRule01_bag.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\GilbreathCardTrick\\old\\GilbreathCardTrickNotWorking.asm");

			// TWO ENUMS WITH THE SAME VALUE FOR AND ENTRY BUT ONE WAS NOT IMPORTED
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\examples\\production_cell\\ert.asm");

			// SET COMPOSED OF ELEMENTS THAT ARE SETS THEMSELVES
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\locationVars\\NumSets2.asm");

			// ERRORS WITH REAL NUMBERS
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\RelationalExpr02.asm");
			add("..\\..\\..\\..\\asm_examples\\SystemCUMLProfile_metahooking\\top.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\clock\\top.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\counter\\top.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\counter2\\top.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\counterAG\\top_user.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\sched\\clock.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\simple_bus\\top.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\old\\HemodialysisRef1.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\old\\HemodialysisRef2.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\hemodialysisDevice\\old\\HemodialysisRef3.asm");
			add("..\\..\\..\\..\\asm_examples\\erinda\\FMS_IO.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\macro\\macro03.asm");
			add("..\\..\\..\\..\\asm_examples\\SystemCUMLProfile_metahooking\\clock.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\inconUpdateWithUndef.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\parser\\concrDomDef.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\terms\\bagTerm.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\terms\\seqTerm.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\terms\\setTerm.asm");

			// COMPATIBILITY BETWEEN DOMAINS WAS NOT DEFINED BETWEEN CONCRETE DOMAINS
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\macro\\macro05.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\macro\\macro06.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\macro\\macro07.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\macro\\macro08.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\macro\\macro04.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\spec_fragments\\functionArity.asm");

			// DOMAIN COMPUTATION FOR SETS WITH ELEMENTS THAT ARE SETS THEMSELVES
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\rpns\\r\\ChooseRule04.asm");

			// UPDATE-RULE INSIDE CHOOSERULE
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\locationVars\\NumSets.asm");

			// OTHER VALIDATION ERRORS
			// ---------- ALL CHECKED
			add("..\\..\\..\\..\\asm_examples\\asmetal2cpp\\asmetal2cpp_codegen\\Bare.asm");
			add("..\\..\\..\\..\\asm_examples\\asmetal2cpp\\asmetal2cpp_codegen\\gameOfLife.asm");
			add("..\\..\\..\\..\\asm_examples\\erinda\\FMS_IO_2.asm");
			add("..\\..\\..\\..\\asm_examples\\rpns\\r\\cluster_test2.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario9.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario6.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario7.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\models\\integer_train.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario1.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\models\\ertms_hl3_integer_train.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\models\\ertms_hl3_integer_disco_lostint_lengthchanged.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\models\\ertms_hl3_integer_disco_lostint_lengthchanged_reco.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\models\\ertms_hl3_integer_disco_lostint_lengthchanged_reco_sweep.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\models\\ertms_hl3_integer_disconnected_train.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\ExistTerm02.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\ForallTerm02.asm");
			add("..\\..\\..\\..\\asm_examples\\rpns\\r\\cluster_movePoints.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario2.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario8.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario5.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\models\\ertms_hl3_integer_disco_lostint_lengthchanged_reco_sweep_timer.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario4.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\RelationalExpr01.asm");
			add("..\\..\\..\\..\\asm_examples\\rpns\\r\\ATM_turbo.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\turbo\\counter.asm");
			add("..\\..\\..\\..\\asm_examples\\test\\simulator\\turbo\\quicksort.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\ermts_etcs\\scenarios\\ertms_hl3_scenario3.asm");
			add("..\\..\\..\\..\\asm_examples\\systemc\\simple_bus\\arbiter.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\models\\OrderSystem2.asm");
			add("..\\..\\..\\..\\asm_examples\\SystemCUMLProfile_metahooking\\ruleSM.asm");
			add("..\\..\\..\\..\\asm_examples\\rpns\\r\\ChooseRule04.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\models\\ordersystem.asm");
			add("..\\..\\..\\..\\asm_examples\\examples\\models\\ordersystem_april2010.asm");
			add("..\\..\\..\\..\\asm_examples\\DAS\\TrafficMonitoringSystem\\SelfHealingSubsystem.asm");

		}
	};

	static ArrayList<String> errors_path = new ArrayList<String> () {{
		// File da saltare perchè sbagliati
		add("..\\..\\..\\asm_examples\\test\\simulator\\ArithmeticExpr02.asm");
		add("..\\..\\..\\asm_examples\\test\\errors\\np\\m1.asm");
		add("..\\..\\..\\asm_examples\\dagstuhl2013\\DijkstraTermination\\old\\terminationFinished20170108.asm");
		add("..\\..\\..\\asm_examples\\dagstuhl2013\\DijkstraTermination\\old\\terminationN20170108.asm");
		add("..\\..\\..\\asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_HC_new_refined_with_arbiter_forMC_old.asm");
		add("..\\..\\..\\asm_examples\\test\\simulator\\macro\\macro06.asm");
		add("..\\..\\..\\asm_examples\\SystemCUMLProfile_metahooking\\SystemCUMLProfile_INIT.asm");
	}};

	private static int counter_to_be_check = 0;
	private static int checked_examples = 0;

	@Test
	public void testFileArrays() throws IOException {
		for (String relativeExamplePath : check) {
			testAsmetaXtFile(relativeExamplePath, false);
		}
	}

	@Test
	public void testFileArrays2() throws IOException {
		
		for (String relativeExamplePath : errors_path) {
			testAsmetaXtFile(relativeExamplePath, false);
		}
	}

	@Test
	public void testCashPoint() throws IOException {
		testAsmetaXtFile("..\\..\\..\\..\\asm_examples\\test\\simulator\\macro\\macro06.asm", false);
	}
	
	@Test
	public void testSingleSpec() throws IOException {
		testAsmetaXtFile("../../../asm_examples/SystemCUMLProfile_metahooking\\SystemCUMLProfile_INIT.asm", false);
	}

	@Test
	public void testPillBox() throws IOException {
		testAsmetaXtFile("../../../../asm_examples/PillBox/Level0/pillbox_0.asm", false);
	}

	
	@Test
	public void testAllExamples() throws IOException {
		Path examplePath = Paths.get("../../../asm_examples");

		Assert.assertTrue(Files.isDirectory(examplePath));
		Iterator<Path> files = Files.walk(examplePath).iterator();
		while (files.hasNext()) {
			Path fileToRead = files.next();
			String specName = fileToRead.toString();

			// ------------------------------------------------------------------------------------
			// FILE CORRECTLY SKIPPED
			// ------------------------------------------------------------------------------------

			// skip the files in the STDL folder, the CTLlibrary and LTLlibrary
			if (specName.contains("STDL"))
				continue;
			if (specName.contains("CTLlibrary.asm"))
				continue;
			if (specName.contains("LTLlibrary.asm"))
				continue;

			// skip old version of examples that do not work with the previous parser too
			if (specName.contains("asm_examples\\asmetal2cpp\\asmetal2cpp_codegen\\SwapSort.asm"))
				continue;
			if (specName.contains("flashProtocol\\old\\flashProtocolWinter.asm"))
				continue;
			if (specName.contains("landingGearSystem\\old\\LandingGearSystem.asm"))
				continue;
			if (specName.contains("landingGearSystem\\old\\LandingGearSystemNoValvesNoCylinders.asm"))
				continue;
			if (specName.contains("unibgstudents\\stufa.asm"))
				continue;
			if (specName.contains("test\\parser\\bagCT.asm"))
				continue;
			if (specName.contains("test\\parser\\setterm.asm"))
				continue;
			if (specName.contains("rpns\\r\\QUICKSORT.asm"))
				continue;
			if (specName.contains("rpns\\r\\philosophers_with_res.asm"))
				continue;
			if (specName.contains("rpns\\r\\MERGE.asm"))
				continue;
			if (specName.contains("rpns\\r\\LocationVarChoose.asm"))
				continue;
			if (specName.contains("rpns\\r\\FLIP_FLOP.asm"))
				continue;
			if (specName.contains("DAS\\TrafficMonitoringSystem\\OrganizationMiddleware_old.asm"))
				continue;
			if (specName.contains("test\\parser\\ntor.asm"))
				continue;
			if (specName.contains("DAS\\TrafficMonitoringSystem\\OrganizationMiddleware_old_old.asm"))
				continue;
			if (specName.contains("DAS\\TrafficMonitoringSystem\\OrganizationMiddleware_old_old_old.asm"))
				continue;
			if (specName.contains("asm_examples\\asmetal2cpp\\asmetal2cpp_hw\\allBindings\\allBindings.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\simulator\\import2\\m1.asm"))
				continue;
			if (specName.contains("asm_examples\\rpns\\r\\fattoriale_ricorsivo.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\simulator\\import2\\m2.asm"))
				continue;
			if (specName.contains(
					"asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\MySmartHomeAQ_HC_refined_forMC.asm"))
				continue;
			if (specName.contains(
					"asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_HC_refined_with_arbiter3.asm"))
				continue;
			if (specName.contains(
					"asm_examples\\workspacePatrizia\\SmartHomeGateway\\asm\\old\\MySmartHomeAQ_HC_new_refined_with_arbiter3.asm"))
				continue;
			if (specName
					.contains("asm_examples\\workspacePatrizia\\Composition_prj\\composition\\ComfortableHeating.asm"))
				continue;
			if (specName.contains("asm_examples\\systemc\\sched\\sched.asm"))
				continue;
			if (specName.contains("asm_examples\\SystemCUMLProfile_metahooking\\sched.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\simulator\\rules\\test_let_rule_in.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\simulator\\rules\\test_let_rule_in1.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\simulator\\rules\\test_let_rule_in2.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\simulator\\rules\\test_let_rule_in3.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\simulator\\terms\\test_let_term_in.asm"))
				continue;
			if (specName.contains("asm_examples\\dagstuhl2013\\DijkstraTermination\\old\\terminationCore.asm"))
				continue;
			if (specName.contains("asm_examples\\dagstuhl2013\\till.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\simulator\\spec_fragments\\static_functions.asm"))
				continue;
			if (specName.contains("asm_examples\\systemc\\simple_bus\\simple_bus.asm"))
				continue;
			if (specName.contains("asm_examples\\erinda\\FMS_IO_3.asm"))
				continue;
			if (specName.contains("asm_examples\\rpns\\r\\cluster.asm"))
				continue;
			if (specName.contains("asm_examples\\rpns\\r\\cluster_java.asm"))
				continue;
			if (specName.contains("asm_examples\\rpns\\r\\cluster_nolocvar.asm"))
				continue;
			if (specName.contains("asm_examples\\rpns\\r\\cluster_nossr.asm"))
				continue;
			if (specName.contains("asm_examples\\rpns\\r\\cluster_nossr1Dint.asm"))
				continue;
			if (specName.contains("asm_examples\\examples\\NeedhamSchroeder\\oldVersion\\NeedhamSchroederWithSpy.asm"))
				continue;
			if (specName.contains("asm_examples\\rpns\\r\\cluster_test.asm"))
				continue;
			if (specName.contains("asm_examples\\DAS\\TrafficMonitoringSystem\\SelfHealingSubsystem_old.asm"))
				continue;
			if (specName.contains("asm_examples\\test\\parser\\itor.asm"))
				continue;
				
			// skip the files that was not verified correctly by the previuos parser and now
			// are considered error
			if (specName.contains("asm_examples\\rpns\\r\\fibonacci_ricorsivo.asm"))
				continue;
			if (specName.contains("asmetal2cpp\\asmetal2cpp_codegen\\AbstractDom.asm"))
				continue;
			if (specName.contains("test\\parser\\GetDomainTest.asm"))
				continue;
			if (specName.contains("test\\parser\\neqAndNot.asm"))
				continue;
			if (specName.contains("test\\parser\\neqAndNot1.asm"))
				continue;
			if (specName.contains("test\\parser\\overloading\\m5.asm"))
				continue;
			if (specName.contains("test\\parser\\overloading\\m6.asm"))
				continue;
			if (specName.contains("rpns\\r\\TupleTerm.asm"))
				continue;
			if (specName.contains("test\\simulator\\CaseTermPair.asm"))
				continue;
			if (specName.contains("test\\simulator\\ChooseInConcrete.asm"))
				continue;
			if (specName.contains("test\\simulator\\ConcreteDomain.asm"))
				continue;
			if (specName.contains("test\\simulator\\MapDomain.asm"))
				continue;
			if (specName.contains("asm_examples\\asmetal2cpp\\asmetal2cpp_codegen\\MapDomain.asm"))
				continue;
			if (specName.contains("asm_examples\\asmetal2cpp\\asmetal2cpp_codegen\\SetDomain.asm"))
				continue;
			if (specName.contains("asm_examples\\examples\\turboRules\\turboReturnRuleWithExtend.asm"))
				continue;

			// skip those in errors
			if (specName.contains("errors"))
				continue;

			// parse and validate them
			if (specName.endsWith(".asm")) {
				if (!testAsmetaXtFile(specName, true)) {
					System.err.println(specName);
					break;
				}
			}
		}

		System.out.println("------ Testing completed");
		System.out.println("------ Checked " + checked_examples + " examples, " + counter_to_be_check + "/"
				+ checked_examples + " to be checked");
	}

	/**
	 * 
	 * @param path
	 * @param excludeFile if true, exclude file from the list
	 *                    <code>AllAsmExamplesTesterWOHelper.errors_path</code>
	 * @return
	 */
	static boolean testAsmetaXtFile(String path, boolean excludeFile) {
		checked_examples++;
		if (excludeFile && errors_path.contains(path)) {
			System.out.println("example " + path + " to be checked");
			counter_to_be_check++;
			return true;
		} else {
			System.out.println("checking " + path);
		}

		try {
			ParseAndValidateResult result = new AsmetaLParserWOHelper().parseAndValidateFile(path, false);
			if (result.getErrors().isEmpty()) {
				System.out.print("parser and validation ok");
				System.out.println();
				return true;
			} else {
				System.out.println("validation errors");
				for (Object o : result.getErrors()) {
					System.out.println(o);
				}
				System.out.println();
				return false;
			}
		} catch (Exception e) {
			System.err.println("problems " + e.getMessage());
			return false;
		}

	}
	
}
