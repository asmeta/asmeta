/*
 * 
 */
package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avallaXt.Block;
import org.asmeta.avallaxt.avallaXt.Check;
import org.asmeta.avallaxt.avallaXt.Command;
import org.asmeta.avallaxt.avallaXt.Element;
import org.asmeta.avallaxt.avallaXt.Exec;
import org.asmeta.avallaxt.avallaXt.ExecBlock;
import org.asmeta.avallaxt.avallaXt.Scenario;
import org.asmeta.avallaxt.avallaXt.Set;
import org.asmeta.avallaxt.avallaXt.Step;
import org.asmeta.avallaxt.avallaXt.StepUntil;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.inject.Injector;

/**
 * The Class StatementToStringBuffer transform a scenario to a list of ASM
 * instructions
 */
public class StatementToStringBuffer extends org.asmeta.avallaxt.avallaXt.util.AvallaXtSwitch<Void> {

	private static final String SCENARIO_EXT = ".avalla";

	private static final String STEP_VAR = "step__";

	/** The count. */
	int count = 1;

	/**
	 * The statements. every statement is the sequence of rules for every step
	 */
	List<String> statements = new ArrayList<String>();

	/** The buffer contains all the rules in the. */
	StringBuffer buff = new StringBuffer();

	/** The old main name. */
	private String oldMainName;

	/** The script. */
	private Scenario scenario;

	/** The indentation. TODO: it does not work yet */
	private int indentation = 5;

	private String scenarioDir;

	/**
	 * The Constructor.
	 * 
	 * @param scenario
	 *            the scenario
	 * @param oMN
	 *            the oold main rule name
	 * @param scenarioDir
	 *            TODO
	 */
	public StatementToStringBuffer(Scenario scenario, String oMN, String scenarioDir) {
		this.scenario = scenario;
		this.oldMainName = oMN;
		this.scenarioDir = scenarioDir;
	}

	// the set that must be set in the init state (initial set of the scencario)
	ArrayList<Set> monitoredInitState;
	List<ArrayList<Set>> allMonitored;
	int state;

	/**
	 * Parses the commands and builds the list of statements containing only
	 * simple commands (remove blocks and exec blocks)
	 */
	void parseCommands() {
		// init PA: 2017/12/29
		ArrayList<Command> commandsNewOrder = new ArrayList<Command>();
		// build the list of command by expanding blocks
		addCommands(commandsNewOrder, scenario.getElements());
		// no command is now a block or exec block or 
		assert ! commandsNewOrder.stream().anyMatch(t -> t instanceof ExecBlock);
		assert ! commandsNewOrder.stream().anyMatch(t -> t instanceof Block);
		// split monitored from the others
		ArrayList<Set> monitored = new ArrayList<Set>();
		// list of monitored set
		allMonitored = new ArrayList<ArrayList<Set>>();
		for (Command command : commandsNewOrder) {
			if (command instanceof Set) {
				monitored.add((Set) command);
			} else if (command instanceof Step || command instanceof StepUntil) {
				allMonitored.add(monitored);
				monitored = new ArrayList<Set>();
			}
		}
		// potresti avere le monitorate dopo lo step
		allMonitored.add(monitored);
		state = 0;
		monitoredInitState = allMonitored.get(state++);
		// add all command in the new order
		for (Command command : commandsNewOrder) {
			if (command instanceof Set)
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
	 * @param untilCmd
	 *            the until cmd
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
	 * @param checkCmd
	 *            the check cmd
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
	 * @param s
	 *            the s
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
	 * @param stepCmd
	 *            the step cmd
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
			ArrayList<Set> monsState = allMonitored.get(state);
			for (Set set : monsState) {
				doSwitch(set);
			}
		}
		state++;
	}

	/**
	 * Append.
	 * 
	 * @param setCmd
	 *            the set cmd
	 */
	@Override
	public Void caseSet(Set setCmd) {
		//26/04/2021 -> Silvia: if simulation time is set to auto increment or use java time and the user has set the time function in the scenario, do not add its assignment in the .asm model
		if ((Environment.timeMngt == TimeMngt.auto_increment || Environment.timeMngt == TimeMngt.use_java_time) && Environment.monTimeFunctions.containsKey(setCmd.getLocation().trim()))
			return null;
		String loc = setCmd.getLocation().trim();
		String value = setCmd.getValue().trim();
		append(loc + " := " + value);
		return null;
	}

	/**
	 * Append.
	 * 
	 * @param execCmd
	 *            the exec cmd
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
	 * @param s
	 *            the s
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