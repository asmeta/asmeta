package org.asmeta.asm2junit.main;

import asmeta.structure.Asm;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.asmeta.asm2junit.formatter.Formatter;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * the real generator
 */
@SuppressWarnings("all")
public abstract class AvallaToJUnitGenerator {
  protected TranslatorOptions options;

  /**
   * override doGenerate(Resource input, IFileSystemAccess fsa) {
   * throw new UnsupportedOperationException("TODO: auto-generated method stub")
   * }
   */
  protected void compileAndWrite(final Asm asm, final String writerPath, final String msg, final TranslatorOptions userOptions) {
    try {
      InputOutput.<String>println(((msg + " file generation in ") + writerPath));
      final FileWriter file = new FileWriter(writerPath);
      final BufferedWriter writer = new BufferedWriter(file);
      final String javaCode = this.compileAsm(asm, userOptions);
      if (this.options.formatter) {
        writer.write(Formatter.formatCode(javaCode));
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
