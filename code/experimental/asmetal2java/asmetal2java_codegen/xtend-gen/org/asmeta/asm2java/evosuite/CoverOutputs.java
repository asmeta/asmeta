package org.asmeta.asm2java.evosuite;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import java.util.List;
import org.asmeta.asm2java.ToString;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class CoverOutputs {
  public static void coverBranches(final Asm asm, final StringBuffer sb) {
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof MonitoredFunction) || (fd instanceof ControlledFunction))) {
        Domain _domain = fd.getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          StringBuffer _append = sb.append("\t");
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("private void cover_");
          String _name = fd.getName();
          _builder.append(_name);
          _builder.append("(){");
          _append.append(_builder);
          Domain _codomain = fd.getCodomain();
          if ((_codomain instanceof EnumTd)) {
            sb.append(System.lineSeparator());
            StringBuffer _append_1 = sb.append("\t\t");
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("switch(this.get_");
            String _name_1 = fd.getName();
            _builder_1.append(_name_1);
            _builder_1.append("()){");
            _append_1.append(_builder_1);
            EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
            for (final Domain dd : _domain_1) {
              boolean _equals = dd.equals(fd.getCodomain());
              if (_equals) {
                if ((dd instanceof EnumTd)) {
                  for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                    {
                      String symbol = new ToString(asm).visit(((EnumTd)dd).getElement().get(i));
                      sb.append(System.lineSeparator());
                      StringBuffer _append_2 = sb.append("\t\t\t");
                      StringConcatenation _builder_2 = new StringConcatenation();
                      _builder_2.append("case ");
                      _builder_2.append(symbol);
                      _builder_2.append(" :");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t\t\t\t\t\t\t");
                      _builder_2.append("System.out.println(\"Branch ");
                      String _name_2 = fd.getCodomain().getName();
                      _builder_2.append(_name_2, "\t\t\t\t\t\t\t");
                      _builder_2.append(" ");
                      _builder_2.append(symbol, "\t\t\t\t\t\t\t");
                      _builder_2.append(" covered\");");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t\t\t\t\t\t\t");
                      _builder_2.append("// Branch ");
                      String _name_3 = fd.getCodomain().getName();
                      _builder_2.append(_name_3, "\t\t\t\t\t\t\t");
                      _builder_2.append(" ");
                      _builder_2.append(symbol, "\t\t\t\t\t\t\t");
                      _builder_2.append(" covered");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t\t\t\t\t\t\t");
                      _builder_2.append("break;");
                      _append_2.append(_builder_2);
                    }
                  }
                }
              }
            }
            sb.append(System.lineSeparator());
            sb.append("\t\t\t");
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("}");
            sb.append(_builder_2);
          } else {
            sb.append(System.lineSeparator());
            StringBuffer _append_2 = sb.append("\t\t");
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("this.get_");
            String _name_2 = fd.getName();
            _builder_3.append(_name_2);
            _builder_3.append("();");
            _append_2.append(_builder_3);
            sb.append(System.lineSeparator());
            StringBuffer _append_3 = sb.append("\t\t");
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append("//1 Branch covered");
            _append_3.append(_builder_4);
          }
          sb.append(System.lineSeparator());
          StringBuffer _append_4 = sb.append("\t");
          StringConcatenation _builder_5 = new StringConcatenation();
          _builder_5.append("}");
          _append_4.append(_builder_5);
          sb.append(System.lineSeparator());
          sb.append(System.lineSeparator());
        } else {
          EList<Domain> _domain_2 = asm.getHeaderSection().getSignature().getDomain();
          for (final Domain dd_1 : _domain_2) {
            boolean _equals_1 = dd_1.equals(fd.getDomain());
            if (_equals_1) {
              StringBuffer _append_5 = sb.append("\t");
              StringConcatenation _builder_6 = new StringConcatenation();
              _builder_6.append("private void cover_");
              String _name_3 = fd.getName();
              _builder_6.append(_name_3);
              _builder_6.append("(){");
              _append_5.append(_builder_6);
              if ((dd_1 instanceof EnumTd)) {
                for (int i = 0; (i < ((EnumTd)dd_1).getElement().size()); i++) {
                  {
                    String symbol = new ToString(asm).visit(((EnumTd)dd_1).getElement().get(i));
                    sb.append(System.lineSeparator());
                    StringBuffer _append_6 = sb.append("\t\t");
                    StringConcatenation _builder_7 = new StringConcatenation();
                    _builder_7.append("this.get_");
                    String _name_4 = fd.getName();
                    _builder_7.append(_name_4);
                    _builder_7.append("_");
                    _builder_7.append(symbol);
                    _builder_7.append("();");
                    _append_6.append(_builder_7);
                  }
                }
                sb.append(System.lineSeparator());
                StringBuffer _append_6 = sb.append("\t\t");
                StringConcatenation _builder_7 = new StringConcatenation();
                _builder_7.append("// ");
                int _size = ((EnumTd)dd_1).getElement().size();
                _builder_7.append(_size);
                _builder_7.append(" Branch covered");
                _append_6.append(_builder_7);
              } else {
                if ((dd_1 instanceof AbstractTd)) {
                  int i = 0;
                  EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
                  for (final Function sf : _function_1) {
                    if ((sf instanceof StaticFunction)) {
                      if ((((StaticFunction)sf).getCodomain().equals(dd_1) && (((StaticFunction)sf).getDomain() == null))) {
                        Domain sd = fd.getCodomain();
                        if ((sd instanceof EnumTd)) {
                          sb.append(System.lineSeparator());
                          StringBuffer _append_7 = sb.append("\t\t");
                          StringConcatenation _builder_8 = new StringConcatenation();
                          _builder_8.append("switch(this.esecuzione.");
                          String _name_4 = fd.getName();
                          _builder_8.append(_name_4);
                          _builder_8.append(".get(");
                          _builder_8.newLineIfNotEmpty();
                          _builder_8.append("\t\t\t\t\t\t\t\t\t\t\t\t");
                          _builder_8.append("this.esecuzione.");
                          String _name_5 = fd.getDomain().getName();
                          _builder_8.append(_name_5, "\t\t\t\t\t\t\t\t\t\t\t\t");
                          _builder_8.append("_Class.get(");
                          _builder_8.newLineIfNotEmpty();
                          _builder_8.append("\t\t\t\t\t\t\t\t\t\t\t\t");
                          _builder_8.append("this.esecuzione.");
                          String _name_6 = fd.getDomain().getName();
                          _builder_8.append(_name_6, "\t\t\t\t\t\t\t\t\t\t\t\t");
                          _builder_8.append("_elemsList.indexOf(\"");
                          String _name_7 = ((StaticFunction)sf).getName();
                          _builder_8.append(_name_7, "\t\t\t\t\t\t\t\t\t\t\t\t");
                          _builder_8.append("\")))){");
                          _append_7.append(_builder_8);
                          for (int j = 0; (j < ((EnumTd)sd).getElement().size()); j++) {
                            {
                              String symbol = new ToString(asm).visit(((EnumTd)sd).getElement().get(j));
                              sb.append(System.lineSeparator());
                              StringBuffer _append_8 = sb.append("\t\t\t");
                              StringConcatenation _builder_9 = new StringConcatenation();
                              _builder_9.append("case ");
                              _builder_9.append(symbol);
                              _builder_9.append(" :");
                              _builder_9.newLineIfNotEmpty();
                              _builder_9.append("\t\t\t\t\t\t\t\t\t\t\t\t");
                              _builder_9.append("System.out.println(\"Branch ");
                              String _name_8 = ((StaticFunction)sf).getName();
                              _builder_9.append(_name_8, "\t\t\t\t\t\t\t\t\t\t\t\t");
                              _builder_9.append(" ");
                              _builder_9.append(symbol, "\t\t\t\t\t\t\t\t\t\t\t\t");
                              _builder_9.append(" covered\");");
                              _builder_9.newLineIfNotEmpty();
                              _builder_9.append("\t\t\t\t\t\t\t\t\t\t\t\t");
                              _builder_9.append("// Branch ");
                              String _name_9 = ((StaticFunction)sf).getName();
                              _builder_9.append(_name_9, "\t\t\t\t\t\t\t\t\t\t\t\t");
                              _builder_9.append(" ");
                              _builder_9.append(symbol, "\t\t\t\t\t\t\t\t\t\t\t\t");
                              _builder_9.append(" covered");
                              _builder_9.newLineIfNotEmpty();
                              _builder_9.append("\t\t\t\t\t\t\t\t\t\t\t\t");
                              _builder_9.append("break;");
                              _append_8.append(_builder_9);
                              int _i = i;
                              i = (_i + 1);
                            }
                          }
                          sb.append(System.lineSeparator());
                          StringBuffer _append_8 = sb.append("\t");
                          StringConcatenation _builder_9 = new StringConcatenation();
                          _builder_9.append("}");
                          _append_8.append(_builder_9);
                        } else {
                          String symbol = ((StaticFunction)sf).getName();
                          sb.append(System.lineSeparator());
                          StringBuffer _append_9 = sb.append("\t\t");
                          StringConcatenation _builder_10 = new StringConcatenation();
                          _builder_10.append("this.get_");
                          String _name_8 = fd.getName();
                          _builder_10.append(_name_8);
                          _builder_10.append("_");
                          _builder_10.append(symbol);
                          _builder_10.append("();");
                          _append_9.append(_builder_10);
                          int _i = i;
                          i = (_i + 1);
                        }
                      }
                    }
                  }
                  sb.append(System.lineSeparator());
                  StringBuffer _append_10 = sb.append("\t\t");
                  StringConcatenation _builder_11 = new StringConcatenation();
                  _builder_11.append("//");
                  _builder_11.append(i);
                  _builder_11.append(" Branch covered");
                  _append_10.append(_builder_11);
                } else {
                  sb.append(System.lineSeparator());
                  StringBuffer _append_11 = sb.append("\t\t");
                  StringConcatenation _builder_12 = new StringConcatenation();
                  _builder_12.append("this.get_");
                  String _name_9 = fd.getName();
                  _builder_12.append(_name_9);
                  _builder_12.append("();");
                  _append_11.append(_builder_12);
                  sb.append(System.lineSeparator());
                  StringBuffer _append_12 = sb.append("\t\t");
                  StringConcatenation _builder_13 = new StringConcatenation();
                  _builder_13.append("//1 Branch covered");
                  _append_12.append(_builder_13);
                }
              }
              sb.append(System.lineSeparator());
              StringBuffer _append_13 = sb.append("\t");
              StringConcatenation _builder_14 = new StringConcatenation();
              _builder_14.append("}");
              _append_13.append(_builder_14);
              sb.append(System.lineSeparator());
              sb.append(System.lineSeparator());
            }
          }
        }
      }
    }
  }

  public static void coverFunctions(final Asm asm, final StringBuffer sb, final boolean monitored) {
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof MonitoredFunction) && (monitored == true))) {
        sb.append(System.lineSeparator());
        StringBuffer _append = sb.append("\t\t");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("cover_");
        String _name = fd.getName();
        _builder.append(_name);
        _builder.append("();");
        _append.append(_builder);
      }
    }
    EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd_1 : _function_1) {
      if (((fd_1 instanceof ControlledFunction) && (monitored == false))) {
        sb.append(System.lineSeparator());
        StringBuffer _append_1 = sb.append("\t\t");
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("cover_");
        String _name_1 = fd_1.getName();
        _builder_1.append(_name_1);
        _builder_1.append("();");
        _append_1.append(_builder_1);
      }
    }
  }

  public static StringBuffer ifFinalState(final Asm asm, final StringBuffer sb, final String[] finalStateConditions) {
    StringBuffer _xifexpression = null;
    if (((finalStateConditions != null) && (!((List<String>)Conversions.doWrapArray(finalStateConditions)).isEmpty()))) {
      StringBuffer _xblockexpression = null;
      {
        StringBuffer _append = sb.append("\t\t\t\t");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/* final state condition */");
        _append.append(_builder);
        sb.append(System.lineSeparator());
        StringBuffer _append_1 = sb.append("\t\t\t\t");
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("if(isFinalState()){");
        _builder_1.newLine();
        _builder_1.append("\t\t\t\t\t\t");
        _builder_1.append("System.out.println(\"\\n<Stato finale>\");");
        _builder_1.newLine();
        _builder_1.append("\t\t\t\t");
        _builder_1.append("}");
        _builder_1.newLine();
        _builder_1.append("\t\t\t\t");
        _builder_1.append("else");
        _append_1.append(_builder_1);
        _xblockexpression = sb.append(System.lineSeparator());
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }

  public static StringBuffer setIsFinalState(final Asm asm, final StringBuffer sb, final String[] finalStateConditions) {
    StringBuffer _xifexpression = null;
    if (((finalStateConditions != null) && (!((List<String>)Conversions.doWrapArray(finalStateConditions)).isEmpty()))) {
      StringBuffer _xblockexpression = null;
      {
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        StringBuffer _append = sb.append("\t");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("// final state condition");
        _append.append(_builder);
        sb.append(System.lineSeparator());
        StringBuffer _append_1 = sb.append("\t");
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("public boolean isFinalState(){");
        _append_1.append(_builder_1);
        sb.append(System.lineSeparator());
        StringBuffer _append_2 = sb.append("\t\t");
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("return");
        _append_2.append(_builder_2);
        int numberOfConditionsExpected = 0;
        int numberOfConditionsActual = 0;
        StringBuffer ss = new StringBuffer();
        for (final String condition : finalStateConditions) {
          {
            int _numberOfConditionsExpected = numberOfConditionsExpected;
            numberOfConditionsExpected = (_numberOfConditionsExpected + 1);
            final String cond_name = condition.replaceAll("^\\s*(\\w+)\\s*.*$", "$1");
            String cond_value = condition.replaceAll("^\\s*\\w+\\s*(.*)$", "$1");
            boolean _equals = cond_name.toLowerCase().equals("stato");
            if (_equals) {
              ss.append(System.lineSeparator());
              StringBuffer _append_3 = ss.append("\t\t\t");
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("this.stato ");
              _builder_3.append(cond_value);
              _builder_3.append(" &&");
              _append_3.append(_builder_3);
              int _numberOfConditionsActual = numberOfConditionsActual;
              numberOfConditionsActual = (_numberOfConditionsActual + 1);
            } else {
              boolean _matches = cond_value.matches("\\d+");
              boolean _not = (!_matches);
              if (_not) {
                String cond_value_operators = cond_value.replaceAll("^([><=!]+).*", "$1");
                StringConcatenation _builder_4 = new StringConcatenation();
                String _name = asm.getName();
                _builder_4.append(_name);
                _builder_4.append(".");
                String _replaceAll = cond_value.replaceAll("^([><=!]+)(.*)", "$2");
                _builder_4.append(_replaceAll);
                cond_value = _builder_4.toString();
                cond_value = cond_value_operators.concat(cond_value);
              }
              EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
              for (final Function fd : _function) {
                if (((fd instanceof ControlledFunction) && fd.getName().equals(cond_name))) {
                  ss.append(System.lineSeparator());
                  StringBuffer _append_4 = ss.append("\t\t\t");
                  StringConcatenation _builder_5 = new StringConcatenation();
                  _builder_5.append("this.get_");
                  String _name_1 = fd.getName();
                  _builder_5.append(_name_1);
                  _builder_5.append("() ");
                  _builder_5.append(cond_value);
                  _builder_5.append(" &&");
                  _append_4.append(_builder_5);
                  int _numberOfConditionsActual_1 = numberOfConditionsActual;
                  numberOfConditionsActual = (_numberOfConditionsActual_1 + 1);
                }
              }
            }
          }
        }
        if ((numberOfConditionsActual == 0)) {
          sb.append(" true;");
          sb.append(System.lineSeparator());
          StringBuffer _append_3 = sb.append("\t");
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("// ERROR - NO Conditions found");
          _append_3.append(_builder_3);
        } else {
          if ((numberOfConditionsActual == numberOfConditionsExpected)) {
            sb.append(ss.toString());
            int _length = sb.length();
            int _minus = (_length - 3);
            sb.setLength(_minus);
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append(";");
            sb.append(_builder_4);
          } else {
            sb.append(ss.toString());
            int _length_1 = sb.length();
            int _minus_1 = (_length_1 - 3);
            sb.setLength(_minus_1);
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append(";");
            sb.append(_builder_5);
            sb.append(System.lineSeparator());
            StringBuffer _append_4 = sb.append("\t");
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append("// ERROR - ");
            _builder_6.append((numberOfConditionsExpected - numberOfConditionsActual));
            _builder_6.append(" Conditions not generated");
            _append_4.append(_builder_6);
          }
        }
        sb.append(System.lineSeparator());
        StringBuffer _append_5 = sb.append("\t");
        StringConcatenation _builder_7 = new StringConcatenation();
        _builder_7.append("}");
        _append_5.append(_builder_7);
        _xblockexpression = sb.append(System.lineSeparator());
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
}
