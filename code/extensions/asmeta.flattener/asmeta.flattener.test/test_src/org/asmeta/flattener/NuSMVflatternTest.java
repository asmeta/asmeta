package org.asmeta.flattener;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.log4j.Logger;
import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.junit.jupiter.api.Test;


class NuSMVflatternTest extends FlattenerTest {

	public static Class<? extends AsmetaFlattener>[] ALL_SMV_FLATTENERS = new Class[] { 
			MacroCallRuleFlattener.class,
			ForallRuleFlattener.class, 
			RemoveArgumentsFlattener.class, 
			LetRuleFlattener.class, 
			CaseRuleFlattener.class,
			RemoveNestingFlattener.class 
		};
	
	@Test
	void sluicegate() throws Exception {
		org.apache.log4j.Logger.getLogger(AsmetaMultipleFlattener.class).setLevel(org.apache.log4j.Level.DEBUG);
		Logger.getRootLogger().addAppender(new org.apache.log4j.ConsoleAppender(new org.apache.log4j.SimpleLayout()));
		AsmetaTermPrinter.CHECK_ENUM_TERM = true;
		String res = flattenerTest(examplesDir + "examples\\sluicegate\\sluiceGateMotorCtl.asm",	false, ALL_SMV_FLATTENERS);
		System.out.println("NuSMVflatternTest.sluicegate result: " + res);
	}
	@Test
	void sluicegateSimplified() throws Exception {
		String res = flattenerTest("examples\\sluiceGateMotorCtl.asm",	false, ALL_SMV_FLATTENERS);
		System.out.println("NuSMVflatternTest.sluicegate result: " + res);
	}
}
