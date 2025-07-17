package org.asmeta.asm2code;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.definitions.domains.impl.StructuredTdImpl;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import org.asmeta.asm2code.main.TranslatorOptions;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class FunctionToH extends ReflectiveVisitor<String> {
  private Asm res;

  private TranslatorOptions options;

  public FunctionToH(final Asm resource) {
    this.res = resource;
    TranslatorOptions _translatorOptions = new TranslatorOptions(true, false, false, false);
    this.options = _translatorOptions;
  }

  public FunctionToH(final Asm resource, final TranslatorOptions options) {
    this.res = resource;
    this.options = options;
  }

  public String visit(final StaticFunction object) {
    StringBuffer function = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof AbstractTd)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("static �returnDomain(object.codomain,true)� �object.name�;");
        _builder.newLine();
        function.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("static �returnDomain(object.codomain,false)� �object.name�();");
        _builder_1.newLine();
        function.append(_builder_1);
      }
    } else {
      Domain _domain_1 = object.getDomain();
      if ((_domain_1 instanceof ProductDomain)) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("static �returnDomain(object.codomain,false)� �object.name� (�adaptProdDomain(object.domain as ProductDomain, object.name, true)�);");
        function.append(_builder_2);
      } else {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("static �returnDomain(object.codomain,false)� �object.name� (�returnParamDefinition(object.domain, object.name,true)�);");
        function.append(_builder_3);
      }
    }
    return function.toString();
  }

  public String returnParamDefinition(final Domain domain, final String name, final boolean pointer) {
    int countparameters = 0;
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("�new ToString(res,pointer).visit(domain)� param�countparameters�_�name�, ");
    sb.append(_builder);
    countparameters++;
    String _string = sb.toString();
    int _length = sb.toString().length();
    int _minus = (_length - 2);
    return _string.substring(0, _minus);
  }

  public String adaptProdDomain(final ProductDomain domain, final String name, final boolean pointer) {
    StringBuffer paramDef = new StringBuffer();
    int countparameters = 0;
    paramDef.append("");
    for (int i = 0; (i < domain.getDomains().size()); i++) {
      {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("�new ToString(res,pointer).visit(domain.domains.get(i))� param�countparameters�_�name�, ");
        paramDef.append(_builder);
        countparameters++;
      }
    }
    int _length = paramDef.length();
    int _minus = (_length - 2);
    return paramDef.substring(0, _minus);
  }

  /**
   * def String returnDomain(Domain domain) {
   * 	var sb = new StringBuffer;
   * 	if (domain instanceof StructuredTd || domain instanceof StructuredTdImpl)
   * 		sb.append('''�new DomainToH(res,true).visit(domain)�''')
   * 	else
   * 		sb.append('''�new ToString(res).visit(domain)�''')
   * 	return sb.toString
   * }
   */
  public String returnDomain(final Domain domain, final boolean pointer) {
    StringBuffer sb = new StringBuffer();
    if (((domain instanceof StructuredTd) || (domain instanceof StructuredTdImpl))) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("�new DomainToH(res,pointer).visit(domain)�");
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�new ToString(res,pointer).visit(domain)�");
      sb.append(_builder_1);
    }
    return sb.toString();
  }

  public String visit(final ControlledFunction object) {
    StringBuffer function = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof AbstractTd)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("�returnDomain(object.codomain,true)� �object.name�[2];");
        function.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�returnDomain(object.codomain,false)� �object.name�[2];");
        function.append(_builder_1);
      }
    } else {
      if (((!(object.getDomain() instanceof ConcreteDomain)) || this.options.useMaps)) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("map<�returnDomain(object.domain,true)�, �returnDomain(object.codomain,true)�> �object.name�[2];");
        String _plus = (this.options.stdNamespacePrefix + _builder_2);
        function.append(_plus);
      } else {
        Domain _domain_1 = object.getDomain();
        ConcreteDomain domain = ((ConcreteDomain) _domain_1);
        Term _body = domain.getDefinition().getBody();
        SetTerm t = ((SetTerm) _body);
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("�returnDomain(object.codomain,false)� �object.name�[2][�t.size�];");
        function.append(_builder_3);
      }
    }
    return function.toString();
  }

  /**
   * TODO: DELETE FOR COVERAGE 	def String visit(SharedFunction object) {
   * var StringBuffer function = new StringBuffer
   * 
   * if (object.domain === null) { // 0-ary function
   * if (object.codomain instanceof AbstractTd)
   * function.append('''�returnDomain(object.codomain,true)� �object.name�[2];''')
   * else
   * function.append('''�returnDomain(object.codomain,false)� �object.name�[2];''')
   * } else {
   * function.
   * append('''map<�returnDomain(object.domain,true)�, �returnDomain(object.codomain,true)�> �object.name�[2];''')
   * }
   * 		if (object.arity<= 1) { // only 1 parameter
   * 			function.
   * 				append('''map<�returnDomain(object.domain)�, �returnDomain(object.codomain)�> �object.name�[2];''')
   * 		} else // multiple parameter
   * 			//function.append('''map<boost::tuple<�returnDomain(object.domain)�>, �returnDomain(object.codomain)�> �object.name�[2];''')
   * 			function.
   * 				append('''map<�returnDomain(object.domain)�, �returnDomain(object.codomain)�> �object.name�[2];''')
   * 
   * }
   * return function.toString
   * }
   */
  public String visit(final MonitoredFunction object) {
    StringBuffer function = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof AbstractTd)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("�returnDomain(object.codomain,true)� �object.name�;");
        function.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�returnDomain(object.codomain,false)� �object.name�;");
        function.append(_builder_1);
      }
    } else {
      if (((!(object.getDomain() instanceof ConcreteDomain)) || this.options.useMaps)) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("map<�returnDomain(object.domain,true)�, �returnDomain(object.codomain,true)�> �object.name�;");
        String _plus = (this.options.stdNamespacePrefix + _builder_2);
        function.append(_plus);
      } else {
        Domain _domain_1 = object.getDomain();
        ConcreteDomain domain = ((ConcreteDomain) _domain_1);
        Term _body = domain.getDefinition().getBody();
        SetTerm t = ((SetTerm) _body);
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("�returnDomain(object.codomain,false)� �object.name�[�t.size�];");
        function.append(_builder_3);
      }
    }
    return function.toString();
  }

  public String visit(final OutFunction object) {
    StringBuffer function = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof AbstractTd)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("�returnDomain(object.codomain,true)� �object.name�;");
        function.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�returnDomain(object.codomain,false)� �object.name�;");
        function.append(_builder_1);
      }
    } else {
      if (this.options.useMaps) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("map<�returnDomain(object.domain,true)�, �returnDomain(object.codomain,true)�> �object.name�;");
        String _plus = (this.options.stdNamespacePrefix + _builder_2);
        function.append(_plus);
      } else {
        Domain _domain_1 = object.getDomain();
        ConcreteDomain domain = ((ConcreteDomain) _domain_1);
        Term _body = domain.getDefinition().getBody();
        SetTerm t = ((SetTerm) _body);
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("�returnDomain(object.codomain,false)� �object.name�[�t.size�];");
        function.append(_builder_3);
      }
    }
    return function.toString();
  }

  public String visit(final DerivedFunction object) {
    StringBuffer function = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof AbstractTd)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("�returnDomain(object.codomain,true)� �object.name�;");
        _builder.newLine();
        function.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�returnDomain(object.codomain,false)� �object.name�();");
        _builder_1.newLine();
        function.append(_builder_1);
      }
    } else {
      Domain _domain_1 = object.getDomain();
      if ((_domain_1 instanceof ProductDomain)) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("�returnDomain(object.codomain,true)� �object.name� (�adaptProdDomain(object.domain as ProductDomain, object.name,true)�);");
        function.append(_builder_2);
      } else {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("�returnDomain(object.codomain,true)� �object.name� (�returnParamDefinition(object.domain, object.name,true)�);");
        function.append(_builder_3);
      }
    }
    return function.toString();
  }
}
