package asmeta.evotest.junit2avalla.util;

import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;

public class VariableUtil {

  public static JavaVariableTerm getVariable0(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("set_servizioSelezionato");
    javaVariable.setType("RegistroDiCassa.Servizio");
    javaVariable.setValue("RegistroDiCassav3Sig.Servizio.NEWORDINE");
    javaVariable.setPrimitive(false);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable1(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("set_pizzaInserita");
    javaVariable.setType("String");
    javaVariable.setValue("margherita");
    javaVariable.setPrimitive(true);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable2(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("set_sceltaDiAggiuntaPizza");
    javaVariable.setType("RegistroDiCassa.AggiungiPizza");
    javaVariable.setValue("RegistroDiCassav3Sig.AggiungiPizza.SI");
    javaVariable.setPrimitive(false);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable3(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("set_sceltaDelTipoPizza");
    javaVariable.setType("RegistroDiCassa.SelezioneTipoDiPizza");
    javaVariable.setValue("RegistroDiCassav3Sig.SelezioneTipoDiPizza.STANDARD");
    javaVariable.setPrimitive(false);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable4(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("set_insertQuantita");
    javaVariable.setType("int");
    javaVariable.setValue("2");
    javaVariable.setPrimitive(true);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable5(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("set_insertPrezzo");
    javaVariable.setType("int");
    javaVariable.setValue("2");
    javaVariable.setPrimitive(true);
    return javaVariable;
  }


}
