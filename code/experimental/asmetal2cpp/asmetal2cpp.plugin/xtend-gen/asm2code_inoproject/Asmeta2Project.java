package asm2code_inoproject;

import asmeta.structure.Asm;
import java.nio.file.Path;
import org.asmeta.asm2code.main.CppGenerator;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.asmeta.codegenerator.HWIntegrationGenerator;
import org.asmeta.codegenerator.InoGenerator;
import org.asmeta.codegenerator.configuration.HWConfiguration;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

@SuppressWarnings("all")
public class Asmeta2Project implements IGenerator {
  private CppGenerator cppGen;

  private HeaderGenerator hGen;

  private HWIntegrationGenerator hwGen;

  private InoGenerator inoGen;

  public Asmeta2Project(final HWConfiguration config) {
    CppGenerator _cppGenerator = new CppGenerator();
    this.cppGen = _cppGenerator;
    HeaderGenerator _headerGenerator = new HeaderGenerator();
    this.hGen = _headerGenerator;
    HWIntegrationGenerator _hWIntegrationGenerator = new HWIntegrationGenerator(config);
    this.hwGen = _hWIntegrationGenerator;
    InoGenerator _inoGenerator = new InoGenerator(config);
    this.inoGen = _inoGenerator;
  }

  public Path generateAll(final Asm model, final String destinationPath, final String destinationName) {
    Path _xblockexpression = null;
    {
      this.cppGen.generate(model, ((destinationPath + destinationName) + CppGenerator.Ext));
      this.hGen.generate(model, ((destinationPath + destinationName) + HeaderGenerator.Ext));
      this.hwGen.generate(model, ((destinationPath + destinationName) + HWIntegrationGenerator.Ext));
      _xblockexpression = this.inoGen.generate(model, ((destinationPath + destinationName) + InoGenerator.Ext));
    }
    return _xblockexpression;
  }

  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
