package asmeta.mutation.operators;

import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;

import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;

// convert a value to a term
public class ValueToTerm extends ReflectiveVisitor<ConstantTerm> {

	public IntegerTerm visit(IntegerValue iv) {
		IntegerTerm i = asmeta.terms.furtherterms.FurthertermsFactory.eINSTANCE.createIntegerTerm();
		i.setSymbol(iv.toString());
		return i;
	}
	
	public EnumTerm visit(EnumValue ev) {
		EnumTerm et = asmeta.terms.furtherterms.FurthertermsFactory.eINSTANCE.createEnumTerm();
		et.setSymbol(ev.getValue());
		return et;
	}
	
}