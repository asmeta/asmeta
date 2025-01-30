/**
 * This configuration package includes the {@link TranslatorOptions} interface
 * that includes all the available operations regarding the translation options.
 * The implementation of this interface is contained in the
 * {@link TranslatorOptionsImpl} class, which works through a
 * {@code HashMap<String,Consumer<Boolean>>} that is a map with the property
 * name as key and a lambda function as content to assign the value to the
 * property, this was designed to reduce the cyclomatic complexity of the class.
 * In addition to this, there is the {@link ModeConstantsConfig} class that
 * contains the configuration constants that are exposed externally in order to
 * have consistency with other projects and the {@link Mode} enumerative class
 * that represents the various modes of use of the application.
 */
package asmeta.asmetal2java.codegen.config;
