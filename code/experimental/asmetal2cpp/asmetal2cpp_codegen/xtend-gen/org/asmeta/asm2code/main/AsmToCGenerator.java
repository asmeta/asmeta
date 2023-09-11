package org.asmeta.asm2code.main;

import asmeta.structure.Asm;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.asmeta.asm2code.formatter.Formatter;
import org.asmeta.parser.ASMParser;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * the real generator
 */
@SuppressWarnings("all")
public abstract class AsmToCGenerator implements IGenerator {
  protected TranslatorOptions options;
  
  public static String Ext = ASMParser.ASM_EXTENSION;
  
  public AsmToCGenerator() {
    TranslatorOptions _translatorOptions = new TranslatorOptions(true, false, false, false);
    this.options = _translatorOptions;
  }
  
  public AsmToCGenerator(final TranslatorOptions options) {
    this.options = options;
  }
  
  public Path generate(final Asm model, final String path) {
    try {
      Path _xblockexpression = null;
      {
        if ((this.options == null)) {
          throw new Exception("TranslationOptions not inizialized");
        }
        String compiled = this.compileAsm(model);
        if (this.options.formatter) {
          compiled = Formatter.formatCode(compiled);
        }
        _xblockexpression = Files.write(Paths.get(path), compiled.getBytes());
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected abstract String compileAsm(final Asm asm);
}
