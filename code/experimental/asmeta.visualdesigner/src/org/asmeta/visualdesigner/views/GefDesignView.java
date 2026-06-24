package org.asmeta.visualdesigner.views;

import org.asmeta.visualdesigner.editparts.AsmEditPartFactory;
import org.asmeta.visualdesigner.factories.RuleCreationFactory;
import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.RuleType;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.asmeta.visualdesigner.factories.TransitionCreationFactory;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.asmeta.visualdesigner.editparts.RuleNodeEditPart;
import org.asmeta.visualdesigner.editparts.StartNodeEditPart;
import org.asmeta.visualdesigner.editparts.TransitionEditPart;
import org.asmeta.visualdesigner.model.RuleType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/*
 * Main view: here manage the panels and interactions to create the different rules and transitions.
 * Also to modify the information.
 * */

public class GefDesignView extends ViewPart {

    public static final String ID = "org.asmeta.visualdesigner.views.GefDesignView";

    private DefaultEditDomain editDomain;
    private PaletteViewer paletteViewer;
    private GraphicalViewer viewer;
    private DiagramModel model;
    
    private Composite propertiesPanel;

    private Label selectedTitle;
    private Text nameText;
    private Label typeValueLabel;
    private Text conditionText;
    private Text calledRuleText;
    private Text parametersText;

    private StyledText asmetaCodeText;
    
    private RuleNode selectedRule;
    private boolean updatingPropertiesPanel = false;

    @Override
    public void createPartControl(Composite parent) {
        SashForm verticalSash = new SashForm(parent, SWT.VERTICAL);

        SashForm topSash = new SashForm(verticalSash, SWT.HORIZONTAL);

        editDomain = new DefaultEditDomain(null);

        paletteViewer = new PaletteViewer();
        paletteViewer.createControl(topSash);
        paletteViewer.setPaletteRoot(createPaletteRoot());

        viewer = new ScrollingGraphicalViewer();
        viewer.createControl(topSash);

        propertiesPanel = createPropertiesPanel(topSash);

        editDomain.addViewer(viewer);
        editDomain.setPaletteViewer(paletteViewer);

        viewer.setRootEditPart(new ScalableFreeformRootEditPart());
        viewer.setEditPartFactory(new AsmEditPartFactory());

        model = createInitialModel();

        viewer.setContents(model);

        viewer.addSelectionChangedListener(this::handleSelectionChanged);

        installKeyboardShortcuts();

        topSash.setWeights(new int[] { 1, 4, 2 });

        createAsmetaCodePanel(verticalSash);

        verticalSash.setWeights(new int[] { 75, 25 });
    }
    
    private void createAsmetaCodePanel(Composite parent) {
        Composite panel = new Composite(parent, SWT.BORDER);
        panel.setLayout(new GridLayout(1, false));

        Label title = new Label(panel, SWT.NONE);
        title.setText("ASMETA code section");

        asmetaCodeText = new StyledText(
                panel,
                SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER
        );

        asmetaCodeText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        String initialText =
                "signature:\n" +
                "\n" +
                "domains:\n" +
                "\n" +
                "properties:\n";

        asmetaCodeText.setText(initialText);

        model.setAsmetaCode(initialText);

        asmetaCodeText.addModifyListener(e -> {
            model.setAsmetaCode(asmetaCodeText.getText());
        });
    }

    private PaletteRoot createPaletteRoot() {
        PaletteRoot root = new PaletteRoot();

        PaletteGroup tools = new PaletteGroup("Tools");

        SelectionToolEntry selectionTool = new SelectionToolEntry();
        tools.add(selectionTool);
        root.setDefaultEntry(selectionTool);

        PaletteDrawer rulesDrawer = new PaletteDrawer("Rules");

        rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "Conditional Rule",
        	    "Create a conditional rule",
        	    RuleType.CONDITIONAL,
        	    new RuleCreationFactory(RuleType.CONDITIONAL, "Conditional Rule"),
        	    null,
        	    null
        	));
        
        rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "CHOOSE Rule",
        	    "Create a choose rule",
        	    RuleType.CHOOSE,
        	    new RuleCreationFactory(RuleType.CHOOSE, "CHOOSE Rule"),
        	    null,
        	    null
        	));

        rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "CALL Rule",
        	    "Create a call rule",
        	    RuleType.CALL,
        	    new RuleCreationFactory(RuleType.CALL, "CALL Rule"),
        	    null,
        	    null
        	));

        	rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "SEQ Rule",
        	    "Create a sequential rule",
        	    RuleType.SEQ,
        	    new RuleCreationFactory(RuleType.SEQ, "SEQ Rule"),
        	    null,
        	    null
        	));

        	rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "PAR Rule",
        	    "Create a parallel rule",
        	    RuleType.PAR,
        	    new RuleCreationFactory(RuleType.PAR, "PAR Rule"),
        	    null,
        	    null
        	));

        	

        	rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "UPDATE Rule",
        	    "Create an update rule",
        	    RuleType.UPDATE,
        	    new RuleCreationFactory(RuleType.UPDATE, "UPDATE Rule"),
        	    null,
        	    null
        	));

        	rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "FORALL Rule",
        	    "Create a forall rule",
        	    RuleType.FORALL,
        	    new RuleCreationFactory(RuleType.FORALL, "FORALL Rule"),
        	    null,
        	    null
        	));

        	rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "SKIP Rule",
        	    "Create a skip rule",
        	    RuleType.SKIP,
        	    new RuleCreationFactory(RuleType.SKIP, "SKIP Rule"),
        	    null,
        	    null
        	));

        	rulesDrawer.add(new CombinedTemplateCreationEntry(
        	    "Unknown Rule",
        	    "Create an unknown rule",
        	    RuleType.UNKNOWN,
        	    new RuleCreationFactory(RuleType.UNKNOWN, "Rule"),
        	    null,
        	    null
        	));

        PaletteDrawer transitionsDrawer = new PaletteDrawer("Transitions");

        transitionsDrawer.add(new ConnectionCreationToolEntry(
            "Transition",
            "Create a transition between rules",
            new TransitionCreationFactory(),
            null,
            null
        ));
        
        root.add(tools);
        root.add(rulesDrawer);
        root.add(transitionsDrawer);
        
        return root;
    }

    private DiagramModel createInitialModel() {
        return new DiagramModel();
    }

    @Override
    public void setFocus() {
        if (viewer != null) {
            viewer.getControl().setFocus();
        }
    }
    
    private void installKeyboardShortcuts() {
        viewer.getControl().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent event) {
                if (event.keyCode == SWT.DEL || event.keyCode == SWT.BS) {
                    deleteSelection();
                    event.doit = false;
                    return;
                }

                boolean modifierPressed = (event.stateMask & SWT.MOD1) != 0;
                char key = Character.toLowerCase(event.character);

                if (modifierPressed && key == 'z') {
                    if (editDomain.getCommandStack().canUndo()) {
                        editDomain.getCommandStack().undo();
                    }
                    event.doit = false;
                    return;
                }

                if (modifierPressed && key == 'y') {
                    if (editDomain.getCommandStack().canRedo()) {
                        editDomain.getCommandStack().redo();
                    }
                    event.doit = false;
                }
            }
        });
    }

    private void deleteSelection() {
        List<?> selectedParts = viewer.getSelectedEditParts();

        if (selectedParts == null || selectedParts.isEmpty()) {
            return;
        }

        CompoundCommand compound = new CompoundCommand("Delete selected rules");

        for (Object selected : selectedParts) {
            if (selected instanceof EditPart) {
                EditPart editPart = (EditPart) selected;

                GroupRequest deleteRequest = new GroupRequest(RequestConstants.REQ_DELETE);
                Command command = editPart.getCommand(deleteRequest);

                if (command != null && command.canExecute()) {
                    compound.add(command);
                }
            }
        }

        if (!compound.isEmpty() && compound.canExecute()) {
            editDomain.getCommandStack().execute(compound);
        }
    }
    
    private Composite createPropertiesPanel(Composite parent) {
        Composite panel = new Composite(parent, SWT.BORDER);
        panel.setLayout(new GridLayout(2, false));

        selectedTitle = new org.eclipse.swt.widgets.Label(panel, SWT.NONE);
        selectedTitle.setText("No selection");
        selectedTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        createSeparator(panel);

        createLabel(panel, "Name:");
        nameText = createText(panel);

        createLabel(panel, "Type:");
        typeValueLabel = new org.eclipse.swt.widgets.Label(panel, SWT.NONE);
        typeValueLabel.setText("-");
        typeValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        createLabel(panel, "Condition:");
        conditionText = createText(panel);

        createLabel(panel, "Called rule:");
        calledRuleText = createText(panel);

        createLabel(panel, "Parameters:");
        parametersText = createText(panel);

        installPropertyModifyListeners();

        setRuleFieldsEnabled(false);

        return panel;
    }

    private void createLabel(Composite parent, String text) {
        org.eclipse.swt.widgets.Label label = new org.eclipse.swt.widgets.Label(parent, SWT.NONE);
        label.setText(text);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
    }

    private Text createText(Composite parent) {
        Text text = new Text(parent, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        return text;
    }

    private void createSeparator(Composite parent) {
        org.eclipse.swt.widgets.Label separator = new org.eclipse.swt.widgets.Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
        separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    }
    
    private void installPropertyModifyListeners() {
        nameText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent event) {
                if (!updatingPropertiesPanel && selectedRule != null) {
                    selectedRule.setName(nameText.getText());
                    selectedTitle.setText("Rule: " + selectedRule.getName());
                    propertiesPanel.layout(true, true);
                }
            }
        });

        conditionText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent event) {
                if (!updatingPropertiesPanel && selectedRule != null) {
                    selectedRule.setCondition(conditionText.getText());
                }
            }
        });

        calledRuleText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent event) {
                if (!updatingPropertiesPanel && selectedRule != null) {
                    selectedRule.setCalledRuleName(calledRuleText.getText());
                }
            }
        });

        parametersText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent event) {
                if (!updatingPropertiesPanel && selectedRule != null) {
                    selectedRule.setParameters(parametersText.getText());
                }
            }
        });
    }
    
    private void handleSelectionChanged(SelectionChangedEvent event) {
        ISelection selection = event.getSelection();

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;

            if (!structuredSelection.isEmpty()) {
                Object selected = structuredSelection.getFirstElement();

                if (selected instanceof RuleNodeEditPart) {
                    RuleNodeEditPart rulePart = (RuleNodeEditPart) selected;
                    showRuleProperties(rulePart.getRuleNode());
                } else if (selected instanceof StartNodeEditPart) {
                    showStartNodeProperties();
                } else if (selected instanceof TransitionEditPart) {
                    showTransitionProperties();
                } else {
                    showNoSelection();
                }
            } else {
                showNoSelection();
            }
        } else {
            showNoSelection();
        }
    }
    
    private void showRuleProperties(RuleNode rule) {
        selectedRule = rule;
        updatingPropertiesPanel = true;

        selectedTitle.setText("Rule: " + rule.getName());

        nameText.setText(rule.getName());
        typeValueLabel.setText(rule.getType().name());

        conditionText.setText(rule.getCondition());
        calledRuleText.setText(rule.getCalledRuleName());
        parametersText.setText(rule.getParameters());

        setRuleFieldsEnabled(true);
        updateSemanticFieldsForType(rule.getType());

        updatingPropertiesPanel = false;
        propertiesPanel.layout(true, true);
    }

    private void showStartNodeProperties() {
        selectedRule = null;
        updatingPropertiesPanel = true;

        selectedTitle.setText("Starting point");

        nameText.setText("Starting point");
        typeValueLabel.setText("INITIAL");
        conditionText.setText("");
        calledRuleText.setText("");
        parametersText.setText("");

        setRuleFieldsEnabled(false);

        updatingPropertiesPanel = false;
        propertiesPanel.layout(true, true);
    }

    private void showTransitionProperties() {
        selectedRule = null;
        updatingPropertiesPanel = true;

        selectedTitle.setText("Transition");

        nameText.setText("");
        typeValueLabel.setText("TRANSITION");
        conditionText.setText("");
        calledRuleText.setText("");
        parametersText.setText("");

        setRuleFieldsEnabled(false);

        updatingPropertiesPanel = false;
        propertiesPanel.layout(true, true);
    }

    private void showNoSelection() {
        selectedRule = null;
        updatingPropertiesPanel = true;

        selectedTitle.setText("No selection");

        nameText.setText("");
        typeValueLabel.setText("-");
        conditionText.setText("");
        calledRuleText.setText("");
        parametersText.setText("");

        setRuleFieldsEnabled(false);

        updatingPropertiesPanel = false;
        propertiesPanel.layout(true, true);
    }
    
    private void setRuleFieldsEnabled(boolean enabled) {
        nameText.setEnabled(enabled);

        conditionText.setEnabled(enabled);
        calledRuleText.setEnabled(enabled);
        parametersText.setEnabled(enabled);
    }

    private void updateSemanticFieldsForType(RuleType type) {
        boolean isIf = type == RuleType.CONDITIONAL;
        boolean isCall = type == RuleType.CALL;

        conditionText.setEnabled(isIf);

        calledRuleText.setEnabled(isCall);
        parametersText.setEnabled(isCall);
    }
}