package asmeta.junit2avalla.filewriter;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import asmeta.junit2avalla.filewriter.FileWriterImpl;
import asmeta.junit2avalla.model.ScenarioFile;
import asmeta.junit2avalla.util.ScenarioAvallaUtil;
import org.junit.Test;

public class FileWriterImplTest {

  @Test
  public void writeToFileAndCheckThatFileIsCreated(){

    FileWriterImpl fileWriterImpl = new FileWriterImpl();
    ScenarioFile scenarioFile = ScenarioAvallaUtil.getScenarioFile();

    boolean result = fileWriterImpl.writeToFile(scenarioFile);
    assertTrue(result);

    Path outputFile = Paths.get(
        System.getProperty("user.dir"),
        "output",
        scenarioFile.getName(),
        scenarioFile.getExtension());
    Files.exists(outputFile);

  }

}
