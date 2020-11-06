package org.asmeta.asm2java;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.definitions.domains.impl.StructuredTdImpl;
import asmeta.structure.Asm;
import org.asmeta.asm2java.DomainToJavaSigDef;
import org.asmeta.asm2java.ToString;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class FunctionToJavaSig extends ReflectiveVisitor<String> {
  private Asm res;
  
  public FunctionToJavaSig(final Asm resource) {
    this.res = resource;
  }
  
  public String visit(final StaticFunction object) {
    StringBuffer function = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//Funzione di tipo statico");
    _builder.newLine();
    function.append(_builder);
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof AbstractTd)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("static ");
        String _returnDomain = this.returnDomain(object.getCodomain(), true);
        _builder_1.append(_returnDomain);
        _builder_1.append(" ");
        String _name = object.getName();
        _builder_1.append(_name);
        _builder_1.append(";");
        _builder_1.newLineIfNotEmpty();
        function.append(_builder_1);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof ProductDomain)) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("static ");
          String _visit = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
          _builder_2.append(_visit);
          _builder_2.append(" ");
          String _name_1 = object.getName();
          _builder_2.append(_name_1);
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          function.append(_builder_2);
        } else {
          Domain _codomain_2 = object.getCodomain();
          if ((_codomain_2 instanceof SequenceDomain)) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("static List");
            String _visit_1 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_3.append(_visit_1);
            _builder_3.append(" ");
            String _name_2 = object.getName();
            _builder_3.append(_name_2);
            _builder_3.append(" = new ArrayList");
            String _visit_2 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_3.append(_visit_2);
            _builder_3.append("();");
            _builder_3.newLineIfNotEmpty();
            function.append(_builder_3);
          } else {
            Domain _codomain_3 = object.getCodomain();
            if ((_codomain_3 instanceof PowersetDomain)) {
              StringConcatenation _builder_4 = new StringConcatenation();
              _builder_4.append("static Set");
              String _visit_3 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_4.append(_visit_3);
              _builder_4.append(" ");
              String _name_3 = object.getName();
              _builder_4.append(_name_3);
              _builder_4.append(" = new HashSet");
              String _visit_4 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_4.append(_visit_4);
              _builder_4.append("();");
              _builder_4.newLineIfNotEmpty();
              function.append(_builder_4);
            } else {
              Domain _codomain_4 = object.getCodomain();
              if ((_codomain_4 instanceof BagDomain)) {
                StringConcatenation _builder_5 = new StringConcatenation();
                _builder_5.append("static Bag");
                String _visit_5 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_5.append(_visit_5);
                _builder_5.append(" ");
                String _name_4 = object.getName();
                _builder_5.append(_name_4);
                _builder_5.append(" = new HashBag");
                String _visit_6 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_5.append(_visit_6);
                _builder_5.append("();");
                _builder_5.newLineIfNotEmpty();
                function.append(_builder_5);
              } else {
                Domain _codomain_5 = object.getCodomain();
                if ((_codomain_5 instanceof MapDomain)) {
                  StringConcatenation _builder_6 = new StringConcatenation();
                  _builder_6.append("static Map");
                  String _visit_7 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                  _builder_6.append(_visit_7);
                  _builder_6.append(" ");
                  String _name_5 = object.getName();
                  _builder_6.append(_name_5);
                  _builder_6.append(" = new HashMap");
                  String _visit_8 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                  _builder_6.append(_visit_8);
                  _builder_6.append("();");
                  _builder_6.newLineIfNotEmpty();
                  function.append(_builder_6);
                } else {
                  StringConcatenation _builder_7 = new StringConcatenation();
                  _builder_7.append("abstract ");
                  String _returnDomain_1 = this.returnDomain(object.getCodomain(), false);
                  _builder_7.append(_returnDomain_1);
                  _builder_7.append(" ");
                  String _name_6 = object.getName();
                  _builder_7.append(_name_6);
                  _builder_7.append("();");
                  _builder_7.newLineIfNotEmpty();
                  function.append(_builder_7);
                }
              }
            }
          }
        }
      }
    } else {
      Domain _domain_1 = object.getDomain();
      if ((_domain_1 instanceof ProductDomain)) {
        StringConcatenation _builder_8 = new StringConcatenation();
        _builder_8.append("abstract ");
        String _returnDomain_2 = this.returnDomain(object.getCodomain(), false);
        _builder_8.append(_returnDomain_2);
        _builder_8.append(" ");
        String _name_7 = object.getName();
        _builder_8.append(_name_7);
        _builder_8.append(" (");
        Domain _domain_2 = object.getDomain();
        String _adaptProdDomain = this.adaptProdDomain(((ProductDomain) _domain_2), object.getName(), true);
        _builder_8.append(_adaptProdDomain);
        _builder_8.append(");");
        _builder_8.newLineIfNotEmpty();
        function.append(_builder_8);
      } else {
        Domain _domain_3 = object.getDomain();
        if ((_domain_3 instanceof SequenceDomain)) {
          StringConcatenation _builder_9 = new StringConcatenation();
          _builder_9.append("abstract ArrayList");
          String _returnDomain_3 = this.returnDomain(object.getCodomain(), false);
          _builder_9.append(_returnDomain_3);
          _builder_9.append(" ");
          String _name_8 = object.getName();
          _builder_9.append(_name_8);
          _builder_9.append(" (ArrayList");
          String _returnParamDefinition = this.returnParamDefinition(object.getDomain(), object.getName(), true);
          _builder_9.append(_returnParamDefinition);
          _builder_9.append(");");
          _builder_9.newLineIfNotEmpty();
          function.append(_builder_9);
        } else {
          StringConcatenation _builder_10 = new StringConcatenation();
          _builder_10.append("abstract ");
          String _returnDomain_4 = this.returnDomain(object.getCodomain(), false);
          _builder_10.append(_returnDomain_4);
          _builder_10.append(" ");
          String _name_9 = object.getName();
          _builder_10.append(_name_9);
          _builder_10.append(" (");
          String _returnParamDefinition_1 = this.returnParamDefinition(object.getDomain(), object.getName(), true);
          _builder_10.append(_returnParamDefinition_1);
          _builder_10.append(");");
          _builder_10.newLineIfNotEmpty();
          function.append(_builder_10);
        }
      }
    }
    return function.toString();
  }
  
  public String returnParamDefinition(final Domain domain, final String name, final boolean pointer) {
    int countparameters = 0;
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new ToString(this.res, pointer).visit(domain);
    _builder.append(_visit);
    _builder.append(" param");
    _builder.append(countparameters);
    _builder.append("_");
    _builder.append(name);
    _builder.append(", ");
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
        String _visit = new ToString(this.res, pointer).visit(domain.getDomains().get(i));
        _builder.append(_visit);
        _builder.append(" param");
        _builder.append(countparameters);
        _builder.append("_");
        _builder.append(name);
        _builder.append(", ");
        paramDef.append(_builder);
        countparameters++;
      }
    }
    int _length = paramDef.length();
    int _minus = (_length - 2);
    return paramDef.substring(0, _minus);
  }
  
  public String returnDomain(final Domain domain, final boolean pointer) {
    StringBuffer sb = new StringBuffer();
    if (((domain instanceof StructuredTd) || (domain instanceof StructuredTdImpl))) {
      StringConcatenation _builder = new StringConcatenation();
      String _visit = new DomainToJavaSigDef(this.res, pointer).visit(domain);
      _builder.append(_visit);
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _visit_1 = new ToString(this.res, pointer).visit(domain);
      _builder_1.append(_visit_1);
      sb.append(_builder_1);
    }
    return sb.toString();
  }
  
  public String visit(final ControlledFunction object) {
    StringBuffer function = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//Funzione di tipo Controlled");
    _builder.newLine();
    function.append(_builder);
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof ProductDomain)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _visit = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
        _builder_1.append(_visit);
        _builder_1.append(" ");
        String _name = object.getName();
        _builder_1.append(_name);
        _builder_1.append("_elem;");
        _builder_1.newLineIfNotEmpty();
        function.append(_builder_1);
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("zeroC<");
        String _returnDomain = this.returnDomain(object.getCodomain(), false);
        _builder_2.append(_returnDomain);
        _builder_2.append("> ");
        String _name_1 = object.getName();
        _builder_2.append(_name_1);
        _builder_2.append(" = new zeroC<>();");
        _builder_2.newLineIfNotEmpty();
        _builder_2.newLine();
        function.append(_builder_2);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof SequenceDomain)) {
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("List");
          String _visit_1 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
          _builder_3.append(_visit_1);
          _builder_3.append(" ");
          String _name_2 = object.getName();
          _builder_3.append(_name_2);
          _builder_3.append("_elem = new ArrayList");
          String _visit_2 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
          _builder_3.append(_visit_2);
          _builder_3.append("();");
          _builder_3.newLineIfNotEmpty();
          _builder_3.append("\t\t\t\t");
          _builder_3.newLine();
          function.append(_builder_3);
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("zeroC<List");
          String _returnDomain_1 = this.returnDomain(object.getCodomain(), false);
          _builder_4.append(_returnDomain_1);
          _builder_4.append("> ");
          String _name_3 = object.getName();
          _builder_4.append(_name_3);
          _builder_4.append(" = new zeroC<>();");
          _builder_4.newLineIfNotEmpty();
          _builder_4.newLine();
          function.append(_builder_4);
        } else {
          Domain _codomain_2 = object.getCodomain();
          if ((_codomain_2 instanceof PowersetDomain)) {
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append("Set");
            String _visit_3 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_5.append(_visit_3);
            _builder_5.append(" ");
            String _name_4 = object.getName();
            _builder_5.append(_name_4);
            _builder_5.append("_elem = new HashSet");
            String _visit_4 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_5.append(_visit_4);
            _builder_5.append("();");
            _builder_5.newLineIfNotEmpty();
            _builder_5.append("\t\t\t\t");
            _builder_5.newLine();
            function.append(_builder_5);
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append("zeroC<Set");
            String _returnDomain_2 = this.returnDomain(object.getCodomain(), false);
            _builder_6.append(_returnDomain_2);
            _builder_6.append("> ");
            String _name_5 = object.getName();
            _builder_6.append(_name_5);
            _builder_6.append(" = new zeroC<>();");
            _builder_6.newLineIfNotEmpty();
            _builder_6.newLine();
            function.append(_builder_6);
          } else {
            Domain _codomain_3 = object.getCodomain();
            if ((_codomain_3 instanceof BagDomain)) {
              StringConcatenation _builder_7 = new StringConcatenation();
              _builder_7.append("Bag");
              String _visit_5 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_7.append(_visit_5);
              _builder_7.append(" ");
              String _name_6 = object.getName();
              _builder_7.append(_name_6);
              _builder_7.append("_elem = new HashBag");
              String _visit_6 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_7.append(_visit_6);
              _builder_7.append("();");
              _builder_7.newLineIfNotEmpty();
              _builder_7.append("\t\t\t\t");
              _builder_7.newLine();
              function.append(_builder_7);
              StringConcatenation _builder_8 = new StringConcatenation();
              _builder_8.append("zeroC<Bag");
              String _returnDomain_3 = this.returnDomain(object.getCodomain(), false);
              _builder_8.append(_returnDomain_3);
              _builder_8.append("> ");
              String _name_7 = object.getName();
              _builder_8.append(_name_7);
              _builder_8.append(" = new zeroC<>();");
              _builder_8.newLineIfNotEmpty();
              _builder_8.newLine();
              function.append(_builder_8);
            } else {
              Domain _codomain_4 = object.getCodomain();
              if ((_codomain_4 instanceof MapDomain)) {
                StringConcatenation _builder_9 = new StringConcatenation();
                _builder_9.append("Map");
                String _visit_7 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_9.append(_visit_7);
                _builder_9.append(" ");
                String _name_8 = object.getName();
                _builder_9.append(_name_8);
                _builder_9.append("_elem = new HashMap");
                String _visit_8 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_9.append(_visit_8);
                _builder_9.append("();");
                _builder_9.newLineIfNotEmpty();
                _builder_9.append("\t\t\t\t");
                _builder_9.newLine();
                function.append(_builder_9);
                StringConcatenation _builder_10 = new StringConcatenation();
                _builder_10.append("zeroC<Map");
                String _returnDomain_4 = this.returnDomain(object.getCodomain(), false);
                _builder_10.append(_returnDomain_4);
                _builder_10.append("> ");
                String _name_9 = object.getName();
                _builder_10.append(_name_9);
                _builder_10.append(" = new zeroC<>();");
                _builder_10.newLineIfNotEmpty();
                _builder_10.newLine();
                function.append(_builder_10);
              } else {
                StringConcatenation _builder_11 = new StringConcatenation();
                _builder_11.append("zeroC <");
                String _returnDomain_5 = this.returnDomain(object.getCodomain(), false);
                _builder_11.append(_returnDomain_5);
                _builder_11.append("> ");
                String _name_10 = object.getName();
                _builder_11.append(_name_10);
                _builder_11.append(" = new zeroC<>();");
                _builder_11.newLineIfNotEmpty();
                _builder_11.newLine();
                function.append(_builder_11);
              }
            }
          }
        }
      }
    } else {
      if (((object.getDomain() instanceof ProductDomain) && (object.getCodomain() != null))) {
        StringConcatenation _builder_12 = new StringConcatenation();
        String _visit_9 = new DomainToJavaSigDef(this.res).visit(object.getDomain());
        _builder_12.append(_visit_9);
        _builder_12.append(" ");
        String _name_11 = object.getName();
        _builder_12.append(_name_11);
        _builder_12.append("_elem;");
        _builder_12.newLineIfNotEmpty();
        function.append(_builder_12);
      }
      StringConcatenation _builder_13 = new StringConcatenation();
      _builder_13.append("nC<");
      String _returnDomain_6 = this.returnDomain(object.getDomain(), true);
      _builder_13.append(_returnDomain_6);
      _builder_13.append(", ");
      String _returnDomain_7 = this.returnDomain(object.getCodomain(), true);
      _builder_13.append(_returnDomain_7);
      _builder_13.append("> ");
      String _name_12 = object.getName();
      _builder_13.append(_name_12);
      _builder_13.append(" = new nC<>();");
      _builder_13.newLineIfNotEmpty();
      _builder_13.newLine();
      function.append(_builder_13);
    }
    return function.toString();
  }
  
  public String visit(final MonitoredFunction object) {
    StringBuffer function = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//Funzione di tipo monitored");
    _builder.newLine();
    function.append(_builder);
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof ProductDomain)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _visit = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
        _builder_1.append(_visit);
        _builder_1.append(" ");
        String _name = object.getName();
        _builder_1.append(_name);
        _builder_1.append("_elem;");
        _builder_1.newLineIfNotEmpty();
        function.append(_builder_1);
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("zero<");
        String _returnDomain = this.returnDomain(object.getCodomain(), false);
        _builder_2.append(_returnDomain);
        _builder_2.append("> ");
        String _name_1 = object.getName();
        _builder_2.append(_name_1);
        _builder_2.append(" = new zero<>();");
        _builder_2.newLineIfNotEmpty();
        _builder_2.newLine();
        function.append(_builder_2);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof SequenceDomain)) {
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("List");
          String _visit_1 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
          _builder_3.append(_visit_1);
          _builder_3.append(" ");
          String _name_2 = object.getName();
          _builder_3.append(_name_2);
          _builder_3.append("_elem = new ArrayList");
          String _visit_2 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
          _builder_3.append(_visit_2);
          _builder_3.append("();");
          _builder_3.newLineIfNotEmpty();
          _builder_3.append("\t\t\t\t");
          _builder_3.newLine();
          function.append(_builder_3);
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("zero<List");
          String _returnDomain_1 = this.returnDomain(object.getCodomain(), false);
          _builder_4.append(_returnDomain_1);
          _builder_4.append("> ");
          String _name_3 = object.getName();
          _builder_4.append(_name_3);
          _builder_4.append(" = new zero<>();");
          _builder_4.newLineIfNotEmpty();
          _builder_4.newLine();
          function.append(_builder_4);
        } else {
          Domain _codomain_2 = object.getCodomain();
          if ((_codomain_2 instanceof PowersetDomain)) {
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append("Set");
            String _visit_3 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_5.append(_visit_3);
            _builder_5.append(" ");
            String _name_4 = object.getName();
            _builder_5.append(_name_4);
            _builder_5.append("_elem = new HashSet");
            String _visit_4 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_5.append(_visit_4);
            _builder_5.append("();");
            _builder_5.newLineIfNotEmpty();
            _builder_5.append("\t\t\t\t");
            _builder_5.newLine();
            function.append(_builder_5);
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append("zero<Set");
            String _returnDomain_2 = this.returnDomain(object.getCodomain(), false);
            _builder_6.append(_returnDomain_2);
            _builder_6.append("> ");
            String _name_5 = object.getName();
            _builder_6.append(_name_5);
            _builder_6.append(" = new zero<>();");
            _builder_6.newLineIfNotEmpty();
            _builder_6.newLine();
            function.append(_builder_6);
          } else {
            Domain _codomain_3 = object.getCodomain();
            if ((_codomain_3 instanceof BagDomain)) {
              StringConcatenation _builder_7 = new StringConcatenation();
              _builder_7.append("Bag");
              String _visit_5 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_7.append(_visit_5);
              _builder_7.append(" ");
              String _name_6 = object.getName();
              _builder_7.append(_name_6);
              _builder_7.append("_elem = new HashBag");
              String _visit_6 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_7.append(_visit_6);
              _builder_7.append("();");
              _builder_7.newLineIfNotEmpty();
              _builder_7.append("\t\t\t\t");
              _builder_7.newLine();
              function.append(_builder_7);
              StringConcatenation _builder_8 = new StringConcatenation();
              _builder_8.append("zero<Bag");
              String _returnDomain_3 = this.returnDomain(object.getCodomain(), false);
              _builder_8.append(_returnDomain_3);
              _builder_8.append("> ");
              String _name_7 = object.getName();
              _builder_8.append(_name_7);
              _builder_8.append(" = new zero<>();");
              _builder_8.newLineIfNotEmpty();
              _builder_8.newLine();
              function.append(_builder_8);
            } else {
              Domain _codomain_4 = object.getCodomain();
              if ((_codomain_4 instanceof MapDomain)) {
                StringConcatenation _builder_9 = new StringConcatenation();
                _builder_9.append("Map");
                String _visit_7 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_9.append(_visit_7);
                _builder_9.append(" ");
                String _name_8 = object.getName();
                _builder_9.append(_name_8);
                _builder_9.append("_elem = new HashMap");
                String _visit_8 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_9.append(_visit_8);
                _builder_9.append("();");
                _builder_9.newLineIfNotEmpty();
                _builder_9.append("\t\t\t\t");
                _builder_9.newLine();
                function.append(_builder_9);
                StringConcatenation _builder_10 = new StringConcatenation();
                _builder_10.append("zero<Map");
                String _returnDomain_4 = this.returnDomain(object.getCodomain(), false);
                _builder_10.append(_returnDomain_4);
                _builder_10.append("> ");
                String _name_9 = object.getName();
                _builder_10.append(_name_9);
                _builder_10.append(" = new zero<>();");
                _builder_10.newLineIfNotEmpty();
                _builder_10.newLine();
                function.append(_builder_10);
              } else {
                Domain _codomain_5 = object.getCodomain();
                if ((_codomain_5 instanceof ConcreteDomain)) {
                  StringConcatenation _builder_11 = new StringConcatenation();
                  _builder_11.append("zero<");
                  String _returnDomain_5 = this.returnDomain(object.getCodomain(), false);
                  _builder_11.append(_returnDomain_5);
                  _builder_11.append("> ");
                  String _name_10 = object.getName();
                  _builder_11.append(_name_10);
                  _builder_11.append(" = new zero<>();");
                  _builder_11.newLineIfNotEmpty();
                  _builder_11.newLine();
                  String _returnDomain_6 = this.returnDomain(object.getCodomain(), false);
                  _builder_11.append(_returnDomain_6);
                  _builder_11.append(" ");
                  String _name_11 = object.getName();
                  _builder_11.append(_name_11);
                  _builder_11.append("_supporto = new ");
                  String _returnDomain_7 = this.returnDomain(object.getCodomain(), false);
                  _builder_11.append(_returnDomain_7);
                  _builder_11.append("();");
                  _builder_11.newLineIfNotEmpty();
                  function.append(_builder_11);
                } else {
                  StringConcatenation _builder_12 = new StringConcatenation();
                  _builder_12.append("zero<");
                  String _returnDomain_8 = this.returnDomain(object.getCodomain(), false);
                  _builder_12.append(_returnDomain_8);
                  _builder_12.append("> ");
                  String _name_12 = object.getName();
                  _builder_12.append(_name_12);
                  _builder_12.append(" = new zero<>();");
                  _builder_12.newLineIfNotEmpty();
                  _builder_12.newLine();
                  function.append(_builder_12);
                }
              }
            }
          }
        }
      }
    } else {
      Domain _domain_1 = object.getDomain();
      if ((_domain_1 instanceof ProductDomain)) {
        StringConcatenation _builder_13 = new StringConcatenation();
        String _visit_9 = new DomainToJavaSigDef(this.res).visit(object.getDomain());
        _builder_13.append(_visit_9);
        _builder_13.append(" ");
        String _name_13 = object.getName();
        _builder_13.append(_name_13);
        _builder_13.append("_elem;");
        _builder_13.newLineIfNotEmpty();
        function.append(_builder_13);
      }
      StringConcatenation _builder_14 = new StringConcatenation();
      _builder_14.append("n<");
      String _returnDomain_9 = this.returnDomain(object.getDomain(), true);
      _builder_14.append(_returnDomain_9);
      _builder_14.append(", ");
      String _returnDomain_10 = this.returnDomain(object.getCodomain(), true);
      _builder_14.append(_returnDomain_10);
      _builder_14.append("> ");
      String _name_14 = object.getName();
      _builder_14.append(_name_14);
      _builder_14.append(" = new n<>();");
      _builder_14.newLineIfNotEmpty();
      _builder_14.newLine();
      function.append(_builder_14);
    }
    return function.toString();
  }
  
  public String visit(final OutFunction object) {
    StringBuffer function = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//Funzione di tipo out");
    _builder.newLine();
    function.append(_builder);
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof ProductDomain)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _visit = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
        _builder_1.append(_visit);
        _builder_1.append(" ");
        String _name = object.getName();
        _builder_1.append(_name);
        _builder_1.append("_elem;");
        _builder_1.newLineIfNotEmpty();
        function.append(_builder_1);
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("zero<");
        String _returnDomain = this.returnDomain(object.getCodomain(), false);
        _builder_2.append(_returnDomain);
        _builder_2.append("> ");
        String _name_1 = object.getName();
        _builder_2.append(_name_1);
        _builder_2.append(" = new zero<>();");
        _builder_2.newLineIfNotEmpty();
        _builder_2.newLine();
        function.append(_builder_2);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof SequenceDomain)) {
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("List");
          String _visit_1 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
          _builder_3.append(_visit_1);
          _builder_3.append(" ");
          String _name_2 = object.getName();
          _builder_3.append(_name_2);
          _builder_3.append("_elem = new ArrayList");
          String _visit_2 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
          _builder_3.append(_visit_2);
          _builder_3.append("();");
          _builder_3.newLineIfNotEmpty();
          _builder_3.append("\t\t\t\t");
          _builder_3.newLine();
          function.append(_builder_3);
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("zero<List");
          String _returnDomain_1 = this.returnDomain(object.getCodomain(), false);
          _builder_4.append(_returnDomain_1);
          _builder_4.append("> ");
          String _name_3 = object.getName();
          _builder_4.append(_name_3);
          _builder_4.append(" = new zero<>();");
          _builder_4.newLineIfNotEmpty();
          _builder_4.newLine();
          function.append(_builder_4);
        } else {
          Domain _codomain_2 = object.getCodomain();
          if ((_codomain_2 instanceof PowersetDomain)) {
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append("Set");
            String _visit_3 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_5.append(_visit_3);
            _builder_5.append(" ");
            String _name_4 = object.getName();
            _builder_5.append(_name_4);
            _builder_5.append("_elem = new HashSet");
            String _visit_4 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_5.append(_visit_4);
            _builder_5.append("();");
            _builder_5.newLineIfNotEmpty();
            _builder_5.append("\t\t\t\t");
            _builder_5.newLine();
            function.append(_builder_5);
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append("zero<Set");
            String _returnDomain_2 = this.returnDomain(object.getCodomain(), false);
            _builder_6.append(_returnDomain_2);
            _builder_6.append("> ");
            String _name_5 = object.getName();
            _builder_6.append(_name_5);
            _builder_6.append(" = new zero<>();");
            _builder_6.newLineIfNotEmpty();
            _builder_6.newLine();
            function.append(_builder_6);
          } else {
            Domain _codomain_3 = object.getCodomain();
            if ((_codomain_3 instanceof BagDomain)) {
              StringConcatenation _builder_7 = new StringConcatenation();
              _builder_7.append("Bag");
              String _visit_5 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_7.append(_visit_5);
              _builder_7.append(" ");
              String _name_6 = object.getName();
              _builder_7.append(_name_6);
              _builder_7.append("_elem = new HashBag");
              String _visit_6 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_7.append(_visit_6);
              _builder_7.append("();");
              _builder_7.newLineIfNotEmpty();
              _builder_7.append("\t\t\t\t");
              _builder_7.newLine();
              function.append(_builder_7);
              StringConcatenation _builder_8 = new StringConcatenation();
              _builder_8.append("zero<Bag");
              String _returnDomain_3 = this.returnDomain(object.getCodomain(), false);
              _builder_8.append(_returnDomain_3);
              _builder_8.append("> ");
              String _name_7 = object.getName();
              _builder_8.append(_name_7);
              _builder_8.append(" = new zero<>();");
              _builder_8.newLineIfNotEmpty();
              _builder_8.newLine();
              function.append(_builder_8);
            } else {
              Domain _codomain_4 = object.getCodomain();
              if ((_codomain_4 instanceof MapDomain)) {
                StringConcatenation _builder_9 = new StringConcatenation();
                _builder_9.append("Map");
                String _visit_7 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_9.append(_visit_7);
                _builder_9.append(" ");
                String _name_8 = object.getName();
                _builder_9.append(_name_8);
                _builder_9.append("_elem = new HashMap");
                String _visit_8 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_9.append(_visit_8);
                _builder_9.append("();");
                _builder_9.newLineIfNotEmpty();
                _builder_9.append("\t\t\t\t");
                _builder_9.newLine();
                function.append(_builder_9);
                StringConcatenation _builder_10 = new StringConcatenation();
                _builder_10.append("zero<Map");
                String _returnDomain_4 = this.returnDomain(object.getCodomain(), false);
                _builder_10.append(_returnDomain_4);
                _builder_10.append("> ");
                String _name_9 = object.getName();
                _builder_10.append(_name_9);
                _builder_10.append(" = new zero<>();");
                _builder_10.newLineIfNotEmpty();
                _builder_10.newLine();
                function.append(_builder_10);
              } else {
                StringConcatenation _builder_11 = new StringConcatenation();
                _builder_11.append("zero<");
                String _returnDomain_5 = this.returnDomain(object.getCodomain(), false);
                _builder_11.append(_returnDomain_5);
                _builder_11.append("> ");
                String _name_10 = object.getName();
                _builder_11.append(_name_10);
                _builder_11.append(" = new zero<>();");
                _builder_11.newLineIfNotEmpty();
                _builder_11.newLine();
                function.append(_builder_11);
              }
            }
          }
        }
      }
    } else {
      Domain _domain_1 = object.getDomain();
      if ((_domain_1 instanceof ProductDomain)) {
        StringConcatenation _builder_12 = new StringConcatenation();
        String _visit_9 = new DomainToJavaSigDef(this.res).visit(object.getDomain());
        _builder_12.append(_visit_9);
        _builder_12.append(" ");
        String _name_11 = object.getName();
        _builder_12.append(_name_11);
        _builder_12.append("_elem;");
        _builder_12.newLineIfNotEmpty();
        function.append(_builder_12);
      }
      StringConcatenation _builder_13 = new StringConcatenation();
      _builder_13.append("n<");
      String _returnDomain_6 = this.returnDomain(object.getDomain(), true);
      _builder_13.append(_returnDomain_6);
      _builder_13.append(", ");
      String _returnDomain_7 = this.returnDomain(object.getCodomain(), true);
      _builder_13.append(_returnDomain_7);
      _builder_13.append("> ");
      String _name_12 = object.getName();
      _builder_13.append(_name_12);
      _builder_13.append(" = new n<>();");
      _builder_13.newLineIfNotEmpty();
      _builder_13.newLine();
      function.append(_builder_13);
    }
    return function.toString();
  }
  
  public String visit(final DerivedFunction object) {
    StringBuffer function = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//Funzione di tipo derived");
    _builder.newLine();
    function.append(_builder);
    Domain _domain = object.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof AbstractTd)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _returnDomain = this.returnDomain(object.getCodomain(), true);
        _builder_1.append(_returnDomain);
        _builder_1.append(" ");
        String _name = object.getName();
        _builder_1.append(_name);
        _builder_1.append(";");
        _builder_1.newLineIfNotEmpty();
        function.append(_builder_1);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof ProductDomain)) {
          StringConcatenation _builder_2 = new StringConcatenation();
          String _visit = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
          _builder_2.append(_visit);
          _builder_2.append(" ");
          String _name_1 = object.getName();
          _builder_2.append(_name_1);
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          function.append(_builder_2);
        } else {
          Domain _codomain_2 = object.getCodomain();
          if ((_codomain_2 instanceof SequenceDomain)) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("List");
            String _visit_1 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_3.append(_visit_1);
            _builder_3.append(" ");
            String _name_2 = object.getName();
            _builder_3.append(_name_2);
            _builder_3.append(" = new ArrayList");
            String _visit_2 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
            _builder_3.append(_visit_2);
            _builder_3.append("();");
            _builder_3.newLineIfNotEmpty();
            function.append(_builder_3);
          } else {
            Domain _codomain_3 = object.getCodomain();
            if ((_codomain_3 instanceof PowersetDomain)) {
              StringConcatenation _builder_4 = new StringConcatenation();
              _builder_4.append("Set");
              String _visit_3 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_4.append(_visit_3);
              _builder_4.append(" ");
              String _name_3 = object.getName();
              _builder_4.append(_name_3);
              _builder_4.append(" = new HashSet");
              String _visit_4 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
              _builder_4.append(_visit_4);
              _builder_4.append("();");
              _builder_4.newLineIfNotEmpty();
              function.append(_builder_4);
            } else {
              Domain _codomain_4 = object.getCodomain();
              if ((_codomain_4 instanceof BagDomain)) {
                StringConcatenation _builder_5 = new StringConcatenation();
                _builder_5.append("Bag");
                String _visit_5 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_5.append(_visit_5);
                _builder_5.append(" ");
                String _name_4 = object.getName();
                _builder_5.append(_name_4);
                _builder_5.append(" = new HashBag");
                String _visit_6 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                _builder_5.append(_visit_6);
                _builder_5.append("();");
                _builder_5.newLineIfNotEmpty();
                function.append(_builder_5);
              } else {
                Domain _codomain_5 = object.getCodomain();
                if ((_codomain_5 instanceof MapDomain)) {
                  StringConcatenation _builder_6 = new StringConcatenation();
                  _builder_6.append("Map");
                  String _visit_7 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                  _builder_6.append(_visit_7);
                  _builder_6.append(" ");
                  String _name_5 = object.getName();
                  _builder_6.append(_name_5);
                  _builder_6.append(" = new HashMap");
                  String _visit_8 = new DomainToJavaSigDef(this.res).visit(object.getCodomain());
                  _builder_6.append(_visit_8);
                  _builder_6.append("();");
                  _builder_6.newLineIfNotEmpty();
                  function.append(_builder_6);
                } else {
                  StringConcatenation _builder_7 = new StringConcatenation();
                  _builder_7.append("abstract ");
                  String _returnDomain_1 = this.returnDomain(object.getCodomain(), false);
                  _builder_7.append(_returnDomain_1);
                  _builder_7.append(" ");
                  String _name_6 = object.getName();
                  _builder_7.append(_name_6);
                  _builder_7.append("();");
                  _builder_7.newLineIfNotEmpty();
                  function.append(_builder_7);
                }
              }
            }
          }
        }
      }
    } else {
      Domain _domain_1 = object.getDomain();
      if ((_domain_1 instanceof ProductDomain)) {
        StringConcatenation _builder_8 = new StringConcatenation();
        _builder_8.append("abstract ");
        String _returnDomain_2 = this.returnDomain(object.getCodomain(), true);
        _builder_8.append(_returnDomain_2);
        _builder_8.append(" ");
        String _name_7 = object.getName();
        _builder_8.append(_name_7);
        _builder_8.append(" (");
        Domain _domain_2 = object.getDomain();
        String _adaptProdDomain = this.adaptProdDomain(((ProductDomain) _domain_2), object.getName(), true);
        _builder_8.append(_adaptProdDomain);
        _builder_8.append(");");
        _builder_8.newLineIfNotEmpty();
        function.append(_builder_8);
      } else {
        StringConcatenation _builder_9 = new StringConcatenation();
        _builder_9.append("abstract ");
        String _returnDomain_3 = this.returnDomain(object.getCodomain(), true);
        _builder_9.append(_returnDomain_3);
        _builder_9.append(" ");
        String _name_8 = object.getName();
        _builder_9.append(_name_8);
        _builder_9.append(" (");
        String _returnParamDefinition = this.returnParamDefinition(object.getDomain(), object.getName(), true);
        _builder_9.append(_returnParamDefinition);
        _builder_9.append(");");
        _builder_9.newLineIfNotEmpty();
        function.append(_builder_9);
      }
    }
    return function.toString();
  }
}
