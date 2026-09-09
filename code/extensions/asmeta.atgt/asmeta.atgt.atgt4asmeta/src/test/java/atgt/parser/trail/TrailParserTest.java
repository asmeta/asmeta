package atgt.parser.trail;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.TestCondition;
import atgt.specification.ASMSpecification;

class TrailParserTest {

	static ASMSpecification spec;

	@BeforeAll
	static void fillSpec(){
		spec = new ASMSpecification();
	}

	@Test void trailParserInputStream() throws Exception {
		readCex("frompan.txt");
	}

	@Test void trailParserInputStream1() throws Exception {
		readCex("frompan1.txt");
	}

	@Test void trailParserInputStream2() throws Exception {
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
