package asmeta.junit2avalla.stepfunction;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asmeta.junit2avalla.antlr.StepFunctionArgsBaseListener;
import asmeta.junit2avalla.antlr.StepFunctionArgsParser.ArgumentContext;
import asmeta.junit2avalla.antlr.StepFunctionArgsParser.ArgumentListContext;
import asmeta.junit2avalla.antlr.StepFunctionArgsParser.NameContext;
import asmeta.junit2avalla.antlr.StepFunctionArgsParser.TypeContext;
import asmeta.junit2avalla.model.terms.JavaArgumentTerm;

/**
 * The {@code StepFunctionArgsListener} class extends {@code StepFunctionArgsBaseListener}
 * and is responsible for processing and collecting argument terms used in step functions.
 * It creates and stores {@link JavaArgumentTerm} objects that represent the arguments
 * encountered during parsing.
 *
 * <p>This listener populates a list of {@link JavaArgumentTerm} objects, each of which
 * stores details about the argument's type, name, and whether it is primitive or not.
 *
 * <p>Each {@link JavaArgumentTerm} is created when the {@code enterArgument} method is triggered,
 * and added to the {@code javaArgumentTermList} when the {@code exitArgument} method is called.
 */
public class StepFunctionArgsListener extends StepFunctionArgsBaseListener {

  private static final Logger log = LogManager.getLogger(StepFunctionArgsListener.class);

  /**
   * A list of {@link JavaArgumentTerm} objects representing the arguments parsed from
   * the step function.
   */
  private List<JavaArgumentTerm> javaArgumentTermList;

  /**
   * The currently processed {@link JavaArgumentTerm} object.
   */
  private JavaArgumentTerm currentJavaArgumentTerm;

  /**
   * Initializes a new {@code StepFunctionArgsListener} with an empty list of
   * {@link JavaArgumentTerm}.
   */
  public StepFunctionArgsListener() {
  }

  /**
   * {@inheritDoc}
   *
   * <p>Create a new empty {@link ArrayList} when an argumentList is encountered.</p>
   *
   * @param ctx the argument parse tree context
   */
  @Override
  public void enterArgumentList(ArgumentListContext ctx) {
    log.info("Parsing the StepFunctionArgs...");
    this.javaArgumentTermList = new ArrayList<>();
    log.debug("Created new javaArgumentTermList");
  }

  /**
   * {@inheritDoc}
   *
   * <p>Create a new empty {@link JavaArgumentTerm} when an argument is encountered.</p>
   *
   * @param ctx the argument parse tree context
   */
  @Override
  public void enterArgument(ArgumentContext ctx) {
    log.debug("Entering argumentList_argument: {} .", ctx.getText());
    this.currentJavaArgumentTerm = new JavaArgumentTerm();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Adds the current {@link JavaArgumentTerm} to the list when the argument has
   * been fully processed.</p>
   *
   * @param ctx the argument parse tree context
   */
  @Override
  public void exitArgument(ArgumentContext ctx) {
    log.debug("Exiting argumentList_argument: {} .", ctx.getText());
    this.javaArgumentTermList.add(this.currentJavaArgumentTerm);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Sets the type of the argument and marks it as primitive if applicable.</p>
   *
   * @param ctx the type parse tree context
   */
  @Override
  public void enterType(TypeContext ctx) {
    log.debug("Entering argumentList_argument_type: {} .", ctx.getText());
    this.currentJavaArgumentTerm.setType(ctx.getText());
    TerminalNode primitiveType = ctx.PrimitiveType();
    this.currentJavaArgumentTerm.setPrimitive(primitiveType != null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Sets the name of the current argument.</p>
   *
   * @param ctx the name parse tree context
   */
  @Override
  public void enterName(NameContext ctx) {
    log.debug("Entering argumentList_argument_name: {} .", ctx.getText());
    this.currentJavaArgumentTerm.setName(ctx.getText());
  }

  /**
   * {@inheritDoc}
   *
   * <p>Update the log info about the status of the operation.</p>
   *
   * @param ctx the name parse tree context
   */
  @Override
  public void exitArgumentList(ArgumentListContext ctx) {
    log.info("Parsing operation completed with success");
  }

  /**
   * Returns the list of {@link JavaArgumentTerm} objects collected during parsing.
   *
   * @return a list of {@link JavaArgumentTerm} objects
   */
  public List<JavaArgumentTerm> getArgumentList() {
    return javaArgumentTermList;
  }

}
