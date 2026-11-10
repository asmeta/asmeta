package asmeta.mutation.operators;

import java.util.List;

public class RelationalOperatorMutator extends ExpressionMutator {

    public RelationalOperatorMutator(String sourceOperator, List<String> replacementOperators) {
        super(new RelationalTermMutator(sourceOperator, replacementOperators));
    }
}