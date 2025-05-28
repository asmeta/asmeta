package org.asmeta.asm2code;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.definitions.domains.impl.StructuredTdImpl;
import asmeta.structure.Asm;
import org.asmeta.asm2code.main.TranslatorOptions;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * TODO: delete this class -> replaced with DomanToH and FunctionToH
 */
@SuppressWarnings("all")
public class DomainToH extends ReflectiveVisitor<String> {
  private Asm res;

  private boolean pointer;

  private TranslatorOptions options;

  public DomainToH(final Asm resource) {
    this.res = resource;
    this.pointer = false;
    TranslatorOptions _translatorOptions = new TranslatorOptions(true, false, false, false);
    this.options = _translatorOptions;
  }

  public DomainToH(final Asm resource, final boolean pointer) {
    this.res = resource;
    this.pointer = pointer;
    TranslatorOptions _translatorOptions = new TranslatorOptions(true, false, false, false);
    this.options = _translatorOptions;
  }

  /**
   * Domain Signature
   */
  public String visit(final ConcreteDomain object) {
    StringBuffer sb = new StringBuffer();
    if (((object.getTypeDomain() instanceof StructuredTd) || (object.getTypeDomain() instanceof StructuredTdImpl))) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("typedef �visit(object.typeDomain)� �object.name�;");
      _builder.newLine();
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("typedef �new ToString(res).visit(object.typeDomain)� �object.name�;");
      _builder_1.newLine();
      sb.append(_builder_1);
    }
    return sb.toString();
  }

  /**
   * TODO: DELETE FOR COVERAGE 	def String visit(RuleDomain object) {
   * throw new ExceptionAsmetaToC("RuleDomain", true)
   * }
   */
  public String visit(final ProductDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("tuple<");
    sb.append(_builder);
    for (int i = 0; (i < object.getDomains().size()); i++) {
      Domain _get = object.getDomains().get(i);
      if ((_get instanceof StructuredTd)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�visit(object.domains.get(i))�, ");
        sb.append(_builder_1);
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("�new ToString(res).visit(object.domains.get(i))�, ");
        sb.append(_builder_2);
      }
    }
    String _string = sb.toString();
    int _length = sb.length();
    int _minus = (_length - 2);
    return _string.substring(0, _minus).concat(">");
  }

  public String visit(final SequenceDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("vector<");
    String _plus = (this.options.stdNamespacePrefix + _builder);
    sb.append(_plus);
    Domain _domain = object.getDomain();
    if ((_domain instanceof StructuredTd)) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�visit(object.domain)�");
      sb.append(_builder_1);
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("�new ToString(res).visit(object.domain)�");
      sb.append(_builder_2);
    }
    return sb.toString().substring(0, sb.length()).concat(">");
  }

  public String visit(final PowersetDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("set<");
    String _plus = (this.options.stdNamespacePrefix + _builder);
    sb.append(_plus);
    Domain _baseDomain = object.getBaseDomain();
    if ((_baseDomain instanceof StructuredTd)) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�visit(object.baseDomain)�");
      sb.append(_builder_1);
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("�new ToString(res).visit(object.baseDomain)�");
      sb.append(_builder_2);
    }
    return sb.toString().substring(0, sb.length()).concat(">");
  }

  public String visit(final BagDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("multiset<");
    String _plus = (this.options.stdNamespacePrefix + _builder);
    sb.append(_plus);
    Domain _domain = object.getDomain();
    if ((_domain instanceof StructuredTd)) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�visit(object.domain)�");
      sb.append(_builder_1);
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("�new ToString(res).visit(object.domain)�");
      sb.append(_builder_2);
    }
    return sb.toString().substring(0, sb.length()).concat(">");
  }

  public String visit(final MapDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("map<");
    String _plus = (this.options.stdNamespacePrefix + _builder);
    sb.append(_plus);
    Domain _sourceDomain = object.getSourceDomain();
    if ((_sourceDomain instanceof StructuredTd)) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�visit(object.sourceDomain)�,");
      sb.append(_builder_1);
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("�new ToString(res).visit(object.sourceDomain)�,");
      sb.append(_builder_2);
    }
    Domain _targetDomain = object.getTargetDomain();
    if ((_targetDomain instanceof StructuredTd)) {
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("�visit(object.targetDomain)�,");
      sb.append(_builder_3);
    } else {
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("�new ToString(res).visit(object.targetDomain)�");
      sb.append(_builder_4);
    }
    return sb.toString().substring(0, sb.length()).concat(">");
  }

  /**
   * TODO: DELETE FOR COVERAGE def void visit(AnyDomain object) {
   * throw new ExceptionAsmetaToC("AnyDomain", true)
   * }
   */
  public String visit(final EnumTd object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum �object.name� {");
    sb.append(_builder);
    for (int i = 0; (i < object.getElement().size()); i++) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�new ToString(res).visit(object.element.get(i))�, ");
      sb.append(_builder_1);
    }
    String _string = sb.toString();
    int _length = sb.length();
    int _minus = (_length - 2);
    return _string.substring(0, _minus).concat("};\n");
  }

  /**
   * def String visit(EnumTd object) {
   * 		var StringBuffer sb = new StringBuffer
   * 		sb.append('''enum �object.name� {''')
   * 		for (var int i = 0; i < object.element.size; i++) {
   * 			sb.append('''�new ToString(res).visit(object.element.get(i))�, ''')
   * 		}
   * 		return sb.toString.substring(0, sb.length - 2).concat("};\n") + setDomainContainer(object.name,false)
   * }
   */
  public String visit(final AbstractTd object) {
    if ((this.pointer == true)) {
      String _name = object.getName();
      return (_name + "*");
    } else {
      return object.getName();
    }
  }

  public String visit(final RealDomain object) {
    return "";
  }
}
