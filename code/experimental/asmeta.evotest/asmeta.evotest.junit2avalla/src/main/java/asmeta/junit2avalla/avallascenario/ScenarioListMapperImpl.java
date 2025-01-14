package asmeta.junit2avalla.avallascenario;

import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.ScenarioFile;

/**
 * The {@code ScenarioListMapperImpl} class implements the
 * {@link ScenarioListMapper} interface and is responsible for mapping a list of
 * {@link Scenario} objects to a list of {@link ScenarioFile} objects. It uses
 * the {@link ScenarioWriter} to perform the conversion of each valid
 * {@link Scenario}.
 */
public class ScenarioListMapperImpl implements ScenarioListMapper {

	/** Logger */
	private final Logger log = LogManager.getLogger(ScenarioListMapperImpl.class);

	/**
	 * The {@link ScenarioWriter} used to convert each {@link Scenario} into a
	 * {@link ScenarioFile}.
	 */
	private final ScenarioWriter scenarioWriter;

	/**
	 * Constructs a new {@code ScenarioListMapper} and initializes the
	 * {@code ScenarioWriter}.
	 */
	public ScenarioListMapperImpl() {
		this.scenarioWriter = new ScenarioWriter();
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * This method iterates through the given list of {@link Scenario} objects and
	 * converts each valid {@link Scenario} into a {@link ScenarioFile} using the
	 * {@link ScenarioWriter}.
	 * </p>
	 *
	 * @param scenarioList the list of {@link Scenario} objects to be mapped to
	 *                     {@link ScenarioFile} objects.
	 * @return a list of {@link ScenarioFile} objects corresponding to the valid
	 *         {@link Scenario} objects in the input list.
	 */
	@Override
	public List<ScenarioFile> mapScenarioListToFileList(List<Scenario> scenarioList) {
		log.debug("Mapping ScenarioList to ScenarioFile");
		List<ScenarioFile> scenarioFiles = new LinkedList<>();
		int validScenario = 0;
		for (Scenario scenario : scenarioList) {
			log.debug("Processing the scenario: {}", scenario);
			if (scenario.isValid()) {
				log.debug("Scenario is valid, mapping scenario to ScenarioFile");
				scenarioFiles.add(scenarioWriter.write(scenario));
				validScenario++;
			}
		}
		log.info("Found {} scenarios and {} of them are valid scenarios.", scenarioList.size(), validScenario);
		return scenarioFiles;
	}

}
