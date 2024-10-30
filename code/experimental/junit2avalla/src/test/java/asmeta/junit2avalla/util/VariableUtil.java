package asmeta.junit2avalla.util;

import java.util.LinkedList;
import java.util.List;
import asmeta.junit2avalla.model.terms.JavaVariableTerm;

public class VariableUtil {

  public static List<JavaVariableTerm> getVariableList(){
    List<JavaVariableTerm> javaVariableList = new LinkedList<>();
    javaVariableList.add(getVariable0());
    javaVariableList.add(getVariable1());
    javaVariableList.add(getVariable2());
    javaVariableList.add(getVariable3());
    javaVariableList.add(getVariable4());
    javaVariableList.add(getVariable5());
    return javaVariableList;
  }

  public static JavaVariableTerm getVariable0(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("servizioSelezionato");
    javaVariable.setType("RegistroDiCassa.Servizio");
    javaVariable.setValue("RegistroDiCassav3Sig.Servizio.NEWORDINE");
    javaVariable.setPrimitive(false);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable1(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("pizzaInserita");
    javaVariable.setType("String");
    javaVariable.setValue("margherita");
    javaVariable.setPrimitive(true);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable2(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("sceltaDiAggiuntaPizza");
    javaVariable.setType("RegistroDiCassa.AggiungiPizza");
    javaVariable.setValue("RegistroDiCassav3Sig.AggiungiPizza.SI");
    javaVariable.setPrimitive(false);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable3(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("sceltaDelTipoPizza");
    javaVariable.setType("RegistroDiCassa.SelezioneTipoDiPizza");
    javaVariable.setValue("RegistroDiCassav3Sig.SelezioneTipoDiPizza.STANDARD");
    javaVariable.setPrimitive(false);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable4(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("insertQuantita");
    javaVariable.setType("int");
    javaVariable.setValue("2");
    javaVariable.setPrimitive(true);
    return javaVariable;
  }

  public static JavaVariableTerm getVariable5(){
    JavaVariableTerm javaVariable = new JavaVariableTerm();
    javaVariable.setName("insertPrezzo");
    javaVariable.setType("int");
    javaVariable.setValue("2");
    javaVariable.setPrimitive(true);
    return javaVariable;
  }


}
