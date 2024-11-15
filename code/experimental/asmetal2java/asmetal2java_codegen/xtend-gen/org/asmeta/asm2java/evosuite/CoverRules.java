package org.asmeta.asm2java.evosuite;

import java.util.List;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Contains the methods to cover the rules of the abstract state machine (ASM).
 */
@SuppressWarnings("all")
public class CoverRules {
  /**
   * Create a method that calls all the cover rules functions
   * 
   * @param rules RulesGetter interface with which request the
   * 		rule to examine the branches.
   */
  public static String coverRulesFunction(final RulesGetter rules) {
    final StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("* Calls all rules covering functions.");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("*/");
    sb.append(_builder);
    sb.append(System.lineSeparator());
    StringBuffer _append = sb.append("\t");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("private void coverRules(){");
    _append.append(_builder_1);
    sb.append(System.lineSeparator());
    Set<String> _rulesName = rules.getRulesName();
    for (final String rule : _rulesName) {
      {
        StringBuffer _append_1 = sb.append("\t\t");
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("cover_");
        _builder_2.append(rule);
        _builder_2.append("_branches();");
        _append_1.append(_builder_2);
        sb.append(System.lineSeparator());
      }
    }
    StringBuffer _append_1 = sb.append("\t");
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("}");
    _append_1.append(_builder_2);
    sb.append(System.lineSeparator());
    return sb.toString();
  }

  /**
   * Create a method for the rule to cover all its branches
   * 
   * @param rules RulesGetter interface with which request the
   * 		rule to examine the branches.
   */
  public static String coverAllRules(final RulesGetter rules) {
    final StringBuffer sb = new StringBuffer();
    Set<String> _rulesName = rules.getRulesName();
    for (final String rule : _rulesName) {
      {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/**");
        _builder.newLine();
        _builder.append("* Cover all the branches of the rule ");
        _builder.append(rule);
        _builder.append(".");
        _builder.newLineIfNotEmpty();
        _builder.append("*/");
        sb.append(_builder);
        sb.append(System.lineSeparator());
        StringBuffer _append = sb.append("\t");
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("private void cover_");
        _builder_1.append(rule);
        _builder_1.append("_branches(){");
        _append.append(_builder_1);
        sb.append(System.lineSeparator());
        List<String> _ruleBranches = rules.getRuleBranches(rule);
        for (final String branch : _ruleBranches) {
          {
            StringBuffer _append_1 = sb.append("\t\t");
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("if( this.execution.");
            _builder_2.append(branch);
            _builder_2.append(" ){");
            _append_1.append(_builder_2);
            sb.append(System.lineSeparator());
            StringBuffer _append_2 = sb.append("\t\t\t");
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("System.out.println(\"");
            _builder_3.append(branch);
            _builder_3.append(" covered\"); ");
            _append_2.append(_builder_3);
            sb.append(System.lineSeparator());
            StringBuffer _append_3 = sb.append("\t\t");
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append("}");
            _append_3.append(_builder_4);
            sb.append(System.lineSeparator());
          }
        }
        StringBuffer _append_1 = sb.append("\t");
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("}");
        _append_1.append(_builder_2);
        sb.append(System.lineSeparator());
      }
    }
    return sb.toString();
  }
}
