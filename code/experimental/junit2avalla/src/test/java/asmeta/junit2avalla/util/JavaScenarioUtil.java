package asmeta.junit2avalla.util;

import java.util.ArrayList;
import java.util.List;
import asmeta.junit2avalla.model.terms.JavaArgumentTerm;

public class JavaScenarioUtil {

  public static String getJavaFile_RegistroDiCassa(){

    return
        "package org.evoservice.RegistroDiCassa;\n"
            + "\n"
            + "import org.junit.Test;\n"
            + "import static org.junit.Assert.*;\n"
            + "\n"
            + "import org.evosuite.runtime.EvoRunner;\n"
            + "import org.evosuite.runtime.EvoRunnerParameters;\n"
            + "import org.junit.runner.RunWith;\n"
            + "\n"
            + "@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) \n"
            + "public class RegistroDiCassav3_ASM_ESTest extends RegistroDiCassav3_ASM_ESTest_scaffolding {\n"
            + "\n"
            + "  @Test(timeout = 4000)\n"
            + "  public void test0()  throws Throwable  {\n"
            + "      RegistroDiCassav3_ASM registroDiCassav3_ASM0 = new RegistroDiCassav3_ASM();\n"
            + "      assertNotNull(registroDiCassav3_ASM0);\n"
            + "      assertFalse(registroDiCassav3_ASM0.isFinalState());\n"
            + "      assertEquals(RegistroDiCassav3Sig.Stati.ATTENDI_ORDINAZIONI, registroDiCassav3_ASM0.get_statoCassa());\n"
            + "      assertNull(registroDiCassav3_ASM0.get_outMess());\n"
            + "      assertEquals(0, registroDiCassav3_ASM0.get_totale());\n"
            + "      \n"
            + "      RegistroDiCassav3Sig.Servizio registroDiCassav3Sig_Servizio0 = RegistroDiCassav3Sig.Servizio.NEWORDINE;\n"
            + "      RegistroDiCassav3Sig.AggiungiPizza registroDiCassav3Sig_AggiungiPizza0 = RegistroDiCassav3Sig.AggiungiPizza.NO;\n"
            + "      RegistroDiCassav3Sig.SelezioneTipoDiPizza registroDiCassav3Sig_SelezioneTipoDiPizza0 = RegistroDiCassav3Sig.SelezioneTipoDiPizza.OTHER;\n"
            + "      RegistroDiCassav3Sig.AggiungiPizza registroDiCassav3Sig_AggiungiPizza1 = RegistroDiCassav3Sig.AggiungiPizza.SI;\n"
            + "      RegistroDiCassav3Sig.SelezioneTipoDiPizza registroDiCassav3Sig_SelezioneTipoDiPizza1 = RegistroDiCassav3Sig.SelezioneTipoDiPizza.STANDARD;\n"
            + "      registroDiCassav3_ASM0.step(registroDiCassav3Sig_Servizio0, \"margherita\", registroDiCassav3Sig_AggiungiPizza1, registroDiCassav3Sig_SelezioneTipoDiPizza1, 2, 2);\n"
            + "      assertFalse(registroDiCassav3Sig_AggiungiPizza1.equals((Object)registroDiCassav3Sig_AggiungiPizza0));\n"
            + "      assertFalse(registroDiCassav3Sig_SelezioneTipoDiPizza1.equals((Object)registroDiCassav3Sig_SelezioneTipoDiPizza0));\n"
            + "      assertNotSame(registroDiCassav3Sig_AggiungiPizza1, registroDiCassav3Sig_AggiungiPizza0);\n"
            + "      assertNotSame(registroDiCassav3Sig_SelezioneTipoDiPizza1, registroDiCassav3Sig_SelezioneTipoDiPizza0);\n"
            + "      assertFalse(registroDiCassav3_ASM0.isFinalState());\n"
            + "      assertEquals(\"Scegli il tipo di pizza desiderata:\", registroDiCassav3_ASM0.get_outMess());\n"
            + "      assertEquals(0, registroDiCassav3_ASM0.get_totale());\n"
            + "      assertEquals(RegistroDiCassav3Sig.Stati.SCEGLI_TIPO_DI_PIZZA, registroDiCassav3_ASM0.get_statoCassa());\n"
            + "     \n"
            + "     }\n"
            + "}";

  }

  public static List<JavaArgumentTerm> getArgumentList_RegistroDiCassa() {

    List<JavaArgumentTerm> stepFunctionArgsList = new ArrayList<>();

    JavaArgumentTerm javaArgumentTerm0 = new JavaArgumentTerm();
    javaArgumentTerm0.setName("servizioSelezionato");
    javaArgumentTerm0.setType("RegistroDiCassa.Servizio");
    javaArgumentTerm0.setPrimitive(false);
    stepFunctionArgsList.add(javaArgumentTerm0);

    JavaArgumentTerm javaArgumentTerm1 = new JavaArgumentTerm();
    javaArgumentTerm1.setName("pizzaInserita");
    javaArgumentTerm1.setType("String");
    javaArgumentTerm1.setPrimitive(true);
    stepFunctionArgsList.add(javaArgumentTerm1);

    JavaArgumentTerm javaArgumentTerm2 = new JavaArgumentTerm();
    javaArgumentTerm2.setName("sceltaDiAggiuntaPizza");
    javaArgumentTerm2.setType("RegistroDiCassa.AggiungiPizza");
    javaArgumentTerm2.setPrimitive(false);
    stepFunctionArgsList.add(javaArgumentTerm2);

    JavaArgumentTerm javaArgumentTerm3 = new JavaArgumentTerm();
    javaArgumentTerm3.setName("sceltaDelTipoPizza");
    javaArgumentTerm3.setType("RegistroDiCassa.SelezioneTipoDiPizza");
    javaArgumentTerm3.setPrimitive(false);
    stepFunctionArgsList.add(javaArgumentTerm3);

    JavaArgumentTerm javaArgumentTerm4 = new JavaArgumentTerm();
    javaArgumentTerm4.setName("insertQuantita");
    javaArgumentTerm4.setType("int");
    javaArgumentTerm4.setPrimitive(true);
    stepFunctionArgsList.add(javaArgumentTerm4);

    JavaArgumentTerm javaArgumentTerm5 = new JavaArgumentTerm();
    javaArgumentTerm5.setName("insertPrezzo");
    javaArgumentTerm5.setType("int");
    javaArgumentTerm5.setPrimitive(true);
    stepFunctionArgsList.add(javaArgumentTerm5);

    return stepFunctionArgsList;
  }


}
