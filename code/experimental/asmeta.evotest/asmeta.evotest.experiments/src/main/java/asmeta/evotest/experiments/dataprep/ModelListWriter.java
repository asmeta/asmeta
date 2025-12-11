package asmeta.evotest.experiments.dataprep;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import asmeta.evotest.experiments.dataprep.ModelDirectoryScanner.FileLabel;

public final class ModelListWriter {

	private ModelListWriter() {
		// Prevent instantiation
	}

	/**
	 * Writes a grouped and formatted list of file paths to the specified target file.
	 *
	 * @param collectedFiles a map associating file paths with their corresponding {@link FileLabel}
	 * @param totalAsms      the total number of files with .asm extension
	 * @param targetPath     the path of the target output file
	 * @throws IOException if an I/O error occurs during writing
	 */
	public static void writeToFile(Map<String, FileLabel> collectedFiles, int totalAsms, String targetPath) {
		try (FileOutputStream os = new FileOutputStream(targetPath)) {
	        // For string formatting
	        int maxLen = collectedFiles.keySet().stream()
	                .mapToInt(str -> str.trim().length())
	                .max().orElse(0);
	        // Write groups in a fixed order
	        List<FileLabel> order = List.of(
	        		FileLabel.SKIP_DIR,
	        		FileLabel.OLD_DIR,
	        		FileLabel.SKIP_ASM,
	        		FileLabel.OLD_ASM,
	        		FileLabel.DUPLICATE_ASM,
	        		FileLabel.ASM_MODULE,
	        		FileLabel.PARSE_ERR_ASM,
	        		FileLabel.NO_PAR,
	        		FileLabel.VALID_ASM
	        );
	        // Print total number of specs (valid and invalid)
	        String initialComment = "// total: " + totalAsms + System.lineSeparator() + System.lineSeparator();;
	        os.write(initialComment.getBytes());
	        os.write(System.lineSeparator().getBytes());
	        // Group by label
	        Map<FileLabel, List<String>> groupedByLabel = new LinkedHashMap<>();
	        for (FileLabel label : order) {
	            groupedByLabel.put(label, ModelDirectoryScanner.getSublistByLabel(collectedFiles, label));
	        }
	        // Write each group
	        for (Entry<FileLabel, List<String>> group : groupedByLabel.entrySet()) {
	            String comment = group.getKey().getComment();
	            List<String> pathList = group.getValue();
	            writeLines(pathList, os, comment, maxLen);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Writes a list of file paths to the given output stream, optionally adding a comment.
	 * If comment is not empty, each line is prefixed with "//" and
	 * suffixed with the comment text, aligned using maxLen.
	 *
	 * @param pathList list of file paths to write
	 * @param os       the output stream to write to
	 * @param comment  the comment to append to each line; if empty, no comment is added
	 * @param maxLen   the maximum path length used for alignment
	 * @throws IOException if an I/O error occurs during writing
	 */
	protected static void writeLines(List<String> pathList, FileOutputStream os, String comment, int maxLen)
			throws IOException {
		String initialComment = comment.isEmpty() ? "valid asm" : comment;
        initialComment = "// " + initialComment + ": " + pathList.size() + System.lineSeparator();
        os.write(initialComment.getBytes());
		for (String path : pathList) {
			if (!comment.isEmpty()) {
				path = String.format("%-" + (maxLen + 1) + "s", path);
				path = "//" + path + "// " + comment;
			}
			path += System.lineSeparator();
			os.write(path.getBytes());
		}
		os.write(System.lineSeparator().getBytes());
	}

}
