package org.asmeta.asm2java;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.CharDomain;
import asmeta.definitions.domains.ComplexDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.UndefDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.FunctionTerm;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ToString extends ReflectiveVisitor<String> {
  Integer numStaticParam;

  private Asm res;

  public ToString(final Asm resource) {
    this.res = resource;
  }

  public String visit(final StringDomain domain) {
    return "String";
  }

  public String visit(final BooleanDomain domain) {
    return "Boolean";
  }

  public String visit(final IntegerDomain domain) {
    return "Integer";
  }

  public String visit(final NaturalDomain domain) {
    return "Integer";
  }

  public String visit(final AnyDomain object) {
    return "Object";
  }

  public String visit(final ConcreteDomain domain) {
    return domain.getName();
  }

  public String visit(final EnumElement elem) {
    return elem.getSymbol();
  }

  public String visit(final EnumTd elem) {
    return elem.getName();
  }

  public String visit(final CharDomain domain) {
    return "Character";
  }

  public String visit(final AgentDomain domain) {
    try {
      throw new Exception("Agent domain not supported");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String visit(final UndefDomain domain) {
    try {
      throw new Exception("Undefined domain not supported");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String visit(final RealDomain domain) {
    return "Double";
  }

  public String visit(final PowersetDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final SequenceDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final AbstractTd object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _name = object.getName();
    _builder.append(_name);
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final RuleDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final ProductDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final BagDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final MapDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final FunctionTerm term) {
    return new TermToJava(this.res).visit(term);
  }

  public String visit(final ComplexDomain domain) {
    try {
      throw new Exception("Complex domain not supported");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
