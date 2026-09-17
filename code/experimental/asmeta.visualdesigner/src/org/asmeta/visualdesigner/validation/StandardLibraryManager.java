package org.asmeta.visualdesigner.validation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

public class StandardLibraryManager {

    public static final String FILE_NAME = "StandardLibrary.asm";

    private static final String RESOURCE_PATH = "/resources/" + FILE_NAME;

    public void copyTo(Path directory) throws IOException {
        Path target = directory.resolve(FILE_NAME);

        if (!Files.exists(target)) {
            try (InputStream input = openLibrary()) {
                Files.copy(input, target);
            }
        }
    }

    public void copyTo(IContainer container, IProgressMonitor monitor) throws IOException, CoreException {
        IFile target = container.getFile(IPath.fromPortableString(FILE_NAME));

        if (!target.exists()) {
            try (InputStream input = openLibrary()) {
                target.create(input, true, monitor);
            }
        }
    }

    private InputStream openLibrary() throws IOException {
        InputStream input = StandardLibraryManager.class.getResourceAsStream(RESOURCE_PATH);

        if (input == null) {
            throw new IOException("Could not locate " + FILE_NAME
                            + " inside the Visual Designer bundle.");
        }

        return input;
    }
}