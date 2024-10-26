package org.asmeta.asm2java.evosuite;

import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import org.asmeta.asm2java.ToString;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class ToStringEvosuite extends ToString {
  public ToStringEvosuite(final Asm resource) {
    super(resource);
  }

  @Override
  public String visit(final PowersetDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaEvosuiteSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  @Override
  public String visit(final SequenceDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaEvosuiteSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  @Override
  public String visit(final RuleDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaEvosuiteSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  @Override
  public String visit(final ProductDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaEvosuiteSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  @Override
  public String visit(final BagDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaEvosuiteSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }

  @Override
  public String visit(final MapDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new DomainToJavaEvosuiteSigDef(this.res).visit(object);
    _builder.append(_visit);
    sb.append(_builder);
    return sb.toString();
  }
}
