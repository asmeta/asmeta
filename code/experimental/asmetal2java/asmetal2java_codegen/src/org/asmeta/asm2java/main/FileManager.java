package org.asmeta.asm2java.main;

import java.io.File;
import java.io.IOException;

import org.asmeta.asm2java.compiler.CompileResult;

import asmeta.AsmCollection;

/**
 * The {@code FileManager} interface defines operations for managing input and output files.
 */
public interface FileManager {
	
	/**
     * Given a string containing the path to the input file, copies the file into the
     * input directory and returns the newly generated file.
     * 
     * @param file the path to the input file (relative or absolute).
     * @return the copied file.
     * @throws IOException if an I/O error occurs during the file copying process.
     */
	File retrieveInput(String file) throws IOException;
	
    /**
     * Sets the output directory where the generated files will be stored.
     * 
     * @param outputDir the path of the output directory.
     */
	void setOutputDir(String outputDir);
	
    /**
     * Generates a Java file in the specified directory, using the given name, extension, 
     * model, and translator options.
     * 
     * @param name the name of the file to be generated.
     * @param model the parsed ASM specification.
     * @param userOptions the translator options to be applied.
     * @param mode the mode for the translation process.
     * @return the generated file.
     * @throws IOException if an I/O error occurs during file generation.
     */
	File generateFile(String name, AsmCollection model,TranslatorOptions userOptions, Mode mode) throws IOException ;
	
    /**
     * Compiles the file with the given name and returns the result of the compilation process.
     * 
     * @param asmName the name of the ASM file to compile.
     * @return the result of the compilation as a {@link CompileResult}.
     */
	CompileResult compileFile(String asmName);
	
    /**
     * Exports (copies) the specified Java file to a desired output folder.
     *
     * @param javaFile the Java file to be exported.
     */
	void exportFile(File javaFile);
	
    /**
     * Cleans the input directory by removing execution-related files.
     */
	void cleanInputDir();

}
