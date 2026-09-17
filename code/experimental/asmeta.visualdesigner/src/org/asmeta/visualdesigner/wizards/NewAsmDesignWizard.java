package org.asmeta.visualdesigner.wizards;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.persistence.DiagramModelJson;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

public class NewAsmDesignWizard extends Wizard implements INewWizard {

    private static final String MAIN_DIAGRAM_NAME = "main";

    private IWorkbench workbench;
    private IStructuredSelection selection;
    private NewAsmDesignWizardPage page;

    public NewAsmDesignWizard() {
        setWindowTitle("New ASMETA Visual Design");
        setNeedsProgressMonitor(false);
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {

        this.workbench = workbench;
        this.selection = selection;
    }

    @Override
    public void addPages() {
        page = new NewAsmDesignWizardPage(selection);
        addPage(page);
    }

    @Override
    public boolean performFinish() {
        boolean finished = false;

        try {
            byte[] contents = createInitialModelContents();
            page.setInitialContents(contents);

            IFile file = page.createNewFile();

            if (file != null) {
                IDE.openEditor(workbench.getActiveWorkbenchWindow().getActivePage(), file, true);

                finished = true;
            }
        } catch (IOException | PartInitException exception) {
            MessageDialog.openError(getShell(),
                    "ASMETA Visual Design",
                    "Could not create the ASM design file.\n\n" + exception.getMessage()
            );
        }

        return finished;
    }

    private byte[] createInitialModelContents() throws IOException {
        Map<String, DiagramModel> diagrams = new LinkedHashMap<>();

        DiagramModel mainModel = new DiagramModel();
        diagrams.put(MAIN_DIAGRAM_NAME, mainModel);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        DiagramModelJson diagramModelJson = new DiagramModelJson();
        diagramModelJson.save(diagrams, output);

        return output.toByteArray();
    }
}