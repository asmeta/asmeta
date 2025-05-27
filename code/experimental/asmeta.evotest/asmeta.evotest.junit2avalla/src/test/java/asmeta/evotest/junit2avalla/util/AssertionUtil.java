package asmeta.evotest.junit2avalla.util;

import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;

public class AssertionUtil {

  public static JavaAssertionTerm getAssertion0(){
    JavaAssertionTerm javaAssertionTerm = new JavaAssertionTerm();
    javaAssertionTerm.setType("AssertEquals");
    javaAssertionTerm.setExpected("\"Scegli il tipo di pizza desiderata:\"");
    javaAssertionTerm.setActual("registroDiCassav3_ASM0.get_outMess()");
    return  javaAssertionTerm;
  }

  public static  JavaAssertionTerm getAssertion1(){
    JavaAssertionTerm javaAssertionTerm = new JavaAssertionTerm();
    javaAssertionTerm.setType("AssertEquals");
    javaAssertionTerm.setExpected("0");
    javaAssertionTerm.setActual("registroDiCassav3_ASM0.get_totale()");
    return  javaAssertionTerm;
  }

  public static JavaAssertionTerm getAssertion2(){
    JavaAssertionTerm javaAssertionTerm = new JavaAssertionTerm();
    javaAssertionTerm.setType("AssertEquals");
    javaAssertionTerm.setExpected("RegistroDiCassav3Sig.Stati.SCEGLI_TIPO_DI_PIZZA");
    javaAssertionTerm.setActual("registroDiCassav3_ASM0.get_statoCassa()");
    return  javaAssertionTerm;
  }

}
