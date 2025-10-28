package asmeta.evotest.experiments.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.eclipse.xtend.lib.macro.file.Path;

import asmeta.AsmCollection;

public class ModelCollector {
	public static final String ASM_EXAMPLES = "../../../../asm_examples";
	public static final String ASE_EXP_MODELS = "data/ase-exp/models";

	public static final String OLD = "old";
	public static final List<String> SKIP_DIRS = Arrays.asList("STDL", "asmetal2cpp", "test", "workspaceMSL", "drafts");

	// TEMPORARY
	public static void main(String[] args) throws IOException {
		List<String> validAsms = collectModels(ASM_EXAMPLES);
		writeToFile(validAsms, ASM_EXAMPLES, "data/fm26-exp/model_list.txt");
	}

	/**
	 * Recursively extracts valid ASM benchmark files from a given directory.
	 * 
	 * A file is considered valid if: it has the .asm extension, it is not marked as
	 * "old", it is not a module, it can be parsed successfully by ASMParser.
	 *
	 * @param baseDir path to the root directory containing ASM files
	 * @return the list of canonical paths of valid asms
	 * @throws IOException if an I/O error occurs during path resolution
	 */
	public static List<String> collectModels(String baseDir) throws IOException {
		File dir = new File(baseDir);
		System.out.println("Base directory: " + dir.getCanonicalPath());
		List<String> validAsms = new ArrayList<>();
		try {
			if (dir.exists())
				collectModelsRec(dir, validAsms);
			else
				System.err.println("Error: File or directory does not exist: " + dir.getCanonicalPath());
		} catch (IOException e) {
			System.err.println("Error while traversing directories:");
			e.printStackTrace();
		}
		System.out.println("Successfully parsed ASM files: " + validAsms.size());
		return validAsms;
	}

	/**
	 * Recursive helper that traverses directories and processes ASM files.
	 *
	 * @param file      current file or directory to analyze
	 * @param validAsms list that collects successfully parsed ASM file paths so far
	 * @throws IOException if an I/O error occurs during path resolution
	 */
	private static void collectModelsRec(File file, List<String> validAsms) throws IOException {
		if (file.isDirectory()) {
			String directoryName = file.getName();
			if (SKIP_DIRS.contains(directoryName) || containsIgnoreCase(directoryName, OLD))
				return;
			for (File child : file.listFiles())
				collectModelsRec(child, validAsms);
		} else {
			String fileName = file.getName();
			if (!fileName.endsWith(ASMParser.ASM_EXTENSION) || containsIgnoreCase(fileName, OLD))
				return;
			String filePath = file.getCanonicalPath();
			try {
				AsmCollection asms = ASMParser.setUpReadAsm(file);
				if (asms.getMain().getMainrule() == null) {
					System.err.println("Skipping (defines a module): " + filePath);
					return;
				}
				System.out.println("Adding: " + filePath);
				validAsms.add(filePath);
			} catch (Exception e) {
				System.err.println("Skipping (parse error): " + filePath);
				e.printStackTrace();
			}
		}
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
	
	/**
	 * Writes the list of ASM file paths (relativized to basePath) to the file in targetPath
	 * 
	 * @param validAsms list that collects successfully parsed ASM file paths
	 * @param basePath path of the base directory containing the ASM files
	 * @param targetPath the target file
	 * @throws IOException if an I/O error occurs during path resolution
	 */
	private static void writeToFile(List<String> validAsms, String basePath, String targetPath) throws IOException {
		FileOutputStream os = new FileOutputStream(targetPath);
		for (String fullPath : validAsms) {
			Path relativePath = new Path(fullPath).relativize(new File(basePath).getCanonicalPath());
			String line = relativePath.toString() + System.lineSeparator();
			os.write(line.getBytes());
		}
		os.close();
	}

}
