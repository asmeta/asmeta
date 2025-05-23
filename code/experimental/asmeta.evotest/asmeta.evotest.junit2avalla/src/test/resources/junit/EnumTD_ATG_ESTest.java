/*
 * This file was automatically generated by EvoSuite
 * Thu Jan 16 09:40:24 GMT 2025
 */

package domaintests;

import org.junit.Test;
import static org.junit.Assert.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class EnumTD_ATG_ESTest extends EnumTD_ATG_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        EnumTD_ATG enumTD_ATG0 = new EnumTD_ATG();
        assertNotNull(enumTD_ATG0);
        assertEquals(EnumTD.EnumDomain.STATE1, enumTD_ATG0.get_enumControlledFunction());
        assertEquals(15, (int) enumTD_ATG0.get_enumtointegerControlledFunction_fromDomain_STATE3());
        assertEquals(15, (int) enumTD_ATG0.get_enumtointegerControlledFunction_fromDomain_STATE2());
        assertEquals(15, (int) enumTD_ATG0.get_enumtointegerControlledFunction_fromDomain_STATE1());
        assertTrue(enumTD_ATG0.get_enumtobooleanControlledFunction_fromDomain_STATE2());
        assertTrue(enumTD_ATG0.get_enumtobooleanControlledFunction_fromDomain_STATE1());
        assertTrue(enumTD_ATG0.get_enumtobooleanControlledFunction_fromDomain_STATE3());
        assertEquals("hello world", enumTD_ATG0.get_enumtostringControlledFunction_fromDomain_STATE1());
        assertEquals("hello world", enumTD_ATG0.get_enumtostringControlledFunction_fromDomain_STATE2());
        assertEquals("hello world", enumTD_ATG0.get_enumtostringControlledFunction_fromDomain_STATE3());
        assertEquals('a', (char)enumTD_ATG0.get_enumtocharControlledFunction_fromDomain_STATE1());
        assertEquals('b', (char)enumTD_ATG0.get_enumtocharControlledFunction_fromDomain_STATE2());
        assertEquals('c', (char)enumTD_ATG0.get_enumtocharControlledFunction_fromDomain_STATE3());
        assertEquals(2.5, enumTD_ATG0.get_enumtorealControlledFunction_fromDomain_STATE3(), 0.01);
        assertEquals(2.5, enumTD_ATG0.get_enumtorealControlledFunction_fromDomain_STATE2(), 0.01);
        assertEquals(2.5, enumTD_ATG0.get_enumtorealControlledFunction_fromDomain_STATE1(), 0.01);
        assertEquals("abstract_value1", enumTD_ATG0.get_enumtoabstractControlledFunction_fromDomain_STATE1());
        assertEquals("abstract_value2", enumTD_ATG0.get_enumtoabstractControlledFunction_fromDomain_STATE2());
        assertEquals("abstract_value1", enumTD_ATG0.get_enumtoabstractControlledFunction_fromDomain_STATE3());
        assertEquals(5, (int) enumTD_ATG0.get_enumtoconcreteControlledFunction_fromDomain_STATE2());
        assertEquals(5, (int) enumTD_ATG0.get_enumtoconcreteControlledFunction_fromDomain_STATE3());
        assertEquals(5, (int) enumTD_ATG0.get_enumtoconcreteControlledFunction_fromDomain_STATE1());
        assertEquals(EnumTD.EnumDomain.STATE2, enumTD_ATG0.get_enumtoenumControlledFunction_fromDomain_STATE3());
        assertEquals(EnumTD.EnumDomain.STATE2, enumTD_ATG0.get_enumtoenumControlledFunction_fromDomain_STATE2());
        assertEquals(EnumTD.EnumDomain.STATE2, enumTD_ATG0.get_enumtoenumControlledFunction_fromDomain_STATE1());
        assertEquals(EnumTD.AnotherEnumDomain.ON, enumTD_ATG0.get_anotherEnumtoenumControlledFunction_fromDomain_STATE1());
        assertEquals(EnumTD.AnotherEnumDomain.ON, enumTD_ATG0.get_anotherEnumtoenumControlledFunction_fromDomain_STATE3());
        assertEquals(EnumTD.AnotherEnumDomain.ON, enumTD_ATG0.get_anotherEnumtoenumControlledFunction_fromDomain_STATE2());

        enumTD_ATG0.step();

        EnumTD.EnumDomain enumControlledFunction = enumTD_ATG0.get_enumControlledFunction();
        assertEquals(EnumTD.EnumDomain.STATE1, enumControlledFunction);
        Integer integer0 = enumTD_ATG0.get_enumtointegerControlledFunction_fromDomain_STATE3();
        assertEquals(15, (int) integer0);
        int int0 = enumTD_ATG0.get_enumtointegerControlledFunction_fromDomain_STATE2();
        assertEquals(15, (int) int0);
        Boolean boolean0 = enumTD_ATG0.get_enumtobooleanControlledFunction_fromDomain_STATE2();
        assertTrue(boolean0);
        boolean boolean1 = enumTD_ATG0.get_enumtobooleanControlledFunction_fromDomain_STATE1();
        assertTrue(boolean1);
        String string0 = enumTD_ATG0.get_enumtostringControlledFunction_fromDomain_STATE1();
        assertEquals("hello world", string0);
        Character character0 = enumTD_ATG0.get_enumtocharControlledFunction_fromDomain_STATE1();
        assertEquals('a', (char)character0);
        char char0 = enumTD_ATG0.get_enumtocharControlledFunction_fromDomain_STATE2();
        assertEquals('b', char0);
        Double double0 = enumTD_ATG0.get_enumtorealControlledFunction_fromDomain_STATE3();
        assertEquals(2.5, double0, 0.01);
        double double1 = enumTD_ATG0.get_enumtorealControlledFunction_fromDomain_STATE2();
        assertEquals(2.5, double1, 0.01);
        String abstract0 = enumTD_ATG0.get_enumtoabstractControlledFunction_fromDomain_STATE1();
        assertEquals("abstract_value1", abstract0);
        Integer int2 = enumTD_ATG0.get_enumtoconcreteControlledFunction_fromDomain_STATE2();
        assertEquals(5, (int) int2);
        EnumTD.EnumDomain enumtoenumControlledFunctionFromDomainState3 = enumTD_ATG0.get_enumtoenumControlledFunction_fromDomain_STATE3();
        assertEquals(EnumTD.EnumDomain.STATE2, enumtoenumControlledFunctionFromDomainState3);
        EnumTD.AnotherEnumDomain anotherEnumtoenumControlledFunctionFromDomainState1 = enumTD_ATG0.get_anotherEnumtoenumControlledFunction_fromDomain_STATE1();
        assertEquals(EnumTD.AnotherEnumDomain.ON, anotherEnumtoenumControlledFunctionFromDomainState1);

        enumTD_ATG0.step();

        enumTD_ATG0.set_enumMonitoredFunction(EnumTD.EnumDomain.STATE1);
        enumTD_ATG0.set_anotherenumMonitoredFunction(EnumTD.AnotherEnumDomain.ON);
        enumTD_ATG0.set_enumtointegerMonitoredFunction_fromDomain_STATE1(10);
        enumTD_ATG0.set_enumtointegerMonitoredFunction_fromDomain_STATE2((-7));
        enumTD_ATG0.set_enumtobooleanMonitoredFunction_fromDomain_STATE1(true);
        enumTD_ATG0.set_enumtobooleanMonitoredFunction_fromDomain_STATE2(false);
        enumTD_ATG0.set_enumtostringMonitoredFunction_fromDomain_STATE1("Hello world");
        enumTD_ATG0.set_enumtostringMonitoredFunction_fromDomain_STATE1("");
        enumTD_ATG0.set_enumtocharMonitoredFunction_fromDomain_STATE1('a');
        enumTD_ATG0.set_enumtorealMonitoredFunction_fromDomain_STATE1(5.7);
        enumTD_ATG0.set_enumtorealMonitoredFunction_fromDomain_STATE1((3.2));
        enumTD_ATG0.set_abstract_enumtoabstractMonitoredFunction_fromDomain_STATE1("value1");
        enumTD_ATG0.set_enumtoconcreteMonitoredFunction_fromDomain_STATE1(5);
        enumTD_ATG0.set_enumtoenumMonitoredFunction_fromDomain_STATE1(EnumTD.EnumDomain.STATE1);
        enumTD_ATG0.set_anotherEnumtoenumMonitoredFunction_fromDomain_OFF(EnumTD.EnumDomain.STATE3);
    }
}