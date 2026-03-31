package asmeta.mutation.operators;

import java.util.List;

import asmeta.AsmCollection;

//generic mutation operator
public abstract class AsmetaMutationOperator {

	public abstract List<AsmCollection> mutate(AsmCollection asmeta);

	
	public String getName() {
		return this.getClass().getSimpleName();
	}

}
