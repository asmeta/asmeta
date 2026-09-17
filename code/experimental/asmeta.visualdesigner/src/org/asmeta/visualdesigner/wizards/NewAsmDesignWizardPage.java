package org.asmeta.visualdesigner.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class NewAsmDesignWizardPage extends WizardNewFileCreationPage {

    private byte[] initialContents = new byte[0];

    public NewAsmDesignWizardPage(IStructuredSelection selection) {
        super("newAsmDesignPage", selection);

        setTitle("ASMETA Visual Design");
        setDescription("Create a new ASMETA visual design model.");
        setFileExtension("asmdesign");
        setFileName("new-design.asmdesign");
    }

    public void setInitialContents(byte[] initialContents) {
        if (initialContents != null) {
            this.initialContents = initialContents;
        } else {
            this.initialContents = new byte[0];
        }
    }

    @Override
    protected InputStream getInitialContents() {
        return new ByteArrayInputStream(initialContents);
    }
}