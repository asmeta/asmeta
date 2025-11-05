package asmeta.evotest.experiments.scenario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.asmeta.xt.validator.AsmetaV;

public class ScenarioDataCollector {
	
	/**
	 * Counts the number of avalla scenarios in a directory
	 * 
	 * @param avallaFolder path of the folder containing the avalla files
	 * @return the number of scenarios in the directory
	 * @throws IOException if an I/O error occurs.
	 */
	public static int getNumberOfScenario(String avallaFolder) throws IOException {
		Path avallaPath = Path.of(avallaFolder);
		return (int) Files.list(avallaPath).filter(path -> path.toString().endsWith(AsmetaV.SCENARIO_EXTENSION)).count();
	}
	
	/**
	 * Computes the total number of {@code step}, {@code check}, and {@code set}
	 * statements across all Avalla scenario files in the given directory.
	 *
	 * @param dir the directory containing Avalla scenario files
	 * @return a map with counts keyed as {@code n_step}, {@code n_check}, and {@code n_set}
	 * @throws IOException if the directory cannot be read
	 */
	public static Map<String, Integer> collectAvallaData(String dir) throws IOException  {
		Map<String, Integer> avallaInfo = new HashMap<>();
		for (String statement : List.of("step", "check", "set"))
			avallaInfo.put("n_" + statement,
					getStatementCount(dir, statement).stream().reduce(0, Integer::sum));
		return avallaInfo;
	}

	/**
	 * Counts all occurrences of the given statement for each avalla file present in
	 * the given folder
	 * 
	 * @param avallaFolder path of the folder containing the avalla files
	 * @param statement    the statement to count
	 * @return the list containing the number of occurrences for each avalla
	 * @throws IOException if an I/O error occurs.
	 */
	private static List<Integer> getStatementCount(String avallaFolder, String statement) throws IOException {
		Path avallaPath = Path.of(avallaFolder);
		List<Integer> statmentCountList = new ArrayList<>();
		Files.list(avallaPath).filter(path -> path.toString().endsWith(AsmetaV.SCENARIO_EXTENSION)).forEach(path -> {
			try (Stream<String> lines = Files.lines(path)) {
				int statementCount = (int) lines.flatMap(line -> Stream.of(line.split("\\W+")))
						.filter(word -> word.equals(statement)).count();
				statmentCountList.add(statementCount);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return statmentCountList;
	}

}
