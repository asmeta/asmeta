package org.asmeta.junit2avalla.filewriter;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.asmeta.junit2avalla.filewriter.FileWriterImpl;
import org.asmeta.junit2avalla.model.ScenarioFile;
import org.asmeta.junit2avalla.util.ScenarioAvallaUtil;
import org.junit.Test;

public class FileWriterImplTest {

  @Test
  public void writeToFileAndCheckThatFileIsCreated(){

    FileWriterImpl fileWriterImpl = new FileWriterImpl();
    ScenarioFile scenarioFile = ScenarioAvallaUtil.getScenarioFile();

    boolean result = false;
	try {
		result = fileWriterImpl.writeToFile(scenarioFile);
	} catch (IOException e) {
		e.printStackTrace();
	}
    assertTrue(result);

    Path outputFile = Paths.get(
        System.getProperty("user.dir"),
        "output",
        scenarioFile.getName(),
        scenarioFile.getExtension());
    Files.exists(outputFile);

  }

}