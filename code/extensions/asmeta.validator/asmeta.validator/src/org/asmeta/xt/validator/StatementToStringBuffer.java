/*
 *
 */
package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.inject.Injector;

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

	// the set that must be set in the init state (initial set and pick of the
	// scencario)
	ArrayList<Command> monitoredInitState;
	List<ArrayList<Command>> allMonitored;
	ArrayList<Pick> allPickRules;
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
		ArrayList<Command> monitored = new ArrayList<>();
		// list of monitored set
		allMonitored = new ArrayList<>();
		for (Command command : commandsNewOrder) {
			if (command instanceof Set || command instanceof Pick) {
				monitored.add(command);
			} else if (command instanceof Step || command instanceof StepUntil) {
				allMonitored.add(monitored);
				monitored = new ArrayList<>();
			}
		}
		// potresti avere le monitorate dopo lo step
		allMonitored.add(monitored);
		state = 0;
		monitoredInitState = allMonitored.get(state++);
		allPickRules = new ArrayList<>();
		for (ArrayList<Command> list : allMonitored) {
			allPickRules.addAll(extractPickRules(list));
		}
		// add all command in the new order
		for (Command command : commandsNewOrder) {
			if (command instanceof Set || command instanceof Pick)
				continue;
			doSwitch(command);
		}
		enclose();
	}

	/**
	 * Extracts all objects that are instances of {@code Pick} from a given list of
	 * {@code Command}.
	 * 
	 * @param list the list of {@code Command} objects to filter
	 * @return a list containing only the objects that are instances of {@code Pick}
	 */
	private List<Pick> extractPickRules(List<Command> list) {
		return list.stream().filter(x -> x instanceof Pick).map(x -> ((Pick) x)).collect(Collectors.toList());
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
		printMonitored();// PA: 2017/12/29
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
		append(oldMainName + "[]");
		printMonitored();// PA: 2017/12/29
		enclose();
		return null;
	}

	private void printMonitored() {
		if (state < allMonitored.size()) {
			ArrayList<Command> monsState = allMonitored.get(state);
			for (Command set : monsState) {
				doSwitch(set);
			}
			// Set all monitored is_picked_X functions to false for all variables that have
			// not been picked in the avalla. Skip last step as setting the value would be
			// useless
			if (state != allMonitored.size() - 1) {
				List<Pick> usedPickRules = extractPickRules(monsState);
				// Using Set instead of List to avoid duplicates
				java.util.Set<String> notUsedPickRules = new HashSet<>();
				for (Pick p1 : allPickRules) {
					if (!usedPickRules.stream().anyMatch(p2 -> pickSameVariable(p1, p2))) {
						String ruleName = p1.getRule();
						if (ruleName == null)
							ruleName = getRuleName(p1.getVar());
						notUsedPickRules.add(AsmetaPrinterForAvalla.IS_PICKED + p1.getVar().substring(1) + "_" + ruleName);
					}
				}
				for (String monitored : notUsedPickRules)
					append(monitored + " := false");
			}
		}
		state++;
	}

	/**
	 * Given two pick rules, check whether they are picking the same variable in the
	 * same rule declaration or not
	 * 
	 * @param p1 the first pick rule
	 * @param p2 the second pick rule
	 * @return true if the two pick rules pick the same variable, false otherwise
	 */
	private boolean pickSameVariable(Pick p1, Pick p2) {
		String p1RuleName = p1.getRule() == null ? getRuleName(p1.getVar()) : p1.getRule();
		String p2RuleName = p2.getRule() == null ? getRuleName(p2.getVar()) : p2.getRule();
		return Objects.equals(p1.getVar(), p2.getVar()) && Objects.equals(p1RuleName, p2RuleName);
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

	@Override
	public Void casePick(Pick pickCmd) {
		String variable = pickCmd.getVar().trim();
		String value = pickCmd.getValue().trim();
		String rule = pickCmd.getRule();
		if (rule == null)
			rule = getRuleName(variable);
		assert rule != null;
		rule = rule.trim();
		String variableWithRule = variable.substring(1) + "_" + rule;
		String is_variable = AsmetaPrinterForAvalla.IS_PICKED + variableWithRule;
		String val_variable = AsmetaPrinterForAvalla.VAL_PICKED + variableWithRule;
		append(is_variable + " := true");
		append(val_variable + " := " + value);
		return null;
	}

	/**
	 * Given the name of a variable (starting with $) of a Pick rule, return the
	 * correspondent rule declaration containing the choose rule that defines a
	 * variable with the same name
	 * 
	 * @param variable the name of the variable
	 * @return the name of the rule declaration
	 */
	private String getRuleName(String variable) {
		for (Entry<ChooseRule, String> chooseRule : this.builder.allChooseRules.entrySet()) {
			if (chooseRule.getKey().getVariable().stream().anyMatch(var -> var.getName().equals(variable)))
				return chooseRule.getValue();
		}
		// should never happen
		return null;
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