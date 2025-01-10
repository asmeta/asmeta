package org.asmeta.asm2java.evosuite;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Contains the methods to cover the outputs of the abstract state machine (ASM),
 * by output we mean the states of the enumerative domains.
 */
@SuppressWarnings("all")
public class CoverOutputs {
  /**
   * Create a method for the function to cover all its outputs
   * 
   * @param asm the Asm specification
   */
  public static String coverOutputBranches(final Asm asm) {
    final StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof MonitoredFunction) || (fd instanceof ControlledFunction))) {
        Domain _codomain = fd.getCodomain();
        if ((_codomain instanceof EnumTd)) {
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
            sb.append(System.lineSeparator());
            StringBuffer _append_1 = sb.append("\t\t");
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("if(this.get_");
            String _name_1 = fd.getName();
            _builder_1.append(_name_1);
            _builder_1.append("() == null){");
            _append_1.append(_builder_1);
            sb.append(System.lineSeparator());
            StringBuffer _append_2 = sb.append("\t\t\t");
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("return;");
            _append_2.append(_builder_2);
            sb.append(System.lineSeparator());
            StringBuffer _append_3 = sb.append("\t\t");
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("}");
            _append_3.append(_builder_3);
            sb.append(System.lineSeparator());
            StringBuffer _append_4 = sb.append("\t\t");
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append("switch(this.get_");
            String _name_2 = fd.getName();
            _builder_4.append(_name_2);
            _builder_4.append("()){");
            _append_4.append(_builder_4);
            EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
            for (final Domain dd : _domain_1) {
              boolean _equals = dd.equals(fd.getCodomain());
              if (_equals) {
                if ((dd instanceof EnumTd)) {
                  for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                    {
                      String symbol = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)dd).getElement().get(i));
                      sb.append(System.lineSeparator());
                      StringBuffer _append_5 = sb.append("\t\t\t");
                      StringConcatenation _builder_5 = new StringConcatenation();
                      _builder_5.append("case ");
                      _builder_5.append(symbol);
                      _builder_5.append(" :");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("\t\t\t\t\t\t\t\t\t\t");
                      _builder_5.append("System.out.println(\"Branch ");
                      String _name_3 = fd.getCodomain().getName();
                      _builder_5.append(_name_3, "\t\t\t\t\t\t\t\t\t\t");
                      _builder_5.append(" ");
                      _builder_5.append(symbol, "\t\t\t\t\t\t\t\t\t\t");
                      _builder_5.append(" covered\");");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("\t\t\t\t\t\t\t\t\t\t");
                      _builder_5.append("break;");
                      _append_5.append(_builder_5);
                      sb.append(System.lineSeparator());
                    }
                  }
                }
              }
            }
            sb.append("\t\t\t");
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append("}");
            sb.append(_builder_5);
            sb.append(System.lineSeparator());
            sb.append("\t\t");
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append("}");
            sb.append(_builder_6);
            sb.append(System.lineSeparator());
          } else {
            EList<Domain> _domain_2 = asm.getHeaderSection().getSignature().getDomain();
            for (final Domain dd_1 : _domain_2) {
              boolean _equals_1 = dd_1.equals(fd.getDomain());
              if (_equals_1) {
                if ((dd_1 instanceof EnumTd)) {
                  for (int i = 0; (i < ((EnumTd)dd_1).getElement().size()); i++) {
                    {
                      String symbol = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)dd_1).getElement().get(i));
                      StringBuffer _append_5 = sb.append("\t");
                      StringConcatenation _builder_7 = new StringConcatenation();
                      _builder_7.append("private void cover_");
                      String _name_3 = fd.getName();
                      _builder_7.append(_name_3);
                      _builder_7.append("_");
                      _builder_7.append(symbol);
                      _builder_7.append("(){");
                      _append_5.append(_builder_7);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_6 = sb.append("\t\t");
                      StringConcatenation _builder_8 = new StringConcatenation();
                      _builder_8.append("if(this.get_");
                      String _name_4 = fd.getName();
                      _builder_8.append(_name_4);
                      _builder_8.append("_");
                      _builder_8.append(symbol);
                      _builder_8.append("() == null){");
                      _append_6.append(_builder_8);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_7 = sb.append("\t\t\t");
                      StringConcatenation _builder_9 = new StringConcatenation();
                      _builder_9.append("return;");
                      _append_7.append(_builder_9);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_8 = sb.append("\t\t");
                      StringConcatenation _builder_10 = new StringConcatenation();
                      _builder_10.append("}");
                      _append_8.append(_builder_10);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_9 = sb.append("\t\t");
                      StringConcatenation _builder_11 = new StringConcatenation();
                      _builder_11.append("switch(this.get_");
                      String _name_5 = fd.getName();
                      _builder_11.append(_name_5);
                      _builder_11.append("_");
                      _builder_11.append(symbol);
                      _builder_11.append("()){");
                      _append_9.append(_builder_11);
                      EList<Domain> _domain_3 = asm.getHeaderSection().getSignature().getDomain();
                      for (final Domain ddd : _domain_3) {
                        boolean _equals_2 = ddd.equals(fd.getCodomain());
                        if (_equals_2) {
                          if ((ddd instanceof EnumTd)) {
                            for (int j = 0; (j < ((EnumTd)ddd).getElement().size()); j++) {
                              {
                                String symbolD = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)ddd).getElement().get(j));
                                sb.append(System.lineSeparator());
                                StringBuffer _append_10 = sb.append("\t\t\t");
                                StringConcatenation _builder_12 = new StringConcatenation();
                                _builder_12.append("case ");
                                _builder_12.append(symbolD);
                                _builder_12.append(" :");
                                _builder_12.newLineIfNotEmpty();
                                _builder_12.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
                                _builder_12.append("System.out.println(\"Branch ");
                                String _name_6 = fd.getCodomain().getName();
                                _builder_12.append(_name_6, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
                                _builder_12.append(" ");
                                _builder_12.append(symbolD, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
                                _builder_12.append(" covered\");");
                                _builder_12.newLineIfNotEmpty();
                                _builder_12.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
                                _builder_12.append("break;");
                                _append_10.append(_builder_12);
                                sb.append(System.lineSeparator());
                              }
                            }
                          }
                        }
                      }
                      sb.append("\t\t\t");
                      StringConcatenation _builder_12 = new StringConcatenation();
                      _builder_12.append("}");
                      sb.append(_builder_12);
                      sb.append(System.lineSeparator());
                      sb.append("\t\t");
                      StringConcatenation _builder_13 = new StringConcatenation();
                      _builder_13.append("}");
                      sb.append(_builder_13);
                      sb.append(System.lineSeparator());
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return sb.toString();
  }

  /**
   * Create a method that calls all the cover output functions
   * 
   * @param asm the Asm specification
   */
  public static String coverOutputs(final Asm asm) {
    final StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Invokes all output coverage functions.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* <p>");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* To achieve complete output coverage, only the functions related to the enum need to be invoked.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* </p>");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    sb.append(_builder);
    StringBuffer _append = sb.append("\t");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("private void coverOutputs(){");
    _append.append(_builder_1);
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof MonitoredFunction) || (fd instanceof ControlledFunction))) {
        Domain _codomain = fd.getCodomain();
        if ((_codomain instanceof EnumTd)) {
          Domain _domain = fd.getDomain();
          boolean _tripleEquals = (_domain == null);
          if (_tripleEquals) {
            sb.append(System.lineSeparator());
            StringBuffer _append_1 = sb.append("\t\t");
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("cover_");
            String _name = fd.getName();
            _builder_2.append(_name);
            _builder_2.append("();");
            _append_1.append(_builder_2);
          } else {
            EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
            for (final Domain dd : _domain_1) {
              boolean _equals = dd.equals(fd.getDomain());
              if (_equals) {
                if ((dd instanceof EnumTd)) {
                  for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                    {
                      String symbol = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)dd).getElement().get(i));
                      sb.append(System.lineSeparator());
                      StringBuffer _append_2 = sb.append("\t\t");
                      StringConcatenation _builder_3 = new StringConcatenation();
                      _builder_3.append("cover_");
                      String _name_1 = fd.getName();
                      _builder_3.append(_name_1);
                      _builder_3.append("_");
                      _builder_3.append(symbol);
                      _builder_3.append("();");
                      _append_2.append(_builder_3);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    StringBuffer _append_2 = sb.append("\t");
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("}");
    _append_2.append(_builder_3);
    return sb.toString();
  }

  /**
   * Monitored functions getters (private, only for cover outputs)
   * 
   * @param asm the Asm specification
   */
  public static String monitoredGetter(final Asm asm) {
    final StringBuffer sb = new StringBuffer();
    String asmName = asm.getName();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          Domain _codomain = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain instanceof EnumTd)) {
            sb.append(System.lineSeparator());
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("/**");
            _builder.newLine();
            _builder.append("* Get the monitored function {@code ");
            String _name = ((MonitoredFunction)fd).getName();
            _builder.append(_name);
            _builder.append("}.");
            _builder.newLineIfNotEmpty();
            _builder.append("*");
            _builder.newLine();
            _builder.append("* @return the selected {@code ");
            _builder.append(asmName);
            _builder.append(".");
            String _name_1 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder.append(_name_1);
            _builder.append(" ");
            String _name_2 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_2);
            _builder.append("} ");
            String _name_3 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_3);
            _builder.newLineIfNotEmpty();
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("private ");
            _builder.append(asmName, "\t");
            _builder.append(".");
            String _name_4 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder.append(_name_4, "\t");
            _builder.append(" get_");
            String _name_5 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_5, "\t");
            _builder.append("(){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("return this.execution.");
            String _name_6 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_6, "\t\t");
            _builder.append(".get();");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            sb.append(_builder);
            sb.append(System.lineSeparator());
          }
        } else {
          Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain_1 instanceof EnumTd)) {
            EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
            for (final Domain dd : _domain_1) {
              boolean _equals = dd.equals(((MonitoredFunction)fd).getDomain());
              if (_equals) {
                if ((dd instanceof EnumTd)) {
                  for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                    {
                      String symbol = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)dd).getElement().get(i));
                      sb.append(System.lineSeparator());
                      StringConcatenation _builder_1 = new StringConcatenation();
                      _builder_1.append("/**");
                      _builder_1.newLine();
                      _builder_1.append("* Get the monitored function {@code ");
                      String _name_7 = ((MonitoredFunction)fd).getName();
                      _builder_1.append(_name_7);
                      _builder_1.append("_");
                      _builder_1.append(symbol);
                      _builder_1.append("}.");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("*");
                      _builder_1.newLine();
                      _builder_1.append("* @return the selected {@code ");
                      _builder_1.append(asmName);
                      _builder_1.append(".");
                      String _name_8 = ((MonitoredFunction)fd).getCodomain().getName();
                      _builder_1.append(_name_8);
                      _builder_1.append(" ");
                      String _name_9 = ((MonitoredFunction)fd).getName();
                      _builder_1.append(_name_9);
                      _builder_1.append("_");
                      _builder_1.append(symbol);
                      _builder_1.append("} ");
                      String _name_10 = ((MonitoredFunction)fd).getName();
                      _builder_1.append(_name_10);
                      _builder_1.append("_");
                      _builder_1.append(symbol);
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("*/");
                      _builder_1.newLine();
                      _builder_1.append("\t");
                      _builder_1.append("private ");
                      _builder_1.append(asmName, "\t");
                      _builder_1.append(".");
                      String _name_11 = ((MonitoredFunction)fd).getCodomain().getName();
                      _builder_1.append(_name_11, "\t");
                      _builder_1.append(" get_");
                      String _name_12 = ((MonitoredFunction)fd).getName();
                      _builder_1.append(_name_12, "\t");
                      _builder_1.append("_");
                      _builder_1.append(symbol, "\t");
                      _builder_1.append("(){");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("\t\t");
                      _builder_1.append("return this.execution.");
                      String _name_13 = ((MonitoredFunction)fd).getName();
                      _builder_1.append(_name_13, "\t\t");
                      _builder_1.append(".get(");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("\t\t\t");
                      _builder_1.append("this.execution.");
                      String _name_14 = ((MonitoredFunction)fd).getDomain().getName();
                      _builder_1.append(_name_14, "\t\t\t");
                      _builder_1.append("_elemsList.get(");
                      _builder_1.append(i, "\t\t\t");
                      _builder_1.append("));");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("\t");
                      _builder_1.append("}");
                      _builder_1.newLine();
                      sb.append(_builder_1);
                      sb.append(System.lineSeparator());
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return sb.toString();
  }
}
