package org.asmeta.atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.asmeta.atgt.generator.AsmTestGeneratorTest;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;
import org.junit.jupiter.api.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.BasicRuleVisitor;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;

// compare the new and old way to compute the TPTree (for example for basic rule coverage)
public class ComparionCovBuilderTest {

	// old coverage builder
	AsmCoverageBuilder tpbuilder_old = new BasicRuleVisitor();
	// the new one
	AsmetaBasicRuleVisitor tpbuilder_new = new AsmetaBasicRuleVisitor();

	@Test
	void testAllExamples() throws IOException {
		Path basepath = Path.of(AsmTestGeneratorTest.FILE_BASE);
		assertTrue(Files.exists(basepath));
		Files.walkFileTree(basepath, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (!file.toString().endsWith(AsmetaParserUtility.ASM_EXTENSION))
					return FileVisitResult.CONTINUE;
				return 	compare(file);
			}
		});

	}

	private FileVisitResult compare(Path f){
		try {
			// OLD
			ASMSpecification spec = new AsmetaLLoader().read(f.toFile());
			AsmCoverage tp_old = tpbuilder_old.getTPTree(spec);
			// NEW
			asmeta.AsmCollection asms = ASMParser.setUpReadAsm(f.toFile());
			AsmCoverage tp_new = tpbuilder_new.getTPTree(new AsmetaAsSpec(asms));
			// print
			if (tp_new.getNumberofTPs() < tp_old.getNumberofTPs()) {
				tp_old.allTPs().forEach(x -> System.out.println(x.getCondition()));
				tp_new.allTPs().forEach(x -> System.out.println(x.getCondition()));
				return FileVisitResult.TERMINATE;
			}
			return FileVisitResult.CONTINUE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return FileVisitResult.TERMINATE;
		}
	}

}
