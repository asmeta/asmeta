package org.asmeta.animator;

import java.io.File;
//Usare collections -> scrivere un wrapper ! gestione thread concorrenti
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.main.Simulator.SimulatorLogger;
import org.asmeta.simulator.value.Value;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.domains.StringDomain;
import asmeta.structure.Asm;

public class VisualizationSimulation implements VisualizationSimulationI {

	// text to be put in the interface
	private static final String TEXT_MOVE_MONITORED_FUNCTIONS_DOWN = "Move Monitored Functions DOWN";
	private static final String TEXT_EXPORT_TO_AVALLA = "export to Avalla";

	private static final Font PREFERRED_FONT = SWTResourceManager.getFont("Calibri", 12, SWT.NORMAL);

	private static final int MAX_NUMBER_RND_STEPS = 10000;

	// get the logger form the simulator
	private static final SimulatorLogger simulatorLogger = Simulator.logger;

	static final int rowHeight = 30;
	static final String CONTROLLED = "C";
	static final String MONITORED = "M";
	static final String RANDOM = "R";
	
	// allow undef when reading monitored variables
	public static boolean allowUndefValuesMonitored;

	Display display = Display.getDefault();
	final Shell shlAsmetaa = new Shell(display);

	protected Table table_functions_left_up, table_states_right_up, table_functions_left_down, table_states_right_down;
	// step Number when random simulation
	private Text textStepNumber;
	private Text textInvError;
	private AsmCollection asm;

	private Button btnRndStep;
	private Button btnInterStep;

	// create the colors here once for ever
	private Color updateColor = new Color(display, 204, 255, 204);
	private Color newFunctionColor = new Color(display, 204, 229, 255);
	private Color red = new Color(display, 255, 0, 0);

	private Image arrowUp;
	private Image arrowDown;
	private String lastMonitoredInteractiveValue;

	/**
	 * build the viewer from a path sort of a factory
	 */
	public static void startAnimator(File asmPath) throws Exception {
		// PARSE THE SPECIFICATION (ASM)
		// parse using the asmeta parser
		assert asmPath.exists();
		final AsmCollection model = ASMParser.setUpReadAsm(asmPath);
		// since the simulator introduces its own appender and also this action introduce its appender,
		// remove all the appenders already in the simulator
		// DEBUG
		// print logger of the simulator
		//System.err.println("simulator appenders: " + Collections.list(simulatorLogger.logger.getAllAppenders()));
		//System.err.println("asmeta appenders: " + Collections.list(Logger.getLogger("org.asmeta").getAllAppenders()));
		//System.err.println("root appenders: " + Collections.list(Logger.getRootLogger().getAllAppenders()));
		// work around
		// remove all the appenders for the simulator
		// hopefully they will be inserted later the next time the simulator is executed
		SimulatorLogger.logger.removeAllAppenders();

		// System.out.println(System.getProperty("user.dir"));
		VisualizationSimulation  sim = new VisualizationSimulation(model);
		sim.run();
	}

	/**
	 * Launch the application. Open the window for interactive simulation.
	 * 
	 * @param asm
	 * 
	 * @param args
	 */
	private VisualizationSimulation(AsmCollection asm) {
		this.asm = asm;
		// Display display = PlatformUI.getWorkbench().getDisplay();
		// String filePath = System.getProperty("user.dir");
		// read the images: in this way they must be inside the code
		// TODO put them in images directory
		arrowUp = new Image(display, getClass().getResourceAsStream("arrowUp.png"));
		arrowDown = new Image(display, getClass().getResourceAsStream("arrowDown.png"));

		shlAsmetaa.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shlAsmetaa.setSize(770, 668);
		shlAsmetaa.setText("AsmetaA");
		shlAsmetaa.setVisible(true);
		shlAsmetaa.setLayout(new GridLayout(1, false));
		createContents(display, shlAsmetaa);
		shlAsmetaa.open();
		shlAsmetaa.layout();
	}

	private void run() {
		while (!shlAsmetaa.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents for interactive simulation
	 * 
	 * @param display
	 * @param shlAsmetaa
	 */
	protected void createContents(Display display, Shell shlAsmetaa) {
		generateGraphicalElements(shlAsmetaa);
		runMixedSimulation();
	}

	// init the graphical parts and the listeners of the buttons.
	private void runMixedSimulation() {
		AsmTestGeneratorMixedSimulation tg = new AsmTestGeneratorMixedSimulation(asm, this);
		try {
			tg.initializeSimulation(this);
			TableColumn column1 = new TableColumn(table_states_right_up, SWT.NONE);
			column1.setText("State " + (table_states_right_up.getColumnCount() - 1));
			table_states_right_up.getColumn(table_states_right_up.getColumnCount() - 1).pack();
			TableColumn column1_down = new TableColumn(table_states_right_down, SWT.NONE);
			column1_down.setText("State " + (table_states_right_down.getColumnCount() - 1));
			table_states_right_down.getColumn(table_states_right_down.getColumnCount() - 1).pack();
			addResizeListener(table_states_right_up, table_states_right_down,
					table_states_right_down.getColumnCount() - 1);
			addResizeListener(table_states_right_down, table_states_right_up,
					table_states_right_down.getColumnCount() - 1);
		} catch (AsmModelNotFoundException | MainRuleNotFoundException e1) {
			e1.printStackTrace();
		}
		/* Execute one step when push the button "Do one step" */
		btnRndStep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int stepNumber;
				if (textStepNumber.getText().isEmpty()) {
					textStepNumber.setText("1");
					stepNumber = 1;
				} else {					
					stepNumber = Integer.parseInt(textStepNumber.getText());
				}
				tg.setRandom();
				// System.out.println("RANDOM SIMULATION");
				for (int i = 0; i < stepNumber; i++) {
					MyState state = tg.runSimulation(true);
					showFunctionsRandomSimulation(state);
				}
			}
		});
		btnInterStep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// disable this button and also random step
				btnInterStep.setEnabled(false);
				btnRndStep.setEnabled(false);
				// set interactive
				tg.setInteractive();
				// System.out.println("INTERACTIVE SIMULATION");
				MyState state = tg.runSimulation(false); // TODO: get initial state
				// if it is not closed meanwhile
				if (!shlAsmetaa.isDisposed()) {
					showFunctionsInteractiveSimulation(state);
					// enable again
					btnInterStep.setEnabled(true);
					btnRndStep.setEnabled(true);
				}
			}
		});

	}

	private void generateGraphicalElements(Shell shlAsmetaa) {
		// shlAsmetaa = new Shell(display);
		/*
		 * shlAsmetaa.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		 * shlAsmetaa.setSize(770, 668); shlAsmetaa.setText("AsmetaA");
		 * shlAsmetaa.setVisible(true); shlAsmetaa.setLayout(new GridLayout(1, false));
		 */

		// Split window in two sub-windows
		SashForm sashForm = new SashForm(shlAsmetaa, SWT.NONE);
		sashForm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Add composite to the left panel with buttons
		addElementsToLeftPanel(sashForm);

		// Add composite to the right panel with two tables, the left table contains the
		// name of functions,
		// the right table the states
		addElementsToRightPanel(sashForm);
		sashForm.setWeights(241, 500);
	}

	private void addElementsToLeftPanel(SashForm sashForm) {
		// Add panel to the left side
		Composite panel_monitored = new Composite(sashForm, SWT.BORDER);
		panel_monitored.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		panel_monitored.setLayout(new FillLayout(SWT.HORIZONTAL));

		// Add scrolled panel to the left side
		ScrolledComposite scrolledPane_Right = new ScrolledComposite(panel_monitored,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledPane_Right.setExpandHorizontal(true);
		scrolledPane_Right.setExpandVertical(true);

		// Add panel to the right side
		Composite composite = new Composite(scrolledPane_Right, SWT.NONE | SWT.V_SCROLL);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(1, false));
		new Label(composite, SWT.NONE);
		// add the button for interactive
		btnInterStep = new Button(composite, SWT.NONE);
		btnInterStep.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btnInterStep.setBackground(SWTResourceManager.getColor(0, 0, 255));
		btnInterStep.setFont(PREFERRED_FONT);
		// only text
		btnInterStep.setText("Do one interactive step");
		new Label(composite, SWT.NONE);
		// Show button to perform simulation steps
		btnRndStep = new Button(composite, SWT.NONE);
		btnRndStep.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btnRndStep.setBackground(SWTResourceManager.getColor(0, 0, 255));
		btnRndStep.setFont(PREFERRED_FONT);
		btnRndStep.setText("Do random step/s");
		Label lblInsertStepNumber = new Label(composite, SWT.NONE);
		lblInsertStepNumber.setFont(PREFERRED_FONT);
		lblInsertStepNumber.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblInsertStepNumber.setText("Insert random step number");
		// steps to be executed when push do random step/s
		textStepNumber = new Text(composite, SWT.BORDER);
		textStepNumber.setBackground(SWTResourceManager.getColor(245, 245, 245));
		textStepNumber.setFont(PREFERRED_FONT);
		textStepNumber.setTouchEnabled(true);
		textStepNumber.setText("1");
		GridData gd_textStepNumber = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_textStepNumber.widthHint = 150;
		textStepNumber.setLayoutData(gd_textStepNumber);
		// allow only numbers (or empty to allow the deletion - in case is like 1)
		textStepNumber.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				// build the text as it would be after the event
				String text = textStepNumber.getText();
				String newText = text.substring(0, e.start) + e.text + text.substring(e.end);
				// allow empty
				if (!newText.isEmpty()) {
					try {
						Integer x = Integer.valueOf(newText);
						// max number of steps ??
						if (x > MAX_NUMBER_RND_STEPS)
							e.doit = false;
					} catch (NumberFormatException ex) {
						e.doit = false;
					}
				}
			}

		});
		new Label(composite, SWT.NONE);
		// invariant checker
		Label lblInvariant = new Label(composite, SWT.WRAP);
		lblInvariant.setFont(PREFERRED_FONT);
		lblInvariant.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblInvariant.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
		lblInvariant.setText("Inviriant violation / exceptions");
		textInvError = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textInvError.setFont(PREFERRED_FONT);
		textInvError.setEditable(false);
		GridData gd_textInvariant = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textInvariant.heightHint = 66;
		gd_textInvariant.widthHint = 178;
		textInvError.setLayoutData(gd_textInvariant);
		textInvError.setForeground(red);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		// Show button to hide/show functions
		Composite compositeMove = new Composite(composite, SWT.BORDER);
		compositeMove.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeMove.setLayout(new GridLayout(1, false));
		Button btnMoveControlledUp = new Button(compositeMove, SWT.NONE);
		GridData gd_btnMoveControlledUp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnMoveControlledUp.heightHint = 33;
		gd_btnMoveControlledUp.widthHint = 195;
		btnMoveControlledUp.setLayoutData(gd_btnMoveControlledUp);
		btnMoveControlledUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		btnMoveControlledUp.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		btnMoveControlledUp.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnMoveControlledUp.setText("Move Controlled Functions UP");
		Button btnMoveControlledDown = new Button(compositeMove, SWT.NONE);
		GridData gd_btnMoveControlledDown = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnMoveControlledDown.widthHint = 195;
		gd_btnMoveControlledDown.heightHint = 33;
		btnMoveControlledDown.setLayoutData(gd_btnMoveControlledDown);
		btnMoveControlledDown.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		btnMoveControlledDown.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		btnMoveControlledDown.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnMoveControlledDown.setText("Move Controlled Functions DOWN");
		Button btnMoveMonitoredUp = new Button(compositeMove, SWT.CENTER);
		GridData gd_btnMoveMonitoredUp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnMoveMonitoredUp.heightHint = 33;
		gd_btnMoveMonitoredUp.widthHint = 195;
		btnMoveMonitoredUp.setLayoutData(gd_btnMoveMonitoredUp);
		btnMoveMonitoredUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		btnMoveMonitoredUp.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		btnMoveMonitoredUp.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnMoveMonitoredUp.setText("Move Monitored Functions UP");
		Button btnMoveMonitoredDown = new Button(compositeMove, SWT.NONE);
		GridData gd_btnMoveMonitoredDown = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnMoveMonitoredDown.widthHint = 195;
		gd_btnMoveMonitoredDown.heightHint = 33;
		btnMoveMonitoredDown.setLayoutData(gd_btnMoveMonitoredDown);
		btnMoveMonitoredDown.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		btnMoveMonitoredDown.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		btnMoveMonitoredDown.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnMoveMonitoredDown.setText(TEXT_MOVE_MONITORED_FUNCTIONS_DOWN);

		// add listener to show functions
		btnMoveMonitoredDown.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				moveMultipleFunctions(table_functions_left_up, table_states_right_up, table_functions_left_down,
						table_states_right_down, MONITORED, arrowUp, false);
			}

		});
		btnMoveControlledUp.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				moveMultipleFunctions(table_functions_left_down, table_states_right_down, table_functions_left_up,
						table_states_right_up, CONTROLLED, arrowDown, true);
			}
		});
		btnMoveControlledDown.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				moveMultipleFunctions(table_functions_left_up, table_states_right_up, table_functions_left_down,
						table_states_right_down, CONTROLLED, arrowUp, false);
			}
		});
		btnMoveMonitoredUp.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				moveMultipleFunctions(table_functions_left_down, table_states_right_down, table_functions_left_up,
						table_states_right_up, MONITORED, arrowDown, true);
			}
		});

		// export button to avalla
		Button exportAvalla = new Button(composite, SWT.NONE);
		exportAvalla.setText(TEXT_EXPORT_TO_AVALLA);
		exportAvalla.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					exportToAvalla();
					break;
				}
			}
		});

		scrolledPane_Right.setContent(composite);

	}

	/** export the table content to avalla */
	protected void exportToAvalla() {
		simulatorLogger.info("//// starting scenario");
		simulatorLogger.info("scenario " + "SCENARIO_NAME");
		simulatorLogger.info("load " + asm.getMain().getName() + ASMParser.ASM_EXTENSION);
		// TODO create new file/document
		// DOWN
		TableItem[] states_down = table_states_right_down.getItems();
		TableItem[] functions_down = table_functions_left_down.getItems();
		// UP
		TableItem[] states_up = table_states_right_up.getItems();
		TableItem[] functions_up = table_functions_left_up.getItems();
		// get the states in items
		for (int column = 0; column < table_states_right_down.getColumnCount(); column++) {
			// all the controlled and then monitored
			String[] functionTypes = { CONTROLLED, MONITORED };
			for (String functionT : functionTypes) {
				addStateToAvalla(states_down, functions_down, column, functionT);
				addStateToAvalla(states_up, functions_up, column, functionT);
			}
			// new step
			simulatorLogger.info("step");
		}
	}

	/**
	 * @param states_down
	 * @param functions_down
	 * @param column
	 * @param functionT
	 */
	private void addStateToAvalla(TableItem[] states_down, TableItem[] functions_down, int column, String functionT) {
		for (int i = 0; i < states_down.length; i++) {
			// get the value of i-th state
			String text = states_down[i].getText(column);
			if (text.length() > 0) {
				// get function name
				TableItem left = functions_down[i];
				String functionName = left.getText(2);
				// function type (C for controlled and so on)
				String functionType = left.getText(1);
				if (!functionType.equals(functionT))
					continue;
				// if the type is a string add the quotes
				// get the functions of this ASM
				Collection<Function> functions = new ArrayList<>();
				for (Asm a : this.asm) {
					functions.addAll(a.getHeaderSection().getSignature().getFunction());
				}
				// add the quotes AG 04-2022
				Function function = org.asmeta.parser.Utility.search_funcName(functions, functionName);
				if (function != null && function.getCodomain() instanceof StringDomain) {
					text = "\"" + text + "\"";
				}
				// print
				if (functionType.equals(VisualizationSimulation.MONITORED))
					simulatorLogger.info("set " + functionName + " := " + text + ";");
				else
					simulatorLogger.info("check " + functionName + " = " + text + ";");
			}
		}
	}

	void moveMultipleFunctions(Table table_functions_left_origin, Table table_states_right_origin,
			Table table_functions_left_dest, Table table_states_right_dest, String type, Image arrow, boolean check) {
		int count = 0;
		TableItem[] itemsL = table_functions_left_origin.getItems();
		for (int i = 0; i < itemsL.length; i++) {
			if (itemsL[i].getText(1).compareTo(type) == 0) {
				moveOneFunction(i - count, table_functions_left_origin, table_states_right_origin,
						table_functions_left_dest, table_states_right_dest, arrow, check);
				count++;
			}
		}
	}

	private void addElementsToRightPanel(SashForm sashForm) {
		// Two tables: - up: selected functions
		// - down: other functions
		SashForm sash_tables = new SashForm(sashForm, SWT.VERTICAL);
		sash_tables.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sash_tables.setLayoutData(new GridData(GridData.FILL_BOTH));

		SashForm sash_tables_up = new SashForm(sash_tables, SWT.BORDER | SWT.SMOOTH);
		sash_tables_up.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		table_functions_left_up = new Table(sash_tables_up, SWT.CHECK | SWT.MULTI | SWT.FULL_SELECTION);
		table_states_right_up = new Table(sash_tables_up, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		SashForm sash_tables_down = new SashForm(sash_tables, SWT.BORDER);
		sash_tables_down.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sash_tables_down.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		table_functions_left_down = new Table(sash_tables_down, SWT.CHECK | SWT.MULTI | SWT.FULL_SELECTION);
		table_states_right_down = new Table(sash_tables_down,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		table_functions_left_up.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event e) {
				sash_tables_down.setWeights(sash_tables_up.getWeights());
			}
		});

		table_functions_left_down.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event e) {
				sash_tables_up.setWeights(sash_tables_down.getWeights());
			}
		});

		// Draw the tables
		drawTable(sash_tables_up, table_functions_left_up, table_states_right_up);
		drawTable(sash_tables_down, table_functions_left_down, table_states_right_down);

		// Scroll two tables together
		scrollTablesTogetherVertically(table_functions_left_up, table_states_right_up);
		scrollTablesTogetherVertically(table_functions_left_down, table_states_right_down);
		scrollTablesTogetherHorizontally(table_states_right_up, table_states_right_down);

		// Select the same line in both tables
		selectLinesTables(table_functions_left_up, table_states_right_up);
		selectLinesTables(table_functions_left_down, table_states_right_down);

		// Add listener to move up/down the functions
		listenerMoveUpDown(table_functions_left_up, table_states_right_up, table_functions_left_down,
				table_states_right_down, arrowUp, false);
		listenerMoveUpDown(table_functions_left_down, table_states_right_down, table_functions_left_up,
				table_states_right_up, arrowDown, true);

		// Resize horizontal scrollbar
		resizeTablesHorizontalBar(sash_tables_up, table_functions_left_up, table_states_right_up);
		resizeTablesHorizontalBar(sash_tables_down, table_functions_left_down, table_states_right_down);

		// Add columns to the left table
		defineLeftTable(table_functions_left_up);
		defineLeftTable(table_functions_left_down);

		// Resize column width
		addResizeListenerLeftTable(table_functions_left_up, table_functions_left_down);
		addResizeListenerLeftTable(table_functions_left_down, table_functions_left_up);

		// Add listener to alphabetically ordered the functions
		sortElements(table_functions_left_up, table_states_right_up);
		sash_tables_up.setWeights(133, 357, 0);
		sortElements(table_functions_left_down, table_states_right_down);
		sash_tables_down.setWeights(133, 357, 0);
	}

	private void listenerMoveUpDown(Table table_functions_left_origin, Table table_states_right_origin,
			Table table_functions_left_dest, Table table_states_right_dest, final Image arrow, final boolean check) {
		table_functions_left_origin.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (arg0.detail == SWT.CHECK) {
					TableItem[] itemsL = table_functions_left_origin.getItems();
					for (int i = 0; i < itemsL.length; i++) {
						if (check && itemsL[i].getChecked())
							moveOneFunction(i, table_functions_left_origin, table_states_right_origin,
									table_functions_left_dest, table_states_right_dest, arrow, check);
						if (!check && !itemsL[i].getChecked())
							moveOneFunction(i, table_functions_left_origin, table_states_right_origin,
									table_functions_left_dest, table_states_right_dest, arrow, check);
					}
				}
			}
		});
	}

	private void moveOneFunction(int selectionIndices, Table table_functions_left_origin,
			Table table_states_right_origin, Table table_functions_left_dest, Table table_states_right_dest,
			Image arrow, boolean check) {
		showTableElements(selectionIndices, table_functions_left_origin, table_states_right_origin,
				table_functions_left_dest, table_states_right_dest, arrow, check);
		table_functions_left_origin.remove(selectionIndices);
		table_states_right_origin.remove(selectionIndices);
	}

	private void showTableElements(int selectionIndices, Table table_functions_left_origin,
			Table table_states_right_origin, Table table_functions_left_dest, Table table_states_right_dest,
			Image arrow, boolean check) {
		TableItem itemToMove_left = table_functions_left_origin.getItems()[selectionIndices];
		TableItem itemToMove_right = table_states_right_origin.getItems()[selectionIndices];
		TableItem itemLeft = new TableItem(table_functions_left_dest, SWT.NONE);
		itemLeft.setChecked(check);
		itemLeft.setImage(0, arrow);
		itemLeft.setText(1, itemToMove_left.getText(1));
		itemLeft.setText(2, itemToMove_left.getText(2));
		TableItem itemRight = new TableItem(table_states_right_dest, SWT.NONE);
		for (int j = 0; j < table_states_right_origin.getColumnCount(); j++) {
			itemRight.setText(j, itemToMove_right.getText(j));
			itemRight.setBackground(j, itemToMove_right.getBackground(j));
		}
	}

	private void resizeTablesHorizontalBar(SashForm sash_tables_up, Table table_functions_left,
			Table table_states_right) {
		ScrollBar hBarRightC = table_states_right.getHorizontalBar();
		Label spacer = new Label(sash_tables_up, SWT.NONE);
		GridData spacerData = new GridData();
		spacerData.heightHint = hBarRightC.getSize().y;
		spacer.setVisible(false);
		sash_tables_up.setBackground(table_functions_left.getBackground());
	}

	public Shell getShell() {
		return shlAsmetaa;
	}

	/* sort table elements when push on the column name */
	void sortElements(Table table_functions_left, Table table_states_right) {
		Listener sortListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				TableItem[] items = table_functions_left.getItems();
				TableItem[] itemsR = table_states_right.getItems();
				Collator collator = Collator.getInstance(Locale.getDefault());
				TableColumn column = (TableColumn) e.widget;
				int index = column == table_functions_left.getColumn(1) ? 1 : 2;
				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (collator.compare(value1, value2) < 0) {
							boolean isChecked = items[i].getChecked();
							Image currentArrow = items[i].getImage(0);
							String[] values = { items[i].getText(1), items[i].getText(2) };
							items[i].dispose();
							TableItem item = new TableItem(table_functions_left, SWT.NONE, j);
							item.setChecked(isChecked);
							item.setImage(0, currentArrow);
							item.setText(1, values[0]);
							item.setText(2, values[1]);
							items = table_functions_left.getItems();
							String[] valuesR = new String[table_states_right.getColumnCount()];
							Color[] background = new Color[table_states_right.getColumnCount()];
							for (int k = 0; k < table_states_right.getColumnCount(); k++) {
								valuesR[k] = itemsR[i].getText(k);
								background[k] = itemsR[i].getBackground(k);
							}
							itemsR[i].dispose();
							TableItem itemR = new TableItem(table_states_right, SWT.NONE, j);
							itemR.setText(valuesR);
							for (int k = 0; k < table_states_right.getColumnCount(); k++) {
								table_states_right.getItem(j).setBackground(k, background[k]);
							}
							itemsR = table_states_right.getItems();
							break;
						}
					}
				}
				table_functions_left.setSortColumn(column);
			}
		};
		table_functions_left.getColumn(1).addListener(SWT.Selection, sortListener);
		table_functions_left.getColumn(2).addListener(SWT.Selection, sortListener);
		table_functions_left.setSortColumn(table_functions_left.getColumn(1));
		table_functions_left.setSortDirection(SWT.UP);
		table_functions_left.setSortColumn(table_functions_left.getColumn(2));
		table_functions_left.setSortDirection(SWT.UP);
	}

	void selectLinesTables(Table table_functions_left, Table table_states_right) {
		// Select the same line in both tables
		table_functions_left.addListener(SWT.Selection,
				event -> table_states_right.setSelection(table_functions_left.getSelectionIndices()));
		table_states_right.addListener(SWT.Selection,
				event -> table_functions_left.setSelection(table_states_right.getSelectionIndices()));
	}

	void scrollTablesTogetherVertically(Table table_functions_left, Table table_states_right) {
		// When scroll vertically the left table, scroll also the right table
		ScrollBar vBarLeft = table_functions_left.getVerticalBar();
		vBarLeft.addListener(SWT.Selection,
				event -> table_states_right.setTopIndex(table_functions_left.getTopIndex()));
		table_functions_left.getVerticalBar().setVisible(false);
		// When scroll vertically the right table, scroll also the left table
		ScrollBar vBarRight = table_states_right.getVerticalBar();
		vBarRight.addListener(SWT.Selection,
				event -> table_functions_left.setTopIndex(table_states_right.getTopIndex()));
	}

	void scrollTablesTogetherHorizontally(Table table_states_right_up, Table table_states_right_down) {
		// When scroll vertically the left table, scroll also the right table
		ScrollBar hBarUp = table_states_right_up.getHorizontalBar();
		hBarUp.addListener(SWT.Selection,
				event -> table_states_right_down.setTopIndex(table_states_right_up.getTopIndex()));
		// When scroll vertically the right table, scroll also the left table
		ScrollBar hBarDown = table_states_right_down.getVerticalBar();
		hBarDown.addListener(SWT.Selection,
				event -> table_states_right_up.setTopIndex(table_states_right_down.getTopIndex()));
	}

	/* draw tables left and rigth */
	void drawTable(Composite composite, Table table_functions_left, Table table_states_right) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = layout.marginHeight = layout.horizontalSpacing = 0;
		composite.setLayout(layout);
		table_functions_left.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true));
		table_functions_left.setTouchEnabled(true);
		table_functions_left.setHeaderVisible(true);
		table_functions_left.setLinesVisible(true);
		table_states_right.setHeaderVisible(true);
		GridData table2Data = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		table_states_right.setLayoutData(table2Data);
		table_states_right.setLinesVisible(true);
		setRowSize(table_functions_left);
		setRowSize(table_states_right);
	}

	private void setRowSize(Table table_functions_left) {
		table_functions_left.addListener(SWT.MeasureItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// height cannot be per row so simply set
				event.height = rowHeight;
			}
		});
	}

	/* add columns to the left table */
	void defineLeftTable(Table table_functions_left) {
		// arrows to move fields
		TableColumn columnMove = new TableColumn(table_functions_left, SWT.NONE);
		columnMove.setText("");
		columnMove.setWidth(20);
		TableColumn column1 = new TableColumn(table_functions_left, SWT.NONE);
		column1.setText("Type");
		column1.setWidth(20);
		// function names
		TableColumn column2 = new TableColumn(table_functions_left, SWT.NONE);
		column2.setText("Functions");
		column1.setWidth(100);
		table_functions_left.getColumn(2).pack();
	}

	void addResizeListenerLeftTable(Table table_moved, Table table_follower) {
		for (int i = 0; i < 3; i++) {
			addResizeListener(table_moved, table_follower, i);
		}
	}

	// Add listener: when resize table_moved resize table_follower
	void addResizeListener(Table table_moved, Table table_follower, int i) {
		final TableColumn column_up = table_moved.getColumn(i);
		column_up.addControlListener(new ControlListener() { // Listener dimensione colonna
			@Override
			public void controlResized(ControlEvent arg0) {
				table_follower.getColumn(i).setWidth(column_up.getWidth());
			}

			@Override
			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	// Show value of function when interactive simulation is performed
	void showFunctionsRandomSimulation(MyState state) {
		TableColumn column1_up = new TableColumn(table_states_right_up, SWT.NONE);
		column1_up.setText("State " + (table_states_right_up.getColumnCount() - 1));
		TableColumn column1_down = new TableColumn(table_states_right_down, SWT.NONE);
		column1_down.setText("State " + (table_states_right_down.getColumnCount() - 1));
		addResizeListener(table_states_right_up, table_states_right_down, table_states_right_down.getColumnCount() - 1);
		addResizeListener(table_states_right_down, table_states_right_up, table_states_right_down.getColumnCount() - 1);
		showControlled(state.getControlledValues());
		showMonitored(state.getMonitoredValues());
		table_states_right_up.getColumn(table_states_right_up.getColumnCount() - 1).pack();
		table_states_right_down.getColumn(table_states_right_down.getColumnCount() - 1).pack();
		table_states_right_down
				.showColumn(table_states_right_down.getColumn(table_states_right_down.getColumnCount() - 1));
		table_states_right_up.showColumn(table_states_right_up.getColumn(table_states_right_up.getColumnCount() - 1));
		// for (int i = 0, n = table_functions_left.getColumnCount(); i < n; i++)
		/*
		 * table_functions_left_up.getColumn(1).pack();
		 * table_functions_left_down.getColumn(1).pack();
		 */
		updateTables();
	}

	// Show value of function when interactive simulation is performed
	private void showFunctionsInteractiveSimulation(MyState state) {
		TableColumn column1_up = new TableColumn(table_states_right_up, SWT.NONE);
		column1_up.setText("State " + (table_states_right_up.getColumnCount() - 1));
		TableColumn column1_down = new TableColumn(table_states_right_down, SWT.NONE);
		column1_down.setText("State " + (table_states_right_down.getColumnCount() - 1));
		addResizeListener(table_states_right_up, table_states_right_down, table_states_right_down.getColumnCount() - 1);
		addResizeListener(table_states_right_down, table_states_right_up, table_states_right_down.getColumnCount() - 1);
		showControlled(state.getControlledValues());
		showMonitored(state.getMonitoredValues());
		addMissingValueMonitoredFunctions(table_states_right_up);
		addMissingValueMonitoredFunctions(table_states_right_down);
		table_states_right_up.getColumn(table_states_right_up.getColumnCount() - 1).pack();
		table_states_right_down.getColumn(table_states_right_down.getColumnCount() - 1).pack();
		table_states_right_down
				.showColumn(table_states_right_down.getColumn(table_states_right_down.getColumnCount() - 1));
		table_states_right_up.showColumn(table_states_right_up.getColumn(table_states_right_up.getColumnCount() - 1));
		updateTables();
	}

	// Show controlled functions
	private void showControlled(Map<Location, Value> location) {
		Set<Location> listKey = location.keySet();
		for (Iterator<Location> it = listKey.iterator(); it.hasNext();) {
			Location key = it.next();
			int existKey_up = searchKeyInTable(key.toString(), table_functions_left_up);
			int existKey_down = searchKeyInTable(key.toString(), table_functions_left_down);
			if (existKey_up == -1 && existKey_down == -1)
				addElementsToTables(table_functions_left_down, table_states_right_down, location.get(key).toString(),
						key.toString(), existKey_down, CONTROLLED, 0);
			else if (existKey_down != -1)
				addElementsToTables(table_functions_left_down, table_states_right_down, location.get(key).toString(),
						key.toString(), existKey_down, CONTROLLED, 0);
			else
				addElementsToTables(table_functions_left_up, table_states_right_up, location.get(key).toString(),
						key.toString(), existKey_up, CONTROLLED, 0);
		}
	}

	private void addElementsToTables(Table table_functions_left, Table table_states_right, String locationValue,
			String locationName, int existKey, String type, int movePositionMonitored) {
		// current column
		int columnCount = table_states_right.getColumnCount();
		// Se monitorate inserisci nello stato n, le controllate nello stato n+1
		// perche' sono i valori dello stato successivo
		int currentColumn = columnCount - 1 - movePositionMonitored;
		int previousColumn = columnCount - 2 - movePositionMonitored;
		if (existKey == -1) {
			// When new function is detected add a row in the right table
			instantiateRowTableRight(table_states_right);
			TableItem itemL = new TableItem(table_functions_left, SWT.NONE);
			// addHideFunctionsButton(table_functions_left, itemL);
			// add image
			itemL.setImage(0, arrowUp);
			table_functions_left.getColumn(0).pack();
			itemL.setText(1, type);
			table_functions_left.getColumn(1).pack();
			// location name
			itemL.setText(2, locationName);
			table_functions_left.getColumn(2).pack();
			TableItem[] itemsL = table_functions_left.getItems();
			TableItem itemR = table_states_right.getItem(itemsL.length - 1);
			itemR.setText(currentColumn, locationValue);
			itemR.setBackground(currentColumn, newFunctionColor);
			// regardless of the color of the theme set the normal text to black 
			itemR.setForeground(currentColumn,display.getSystemColor(SWT.COLOR_BLACK));
		} else {
			TableItem itemR = table_states_right.getItem(existKey);
			itemR.setText(currentColumn, locationValue);
			// no need to set the color, it will take the correct one
			// itemR.setForeground(display.getSystemColor(SWT.FOREGROUND));
			if (itemR.getText(previousColumn).compareTo(locationValue) != 0) {
				itemR.setBackground(currentColumn, updateColor);
				// regardless of the color of the theme change also the text
				itemR.setForeground(currentColumn,display.getSystemColor(SWT.COLOR_BLACK));
			} 
		}
		// try to pack if its shows the results 
		//table_states_right.getColumn(currentColumn).pack();
		// this for the first column must be checked
		//table_states_right.getColumn(previousColumn).pack();
	}

	// Show monitored functions random simulation
	void showMonitored(Map<Location, Value> location) {
		Set<Location> listKey = location.keySet();
		for (Iterator<Location> it = listKey.iterator(); it.hasNext();) {
			Location key = it.next();
			int existKey_up = searchKeyInTable(key.toString(), table_functions_left_up);
			int existKey_down = searchKeyInTable(key.toString(), table_functions_left_down);
			if (existKey_up == -1 && existKey_down == -1)
				addElementsToTables(table_functions_left_down, table_states_right_down, location.get(key).toString(),
						key.toString(), existKey_down, MONITORED, 1);
			else if (existKey_down != -1)
				addElementsToTables(table_functions_left_down, table_states_right_down, location.get(key).toString(),
						key.toString(), existKey_down, MONITORED, 1);
			else
				addElementsToTables(table_functions_left_up, table_states_right_up, location.get(key).toString(),
						key.toString(), existKey_up, MONITORED, 1);
		}
		addMissingValueMonitoredFunctions(table_states_right_up);
		addMissingValueMonitoredFunctions(table_states_right_down);
	}

	public void showMonitoredInteractiveSimulation(String input, String location) {
		lastMonitoredInteractiveValue = input;
		int existKey_up = searchKeyInTable(location, table_functions_left_up);
		int existKey_down = searchKeyInTable(location, table_functions_left_down);
		if (existKey_up == -1 && existKey_down == -1)
			addElementsToTables(table_functions_left_down, table_states_right_down, input, location, existKey_down,	MONITORED, 0);
		else if (existKey_down != -1)
			addElementsToTables(table_functions_left_down, table_states_right_down, input, location, existKey_down,	MONITORED, 0);
		else
			addElementsToTables(table_functions_left_up, table_states_right_up, input, location, existKey_up, MONITORED,0);
	}

	public String getLastMonitoredInteractiveValue() {
		return lastMonitoredInteractiveValue;
	}

	void instantiateRowTableRight(Table table) {
		// Attention!!! Before insert elements into the state (right) table the rows
		// must be instantiate
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText("");
	}

	/*
	 * if monitored function is not setted in the current state, it is empty. The
	 * method rewrite the content of previous column in the current column
	 */
	private void addMissingValueMonitoredFunctions(Table table_states_right) {
		if (table_states_right.getColumnCount() >= 2) {
			int j = 1;
			TableItem[] items = table_states_right.getItems();
			for (int i = 0; i < items.length; i++) {
				if (items[i].getText(table_states_right.getColumnCount() - 1 - j).compareToIgnoreCase("") == 0)
					if (items[i].getText(table_states_right.getColumnCount() - 2 - j).compareToIgnoreCase("") != 0) {
						items[i].setText(table_states_right.getColumnCount() - 1 - j,
								items[i].getText(table_states_right.getColumnCount() - 2 - j));
						table_states_right.getColumn(table_states_right.getColumnCount() - 1 - j).pack();
					}
			}
		}
	}

	// Search if function is already in the table. If yes add value to the
	// corresponding row, otherwise add new row.
	int searchKeyInTable(String string, Table table_functions_left) {
		TableItem[] items = table_functions_left.getItems();
		if (items.length == 0) {
			// System.out.println("NEW FUNCTIO " + string);
			return -1;
		} else
			for (int i = 0; i < items.length; i++) {
				if (items[i].getText(2).compareToIgnoreCase(string) == 0)
					return i;
			}
		return -1;
	}

	@Override
	public void setInvalidIvariantText(String s) {
		if (!textInvError.isDisposed())
			textInvError.setText(s == null ? "null" : s);
	}

	@Override
	public void setInitValues(Map<Location, Value> locationsPrevSet2) {
		Iterator<Location> me = locationsPrevSet2.keySet().iterator();
		while (me.hasNext()) {
			Location key = me.next();
			int existKey_up = searchKeyInTable(key.toString(), table_functions_left_up);
			int existKey_down = searchKeyInTable(key.toString(), table_functions_left_down);
			if (existKey_up == -1 && existKey_down == -1)
				addElementsToTables(table_functions_left_down, table_states_right_down,
						locationsPrevSet2.get(key).toString(), key.toString(), existKey_down, CONTROLLED, 0);
			else if (existKey_down != -1)
				addElementsToTables(table_functions_left_down, table_states_right_down,
						locationsPrevSet2.get(key).toString(), key.toString(), existKey_down, CONTROLLED, 0);
			else
				addElementsToTables(table_functions_left_up, table_states_right_up,
						locationsPrevSet2.get(key).toString(), key.toString(), existKey_up, CONTROLLED, 0);
		}
		// System.out.println("INIT" + locationsPrevSet2.toString());
		// does not work ! - I wanted to fix the problem if the values are not 
		updateTables();
	}
	
	// I hoped that this woudl update the view of the table 
	private void updateTables() {
		table_functions_left_up.update();
		table_functions_left_down.update();
		table_states_right_up.update();
		table_states_right_down.update();
	}


}
