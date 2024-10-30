package asmeta.junit2avalla.stepfunction;

import java.nio.file.Path;
import java.util.List;
import asmeta.junit2avalla.model.terms.JavaArgumentTerm;

/**
 * The {@code StepFunctionReaderIF} interface defines the contract for reading and parsing step
 * function definitions from a file.
 */
public interface StepFunctionReader {

  /**
   * Reads a step function definition from the file at the specified {@code path}
   * and parses its content to retrieve a list of {@link JavaArgumentTerm} objects.
   *
   * @param path the {@link Path} to the file containing the step function definition
   * @return a list of {@link JavaArgumentTerm} objects parsed from the file, or an empty list if
   * an error occurs
   */
  public List<JavaArgumentTerm> readStepFunction(Path path);

  /**
   * Reads a step function definition from the file at the default {@code path}
   * and parses its content to retrieve a list of {@link JavaArgumentTerm} objects.
   *
   * @return a list of {@link JavaArgumentTerm} objects parsed from the file, or an empty list if
   * an error occurs
   */
  public List<JavaArgumentTerm> readStepFunction();

}
