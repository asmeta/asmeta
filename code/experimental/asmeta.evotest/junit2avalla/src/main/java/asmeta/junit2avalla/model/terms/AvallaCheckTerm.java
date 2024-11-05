package asmeta.junit2avalla.model.terms;

/**
 * Represents a check term in the Avalla language,
 * which compares two terms: a left term and a right term.
 * <p>
 * This class stores two string values: the left term and the right term,
 * and provides methods to retrieve and modify these values.
 * </p>
 */
public class AvallaCheckTerm extends AvallaTerm {

  /** The left term to be compared. It's usually a variable name */
  private String leftTerm;

  /** The right term to be compared. It's usually a value */
  private String rightTerm;

  /**
   * Default constructor that creates an empty {@code AvallaCheckTerm}.
   */
  public AvallaCheckTerm() {
  }

  /**
   * Constructs an {@code AvallaCheckTerm} with the specified left and right terms.
   *
   * @param leftTerm  the left term.
   * @param rightTerm the right term.
   */
  public AvallaCheckTerm(String leftTerm, String rightTerm) {
    this.leftTerm = leftTerm;
    this.rightTerm = rightTerm;
  }

  /**
   * Returns the left term of this check term.
   *
   * @return the left term.
   */
  public String getLeftTerm() {
    return leftTerm;
  }

  /**
   * Sets the left term of this check term.
   *
   * @param leftTerm the left term to set.
   */
  public void setLeftTerm(String leftTerm) {
    this.leftTerm = leftTerm;
  }

  /**
   * Returns the right term of this check term.
   *
   * @return the right term.
   */
  public String getRightTerm() {
    return rightTerm;
  }

  /**
   * Sets the right term of this check term.
   *
   * @param rightTerm the right term to set.
   */
  public void setRightTerm(String rightTerm) {
    this.rightTerm = rightTerm;
  }
}
