/**
 * The {@code application} package is responsible for exposing the methods to
 * interact with the application from the outside, this is the task of the
 * {@link Translator} interface, which is implemented by the
 * {@link TranslatorImpl} class. The {@link FileManager} class provides support
 * to the {@code TranslatorImpl} class for file writing operations, providing
 * the paths to the folders and allowing the communication with the generators and
 * the compiler. There is also the custom exception {@link AsmParsingException}
 * that handles the case of .asm file parsing error.
 */
package asmeta.asmetal2java.codegen.application;
