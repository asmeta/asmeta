package asmeta.evotest.junit2avalla.model.terms;

/**
 * A value to use for a choose variable during the next execution of an ASM
 * rule.
 */
public class AvallaPickTerm extends AvallaTerm {

	private final String variable;
	private final String rule;
	private final String value;

	public AvallaPickTerm(String variable, String rule, String value) {
		this.variable = variable;
		this.rule = rule;
		this.value = value;
	}

	public String getVariable() {
		return variable;
	}

	public String getRule() {
		return rule;
	}

	public String getValue() {
		return value;
	}
}
