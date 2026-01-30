package asmeta.asmetal2java.codegen.evosuite;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

import asmeta.asmetal2java.codegen.translator.DomainToJavaString;
import asmeta.asmetal2java.codegen.translator.TermToJava;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;

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
                      String elem = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)dd_1).getElement().get(i));
                      sb.append(AsmMethodsUtil.genCoverOutputMethod(fd, elem, asm));
                    }
                  }
                } else {
                  if ((dd_1 instanceof AbstractTd)) {
                    EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
                    for (final Function sf : _function_1) {
                      if ((sf instanceof StaticFunction)) {
                        if ((sf.getCodomain().equals(dd_1) && (sf.getDomain() == null))) {
                          String elem = sf.getName();
                          sb.append(AsmMethodsUtil.genCoverOutputMethod(fd, elem, asm));
                        }
                      }
                    }
                  } else {
                    if ((dd_1 instanceof ConcreteDomain)) {
                      EList<Domain> _domain_3 = asm.getHeaderSection().getSignature().getDomain();
                      for (final Domain cd : _domain_3) {
                        if ((cd instanceof ConcreteDomain)) {
                          boolean _equals_2 = cd.getName().equals(fd.getDomain().getName());
                          if (_equals_2) {
                            final String elemsString = new TermToJava(asm).visit(((ConcreteDomain)cd).getDefinition().getBody());
                            final Function1<String, String> _function_2 = (String it) -> {
                              int _lastIndexOf = it.lastIndexOf(".");
                              int _plus = (_lastIndexOf + 1);
                              return it.substring(_plus);
                            };
                            final List<String> elems = ListExtensions.<String, String>map(((List<String>)Conversions.doWrapArray(elemsString.replace("(", "").replace(")", "").split(", "))), _function_2);
                            for (final String elem_1 : elems) {
                              sb.append(AsmMethodsUtil.genCoverOutputMethod(fd, elem_1, asm));
                            }
                          }
                        }
                      }
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
    _builder.append("* To achieve complete output coverage, only the functions that cover enum codomains need to be invoked.");
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
                      _builder_3.append("_fromDomain_");
                      _builder_3.append(symbol);
                      _builder_3.append("();");
                      _append_2.append(_builder_3);
                    }
                  }
                } else {
                  if ((dd instanceof AbstractTd)) {
                    EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
                    for (final Function sf : _function_1) {
                      if ((sf instanceof StaticFunction)) {
                        if ((sf.getCodomain().equals(dd) && (sf.getDomain() == null))) {
                          String symbol = sf.getName();
                          sb.append(System.lineSeparator());
                          StringBuffer _append_2 = sb.append("\t\t");
                          StringConcatenation _builder_3 = new StringConcatenation();
                          _builder_3.append("cover_");
                          String _name_1 = fd.getName();
                          _builder_3.append(_name_1);
                          _builder_3.append("_fromDomain_");
                          _builder_3.append(symbol);
                          _builder_3.append("();");
                          _append_2.append(_builder_3);
                        }
                      }
                    }
                  } else {
                    if ((dd instanceof ConcreteDomain)) {
                      EList<Domain> _domain_2 = asm.getHeaderSection().getSignature().getDomain();
                      for (final Domain cd : _domain_2) {
                        if ((cd instanceof ConcreteDomain)) {
                          boolean _equals_1 = cd.getName().equals(fd.getDomain().getName());
                          if (_equals_1) {
                            final String elemsString = new TermToJava(asm).visit(((ConcreteDomain)cd).getDefinition().getBody());
                            final Function1<String, String> _function_2 = (String it) -> {
                              int _lastIndexOf = it.lastIndexOf(".");
                              int _plus = (_lastIndexOf + 1);
                              return it.substring(_plus);
                            };
                            final List<String> elems = ListExtensions.<String, String>map(((List<String>)Conversions.doWrapArray(elemsString.replace("(", "").replace(")", "").split(", "))), _function_2);
                            for (final String elem : elems) {
                              {
                                sb.append(System.lineSeparator());
                                StringBuffer _append_3 = sb.append("\t\t");
                                StringConcatenation _builder_4 = new StringConcatenation();
                                _builder_4.append("cover_");
                                String _name_2 = fd.getName();
                                _builder_4.append(_name_2);
                                _builder_4.append("_fromDomain_");
                                _builder_4.append(elem);
                                _builder_4.append("();");
                                _append_3.append(_builder_4);
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    StringBuffer _append_3 = sb.append("\t");
    StringConcatenation _builder_4 = new StringConcatenation();
    _builder_4.append("}");
    _append_3.append(_builder_4);
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
        Domain _domain = fd.getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          Domain _codomain = fd.getCodomain();
          if ((_codomain instanceof EnumTd)) {
            sb.append(System.lineSeparator());
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("/**");
            _builder.newLine();
            _builder.append("* Get the monitored function {@code ");
            String _name = fd.getName();
            _builder.append(_name);
            _builder.append("}.");
            _builder.newLineIfNotEmpty();
            _builder.append("*");
            _builder.newLine();
            _builder.append("* @return the selected {@code ");
            _builder.append(asmName);
            _builder.append(".");
            String _name_1 = fd.getCodomain().getName();
            _builder.append(_name_1);
            _builder.append(" ");
            String _name_2 = fd.getName();
            _builder.append(_name_2);
            _builder.append("} ");
            String _name_3 = fd.getName();
            _builder.append(_name_3);
            _builder.newLineIfNotEmpty();
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("private ");
            _builder.append(asmName, "\t");
            _builder.append(".");
            String _name_4 = fd.getCodomain().getName();
            _builder.append(_name_4, "\t");
            _builder.append(" get_");
            String _name_5 = fd.getName();
            _builder.append(_name_5, "\t");
            _builder.append("(){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("return this.execution.");
            String _name_6 = fd.getName();
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
          Domain _codomain_1 = fd.getCodomain();
          if ((_codomain_1 instanceof EnumTd)) {
            EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
            for (final Domain dd : _domain_1) {
              boolean _equals = dd.equals(fd.getDomain());
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
                      String _name_7 = fd.getName();
                      _builder_1.append(_name_7);
                      _builder_1.append("_fromDomain_");
                      _builder_1.append(symbol);
                      _builder_1.append("}.");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("*");
                      _builder_1.newLine();
                      _builder_1.append("* @return the selected {@code ");
                      _builder_1.append(asmName);
                      _builder_1.append(".");
                      String _name_8 = fd.getCodomain().getName();
                      _builder_1.append(_name_8);
                      _builder_1.append(" ");
                      String _name_9 = fd.getName();
                      _builder_1.append(_name_9);
                      _builder_1.append("_fromDomain_");
                      _builder_1.append(symbol);
                      _builder_1.append("} ");
                      String _name_10 = fd.getName();
                      _builder_1.append(_name_10);
                      _builder_1.append("_fromDomain_");
                      _builder_1.append(symbol);
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("*/");
                      _builder_1.newLine();
                      _builder_1.append("\t");
                      _builder_1.append("private ");
                      _builder_1.append(asmName, "\t");
                      _builder_1.append(".");
                      String _name_11 = fd.getCodomain().getName();
                      _builder_1.append(_name_11, "\t");
                      _builder_1.append(" get_");
                      String _name_12 = fd.getName();
                      _builder_1.append(_name_12, "\t");
                      _builder_1.append("_fromDomain_");
                      _builder_1.append(symbol, "\t");
                      _builder_1.append("(){");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("\t\t");
                      _builder_1.append("return this.execution.");
                      String _name_13 = fd.getName();
                      _builder_1.append(_name_13, "\t\t");
                      _builder_1.append(".get(");
                      _builder_1.newLineIfNotEmpty();
                      _builder_1.append("\t\t\t");
                      _builder_1.append("this.execution.");
                      String _name_14 = fd.getDomain().getName();
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
                } else {
                  if ((dd instanceof AbstractTd)) {
                    EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
                    for (final Function sf : _function_1) {
                      if ((sf instanceof StaticFunction)) {
                        if ((sf.getCodomain().equals(dd) && (sf.getDomain() == null))) {
                          String symbol = sf.getName();
                          sb.append(System.lineSeparator());
                          StringConcatenation _builder_1 = new StringConcatenation();
                          _builder_1.append("/**");
                          _builder_1.newLine();
                          _builder_1.append("* Get the monitored function {@code ");
                          String _name_7 = fd.getName();
                          _builder_1.append(_name_7);
                          _builder_1.append("_fromDomain_");
                          _builder_1.append(symbol);
                          _builder_1.append("}.");
                          _builder_1.newLineIfNotEmpty();
                          _builder_1.append("*");
                          _builder_1.newLine();
                          _builder_1.append("* @return the selected {@code ");
                          _builder_1.append(asmName);
                          _builder_1.append(".");
                          String _name_8 = fd.getCodomain().getName();
                          _builder_1.append(_name_8);
                          _builder_1.append(" ");
                          String _name_9 = fd.getName();
                          _builder_1.append(_name_9);
                          _builder_1.append("_fromDomain_");
                          _builder_1.append(symbol);
                          _builder_1.append("} ");
                          String _name_10 = fd.getName();
                          _builder_1.append(_name_10);
                          _builder_1.append("_fromDomain_");
                          _builder_1.append(symbol);
                          _builder_1.newLineIfNotEmpty();
                          _builder_1.append("*/");
                          _builder_1.newLine();
                          _builder_1.append("\t");
                          _builder_1.append("private ");
                          _builder_1.append(asmName, "\t");
                          _builder_1.append(".");
                          String _name_11 = fd.getCodomain().getName();
                          _builder_1.append(_name_11, "\t");
                          _builder_1.append(" get_");
                          String _name_12 = fd.getName();
                          _builder_1.append(_name_12, "\t");
                          _builder_1.append("_fromDomain_");
                          _builder_1.append(symbol, "\t");
                          _builder_1.append("(){");
                          _builder_1.newLineIfNotEmpty();
                          _builder_1.append("\t\t");
                          _builder_1.append("return this.execution.");
                          String _name_13 = fd.getName();
                          _builder_1.append(_name_13, "\t\t");
                          _builder_1.append(".get(");
                          _builder_1.newLineIfNotEmpty();
                          _builder_1.append("\t\t\t");
                          _builder_1.append(asmName, "\t\t\t");
                          _builder_1.append(".");
                          String _name_14 = fd.getDomain().getName();
                          _builder_1.append(_name_14, "\t\t\t");
                          _builder_1.append(".get(\"");
                          _builder_1.append(symbol, "\t\t\t");
                          _builder_1.append("\"));");
                          _builder_1.newLineIfNotEmpty();
                          _builder_1.append("\t");
                          _builder_1.append("}");
                          _builder_1.newLine();
                          sb.append(_builder_1);
                          sb.append(System.lineSeparator());
                        }
                      }
                    }
                  } else {
                    if ((dd instanceof ConcreteDomain)) {
                      EList<Domain> _domain_2 = asm.getHeaderSection().getSignature().getDomain();
                      for (final Domain cd : _domain_2) {
                        if ((cd instanceof ConcreteDomain)) {
                          boolean _equals_1 = cd.getName().equals(fd.getDomain().getName());
                          if (_equals_1) {
                            final String elemsString = new TermToJava(asm).visit(((ConcreteDomain)cd).getDefinition().getBody());
                            final Function1<String, String> _function_2 = (String it) -> {
                              int _lastIndexOf = it.lastIndexOf(".");
                              int _plus = (_lastIndexOf + 1);
                              return it.substring(_plus);
                            };
                            final List<String> elems = ListExtensions.<String, String>map(((List<String>)Conversions.doWrapArray(elemsString.replace("(", "").replace(")", "").split(", "))), _function_2);
                            for (final String elem : elems) {
                              {
                                String originalDomain = new DomainToJavaString(asm).visit(((ConcreteDomain)cd).getDefinition().getDefinedDomain().getTypeDomain());
                                String symbol_1 = elem;
                                boolean _contains = AsmMethodsUtil.basicTdList.contains(originalDomain);
                                boolean _not = (!_contains);
                                if (_not) {
                                  symbol_1 = asmName.concat(".").concat(originalDomain).concat(".").concat(elem);
                                }
                                sb.append(System.lineSeparator());
                                StringConcatenation _builder_2 = new StringConcatenation();
                                _builder_2.append("/**");
                                _builder_2.newLine();
                                _builder_2.append("* Get the monitored function {@code ");
                                String _name_15 = fd.getName();
                                _builder_2.append(_name_15);
                                _builder_2.append("_fromDomain_");
                                _builder_2.append(elem);
                                _builder_2.append("}.");
                                _builder_2.newLineIfNotEmpty();
                                _builder_2.append("*");
                                _builder_2.newLine();
                                _builder_2.append("* @return the selected {@code ");
                                _builder_2.append(asmName);
                                _builder_2.append(".");
                                String _name_16 = fd.getCodomain().getName();
                                _builder_2.append(_name_16);
                                _builder_2.append(" ");
                                String _name_17 = fd.getName();
                                _builder_2.append(_name_17);
                                _builder_2.append("_fromDomain_");
                                _builder_2.append(elem);
                                _builder_2.append("} ");
                                String _name_18 = fd.getName();
                                _builder_2.append(_name_18);
                                _builder_2.append("_fromDomain_");
                                _builder_2.append(elem);
                                _builder_2.newLineIfNotEmpty();
                                _builder_2.append("*/");
                                _builder_2.newLine();
                                _builder_2.append("\t");
                                _builder_2.append("private ");
                                _builder_2.append(asmName, "\t");
                                _builder_2.append(".");
                                String _name_19 = fd.getCodomain().getName();
                                _builder_2.append(_name_19, "\t");
                                _builder_2.append(" get_");
                                String _name_20 = fd.getName();
                                _builder_2.append(_name_20, "\t");
                                _builder_2.append("_fromDomain_");
                                _builder_2.append(elem, "\t");
                                _builder_2.append("(){");
                                _builder_2.newLineIfNotEmpty();
                                _builder_2.append("\t\t");
                                _builder_2.append("return this.execution.");
                                String _name_21 = fd.getName();
                                _builder_2.append(_name_21, "\t\t");
                                _builder_2.append(".get(");
                                _builder_2.newLineIfNotEmpty();
                                _builder_2.append("\t\t\t");
                                _builder_2.append(asmName, "\t\t\t");
                                _builder_2.append(".");
                                String _name_22 = fd.getDomain().getName();
                                _builder_2.append(_name_22, "\t\t\t");
                                _builder_2.append(".valueOf(");
                                _builder_2.append(symbol_1, "\t\t\t");
                                _builder_2.append("));");
                                _builder_2.newLineIfNotEmpty();
                                _builder_2.append("\t");
                                _builder_2.append("}");
                                _builder_2.newLine();
                                sb.append(_builder_2);
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
            }
          }
        }
      }
    }
    return sb.toString();
  }
}
