package org.asmeta.visualdesigner.export;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class AsmExportWizardPage extends WizardPage {

    private Text sourceText;
    private Text destinationText;

    private IFile selectedSourceFile;
    private IContainer selectedDestinationContainer;

    public AsmExportWizardPage(IStructuredSelection selection) {
        super("asmExportPage");

        setTitle("Export ASM model");
        setDescription("Generate an ASM file from an ASM Designer model.");
        initializeSelection(selection);
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(3, false));

        createSourceControls(container);
        createDestinationControls(container);

        setControl(container);

        initializeFields();
        validatePage();
    }

    private void createSourceControls(Composite parent) {
        Label sourceLabel = new Label(parent, SWT.NONE);
        sourceLabel.setText("ASM design file:");

        sourceText = new Text(parent, SWT.BORDER);
        sourceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Button browseButton = new Button(parent, SWT.PUSH);
        browseButton.setText("Browse...");
        setButtonLayoutData(browseButton);

        browseButton.addListener(SWT.Selection, event -> browseSourceFile());

        ModifyListener listener = event -> validatePage();

        sourceText.addModifyListener(listener);
    }

    private void createDestinationControls(Composite parent) {
        Label destinationLabel = new Label(parent, SWT.NONE);
        destinationLabel.setText("Destination file:");

        destinationText = new Text(parent, SWT.BORDER);
        destinationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Button browseButton = new Button(parent, SWT.PUSH);
        browseButton.setText("Browse...");
        setButtonLayoutData(browseButton);

        browseButton.addListener(SWT.Selection, event -> browseDestinationContainer());

        destinationText.addModifyListener(event -> validatePage());
    }

    private void initializeSelection(IStructuredSelection selection) {
        if (selection != null && selection.size() == 1 && selection.getFirstElement() instanceof IFile) {
            IFile file = (IFile) selection.getFirstElement();
            if ("asmdesign".equalsIgnoreCase(file.getFileExtension())) {
                selectedSourceFile = file;
                selectedDestinationContainer = file.getParent();
            }
        }
    }

    private void initializeFields() {
        if (selectedSourceFile != null) {
            sourceText.setText(selectedSourceFile.getFullPath().toString());
            destinationText.setText(createDefaultDestinationPath(selectedSourceFile));
        }
    }

    private String createDefaultDestinationPath(IFile sourceFile) {
        String fileName = sourceFile.getFullPath().removeFileExtension().addFileExtension("asm").toString();
        return fileName;
    }

    private void browseSourceFile() {
        ElementTreeSelectionDialog dialog =new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(), new WorkbenchContentProvider());

        dialog.setTitle("Select ASM design");
        dialog.setMessage("Select the .asmdesign file to export.");
        dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
        dialog.addFilter(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement, Object element) {
                boolean visible = element instanceof IContainer;

                if (element instanceof IFile) {
                    IFile file = (IFile) element;
                    visible = "asmdesign".equalsIgnoreCase(file.getFileExtension());
                }

                return visible;
            }
        });

        if (selectedSourceFile != null) {
            dialog.setInitialSelection(selectedSourceFile);
        }

        if (dialog.open() == ElementTreeSelectionDialog.OK) {
            Object result = dialog.getFirstResult();

            if (result instanceof IFile) {
                selectedSourceFile = (IFile) result;
                selectedDestinationContainer =selectedSourceFile.getParent();
                sourceText.setText(selectedSourceFile.getFullPath().toString());
                destinationText.setText(createDefaultDestinationPath(selectedSourceFile));
            }
        }
    }

    private void browseDestinationContainer() {
        ContainerSelectionDialog dialog =new ContainerSelectionDialog(getShell(), selectedDestinationContainer, false, "Select the destination folder.");
        if (dialog.open() == ContainerSelectionDialog.OK) {
            Object[] result = dialog.getResult();
            if (result != null && result.length > 0 && result[0] instanceof IPath) {

                IPath containerPath = (IPath) result[0];
                IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(containerPath);

                if (resource instanceof IContainer) {
                    selectedDestinationContainer =(IContainer) resource;
                }

                String currentFileName =getDestinationFileName();

                if (currentFileName.isBlank()) {
                    currentFileName = "model.asm";
                }

                destinationText.setText(containerPath.append(currentFileName).toString());
            }
        }
    }

    private String getDestinationFileName() {
        String fileName = "";

        if (destinationText != null) {
            String value = destinationText.getText().trim();
            if (!value.isEmpty()) {
                fileName = IPath.fromPortableString(value).lastSegment();
            }
        }

        return fileName != null ? fileName : "";
    }

    private void validatePage() {
        String error = null;

        IFile sourceFile = resolveSourceFile();
        IFile destinationFile = resolveDestinationFile();

        if (sourceText == null || sourceText.getText().isBlank()) {
            error = "Select an ASM design file.";
        } else if (sourceFile == null || !sourceFile.exists()) {
            error = "The selected ASM design file does not exist.";
        } else if (!"asmdesign".equalsIgnoreCase(sourceFile.getFileExtension())) {
            error = "The source file must have the .asmdesign extension.";
        } else if (destinationText == null || destinationText.getText().isBlank()) {
            error = "Enter the destination ASM file.";
        } else if (destinationFile == null) {
            error = "The destination must be inside the workspace.";
        } else if (!"asm".equalsIgnoreCase(
                destinationFile.getFileExtension()
        )) {
            error = "The destination file must have the .asm extension.";
        } else if (!destinationFile.getParent().exists()) {
            error = "The destination folder does not exist.";
        } else if (sourceFile.getFullPath().equals(destinationFile.getFullPath())) {
            error = "The source and destination files must be different.";
        }

        setErrorMessage(error);
        setPageComplete(error == null);
    }

    public IFile getSourceFile() {
        return resolveSourceFile();
    }

    public IFile getDestinationFile() {
        return resolveDestinationFile();
    }

    private IFile resolveSourceFile() {
        IFile file = null;

        if (sourceText != null && !sourceText.getText().isBlank()) {
            IPath path = IPath.fromPortableString(sourceText.getText().trim());
            file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
        }

        return file;
    }

    private IFile resolveDestinationFile() {
        IFile file = null;

        if (destinationText != null && !destinationText.getText().isBlank()) {
            IPath path = IPath.fromPortableString(destinationText.getText().trim());
            if (path.segmentCount() >= 2) {
                file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            }
        }

        return file;
    }
}