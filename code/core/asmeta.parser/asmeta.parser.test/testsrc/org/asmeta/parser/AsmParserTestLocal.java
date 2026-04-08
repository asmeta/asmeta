package org.asmeta.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;

// it is too difficult to test a checker in isolation
class AsmParserTestLocal extends AsmParserTest {

	@BeforeAll
	static void setUp() {
	}

	// 
	@Test 
	void wrongUseOfLocal() {
		// local cannot be declared in signature
		String err = testOneSpecWithError("test/errors/UsingLocal.asm");
		System.err.println(err);
		assertTrue(err.contains("A local dynamic function cannot be declared in the signature"));
	}

	@Test 
	void specificTestNoErrors() {
		testOneSpec("test/parser/UsingLocal2.asm");
	}
}
