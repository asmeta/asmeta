/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.project;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import atgt.specification.ASMSpecification;
import atgt.specification.location.Constant;
import atgt.specification.location.Function;
import atgt.specification.location.LocationVisitorI;
import atgt.specification.location.LogicalVariable;
import atgt.specification.location.Variable;
import atgt.specification.statement.BasicRule;
import atgt.specification.statement.CaseStatement;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.MacroCallRule;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.statement.RuleDeclarationVisitor;
import atgt.specification.statement.RuleVisitor;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import atgt.specification.type.DummyType;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.IntegerType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.definitions.expression.type.TypeVisitorI;
import tgtlib.specification.Axiom;
import tgtlib.specification.SpecificationAnalyzer;

/**
 * Crea la rappresentazione ad albero della specifica. It uses the data
 * structure DefaultMutableTreeNode provided by Java itself
 * 
 * @author Sax Rinzivillo, Sergio Galati
 */
public class TreeModelSpecificationVisitor implements
		SpecificationAnalyzer<DefaultMutableTreeNode,ASMSpecification>,
		RuleVisitor<DefaultMutableTreeNode>,
		RuleDeclarationVisitor<DefaultMutableTreeNode>,
		LocationVisitorI<DefaultMutableTreeNode>,
		TypeVisitorI<DefaultMutableTreeNode> {

	// Metodi per RuleVisitor

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forIfThenElse(atgt.specification.statement.ConditionalRule)
	 */
	@Override
	public DefaultMutableTreeNode forIfThenElse(ConditionalRule ite) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(ite);
		node.add(new DefaultMutableTreeNode(ite.getGuard()));
		node.add(ite.getThenPart().accept(this));
		node.add(ite.getElsePart().accept(this));

		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forSkip(atgt.specification.statement.Skip)
	 */
	@Override
	public DefaultMutableTreeNode forSkip(Skip s) {
		return new DefaultMutableTreeNode(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forAssignment(atgt.specification.statement.UpdateRule)
	 */
	@Override
	public DefaultMutableTreeNode forAssignment(UpdateRule a) {
		return new DefaultMutableTreeNode(a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forDoStatement(atgt.specification.statement.DoStatement)
	 */
	@Override
	public DefaultMutableTreeNode forDoStatement(DoStatement d) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(d);
		for (Enumeration e = d.allStatements(); e.hasMoreElements();)
			node.add(((BasicRule) e.nextElement()).accept(this));
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleDeclarationVisitor#forRuleDeclaration(atgt.specification.statement.RuleDeclaration)
	 */
	@Override
	public DefaultMutableTreeNode forRuleDeclaration(RuleDeclaration r) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(r);
		// node.add((DefaultMutableTreeNode)(r.getBody()).accept(this));
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forMacroCallRule(atgt.specification.statement.MacroCallRule)
	 */
	@Override
	public DefaultMutableTreeNode forMacroCallRule(MacroCallRule r) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(r
				.getRuleDeclaration());
		return node;
	}

	// Metodi per LocationVisitorI
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.location.LocationVisitorI#forConstant(atgt.specification.location.Constant)
	 */
	@Override
	public DefaultMutableTreeNode forConstant(Constant c) {
		return new DefaultMutableTreeNode(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.location.LocationVisitorI#forVariable(atgt.specification.location.Variable)
	 */
	@Override
	public DefaultMutableTreeNode forVariable(Variable v) {
		return new DefaultMutableTreeNode(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.location.LocationVisitorI#forFunction(atgt.specification.location.Function)
	 */
	@Override
	public DefaultMutableTreeNode forFunction(Function f) {
		return new DefaultMutableTreeNode(f);
	}

	// Metodi per TypeVisitorI
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.type.TypeVisitorI#forBoundType(atgt.specification.type.BoundType)
	 */
	@Override
	public DefaultMutableTreeNode forBoundType(BoundType b) {
		return new DefaultMutableTreeNode(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.type.TypeVisitorI#forEnumType(atgt.specification.type.EnumType)
	 */
	@Override
	public DefaultMutableTreeNode forEnumType(EnumType e) {
		return new DefaultMutableTreeNode(e);
	}

	@Override
	public DefaultMutableTreeNode forBoolType(BoolType e) {
		return new DefaultMutableTreeNode(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.type.TypeVisitorI#forDummyType(atgt.specification.type.DummyType)
	 */
	public DefaultMutableTreeNode forDummyType(DummyType d) {
		return new DefaultMutableTreeNode(d);
	}

	/**
	 * For axiom.
	 * 
	 * @param a
	 *            the a
	 * 
	 * @return the default mutable tree node
	 */
	private DefaultMutableTreeNode forAxiom(Axiom a) {
		return new DefaultMutableTreeNode(a);
	}

	// Metodi per SpecificationVisitor

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.SpecificationAnalyzer#analyze(atgt.specification.ASMSpecification)
	 */
	@Override
	public DefaultMutableTreeNode analyze(ASMSpecification sp) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				"Specification");

		// Crea il sottoalbero per i tipi
		DefaultMutableTreeNode category = new DefaultMutableTreeNode("Types");
		for (Type t : sp.allTypes())
			category.add(t.accept(this));
		node.add(category);

		// crea un sottoalbero per gli assiomi
		category = new DefaultMutableTreeNode("Axioms");
		for (Axiom a : sp.getAxiom())
			category.add(this.forAxiom(a));
		node.add(category);

		// Crea il sottoalbero per le costanti
		category = new DefaultMutableTreeNode("Constants");
		for (Constant c : sp.allConstants())
			category.add(c.accept(this));
		node.add(category);

		// Crea il sottoalbero per le variabili
		category = new DefaultMutableTreeNode("Variables");
		for (Enumeration e = sp.allVariables(); e.hasMoreElements();)
			category.add((((Variable) e.nextElement()).accept(this)));
		node.add(category);

		// Crea il sottoalbero per le funzioni
		category = new DefaultMutableTreeNode("Functions");
		for (Enumeration e = sp.allFunction(); e.hasMoreElements();)
			category.add((((Function) e.nextElement()).accept(this)));
		node.add(category);

		// Crea il sottoalbero per le regole
		category = new DefaultMutableTreeNode("Rules");
		for (RuleDeclaration r: sp.allRules())
			category.add(r.accept(this));
		node.add(category);

		return node;
	}

	@Override
	public DefaultMutableTreeNode forIntegerType(IntegerType intType) {
		throw new RuntimeException("visitor not defined yet");
	}

	@Override
	public DefaultMutableTreeNode forChooseRule(ChooseRule chooseRule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public DefaultMutableTreeNode forCaseStatement(CaseStatement caseStatement) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public DefaultMutableTreeNode forLogicalVariable(LogicalVariable logicalVariable) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
