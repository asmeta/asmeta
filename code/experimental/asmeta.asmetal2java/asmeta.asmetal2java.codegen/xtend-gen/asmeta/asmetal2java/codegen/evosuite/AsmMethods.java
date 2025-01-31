package asmeta.asmetal2java.codegen.evosuite;

import asmeta.asmetal2java.codegen.config.TranslatorOptions;
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
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Contains all the methods to control the translated java class as
 * an abstract state machine (ASM)
 */
@SuppressWarnings("all")
public class AsmMethods {
  public static final String BOOLEAN = AsmMethodsUtil.BOOLEAN;

  public static final String INTEGER = AsmMethodsUtil.INTEGER;

  public static final String REAL = AsmMethodsUtil.REAL;

  public static final String STRING = AsmMethodsUtil.STRING;

  public static final String CHAR = AsmMethodsUtil.CHAR;

  public static final String NATURAL = AsmMethodsUtil.NATURAL;

  /**
   * Controlled functions getters (public getters)
   * 
   * @param asm the Asm specification
   */
  public static String controlledGetter(final Asm asm, final TranslatorOptions translatorOptions) {
    final StringBuffer sb = new StringBuffer();
    String asmName = asm.getName();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof ControlledFunction)) {
        sb.append(System.lineSeparator());
        Domain _domain = ((ControlledFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          Domain _codomain = ((ControlledFunction)fd).getCodomain();
          if ((_codomain instanceof ConcreteDomain)) {
            String type = AsmMethodsUtil.getConcreteDomainType(asm, fd, ((ControlledFunction)fd).getCodomain().getName());
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("public ");
            _builder.append(type);
            _builder.append(" get_");
            String _name = ((ControlledFunction)fd).getName();
            _builder.append(_name);
            _builder.append("(){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("if(this.execution.");
            String _name_1 = ((ControlledFunction)fd).getName();
            _builder.append(_name_1, "\t");
            _builder.append(".get() != null){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("return this.execution.");
            String _name_2 = ((ControlledFunction)fd).getName();
            _builder.append(_name_2, "\t\t");
            _builder.append(".get().value;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("return null;");
            _builder.newLine();
            _builder.append("}");
            _builder.newLine();
            sb.append(_builder);
          } else {
            Domain _codomain_1 = ((ControlledFunction)fd).getCodomain();
            if ((_codomain_1 instanceof EnumTd)) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("public ");
              _builder_1.append(asmName);
              _builder_1.append(".");
              String _name_3 = ((ControlledFunction)fd).getCodomain().getName();
              _builder_1.append(_name_3);
              _builder_1.append(" get_");
              String _name_4 = ((ControlledFunction)fd).getName();
              _builder_1.append(_name_4);
              _builder_1.append("(){");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t");
              _builder_1.append("return this.execution.");
              String _name_5 = ((ControlledFunction)fd).getName();
              _builder_1.append(_name_5, "\t");
              _builder_1.append(".get();");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("}");
              _builder_1.newLine();
              sb.append(_builder_1);
            } else {
              Domain _codomain_2 = ((ControlledFunction)fd).getCodomain();
              if ((_codomain_2 instanceof AbstractTd)) {
                StringConcatenation _builder_2 = new StringConcatenation();
                _builder_2.append("public String get_");
                String _name_6 = ((ControlledFunction)fd).getName();
                _builder_2.append(_name_6);
                _builder_2.append("(){");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("\t");
                _builder_2.append("String value = this.execution.");
                String _name_7 = ((ControlledFunction)fd).getName();
                _builder_2.append(_name_7, "\t");
                _builder_2.append(".get().toString();");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("\t");
                _builder_2.append("return value != null ? \"abstract_\" + value : null;");
                _builder_2.newLine();
                _builder_2.append("}");
                _builder_2.newLine();
                sb.append(_builder_2);
              } else {
                Domain _codomain_3 = ((ControlledFunction)fd).getCodomain();
                if ((_codomain_3 instanceof SequenceDomain)) {
                  String type_1 = new DomainToJavaString(asm).visit(((ControlledFunction)fd).getCodomain()).replaceAll("<", "").replaceAll(">", "").trim();
                  boolean _contains = AsmMethodsUtil.basicTdList.contains(type_1);
                  if (_contains) {
                    type_1 = AsmMethodsUtil.getWrapperBasicTdType(type_1);
                    sb.append(AsmMethodsUtil.genSequenceGetter(((ControlledFunction)fd).getName(), type_1));
                  } else {
                    EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
                    for (final Domain cd : _domain_1) {
                      boolean _equals = cd.getName().equals(type_1);
                      if (_equals) {
                        if ((cd instanceof EnumTd)) {
                          type_1 = asm.getName().concat(".").concat(type_1);
                          sb.append(AsmMethodsUtil.genSequenceGetter(((ControlledFunction)fd).getName(), type_1));
                        } else {
                          if ((cd instanceof AbstractTd)) {
                            type_1 = asm.getName().concat(".").concat(type_1);
                            sb.append(AsmMethodsUtil.genSequenceGetter(((ControlledFunction)fd).getName(), type_1));
                          }
                        }
                      }
                    }
                  }
                } else {
                  boolean _equals_1 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.BOOLEAN);
                  if (_equals_1) {
                    StringConcatenation _builder_3 = new StringConcatenation();
                    _builder_3.append("public Boolean get_");
                    String _name_8 = ((ControlledFunction)fd).getName();
                    _builder_3.append(_name_8);
                    _builder_3.append("(){");
                    _builder_3.newLineIfNotEmpty();
                    _builder_3.append("\t");
                    _builder_3.append("return this.execution.");
                    String _name_9 = ((ControlledFunction)fd).getName();
                    _builder_3.append(_name_9, "\t");
                    _builder_3.append(".get();");
                    _builder_3.newLineIfNotEmpty();
                    _builder_3.append("}");
                    _builder_3.newLine();
                    sb.append(_builder_3);
                  } else {
                    boolean _equals_2 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.INTEGER);
                    if (_equals_2) {
                      StringConcatenation _builder_4 = new StringConcatenation();
                      _builder_4.append("public Integer get_");
                      String _name_10 = ((ControlledFunction)fd).getName();
                      _builder_4.append(_name_10);
                      _builder_4.append("(){");
                      _builder_4.newLineIfNotEmpty();
                      _builder_4.append("\t");
                      _builder_4.append("return this.execution.");
                      String _name_11 = ((ControlledFunction)fd).getName();
                      _builder_4.append(_name_11, "\t");
                      _builder_4.append(".get();");
                      _builder_4.newLineIfNotEmpty();
                      _builder_4.append("}");
                      _builder_4.newLine();
                      sb.append(_builder_4);
                    } else {
                      boolean _equals_3 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.REAL);
                      if (_equals_3) {
                        StringConcatenation _builder_5 = new StringConcatenation();
                        _builder_5.append("public Double get_");
                        String _name_12 = ((ControlledFunction)fd).getName();
                        _builder_5.append(_name_12);
                        _builder_5.append("(){");
                        _builder_5.newLineIfNotEmpty();
                        _builder_5.append("\t");
                        _builder_5.append("return this.execution.");
                        String _name_13 = ((ControlledFunction)fd).getName();
                        _builder_5.append(_name_13, "\t");
                        _builder_5.append(".get();");
                        _builder_5.newLineIfNotEmpty();
                        _builder_5.append("}");
                        _builder_5.newLine();
                        sb.append(_builder_5);
                      } else {
                        boolean _equals_4 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.STRING);
                        if (_equals_4) {
                          StringConcatenation _builder_6 = new StringConcatenation();
                          _builder_6.append("public String get_");
                          String _name_14 = ((ControlledFunction)fd).getName();
                          _builder_6.append(_name_14);
                          _builder_6.append("(){");
                          _builder_6.newLineIfNotEmpty();
                          _builder_6.append("\t");
                          _builder_6.append("return this.execution.");
                          String _name_15 = ((ControlledFunction)fd).getName();
                          _builder_6.append(_name_15, "\t");
                          _builder_6.append(".get();");
                          _builder_6.newLineIfNotEmpty();
                          _builder_6.append("}");
                          _builder_6.newLine();
                          sb.append(_builder_6);
                        } else {
                          boolean _equals_5 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.CHAR);
                          if (_equals_5) {
                            StringConcatenation _builder_7 = new StringConcatenation();
                            _builder_7.append("public Character get_");
                            String _name_16 = ((ControlledFunction)fd).getName();
                            _builder_7.append(_name_16);
                            _builder_7.append("(){");
                            _builder_7.newLineIfNotEmpty();
                            _builder_7.append("\t");
                            _builder_7.append("return this.execution.");
                            String _name_17 = ((ControlledFunction)fd).getName();
                            _builder_7.append(_name_17, "\t");
                            _builder_7.append(".get();");
                            _builder_7.newLineIfNotEmpty();
                            _builder_7.append("}");
                            _builder_7.newLine();
                            sb.append(_builder_7);
                          } else {
                            boolean _equals_6 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL);
                            if (_equals_6) {
                              StringConcatenation _builder_8 = new StringConcatenation();
                              _builder_8.append("public Integer get_natural_");
                              String _name_18 = ((ControlledFunction)fd).getName();
                              _builder_8.append(_name_18);
                              _builder_8.append("(){");
                              _builder_8.newLineIfNotEmpty();
                              _builder_8.append("\t");
                              _builder_8.append("return this.execution.");
                              String _name_19 = ((ControlledFunction)fd).getName();
                              _builder_8.append(_name_19, "\t");
                              _builder_8.append(".get();");
                              _builder_8.newLineIfNotEmpty();
                              _builder_8.append("}");
                              _builder_8.newLine();
                              sb.append(_builder_8);
                            } else {
                              AsmMethods.manageNotSupportedDomain(translatorOptions, ((ControlledFunction)fd).getCodomain().getName());
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
        } else {
          EList<Domain> _domain_2 = asm.getHeaderSection().getSignature().getDomain();
          for (final Domain dd : _domain_2) {
            boolean _equals_7 = dd.equals(((ControlledFunction)fd).getDomain());
            if (_equals_7) {
              if ((dd instanceof EnumTd)) {
                for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                  {
                    String symbol = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)dd).getElement().get(i));
                    Domain _codomain_4 = ((ControlledFunction)fd).getCodomain();
                    if ((_codomain_4 instanceof ConcreteDomain)) {
                      String type_2 = AsmMethodsUtil.getConcreteDomainType(asm, fd, ((ControlledFunction)fd).getCodomain().getName());
                      StringBuffer _append = sb.append("\t");
                      StringConcatenation _builder_9 = new StringConcatenation();
                      _builder_9.append("public ");
                      _builder_9.append(type_2);
                      _builder_9.append(" get_");
                      String _name_20 = ((ControlledFunction)fd).getName();
                      _builder_9.append(_name_20);
                      _builder_9.append("_fromDomain_");
                      _builder_9.append(symbol);
                      _builder_9.append("(){");
                      _append.append(_builder_9).append(
                        System.lineSeparator());
                      StringBuffer _append_1 = sb.append("\t\t");
                      StringConcatenation _builder_10 = new StringConcatenation();
                      _builder_10.append("return this.execution.");
                      String _name_21 = ((ControlledFunction)fd).getName();
                      _builder_10.append(_name_21);
                      _builder_10.append(".get(");
                      _append_1.append(_builder_10).append(
                        System.lineSeparator());
                      StringBuffer _append_2 = sb.append("\t\t\t");
                      StringConcatenation _builder_11 = new StringConcatenation();
                      _builder_11.append("this.execution.");
                      String _name_22 = ((ControlledFunction)fd).getDomain().getName();
                      _builder_11.append(_name_22);
                      _builder_11.append("_elemsList.get(");
                      _builder_11.append(i);
                      _builder_11.append(")).value;");
                      _append_2.append(_builder_11).append(System.lineSeparator());
                      StringBuffer _append_3 = sb.append("\t");
                      StringConcatenation _builder_12 = new StringConcatenation();
                      _builder_12.append("}");
                      _append_3.append(_builder_12);
                    } else {
                      Domain _codomain_5 = ((ControlledFunction)fd).getCodomain();
                      if ((_codomain_5 instanceof AbstractTd)) {
                        StringBuffer _append_4 = sb.append("\t");
                        StringConcatenation _builder_13 = new StringConcatenation();
                        _builder_13.append("public String get_");
                        String _name_23 = ((ControlledFunction)fd).getName();
                        _builder_13.append(_name_23);
                        _builder_13.append("_fromDomain_");
                        _builder_13.append(symbol);
                        _builder_13.append("(){");
                        _append_4.append(_builder_13).append(
                          System.lineSeparator());
                        StringBuffer _append_5 = sb.append("\t\t");
                        StringConcatenation _builder_14 = new StringConcatenation();
                        _builder_14.append("String value = this.execution.");
                        String _name_24 = ((ControlledFunction)fd).getName();
                        _builder_14.append(_name_24);
                        _builder_14.append(".get(");
                        _append_5.append(_builder_14).append(System.lineSeparator());
                        StringBuffer _append_6 = sb.append("\t\t\t");
                        StringConcatenation _builder_15 = new StringConcatenation();
                        _builder_15.append("this.execution.");
                        String _name_25 = ((ControlledFunction)fd).getDomain().getName();
                        _builder_15.append(_name_25);
                        _builder_15.append("_elemsList.get(");
                        _builder_15.append(i);
                        _builder_15.append(")).toString();");
                        _append_6.append(_builder_15).append(System.lineSeparator());
                        StringBuffer _append_7 = sb.append("\t\t");
                        StringConcatenation _builder_16 = new StringConcatenation();
                        _builder_16.append("return value != null ? \"abstract_\" + value : null;");
                        _append_7.append(_builder_16).append(
                          System.lineSeparator());
                        StringBuffer _append_8 = sb.append("\t");
                        StringConcatenation _builder_17 = new StringConcatenation();
                        _builder_17.append("}");
                        _append_8.append(_builder_17);
                      } else {
                        if (((((((((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.INTEGER) || ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.BOOLEAN)) || 
                          ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.STRING)) || ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.REAL)) || 
                          ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.CHAR)) || ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL)) || 
                          (((ControlledFunction)fd).getCodomain() instanceof EnumTd))) {
                          String methodGetterSignature = new String();
                          boolean _equals_8 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL);
                          if (_equals_8) {
                            methodGetterSignature = "get_natural_".concat(((ControlledFunction)fd).getName()).concat(
                              "_fromDomain_").concat(symbol);
                          } else {
                            methodGetterSignature = "get_".concat(((ControlledFunction)fd).getName()).concat("_fromDomain_").concat(symbol);
                          }
                          sb.append(
                            AsmMethodsUtil.getMethodSignature(asmName, methodGetterSignature, 
                              ((ControlledFunction)fd).getCodomain().getName())).append(System.lineSeparator());
                          StringBuffer _append_9 = sb.append("\t\t");
                          StringConcatenation _builder_18 = new StringConcatenation();
                          _builder_18.append("return this.execution.");
                          String _name_26 = ((ControlledFunction)fd).getName();
                          _builder_18.append(_name_26);
                          _builder_18.append(".get(");
                          _append_9.append(_builder_18).append(
                            System.lineSeparator());
                          StringBuffer _append_10 = sb.append("\t\t\t");
                          StringConcatenation _builder_19 = new StringConcatenation();
                          _builder_19.append("this.execution.");
                          String _name_27 = ((ControlledFunction)fd).getDomain().getName();
                          _builder_19.append(_name_27);
                          _builder_19.append("_elemsList.get(");
                          _builder_19.append(i);
                          _builder_19.append("));");
                          _append_10.append(_builder_19).append(
                            System.lineSeparator());
                          StringBuffer _append_11 = sb.append("\t");
                          StringConcatenation _builder_20 = new StringConcatenation();
                          _builder_20.append("}");
                          _append_11.append(_builder_20);
                        } else {
                          AsmMethods.manageNotSupportedDomain(translatorOptions, ((ControlledFunction)fd).getCodomain().getName());
                        }
                      }
                    }
                    sb.append(System.lineSeparator());
                  }
                }
              } else {
                if ((dd instanceof AbstractTd)) {
                  EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
                  for (final Function sf : _function_1) {
                    if ((sf instanceof StaticFunction)) {
                      if ((((StaticFunction)sf).getCodomain().equals(dd) && (((StaticFunction)sf).getDomain() == null))) {
                        String symbol = ((StaticFunction)sf).getName();
                        Domain _codomain_4 = ((ControlledFunction)fd).getCodomain();
                        if ((_codomain_4 instanceof ConcreteDomain)) {
                          String type_2 = AsmMethodsUtil.getConcreteDomainType(asm, fd, 
                            ((ControlledFunction)fd).getCodomain().getName());
                          StringBuffer _append = sb.append("\t");
                          StringConcatenation _builder_9 = new StringConcatenation();
                          _builder_9.append("public ");
                          _builder_9.append(type_2);
                          _builder_9.append(" get_");
                          String _name_20 = ((ControlledFunction)fd).getName();
                          _builder_9.append(_name_20);
                          _builder_9.append("_fromDomain_");
                          _builder_9.append(symbol);
                          _builder_9.append("(){");
                          _append.append(_builder_9).append(System.lineSeparator());
                          StringBuffer _append_1 = sb.append("\t\t");
                          StringConcatenation _builder_10 = new StringConcatenation();
                          _builder_10.append("return this.execution.");
                          String _name_21 = ((ControlledFunction)fd).getName();
                          _builder_10.append(_name_21);
                          _builder_10.append(".get(");
                          _append_1.append(_builder_10).append(System.lineSeparator());
                          StringBuffer _append_2 = sb.append("\t\t\t");
                          StringConcatenation _builder_11 = new StringConcatenation();
                          _builder_11.append(asmName);
                          _builder_11.append(".");
                          String _name_22 = ((ControlledFunction)fd).getDomain().getName();
                          _builder_11.append(_name_22);
                          _builder_11.append(".get(\"");
                          _builder_11.append(symbol);
                          _builder_11.append("\")).value;");
                          _append_2.append(_builder_11).append(System.lineSeparator());
                          StringBuffer _append_3 = sb.append("\t");
                          StringConcatenation _builder_12 = new StringConcatenation();
                          _builder_12.append("}");
                          _append_3.append(_builder_12);
                        } else {
                          Domain _codomain_5 = ((ControlledFunction)fd).getCodomain();
                          if ((_codomain_5 instanceof AbstractTd)) {
                            StringBuffer _append_4 = sb.append("\t");
                            StringConcatenation _builder_13 = new StringConcatenation();
                            _builder_13.append("public String get_");
                            String _name_23 = ((ControlledFunction)fd).getName();
                            _builder_13.append(_name_23);
                            _builder_13.append("_fromDomain_");
                            _builder_13.append(symbol);
                            _builder_13.append("(){");
                            _append_4.append(_builder_13).append(System.lineSeparator());
                            StringBuffer _append_5 = sb.append("\t\t");
                            StringConcatenation _builder_14 = new StringConcatenation();
                            _builder_14.append("String value = this.execution.");
                            String _name_24 = ((ControlledFunction)fd).getName();
                            _builder_14.append(_name_24);
                            _builder_14.append(".get(");
                            _append_5.append(_builder_14).append(
                              System.lineSeparator());
                            StringBuffer _append_6 = sb.append("\t\t");
                            StringConcatenation _builder_15 = new StringConcatenation();
                            _builder_15.append(asmName);
                            _builder_15.append(".");
                            String _name_25 = ((ControlledFunction)fd).getDomain().getName();
                            _builder_15.append(_name_25);
                            _builder_15.append(".get(\"");
                            _builder_15.append(symbol);
                            _builder_15.append("\")).toString();");
                            _append_6.append(_builder_15).append(System.lineSeparator());
                            StringBuffer _append_7 = sb.append("\t\t");
                            StringConcatenation _builder_16 = new StringConcatenation();
                            _builder_16.append("return value != null ? \"abstract_\" + value : null;");
                            _append_7.append(_builder_16).append(System.lineSeparator());
                            StringBuffer _append_8 = sb.append("\t");
                            StringConcatenation _builder_17 = new StringConcatenation();
                            _builder_17.append("}");
                            _append_8.append(_builder_17);
                          } else {
                            if (((((((((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.INTEGER) || 
                              ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.BOOLEAN)) || ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.STRING)) || 
                              ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.REAL)) || ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.CHAR)) || 
                              ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL)) || (((ControlledFunction)fd).getCodomain() instanceof EnumTd))) {
                              String methodGetterSignature = new String();
                              boolean _equals_8 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL);
                              if (_equals_8) {
                                methodGetterSignature = "get_natural_".concat(((ControlledFunction)fd).getName()).concat(
                                  "_fromDomain_").concat(symbol);
                              } else {
                                methodGetterSignature = "get_".concat(((ControlledFunction)fd).getName()).concat(
                                  "_fromDomain_").concat(symbol);
                              }
                              sb.append(
                                AsmMethodsUtil.getMethodSignature(asmName, methodGetterSignature, 
                                  ((ControlledFunction)fd).getCodomain().getName())).append(System.lineSeparator());
                              StringBuffer _append_9 = sb.append("\t\t");
                              StringConcatenation _builder_18 = new StringConcatenation();
                              _builder_18.append("return this.execution.");
                              String _name_26 = ((ControlledFunction)fd).getName();
                              _builder_18.append(_name_26);
                              _builder_18.append(".get(");
                              _append_9.append(_builder_18).append(System.lineSeparator());
                              StringBuffer _append_10 = sb.append("\t\t\t");
                              StringConcatenation _builder_19 = new StringConcatenation();
                              _builder_19.append(asmName);
                              _builder_19.append(".");
                              String _name_27 = ((ControlledFunction)fd).getDomain().getName();
                              _builder_19.append(_name_27);
                              _builder_19.append(".get(\"");
                              _builder_19.append(symbol);
                              _builder_19.append("\"));");
                              _append_10.append(_builder_19).append(
                                System.lineSeparator());
                              StringBuffer _append_11 = sb.append("\t");
                              StringConcatenation _builder_20 = new StringConcatenation();
                              _builder_20.append("}");
                              _append_11.append(_builder_20);
                            } else {
                              AsmMethods.manageNotSupportedDomain(translatorOptions, ((ControlledFunction)fd).getCodomain().getName());
                            }
                          }
                        }
                        sb.append(System.lineSeparator());
                      }
                    }
                  }
                } else {
                  if ((dd instanceof ConcreteDomain)) {
                    EList<Domain> _domain_3 = asm.getHeaderSection().getSignature().getDomain();
                    for (final Domain cd_1 : _domain_3) {
                      if ((cd_1 instanceof ConcreteDomain)) {
                        boolean _equals_9 = ((ConcreteDomain)cd_1).getName().equals(((ControlledFunction)fd).getDomain().getName());
                        if (_equals_9) {
                          final String elemsString = new TermToJava(asm).visit(((ConcreteDomain)cd_1).getDefinition().getBody());
                          final Function1<String, String> _function_2 = (String it) -> {
                            int _lastIndexOf = it.lastIndexOf(".");
                            int _plus = (_lastIndexOf + 1);
                            return it.substring(_plus);
                          };
                          final List<String> elems = ListExtensions.<String, String>map(((List<String>)Conversions.doWrapArray(elemsString.replace("(", "").replace(")", "").split(", "))), _function_2);
                          for (final String elem : elems) {
                            {
                              String originalDomain = new DomainToJavaString(asm).visit(
                                ((ConcreteDomain)cd_1).getDefinition().getDefinedDomain().getTypeDomain());
                              String symbol_1 = elem;
                              boolean _contains_1 = AsmMethodsUtil.basicTdList.contains(originalDomain);
                              boolean _not = (!_contains_1);
                              if (_not) {
                                symbol_1 = asmName.concat(".").concat(originalDomain).concat(".").concat(elem);
                              }
                              Domain _codomain_6 = ((ControlledFunction)fd).getCodomain();
                              if ((_codomain_6 instanceof ConcreteDomain)) {
                                StringBuffer _append_12 = sb.append("\t\t");
                                StringConcatenation _builder_21 = new StringConcatenation();
                                _builder_21.append("public ");
                                _builder_21.append(originalDomain);
                                _builder_21.append(" get_");
                                String _name_28 = ((ControlledFunction)fd).getName();
                                _builder_21.append(_name_28);
                                _builder_21.append("_fromDomain_");
                                _builder_21.append(elem);
                                _builder_21.append("(){");
                                _append_12.append(_builder_21).append(System.lineSeparator());
                                StringBuffer _append_13 = sb.append("\t\t");
                                StringConcatenation _builder_22 = new StringConcatenation();
                                _builder_22.append("return this.execution.");
                                String _name_29 = ((ControlledFunction)fd).getName();
                                _builder_22.append(_name_29);
                                _builder_22.append(".get(");
                                _append_13.append(_builder_22).append(
                                  System.lineSeparator());
                                StringBuffer _append_14 = sb.append("\t\t\t");
                                StringConcatenation _builder_23 = new StringConcatenation();
                                _builder_23.append(asmName);
                                _builder_23.append(".");
                                String _name_30 = ((ControlledFunction)fd).getDomain().getName();
                                _builder_23.append(_name_30);
                                _builder_23.append(".valueOf(");
                                _builder_23.append(symbol_1);
                                _builder_23.append(")).value;");
                                _append_14.append(_builder_23).append(System.lineSeparator());
                                StringBuffer _append_15 = sb.append("\t");
                                StringConcatenation _builder_24 = new StringConcatenation();
                                _builder_24.append("}");
                                _append_15.append(_builder_24);
                              } else {
                                Domain _codomain_7 = ((ControlledFunction)fd).getCodomain();
                                if ((_codomain_7 instanceof AbstractTd)) {
                                  StringBuffer _append_16 = sb.append("\t\t");
                                  StringConcatenation _builder_25 = new StringConcatenation();
                                  _builder_25.append("public String get_");
                                  String _name_31 = ((ControlledFunction)fd).getName();
                                  _builder_25.append(_name_31);
                                  _builder_25.append("_fromDomain_");
                                  _builder_25.append(elem);
                                  _builder_25.append("(){");
                                  _append_16.append(_builder_25).append(System.lineSeparator());
                                  StringBuffer _append_17 = sb.append("\t\t");
                                  StringConcatenation _builder_26 = new StringConcatenation();
                                  _builder_26.append("return this.execution.");
                                  String _name_32 = ((ControlledFunction)fd).getName();
                                  _builder_26.append(_name_32);
                                  _builder_26.append(".get(");
                                  _append_17.append(_builder_26).append(
                                    System.lineSeparator());
                                  StringBuffer _append_18 = sb.append("\t\t\t");
                                  StringConcatenation _builder_27 = new StringConcatenation();
                                  _builder_27.append(asmName);
                                  _builder_27.append(".");
                                  String _name_33 = ((ControlledFunction)fd).getDomain().getName();
                                  _builder_27.append(_name_33);
                                  _builder_27.append(".valueOf(");
                                  _builder_27.append(symbol_1);
                                  _builder_27.append(")).toString();");
                                  _append_18.append(_builder_27).append(System.lineSeparator());
                                  StringBuffer _append_19 = sb.append("\t");
                                  StringConcatenation _builder_28 = new StringConcatenation();
                                  _builder_28.append("}");
                                  _append_19.append(_builder_28);
                                } else {
                                  if (((((((((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.INTEGER) || 
                                    ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.BOOLEAN)) || 
                                    ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.STRING)) || ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.REAL)) || 
                                    ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.CHAR)) || ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL)) || 
                                    (((ControlledFunction)fd).getCodomain() instanceof EnumTd))) {
                                    String methodGetterSignature_1 = new String();
                                    boolean _equals_10 = ((ControlledFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL);
                                    if (_equals_10) {
                                      methodGetterSignature_1 = "get_natural_".concat(((ControlledFunction)fd).getName()).concat(
                                        "_fromDomain_").concat(elem);
                                    } else {
                                      methodGetterSignature_1 = "get_".concat(((ControlledFunction)fd).getName()).concat(
                                        "_fromDomain_").concat(elem);
                                    }
                                    sb.append(
                                      AsmMethodsUtil.getMethodSignature(asmName, methodGetterSignature_1, ((ControlledFunction)fd).getCodomain().getName())).append(
                                      System.lineSeparator());
                                    StringBuffer _append_20 = sb.append("\t\t");
                                    StringConcatenation _builder_29 = new StringConcatenation();
                                    _builder_29.append("return this.execution.");
                                    String _name_34 = ((ControlledFunction)fd).getName();
                                    _builder_29.append(_name_34);
                                    _builder_29.append(".get(");
                                    _append_20.append(_builder_29).append(
                                      System.lineSeparator());
                                    StringBuffer _append_21 = sb.append("\t\t\t");
                                    StringConcatenation _builder_30 = new StringConcatenation();
                                    _builder_30.append(asmName);
                                    _builder_30.append(".");
                                    String _name_35 = ((ControlledFunction)fd).getDomain().getName();
                                    _builder_30.append(_name_35);
                                    _builder_30.append(".valueOf(");
                                    _builder_30.append(symbol_1);
                                    _builder_30.append("));");
                                    _append_21.append(_builder_30).append(System.lineSeparator());
                                    StringBuffer _append_22 = sb.append("\t");
                                    StringConcatenation _builder_31 = new StringConcatenation();
                                    _builder_31.append("}");
                                    _append_22.append(_builder_31);
                                  } else {
                                    AsmMethods.manageNotSupportedDomain(translatorOptions, ((ControlledFunction)fd).getCodomain().getName());
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  } else {
                    AsmMethods.manageNotSupportedDomain(translatorOptions, ((ControlledFunction)fd).getDomain().getName());
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
   * Monitored functions setters (public setters)
   * 
   * @param asm the Asm specification
   */
  public static String monitoredSetters(final Asm asm, final TranslatorOptions translatorOptions) {
    final StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        sb.append(System.lineSeparator());
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          Domain _codomain = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain instanceof EnumTd)) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("public void set_");
            String _name = ((MonitoredFunction)fd).getName();
            _builder.append(_name);
            _builder.append("(");
            String _name_1 = asm.getName();
            _builder.append(_name_1);
            _builder.append(".");
            String _name_2 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder.append(_name_2);
            _builder.append(" ");
            String _name_3 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_3);
            _builder.append(") {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("this.execution.");
            String _name_4 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_4, "\t");
            _builder.append(".set(");
            String _name_5 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_5, "\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("System.out.println(\"Set ");
            String _name_6 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_6, "\t");
            _builder.append(" = \" + ");
            String _name_7 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_7, "\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
            _builder.append("}");
            sb.append(_builder);
          } else {
            Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
            if ((_codomain_1 instanceof ConcreteDomain)) {
              String type = AsmMethodsUtil.getConcreteDomainType(asm, fd, ((MonitoredFunction)fd).getCodomain().getName());
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("public void set_");
              String _name_8 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_8);
              _builder_1.append("(");
              _builder_1.append(type);
              _builder_1.append(" ");
              String _name_9 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_9);
              _builder_1.append(") {");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t");
              _builder_1.append("this.execution.");
              String _name_10 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_10, "\t");
              _builder_1.append(".set(");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t\t");
              String _name_11 = asm.getName();
              _builder_1.append(_name_11, "\t\t");
              _builder_1.append(".");
              String _name_12 = ((MonitoredFunction)fd).getCodomain().getName();
              _builder_1.append(_name_12, "\t\t");
              _builder_1.append(".valueOf(");
              String _name_13 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_13, "\t\t");
              _builder_1.append("));");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t");
              _builder_1.append("System.out.println(\"Set ");
              String _name_14 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_14, "\t");
              _builder_1.append(" = \" + ");
              String _name_15 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_15, "\t");
              _builder_1.append(");");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("}");
              sb.append(_builder_1);
              sb.append(System.lineSeparator());
            } else {
              Domain _codomain_2 = ((MonitoredFunction)fd).getCodomain();
              if ((_codomain_2 instanceof AbstractTd)) {
                StringConcatenation _builder_2 = new StringConcatenation();
                _builder_2.append("public void set_abstract_");
                String _name_16 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_16);
                _builder_2.append("(String ");
                String _name_17 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_17);
                _builder_2.append(") {");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("\t");
                _builder_2.append("this.execution.");
                String _name_18 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_18, "\t");
                _builder_2.append(".set(");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("\t");
                String _name_19 = asm.getName();
                _builder_2.append(_name_19, "\t");
                _builder_2.append(".");
                String _name_20 = ((MonitoredFunction)fd).getCodomain().getName();
                _builder_2.append(_name_20, "\t");
                _builder_2.append(".get(");
                String _name_21 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_21, "\t");
                _builder_2.append("));");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("\t");
                _builder_2.append("System.out.println(\"Set ");
                String _name_22 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_22, "\t");
                _builder_2.append(" = \" + ");
                String _name_23 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_23, "\t");
                _builder_2.append(");");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("}");
                sb.append(_builder_2);
                sb.append(System.lineSeparator());
              } else {
                Domain _codomain_3 = ((MonitoredFunction)fd).getCodomain();
                if ((_codomain_3 instanceof SequenceDomain)) {
                  String type_1 = new DomainToJavaString(asm).visit(((MonitoredFunction)fd).getCodomain()).replaceAll("<", "").replaceAll(">", "").trim();
                  boolean _contains = AsmMethodsUtil.basicTdList.contains(type_1);
                  if (_contains) {
                    type_1 = AsmMethodsUtil.getWrapperBasicTdType(type_1);
                    String parsingMethod = AsmMethodsUtil.getParsingMethod(type_1);
                    sb.append(AsmMethodsUtil.genSequenceSetter(((MonitoredFunction)fd).getName(), type_1, parsingMethod));
                  } else {
                    EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
                    for (final Domain cd : _domain_1) {
                      boolean _equals = cd.getName().equals(type_1);
                      if (_equals) {
                        if ((cd instanceof EnumTd)) {
                          type_1 = asm.getName().concat(".").concat(type_1);
                          String parsingMethod_1 = (type_1 + "::valueOf");
                          sb.append(AsmMethodsUtil.genSequenceSetter(((MonitoredFunction)fd).getName(), type_1, parsingMethod_1));
                        } else {
                          if ((cd instanceof AbstractTd)) {
                            type_1 = asm.getName().concat(".").concat(type_1);
                            String parsingMethod_2 = (type_1 + "::get");
                            sb.append(AsmMethodsUtil.genSequenceSetter(((MonitoredFunction)fd).getName(), type_1, parsingMethod_2));
                          }
                        }
                      }
                    }
                  }
                } else {
                  boolean _equals_1 = ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL);
                  if (_equals_1) {
                    StringConcatenation _builder_3 = new StringConcatenation();
                    _builder_3.append("public void set_natural_");
                    String _name_24 = ((MonitoredFunction)fd).getName();
                    _builder_3.append(_name_24);
                    _builder_3.append("(int ");
                    String _name_25 = ((MonitoredFunction)fd).getName();
                    _builder_3.append(_name_25);
                    _builder_3.append(") {");
                    _builder_3.newLineIfNotEmpty();
                    _builder_3.append("\t");
                    _builder_3.append("this.execution.");
                    String _name_26 = ((MonitoredFunction)fd).getName();
                    _builder_3.append(_name_26, "\t");
                    _builder_3.append(".set(");
                    String _name_27 = ((MonitoredFunction)fd).getName();
                    _builder_3.append(_name_27, "\t");
                    _builder_3.append(");");
                    _builder_3.newLineIfNotEmpty();
                    _builder_3.append("\t");
                    _builder_3.append("System.out.println(\"Set ");
                    String _name_28 = ((MonitoredFunction)fd).getName();
                    _builder_3.append(_name_28, "\t");
                    _builder_3.append(" = \" + ");
                    String _name_29 = ((MonitoredFunction)fd).getName();
                    _builder_3.append(_name_29, "\t");
                    _builder_3.append(" +\"n\");");
                    _builder_3.newLineIfNotEmpty();
                    _builder_3.append("}");
                    sb.append(_builder_3);
                  } else {
                    if (((((((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.INTEGER) || ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.BOOLEAN)) || 
                      ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.STRING)) || ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.REAL)) || 
                      ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.CHAR))) {
                      String type_2 = AsmMethodsUtil.getBasicTdType(((MonitoredFunction)fd).getCodomain().getName());
                      StringConcatenation _builder_4 = new StringConcatenation();
                      _builder_4.append("public void set_");
                      String _name_30 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_30);
                      _builder_4.append("(");
                      _builder_4.append(type_2);
                      _builder_4.append(" ");
                      String _name_31 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_31);
                      _builder_4.append(") {");
                      _builder_4.newLineIfNotEmpty();
                      _builder_4.append("\t");
                      _builder_4.append("this.execution.");
                      String _name_32 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_32, "\t");
                      _builder_4.append(".set(");
                      String _name_33 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_33, "\t");
                      _builder_4.append(");");
                      _builder_4.newLineIfNotEmpty();
                      _builder_4.append("\t");
                      _builder_4.append("System.out.println(\"Set ");
                      String _name_34 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_34, "\t");
                      _builder_4.append(" = \" + ");
                      String _name_35 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_35, "\t");
                      _builder_4.append(");");
                      _builder_4.newLineIfNotEmpty();
                      _builder_4.append("}");
                      sb.append(_builder_4);
                    } else {
                      AsmMethods.manageNotSupportedDomain(translatorOptions, ((MonitoredFunction)fd).getCodomain().getName());
                    }
                  }
                }
              }
            }
          }
        } else {
          Domain dd = ((MonitoredFunction)fd).getDomain();
          if ((dd instanceof EnumTd)) {
            for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
              {
                String symbol = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)dd).getElement().get(i));
                Domain _codomain_4 = ((MonitoredFunction)fd).getCodomain();
                if ((_codomain_4 instanceof ConcreteDomain)) {
                  String type_3 = AsmMethodsUtil.getConcreteDomainType(asm, fd, ((MonitoredFunction)fd).getCodomain().getName());
                  StringConcatenation _builder_5 = new StringConcatenation();
                  _builder_5.append("public void set_");
                  String _name_36 = ((MonitoredFunction)fd).getName();
                  _builder_5.append(_name_36);
                  _builder_5.append("_fromDomain_");
                  _builder_5.append(symbol);
                  _builder_5.append("(");
                  _builder_5.append(type_3);
                  _builder_5.append(" ");
                  String _name_37 = ((MonitoredFunction)fd).getName();
                  _builder_5.append(_name_37);
                  _builder_5.append("_");
                  _builder_5.append(symbol);
                  _builder_5.append(") {");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("\t");
                  _builder_5.append("this.execution.");
                  String _name_38 = ((MonitoredFunction)fd).getName();
                  _builder_5.append(_name_38, "\t");
                  _builder_5.append(".set(");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("\t");
                  String _name_39 = asm.getName();
                  _builder_5.append(_name_39, "\t");
                  _builder_5.append(".");
                  String _name_40 = ((EnumTd)dd).getName();
                  _builder_5.append(_name_40, "\t");
                  _builder_5.append(".");
                  _builder_5.append(symbol, "\t");
                  _builder_5.append(",");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("\t");
                  String _name_41 = asm.getName();
                  _builder_5.append(_name_41, "\t");
                  _builder_5.append(".");
                  String _name_42 = ((MonitoredFunction)fd).getCodomain().getName();
                  _builder_5.append(_name_42, "\t");
                  _builder_5.append(".valueOf(this.execution.");
                  String _name_43 = ((MonitoredFunction)fd).getCodomain().getName();
                  _builder_5.append(_name_43, "\t");
                  _builder_5.append("_elems.get(");
                  String _name_44 = ((MonitoredFunction)fd).getName();
                  _builder_5.append(_name_44, "\t");
                  _builder_5.append("_");
                  _builder_5.append(symbol, "\t");
                  _builder_5.append(")));");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("\t");
                  _builder_5.append("System.out.println(\"Set ");
                  String _name_45 = ((MonitoredFunction)fd).getName();
                  _builder_5.append(_name_45, "\t");
                  _builder_5.append("_");
                  _builder_5.append(symbol, "\t");
                  _builder_5.append(" = \" + ");
                  String _name_46 = ((MonitoredFunction)fd).getName();
                  _builder_5.append(_name_46, "\t");
                  _builder_5.append("_");
                  _builder_5.append(symbol, "\t");
                  _builder_5.append(");");
                  _builder_5.newLineIfNotEmpty();
                  _builder_5.append("}");
                  sb.append(_builder_5);
                } else {
                  Domain _codomain_5 = ((MonitoredFunction)fd).getCodomain();
                  if ((_codomain_5 instanceof EnumTd)) {
                    StringConcatenation _builder_6 = new StringConcatenation();
                    _builder_6.append("public void set_");
                    String _name_47 = ((MonitoredFunction)fd).getName();
                    _builder_6.append(_name_47);
                    _builder_6.append("_fromDomain_");
                    _builder_6.append(symbol);
                    _builder_6.append("(");
                    String _name_48 = asm.getName();
                    _builder_6.append(_name_48);
                    _builder_6.append(".");
                    String _name_49 = ((MonitoredFunction)fd).getCodomain().getName();
                    _builder_6.append(_name_49);
                    _builder_6.append(" ");
                    String _name_50 = ((MonitoredFunction)fd).getName();
                    _builder_6.append(_name_50);
                    _builder_6.append("_");
                    _builder_6.append(symbol);
                    _builder_6.append(") {");
                    _builder_6.newLineIfNotEmpty();
                    _builder_6.append("\t");
                    _builder_6.append("this.execution.");
                    String _name_51 = ((MonitoredFunction)fd).getName();
                    _builder_6.append(_name_51, "\t");
                    _builder_6.append(".set(");
                    String _name_52 = asm.getName();
                    _builder_6.append(_name_52, "\t");
                    _builder_6.append(".");
                    String _name_53 = ((EnumTd)dd).getName();
                    _builder_6.append(_name_53, "\t");
                    _builder_6.append(".");
                    _builder_6.append(symbol, "\t");
                    _builder_6.append(", ");
                    String _name_54 = ((MonitoredFunction)fd).getName();
                    _builder_6.append(_name_54, "\t");
                    _builder_6.append("_");
                    _builder_6.append(symbol, "\t");
                    _builder_6.append(");");
                    _builder_6.newLineIfNotEmpty();
                    _builder_6.append("\t");
                    _builder_6.append("System.out.println(\"Set ");
                    String _name_55 = ((MonitoredFunction)fd).getName();
                    _builder_6.append(_name_55, "\t");
                    _builder_6.append("_");
                    _builder_6.append(symbol, "\t");
                    _builder_6.append(" = \" + ");
                    String _name_56 = ((MonitoredFunction)fd).getName();
                    _builder_6.append(_name_56, "\t");
                    _builder_6.append("_");
                    _builder_6.append(symbol, "\t");
                    _builder_6.append(");");
                    _builder_6.newLineIfNotEmpty();
                    _builder_6.append("}");
                    sb.append(_builder_6);
                  } else {
                    Domain _codomain_6 = ((MonitoredFunction)fd).getCodomain();
                    if ((_codomain_6 instanceof AbstractTd)) {
                      StringConcatenation _builder_7 = new StringConcatenation();
                      _builder_7.append("public void set_abstract_");
                      String _name_57 = ((MonitoredFunction)fd).getName();
                      _builder_7.append(_name_57);
                      _builder_7.append("_fromDomain_");
                      _builder_7.append(symbol);
                      _builder_7.append("(String ");
                      String _name_58 = ((MonitoredFunction)fd).getName();
                      _builder_7.append(_name_58);
                      _builder_7.append("_");
                      _builder_7.append(symbol);
                      _builder_7.append(") {");
                      _builder_7.newLineIfNotEmpty();
                      _builder_7.append("\t");
                      _builder_7.append("this.execution.");
                      String _name_59 = ((MonitoredFunction)fd).getName();
                      _builder_7.append(_name_59, "\t");
                      _builder_7.append(".set(");
                      String _name_60 = asm.getName();
                      _builder_7.append(_name_60, "\t");
                      _builder_7.append(".");
                      String _name_61 = ((EnumTd)dd).getName();
                      _builder_7.append(_name_61, "\t");
                      _builder_7.append(".");
                      _builder_7.append(symbol, "\t");
                      _builder_7.append(", ");
                      _builder_7.newLineIfNotEmpty();
                      _builder_7.append("\t");
                      String _name_62 = asm.getName();
                      _builder_7.append(_name_62, "\t");
                      _builder_7.append(".");
                      String _name_63 = ((MonitoredFunction)fd).getCodomain().getName();
                      _builder_7.append(_name_63, "\t");
                      _builder_7.append(".get(");
                      String _name_64 = ((MonitoredFunction)fd).getName();
                      _builder_7.append(_name_64, "\t");
                      _builder_7.append("_");
                      _builder_7.append(symbol, "\t");
                      _builder_7.append("));");
                      _builder_7.newLineIfNotEmpty();
                      _builder_7.append("\t");
                      _builder_7.append("System.out.println(\"Set ");
                      String _name_65 = ((MonitoredFunction)fd).getName();
                      _builder_7.append(_name_65, "\t");
                      _builder_7.append("_");
                      _builder_7.append(symbol, "\t");
                      _builder_7.append(" = \" + ");
                      String _name_66 = ((MonitoredFunction)fd).getName();
                      _builder_7.append(_name_66, "\t");
                      _builder_7.append("_");
                      _builder_7.append(symbol, "\t");
                      _builder_7.append(");");
                      _builder_7.newLineIfNotEmpty();
                      _builder_7.append("}");
                      sb.append(_builder_7);
                    } else {
                      boolean _equals_2 = ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL);
                      if (_equals_2) {
                        StringConcatenation _builder_8 = new StringConcatenation();
                        _builder_8.append("public void set_natural_");
                        String _name_67 = ((MonitoredFunction)fd).getName();
                        _builder_8.append(_name_67);
                        _builder_8.append("_fromDomain_");
                        _builder_8.append(symbol);
                        _builder_8.append("(int ");
                        String _name_68 = ((MonitoredFunction)fd).getName();
                        _builder_8.append(_name_68);
                        _builder_8.append("_");
                        _builder_8.append(symbol);
                        _builder_8.append(") {");
                        _builder_8.newLineIfNotEmpty();
                        _builder_8.append("\t");
                        _builder_8.append("this.execution.");
                        String _name_69 = ((MonitoredFunction)fd).getName();
                        _builder_8.append(_name_69, "\t");
                        _builder_8.append(".set(");
                        String _name_70 = asm.getName();
                        _builder_8.append(_name_70, "\t");
                        _builder_8.append(".");
                        String _name_71 = ((EnumTd)dd).getName();
                        _builder_8.append(_name_71, "\t");
                        _builder_8.append(".");
                        _builder_8.append(symbol, "\t");
                        _builder_8.append(", ");
                        String _name_72 = ((MonitoredFunction)fd).getName();
                        _builder_8.append(_name_72, "\t");
                        _builder_8.append("_");
                        _builder_8.append(symbol, "\t");
                        _builder_8.append(");");
                        _builder_8.newLineIfNotEmpty();
                        _builder_8.append("\t");
                        _builder_8.append("System.out.println(\"Set ");
                        String _name_73 = ((MonitoredFunction)fd).getName();
                        _builder_8.append(_name_73, "\t");
                        _builder_8.append("_");
                        _builder_8.append(symbol, "\t");
                        _builder_8.append(" = \" + ");
                        String _name_74 = ((MonitoredFunction)fd).getName();
                        _builder_8.append(_name_74, "\t");
                        _builder_8.append("_");
                        _builder_8.append(symbol, "\t");
                        _builder_8.append(" + \"n\");");
                        _builder_8.newLineIfNotEmpty();
                        _builder_8.append("}");
                        sb.append(_builder_8);
                      } else {
                        if (((((((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.INTEGER) || ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.BOOLEAN)) || 
                          ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.STRING)) || ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.REAL)) || 
                          ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.CHAR))) {
                          String type_4 = AsmMethodsUtil.getBasicTdType(((MonitoredFunction)fd).getCodomain().getName());
                          StringConcatenation _builder_9 = new StringConcatenation();
                          _builder_9.append("public void set_");
                          String _name_75 = ((MonitoredFunction)fd).getName();
                          _builder_9.append(_name_75);
                          _builder_9.append("_fromDomain_");
                          _builder_9.append(symbol);
                          _builder_9.append("(");
                          _builder_9.append(type_4);
                          _builder_9.append(" ");
                          String _name_76 = ((MonitoredFunction)fd).getName();
                          _builder_9.append(_name_76);
                          _builder_9.append("_");
                          _builder_9.append(symbol);
                          _builder_9.append(") {");
                          _builder_9.newLineIfNotEmpty();
                          _builder_9.append("\t");
                          _builder_9.append("this.execution.");
                          String _name_77 = ((MonitoredFunction)fd).getName();
                          _builder_9.append(_name_77, "\t");
                          _builder_9.append(".set(");
                          String _name_78 = asm.getName();
                          _builder_9.append(_name_78, "\t");
                          _builder_9.append(".");
                          String _name_79 = ((EnumTd)dd).getName();
                          _builder_9.append(_name_79, "\t");
                          _builder_9.append(".");
                          _builder_9.append(symbol, "\t");
                          _builder_9.append(", ");
                          String _name_80 = ((MonitoredFunction)fd).getName();
                          _builder_9.append(_name_80, "\t");
                          _builder_9.append("_");
                          _builder_9.append(symbol, "\t");
                          _builder_9.append(");");
                          _builder_9.newLineIfNotEmpty();
                          _builder_9.append("\t");
                          _builder_9.append("System.out.println(\"Set ");
                          String _name_81 = ((MonitoredFunction)fd).getName();
                          _builder_9.append(_name_81, "\t");
                          _builder_9.append("_");
                          _builder_9.append(symbol, "\t");
                          _builder_9.append(" = \" + ");
                          String _name_82 = ((MonitoredFunction)fd).getName();
                          _builder_9.append(_name_82, "\t");
                          _builder_9.append("_");
                          _builder_9.append(symbol, "\t");
                          _builder_9.append(");");
                          _builder_9.newLineIfNotEmpty();
                          _builder_9.append("}");
                          sb.append(_builder_9);
                        } else {
                          AsmMethods.manageNotSupportedDomain(translatorOptions, ((MonitoredFunction)fd).getCodomain().getName());
                        }
                      }
                    }
                  }
                }
              }
            }
          } else {
            Domain _domain_2 = ((MonitoredFunction)fd).getDomain();
            if ((_domain_2 instanceof AbstractTd)) {
              EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
              for (final Function sf : _function_1) {
                if ((sf instanceof StaticFunction)) {
                  if ((((StaticFunction)sf).getCodomain().equals(((MonitoredFunction)fd).getDomain()) && (((StaticFunction)sf).getDomain() == null))) {
                    String symbol = ((StaticFunction)sf).getName();
                    Domain _codomain_4 = ((MonitoredFunction)fd).getCodomain();
                    if ((_codomain_4 instanceof ConcreteDomain)) {
                      String type_3 = AsmMethodsUtil.getConcreteDomainType(asm, fd, ((MonitoredFunction)fd).getCodomain().getName());
                      StringConcatenation _builder_5 = new StringConcatenation();
                      _builder_5.append("public void set_");
                      String _name_36 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_36);
                      _builder_5.append("_fromDomain_");
                      _builder_5.append(symbol);
                      _builder_5.append("(");
                      _builder_5.append(type_3);
                      _builder_5.append(" ");
                      String _name_37 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_37);
                      _builder_5.append("_");
                      _builder_5.append(symbol);
                      _builder_5.append(") {");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("\t");
                      _builder_5.append("this.execution.");
                      String _name_38 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_38, "\t");
                      _builder_5.append(".set(");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("\t");
                      String _name_39 = asm.getName();
                      _builder_5.append(_name_39, "\t");
                      _builder_5.append(".");
                      String _name_40 = ((MonitoredFunction)fd).getDomain().getName();
                      _builder_5.append(_name_40, "\t");
                      _builder_5.append(".get(\"");
                      _builder_5.append(symbol, "\t");
                      _builder_5.append("\"),");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("\t");
                      String _name_41 = asm.getName();
                      _builder_5.append(_name_41, "\t");
                      _builder_5.append(".");
                      String _name_42 = ((MonitoredFunction)fd).getCodomain().getName();
                      _builder_5.append(_name_42, "\t");
                      _builder_5.append(".valueOf(this.execution.");
                      String _name_43 = ((MonitoredFunction)fd).getCodomain().getName();
                      _builder_5.append(_name_43, "\t");
                      _builder_5.append("_elems.get(");
                      String _name_44 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_44, "\t");
                      _builder_5.append("_");
                      _builder_5.append(symbol, "\t");
                      _builder_5.append(")));");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("\t");
                      _builder_5.append("System.out.println(\"Set ");
                      String _name_45 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_45, "\t");
                      _builder_5.append("_");
                      _builder_5.append(symbol, "\t");
                      _builder_5.append(" = \" + ");
                      String _name_46 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_46, "\t");
                      _builder_5.append("_");
                      _builder_5.append(symbol, "\t");
                      _builder_5.append(");");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("}");
                      sb.append(_builder_5);
                    } else {
                      Domain _codomain_5 = ((MonitoredFunction)fd).getCodomain();
                      if ((_codomain_5 instanceof EnumTd)) {
                        StringConcatenation _builder_6 = new StringConcatenation();
                        _builder_6.append("public void set_");
                        String _name_47 = ((MonitoredFunction)fd).getName();
                        _builder_6.append(_name_47);
                        _builder_6.append("_fromDomain_");
                        _builder_6.append(symbol);
                        _builder_6.append("(");
                        String _name_48 = asm.getName();
                        _builder_6.append(_name_48);
                        _builder_6.append(".");
                        String _name_49 = ((MonitoredFunction)fd).getCodomain().getName();
                        _builder_6.append(_name_49);
                        _builder_6.append(" ");
                        String _name_50 = ((MonitoredFunction)fd).getName();
                        _builder_6.append(_name_50);
                        _builder_6.append("_");
                        _builder_6.append(symbol);
                        _builder_6.append(") {");
                        _builder_6.newLineIfNotEmpty();
                        _builder_6.append("\t");
                        _builder_6.append("this.execution.");
                        String _name_51 = ((MonitoredFunction)fd).getName();
                        _builder_6.append(_name_51, "\t");
                        _builder_6.append(".set(");
                        _builder_6.newLineIfNotEmpty();
                        _builder_6.append("\t");
                        String _name_52 = asm.getName();
                        _builder_6.append(_name_52, "\t");
                        _builder_6.append(".");
                        String _name_53 = ((MonitoredFunction)fd).getDomain().getName();
                        _builder_6.append(_name_53, "\t");
                        _builder_6.append(".get(\"");
                        _builder_6.append(symbol, "\t");
                        _builder_6.append("\"),");
                        String _name_54 = ((MonitoredFunction)fd).getName();
                        _builder_6.append(_name_54, "\t");
                        _builder_6.append("_");
                        _builder_6.append(symbol, "\t");
                        _builder_6.append(");");
                        _builder_6.newLineIfNotEmpty();
                        _builder_6.append("\t");
                        _builder_6.append("System.out.println(\"Set ");
                        String _name_55 = ((MonitoredFunction)fd).getName();
                        _builder_6.append(_name_55, "\t");
                        _builder_6.append("_");
                        _builder_6.append(symbol, "\t");
                        _builder_6.append(" = \" + ");
                        String _name_56 = ((MonitoredFunction)fd).getName();
                        _builder_6.append(_name_56, "\t");
                        _builder_6.append("_");
                        _builder_6.append(symbol, "\t");
                        _builder_6.append(");");
                        _builder_6.newLineIfNotEmpty();
                        _builder_6.append("}");
                        sb.append(_builder_6);
                      } else {
                        Domain _codomain_6 = ((MonitoredFunction)fd).getCodomain();
                        if ((_codomain_6 instanceof AbstractTd)) {
                          StringConcatenation _builder_7 = new StringConcatenation();
                          _builder_7.append("public void set_abstract_");
                          String _name_57 = ((MonitoredFunction)fd).getName();
                          _builder_7.append(_name_57);
                          _builder_7.append("_fromDomain_");
                          _builder_7.append(symbol);
                          _builder_7.append("(String ");
                          String _name_58 = ((MonitoredFunction)fd).getName();
                          _builder_7.append(_name_58);
                          _builder_7.append("_");
                          _builder_7.append(symbol);
                          _builder_7.append(") {");
                          _builder_7.newLineIfNotEmpty();
                          _builder_7.append("\t");
                          _builder_7.append("this.execution.");
                          String _name_59 = ((MonitoredFunction)fd).getName();
                          _builder_7.append(_name_59, "\t");
                          _builder_7.append(".set(");
                          _builder_7.newLineIfNotEmpty();
                          _builder_7.append("\t");
                          String _name_60 = asm.getName();
                          _builder_7.append(_name_60, "\t");
                          _builder_7.append(".");
                          String _name_61 = ((MonitoredFunction)fd).getDomain().getName();
                          _builder_7.append(_name_61, "\t");
                          _builder_7.append(".get(\"");
                          _builder_7.append(symbol, "\t");
                          _builder_7.append("\"),");
                          _builder_7.newLineIfNotEmpty();
                          _builder_7.append("\t");
                          String _name_62 = asm.getName();
                          _builder_7.append(_name_62, "\t");
                          _builder_7.append(".");
                          String _name_63 = ((MonitoredFunction)fd).getCodomain().getName();
                          _builder_7.append(_name_63, "\t");
                          _builder_7.append(".get(");
                          String _name_64 = ((MonitoredFunction)fd).getName();
                          _builder_7.append(_name_64, "\t");
                          _builder_7.append("_");
                          _builder_7.append(symbol, "\t");
                          _builder_7.append("));");
                          _builder_7.newLineIfNotEmpty();
                          _builder_7.append("\t");
                          _builder_7.append("System.out.println(\"Set ");
                          String _name_65 = ((MonitoredFunction)fd).getName();
                          _builder_7.append(_name_65, "\t");
                          _builder_7.append("_");
                          _builder_7.append(symbol, "\t");
                          _builder_7.append(" = \" + ");
                          String _name_66 = ((MonitoredFunction)fd).getName();
                          _builder_7.append(_name_66, "\t");
                          _builder_7.append("_");
                          _builder_7.append(symbol, "\t");
                          _builder_7.append(");");
                          _builder_7.newLineIfNotEmpty();
                          _builder_7.append("}");
                          sb.append(_builder_7);
                        } else {
                          boolean _equals_2 = ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL);
                          if (_equals_2) {
                            StringConcatenation _builder_8 = new StringConcatenation();
                            _builder_8.append("public void set_natural_");
                            String _name_67 = ((MonitoredFunction)fd).getName();
                            _builder_8.append(_name_67);
                            _builder_8.append("_fromDomain_");
                            _builder_8.append(symbol);
                            _builder_8.append("(int ");
                            String _name_68 = ((MonitoredFunction)fd).getName();
                            _builder_8.append(_name_68);
                            _builder_8.append("_");
                            _builder_8.append(symbol);
                            _builder_8.append(") {");
                            _builder_8.newLineIfNotEmpty();
                            _builder_8.append("\t");
                            _builder_8.append("this.execution.");
                            String _name_69 = ((MonitoredFunction)fd).getName();
                            _builder_8.append(_name_69, "\t");
                            _builder_8.append(".set(");
                            _builder_8.newLineIfNotEmpty();
                            _builder_8.append("\t");
                            String _name_70 = asm.getName();
                            _builder_8.append(_name_70, "\t");
                            _builder_8.append(".");
                            String _name_71 = ((MonitoredFunction)fd).getDomain().getName();
                            _builder_8.append(_name_71, "\t");
                            _builder_8.append(".get(\"");
                            _builder_8.append(symbol, "\t");
                            _builder_8.append("\"),");
                            String _name_72 = ((MonitoredFunction)fd).getName();
                            _builder_8.append(_name_72, "\t");
                            _builder_8.append("_");
                            _builder_8.append(symbol, "\t");
                            _builder_8.append(");");
                            _builder_8.newLineIfNotEmpty();
                            _builder_8.append("\t");
                            _builder_8.append("System.out.println(\"Set ");
                            String _name_73 = ((MonitoredFunction)fd).getName();
                            _builder_8.append(_name_73, "\t");
                            _builder_8.append("_");
                            _builder_8.append(symbol, "\t");
                            _builder_8.append(" = \" + ");
                            String _name_74 = ((MonitoredFunction)fd).getName();
                            _builder_8.append(_name_74, "\t");
                            _builder_8.append("_");
                            _builder_8.append(symbol, "\t");
                            _builder_8.append(" + \"n\");");
                            _builder_8.newLineIfNotEmpty();
                            _builder_8.append("}");
                            sb.append(_builder_8);
                          } else {
                            if (((((((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.INTEGER) || ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.BOOLEAN)) || 
                              ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.STRING)) || ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.REAL)) || 
                              ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.CHAR))) {
                              String type_4 = AsmMethodsUtil.getBasicTdType(((MonitoredFunction)fd).getCodomain().getName());
                              StringConcatenation _builder_9 = new StringConcatenation();
                              _builder_9.append("public void set_");
                              String _name_75 = ((MonitoredFunction)fd).getName();
                              _builder_9.append(_name_75);
                              _builder_9.append("_fromDomain_");
                              _builder_9.append(symbol);
                              _builder_9.append("(");
                              _builder_9.append(type_4);
                              _builder_9.append(" ");
                              String _name_76 = ((MonitoredFunction)fd).getName();
                              _builder_9.append(_name_76);
                              _builder_9.append("_");
                              _builder_9.append(symbol);
                              _builder_9.append(") {");
                              _builder_9.newLineIfNotEmpty();
                              _builder_9.append("\t");
                              _builder_9.append("this.execution.");
                              String _name_77 = ((MonitoredFunction)fd).getName();
                              _builder_9.append(_name_77, "\t");
                              _builder_9.append(".set(");
                              _builder_9.newLineIfNotEmpty();
                              _builder_9.append("\t");
                              String _name_78 = asm.getName();
                              _builder_9.append(_name_78, "\t");
                              _builder_9.append(".");
                              String _name_79 = ((MonitoredFunction)fd).getDomain().getName();
                              _builder_9.append(_name_79, "\t");
                              _builder_9.append(".get(\"");
                              _builder_9.append(symbol, "\t");
                              _builder_9.append("\"),");
                              String _name_80 = ((MonitoredFunction)fd).getName();
                              _builder_9.append(_name_80, "\t");
                              _builder_9.append("_");
                              _builder_9.append(symbol, "\t");
                              _builder_9.append(");");
                              _builder_9.newLineIfNotEmpty();
                              _builder_9.append("\t");
                              _builder_9.append("System.out.println(\"Set ");
                              String _name_81 = ((MonitoredFunction)fd).getName();
                              _builder_9.append(_name_81, "\t");
                              _builder_9.append("_");
                              _builder_9.append(symbol, "\t");
                              _builder_9.append(" = \" + ");
                              String _name_82 = ((MonitoredFunction)fd).getName();
                              _builder_9.append(_name_82, "\t");
                              _builder_9.append("_");
                              _builder_9.append(symbol, "\t");
                              _builder_9.append(");");
                              _builder_9.newLineIfNotEmpty();
                              _builder_9.append("}");
                              sb.append(_builder_9);
                            } else {
                              AsmMethods.manageNotSupportedDomain(translatorOptions, ((MonitoredFunction)fd).getCodomain().getName());
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            } else {
              Domain _domain_3 = ((MonitoredFunction)fd).getDomain();
              if ((_domain_3 instanceof ConcreteDomain)) {
                EList<Domain> _domain_4 = asm.getHeaderSection().getSignature().getDomain();
                for (final Domain cd_1 : _domain_4) {
                  if ((cd_1 instanceof ConcreteDomain)) {
                    boolean _equals_3 = ((ConcreteDomain)cd_1).getName().equals(((MonitoredFunction)fd).getDomain().getName());
                    if (_equals_3) {
                      final String elemsString = new TermToJava(asm).visit(((ConcreteDomain)cd_1).getDefinition().getBody());
                      final Function1<String, String> _function_2 = (String it) -> {
                        int _lastIndexOf = it.lastIndexOf(".");
                        int _plus = (_lastIndexOf + 1);
                        return it.substring(_plus);
                      };
                      final List<String> elems = ListExtensions.<String, String>map(((List<String>)Conversions.doWrapArray(elemsString.replace("(", "").replace(")", "").split(", "))), _function_2);
                      for (final String elem : elems) {
                        {
                          String symbol_1 = elem;
                          String originalDomain = new DomainToJavaString(asm).visit(
                            ((ConcreteDomain)cd_1).getDefinition().getDefinedDomain().getTypeDomain());
                          boolean _contains_1 = AsmMethodsUtil.basicTdList.contains(originalDomain);
                          boolean _not = (!_contains_1);
                          if (_not) {
                            symbol_1 = asm.getName().concat(".").concat(originalDomain).concat(".").concat(elem);
                          }
                          Domain _codomain_7 = ((MonitoredFunction)fd).getCodomain();
                          if ((_codomain_7 instanceof ConcreteDomain)) {
                            String type_5 = AsmMethodsUtil.getConcreteDomainType(asm, fd, ((MonitoredFunction)fd).getCodomain().getName());
                            StringConcatenation _builder_10 = new StringConcatenation();
                            _builder_10.append("public void set_");
                            String _name_83 = ((MonitoredFunction)fd).getName();
                            _builder_10.append(_name_83);
                            _builder_10.append("_fromDomain_");
                            _builder_10.append(elem);
                            _builder_10.append("(");
                            _builder_10.append(type_5);
                            _builder_10.append(" ");
                            String _name_84 = ((MonitoredFunction)fd).getName();
                            _builder_10.append(_name_84);
                            _builder_10.append("_");
                            _builder_10.append(elem);
                            _builder_10.append(") {");
                            _builder_10.newLineIfNotEmpty();
                            _builder_10.append("\t");
                            _builder_10.append("this.execution.");
                            String _name_85 = ((MonitoredFunction)fd).getName();
                            _builder_10.append(_name_85, "\t");
                            _builder_10.append(".set(");
                            _builder_10.newLineIfNotEmpty();
                            _builder_10.append("\t");
                            String _name_86 = asm.getName();
                            _builder_10.append(_name_86, "\t");
                            _builder_10.append(".");
                            String _name_87 = ((MonitoredFunction)fd).getDomain().getName();
                            _builder_10.append(_name_87, "\t");
                            _builder_10.append(".valueOf(");
                            _builder_10.append(symbol_1, "\t");
                            _builder_10.append("),");
                            _builder_10.newLineIfNotEmpty();
                            _builder_10.append("\t");
                            String _name_88 = asm.getName();
                            _builder_10.append(_name_88, "\t");
                            _builder_10.append(".");
                            String _name_89 = ((MonitoredFunction)fd).getCodomain().getName();
                            _builder_10.append(_name_89, "\t");
                            _builder_10.append(".valueOf(");
                            String _name_90 = ((MonitoredFunction)fd).getName();
                            _builder_10.append(_name_90, "\t");
                            _builder_10.append("_");
                            _builder_10.append(elem, "\t");
                            _builder_10.append("));");
                            _builder_10.newLineIfNotEmpty();
                            _builder_10.append("\t");
                            _builder_10.append("System.out.println(\"Set ");
                            String _name_91 = ((MonitoredFunction)fd).getName();
                            _builder_10.append(_name_91, "\t");
                            _builder_10.append("_");
                            _builder_10.append(elem, "\t");
                            _builder_10.append(" = \" + ");
                            String _name_92 = ((MonitoredFunction)fd).getName();
                            _builder_10.append(_name_92, "\t");
                            _builder_10.append("_");
                            _builder_10.append(elem, "\t");
                            _builder_10.append(");");
                            _builder_10.newLineIfNotEmpty();
                            _builder_10.append("}");
                            sb.append(_builder_10);
                          } else {
                            Domain _codomain_8 = ((MonitoredFunction)fd).getCodomain();
                            if ((_codomain_8 instanceof EnumTd)) {
                              StringConcatenation _builder_11 = new StringConcatenation();
                              _builder_11.append("public void set_");
                              String _name_93 = ((MonitoredFunction)fd).getName();
                              _builder_11.append(_name_93);
                              _builder_11.append("_fromDomain_");
                              _builder_11.append(elem);
                              _builder_11.append("(");
                              String _name_94 = asm.getName();
                              _builder_11.append(_name_94);
                              _builder_11.append(".");
                              String _name_95 = ((MonitoredFunction)fd).getCodomain().getName();
                              _builder_11.append(_name_95);
                              _builder_11.append(" ");
                              String _name_96 = ((MonitoredFunction)fd).getName();
                              _builder_11.append(_name_96);
                              _builder_11.append("_");
                              _builder_11.append(elem);
                              _builder_11.append(") {");
                              _builder_11.newLineIfNotEmpty();
                              _builder_11.append("\t");
                              _builder_11.append("this.execution.");
                              String _name_97 = ((MonitoredFunction)fd).getName();
                              _builder_11.append(_name_97, "\t");
                              _builder_11.append(".set(");
                              _builder_11.newLineIfNotEmpty();
                              _builder_11.append("\t");
                              String _name_98 = asm.getName();
                              _builder_11.append(_name_98, "\t");
                              _builder_11.append(".");
                              String _name_99 = ((MonitoredFunction)fd).getDomain().getName();
                              _builder_11.append(_name_99, "\t");
                              _builder_11.append(".valueOf(");
                              _builder_11.append(symbol_1, "\t");
                              _builder_11.append("),");
                              String _name_100 = ((MonitoredFunction)fd).getName();
                              _builder_11.append(_name_100, "\t");
                              _builder_11.append("_");
                              _builder_11.append(elem, "\t");
                              _builder_11.append(");");
                              _builder_11.newLineIfNotEmpty();
                              _builder_11.append("\t");
                              _builder_11.append("System.out.println(\"Set ");
                              String _name_101 = ((MonitoredFunction)fd).getName();
                              _builder_11.append(_name_101, "\t");
                              _builder_11.append("_");
                              _builder_11.append(elem, "\t");
                              _builder_11.append(" = \" + ");
                              String _name_102 = ((MonitoredFunction)fd).getName();
                              _builder_11.append(_name_102, "\t");
                              _builder_11.append("_");
                              _builder_11.append(elem, "\t");
                              _builder_11.append(");");
                              _builder_11.newLineIfNotEmpty();
                              _builder_11.append("}");
                              sb.append(_builder_11);
                            } else {
                              Domain _codomain_9 = ((MonitoredFunction)fd).getCodomain();
                              if ((_codomain_9 instanceof AbstractTd)) {
                                StringConcatenation _builder_12 = new StringConcatenation();
                                _builder_12.append("public void set_abstract_");
                                String _name_103 = ((MonitoredFunction)fd).getName();
                                _builder_12.append(_name_103);
                                _builder_12.append("_fromDomain_");
                                _builder_12.append(elem);
                                _builder_12.append("(String ");
                                String _name_104 = ((MonitoredFunction)fd).getName();
                                _builder_12.append(_name_104);
                                _builder_12.append("_");
                                _builder_12.append(elem);
                                _builder_12.append(") {");
                                _builder_12.newLineIfNotEmpty();
                                _builder_12.append("\t");
                                _builder_12.append("this.execution.");
                                String _name_105 = ((MonitoredFunction)fd).getName();
                                _builder_12.append(_name_105, "\t");
                                _builder_12.append(".set(");
                                _builder_12.newLineIfNotEmpty();
                                _builder_12.append("\t");
                                String _name_106 = asm.getName();
                                _builder_12.append(_name_106, "\t");
                                _builder_12.append(".");
                                String _name_107 = ((MonitoredFunction)fd).getDomain().getName();
                                _builder_12.append(_name_107, "\t");
                                _builder_12.append(".valueOf(");
                                _builder_12.append(symbol_1, "\t");
                                _builder_12.append("),");
                                _builder_12.newLineIfNotEmpty();
                                _builder_12.append("\t");
                                String _name_108 = asm.getName();
                                _builder_12.append(_name_108, "\t");
                                _builder_12.append(".");
                                String _name_109 = ((MonitoredFunction)fd).getCodomain().getName();
                                _builder_12.append(_name_109, "\t");
                                _builder_12.append(".get(");
                                String _name_110 = ((MonitoredFunction)fd).getName();
                                _builder_12.append(_name_110, "\t");
                                _builder_12.append("_");
                                _builder_12.append(elem, "\t");
                                _builder_12.append("));");
                                _builder_12.newLineIfNotEmpty();
                                _builder_12.append("\t");
                                _builder_12.append("System.out.println(\"Set ");
                                String _name_111 = ((MonitoredFunction)fd).getName();
                                _builder_12.append(_name_111, "\t");
                                _builder_12.append("_");
                                _builder_12.append(elem, "\t");
                                _builder_12.append(" = \" + ");
                                String _name_112 = ((MonitoredFunction)fd).getName();
                                _builder_12.append(_name_112, "\t");
                                _builder_12.append("_");
                                _builder_12.append(elem, "\t");
                                _builder_12.append(");");
                                _builder_12.newLineIfNotEmpty();
                                _builder_12.append("}");
                                sb.append(_builder_12);
                              } else {
                                boolean _equals_4 = ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.NATURAL);
                                if (_equals_4) {
                                  StringConcatenation _builder_13 = new StringConcatenation();
                                  _builder_13.append("public void set_natural_");
                                  String _name_113 = ((MonitoredFunction)fd).getName();
                                  _builder_13.append(_name_113);
                                  _builder_13.append("_fromDomain_");
                                  _builder_13.append(elem);
                                  _builder_13.append("(int ");
                                  String _name_114 = ((MonitoredFunction)fd).getName();
                                  _builder_13.append(_name_114);
                                  _builder_13.append("_");
                                  _builder_13.append(elem);
                                  _builder_13.append(") {");
                                  _builder_13.newLineIfNotEmpty();
                                  _builder_13.append("\t");
                                  _builder_13.append("this.execution.");
                                  String _name_115 = ((MonitoredFunction)fd).getName();
                                  _builder_13.append(_name_115, "\t");
                                  _builder_13.append(".set(");
                                  _builder_13.newLineIfNotEmpty();
                                  _builder_13.append("\t");
                                  String _name_116 = asm.getName();
                                  _builder_13.append(_name_116, "\t");
                                  _builder_13.append(".");
                                  String _name_117 = ((MonitoredFunction)fd).getDomain().getName();
                                  _builder_13.append(_name_117, "\t");
                                  _builder_13.append(".valueOf(");
                                  _builder_13.append(symbol_1, "\t");
                                  _builder_13.append("),");
                                  String _name_118 = ((MonitoredFunction)fd).getName();
                                  _builder_13.append(_name_118, "\t");
                                  _builder_13.append("_");
                                  _builder_13.append(elem, "\t");
                                  _builder_13.append(");");
                                  _builder_13.newLineIfNotEmpty();
                                  _builder_13.append("\t");
                                  _builder_13.append("System.out.println(\"Set ");
                                  String _name_119 = ((MonitoredFunction)fd).getName();
                                  _builder_13.append(_name_119, "\t");
                                  _builder_13.append("_");
                                  _builder_13.append(elem, "\t");
                                  _builder_13.append(" = \" + ");
                                  String _name_120 = ((MonitoredFunction)fd).getName();
                                  _builder_13.append(_name_120, "\t");
                                  _builder_13.append("_");
                                  _builder_13.append(elem, "\t");
                                  _builder_13.append(" + \"n\");");
                                  _builder_13.newLineIfNotEmpty();
                                  _builder_13.append("}");
                                  sb.append(_builder_13);
                                } else {
                                  if (((((((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.INTEGER) || 
                                    ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.BOOLEAN)) || ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.STRING)) || 
                                    ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.REAL)) || ((MonitoredFunction)fd).getCodomain().getName().equals(AsmMethods.CHAR))) {
                                    String type_6 = AsmMethodsUtil.getBasicTdType(((MonitoredFunction)fd).getCodomain().getName());
                                    StringConcatenation _builder_14 = new StringConcatenation();
                                    _builder_14.append("public void set_");
                                    String _name_121 = ((MonitoredFunction)fd).getName();
                                    _builder_14.append(_name_121);
                                    _builder_14.append("_fromDomain_");
                                    _builder_14.append(elem);
                                    _builder_14.append("(");
                                    _builder_14.append(type_6);
                                    _builder_14.append(" ");
                                    String _name_122 = ((MonitoredFunction)fd).getName();
                                    _builder_14.append(_name_122);
                                    _builder_14.append("_");
                                    _builder_14.append(elem);
                                    _builder_14.append(") {");
                                    _builder_14.newLineIfNotEmpty();
                                    _builder_14.append("\t");
                                    _builder_14.append("this.execution.");
                                    String _name_123 = ((MonitoredFunction)fd).getName();
                                    _builder_14.append(_name_123, "\t");
                                    _builder_14.append(".set(");
                                    _builder_14.newLineIfNotEmpty();
                                    _builder_14.append("\t");
                                    String _name_124 = asm.getName();
                                    _builder_14.append(_name_124, "\t");
                                    _builder_14.append(".");
                                    String _name_125 = ((MonitoredFunction)fd).getDomain().getName();
                                    _builder_14.append(_name_125, "\t");
                                    _builder_14.append(".valueOf(");
                                    _builder_14.append(symbol_1, "\t");
                                    _builder_14.append("),");
                                    String _name_126 = ((MonitoredFunction)fd).getName();
                                    _builder_14.append(_name_126, "\t");
                                    _builder_14.append("_");
                                    _builder_14.append(elem, "\t");
                                    _builder_14.append(");");
                                    _builder_14.newLineIfNotEmpty();
                                    _builder_14.append("\t");
                                    _builder_14.append("System.out.println(\"Set ");
                                    String _name_127 = ((MonitoredFunction)fd).getName();
                                    _builder_14.append(_name_127, "\t");
                                    _builder_14.append("_");
                                    _builder_14.append(elem, "\t");
                                    _builder_14.append(" = \" + ");
                                    String _name_128 = ((MonitoredFunction)fd).getName();
                                    _builder_14.append(_name_128, "\t");
                                    _builder_14.append("_");
                                    _builder_14.append(elem, "\t");
                                    _builder_14.append(");");
                                    _builder_14.newLineIfNotEmpty();
                                    _builder_14.append("}");
                                    sb.append(_builder_14);
                                  } else {
                                    AsmMethods.manageNotSupportedDomain(translatorOptions, ((MonitoredFunction)fd).getCodomain().getName());
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
              } else {
                AsmMethods.manageNotSupportedDomain(translatorOptions, ((MonitoredFunction)fd).getDomain().getName());
              }
            }
          }
        }
      }
    }
    sb.append(System.lineSeparator());
    return sb.toString();
  }

  /**
   * Handles the case of an unrecognized domain,
   * if the ignoreDomainException option is active it prints the error,
   * otherwise it throws an exception and blocks the program flow.
   * 
   * @param translatorOptions translator options
   * @param domainName name of the unsupported domain
   */
  private static String manageNotSupportedDomain(final TranslatorOptions translatorOptions, final String domainName) {
    String _xifexpression = null;
    boolean _ignoreDomainException = translatorOptions.getIgnoreDomainException();
    if (_ignoreDomainException) {
      _xifexpression = InputOutput.<String>print((("ERROR! domain not supported: " + domainName) + "\n"));
    } else {
      throw new DomainNotSupportedException((("The Domain " + domainName) + " is not supported by the ATG class."));
    }
    return _xifexpression;
  }
}
