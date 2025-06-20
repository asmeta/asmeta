package asmeta.mutation.mutationscore;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

public class TestForMutScoreSingleFile {


	@Test
	public void test1file() throws Exception {
		String avallaTotest = TestForCoverageShortICTSS.base_dir + "/results/run1/atgttests/Lift/testRG_r_Main_TTRG2.avalla";
		MutatedScenarioExecutor executor = new MutatedScenarioExecutor();
		var res = executor.computeMutationScore(avallaTotest);
		System.out.println(res);
	}

}
