package org.asmeta.xt.validation.validators;

import org.asmeta.xt.asmetal.AgentInitialization;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.asmetal.DomainDefinition;
import org.asmeta.xt.asmetal.DomainInitialization;
import org.asmeta.xt.asmetal.ExportClause;
import org.asmeta.xt.asmetal.FunctionDefinition;
import org.asmeta.xt.asmetal.FunctionInitialization;
import org.asmeta.xt.asmetal.Header;
import org.asmeta.xt.asmetal.ImportClause;
import org.asmeta.xt.asmetal.Initialization;
import org.asmeta.xt.asmetal.Signature;
import org.asmeta.xt.validation.checkers.StructureChecker;
import org.asmeta.xt.validation.utility.ErrorType;
import org.asmeta.xt.validation.utility.MessageType;
import org.asmeta.xt.validation.utility.Utility;
import org.asmeta.xt.validation.utility.WarningType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * The class call the single validation rule and return the errors
 */
@SuppressWarnings("all")
public class StructureValidator {
  public static ErrorType checkError(final Asm asm) {
    ErrorType error = null;
    error = StructureChecker.isAsmNameOK(asm);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isMainRuleArityOK(asm);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static WarningType checkWarning(final Asm asm) {
    WarningType warning = null;
    warning = StructureChecker.isModuleMainRuleOK(asm);
    if ((warning != null)) {
      return warning;
    }
    warning = StructureChecker.isModuleDefaultInitialStateOK(asm);
    if ((warning != null)) {
      return warning;
    }
    warning = StructureChecker.isModuleInitialStateOK(asm);
    if ((warning != null)) {
      return warning;
    }
    return null;
  }

  public static ErrorType checkError(final ImportClause import_clause) {
    ErrorType error = null;
    error = StructureChecker.isImportedClauseModuleNameOK(import_clause);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isImportedListOK(import_clause);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static WarningType checkWarning(final ImportClause import_clause) {
    WarningType warning = null;
    warning = StructureChecker.haveSomethingToImport(import_clause);
    if ((warning != null)) {
      return warning;
    }
    return null;
  }

  public static MessageType checkError(final ExportClause export_clause) {
    EObject _eContainer = export_clause.eContainer();
    Header header = ((Header) _eContainer);
    EObject _eContainer_1 = header.eContainer();
    Asm asm = ((Asm) _eContainer_1);
    return StructureChecker.isExportedListOK(export_clause, header.getSignature(), asm.getBodySection());
  }

  public static ErrorType checkError(final Signature signature) {
    ErrorType error = null;
    error = StructureChecker.isFunctionListOK(signature);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final DomainDefinition domain_definition) {
    ErrorType error = null;
    error = StructureChecker.isDefinedOnceOK(domain_definition);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isDeclaredDomainOK(domain_definition.getDefinedDomainName());
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isBodyOK(domain_definition);
    if ((error != null)) {
      return error;
    }
    try {
      Utility.domain_declarations_map.put(domain_definition.getDefinedDomain(), domain_definition);
    } catch (final Throwable _t) {
      if (_t instanceof ClassCastException) {
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }

  public static ErrorType checkError(final FunctionDefinition function_definition) {
    ErrorType error = null;
    Utility.fillVariableMap(function_definition);
    error = StructureChecker.isDefinedOnceOK(function_definition);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isVariableListOK(function_definition);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isDomainListOK(function_definition);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isDefinedFunctionOK(function_definition);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isBodyOK(function_definition);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final Initialization init) {
    return null;
  }

  public static ErrorType checkError(final DomainInitialization domain_init) {
    ErrorType error = null;
    error = StructureChecker.isDefineOnceOK(domain_init);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isInizializedDomainOK(domain_init.getInitializedDomain());
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isBodyOK(domain_init);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final FunctionInitialization function_init) {
    ErrorType error = null;
    Utility.fillVariableMap(function_init);
    error = StructureChecker.isDefinedOnceOK(function_init);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isVariableListOK(function_init);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isDomainListOK(function_init);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isInizializedFunctionOK(function_init);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isBodyOK(function_init);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final AgentInitialization agent_inut) {
    ErrorType error = null;
    error = StructureChecker.isDomainOK(agent_inut);
    if ((error != null)) {
      return error;
    }
    error = StructureChecker.isProgramOK(agent_inut);
    if ((error != null)) {
      return error;
    }
    return null;
  }
}
