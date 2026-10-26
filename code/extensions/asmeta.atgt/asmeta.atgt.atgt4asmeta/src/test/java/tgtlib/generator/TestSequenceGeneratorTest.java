package tgtlib.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;


import tgtlib.coverage.CoverageTree;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.TestSuite;
import tgtlib.definitions.TestSuiteFactory;

class TestSequenceGeneratorTest {

	String sequence = "";

	@Test void runAllOK() {
		MyCoverage myCov = new MyCoverage();
		MyTestSuiteGen tgc = new MyTestSuiteGen(myCov);
		tgc.generateTestsWait();
		assert !tgc.currentThread.isAlive();
		// another run
		tgc.generateTestsWait();
		assert !tgc.currentThread.isAlive();
		assertEquals("SFSF", sequence);
	}

	@Test void runTwice() {
		MyCoverage myCov = new MyCoverage();
		MyTestSuiteGen tgc = new MyTestSuiteGen(myCov);
		tgc.generateTests();
		try {
			Thread.sleep(10);
			tgc.generateTests();	
		} catch (Exception e) {
			assert e instanceof TSGenException;
			assertEquals("S", sequence);
		}
	}

	@Test void runConcurrent() throws Exception {
		MyCoverage myCov = new MyCoverage();
		MyTestSuiteGen tgc = new MyTestSuiteGen(myCov);
		tgc.generateTests();
		Thread.sleep(10);
		assertEquals("S", sequence);
		assert tgc.currentThread.isAlive();
		while (tgc.currentThread.isAlive());
		assert !tgc.currentThread.isAlive();
		assertEquals("SF", sequence);
	}

	@Test void runInterrupted() throws Exception {
		MyCoverage myCov = new MyCoverage();
		MyTestSuiteGen tgc = new MyTestSuiteGen(myCov);
		tgc.generateTests();
		assert tgc.currentThread.isAlive();
		Thread.sleep(100);
		tgc.stop();
		assertEquals("SI", sequence);
		assert !tgc.currentThread.isAlive();
		tgc.generateTests();
		Thread.sleep(10);
		assertEquals("SIS", sequence);
	}

	class MyCoverage extends CoverageTree {

		protected MyCoverage() {
			super("");
			// TODO Auto-generated constructor stub
		}
	}

	class MyTestSuiteGen extends TestSuiteGenerator {

		protected MyTestSuiteGen(CoverageTree cov) {
			super(null,cov,new TestSuiteFactory<TestSuite<?,?>>() {

				@Override
				public TestSuite<?, ?> buildEmptyTestSuite() {
					return new TestSuite(){
						
					};
				}
			});
		}

		@Override
		protected void addTestsForCoverage(CoverageTree cov, TestSuite testSuite) {
			System.out.println("computing test for coverage and adding");
			sequence +=  "S";
			try {
				Thread.sleep(500);
				System.out.println("completed");
				sequence +=  "F";
			} catch (InterruptedException e) {
				System.out.println("interrupted");
				sequence +=  "I";
			}
		}

		public void stop() {
			currentThread.interrupt();
		}
	}
}