package org.asmeta.visualdesigner.export;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.persistence.DiagramModelJson;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

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
        IFile destinationFile =
                exportPage.getDestinationFile();

        try {
            getContainer().run(true, false,
                    monitor -> {
                        try {
                            monitor.beginTask(
                                    "Exporting ASM model",
                                    3
                            );

                            Map<String, DiagramModel> diagrams;

                            try (InputStream input = sourceFile.getContents()) {
                                diagrams = new DiagramModelJson().load(input);
                            }

                            monitor.worked(1);

                            String asmName = getAsmName(destinationFile);

                            String asmCode = new AsmModelExporter().export(asmName, diagrams);
                            monitor.worked(1);

                            byte[] bytes = asmCode.getBytes(StandardCharsets.UTF_8);

                            try (ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
                                if (destinationFile.exists()) {
                                    destinationFile.setContents(input, true, false, monitor);
                                } else {
                                    destinationFile.create(input, true, monitor);
                                }
                            }

                            monitor.worked(1);
                        } catch (CoreException | java.io.IOException | AsmExportException exception) {

                            throw new InvocationTargetException(exception);
                        } finally {
                            monitor.done();
                        }
                    }
            );

            return true;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return false;
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            MessageDialog.openError(getShell(), "ASM export", "Could not export the ASM model.\n\n" + getErrorMessage(cause));
            return false;
        }
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