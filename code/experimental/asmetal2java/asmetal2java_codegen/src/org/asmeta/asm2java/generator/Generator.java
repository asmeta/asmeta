package org.asmeta.asm2java.generator;

import org.asmeta.asm2java.config.TranslatorOptions;

import asmeta.structure.Asm;

/**
 * The {@code Generator} interface defines the contract for generating a translation.
 */
public interface Generator {
	
	/**
	 * Generate the Java translation.
	 * 
	 * @param asm the main of the parsed ASM.
	 * @param writerPath the canonical path of the java file to generate.
	 * @param userOptions the TranslationOptions selected by the user.
	 */
	void generateJava(Asm asm, String writerPath, TranslatorOptions userOptions);
	
	/**
	 * Generate an executable of the generated Java class.
	 * 
	 * @param asm the main of the parsed ASM.
	 * @param writerPath the canonical path of the java file to generate.
	 * @param userOptions the TranslationOptions selected by the user.
	 */
	void generateExe(Asm asm, String writerPath, TranslatorOptions userOptions);
	
	/**
	 * Generate an executable of the generated Java class with a Graphical User Interface (GUI).
	 * 
	 * @param asm the main of the parsed ASM.
	 * @param writerPath the canonical path of the java file to generate.
	 * @param userOptions the TranslationOptions selected by the user.
	 */
	void generateWin(Asm asm, String writerPath, TranslatorOptions userOptions);
	
	/**
	 * Generate a specific translation for test generation.
	 * 
	 * @param asm the main of the parsed ASM.
	 * @param writerPath the canonical path of the java file to generate.
	 * @param userOptions the TranslationOptions selected by the user.
	 */
	void generateTest(Asm asm, String writerPath, TranslatorOptions userOptions);
	
	/**
	 * Generate a class designed for generating tests for the translated Java class.
	 * 
	 * @param asm the main of the parsed ASM.
	 * @param writerPath the canonical path of the java file to generate.
	 * @param userOptions the TranslationOptions selected by the user.
	 */
	void generateAtg(Asm asm, String writerPath, TranslatorOptions userOptions);

}