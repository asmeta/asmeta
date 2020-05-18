package org.asmeta.xt.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.avallaxt.avallaXt.Command;
import org.asmeta.avallaxt.avallaXt.Element;
import org.asmeta.avallaxt.avallaXt.ExecBlock;
import org.asmeta.avallaxt.avallaXt.Scenario;



public class Flattener {
	private List<Command> commands;
	private Scenario scenario;
	private String scenarioDir;

	public Flattener(Scenario scenario, String scenarioDir) {
		this.scenario = scenario;
		this.scenarioDir = scenarioDir;
	}

	public List<Command> getCommands() {
		if (commands == null) {
			parseCommands();
		}
		return commands;
	}

	private void parseCommands() {
		commands = new ArrayList<Command>();
		for (Element command : scenario.getElements()) {
			assert command != null;
			commands.addAll(getCommands(command));
		}
	}

	private List<Command> getCommands(Element command) {
		if (command instanceof ExecBlock) {
			return getExecBlock((ExecBlock) command);
		} else if (command instanceof Command){
			return Collections.singletonList((Command)command);
		} return Collections.EMPTY_LIST;
	}

	private List<Command> getExecBlock(ExecBlock eb) {
/*		String[] blockName = eb.getBlock().trim().split("\\.");
		assert blockName.length == 2 : eb.getBlock().toString();
		String scenarioName = blockName[0];
		String block = blockName[1];
		FileReader fr;
		try {
			fr = new FileReader(scenarioDir + File.separator + scenarioName + ".test");
			Scenario s = new ScriptParser(fr).read();
			// get the block
			Block b = getBlockByName(s, block);
			Flattener flattener = new Flattener(s, scenarioDir);
			List<Command> cmds = new ArrayList<Command>();
			for (Command command : b.getCommands()) {
				cmds.addAll(flattener.getCommands(command));
			}
			return cmds;

		} catch (FileNotFoundException e) {
			System.err.println(scenarioName + ".test" + " not found");
			e.printStackTrace();
			throw new Error();
		} catch (ParseException e) {
			System.err.println("parse exception " + e.getLocalizedMessage());
			e.printStackTrace();
			throw new Error();
		}*/
		throw new RuntimeException("not implemented yet");
	}

/*	private Block getBlockByName(Scenario s, String block) {
		for (Block b : s.getBlocks()) {
			if (b.getName().equals(block))
				return b;
		}
		throw new RuntimeException("block " + block + " not found");
	}*/
}
