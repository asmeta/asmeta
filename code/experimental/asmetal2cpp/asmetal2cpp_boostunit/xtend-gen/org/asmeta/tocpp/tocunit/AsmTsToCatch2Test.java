package org.asmeta.tocpp.tocunit;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.specification.location.Location;
import java.util.List;
import java.util.Map;
import org.asmeta.tocpp.tocunit.TestSuiteTranslator;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class AsmTsToCatch2Test extends TestSuiteTranslator {
  private int counter = 0;
  
  public AsmTsToCatch2Test(final AsmCollection asm) {
    super(asm, "REQUIRE");
  }
  
  @Override
  public CharSequence convertTestSuite(final AsmTestSuite testSuite) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#define CATCH_CONFIG_MAIN  // This tells Catch to provide a main() - only do this in one cpp file");
    _builder.newLine();
    _builder.append("#include \"catch.hpp\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include <iostream>");
    _builder.newLine();
    _builder.append("#include \"");
    _builder.append(this.asmName);
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.newLine();
    this.getAbstractDomain(this.asm);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      for(final AsmTestSequence t : testSuite) {
        CharSequence _printTestCase = this.printTestCase(t);
        _builder.append(_printTestCase);
        _builder.newLineIfNotEmpty();
        _builder.append("//");
        int _plusPlus = this.counter++;
        _builder.append(_plusPlus);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence printTestCase(final AsmTestSequence test) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("TEST_CASE( \"my_test_");
    _builder.append(this.counter);
    _builder.append("\", \"my_test_");
    _builder.append(this.counter);
    _builder.append("\"){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("// instance of the SUT");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(this.asmName, "\t");
    _builder.append(" ");
    String _lowerCase = this.asmName.toLowerCase();
    _builder.append(_lowerCase, "\t");
    _builder.append(";\t");
    _builder.newLineIfNotEmpty();
    {
      List<Map<Location, String>> _allInstructions = test.allInstructions();
      for(final Map<Location, String> t : _allInstructions) {
        _builder.append("\t");
        _builder.append("// state ");
        _builder.newLine();
        _builder.append("\t");
        Map<Location, String> _get = test.allInstructions().get(0);
        boolean _tripleEquals = (_get == t);
        StringConcatenation _printState = this.printState(t, _tripleEquals);
        _builder.append(_printState, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("// call main rule");
        _builder.newLine();
        _builder.append("\t");
        String _lowerCase_1 = this.asmName.toLowerCase();
        _builder.append(_lowerCase_1, "\t");
        _builder.append(".");
        _builder.append(this.mainRuleName, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _lowerCase_2 = this.asmName.toLowerCase();
        _builder.append(_lowerCase_2, "\t");
        _builder.append(".fireUpdateSet();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    return _builder;
  }
}
