package org.asmeta.asm2code;

import asmeta.structure.Asm;
import asmeta.structure.ImportClause;
import org.asmeta.asm2code.main.TranslatorOptions;
import org.asmeta.parser.util.ReflectiveVisitor;

@SuppressWarnings("all")
public class ImportToH extends ReflectiveVisitor<String> {
  private Asm res;
  
  private TranslatorOptions options;
  
  public ImportToH(final Asm resource) {
    this.res = resource;
    TranslatorOptions _translatorOptions = new TranslatorOptions(true, false, false, false);
    this.options = _translatorOptions;
  }
  
  public String visit(final ImportClause ic) {
    return ic.getModuleName();
  }
}
