package org.asmeta.asm2java;

import asmeta.definitions.BasicFunction;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.FunctionTerm;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * This class is used when implementing operations of the StandardLibrary
 */
@SuppressWarnings("all")
public class TermToJavaStandardLibrary extends TermToJava {
  public TermToJavaStandardLibrary(final Asm resource) {
    this(resource, false);
  }

  public TermToJavaStandardLibrary(final Asm resource, final boolean leftHandSide) {
    super(resource, leftHandSide, "");
  }

  @Override
  protected String _caseFunctionTermSupp(final ControlledFunction fd, final FunctionTerm ft) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field eContainer is undefined for the type TupleTerm"
      + "\nThe method or field eContainer is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nsize cannot be resolved"
      + "\n== cannot be resolved"
      + "\nget cannot be resolved"
      + "\nget cannot be resolved"
      + "\nget cannot be resolved"
      + "\nget cannot be resolved"
      + "\nget cannot be resolved"
      + "\nget cannot be resolved");
  }

  @Override
  protected String _caseFunctionTermSupp(final MonitoredFunction fd, final FunctionTerm ft) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nsize cannot be resolved"
      + "\n== cannot be resolved"
      + "\nget cannot be resolved"
      + "\nget cannot be resolved"
      + "\nsize cannot be resolved"
      + "\nget cannot be resolved");
  }

  @Override
  protected String _caseFunctionTermSupp(final StaticFunction fd, final FunctionTerm ft) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field domains is undefined for the type ProductDomain"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nsize cannot be resolved"
      + "\nget cannot be resolved"
      + "\nget cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nget cannot be resolved"
      + "\ndomain cannot be resolved"
      + "\nget cannot be resolved"
      + "\ndomain cannot be resolved");
  }

  @Override
  @XbaseGenerated
  public String caseFunctionTermSupp(final BasicFunction fd, final FunctionTerm ft) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method _caseFunctionTermSupp(ControlledFunction, FunctionTerm) of type TermToJavaStandardLibrary must override a superclass method."
      + "\nThe method _caseFunctionTermSupp(MonitoredFunction, FunctionTerm) of type TermToJavaStandardLibrary must override a superclass method."
      + "\nThe method _caseFunctionTermSupp(StaticFunction, FunctionTerm) of type TermToJavaStandardLibrary must override a superclass method.");
  }
}
