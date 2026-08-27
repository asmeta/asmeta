package atgt.specification;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import tgtlib.definitions.expression.type.BoundType;


public class ASMSpecificationTest {

	@Test
	public void testAddType1() {
		ASMSpecification spec = new ASMSpecification();
		String tname = "int12";
		BoundType bt1 = new BoundType(tname,1,2);
		spec.addType(bt1);
		assertSame(bt1, spec.getTypeFor(tname));
	}

}
