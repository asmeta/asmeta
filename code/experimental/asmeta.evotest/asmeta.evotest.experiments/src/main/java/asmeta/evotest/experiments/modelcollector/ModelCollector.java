package asmeta.evotest.experiments.modelcollector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;

public final class ModelCollector {
	
    private ModelCollector() {
        // Prevent instantiation
    }

	public enum FileLabel {
		VALID_ASM(""),
		OLD_ASM("old asm"),
		DUPLICATE_ASM("duplicated"),
		PARSE_ERR_ASM("parsing error"),
		ASM_MODULE("module"),
		OLD_DIR("old directory"),
		SKIP_DIR("skip directory");

		private final String comment;
		FileLabel(String comment) {
			this.comment = comment;
		}
		public String getComment() {
			return comment;
		}
	}

	private static final String OLD = "old";

	private static final List<String> SKIP_DIRS = Arrays.asList(
			"STDL",
			"asmetal2cpp",
			"test",
			"workspaceMSL",
			"drafts"
		);

	private static final List<String> DUPLICATES = Arrays.asList(
			"examples\\landingGearSystem\\LandingGearSystemWithCylAndValves.asm",
			"examples\\landingGearSystem\\LandingGearSystemWithCylValvesAndSensors3LandSets.asm",
			"examples\\landingGearSystem\\LandingGearSystemWithCylAndValves_v2.asm",
			"examples\\landingGearSystem\\LandingGearSystemWithCylValvesAndSensors_v2.asm"
		);

	/**
	 * Extracts ASM relative file paths from a given directory. Also label each file
	 * path.
	 *
	 * @param baseDir path to the root directory containing ASM files
	 * @return the ASM files along with their labels
	 * @throws IOException if an I/O error occurs during path resolution
	 */
	public static Map<String, FileLabel> collectModels(String baseDir) {
		try {
			File dir = new File(baseDir);
			System.out.println("Base directory: " + dir.getCanonicalPath());
			Map<String, FileLabel> collectedFiles = new HashMap<>();
			if (dir.exists())
				collectModelsRec(dir, collectedFiles, baseDir);
			else
				System.err.println("Error: File or directory does not exist: " + dir.getCanonicalPath());
			List<String> validAsms = getSublistByLabel(collectedFiles, FileLabel.VALID_ASM);
			System.out.println("\nValid ASM files: " + validAsms.size());
			return collectedFiles;
		} catch (IOException e) {
			System.err.println("Error while traversing directories:");
			e.printStackTrace();
			return new HashMap<>();
		}
	}

	/**
	 * Get the list of file paths with a given label
	 * 
	 * @param collectedFiles map with paths of the collected files
	 * @param label          the label
	 * @return a list of file paths
	 */
	protected static List<String> getSublistByLabel(Map<String, FileLabel> collectedFiles, FileLabel label) {
		return collectedFiles.entrySet().stream()
				.filter(entry -> entry.getValue() == label)
				.map(entry -> entry.getKey()).collect(Collectors.toList());
	}

	/**
	 * Recursive helper that traverses directories and collects ASM files.
	 *
	 * @param file           current file or directory to analyze
	 * @param collectedFiles map with paths of the files collected so far
	 * @throws IOException if an I/O error occurs during path resolution
	 */
	private static void collectModelsRec(File file, Map<String, FileLabel> collectedFiles, String basePath)
			throws IOException {
		String name = file.getName();
		String path = getRelativePath(new File(basePath), file);
		if (file.isDirectory()) {
			if (SKIP_DIRS.contains(name)) {
				collectedFiles.put(path, FileLabel.SKIP_DIR);
			} else if (containsIgnoreCase(name, OLD)) {
				collectedFiles.put(path, FileLabel.OLD_DIR);
			} else {
				for (File child : file.listFiles())
					collectModelsRec(child, collectedFiles, basePath);
			}
		} else {
			if (name.endsWith(ASMParser.ASM_EXTENSION)) {
				if (containsIgnoreCase(name, OLD)) {
					collectedFiles.put(path, FileLabel.OLD_ASM);
				} else if (DUPLICATES.contains(path)) {
					collectedFiles.put(path, FileLabel.DUPLICATE_ASM);
				} else {
					try {
						AsmCollection asms = ASMParser.setUpReadAsm(file);
						if (asms.getMain().getMainrule() == null) {
							collectedFiles.put(path, FileLabel.ASM_MODULE);
							return;
						}
						collectedFiles.put(path, FileLabel.VALID_ASM);
					} catch (Exception e) {
						collectedFiles.put(path, FileLabel.PARSE_ERR_ASM);
					}
				}
			}
		}
	}

	/**
	 * Returns the relative path of a target file or directory with respect to a
	 * base directory.
	 * 
	 * @param base   the base directory
	 * @param target the target file or directory
	 * @return the relative path, or the canonical target path if it cannot be
	 *         relativized
	 * @throws IOException if an I/O error occurs while accessing the paths
	 */
	private static String getRelativePath(File base, File target) throws IOException {
		Path basePath = base.getCanonicalFile().toPath();
		Path targetPath = target.getCanonicalFile().toPath();
		if (targetPath.startsWith(basePath))
			return basePath.relativize(targetPath).toString();
		return targetPath.toString();
	}

	/**
	 * Returns true if and only if str contains sub. The check is case insensitive.
	 * 
	 * @param str the sequence
	 * @param sub the sequence to search for
	 * @return true if str contains sub, ignoring case, false otherwise
	 */
	private static boolean containsIgnoreCase(String str, String sub) {
		if (str == null || sub == null)
			return false;
		return str.toLowerCase().contains(sub.toLowerCase());
	}

}
