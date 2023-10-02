package org.asmeta.tocpp.tocunit;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class AsmTsToCatch2Test extends TestSuiteTranslator {
  private int counter = 0;

  public AsmTsToCatch2Test(final AsmCollection asm) {
    super(asm, "REQUIRE");
  }

  public CharSequence convertTestSuite(final AsmTestSuite testSuite) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#define CATCH_CONFIG_MAIN  // This tells Catch to provide a main() - only do this in one cpp file");
    _builder.newLine();
    _builder.append("#include \"catch.hpp\"");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include <iostream>");
    _builder.newLine();
    _builder.append("#include \"�asmName�.h\"");
    _builder.newLine();
    _builder.append("using namespace std;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("�getAbstractDomain(asm)�");
    _builder.newLine();
    _builder.newLine();
    _builder.append("�FOR t : testSuite�");
    _builder.newLine();
    _builder.append("�printTestCase(t)�");
    _builder.newLine();
    _builder.append("//�counter++�");
    _builder.newLine();
    _builder.append("�ENDFOR�");
    _builder.newLine();
    return _builder;
  }

  public CharSequence printTestCase(final AsmTestSequence test) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("TEST_CASE( \"my_test_�counter�\", \"my_test_�counter�\"){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// instance of the SUT");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("�asmName� �asmName.toLowerCase�;\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("�FOR t : test.allInstructions()�");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// state ");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�printState(t,test.allInstructions.get(0)===t)�");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// call main rule");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�asmName.toLowerCase�.�mainRuleName�();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�asmName.toLowerCase�.fireUpdateSet();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("�ENDFOR�");
    _builder.newLine();
    _builder.append("}");
    return _builder;
  }
}
