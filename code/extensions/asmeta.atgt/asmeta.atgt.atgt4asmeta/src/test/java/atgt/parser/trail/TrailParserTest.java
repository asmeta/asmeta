package atgt.parser.trail;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.TestCondition;
import atgt.specification.ASMSpecification;

public class TrailParserTest {

	static ASMSpecification spec;
	
	@BeforeClass
	public static void fillSpec(){
		spec = new ASMSpecification();
	}
	
	@Test
	public void testTrailParserInputStream() throws ParseException {
		readCex("frompan.txt");
	}
	@Test
	public void testTrailParserInputStream1() throws ParseException {
		readCex("frompan1.txt");
	}
	@Test
	public void testTrailParserInputStream2() throws ParseException {
		readCex("frompan2.txt");
	}

	private void readCex(String file) throws ParseException {
		InputStream mcResult = this.getClass().getResourceAsStream(file);
		TrailParser tp = new TrailParser(mcResult);
		AsmTestSequence ts = new AsmTestSequence(null);
		final TestCondition findTc = new TestCondition("yy", null);
		AsmCoverageTree ct = new AsmCoverageTree("tree"){
			@Override
			public	TestCondition findTestCondition(String s){
				return findTc;
			}
		};
		tp.analysis(ct, ts);
	}

}
