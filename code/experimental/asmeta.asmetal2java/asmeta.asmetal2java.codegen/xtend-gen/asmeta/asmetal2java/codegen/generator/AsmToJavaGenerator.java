package asmeta.asmetal2java.codegen.generator;

import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.formatter.Formatter;
import asmeta.asmetal2java.codegen.formatter.FormatterImpl;
import asmeta.structure.Asm;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Generators superclass.
 */
@SuppressWarnings("all")
public abstract class AsmToJavaGenerator implements IGenerator {
  protected TranslatorOptions options;

  protected Formatter formatter;

  public AsmToJavaGenerator() {
    FormatterImpl _formatterImpl = new FormatterImpl();
    this.formatter = _formatterImpl;
  }

  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  public void compileAndWrite(final Asm asm, final String writerPath, final String msg, final TranslatorOptions userOptions) {
    try {
      InputOutput.<String>println(((msg + " file generation in ") + writerPath));
      final FileWriter file = new FileWriter(writerPath);
      final BufferedWriter writer = new BufferedWriter(file);
      final String javaCode = this.compileAsm(asm, userOptions);
      boolean _formatter = this.options.getFormatter();
      if (_formatter) {
        writer.write(this.formatter.formatCode(javaCode));
      } else {
        writer.write(javaCode);
      }
      writer.newLine();
      writer.close();
      InputOutput.<String>println((msg + " file generation finished"));
      boolean _exists = new File(writerPath).exists();
      boolean _not = (!_exists);
      if (_not) {
        throw new RuntimeException((("file for " + msg) + "not produced"));
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  protected abstract String compileAsm(final Asm asm);

  public final String compileAsm(final Asm asm, final TranslatorOptions options) {
    String _xblockexpression = null;
    {
      this.options = options;
      _xblockexpression = this.compileAsm(asm);
    }
    return _xblockexpression;
  }
}
