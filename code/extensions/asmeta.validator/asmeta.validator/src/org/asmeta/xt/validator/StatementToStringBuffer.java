/*
 *
 */
package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avalla.Block;
import org.asmeta.avallaxt.avalla.Check;
import org.asmeta.avallaxt.avalla.Pick;
import org.asmeta.avallaxt.avalla.Command;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Exec;
import org.asmeta.avallaxt.avalla.ExecBlock;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.avallaxt.avalla.Step;
import org.asmeta.avallaxt.avalla.StepUntil;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.RuleSubstitution;
import org.asmeta.simulator.TermAssignment;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.inject.Injector;

import asmeta.terms.basicterms.BasictermsFactory;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;

/**
 * The Class StatementToStringBuffer transform a scenario to a list of ASM
 * instructions
 */
public class StatementToStringBuffer extends org.asmeta.avallaxt.avalla.util.AvallaSwitch<Void> {

	private static final String SCENARIO_EXT = ".avalla";

	static final String STEP_VAR = "step__";

	/** The count. */
	int count = 1;

	/**
	 * The statements. every statement is the sequence of rules for every step
	 */
	List<String> statements = new ArrayList<>();

	/** The buffer contains all the rules in the. */
	StringBuffer buff = new StringBuffer();

	/** The old main name. */
	private String oldMainName;

	/** The script. */
	private Scenario scenario;

	/** The indentation. TODO: it does not work yet */
	private int indentation = 5;

	private String scenarioDir;

	/** The builder */
	private AsmetaFromAvallaBuilder builder;

	/**
	 * The Constructor.
	 *
	 * @param scenario    the scenario
	 * @param oMN         the oold main rule name
	 * @param scenarioDir
	 * @param builder     the builder TODO
	 */
	public StatementToStringBuffer(Scenario scenario, String oMN, String scenarioDir, AsmetaFromAvallaBuilder builder) {
		this.scenario = scenario;
		this.oldMainName = oMN;
		this.scenarioDir = scenarioDir;
		this.builder = builder;
	}

	// the set that must be set in the init state (initial set of the scencario)
	ArrayList<Command> monitoredInitState;
	// List of lists: One list for each step, each list contains the set statements
	// for that step
	List<ArrayList<Command>> allMonitoredFromSet;
	// List of lists: One list for each step, each list contains the pick statements
	// for that step
	List<ArrayList<Pick>> pickStatements;
	List<Pick> allPickStatements;
	int state;

	/**
	 * Parses the commands and builds the list of statements containing only simple
	 * commands (remove blocks and exec blocks)
	 */
	void parseCommands() {
		// init PA: 2017/12/29
		ArrayList<Command> commandsNewOrder = new ArrayList<>();
		// build the list of command by expanding blocks
		addCommands(commandsNewOrder, scenario.getElements());
		// no command is now a block or exec block or
		assert !commandsNewOrder.stream().anyMatch(t -> t instanceof ExecBlock);
		assert !commandsNewOrder.stream().anyMatch(t -> t instanceof Block);
		// split monitored from the others
		ArrayList<Command> sets = new ArrayList<>();
		ArrayList<Pick> picks = new ArrayList<>();
		// list of monitored set
		allMonitoredFromSet = new ArrayList<>();
		pickStatements = new ArrayList<>();
		allPickStatements = new ArrayList<>();
		for (Command command : commandsNewOrder) {
			if (command instanceof Set) {
				sets.add(command);
			} else if (command instanceof Pick) {
				Pick pick = (Pick) command;
				picks.add(pick);
				allPickStatements.add(pick);
			} else if (command instanceof Step || command instanceof StepUntil) {
				allMonitoredFromSet.add(sets);
				pickStatements.add(picks);
				sets = new ArrayList<>();
				picks = new ArrayList<>();
			}
		}
		// there can be sets and picks after last step
		allMonitoredFromSet.add(sets);
		pickStatements.add(picks);
		state = 0;
		monitoredInitState = allMonitoredFromSet.get(state++);
		// add all command in the new order
		for (Command command : commandsNewOrder) {
			if (command instanceof Set || command instanceof Pick)
				continue;
			doSwitch(command);
		}
		enclose();
	}

	// put the commands in a new order
	// remove blocks and execblocks
	private void addCommands(ArrayList<Command> commandsNewOrder, EList<Element> elements) {
		for (Element ele : elements) {
			if (ele instanceof Block) {
				// expand the elements in the block
				addCommands(commandsNewOrder, ((Block) ele).getElements());
			} else if (ele instanceof ExecBlock) {
				ExecBlock eb = (ExecBlock) ele;
				// expand the elements in the block exec block refers to
				// if scenario is null, search for the block inside this scenario
				if (eb.getScenario() == null) {
					Block b = getBlockByName(scenario, eb.getBlock());
					addCommands(commandsNewOrder, b.getElements());
				} else {
					// read the scenario
					String scenarioPath = scenarioDir + File.separator + eb.getScenario() + SCENARIO_EXT;
					try {
						assert Files.exists(Paths.get(scenarioPath));
						final Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
						final ResourceSet rs = injector.<ResourceSet>getInstance(ResourceSet.class);
						final Resource resource = rs.getResource(URI.createFileURI(scenarioPath), true);
						resource.load(null);
						Scenario sc = (Scenario) resource.getContents().get(0);
						Block b = getBlockByName(sc, eb.getBlock());
						addCommands(commandsNewOrder, b.getElements());
					} catch (FileNotFoundException e) {
						System.err.println(scenarioPath + " not found");
						e.printStackTrace();
						throw new Error();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				assert ele instanceof Command;
				commandsNewOrder.add((Command) ele);
			}
		}
	}

	/**
	 * Append.
	 *
	 * @param untilCmd the until cmd
	 */
	@Override
	public Void caseStepUntil(StepUntil untilCmd) {
		// group the rules before stepuntil
		enclose();
		// add the step
		printRulesFromPicks();
		String cond = untilCmd.getExpression().trim();
		append("if " + cond + " then");
		indent();
		append("step__ := " + count);
		unIndent();
		append("else");
		indent();
		append(oldMainName + "[]");
		unIndent();
		append("endif");
		next();
		printMonitoredFromSet();// PA: 2017/12/29
		return null;
	}

	/**
	 * Append.
	 *
	 * @param checkCmd the check cmd
	 */
	@Override
	public Void caseCheck(Check checkCmd) {
		String cond = checkCmd.getExpression().trim();
		cond = zipWhites(cond);
		append("if " + cond + " then");
		indent();
		append("result := print(\"check succeeded: " + cond.replace("\"", "\\\"") + "\")");
		// append("step__ := " + count);
		unIndent();
		append("else");
		indent();
		append("seq");
		indent();
		append("result := print(\"CHECK FAILED: " + cond.replace("\"", "\\\"") + " at step " + (count - 1) + "\")");
		// -2 so plus 1 is still < 0
		append("step__ := -2");
		unIndent();
		append("endseq");
		unIndent();
		append("endif");
		return null;
	}

	/**
	 * Zip whites.
	 *
	 * @param s the s
	 *
	 * @return the string
	 */
	String zipWhites(String s) {
		StringBuffer s2 = new StringBuffer();
		boolean prevIsWhite = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isWhitespace(c)) {
				if (!prevIsWhite) {
					s2.append(' ');
				}
				prevIsWhite = true;
			} else {
				prevIsWhite = false;
				s2.append(c);
			}
		}
		return s2.toString();
	}

	/**
	 * Append.
	 *
	 * @param stepCmd the step cmd
	 */
	@Override
	public Void caseStep(Step stepCmd) {
		// Set monitored for this step (do not increase state counter)
		printRulesFromPicks();
		append(oldMainName + "[]");
		// Set monitored for the next step (increase state counter)
		printMonitoredFromSet();// PA: 2017/12/29
		enclose();
		return null;
	}

	/**
	 * Append update rules and conditional rules that sets controlled functions
	 * introuduced for handling non-determinism in choose rule
	 */
	private void printRulesFromPicks() {
		AsmetaTermPrinter printer = AsmetaTermPrinter.getAsmetaTermPrinter(false);
		List<Pick> picks = new ArrayList<>();
		if (pickStatements.size() > state - 1)
			picks = pickStatements.get(state - 1);
		// For all vars in all choose rules, add an update rule if it is picked. Add
		// a single choose rules for not picked vars
		for (Entry<ChooseRule, String> mapEntry : this.builder.allChooseRules.entrySet()) {
			ChooseRule chooseRule = mapEntry.getKey();
			String macroRuleName = mapEntry.getValue();
			// First element is the picked variable name (String), second is the value
			// (String), third is the domain (Term)
			List<Object[]> pickedVars = new ArrayList<>();
			List<String> pickedVarsNames = new ArrayList<>();
			// First element is the picked variable name, second is the domain
			List<String[]> notPickedVars = new ArrayList<>();
			List<String> notPickedVarsNames = new ArrayList<>();
			for (int i = 0; i < chooseRule.getVariable().size(); i++) {
				VariableTerm var = chooseRule.getVariable().get(i);
				String pickedValue = getPickFromVariable(var, macroRuleName, picks);
				String domain = printer.visit(chooseRule.getRanges().get(i));
				if (pickedValue == null) {
					notPickedVars.add(new String[] { var.getName(), domain });
					notPickedVarsNames.add(var.getName());
				} else {
					pickedVars.add(new Object[] { var.getName(), pickedValue, chooseRule.getRanges().get(i) });
					pickedVarsNames.add(var.getName());
				}
			}
			// Start with picked variables, a term substitution from variables to controlled
			// functions is needed for guards
			TermAssignment assignment = new TermAssignment();
			List<VariableTerm> variables = new ArrayList<>();
			List<Term> controlledFunctions = new ArrayList<>();
			for (Object[] pickedVar : pickedVars) {
				String variable = (String) pickedVar[0];
				String value = (String) pickedVar[1];
				Term domain = (Term) pickedVar[2];
				String controlledFunction = variable.substring(1) + "_" + macroRuleName
						+ AsmetaPrinterForAvalla.ACTUAL_VALUE;
				VariableTerm term = BasictermsFactory.eINSTANCE.createVariableTerm();
				term.setName(variable);
				variables.add(term);
				term = BasictermsFactory.eINSTANCE.createVariableTerm();
				term.setName(controlledFunction);
				controlledFunctions.add(term);
				// when the range is not a DomainTern, check with a conditional rule whether the
				// picked value is in the term (e.g. {10:20}) used as range or not
				if (!(domain instanceof DomainTerm)) {
					append("if contains(" + printer.visit(domain) + ", " + value + ") then");
					indent();
					append(controlledFunction + " := " + value);
					unIndent();
					append("else");
					indent();
					append("seq");
					indent();
					append(controlledFunction + " := undef");
					append("result := print(\"ERROR: the value picked for " + variable + " in " + macroRuleName
							+ " is not in the domain\")");
					append("step__ := -2"); // -2 so plus 1 is still < 0
					unIndent();
					append("endseq");
					unIndent();
					append("endif");
				} else {
					append(controlledFunction + " := " + value);
				}
			}
			assignment.put(variables, controlledFunctions);
			// Do the term substitution to obtain the new guard
			RuleSubstitution substitution = new RuleSubstitution(assignment, new RuleFactory());
			Term newGuardTerm = substitution.visit(chooseRule.getGuard());
			String newGuardString = printer.visit(newGuardTerm);
			// First case: all variables are picked, no choose is needed, just a check on
			// the guard
			if (notPickedVars.isEmpty()) {
				append("if not(" + newGuardString + ") then");
				indent();
				append("seq");
				indent();
				append("result := print(\"ERROR: the value(s) picked for " + String.join(", ", pickedVarsNames) + " in "
						+ macroRuleName + " make the guard evaluate to false \")");
				append("step__ := -2"); // -2 so plus 1 is still < 0
				unIndent();
				append("endseq");
				unIndent();
				append("endif");
			} else { // Second case: at least one variables is not picked, a choose rule is needed
				List<String> varsWithDomains = new ArrayList<>();
				for (String[] notPickedVar : notPickedVars) {
					String var = notPickedVar[0];
					// Add _stepX to avoid variables with same names in the main rule r_main__
					String newVar = var + "_step" + (state - 1);
					String domain = notPickedVar[1];
					newGuardString = newGuardString.replace(var, newVar);
					varsWithDomains.add(newVar + " in " + domain);
				}
				append("choose " + String.join(", ", varsWithDomains) + " with " + newGuardString + " do");
				indent();
				if (notPickedVars.size() > 1) {
					append("par");
					indent();
				}
				for (String notPickedVar : notPickedVarsNames) {
					String controlledFunction = notPickedVar.substring(1) + "_" + macroRuleName
							+ AsmetaPrinterForAvalla.ACTUAL_VALUE;
					append(controlledFunction + " := " + notPickedVar + "_step" + (state - 1));
				}
				if (notPickedVars.size() > 1) {
					unIndent();
					append("endpar");
				}
				unIndent();
				append("ifnone");
				indent();
				append("seq");
				indent();
				for (String notPickedVar : notPickedVarsNames) {
					String controlledFunction = notPickedVar.substring(1) + "_" + macroRuleName
							+ AsmetaPrinterForAvalla.ACTUAL_VALUE;
					append(controlledFunction + " := undef");
				}
				if (pickedVars.size() != 0)
					append("result := print(\"ERROR: the value(s) picked for " + String.join(", ", pickedVarsNames)
							+ " in " + macroRuleName + " make the guard evaluate to false \")");
				else
					append("result := print(\"ERROR: the guard of a choose rule in " + macroRuleName
							+ " always evaluates to false \")");
				append("step__ := -2"); // -2 so plus 1 is still < 0
				unIndent();
				append("endseq");
				unIndent();
			}
		}
	}

	/**
	 * Given a VariableTerm, the name of the RuleDeclaration in which it is used,
	 * and a list of Pick rules, search and return the value of the last appearance
	 * of a Pick rule in the list that picks a value for that variable term.
	 * 
	 * @param variable            the variable term to search in the list of pick
	 *                            rules
	 * @param ruleDeclarationName the name of the rule declaration in which the
	 *                            variable term is used
	 * @param pickList            the list of picks where to search the variable
	 *                            name
	 * @return the value of the last Pick that picks a value for the variable term,
	 *         null if not present
	 */
	private String getPickFromVariable(VariableTerm variable, String ruleDeclarationName, List<Pick> pickList) {
		// reversed to get the last, so if pickList is a list of pick
		// where the same variable is picked multiple times,
		// only the last pick is considered
		pickList = pickList.subList(0, pickList.size());
		Collections.reverse(pickList);
		// scan the list
		for (Pick pick : pickList)
			if (pick.getVar().equals(variable.getName())
					&& (pick.getRule() == null || pick.getRule().equals(ruleDeclarationName)))
				return pick.getValue();
		return null;
	}

	/**
	 * Append update rules setting values for controlled functions introuduced by
	 * set statments and increment state counter
	 */
	private void printMonitoredFromSet() {
		if (state < allMonitoredFromSet.size()) {
			ArrayList<Command> monsState = allMonitoredFromSet.get(state);
			for (Command set : monsState) {
				doSwitch(set);
			}
		}
		state++;
	}

	/**
	 * Append.
	 *
	 * @param setCmd the set cmd
	 */
	@Override
	public Void caseSet(Set setCmd) {
		// 26/04/2021 -> Silvia: if simulation time is set to auto increment or use java
		// time and the user has set the time function in the scenario, do not add its
		// assignment in the .asm model
		if ((Environment.timeMngt == TimeMngt.auto_increment || Environment.timeMngt == TimeMngt.use_java_time)
				&& Environment.monTimeFunctions.containsKey(setCmd.getLocation().trim()))
			return null;
		String loc = setCmd.getLocation().trim();
		String value = setCmd.getValue().trim();
		append(loc + " := " + value);
		return null;
	}

	/**
	 * Append.
	 *
	 * @param execCmd the exec cmd
	 */
	@Override
	public Void caseExec(Exec execCmd) {
		String rule = execCmd.getRule();
		append(rule);
		return null;
	}

	@Override
	public Void caseBlock(Block b) {
		throw new RuntimeException("should never happen since this is expanded");
	}

	@Override
	public Void caseExecBlock(ExecBlock eb) {
		throw new RuntimeException("should never happen since this is expanded in its block");
	}

	private Block getBlockByName(Scenario s, String block) {
		for (Element b : s.getElements()) {
			if ((b instanceof Block) && ((Block) b).getName().equals(block))
				return (Block) b;
		}
		throw new RuntimeException("block " + block + " not found");
	}

	/**
	 * Append.
	 *
	 * @param s the s
	 */
	void append(String s) {
		for (int i = 0; i < indentation; i++)
			buff.append('\t');
		buff.append(s + "\n");
	}

	// X -> seq X step := count endseq
	// and store as next step
	/**
	 * Enclose.
	 */
	void enclose() {
		if (buff.length() > 0) {
			buff.insert(0, "seq\n");
			// questo non va bene perche' il check potrebbe mettere step a -1
			// append("step__ := " + count);
			append(STEP_VAR + " := " + STEP_VAR + " + 1");
			unIndent();
			append("endseq");
			indent();
			next();
		}
	}

	// inc counter and store the current buffer as step
	/**
	 * Next.
	 */
	void next() {
		count++;
		statements.add(buff.toString());
		buff.delete(0, buff.length());
	}

	// increases the indentation
	/**
	 * Indent.
	 */
	protected void indent() {
		indentation++;
	}

	/**
	 * Unindent.
	 */
	protected void unIndent() {
		indentation--;
	}
}