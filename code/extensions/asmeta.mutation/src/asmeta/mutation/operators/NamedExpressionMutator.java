
package asmeta.mutation.operators;

import java.util.Objects;

/**
 * Expression mutation operator with a unique name.
 *
 * Allows MutationScoreExecutor to distinguish between
 * different expression mutation configurations. Usefull to run experiments and tag the results.
 */
public class NamedExpressionMutator extends ExpressionMutator {

    private final String name;

    public NamedExpressionMutator(String name, TermMutator termMutator) {
        super(termMutator);
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String getName() {
        return name;
    }
}
