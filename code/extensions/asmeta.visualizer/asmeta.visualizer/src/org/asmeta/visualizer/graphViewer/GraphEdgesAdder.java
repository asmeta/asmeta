/*******************************************************************************
 * Copyright (c) 2014, 2015 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API & implementation
 *
 *******************************************************************************/
package org.asmeta.visualizer.graphViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.asmeta.parser.util.TermPrinter;
import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

/**
 * visits a rule adds to the graph the nodes and returns the graphnode to be
 * connected to
 */
public class GraphEdgesAdder extends RuleVisitor<Node> {
	static TermPrinter tp = new AdvancedTermPrinter(false);
	// static TermDecod tp = new TermDecod(false);
	public static boolean detectSemanticPatterns = false;

	static public class Edge {
		private Node src;
		private Node dest;
		private String label;

		public Edge(Node src, String label, Node dest) {
			this.src = src;
			this.dest = dest;
			this.label = label;
		}

		public Object getSource() {
			return src;
		}

		public Object getDestination() {
			return dest;
		}

		public String getLabel() {
			return label;
		}

	}

	// graph edges
	List<Edge> edges;

	public GraphEdgesAdder() {
		this.edges = new ArrayList<>();
	}

	@Override
	public Node visit(BlockRule block) {
		// check state pattern
		if (detectSemanticPatterns) {
			List<Edge> statePattern = checkStatePattern(block.getRules());
			if (statePattern != null) {
				edges.addAll(statePattern);
				return (Node) statePattern.get(0).getSource();
			}
			List<Edge> mutExlcPattern = checkMutuallyExclusivePattern(block.getRules());
			if (mutExlcPattern != null) {
				edges.addAll(mutExlcPattern);
				return (Node) mutExlcPattern.get(0).getSource();
			}
		}
		LabeledPointNode startingNode = new LabeledPointNode("par");
		// LabeledNode startingNode = new LabeledNode("par");
		for (Rule r : block.getRules()) {
			// add the nodes
			Node node_i = visit(r);
			edges.add(new Edge(startingNode, "", node_i));
		}
		return startingNode;
	}

	private List<Edge> checkStatePattern(List<Rule> rules) {
		//
		Term updatedTerm = null;
		// guarded value to rule that is guarded by that value
		Map<String, Rule> guardedRule = new HashMap<String, Rule>();
		Map<String, StateNode> stateNodes = new HashMap<String, StateNode>();
		//
		for (Rule r : rules) {
			// check conditional rule
			// of type if x = y then
			if (!(r instanceof ConditionalRule)) {
				return null;
			}
			ConditionalRule cr = (ConditionalRule) r;
			if (!(cr.getGuard() instanceof FunctionTerm)) {
				return null;
			}
			FunctionTerm ft = (FunctionTerm) cr.getGuard();
			if (!(ft.getFunction().getName().equals("eq"))) {
				return null;
			}
			// if with eq found;
			// get the term changed:
			Term ti = ft.getArguments().getTerms().get(0);
			// if not Locationterm
			if (!(ti instanceof LocationTerm)) {
				return null;
			}
			if (updatedTerm == null) {
				updatedTerm = ti;
			} else if (!tp.visit(ti).equals(tp.visit(updatedTerm))) {
				return null;
			}
			// = what???
			Term lt = ft.getArguments().getTerms().get(1);
			// get only the then part TODO add else
			guardedRule.put(tp.visit(lt), cr.getThenRule());
			stateNodes.put(tp.visit(lt), new StateNode(tp.visit(updatedTerm), tp.visit(lt)));
		}
		System.out.println("par if with eq found and updating all the same term " + tp.visit(updatedTerm));
		System.out.println("rules guarded by " + guardedRule);
		List<Edge> edges = new ArrayList<Edge>();
		CanChangeTo cct = new CanChangeTo(updatedTerm);
		Set<String> startStates = new HashSet<String>();
		Set<String> endStates = new HashSet<String>();
		Map<Rule, Node> ruleNodes = new HashMap<Rule, Node>();
		Map<Node, Set<Node>> ruleNodeIncomingEdges = new HashMap<Node, Set<Node>>();

		for (Entry<String, Rule> r : guardedRule.entrySet()) {
			startStates.add(r.getKey());
			startStates.removeAll(endStates);
			Set<String> toNodes = cct.visit(r.getValue());
			if (!toNodes.isEmpty()) {
				for (String toNode : toNodes) {
					System.out.println(r.getKey() + " ---> " + toNode);
					endStates.add(toNode);
					startStates.remove(toNode);

					// this solution duplicates edges
					/*
					 * Node ruleNode = this.visit(r.getValue()); edges.add(new
					 * Edge(stateNodes.get(r.getKey()), "", ruleNode));
					 */
					// this solution does not duplicate edges
					Node ruleNode = null;
					if (ruleNodes.containsKey(r.getValue())) {
						ruleNode = ruleNodes.get(r.getValue());
					} else {
						ruleNode = this.visit(r.getValue());
						ruleNodes.put(r.getValue(), ruleNode);
						ruleNodeIncomingEdges.put(ruleNode, new HashSet<Node>());
					}
					StateNode startNode = stateNodes.get(r.getKey());
					if (!ruleNodeIncomingEdges.get(ruleNode).contains(startNode)) {
						edges.add(new Edge(startNode, "", ruleNode));
						ruleNodeIncomingEdges.get(ruleNode).add(startNode);
					}
					if (toNode != null) {
						edges.add(new Edge(ruleNode, "", stateNodes.get(toNode)));
					}
				}
			} else {
				System.out.println("end: " + r.getKey());
				Node ruleNode = this.visit(r.getValue());
				edges.add(new Edge(stateNodes.get(r.getKey()), "", ruleNode));
			}
		}
		System.out.println(startStates);
		/*
		 * if(startStates.size() == 1) { return edges; } return null;
		 */
		return edges;
	}

	private List<Edge> checkMutuallyExclusivePattern(List<Rule> rules) {
		// guarded value to rule that is guarded by that value
		Map<String, Rule> guardedRule = new HashMap<String, Rule>();
		Term updatedTerm = null;
		for (Rule r : rules) {
			// check conditional rule
			// of type if x = y then
			if (!(r instanceof ConditionalRule)) {
				return null;
			}
			ConditionalRule cr = (ConditionalRule) r;
			if (!(cr.getGuard() instanceof FunctionTerm)) {
				return null;
			}
			FunctionTerm ft = (FunctionTerm) cr.getGuard();
			if (!(ft.getFunction().getName().equals("eq"))) {
				return null;
			}
			// if with eq found;
			// get the term changed:
			Term ti = ft.getArguments().getTerms().get(0);
			// if not Locationterm
			if (!(ti instanceof LocationTerm)) {
				return null;
			}
			if (updatedTerm == null) {
				updatedTerm = ti;
			} else if (!tp.visit(ti).equals(tp.visit(updatedTerm))) {
				return null;
			}
			// = what???
			Term lt = ft.getArguments().getTerms().get(1);
			guardedRule.put(tp.visit(lt), cr.getThenRule());
		}
		List<Edge> edges = new ArrayList<Edge>();
		ConditionalNode condNode = new ConditionalNode(tp.visit(updatedTerm));
		for (Entry<String, Rule> r : guardedRule.entrySet()) {
			Node ruleNode = this.visit(r.getValue());
			edges.add(new Edge(condNode, r.getKey(), ruleNode));
		}
		// System.out.println(edges);
		return edges;
	}

	@Override
	public Node visit(CaseRule rule) {
		CaseRule cr = rule;
		Term caseterm = rule.getTerm();
		CaseNode casenode = new CaseNode(tp.visit(caseterm));
		EList<Rule> node = cr.getCaseBranches();
		for (int i = 0; i < cr.getCaseBranches().size(); i++) {
			Node caseN = this.visit(node.get(i));
			edges.add(new Edge(casenode, tp.visit(cr.getCaseTerm().get(i)), caseN));
		}
		if (cr.getOtherwiseBranch() != null) {
			Node caseN = this.visit(cr.getOtherwiseBranch());
			edges.add(new Edge(casenode, "", caseN));
		}
		return casenode;
	}

	@Override
	public Node visit(ConditionalRule rule) {
		// directed connections
		ConditionalRule cd = rule;
		// condition
		Term guard = cd.getGuard();
		// ConditionalNode condition = new
		// ConditionalNode(checkContent(tp.visit(guard)));
		ConditionalNode condition = new ConditionalNode(tp.visit(guard));
		// then
		Node thenN = this.visit(cd.getThenRule());
		if (cd.getElseRule() != null) {
			edges.add(new Edge(condition, "true", thenN));
			Node elseN = this.visit(cd.getElseRule());
			edges.add(new Edge(condition, "false", elseN));
		} else {
			edges.add(new Edge(condition, "", thenN));
		}
		return condition;
	}

	/*
	 * private String checkContent(String visit) { String s="";
	 * System.out.println(visit); if (visit.startsWith("eq")){
	 * s+=visit.substring(visit.indexOf("(")+1, visit.indexOf(","))+ " = " +
	 * visit.substring(visit.indexOf(",")+1, visit.indexOf(")")); return s; } else
	 * return visit; }
	 */

	@Override
	public Node visit(ExtendRule rule) {
		throw new RuntimeException(rule.getClass() + " not implemented");
	}

	private Node visitForallChoose(String ruleName, Term guard, Rule doRule, List<VariableTerm> vars,
			List<Term> ranges) {
		LabeledPointNode startingNode = new LabeledPointNode(ruleName);
		ConditionalNode condition = new ConditionalNode(tp.visit(guard));
		Node nodeDoRule = this.visit(doRule);
		StringBuilder sb = new StringBuilder();
		sb.append(tp.visit(vars.get(0)) + " in " + tp.visit(ranges.get(0)));
		for (int i = 1; i < ranges.size(); i++) {
			sb.append(", " + tp.visit(vars.get(i)) + " in " + tp.visit(ranges.get(i)));
		}
		edges.add(new Edge(startingNode, sb.toString(), condition));
		edges.add(new Edge(condition, "", nodeDoRule));
		return startingNode;
	}

	@Override
	public Node visit(ForallRule forallRule) {
		return visitForallChoose("forall", forallRule.getGuard(), forallRule.getDoRule(), forallRule.getVariable(),
				forallRule.getRanges());
	}

	@Override
	public Node visit(ChooseRule chooseRule) {
		Node startingChooseNode = visitForallChoose("choose", chooseRule.getGuard(), chooseRule.getDoRule(),
				chooseRule.getVariable(), chooseRule.getRanges());
		Rule ifnone = chooseRule.getIfnone();
		if (ifnone != null) {
			Node nodeIfnone = this.visit(ifnone);
			edges.add(new Edge(startingChooseNode, "ifnone", nodeIfnone));
		}
		return startingChooseNode;
	}

	@Override
	public Node visit(LetRule letRule) {
		LabeledPointNode letNode = new LabeledPointNode("let");
		StringBuilder sb = new StringBuilder();
		List<VariableTerm> vars = letRule.getVariable();
		List<Term> inits = letRule.getInitExpression();
		sb.append(tp.visit(vars.get(0)) + " = " + tp.visit(inits.get(0)));
		for (int i = 1; i < inits.size(); i++) {
			sb.append(", " + tp.visit(vars.get(i)) + " = " + tp.visit(inits.get(i)));
		}
		edges.add(new Edge(letNode, sb.toString(), visit(letRule.getInRule())));
		return letNode;
	}

	@Override
	public Node visit(MacroCallRule rule) throws Exception {
		return new MacroCallNode(rule.getCalledMacro());
	}

	@Override
	public Node visit(SeqRule rule) {
		throw new RuntimeException(" not implemented");
		/*
		 * List<Edge> edges = new ArrayList<Edge>(); List<org.eclipse.gef4.graph.Node>
		 * nodes = new ArrayList<org.eclipse.gef4.graph.Node>(); HashMap<String, Object>
		 * attrs = new HashMap<String, Object>(); EList<Rule> rules = rule.getRules();
		 * Node oldNode = nodebuilder.visit(rules.get(0)); nodes.add(oldNode); for (int
		 * i = 1; i < rules.size(); i++) { Node newNode =
		 * nodebuilder.visit(rules.get(i)); nodes.add(newNode);
		 * edges.add(Node4RuleBuilder.eNoLabel(oldNode, newNode)); oldNode = newNode; }
		 * Graph graph = new Graph(attrs, nodes, edges); BoxLayoutAlgorithm layout = new
		 * BoxLayoutAlgorithm(2); layout.setResizing(false);
		 * ZestProperties.setType(graph, ZestProperties.GRAPH_TYPE_DIRECTED);
		 * ZestProperties.setLayout(graph, new BoxLayoutAlgorithm(2)); return graph;
		 */
	}

	@Override
	public Node visit(SkipRule rule) {
		return new LabeledNode("skip");
	}

	@Override
	public Node visit(TermAsRule rule) {
		throw new RuntimeException(rule.getClass() + " not implemented");
	}

	@Override
	public Node visit(UpdateRule rule) {
		UpdateRule up = rule;
		String loc = tp.visit(up.getLocation());
		String upt = tp.visit(up.getUpdatingTerm());
		Node updateNode = new LabeledNode(loc + ":=" + upt);
		return updateNode;
	}
}