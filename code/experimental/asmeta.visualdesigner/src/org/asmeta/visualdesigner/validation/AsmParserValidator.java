package org.asmeta.visualdesigner.validation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asmeta.parser.ASMParser;

public class AsmParserValidator {

    private final StandardLibraryManager standardLibraryManager = new StandardLibraryManager();
    private static final Pattern ERROR_LOCATION_PATTERN = Pattern.compile("Line:\\s*(\\d+)\\s+column:\\s*(\\d+)", Pattern.CASE_INSENSITIVE);
    
    public void validate(String asmName, String asmCode) throws AsmValidationException {

        Path temporaryDirectory = null;

        try {
            temporaryDirectory = Files.createTempDirectory("asmeta-parser-");

            standardLibraryManager.copyTo(temporaryDirectory);

            Path asmFile = temporaryDirectory.resolve(asmName + ".asm");

            Files.writeString(asmFile, asmCode, StandardCharsets.UTF_8);

            validate(asmFile.toFile());

        } catch (IOException exception) {
            throw new AsmValidationException("Could not create the temporary ASM " + "specification used for validation.",exception);
        } finally {
            deleteTemporaryDirectory(temporaryDirectory);
        }
    }

    public void validate(File asmFile) throws AsmValidationException {
        if (asmFile == null) {
            throw new AsmValidationException("The generated ASM file could not be located.");
        }

        if (!asmFile.exists() || !asmFile.isFile()) {
            throw new AsmValidationException("The generated ASM file does not exist: "
                            + asmFile.getAbsolutePath()
            );
        }

        try {
            ASMParser.setUpReadAsm(asmFile);

        } catch (Exception exception) {
            String message = getParserErrorMessage(exception);
            ParserErrorLocation location = getParserErrorLocation(message);
            throw new AsmValidationException(message, location.line, location.column, exception);
        }
    }

    private void deleteTemporaryDirectory(Path temporaryDirectory) {
        if (temporaryDirectory != null) {
            try (Stream<Path> paths = Files.walk(temporaryDirectory)) {
                paths.sorted(Comparator.reverseOrder())
                        .forEach(this::deleteTemporaryFile);
            } catch (IOException exception) {
                // Ignore cleanup errors to preserve the validation result.
            }
        }
    }

    private void deleteTemporaryFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException exception) {
            // Best-effort cleanup of temporary files.
        }
    }

    private String getParserErrorMessage(Exception exception) {
        String message = "The parser did not provide an error message.";
        if (exception.getMessage() != null && !exception.getMessage().isBlank()) {
            message = exception.getMessage();
        }

        return message;
    }
    
    private ParserErrorLocation getParserErrorLocation(String message) {
        int line = -1;
        int column = -1;

        Matcher matcher = ERROR_LOCATION_PATTERN.matcher(message);

        if (matcher.find()) {
            line = Integer.parseInt(matcher.group(1));

            column = Integer.parseInt(matcher.group(2));
        }

        return new ParserErrorLocation(line, column);
    }

    private static class ParserErrorLocation {

        private final int line;
        private final int column;

        private ParserErrorLocation(int line, int column) {
            this.line = line;
            this.column = column;
        }
    }
    
}