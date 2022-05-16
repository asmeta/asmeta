package org.asmeta.tocpp.tocunit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.junit.Assert;

import asmeta.AsmCollection;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import atgt.coverage.AsmTestSuite;
import atgt.specification.location.FunctionApplication;
import atgt.specification.location.Location;
import tgtlib.definitions.expression.IdExpression;

public abstract class TestSuiteTranslator {

	String asmName;
	AsmCollection asm;
	String mainRuleName;
	ArrayList<String> abstractFunction = new ArrayList<>();
	private String check_command;

	protected TestSuiteTranslator(AsmCollection asm, String check_command) {
		this.asm = asm;
		this.asmName = asm.getMain().getName();
		mainRuleName = asm.getMain().getMainrule().getName();
		this.check_command = check_command;
	}

	protected abstract CharSequence convertTestSuite(AsmTestSuite testsuite);

	// traduce il singolo stato in C++
	public StringConcatenation printState(final Map<Location, String> state, final boolean firstState) {
		StringConcatenation builder = new StringConcatenation();
		builder.append("// set monitored variables \n");
		Set<Map.Entry<Location, String>> _entrySet = state.entrySet();
		for (final Map.Entry<Location, String> assignment : _entrySet) {
			{
				Location location = assignment.getKey();
				boolean _isMonitored = location.isMonitored();
				if (_isMonitored) {
					String monLoc = null;
					if ((location instanceof FunctionApplication)) {
						final String arg = ((FunctionApplication) location).getArgs().toString();
						Assert.assertTrue((arg + " does not contain ["), arg.contains("["));
						final String name = ((FunctionApplication) location).getName();
						Assert.assertFalse(name.contains("("));
						monLoc = (name + arg);
					} else {
						monLoc = location.toString();
						Assert.assertFalse(monLoc.contains("("));
					}
					String _lowerCase = this.asmName.toLowerCase();
					String _plus = (_lowerCase + ".");
					String _plus_1 = (_plus + monLoc);
					String _plus_2 = (_plus_1 + "=");
					String _value = assignment.getValue();
					String _plus_3 = (_plus_2 + _value);
					String _plus_4 = (_plus_3 + ";\n");
					builder.append(_plus_4);
				}
			}
		}
		if (firstState) {
			builder.append("//Init controlled with monitored term \n");
			String _lowerCase = this.asmName.toLowerCase();
			String _plus = (_lowerCase + ".");
			String _plus_1 = (_plus + "initControlledWithMonitored();\n");
			builder.append(_plus_1);
		}
		builder.append("// check controlled variables \n");
		Set<Map.Entry<Location, String>> _entrySet_1 = state.entrySet();
		for (final Map.Entry<Location, String> assignment_1 : _entrySet_1) {
			{
				Location location = assignment_1.getKey();
				final String ctrLocName = location.getIdExpression().toString();
				final Function1<Function, Boolean> _function = (Function it) -> {
					return Boolean.valueOf(it.getName().equals(ctrLocName));
				};
				final Iterable<Function> functions = IterableExtensions.<Function>filter(
						this.asm.getMain().getHeaderSection().getSignature().getFunction(), _function);
				int _size = IterableExtensions.size(functions);
				boolean _tripleEquals = (_size == 0);
				if (_tripleEquals) {
					InputOutput.<String>println((ctrLocName + " not found"));
				} else {
					final Function f = ((Function[]) Conversions.unwrapArray(functions, Function.class))[0];
					int _size_1 = IterableExtensions.size(functions);
					String _plus_2 = ((ctrLocName + " found (") + Integer.valueOf(_size_1));
					String _plus_3 = (_plus_2 + ") .. ");
					InputOutput.<String>print(_plus_3);
					if ((f instanceof DerivedFunction)) {
						Location.VarKind _varKind = location.getVarKind();
						String _plus_4 = ((("derived function location: " + location) + " ") + _varKind);
						InputOutput.<String>println(_plus_4);
					} else {
						if ((location.isControlled() || (location.getVarKind() == null))) {
							Assert.assertTrue((f instanceof ControlledFunction));
							Location.VarKind _varKind_1 = location.getVarKind();
							String _plus_5 = ((("controlled location: " + location) + " ") + _varKind_1);
							InputOutput.<String>println(_plus_5);
							String ctrVar = (ctrLocName + "[0]");
							if ((location instanceof FunctionApplication)) {
								List<IdExpression> arguments = ((FunctionApplication) location).getArgs();
								int _size_2 = arguments.size();
								boolean _greaterEqualsThan = (_size_2 >= 2);
								if (_greaterEqualsThan) {
									String stringArgs = (arguments + "");
									stringArgs = stringArgs.replace("[", "(").replace("]", ")");
									String _ctrVar = ctrVar;
									ctrVar = (_ctrVar + (("[make_tuple" + stringArgs) + "]"));
								} else {
									String _ctrVar_1 = ctrVar;
									ctrVar = (_ctrVar_1 + arguments);
								}
							}
							String _plus_6 = (check_command + "(" + this.asmName.toLowerCase());
							String _value = assignment_1.getValue();
							builder.append((((((_plus_6 + ".") + ctrVar) + "==") + _value) + ");\n"));
						}
					}
				}
			}
		}
		String replaced = builder.toString();
		for (int i = 0; (i < this.abstractFunction.size()); i++) {
			String _get = this.abstractFunction.get(i);
			String _lowerCase_1 = this.asmName.toLowerCase();
			String _plus_2 = (_lowerCase_1 + ".");
			String _get_1 = this.abstractFunction.get(i);
			String _plus_3 = (_plus_2 + _get_1);
			replaced = replaced.replaceAll(_get, _plus_3);
		}
		StringConcatenation newbuilder = new StringConcatenation();
		newbuilder.append(replaced);
		return newbuilder;
	}

	protected void getAbstractDomain(final AsmCollection asm) {
		EList<Function> _function = asm.getMain().getHeaderSection().getSignature().getFunction();
		for (final Function fd : _function) {
			if (((fd instanceof StaticFunction) && (fd.getCodomain() instanceof AbstractTd))) {
				this.abstractFunction.add(fd.getName());
			}
		}
	}

}
