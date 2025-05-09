/**
 * generated by Xtext 2.16.0
 */
package org.asmeta.avallaxt.validation;

import asmeta.AsmCollection;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.SharedFunction;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import com.google.inject.Injector;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avalla.AvallaPackage;
import org.asmeta.avallaxt.avalla.Block;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.ExecBlock;
import org.asmeta.avallaxt.avalla.Pick;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ImportNotFoundException;
import org.asmeta.parser.ParseException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * This class contains custom validation rules.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("all")
public class AvallaValidator extends AbstractAvallaValidator {
  private AsmCollection asmCollection;

  private List<String> monFunNames;

  private List<String> controlledFunNames;

  private List<String> sharedFunNames;

  private Map<ChooseRule, String> chooseRules;

  @Check
  public void checkLoadASMexists(final Scenario scenario) {
    String specName = scenario.getSpec();
    boolean _startsWith = specName.startsWith("\"");
    if (_startsWith) {
      boolean _endsWith = specName.endsWith("\"");
      boolean _not = (!_endsWith);
      if (_not) {
        this.error("should end with the quote as well", AvallaPackage.Literals.SCENARIO__SPEC);
        return;
      }
      int _length = specName.length();
      int _minus = (_length - 1);
      specName = specName.substring(1, _minus);
    }
    boolean _endsWith_1 = specName.endsWith(ASMParser.ASM_EXTENSION);
    boolean _not_1 = (!_endsWith_1);
    if (_not_1) {
      this.error("Asm spec should end with asm", AvallaPackage.Literals.SCENARIO__SPEC);
      return;
    }
    final Path asmPath = ScenarioUtility.getAsmPath(scenario);
    boolean _not_2 = (!(Files.exists(asmPath) && Files.isRegularFile(asmPath)));
    if (_not_2) {
      String _spec = scenario.getSpec();
      String _plus = ("File " + _spec);
      String _plus_1 = (_plus + " does not exist as ");
      String _plus_2 = (_plus_1 + asmPath);
      this.error(_plus_2, AvallaPackage.Literals.SCENARIO__SPEC);
      return;
    }
    try {
      this.setAsmCollection(ASMParser.setUpReadAsm(asmPath.toFile()));
    } catch (final Throwable _t) {
      if (_t instanceof ParseException) {
        this.warning(("Error in parsing asm in " + asmPath), AvallaPackage.Literals.SCENARIO__SPEC);
      } else if (_t instanceof ImportNotFoundException) {
        final ImportNotFoundException infe = (ImportNotFoundException)_t;
        String _message = infe.getMessage();
        String _plus_3 = ((("Error in parsing asm in " + asmPath) + " cause:") + _message);
        this.warning(_plus_3, 
          AvallaPackage.Literals.SCENARIO__SPEC);
      } else if (_t instanceof Throwable) {
        final Throwable t = (Throwable)_t;
        String _message_1 = t.getMessage();
        String _plus_4 = ("error " + _message_1);
        this.error(_plus_4, AvallaPackage.Literals.SCENARIO__SPEC);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  public Map<ChooseRule, String> setAsmCollection(final AsmCollection collection) {
    Map<ChooseRule, String> _xblockexpression = null;
    {
      this.asmCollection = collection;
      final HashSet<Function> functions = new HashSet<Function>();
      final Consumer<Asm> _function = (Asm asm) -> {
        functions.addAll(asm.getHeaderSection().getSignature().getFunction());
      };
      this.asmCollection.forEach(_function);
      final Predicate<Function> _function_1 = (Function x) -> {
        return (x instanceof MonitoredFunction);
      };
      final java.util.function.Function<Function, String> _function_2 = (Function y) -> {
        return y.getName();
      };
      this.monFunNames = functions.stream().filter(_function_1).<String>map(_function_2).collect(Collectors.<String>toList());
      final Predicate<Function> _function_3 = (Function x) -> {
        return (x instanceof ControlledFunction);
      };
      final java.util.function.Function<Function, String> _function_4 = (Function y) -> {
        return y.getName();
      };
      this.controlledFunNames = functions.stream().filter(_function_3).<String>map(_function_4).collect(Collectors.<String>toList());
      final Predicate<Function> _function_5 = (Function x) -> {
        return (x instanceof SharedFunction);
      };
      final java.util.function.Function<Function, String> _function_6 = (Function y) -> {
        return y.getName();
      };
      this.sharedFunNames = functions.stream().filter(_function_5).<String>map(_function_6).collect(Collectors.<String>toList());
      _xblockexpression = this.chooseRules = AsmCollectionUtility.getAllChooseRules(this.asmCollection);
    }
    return _xblockexpression;
  }

  @Check
  public void checkSetLocation(final Set set) {
    if ((this.asmCollection == null)) {
      return;
    }
    String _xifexpression = null;
    boolean _contains = set.getLocation().contains("(");
    if (_contains) {
      _xifexpression = set.getLocation().substring(0, set.getLocation().indexOf("("));
    } else {
      _xifexpression = set.getLocation();
    }
    final String locationName = _xifexpression;
    if (((!this.monFunNames.contains(locationName)) && (!this.sharedFunNames.contains(locationName)))) {
      boolean _contains_1 = this.controlledFunNames.contains(locationName);
      boolean _not = (!_contains_1);
      if (_not) {
        this.error((("location " + locationName) + " does not exist"), AvallaPackage.Literals.SET__LOCATION);
      } else {
        this.error((("location " + locationName) + " is controlled cannot be set"), AvallaPackage.Literals.SET__LOCATION);
      }
    }
  }

  @Check
  public void checkBlock(final Block b) {
    final Scenario s = this.getScenario(b);
    final List<String> names = new ArrayList<String>();
    final List<String> duplicated = new ArrayList<String>();
    ScenarioUtility.addBlockNames(names, duplicated, s.getElements());
    boolean _contains = duplicated.contains(b.getName());
    if (_contains) {
      String _name = b.getName();
      String _plus = ("block " + _name);
      String _plus_1 = (_plus + " declared multiple times");
      this.error(_plus_1, AvallaPackage.Literals.BLOCK__NAME);
    }
  }

  @Check
  public void checkPick(final Pick pick) {
    String errorMessage = ScenarioUtility.checkPickRule(pick, this.asmCollection.getMain());
    if ((errorMessage != null)) {
      this.error(errorMessage, AvallaPackage.Literals.PICK__RULE);
    } else {
      errorMessage = ScenarioUtility.checkPickVariable(pick, this.chooseRules);
    }
    if ((errorMessage != null)) {
      this.error(errorMessage, AvallaPackage.Literals.PICK__VAR);
    }
  }

  public Scenario getScenario(final Element b) {
    EObject _eContainer = b.eContainer();
    if ((_eContainer instanceof Scenario)) {
      EObject _eContainer_1 = b.eContainer();
      return ((Scenario) _eContainer_1);
    } else {
      EObject _eContainer_2 = b.eContainer();
      return this.getScenario(((Element) _eContainer_2));
    }
  }

  @Check
  public void checkExecBlock(final ExecBlock eb) {
    try {
      final String scenario = eb.getScenario();
      if ((scenario == null)) {
        final Scenario s = this.getScenario(eb);
        final List<String> blockNames = ScenarioUtility.getBlockNames(s);
        boolean _contains = blockNames.contains(eb.getBlock());
        boolean _not = (!_contains);
        if (_not) {
          String _block = eb.getBlock();
          String _plus = ("block " + _block);
          String _plus_1 = (_plus + " does not exist in this scenario");
          this.error(_plus_1, 
            AvallaPackage.Literals.EXEC_BLOCK__BLOCK);
        }
      } else {
        String _baseDir = ScenarioUtility.getBaseDir(this.getScenario(eb));
        String _plus_2 = (_baseDir + File.separator);
        String _plus_3 = (_plus_2 + scenario);
        final String scenarioPath = (_plus_3 + ".avalla");
        boolean _exists = Files.exists(Paths.get(scenarioPath));
        boolean _not_1 = (!_exists);
        if (_not_1) {
          this.error((("scenario " + scenario) + " does not exist"), AvallaPackage.Literals.EXEC_BLOCK__SCENARIO);
        } else {
          final Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
          final ResourceSet rs = injector.<ResourceSet>getInstance(ResourceSet.class);
          InputOutput.<String>println(scenarioPath);
          final Resource resource = rs.getResource(URI.createFileURI(scenarioPath), true);
          resource.load(null);
          EObject _get = resource.getContents().get(0);
          final Scenario sc = ((Scenario) _get);
          boolean _contains_1 = ScenarioUtility.getBlockNames(sc).contains(eb.getBlock());
          boolean _not_2 = (!_contains_1);
          if (_not_2) {
            String _block_1 = eb.getBlock();
            String _plus_4 = ((("scenario " + scenario) + " does not contain block ") + _block_1);
            this.error(_plus_4, 
              AvallaPackage.Literals.EXEC_BLOCK__BLOCK);
          }
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
