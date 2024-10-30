package asmeta.junit2avalla.stepfunction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import asmeta.junit2avalla.antlr.StepFunctionArgsLexer;
import asmeta.junit2avalla.antlr.StepFunctionArgsParser;
import asmeta.junit2avalla.model.terms.JavaArgumentTerm;

import org.junit.Test;

public class StepFunctionArgsTest {

  @Test
  public void whenStepFunctionSupportContainsArguments_thenListOfArgumentsIsReturned(){
    String stepFunctionArguments = "RegistroDiCassa.Servizio servizioSelezionato,\n" +
    "String pizzaInserita,\n" +
    "RegistroDiCassa.AggiungiPizza sceltaDiAggiuntaPizza,\n" +
    "RegistroDiCassa.SelezioneTipoDiPizza sceltaDelTipoPizza,\n" +
    "int insertQuantita,\n" +
    "int insertPrezzo" ;
    StepFunctionArgsLexer stepFunctionArgsLexer = new StepFunctionArgsLexer(CharStreams.fromString(stepFunctionArguments));
    CommonTokenStream tokens = new CommonTokenStream( stepFunctionArgsLexer );
    StepFunctionArgsParser stepFunctionSupportParser = new StepFunctionArgsParser(tokens);
    ParseTreeWalker walker = new ParseTreeWalker();
    StepFunctionArgsListener stepFunctionSupportWalker = new StepFunctionArgsListener();
    walker.walk(stepFunctionSupportWalker, stepFunctionSupportParser.argumentList());

    assertThat(stepFunctionSupportWalker.getArgumentList().size(), is(6));

    JavaArgumentTerm javaArgumentTerm = stepFunctionSupportWalker.getArgumentList().get(0);
    assertThat(javaArgumentTerm.getName(), is("servizioSelezionato"));
    assertThat(javaArgumentTerm.getType(), is("RegistroDiCassa.Servizio"));
    assertThat(javaArgumentTerm.isPrimitive(), is(false));

    javaArgumentTerm = stepFunctionSupportWalker.getArgumentList().get(1);
    assertThat(javaArgumentTerm.getName(), is("pizzaInserita"));
    assertThat(javaArgumentTerm.getType(), is("String"));
    assertThat(javaArgumentTerm.isPrimitive(), is(true));

    javaArgumentTerm = stepFunctionSupportWalker.getArgumentList().get(2);
    assertThat(javaArgumentTerm.getName(), is("sceltaDiAggiuntaPizza"));
    assertThat(javaArgumentTerm.getType(), is("RegistroDiCassa.AggiungiPizza"));
    assertThat(javaArgumentTerm.isPrimitive(), is(false));

    javaArgumentTerm = stepFunctionSupportWalker.getArgumentList().get(3);
    assertThat(javaArgumentTerm.getName(), is("sceltaDelTipoPizza"));
    assertThat(javaArgumentTerm.getType(), is("RegistroDiCassa.SelezioneTipoDiPizza"));
    assertThat(javaArgumentTerm.isPrimitive(), is(false));

    javaArgumentTerm = stepFunctionSupportWalker.getArgumentList().get(4);
    assertThat(javaArgumentTerm.getName(), is("insertQuantita"));
    assertThat(javaArgumentTerm.getType(), is("int"));
    assertThat(javaArgumentTerm.isPrimitive(), is(true));

    javaArgumentTerm = stepFunctionSupportWalker.getArgumentList().get(5);
    assertThat(javaArgumentTerm.getName(), is("insertPrezzo"));
    assertThat(javaArgumentTerm.getType(), is("int"));
    assertThat(javaArgumentTerm.isPrimitive(), is(true));

  }

  @Test
  public void whenReadingStepFunctionArgumentsFromFile_thenListOfArgumentsIsNotEmpty() {

    Path filePath = Paths.get("src/test/resources/parserSupport.txt");
    String content = null;

    try {
      byte[] fileBytes = Files.readAllBytes(filePath);
      content = new String(fileBytes, StandardCharsets.UTF_8);
    } catch (IOException e) {
      fail("An exception occurred while reading the file: " + e.getMessage());
    }

    assertNotNull(content);

    StepFunctionArgsLexer stepFunctionSupportLexer = new StepFunctionArgsLexer(CharStreams.fromString(content));
    CommonTokenStream tokens = new CommonTokenStream( stepFunctionSupportLexer );
    StepFunctionArgsParser stepFunctionSupportParser = new StepFunctionArgsParser(tokens);
    ParseTreeWalker walker = new ParseTreeWalker();
    StepFunctionArgsListener stepFunctionSupportWalker = new StepFunctionArgsListener();
    walker.walk(stepFunctionSupportWalker, stepFunctionSupportParser.argumentList());

    assertFalse(stepFunctionSupportWalker.getArgumentList().isEmpty());

  }

}
