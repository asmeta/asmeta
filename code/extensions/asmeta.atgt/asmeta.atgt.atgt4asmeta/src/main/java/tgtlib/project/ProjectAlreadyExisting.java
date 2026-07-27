package tgtlib.project;

public class ProjectAlreadyExisting extends Exception {

	public ProjectAlreadyExisting(String zipFilename) {
		super("project with name "+ zipFilename + " already exists");
	}

}
