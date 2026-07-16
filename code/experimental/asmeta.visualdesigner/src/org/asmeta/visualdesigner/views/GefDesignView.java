package org.asmeta.visualdesigner.views;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.asmeta.visualdesigner.editparts.AsmEditPartFactory;
import org.asmeta.visualdesigner.editparts.RuleNodeEditPart;
import org.asmeta.visualdesigner.editparts.StartNodeEditPart;
import org.asmeta.visualdesigner.editparts.TransitionEditPart;
import org.asmeta.visualdesigner.factories.RuleCreationFactory;
import org.asmeta.visualdesigner.factories.TransitionCreationFactory;
import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.DomainSignature;
import org.asmeta.visualdesigner.model.FunctionSignature;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.RuleType;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.layout.TableColumnLayout;

/*
 * Main view: here manage the panels and interactions to create the different rules and transitions.
 * Also to modify the information.
 */
public class GefDesignView extends ViewPart {

    public static final String ID = "org.asmeta.visualdesigner.views.GefDesignView";

    private static final String MAIN_DIAGRAM_NAME = "main";

    private DefaultEditDomain editDomain;
    private PaletteViewer paletteViewer;
    private SashForm rightSash;
    private Composite bottomPanel;
    private StackLayout bottomStack;

    private ScrolledComposite propertiesScroll;
    private Composite propertiesPanel;
    private Composite definitionsPanel;
    private Button definitionsButton;

    private Label selectedTitle;
    private Text nameText;
    private Label typeValueLabel;
    private Text conditionText;
    private Text calledRuleText;
    private Text parametersText;
    private Button openCalledRuleButton;

    private StyledText asmetaCodeText;

    private RuleNode selectedRule;
    private boolean updatingPropertiesPanel = false;

    private TableViewer domainViewer;
    private TableViewer functionViewer;

    private CTabFolder diagramTabs;

    private final Map<String, DiagramModel> diagramsByName = new LinkedHashMap<>();
    private final Map<String, DiagramPage> openPagesByName = new LinkedHashMap<>();

    private DiagramPage activePage;

    private static class DiagramPage {
        private final String name;
        private final DiagramModel model;
        private final ScrollingGraphicalViewer viewer;
        private final CTabItem tabItem;

        private DiagramPage(String name, DiagramModel model, ScrollingGraphicalViewer viewer, CTabItem tabItem) {
            this.name = name;
            this.model = model;
            this.viewer = viewer;
            this.tabItem = tabItem;
        }
    }

    @Override
    public void createPartControl(Composite parent) {
        SashForm sash = new SashForm(parent, SWT.HORIZONTAL);

        editDomain = new DefaultEditDomain(null);

        Composite palettePanel = new Composite(sash, SWT.NONE);
        palettePanel.setLayout(new GridLayout(1, false));

        paletteViewer = new PaletteViewer();
        paletteViewer.createControl(palettePanel);
        paletteViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        paletteViewer.setPaletteRoot(createPaletteRoot());

        definitionsButton = new Button(palettePanel, SWT.PUSH);
        definitionsButton.setText("Definitions");
        definitionsButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        definitionsButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                toggleDefinitionsPanel();
            }
        });

        rightSash = new SashForm(sash, SWT.VERTICAL);

        diagramTabs = new CTabFolder(rightSash, SWT.BORDER);
        diagramTabs.setSimple(false);

        bottomPanel = new Composite(rightSash, SWT.NONE);
        bottomStack = new StackLayout();
        bottomPanel.setLayout(bottomStack);

        createPropertiesPanel(bottomPanel);
        bottomStack.topControl = propertiesScroll;

        rightSash.setWeights(new int[] { 75, 25 });
        sash.setWeights(new int[] { 15, 85 });

        editDomain.setPaletteViewer(paletteViewer);

        diagramTabs.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                CTabItem selectedItem = diagramTabs.getSelection();

                if (selectedItem != null) {
                    activePage = (DiagramPage) selectedItem.getData();
                    selectedRule = null;
                    showNoSelection();
                }
            }
        });

        createDiagramTab(MAIN_DIAGRAM_NAME);
    }

    private void toggleDefinitionsPanel() {
        boolean definitionsVisible = definitionsPanel != null && !definitionsPanel.isDisposed()
                && bottomStack.topControl == definitionsPanel;
        if (definitionsVisible) {
            closeDefinitionsPanel();
        } else {
            openDefinitionsPanel();
        }
    }

    private void openDefinitionsPanel() {
    	if (definitionsPanel == null || definitionsPanel.isDisposed()) {
            definitionsPanel = createDefinitionsPanel(bottomPanel);
        }

        refreshDefinitionViewers();
        bottomStack.topControl = definitionsPanel;
        rightSash.setWeights(new int[] { 65, 35 });
        definitionsButton.setText("Hide definitions");
        bottomPanel.layout(true, true);
        rightSash.layout(true, true);
    }

    private void closeDefinitionsPanel() {
        if (propertiesScroll != null && !propertiesScroll.isDisposed()) {
            bottomStack.topControl = propertiesScroll;
            rightSash.setWeights(new int[] { 75, 25 });
            definitionsButton.setText("Definitions");
            refreshPropertiesPanelLayout();
            bottomPanel.layout(true, true);
            rightSash.layout(true, true);
        }
    }

    private void refreshDefinitionViewers() {
        if (domainViewer != null && !domainViewer.getTable().isDisposed()) {
            domainViewer.refresh();
        }

        if (functionViewer != null && !functionViewer.getTable().isDisposed()) {
            functionViewer.refresh();
        }
    }

    private Composite createDefinitionsPanel(Composite parent) {
        Composite panel = new Composite(parent, SWT.BORDER);
        panel.setLayout(new GridLayout(1, false));

        Composite header = new Composite(panel, SWT.NONE);
        header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        header.setLayout(new GridLayout(2, false));

        Label title = new Label(header, SWT.NONE);
        title.setText("Definitions - global model signature");
        title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Button closeButton = new Button(header, SWT.PUSH);
        closeButton.setText("Close");
        closeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                closeDefinitionsPanel();
            }
        });

        Composite signatures = createSignaturesPanel(panel);
        signatures.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        return panel;
    }

    private DiagramPage createDiagramTab(String diagramName) {
        DiagramModel diagramModel = diagramsByName.computeIfAbsent(diagramName, this::createInitialModel);

        Composite tabComposite = new Composite(diagramTabs, SWT.NONE);
        tabComposite.setLayout(new FillLayout());

        ScrollingGraphicalViewer pageViewer = new ScrollingGraphicalViewer();
        pageViewer.createControl(tabComposite);

        pageViewer.setRootEditPart(new ScalableFreeformRootEditPart());
        pageViewer.setEditPartFactory(new AsmEditPartFactory());
        pageViewer.setContents(diagramModel);
        pageViewer.addSelectionChangedListener(this::handleSelectionChanged);

        pageViewer.getControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent event) {
                handleDoubleClickOnViewer(pageViewer);
            }
        });

        installKeyboardShortcuts(pageViewer);
        editDomain.addViewer(pageViewer);

        int tabStyle = MAIN_DIAGRAM_NAME.equals(diagramName) ? SWT.NONE : SWT.CLOSE;

        CTabItem tabItem = new CTabItem(diagramTabs, tabStyle);
        tabItem.setText(diagramName);
        tabItem.setControl(tabComposite);

        DiagramPage page = new DiagramPage(diagramName, diagramModel, pageViewer, tabItem);

        tabItem.setData(page);
        tabItem.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent event) {
                openPagesByName.remove(diagramName);

                if (activePage == page) {
                    CTabItem selectedItem = diagramTabs.getSelection();

                    if (selectedItem != null) {
                        activePage = (DiagramPage) selectedItem.getData();
                    } else {
                        activePage = openPagesByName.get(MAIN_DIAGRAM_NAME);
                    }

                    showNoSelection();
                }
            }
        });

        openPagesByName.put(diagramName, page);

        diagramTabs.setSelection(tabItem);
        activePage = page;
        pageViewer.getControl().setFocus();

        return page;
    }

    private void openDiagramTab(String diagramName) {
        String normalizedName = normalizeDiagramName(diagramName);

        if (!normalizedName.isEmpty()) {
            DiagramPage existingPage = openPagesByName.get(normalizedName);

            if (existingPage != null && !existingPage.tabItem.isDisposed()) {
                diagramTabs.setSelection(existingPage.tabItem);
                activePage = existingPage;
                existingPage.viewer.getControl().setFocus();
                showNoSelection();
            } else {
                createDiagramTab(normalizedName);
            }
        }
    }

    private String normalizeDiagramName(String diagramName) {
        String normalizedName = "";

        if (diagramName != null) {
            normalizedName = diagramName.trim();
        }

        return normalizedName;
    }

    private void handleDoubleClickOnViewer(ScrollingGraphicalViewer sourceViewer) {
        RuleNode rule = getSingleSelectedRule(sourceViewer);

        if (!canOpenCalledRule(rule) && selectedRule != null) {
            rule = selectedRule;
        }

        if (canOpenCalledRule(rule)) {
            openDiagramTab(rule.getCalledRuleName());
        }
    }

    private RuleNode getSingleSelectedRule(ScrollingGraphicalViewer sourceViewer) {
        RuleNode rule = null;

        if (sourceViewer != null) {
            List<?> selectedEditParts = sourceViewer.getSelectedEditParts();

            if (selectedEditParts != null && selectedEditParts.size() == 1) {
                Object selected = selectedEditParts.get(0);

                if (selected instanceof EditPart) {
                    Object selectedModel = ((EditPart) selected).getModel();

                    if (selectedModel instanceof RuleNode) {
                        rule = (RuleNode) selectedModel;
                    }
                }
            }
        }

        return rule;
    }

    private ScrollingGraphicalViewer getCurrentViewer() {
        return activePage != null ? activePage.viewer : null;
    }

    private DiagramModel getCurrentModel() {
        return activePage != null ? activePage.model : null;
    }

    private DiagramModel getMainModel() {
        return diagramsByName.computeIfAbsent(MAIN_DIAGRAM_NAME, this::createInitialModel);
    }

    private void refreshCurrentViewer() {
        ScrollingGraphicalViewer currentViewer = getCurrentViewer();
        DiagramModel currentModel = getCurrentModel();

        if (currentViewer != null && currentModel != null) {
            currentViewer.setContents(currentModel);
        }
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

        DiagramModel mainModel = getMainModel();
        mainModel.setAsmetaCode(initialText);

        asmetaCodeText.addModifyListener(e -> {
            mainModel.setAsmetaCode(asmetaCodeText.getText());
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

    private DiagramModel createInitialModel(String diagramName) {
        DiagramModel diagram = new DiagramModel();

        if (MAIN_DIAGRAM_NAME.equals(diagramName)) {
            // EXAMPLE FOR TABLE DOMAIN AND FUNCTION. DELETE AFTER MEETING.
            diagram.getDomains().add(new DomainSignature("Coin", "enum", false, "HALF, ONE"));
            diagram.getDomains().add(new DomainSignature("NumberOfSlot", "subsetof Integer", false, "1:20"));
            diagram.getDomains().add(new DomainSignature("Student", "abstract", true, "s1, s2"));

            diagram.getFunctions().add(new FunctionSignature("x", "monitored", "", "Boolean", ""));
            diagram.getFunctions().add(new FunctionSignature(
                    "max",
                    "static",
                    "Prod(Integer, Integer)",
                    "Integer",
                    "max(x in Integer, y in Integer) = if x > y then x else y endif"
            ));
            diagram.getFunctions().add(new FunctionSignature(
                    "temperature",
                    "controlled",
                    "",
                    "Real",
                    "temperature = 10.4"
            ));
        }

        return diagram;
    }

    @Override
    public void setFocus() {
        ScrollingGraphicalViewer currentViewer = getCurrentViewer();

        if (currentViewer != null) {
            currentViewer.getControl().setFocus();
        }
    }

    private void installKeyboardShortcuts(ScrollingGraphicalViewer pageViewer) {
        pageViewer.getControl().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent event) {
                boolean deletePressed = event.keyCode == SWT.DEL || event.keyCode == SWT.BS;
                boolean modifierPressed = (event.stateMask & SWT.MOD1) != 0;
                char key = Character.toLowerCase(event.character);

                if (deletePressed) {
                    deleteSelection(pageViewer);
                    event.doit = false;
                } else if (modifierPressed && key == 'z') {
                    if (editDomain.getCommandStack().canUndo()) {
                        editDomain.getCommandStack().undo();
                    }

                    event.doit = false;
                } else if (modifierPressed && key == 'y') {
                    if (editDomain.getCommandStack().canRedo()) {
                        editDomain.getCommandStack().redo();
                    }

                    event.doit = false;
                }
            }
        });
    }

    private void deleteSelection(ScrollingGraphicalViewer sourceViewer) {
        if (sourceViewer != null) {
            List<?> selectedParts = sourceViewer.getSelectedEditParts();

            if (selectedParts != null && !selectedParts.isEmpty()) {
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
        }
    }

    private Composite createPropertiesPanel(Composite parent) {
        propertiesScroll = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        propertiesScroll.setExpandHorizontal(true);
        propertiesScroll.setExpandVertical(true);

        Composite panel = new Composite(propertiesScroll, SWT.NONE);
        panel.setLayout(new GridLayout(2, false));
        propertiesPanel = panel;
        propertiesScroll.setContent(panel);

        selectedTitle = new Label(panel, SWT.NONE);
        selectedTitle.setText("No selection");
        selectedTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        createSeparator(panel);

        createLabel(panel, "Name:");
        nameText = createText(panel);

        createLabel(panel, "Type:");
        typeValueLabel = new Label(panel, SWT.NONE);
        typeValueLabel.setText("-");
        typeValueLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        createLabel(panel, "Condition:");
        conditionText = createText(panel);

        createLabel(panel, "Called rule:");
        calledRuleText = createText(panel);

        createLabel(panel, "");
        openCalledRuleButton = new Button(panel, SWT.PUSH);
        openCalledRuleButton.setText("Open rule model");
        openCalledRuleButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        openCalledRuleButton.setEnabled(false);
        openCalledRuleButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                if (canOpenCalledRule(selectedRule)) {
                    openDiagramTab(selectedRule.getCalledRuleName());
                }
            }
        });

        createLabel(panel, "Parameters:");
        parametersText = createText(panel);

        installPropertyModifyListeners();

        setRuleFieldsEnabled(false);
        refreshPropertiesPanelLayout();

        return propertiesScroll;
    }

    private void refreshPropertiesPanelLayout() {
        boolean panelExists = propertiesPanel != null && !propertiesPanel.isDisposed();
        boolean scrollExists = propertiesScroll != null && !propertiesScroll.isDisposed();

        if (panelExists) {
            propertiesPanel.layout(true, true);
        }

        if (panelExists && scrollExists) {
            propertiesScroll.setMinSize(propertiesPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
            propertiesScroll.layout(true, true);
        }
    }

    private void createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(text);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
    }

    private Text createText(Composite parent) {
        Text text = new Text(parent, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        return text;
    }

    private void createSeparator(Composite parent) {
        Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
        separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    }

    private void installPropertyModifyListeners() {
        nameText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent event) {
                if (!updatingPropertiesPanel && selectedRule != null) {
                    selectedRule.setName(nameText.getText());
                    selectedTitle.setText("Rule: " + selectedRule.getName());
                    refreshPropertiesPanelLayout();
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
                    updateOpenCalledRuleButton();
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

        selectedTitle.setText("Rule: " + safeText(rule.getName()));

        nameText.setText(safeText(rule.getName()));

        RuleType type = rule.getType();
        typeValueLabel.setText(type != null ? type.name() : "-");

        conditionText.setText(safeText(rule.getCondition()));
        calledRuleText.setText(safeText(rule.getCalledRuleName()));
        parametersText.setText(safeText(rule.getParameters()));

        setRuleFieldsEnabled(true);
        updateSemanticFieldsForType(type);

        updatingPropertiesPanel = false;
        refreshPropertiesPanelLayout();
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
        refreshPropertiesPanelLayout();
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
        refreshPropertiesPanelLayout();
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
        refreshPropertiesPanelLayout();
    }

    private void setRuleFieldsEnabled(boolean enabled) {
        nameText.setEnabled(enabled);

        conditionText.setEnabled(enabled);
        calledRuleText.setEnabled(enabled);
        parametersText.setEnabled(enabled);

        updateOpenCalledRuleButton();
    }

    private void updateSemanticFieldsForType(RuleType type) {
        boolean isIf = type == RuleType.CONDITIONAL;
        boolean isCall = type == RuleType.CALL;

        conditionText.setEnabled(isIf);

        calledRuleText.setEnabled(isCall);
        parametersText.setEnabled(isCall);

        updateOpenCalledRuleButton();
    }

    private void updateOpenCalledRuleButton() {
        if (openCalledRuleButton != null && !openCalledRuleButton.isDisposed()) {
            openCalledRuleButton.setEnabled(canOpenCalledRule(selectedRule));
        }
    }

    private boolean canOpenCalledRule(RuleNode rule) {
        return rule != null
                && rule.getType() == RuleType.CALL
                && rule.getCalledRuleName() != null
                && !rule.getCalledRuleName().trim().isEmpty();
    }

    private String safeText(String value) {
        return value == null ? "" : value;
    }

    private static final String[] DOMAIN_TYPE_OPTIONS = {
            "abstract",
            "enum",
            "subsetof Integer",
            "subsetof Natural",
            "subsetof Real",
            "Prod(...)",
            "Powerset(...)"
    };

    private static final String[] YES_NO_OPTIONS = {
            "no",
            "yes"
    };

    private static final String[] FUNCTION_TYPE_OPTIONS = {
            "static",
            "monitored",
            "controlled",
            "derived",
            "out",
            "shared"
    };

    private Composite createSignaturesPanel(Composite parent) {
        Composite panel = new Composite(parent, SWT.BORDER);
        panel.setLayout(new FillLayout());

        CTabFolder folder = new CTabFolder(panel, SWT.BORDER);
        folder.setSimple(false);

        CTabItem domainsTab = new CTabItem(folder, SWT.NONE);
        domainsTab.setText("Domains");
        domainsTab.setControl(createDomainsTab(folder));

        CTabItem functionsTab = new CTabItem(folder, SWT.NONE);
        functionsTab.setText("Functions");
        functionsTab.setControl(createFunctionsTab(folder));

        folder.setSelection(domainsTab);

        return panel;
    }

    private Composite createDomainsTab(Composite parent) {
        DiagramModel signatureModel = getMainModel();

        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(1, false));

        Composite buttons = new Composite(container, SWT.NONE);
        buttons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        buttons.setLayout(new GridLayout(2, false));

        Button addButton = new Button(buttons, SWT.PUSH);
        addButton.setText("+ Domain");

        Button deleteButton = new Button(buttons, SWT.PUSH);
        deleteButton.setText("Delete");

        domainViewer = createTableViewer(container);

        this.<DomainSignature>addColumn(
                domainViewer,
                "Domain name",
                170,
                DomainSignature::getName,
                new TextEditingSupport<>(
                        domainViewer,
                        DomainSignature::getName,
                        (domain, value) -> {
                            domain.setName(value);

                            if (functionViewer != null) {
                                functionViewer.refresh();
                            }
                        }
                )
        );

        this.<DomainSignature>addColumn(
                domainViewer,
                "Type",
                160,
                DomainSignature::getType,
                new ComboEditingSupport<>(
                        domainViewer,
                        DomainSignature::getType,
                        DomainSignature::setType,
                        () -> DOMAIN_TYPE_OPTIONS
                )
        );

        this.<DomainSignature>addColumn(
                domainViewer,
                "Dynamic?",
                100,
                domain -> domain.isDynamic() ? "yes" : "no",
                new ComboEditingSupport<>(
                        domainViewer,
                        domain -> ((DomainSignature) domain).isDynamic() ? "yes" : "no",
                        (domain, value) -> ((DomainSignature) domain).setDynamic("yes".equals(value)),
                        () -> YES_NO_OPTIONS
                )
        );

        this.<DomainSignature>addColumn(
                domainViewer,
                "Values",
                220,
                DomainSignature::getValues,
                new TextEditingSupport<>(
                        domainViewer,
                        DomainSignature::getValues,
                        DomainSignature::setValues
                )
        );

        domainViewer.setInput(signatureModel.getDomains());

        addButton.addListener(SWT.Selection, event -> {
            DomainSignature domain = new DomainSignature(
                    nextDomainName(),
                    "abstract",
                    false,
                    ""
            );

            signatureModel.getDomains().add(domain);
            domainViewer.refresh();
        });

        deleteButton.addListener(SWT.Selection, event -> {
            IStructuredSelection selection = (IStructuredSelection) domainViewer.getSelection();

            for (Object selected : selection.toArray()) {
                signatureModel.getDomains().remove(selected);
            }

            domainViewer.refresh();

            if (functionViewer != null) {
                functionViewer.refresh();
            }
        });

        return container;
    }

    private Composite createFunctionsTab(Composite parent) {
        DiagramModel signatureModel = getMainModel();

        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(1, false));

        Composite buttons = new Composite(container, SWT.NONE);
        buttons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        buttons.setLayout(new GridLayout(2, false));

        Button addButton = new Button(buttons, SWT.PUSH);
        addButton.setText("+ Function");

        Button deleteButton = new Button(buttons, SWT.PUSH);
        deleteButton.setText("Delete");

        Composite tableContainer = new Composite(container, SWT.NONE);
        tableContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        TableColumnLayout functionTableLayout = new TableColumnLayout();
        tableContainer.setLayout(functionTableLayout);

        functionViewer = createTableViewer(tableContainer);

        TableViewerColumn functionNameColumn = this.<FunctionSignature>addColumn(
                functionViewer,
                "Function name",
                170,
                FunctionSignature::getName,
                new TextEditingSupport<>(
                        functionViewer,
                        FunctionSignature::getName,
                        FunctionSignature::setName
                )
        );

        TableViewerColumn functionTypeColumn = this.<FunctionSignature>addColumn(
                functionViewer,
                "Type",
                140,
                FunctionSignature::getType,
                new ComboEditingSupport<>(
                        functionViewer,
                        FunctionSignature::getType,
                        FunctionSignature::setType,
                        () -> FUNCTION_TYPE_OPTIONS
                )
        );

        TableViewerColumn functionDomainColumn = this.<FunctionSignature>addColumn(
                functionViewer,
                "Domain",
                190,
                FunctionSignature::getDomain,
                new TextEditingSupport<>(
                        functionViewer,
                        FunctionSignature::getDomain,
                        FunctionSignature::setDomain
                )
        );

        TableViewerColumn functionCodomainColumn = this.<FunctionSignature>addColumn(
                functionViewer,
                "Codomain",
                150,
                FunctionSignature::getCodomain,
                new ComboEditingSupport<>(
                        functionViewer,
                        FunctionSignature::getCodomain,
                        FunctionSignature::setCodomain,
                        this::getCodomainOptions
                )
        );

        TableViewerColumn functionDefinitionColumn = this.<FunctionSignature>addColumn(
                functionViewer,
                "Values / definition / initialization",
                600,
                FunctionSignature::getDefinition,
                new MultilineTextEditingSupport<>(
                        functionViewer,
                        FunctionSignature::getDefinition,
                        FunctionSignature::setDefinition,
                        "Function definition"
                )
        );

        functionTableLayout.setColumnData(functionNameColumn.getColumn(), new ColumnWeightData(16, 160, true));
        functionTableLayout.setColumnData(functionTypeColumn.getColumn(), new ColumnWeightData(12, 120, true));
        functionTableLayout.setColumnData(functionDomainColumn.getColumn(), new ColumnWeightData(18, 170, true));
        functionTableLayout.setColumnData(functionCodomainColumn.getColumn(), new ColumnWeightData(14, 140, true));
        functionTableLayout.setColumnData(functionDefinitionColumn.getColumn(), new ColumnWeightData(40, 450, true));

        functionViewer.setInput(signatureModel.getFunctions());

        addButton.addListener(SWT.Selection, event -> {
            FunctionSignature function = new FunctionSignature(
                    nextFunctionName(),
                    "controlled",
                    "",
                    "Boolean",
                    ""
            );

            signatureModel.getFunctions().add(function);
            functionViewer.refresh();
        });

        deleteButton.addListener(SWT.Selection, event -> {
            IStructuredSelection selection = (IStructuredSelection) functionViewer.getSelection();

            for (Object selected : selection.toArray()) {
                signatureModel.getFunctions().remove(selected);
            }

            functionViewer.refresh();
        });

        return container;
    }

    private TableViewer createTableViewer(Composite parent) {
        TableViewer viewer = new TableViewer(
                parent,
                SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL
        );

        Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        viewer.setContentProvider(ArrayContentProvider.getInstance());

        return viewer;
    }

    private <T> TableViewerColumn addColumn(
            TableViewer viewer,
            String title,
            int width,
            Function<T, String> textProvider,
            EditingSupport editingSupport
    ) {
        TableViewerColumn column = new TableViewerColumn(viewer, SWT.LEFT);
        column.getColumn().setText(title);
        column.getColumn().setWidth(width);
        column.getColumn().setResizable(true);
        column.getColumn().setMoveable(true);

        column.setLabelProvider(new ColumnLabelProvider() {
            @SuppressWarnings("unchecked")
            @Override
            public String getText(Object element) {
                String value = textProvider.apply((T) element);
                return value == null ? "" : value;
            }
        });

        column.setEditingSupport(editingSupport);

        return column;
    }

    private String[] getCodomainOptions() {
        DiagramModel signatureModel = getMainModel();
        List<String> options = new ArrayList<>();

        options.add("Boolean");
        options.add("Integer");
        options.add("Natural");
        options.add("Real");
        options.add("String");

        for (DomainSignature domain : signatureModel.getDomains()) {
            if (domain.getName() != null && !domain.getName().isBlank()) {
                options.add(domain.getName());
            }
        }

        return options.toArray(new String[0]);
    }

    private String nextDomainName() {
        DiagramModel signatureModel = getMainModel();
        return "Domain" + (signatureModel.getDomains().size() + 1);
    }

    private String nextFunctionName() {
        DiagramModel signatureModel = getMainModel();
        return "function" + (signatureModel.getFunctions().size() + 1);
    }

    private static class TextEditingSupport<T> extends EditingSupport {

        private final TableViewer viewer;
        private final Function<T, String> getter;
        private final BiConsumer<T, String> setter;

        public TextEditingSupport(
                TableViewer viewer,
                Function<T, String> getter,
                BiConsumer<T, String> setter
        ) {
            super(viewer);
            this.viewer = viewer;
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return new TextCellEditor(viewer.getTable());
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Object getValue(Object element) {
            String value = getter.apply((T) element);
            return value == null ? "" : value;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void setValue(Object element, Object value) {
            setter.accept((T) element, String.valueOf(value));
            viewer.update(element, null);
        }
    }


    private static class MultilineTextEditingSupport<T> extends EditingSupport {

        private final TableViewer viewer;
        private final Function<T, String> getter;
        private final BiConsumer<T, String> setter;
        private final String title;

        public MultilineTextEditingSupport(
                TableViewer viewer,
                Function<T, String> getter,
                BiConsumer<T, String> setter,
                String title
        ) {
            super(viewer);
            this.viewer = viewer;
            this.getter = getter;
            this.setter = setter;
            this.title = title;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            String currentValue = getSafeValue(element);

            return new DialogCellEditor(viewer.getTable()) {
                @Override
                protected Object openDialogBox(Control cellEditorWindow) {
                    return openMultilineEditor(cellEditorWindow.getShell(), currentValue);
                }
            };
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @Override
        protected Object getValue(Object element) {
            return getSafeValue(element);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void setValue(Object element, Object value) {
            setter.accept((T) element, String.valueOf(value));
            viewer.update(element, null);
        }

        @SuppressWarnings("unchecked")
        private String getSafeValue(Object element) {
            String value = getter.apply((T) element);
            return value == null ? "" : value;
        }

        private String openMultilineEditor(Shell parentShell, String currentValue) {
            Shell dialog = new Shell(parentShell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.RESIZE);
            dialog.setText(title);
            dialog.setLayout(new GridLayout(1, false));

            Label label = new Label(dialog, SWT.NONE);
            label.setText("Edit value / definition / initialization:");
            label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            StyledText text = new StyledText(dialog, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
            text.setText(currentValue);
            text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

            Composite buttons = new Composite(dialog, SWT.NONE);
            buttons.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
            buttons.setLayout(new GridLayout(2, false));

            final String[] editedValue = new String[] { currentValue };

            Button okButton = new Button(buttons, SWT.PUSH);
            okButton.setText("OK");
            okButton.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    editedValue[0] = text.getText();
                    dialog.close();
                }
            });

            Button cancelButton = new Button(buttons, SWT.PUSH);
            cancelButton.setText("Cancel");
            cancelButton.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    dialog.close();
                }
            });

            dialog.setDefaultButton(okButton);
            dialog.setSize(760, 420);
            dialog.open();

            while (!dialog.isDisposed()) {
                if (!parentShell.getDisplay().readAndDispatch()) {
                    parentShell.getDisplay().sleep();
                }
            }

            return editedValue[0];
        }
    }

    private static class ComboEditingSupport<T> extends EditingSupport {

        private final TableViewer viewer;
        private final Function<T, String> getter;
        private final BiConsumer<T, String> setter;
        private final Supplier<String[]> valuesSupplier;

        public ComboEditingSupport(
                TableViewer viewer,
                Function<T, String> getter,
                BiConsumer<T, String> setter,
                Supplier<String[]> valuesSupplier
        ) {
            super(viewer);
            this.viewer = viewer;
            this.getter = getter;
            this.setter = setter;
            this.valuesSupplier = valuesSupplier;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return new ComboBoxCellEditor(
                    viewer.getTable(),
                    valuesSupplier.get(),
                    SWT.READ_ONLY
            );
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Object getValue(Object element) {
            String currentValue = getter.apply((T) element);
            String[] values = valuesSupplier.get();
            int selectedIndex = 0;

            for (int i = 0; i < values.length; i++) {
                if (values[i].equals(currentValue)) {
                    selectedIndex = i;
                }
            }

            return selectedIndex;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void setValue(Object element, Object value) {
            int index = (Integer) value;
            String[] values = valuesSupplier.get();

            if (index >= 0 && index < values.length) {
                setter.accept((T) element, values[index]);
                viewer.update(element, null);
            }
        }
    }
}