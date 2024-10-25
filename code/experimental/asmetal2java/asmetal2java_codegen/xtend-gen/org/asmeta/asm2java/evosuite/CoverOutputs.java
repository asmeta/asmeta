package org.asmeta.asm2java.evosuite;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import org.asmeta.asm2java.ToString;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class CoverOutputs {
  public static String coverOutputBranches(final Asm asm) {
    final StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof MonitoredFunction) || (fd instanceof ControlledFunction))) {
        Domain _domain = fd.getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          Domain _codomain = fd.getCodomain();
          if ((_codomain instanceof EnumTd)) {
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
                      _builder_2.append("\t\t\t\t\t\t\t\t\t\t");
                      _builder_2.append("System.out.println(\"Branch ");
                      String _name_2 = fd.getCodomain().getName();
                      _builder_2.append(_name_2, "\t\t\t\t\t\t\t\t\t\t");
                      _builder_2.append(" ");
                      _builder_2.append(symbol, "\t\t\t\t\t\t\t\t\t\t");
                      _builder_2.append(" covered\");");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t\t\t\t\t\t\t\t\t\t");
                      _builder_2.append("break;");
                      _append_2.append(_builder_2);
                      sb.append(System.lineSeparator());
                    }
                  }
                }
              }
            }
            sb.append("\t\t\t");
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("}");
            sb.append(_builder_2);
            sb.append(System.lineSeparator());
            sb.append("\t\t");
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("}");
            sb.append(_builder_3);
            sb.append(System.lineSeparator());
          }
        }
      }
    }
    return sb.toString();
  }

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
        Domain _domain = fd.getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          Domain _codomain = fd.getCodomain();
          if ((_codomain instanceof EnumTd)) {
            sb.append(System.lineSeparator());
            StringBuffer _append_1 = sb.append("\t\t");
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("cover_");
            String _name = fd.getName();
            _builder_2.append(_name);
            _builder_2.append("();");
            _append_1.append(_builder_2);
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
        }
      }
    }
    return sb.toString();
  }
}
