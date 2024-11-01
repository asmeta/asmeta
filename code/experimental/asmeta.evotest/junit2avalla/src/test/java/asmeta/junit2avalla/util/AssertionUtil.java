package asmeta.junit2avalla.util;

import  asmeta.junit2avalla.model.terms.JavaAssertionTerm;

public class AssertionUtil {

  public static JavaAssertionTerm getAssertion0(){
    JavaAssertionTerm javaAssertionTerm = new JavaAssertionTerm();
    javaAssertionTerm.setType("AssertEquals");
    javaAssertionTerm.setActual("\"Scegli il tipo di pizza desiderata:\"");
    javaAssertionTerm.setExpected("registroDiCassav3_ASM0.get_outMess()");
    return  javaAssertionTerm;
  }

  public static  JavaAssertionTerm getAssertion1(){
    JavaAssertionTerm javaAssertionTerm = new JavaAssertionTerm();
    javaAssertionTerm.setType("AssertEquals");
    javaAssertionTerm.setActual("0");
    javaAssertionTerm.setExpected("registroDiCassav3_ASM0.get_totale()");
    return  javaAssertionTerm;
  }

  public static JavaAssertionTerm getAssertion2(){
    JavaAssertionTerm javaAssertionTerm = new JavaAssertionTerm();
    javaAssertionTerm.setType("AssertEquals");
    javaAssertionTerm.setActual("RegistroDiCassav3Sig.Stati.SCEGLI_TIPO_DI_PIZZA");
    javaAssertionTerm.setExpected("registroDiCassav3_ASM0.get_statoCassa()");
    return  javaAssertionTerm;
  }

}
