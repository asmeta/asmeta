package org.asmeta.asm2java.evosuite;

import asmeta.definitions.DerivedFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import org.asmeta.asm2java.translator.FunctionToJavaSig;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class FunctionToJavaEvosuiteSig extends FunctionToJavaSig {
  public FunctionToJavaEvosuiteSig(final Asm resource) {
    super(resource);
  }

  /**
   * Create an instance of the {@code DomainToJavaEvosuiteSigDef} object.
   */
  @Override
  public DomainToJavaEvosuiteSigDef createDomainSigDef(final Asm resource) {
    return new DomainToJavaEvosuiteSigDef(resource);
  }

  /**
   * Create an instance of the {@code ToString} object.
   */
  @Override
  public ToStringEvosuite createToString(final Asm resource) {
    return new ToStringEvosuite(resource);
  }

  /**
   * Method to build static function.
   * Calls DomainToJavaEvosuiteSigDef instead of DomainToJavaSigDef.
   */
  @Override
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
        _builder_1.append("private static ");
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
          _builder_2.append("private static ");
          String _visit = this.createDomainSigDef(this.res).visit(object.getCodomain());
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
            _builder_3.append("private static List");
            String _visit_1 = this.createDomainSigDef(this.res).visit(object.getCodomain());
            _builder_3.append(_visit_1);
            _builder_3.append(" ");
            String _name_2 = object.getName();
            _builder_3.append(_name_2);
            _builder_3.append(" = new ArrayList");
            String _visit_2 = this.createDomainSigDef(this.res).visit(object.getCodomain());
            _builder_3.append(_visit_2);
            _builder_3.append("();");
            _builder_3.newLineIfNotEmpty();
            function.append(_builder_3);
          } else {
            Domain _codomain_3 = object.getCodomain();
            if ((_codomain_3 instanceof PowersetDomain)) {
              StringConcatenation _builder_4 = new StringConcatenation();
              _builder_4.append("private static Set");
              String _visit_3 = this.createDomainSigDef(this.res).visit(object.getCodomain());
              _builder_4.append(_visit_3);
              _builder_4.append(" ");
              String _name_3 = object.getName();
              _builder_4.append(_name_3);
              _builder_4.append(" = new HashSet");
              String _visit_4 = this.createDomainSigDef(this.res).visit(object.getCodomain());
              _builder_4.append(_visit_4);
              _builder_4.append("();");
              _builder_4.newLineIfNotEmpty();
              function.append(_builder_4);
            } else {
              Domain _codomain_4 = object.getCodomain();
              if ((_codomain_4 instanceof BagDomain)) {
                StringConcatenation _builder_5 = new StringConcatenation();
                _builder_5.append("private static Bag");
                String _visit_5 = this.createDomainSigDef(this.res).visit(object.getCodomain());
                _builder_5.append(_visit_5);
                _builder_5.append(" ");
                String _name_4 = object.getName();
                _builder_5.append(_name_4);
                _builder_5.append(" = new HashBag");
                String _visit_6 = this.createDomainSigDef(this.res).visit(object.getCodomain());
                _builder_5.append(_visit_6);
                _builder_5.append("();");
                _builder_5.newLineIfNotEmpty();
                function.append(_builder_5);
              } else {
                Domain _codomain_5 = object.getCodomain();
                if ((_codomain_5 instanceof MapDomain)) {
                  StringConcatenation _builder_6 = new StringConcatenation();
                  _builder_6.append("private static Map");
                  String _visit_7 = this.createDomainSigDef(this.res).visit(object.getCodomain());
                  _builder_6.append(_visit_7);
                  _builder_6.append(" ");
                  String _name_5 = object.getName();
                  _builder_6.append(_name_5);
                  _builder_6.append(" = new HashMap");
                  String _visit_8 = this.createDomainSigDef(this.res).visit(object.getCodomain());
                  _builder_6.append(_visit_8);
                  _builder_6.append("();");
                  _builder_6.newLineIfNotEmpty();
                  function.append(_builder_6);
                } else {
                }
              }
            }
          }
        }
      }
    }
    return function.toString();
  }

  @Override
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
          String _visit = this.createDomainSigDef(this.res).visit(object.getCodomain());
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
            String _visit_1 = this.createDomainSigDef(this.res).visit(object.getCodomain());
            _builder_3.append(_visit_1);
            _builder_3.append(" ");
            String _name_2 = object.getName();
            _builder_3.append(_name_2);
            _builder_3.append(" = new ArrayList");
            String _visit_2 = this.createDomainSigDef(this.res).visit(object.getCodomain());
            _builder_3.append(_visit_2);
            _builder_3.append("();");
            _builder_3.newLineIfNotEmpty();
            function.append(_builder_3);
          } else {
            Domain _codomain_3 = object.getCodomain();
            if ((_codomain_3 instanceof PowersetDomain)) {
              StringConcatenation _builder_4 = new StringConcatenation();
              _builder_4.append("Set");
              String _visit_3 = this.createDomainSigDef(this.res).visit(object.getCodomain());
              _builder_4.append(_visit_3);
              _builder_4.append(" ");
              String _name_3 = object.getName();
              _builder_4.append(_name_3);
              _builder_4.append(" = new HashSet");
              String _visit_4 = this.createDomainSigDef(this.res).visit(object.getCodomain());
              _builder_4.append(_visit_4);
              _builder_4.append("();");
              _builder_4.newLineIfNotEmpty();
              function.append(_builder_4);
            } else {
              Domain _codomain_4 = object.getCodomain();
              if ((_codomain_4 instanceof BagDomain)) {
                StringConcatenation _builder_5 = new StringConcatenation();
                _builder_5.append("Bag");
                String _visit_5 = this.createDomainSigDef(this.res).visit(object.getCodomain());
                _builder_5.append(_visit_5);
                _builder_5.append(" ");
                String _name_4 = object.getName();
                _builder_5.append(_name_4);
                _builder_5.append(" = new HashBag");
                String _visit_6 = this.createDomainSigDef(this.res).visit(object.getCodomain());
                _builder_5.append(_visit_6);
                _builder_5.append("();");
                _builder_5.newLineIfNotEmpty();
                function.append(_builder_5);
              } else {
                Domain _codomain_5 = object.getCodomain();
                if ((_codomain_5 instanceof MapDomain)) {
                  StringConcatenation _builder_6 = new StringConcatenation();
                  _builder_6.append("Map");
                  String _visit_7 = this.createDomainSigDef(this.res).visit(object.getCodomain());
                  _builder_6.append(_visit_7);
                  _builder_6.append(" ");
                  String _name_5 = object.getName();
                  _builder_6.append(_name_5);
                  _builder_6.append(" = new HashMap");
                  String _visit_8 = this.createDomainSigDef(this.res).visit(object.getCodomain());
                  _builder_6.append(_visit_8);
                  _builder_6.append("();");
                  _builder_6.newLineIfNotEmpty();
                  function.append(_builder_6);
                }
              }
            }
          }
        }
      }
    }
    return function.toString();
  }
}
