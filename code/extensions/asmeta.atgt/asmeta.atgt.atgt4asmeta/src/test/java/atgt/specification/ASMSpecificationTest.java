package atgt.specification;

import static org.junit.jupiter.api.Assertions.assertSame;


import tgtlib.definitions.expression.type.BoundType;

import org.junit.jupiter.api.Test;


class ASMSpecificationTest {

	@Test void addType1() {
		ASMSpecification spec = new ASMSpecification();
		String tname = "int12";
		BoundType bt1 = new BoundType(tname,1,2);
		spec.addType(bt1);
		assertSame(bt1, spec.getTypeFor(tname));
	}

}
