/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.value;

import java.util.ArrayList;
import java.util.Collection;

import org.asmeta.parser.Utility;
import org.asmeta.parser.util.Defs;

import asmeta.definitions.domains.Domain;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * An agent value.
 * 
 */
public class AgentValue extends ReserveValue {
	
	/**
	 * Domain Agent.
	 * 
	 */
	private static Domain agentDomain;
	
	
	/**
	 * Agents' initializations.
	 * 
	 */
	private static Collection<?> initList = new ArrayList<AgentInitialization>();
	
	/**
	 * Agent's program.
	 * 
	 */
	private Rule program;


	/**
	 * Agent's domain.
	 * 
	 */
	private Domain domain;
	
	/**
	 * Extends the given agent domain.
	 * 
	 * @param domainToExtend agent domain
	 * @return a new agent
	 */
	protected static AgentValue extendDomain(Domain domainToExtend) {
		// finds the agent's rule
		for (Object o : initList) {
			AgentInitialization agentInit = (AgentInitialization) o;
			Domain domain = agentInit.getDomain();
			Rule program = agentInit.getProgram();
			if (Defs.equals(domain, domainToExtend)) {
				//String name = mangleName(domainToExtend.getName());
				//modifica PA 4/5/2010
				String name = mangleName(domainToExtend);
				return new AgentValue(name, domainToExtend, program);
			}
		}
		throw new RuntimeException(
				"No initialization found for agent " + domainToExtend.getName());
	}
	
	/**
	 * Gets the domain Agent.
	 * 
	 * @return domain Agent
	 */
	public static Domain getAgentDomain() {
		return agentDomain;
	}
	
	/**
	 * Used to initialize the static attributes of this class. It should
	 * be called by the simulator.
	 * 
	 * @param asmModel the ASM model to simulate
	 */
	public static void setAsm(Asm asmModel) {
		agentDomain = Utility.getPredefinedDomain(null, "Agent");
		if (agentDomain == null) {
			throw new RuntimeException("Agent domain declaration not found");
		}
		// sets initializations
		if (asmModel.getDefaultInitialState() != null 
			&& asmModel.getDefaultInitialState().getAgentInitialization() != null) {
			initList = asmModel.getDefaultInitialState().getAgentInitialization();	
		}
					
	}
	
	/**
	 * Creates an agent. It should be used only during the
	 * initialization of the simulator, not to create new values.
	 * For this purpose, use method <i>extendDomain()</i>.
	 * 
	 * @param idAgent agent's name
	 * @param domain agent's domain
	 * @param program agent's program
	 */
	public AgentValue(String name, Domain domain, Rule program) {		
		super(name);
		this.domain = domain;
		this.program = program;
	}
	
	/**
	 * Gets the agent's name.
	 * 
	 * @return agent's name
	 */
	public String getId() {		
		return name;
	}
	
	/**
	 * Gets the agent's domain.
	 * 
	 * @return agent's domain
	 */
	public Domain getDomain() {
		return domain;
	}
	
	/**
	 * Gets the agent's program.
	 * 
	 * @return agent's program
	 */
	public Rule getProgram() {
		return program;
	}
	
	/**
	 * Sets the agent's program.
	 * 
	 * @param program agent's program
	 */
	public void setProgram(Rule program) {
		this.program = program;
	}
	
}
