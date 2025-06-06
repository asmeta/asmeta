package asmeta.mutation.operators;

import java.util.List;

import asmeta.AsmCollection;
import asmeta.structure.Asm;

//generic mutation operator
public abstract class AsmetaMutationOperator {

	public abstract List<AsmCollection> mutate(AsmCollection asmeta);

	
	public String getName() {
		return this.getClass().getSimpleName();
	}

}
