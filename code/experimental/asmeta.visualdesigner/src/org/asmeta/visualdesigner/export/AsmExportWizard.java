package org.asmeta.visualdesigner.export;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.persistence.DiagramModelJson;
import org.asmeta.visualdesigner.validation.AsmParserValidator;
import org.asmeta.visualdesigner.validation.AsmValidationException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.asmeta.visualdesigner.validation.StandardLibraryManager;

public class AsmExportWizard extends Wizard implements IExportWizard {

    private IStructuredSelection selection;
    private AsmExportWizardPage exportPage;

    public AsmExportWizard() {
        setWindowTitle("Export ASM model");
        setNeedsProgressMonitor(true);
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
    }

    @Override
    public void addPages() {
        exportPage = new AsmExportWizardPage(selection);
        addPage(exportPage);
    }

    @Override
    public boolean performFinish() {
        IFile sourceFile = exportPage.getSourceFile();

        IFile destinationFile = exportPage.getDestinationFile();

        AsmModelExporter exporter = new AsmModelExporter();

        AsmParserValidator parserValidator = new AsmParserValidator();

        StandardLibraryManager standardLibraryManager = new StandardLibraryManager();
        
        try {
            getContainer().run(true,false,
                    monitor -> {
                        try {
                            monitor.beginTask("Exporting and validating ASM model", 5);
                            Map<String, DiagramModel> diagrams;
                            try (InputStream input = sourceFile.getContents()) {
                                diagrams = new DiagramModelJson().load(input);
                            }
                            monitor.worked(1);
                            String asmName = getAsmName(destinationFile);
                            String asmCode = exporter.export(asmName, diagrams);
                            monitor.worked(1);
                            if (!exporter.hasIncompleteModel()) {
                                parserValidator.validate(asmName, asmCode);
                            }
                            monitor.worked(1);
                            standardLibraryManager.copyTo(destinationFile.getParent(), monitor);
                            monitor.worked(1);
                            writeAsmFile(destinationFile, asmCode, monitor);
                            monitor.worked(1);
                        } catch (CoreException
                                | java.io.IOException
                                | AsmExportException
                                | AsmValidationException exception) {
                            throw new InvocationTargetException(exception);
                        } finally {
                            monitor.done();
                        }
                    }
            );

            showExportResult(exporter);

            return true;

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return false;

        } catch (InvocationTargetException exception) {
            showExportError(exception.getCause());
            return false;
        }
    }

    private void writeAsmFile(IFile destinationFile, String asmCode, org.eclipse.core.runtime.IProgressMonitor monitor) throws CoreException {

        byte[] bytes = asmCode.getBytes(StandardCharsets.UTF_8);

        try (ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
            if (destinationFile.exists()) {
                destinationFile.setContents(input, true, false, monitor);
            } else {
                destinationFile.create(input, true, monitor);
            }
        } catch (java.io.IOException exception) {
            throw new CoreException(org.eclipse.core.runtime.Status.error("Could not close the generated ASM file.",exception));
        }
    }


    private void showExportResult(AsmModelExporter exporter) {
        if (exporter.hasIncompleteModel()) {
            MessageDialog.openWarning(getShell(),
                    "Incomplete ASM model",
                    "The ASM model was exported successfully, "
                            + "but some parts of the visual model "
                            + "are incomplete.\n\n"
                            + "The generated .asm file contains "
                            + "comments indicating the sections "
                            + "that still need to be completed.\n\n"
                            + "Parser validation was skipped because "
                            + "an incomplete model cannot produce "
                            + "a valid ASM specification."
            );
            return;
        }

        MessageDialog.openInformation(getShell(),
                "ASM export",
                "The ASM model was exported successfully "
                        + "and accepted by the ASMETA parser."
        );
    }

    private void showExportError(Throwable cause) {
        if (cause instanceof AsmValidationException) {
            MessageDialog.openError(getShell(),
                    "ASM parser validation",
                    "The .asm file was generated, but it was "
                            + "not accepted by the ASMETA parser.\n\n"
                            + getErrorMessage(cause)
            );

            return;
        }

        MessageDialog.openError(getShell(),
                "ASM parser validation",
                "The ASM model was not exported because "
                        + "it was not accepted by the ASMETA parser.\n\n"
                        + getErrorMessage(cause)
        );
    }

    private String getAsmName(IFile destinationFile) {
        String fileName = destinationFile.getName();

        int extensionPosition = fileName.lastIndexOf('.');

        String asmName = fileName;

        if (extensionPosition > 0) {
            asmName = fileName.substring(0, extensionPosition);
        }

        return asmName;
    }

    private String getErrorMessage(Throwable throwable) {
        String message = "Unknown error.";

        if (throwable != null && throwable.getMessage() != null && !throwable.getMessage().isBlank()) {
            message = throwable.getMessage();
        }

        return message;
    }
}