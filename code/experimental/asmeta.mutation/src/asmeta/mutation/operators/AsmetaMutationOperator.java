package asmeta.mutation.operators;

import java.util.List;

import asmeta.AsmCollection;
import asmeta.structure.Asm;

public abstract class AsmetaMutationOperator {

	public abstract List<AsmCollection> mutate(AsmCollection asmeta);


}