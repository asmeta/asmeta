package org.asmeta.simulationUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.asmeta.assertion_catalog.InvariantGUI;
import org.asmeta.assertion_catalog.LoadComboItem;
import org.asmeta.assertion_catalog.LoadDialog;
import org.asmeta.assertion_catalog.LoadSelectedSimulation;
import org.asmeta.parser.ASMParser;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.IModelAdaptation;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.impl.MonitoredFunctionImpl;
import javax.swing.JSeparator;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 */
public class SimGUI extends JFrame {
	public static JPanel contentPane;
	public static boolean darkMode;
	public static int fontSize;
	public static List<Image> icons;
	public static List<Integer> loadedIDs;
	public static ByteArrayOutputStream simConsole;
	private PrintStream previousConsole;
	
	static JScrollPane scrollPane;
	static JTextPane textPaneID;
	static JLabel lblSimID;
	static JTextPane textPaneModel;
	static JLabel lblModel;
	static JButton btnStop;
	static JButton btnRunStep;
	static JButton btnRunStepTimeout;
	static JButton btnRunUntilEmpty;
	static JButton btnRunUntilEmptyTimeout;
	static JMenuBar menuBar;
	static JMenu fileMenu;
	static JMenuItem openMenuItem;
	static JMenu windowMenu;
	static JRadioButtonMenuItem _12fontRadioItem;
	static JRadioButtonMenuItem _14fontRadioItem;
	static JRadioButtonMenuItem _16fontRadioItem;
	static JRadioButtonMenuItem _18fontRadioItem;
	static JTextArea textAreaLog;
	static JMenu fontSizeMenu;
	static JCheckBoxMenuItem darkModeCheckItem;
	static JMenuItem invManagerMenuItem;
	static ButtonGroup fontSizeGroup;
	static JMenu simulationMenu;
	static JMenuItem currentSimulationMenuItem;
	static JMenuItem saveMenuItem;
	static JMenuItem clearMenuItem;
	static JSeparator separator;
	static JMenuItem compositionMenuItem;
	static JMenu compositionTypeMenu;
	static JRadioButtonMenuItem pipeRadioItem;
	static JRadioButtonMenuItem bidPipeRadioItem;
	static JRadioButtonMenuItem parallelRadioItem;
	static ButtonGroup compositionTypeGroup;
	
	private static SimulationContainer containerInstance;
	private static int currentLoadedID;
	private static int currentMaxInstances;
	private static String currentLoadedModel;
	
	static final String PROPERTIES_FILE_PATH = "src/org/asmeta/simulationUI/.properties";

	/**
	 * Launch the application.
	 */	
	public static void main(IModelAdaptation containerInstance) {
		SimulationContainer contInstance;
		if(containerInstance == null) {
			contInstance = new SimulationContainer();
		} else {
			contInstance = (SimulationContainer) containerInstance;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File propertiesFile = new File(PROPERTIES_FILE_PATH);
					Scanner reader = new Scanner(propertiesFile);
					while(reader.hasNextLine()) {
						String data = reader.nextLine();
						if(data.startsWith("mode=")) {
							if(data.endsWith("dark")) {
								darkMode = true;
							} else {
								darkMode = false;
							}
						} else if(data.startsWith("fontSize=")) {
							switch(Integer.parseInt(data.substring(9))) {
								case 14: fontSize = 14; break;
								case 16: fontSize = 16; break;
								case 18: fontSize = 18; break;
								default: fontSize = 12;
							}
						}
					}
					reader.close();
					if(darkMode) {
						UIManager.setLookAndFeel(new FlatDarkLaf());
					} else {
						UIManager.setLookAndFeel(new FlatLightLaf());
					}
					SimGUI frame = new SimGUI(contInstance);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public SimGUI(SimulationContainer contInstance) {
		initialize();
		enableLoadSimButtons(false);
		containerInstance=contInstance;
		currentLoadedID=-99;
		currentLoadedModel="";
		currentMaxInstances=0;
	}
	
	
	private void initialize() {
		previousConsole = System.out;
		 
        // Set the standard output to use simConsole.
        simConsole = new ByteArrayOutputStream();
        icons = new Vector<Image>();
        
        icons.add(Toolkit.getDefaultToolkit().getImage(SimGUI.class.getResource("/org/asmeta/animator/icona_circolare_16.png")));
        icons.add(Toolkit.getDefaultToolkit().getImage(SimGUI.class.getResource("/org/asmeta/animator/icona_circolare_64.png")));
        
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, fontSize));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, fontSize));
        setIconImages(icons);
        setResizable(true);
        setMinimumSize(new Dimension(630, 350));
		setTitle("Simulator Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 545);
		setLocationRelativeTo(null); // open the SimGUI window in the center of the screen
		
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				if (currentLoadedID>0) {
					boolean changed=true;
					Map<Integer,String> ids = containerInstance.getLoadedIDs();
					if (ids!=null && ids.containsKey(currentLoadedID))
						if (ids.get(currentLoadedID).equals(currentLoadedModel))
							changed=false;
					if (changed) {
						enableLoadSimButtons(false);
						textPaneModel.setText("No simulation loaded");
						textPaneID.setText("X");
						currentLoadedID=-99;
						currentLoadedModel="";
						JOptionPane.showMessageDialog(contentPane, "Previously loaded simulation was terminated or changed ID externally", "Warning", JOptionPane.WARNING_MESSAGE);	
					}
				}
			}

			@Override
			public void windowLostFocus(WindowEvent e) { return; }
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if(btnStop.isEnabled()) {
		        	btnStop.doClick();
		        }
		    }
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Handle window frame dynamic resizing of components
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				int frameWidth = e.getComponent().getWidth();
				int frameHeight = e.getComponent().getHeight();
				// DEBUG: System.out.println("Width: " + frameWidth);
				// DEBUG: System.out.println("Height: " + frameHeight);
				
				// Handle menuBar resizing
				menuBar.setBounds(new Rectangle(0, 0, frameWidth - 16, 22));
				
				// Handle textPaneModel and textPaneID resizing and replacement
				textPaneModel.setBounds(new Rectangle(47, 60, frameWidth - 215, 22));
				textPaneID.setBounds(new Rectangle(frameWidth - 145, 60, 78, 22));
				
				// Handle lblModel and lblSimID resizing and replacement
				lblSimID.setBounds(new Rectangle(frameWidth - 145, 33, 119, 22));
				lblModel.setBounds(new Rectangle(47, 33, 230, 22));
				
				// Handle scrollPane resizing
				scrollPane.setBounds(new Rectangle(47, 93, frameWidth - 114, frameHeight - 273));
				
				// Handle btnStop, btnRunStep, btnRunStepTimeout, btnRunUntilEmpty, btnRunUntilEmptyTimeout replacement
				if(frameWidth < 989) {
					btnRunStep.setBounds(new Rectangle(57, frameHeight - 155, 163, 40));
					btnRunStepTimeout.setBounds(new Rectangle(57, frameHeight - 105, 163, 40));
					btnRunUntilEmpty.setBounds(new Rectangle(230, frameHeight - 155, 163, 40));
					btnRunUntilEmptyTimeout.setBounds(new Rectangle(230, frameHeight - 105, 163, 40));
					btnStop.setBounds(new Rectangle(Math.min(frameWidth - 230, 440), frameHeight - 145, 163, 70));
				} else {
					btnRunStep.setBounds(new Rectangle(57, frameHeight - 155, 163, 50));
					btnRunUntilEmpty.setBounds(new Rectangle(230, frameHeight - 155, 163, 50));
					btnRunStepTimeout.setBounds(new Rectangle(403, frameHeight - 155, 163, 50));
					btnRunUntilEmptyTimeout.setBounds(new Rectangle(576, frameHeight - 155, 163, 50));
					btnStop.setBounds(new Rectangle(frameWidth - 230, frameHeight - 165, 163, 70));
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) { return; }

			@Override
			public void componentShown(ComponentEvent e) { return; }

			@Override
			public void componentHidden(ComponentEvent e) { return; }
		});
		
		/*
		 *	Deprecated: using openMenuItem and invManagerMenuItem instead (#22)
		 *
		 *	JButton choose = new JButton("Choose simulation");
		 *	choose.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		 *	choose.setBounds(649, 53, 170, 71);
		 *	contentPane.add(choose);
		 *	
		 *	JButton invmanager = new JButton("Show assertion catalog");
		 *	invmanager.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		 *	invmanager.setBounds(649, 142, 170, 71);
		 *	contentPane.add(invmanager);
		 *
		 */
			
		
		btnRunStep = new JButton("Run step");
		btnRunStep.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		btnRunStep.setBounds(57, 390, 163, 40);
		contentPane.add(btnRunStep);
		
		btnRunUntilEmpty = new JButton("Run until empty");
		btnRunUntilEmpty.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		btnRunUntilEmpty.setBounds(230, 390, 163, 40);
		contentPane.add(btnRunUntilEmpty);
		
		lblModel = new JLabel("Loaded model:");
		lblModel.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
		lblModel.setHorizontalAlignment(SwingConstants.LEFT);
		lblModel.setBounds(47, 33, 230, 22);
		contentPane.add(lblModel);
		
		textPaneModel = new JTextPane();
		textPaneModel.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		if(!darkMode) {
			textPaneModel.setBackground(Color.WHITE);
		} else {
			textPaneModel.setBackground(new Color(40, 40, 40));
		}
		textPaneModel.setText("No simulation loaded");
		textPaneModel.setBounds(47, 60, 465, 22);
		textPaneModel.setEditable(false);
		contentPane.add(textPaneModel);

		if(fontSize > 16) {
			lblSimID = new JLabel("Sim. ID:");
		} else {
			lblSimID = new JLabel("Simulation ID:");
		}
		lblSimID.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
		lblSimID.setBounds(525, 33, 129, 22);
		lblSimID.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblSimID);
		
		textPaneID = new JTextPane();
		textPaneID.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		textPaneID.getStyledDocument().setParagraphAttributes(0, textPaneID.getStyledDocument().getLength(), center, false);
		if(!darkMode) {
			textPaneID.setBackground(Color.WHITE);
		} else {
			textPaneID.setBackground(new Color(40, 40, 40));
		}
		textPaneID.setText("X");
		textPaneID.setBounds(535, 60, 78, 22);
		textPaneID.setEditable(false);
		contentPane.add(textPaneID);
		
		btnStop = new JButton("Stop simulation");
		btnStop.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		btnStop.setBounds(440, 400, 163, 70);
		btnStop.setEnabled(false);
		contentPane.add(btnStop);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 93, 566, 272);
		contentPane.add(scrollPane);
		
		textAreaLog = new JTextArea();
		textAreaLog.setEditable(false);
		textAreaLog.setFont(new Font("Consolas", Font.PLAIN, fontSize + 1));
		if(!darkMode) {
			textAreaLog.setBackground(Color.WHITE);
		} else {
			textAreaLog.setBackground(new Color(40, 40, 40));
		}
		textAreaLog.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret)textAreaLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textAreaLog);
		
		if(fontSize > 12) {
			btnRunStepTimeout = new JButton("Run step TO");
		} else {
			btnRunStepTimeout = new JButton("Run step timeout");
		}
		btnRunStepTimeout.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		btnRunStepTimeout.setEnabled(false);
		btnRunStepTimeout.setBounds(57, 440, 163, 40);
		contentPane.add(btnRunStepTimeout);
		
		if(fontSize > 12) {
			btnRunUntilEmptyTimeout = new JButton("RunUntilEmptyTO");
		} else {
			btnRunUntilEmptyTimeout = new JButton("Run until empty timeout");	
		}
		btnRunUntilEmptyTimeout.setFont(new Font("Segoe UI", Font.PLAIN, Math.min(fontSize, 16)));
		btnRunUntilEmptyTimeout.setEnabled(false);
		btnRunUntilEmptyTimeout.setBounds(230, 440, 163, 40);
		contentPane.add(btnRunUntilEmptyTimeout);
		
		menuBar = new JMenuBar();
		if(!darkMode) {
			menuBar.setBackground(new Color(227, 227, 227));
		} else {
			menuBar.setBorder(null);
			menuBar.setBackground(new Color(40, 40, 40));
		}
		menuBar.setBounds(0, 0,  664, 22);
		contentPane.add(menuBar);
		
		fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		menuBar.add(fileMenu);
		
		openMenuItem = new JMenuItem("Load simulations...");
		openMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		fileMenu.add(openMenuItem);
		
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setEnabled(false);
		saveMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		fileMenu.add(saveMenuItem);
		
		simulationMenu = new JMenu("Simulation");
		simulationMenu.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		menuBar.add(simulationMenu);
		
		invManagerMenuItem = new JMenuItem("Open assertion catalog");
		invManagerMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		invManagerMenuItem.setEnabled(false);
		simulationMenu.add(invManagerMenuItem);
		
		currentSimulationMenuItem = new JMenuItem("Select simulation");
		currentSimulationMenuItem.setEnabled(false);
		currentSimulationMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		simulationMenu.add(currentSimulationMenuItem);
		
		compositionTypeMenu = new JMenu("Select composition pattern");
		compositionTypeMenu.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		compositionTypeMenu.setEnabled(false);
		simulationMenu.add(compositionTypeMenu);
		
		compositionTypeGroup = new ButtonGroup();
		
		pipeRadioItem = new JRadioButtonMenuItem("Pipe");
		pipeRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		pipeRadioItem.setSelected(true);
		compositionTypeGroup.add(pipeRadioItem);
		compositionTypeMenu.add(pipeRadioItem);
		
		bidPipeRadioItem = new JRadioButtonMenuItem("Bidirectional pipe");
		bidPipeRadioItem.setEnabled(false);
		bidPipeRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		compositionTypeGroup.add(bidPipeRadioItem);
		compositionTypeMenu.add(bidPipeRadioItem);
		
		parallelRadioItem = new JRadioButtonMenuItem("Parallel");
		parallelRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		compositionTypeGroup.add(parallelRadioItem);
		compositionTypeMenu.add(parallelRadioItem);
		
		compositionMenuItem = new JMenuItem("Compose models");
		compositionMenuItem.setEnabled(false);
		compositionMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		simulationMenu.add(compositionMenuItem);
		
		separator = new JSeparator();
		simulationMenu.add(separator);
		
		clearMenuItem = new JMenuItem("Clear output");
		clearMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		clearMenuItem.setEnabled(false);
		simulationMenu.add(clearMenuItem);
		
		windowMenu = new JMenu("Window");
		windowMenu.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		menuBar.add(windowMenu);
		
		darkModeCheckItem = new JCheckBoxMenuItem("Dark Mode");
		darkModeCheckItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		windowMenu.add(darkModeCheckItem);
		darkModeCheckItem.setState(darkMode);
		
		fontSizeMenu = new JMenu("Font size");
		fontSizeMenu.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		windowMenu.add(fontSizeMenu);
		
		fontSizeGroup = new ButtonGroup();
		
		_12fontRadioItem = new JRadioButtonMenuItem("12");
		_12fontRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		fontSizeMenu.add(_12fontRadioItem);
		fontSizeGroup.add(_12fontRadioItem);
		
		_14fontRadioItem = new JRadioButtonMenuItem("14");
		_14fontRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		fontSizeMenu.add(_14fontRadioItem);
		fontSizeGroup.add(_14fontRadioItem);
		
		_16fontRadioItem = new JRadioButtonMenuItem("16");
		_16fontRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		fontSizeMenu.add(_16fontRadioItem);
		fontSizeGroup.add(_16fontRadioItem);
		
		_18fontRadioItem = new JRadioButtonMenuItem("18");
		_18fontRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
		fontSizeMenu.add(_18fontRadioItem);
		fontSizeGroup.add(_18fontRadioItem);
		
		switch(fontSize) {
			case 14: _14fontRadioItem.setSelected(true); break;
			case 16: _16fontRadioItem.setSelected(true); break;
			case 18: _18fontRadioItem.setSelected(true); break;
			default: _12fontRadioItem.setSelected(true);
		}
		
		_12fontRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimGUI.setProperty("fontSize", "12");
				JOptionPane.showMessageDialog(contentPane, "Re-open the application to apply the new font size!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		
		_14fontRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimGUI.setProperty("fontSize", "14");
				JOptionPane.showMessageDialog(contentPane, "Re-open the application to apply the new font size!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		
		_16fontRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimGUI.setProperty("fontSize", "16");
				JOptionPane.showMessageDialog(contentPane, "Re-open the application to apply the new font size!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		
		_18fontRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimGUI.setProperty("fontSize", "18");
				JOptionPane.showMessageDialog(contentPane, "Re-open the application to apply the new font size!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		
		pipeRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompositionGUI.compType = CompositionType.PIPE;
			}
		});
		
		bidPipeRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompositionGUI.compType = CompositionType.BID_PIPE;
			}
		});
		
		parallelRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompositionGUI.compType = CompositionType.PARALLEL;
			}
		});
		
		darkModeCheckItem.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				if(darkModeCheckItem.getState()) {
					SimGUI.setProperty("mode", "dark");
					JOptionPane.showMessageDialog(contentPane, "Re-open the application to apply the dark mode!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
				} else {
					SimGUI.setProperty("mode", "light");
					JOptionPane.showMessageDialog(contentPane, "Re-open the application to disable the dark mode!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
				}
			}
		});
		
		compositionMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loadedIDs = new ArrayList<>(containerInstance.getLoadedIDs().keySet());
					loadedIDs.remove((Object) currentLoadedID);
					
					int receiverID = (int) JOptionPane.showInputDialog(contentPane, 
																	   "  Select the ID of the model that will be\ncomposed with the current loaded model:",
																	   "Receiver ID",
																	   JOptionPane.QUESTION_MESSAGE,
																	   null,
																	   loadedIDs.toArray(),
																	   null);
					// DEBUG: System.out.println(receiverID);
					CompositionGUI.main(containerInstance, currentLoadedID, receiverID);
					if(loadedIDs.size() <= 1) {
						compositionMenuItem.setEnabled(false);
					}
				} catch(Exception ex) {
					//DEBUG: ex.printStackTrace();
					return;
				}
			}
		});
		
		clearMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentLoadedID >= 1 && textAreaLog.getText() != null) {
					if(JOptionPane.showConfirmDialog(contentPane, 
												  "Do you want to save the current simulation output?",
												  "Save",
												  JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						saveMenuItem.doClick();
					}
					textAreaLog.setText("");
				}
				return;
			}
		});
		
		saveMenuItem.addActionListener(new ActionListener() {
			public String toTxt(String s) {
				if(s.length() < 4) {
					return null;
				}
				if(s.substring(s.length() - 4, s.length()).equals(".txt")) {
					return s;
				}
				return (s + ".txt");
			}
			
			public void actionPerformed(ActionEvent e) {
				if(currentLoadedID >= 1 && textAreaLog.getText() != null) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
					fileChooser.setApproveButtonText("Save");
					fileChooser.setSelectedFile(new File("simulation_output.txt"));
					
					String filePath;
					File outputFile;
					FileWriter writer;
					StringBuilder infoData = new StringBuilder();
					SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					SimpleDateFormat dateTimeFormatter_ = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
					
					if(fileChooser.showSaveDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
						try {
							filePath = fileChooser.getSelectedFile().getAbsolutePath();
							outputFile = new File(toTxt(filePath));
							
							if(outputFile.isDirectory()) {
								throw new FileNotFoundException();
							}
							if(outputFile.exists()) {
								if(JOptionPane.showConfirmDialog(contentPane, 
																 "Do you want to overwrite the existing file?", 
																 "Overwrite", 
																 JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
									outputFile = new File(fileChooser.getSelectedFile().getParent(), 
														  toTxt("simulation_output_" + dateTimeFormatter_.format(new Date())));
								}
							}
							outputFile.createNewFile();
							infoData.append("Simulation Output timestamp: " + dateTimeFormatter.format(new Date()) + "\n");
							infoData.append("Model path: " + currentLoadedModel + "\n");
							infoData.append("Simulation ID: " + Integer.toString(currentLoadedID) + "\n");
							infoData.append("------------------------------------------------\n\n");
							
							writer = new FileWriter(outputFile);
							writer.write(infoData.toString());
							writer.write(textAreaLog.getText());
							writer.close();
							JOptionPane.showMessageDialog(contentPane, "Simulation output saved to file!", "Success", JOptionPane.INFORMATION_MESSAGE);
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(contentPane, "Error: could not save the file!", "Error", JOptionPane.ERROR_MESSAGE);
							//ex.printStackTrace();
						}
					} else {
						fileChooser.cancelSelection();
					}
				}
			}
		});
		
		currentSimulationMenuItem.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   try {	
					   Map<Integer, String> ids = containerInstance.getLoadedIDs();
					   LoadComboItem ci=null;
					   if(!ids.isEmpty()) {
						   ci = new LoadSelectedSimulation(ids).showDialog();
					   }
					   if(ci!=null) {
						   currentLoadedID = ci.getInt();
						   currentLoadedModel = ci.getStr();
						   if(!currentLoadedModel.isEmpty() && currentLoadedModel.indexOf(".asm")!=-1){
							   if (currentLoadedModel.indexOf("\\")>=0) {
								   textPaneModel.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
								   textPaneID.setText(Integer.toString(currentLoadedID));
							   } else {
								   textPaneModel.setText(currentLoadedModel);
								   textPaneID.setText(Integer.toString(currentLoadedID));
							   }
							   clearMenuItem.doClick();
							   textAreaLog.setText("Simulation ready.\n");
						   }
						   else if(currentLoadedModel.indexOf(".asm")==-1 && !currentLoadedModel.isEmpty())
							   JOptionPane.showMessageDialog(contentPane, "Error: wrong extension!", "Error", JOptionPane.ERROR_MESSAGE);
					   }
				   } catch (Exception ex) {
			    		if(ex instanceof java.io.FileNotFoundException)
				    		JOptionPane.showMessageDialog(contentPane, "Error: no file selected!", "Error", JOptionPane.ERROR_MESSAGE);
				    	else if(ex instanceof java.lang.NullPointerException) {
				    		JOptionPane.showMessageDialog(contentPane, "Error: parser error!", "Error", JOptionPane.ERROR_MESSAGE);
				    		ex.printStackTrace();
				    	} 	
				    	else {
				    		ex.printStackTrace();
				    	}
				   }
			   }
		});

		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentMaxInstances<1)
				{
					String num = (String) JOptionPane.showInputDialog(
							contentPane, 											// parent component
							"How many simulations do you want to instantiate?", 	// message
							"Number of simulations", 								// title
							JOptionPane.QUESTION_MESSAGE, 							// message type
							null, 													// icon
							null, 													// options
							"1"														// initial default value
					);
					if(num!=null) {
						try {
							if(Integer.parseInt(num)>0) {
								currentMaxInstances=containerInstance.init(Integer.parseInt(num));
								openMenuItem.doClick();
							} else {
								throw new NumberFormatException();
							}
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(contentPane, "Error: not a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else { // currentMaxInstances>=1
					LoadComboItem ci = null;
					LoadDialog ld = null;
					Map<Integer, String> ids = containerInstance.getLoadedIDs();
						
			    	if (!ids.isEmpty()) {
			    		// setAllEnabled(1);
			    		ci = new LoadDialog(containerInstance,ids).showDialog();
			    		//JOptionPane.showMessageDialog(null, ci.getStr());
			    	} else {
			    		ld = new LoadDialog(containerInstance,ids);
			    		ld.disablebutton();
			    		ci = new LoadDialog(containerInstance,ids).showDialog();
			    	}
					
					
		    		if (ci!=null) {
		    			currentLoadedID = ci.getInt();
		    			currentLoadedModel = ci.getStr();
		    			textPaneID.setText(""+currentLoadedID);
		    			//textPaneModel.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
		    			if (currentLoadedModel.indexOf("\\")>=0)
		    				textPaneModel.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
		    			else
		    				textPaneModel.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
		    			enableLoadSimButtons(true);
		    			textAreaLog.setText("Simulation ready.\n");
		    			invManagerMenuItem.setEnabled(true);
		    			saveMenuItem.setEnabled(true);
		    			clearMenuItem.setEnabled(true);
		    			
		    			if(currentMaxInstances >= 2) {
		    				if(currentMaxInstances == 2) {
		    					bidPipeRadioItem.setEnabled(true);
		    				}
		    				currentSimulationMenuItem.setEnabled(true);
		    				compositionMenuItem.setEnabled(true);
		    				compositionTypeMenu.setEnabled(true);
		    			}
		    			
		    			if(containerInstance.getLoadedIDs().size() >= currentMaxInstances) {
		    				openMenuItem.setEnabled(false);
		    			}
		    		}
				}
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentLoadedID>0) {
					int stop = containerInstance.stopExecution(currentLoadedID);
					if (stop==-1)
						JOptionPane.showMessageDialog(contentPane, "Error: couldn't stop simulation!", "Error", JOptionPane.ERROR_MESSAGE);
					else {
						currentLoadedID=-99;
						currentLoadedModel="";
						enableLoadSimButtons(false);
						if(SimGUI.containerInstance.getLoadedIDs().isEmpty() || !SimGUI.containerInstance.getLoadedIDs().containsKey(InvariantGUI.getCurrentLoadedID())) {
							InvariantGUI.setAllEnabled(0);
						}
						textPaneModel.setText("No simulation loaded");
						textPaneID.setText("X");
						textAreaLog.append("Simulation stopped.\n");
					}
				}else {
					JOptionPane.showMessageDialog(contentPane, "Error: no simulation selected!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		invManagerMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentLoadedID<1)
					JOptionPane.showMessageDialog(contentPane, "Error: no simulation selected!", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					InvariantGUI invGUI = new InvariantGUI(containerInstance,currentLoadedID,currentLoadedModel);
					invGUI.setVisible();
					InvariantGUI.frame.setLocationRelativeTo(contentPane);
				}	
			}
		});
		
		btnRunUntilEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.setErr(new PrintStream(simConsole));
				System.setOut(new PrintStream(simConsole));
				List<String> monitored = getMonitored();
				RunOutput out=new RunOutput(Esit.UNSAFE, "rout not intialized");
				if(monitored == null) {
					textAreaLog.append("Couldn't execute operation.\n");
				} else {
					if(monitored.size() < 1) {
						out=containerInstance.runUntilEmpty(currentLoadedID);
					} else {
						Map<String, String> input = getInput(monitored, false);
						out=containerInstance.runUntilEmpty(currentLoadedID, input);
					}
				}
				previousConsole.println(simConsole.toString()); // Display output of simConsole.
				 
		        // Restore back the standard console output.
		        System.setOut(previousConsole);
		        System.setErr(previousConsole);
		        textAreaLog.append("");
		        textAreaLog.append(simConsole.toString());
		        simConsole.reset();
			}
		});
		
		btnRunUntilEmptyTimeout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.setErr(new PrintStream(simConsole));
				System.setOut(new PrintStream(simConsole));
				List<String> monitored = getMonitored();
				RunOutput out=new RunOutput(Esit.UNSAFE, "rout not intialized");
				int timeout=-1;
				String num=JOptionPane.showInputDialog(contentPane, "Insert timeout (milliseconds):", "Timeout", JOptionPane.PLAIN_MESSAGE);
				if(num!=null)
				{
					try {
						timeout=Integer.parseInt(num);
						if(timeout < 0) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(contentPane, "Error: not a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				if(timeout >= 0 && monitored != null) {
					if (monitored.size()<1)
						out=containerInstance.runUntilEmptyTimeout(currentLoadedID,timeout);
					else {
						Map<String, String> input = getInput(monitored, false);
						out=containerInstance.runUntilEmptyTimeout(currentLoadedID, input,timeout);
					}
					//JOptionPane.showMessageDialog(null, out.toString());	
					//textAreaLog.append("Runstep with timeout executed with current result:\n"+out.MytoString()+"\n");
				} else
					textAreaLog.append("Couldn't execute operation.\n");
				previousConsole.println(simConsole.toString()); // Display output of simConsole.
				 
		        // Restore back the standard console output.
		        System.setOut(previousConsole);
		        System.setErr(previousConsole);
		        textAreaLog.append("");
		        textAreaLog.append(simConsole.toString());
		        simConsole.reset();
			}
		});
		
		btnRunStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.setErr(new PrintStream(simConsole));
				System.setOut(new PrintStream(simConsole));
				List<String> monitored = getMonitored();
				RunOutput out = new RunOutput(Esit.UNSAFE, "rout not intialized");
				if(monitored == null) {
					textAreaLog.append("Couldn't execute operation.\n");
				} else {
					if(monitored.size() < 1) {
						out = containerInstance.runStep(currentLoadedID);
					} else {
						Map<String, String> input = getInput(monitored, false);
						out = containerInstance.runStep(currentLoadedID, input);
					}
				}
				
				// Supporting multi-model composition (unidirectional cascade pipe, partial bidirectional pipe, (coupled) fork-join execution)
				CompositionPanel tab = null;
				if(CompositionGUI.getConPane() != null && CompositionGUI.compositionContainer != null) {
					// Logic is handled entirely by the composition container
					try {
						CompositionGUI.compositionContainer.runStep(out, true);
					} catch(EmptyCompositionListException e) {
						JOptionPane.showMessageDialog(contentPane, "Error: the composition container is empty!", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(CompositionSizeOutOfBoundException e) {
						JOptionPane.showMessageDialog(contentPane, "Error: the bidirectional pipe requires only two models!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					// Graphics (GUI) is update with a simple loop separated from the logic.
					for(Composition comp: CompositionGUI.getCompositionTabs().keySet()) {
						tab = CompositionGUI.getCompositionTabs().get(comp);
						previousConsole.println(comp.outputConsole.toString());
						tab.textAreaLog.append("");
						tab.textAreaLog.append(comp.outputConsole.toString());
						comp.outputConsole.reset();
					}
				}
				 
		        previousConsole.println(simConsole.toString()); // Display output of simConsole.
		        
		        // Restore back the standard console output.
		        System.setOut(previousConsole);
		        System.setErr(previousConsole);
		        textAreaLog.append("");
		        textAreaLog.append(simConsole.toString());
		        simConsole.reset();
			}
		});
		
		btnRunStepTimeout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.setErr(new PrintStream(simConsole));
				System.setOut(new PrintStream(simConsole));
				List<String> monitored = getMonitored();
				RunOutput out=new RunOutput(Esit.UNSAFE, "rout not intialized");
				int timeout=-1;
				String num=JOptionPane.showInputDialog(contentPane, "Insert timeout (milliseconds):", "Timeout", JOptionPane.PLAIN_MESSAGE);
				if(num!=null)
				{
					try {
						timeout=Integer.parseInt(num);
						if(timeout < 0) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(contentPane, "Error: not a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				if(timeout >= 0 && monitored != null) {
					if (monitored.size()<1)
						out=containerInstance.runStepTimeout(currentLoadedID,timeout);
					else {
						Map<String, String> input = getInput(monitored, false);
						out=containerInstance.runStepTimeout(currentLoadedID, input,timeout);
					}
					//JOptionPane.showMessageDialog(null, out.toString());	
					//textAreaLog.append("Runstep with timeout executed with current result:\n"+out.MytoString()+"\n");
				} else
					textAreaLog.append("Couldn't execute operation.\n");
				previousConsole.println(simConsole.toString()); // Display output of simConsole.
				 
		        // Restore back the standard console output.
		        System.setOut(previousConsole);
		        System.setErr(previousConsole);
		        textAreaLog.append("");
		        textAreaLog.append(simConsole.toString());
		        simConsole.reset();
			}
		});
	}
	
	/**
	 * Change a property value in the '.properties' file.
	 * @param propertyName: name of the property in '.properties' file.
	 * @param value: new property value.
	 */
	public static void setProperty(String propertyName, String value) {
		try {
			File propertiesFile = new File(PROPERTIES_FILE_PATH);
			ArrayList<String> fileContent = new ArrayList<>();
			String line = "";
			BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));
			while((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			
			FileWriter writer = new FileWriter(propertiesFile);
			for(String property: fileContent) {
				property = property.replaceAll(("^" + propertyName + "=.+?$"), (propertyName + "=" + value));
				writer.write(property);
				writer.write(System.lineSeparator());
			}
			reader.close();
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void enableLoadSimButtons(boolean enable) {
		btnStop.setEnabled(enable);
		btnRunStep.setEnabled(enable);
		btnRunUntilEmpty.setEnabled(enable);
		btnRunStepTimeout.setEnabled(enable);
		btnRunUntilEmptyTimeout.setEnabled(enable);
		invManagerMenuItem.setEnabled(enable);
	}
	
	private List<String> getMonitored(){
		ArrayList<String> monitoredList = new ArrayList<String>();
		if (!currentLoadedModel.equals("")) {
			File asmFile = new File(currentLoadedModel);
			if (asmFile.exists()) {
				AsmCollection asm;
				try {
					asm = ASMParser.setUpReadAsm(asmFile);
					for (int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
						Function f = asm.getMain().getHeaderSection().getSignature().getFunction().get(i);
						if (f instanceof MonitoredFunctionImpl) {
							if(f.getArity() == 0) {
								monitoredList.add(f.getName());
							} else {
								String domainValue = (String) JOptionPane.showInputDialog( // expected input syntax: x,y,z,... -> ex. 50,120 or just 50
										contentPane, 												// parent component
										"Insert '" + f.getName() + "' domain value/values:\n" + 
										"Domain type: [ " + f.getDomain().getName() + " ]", 		// message
										"Domain input", 											// title
										JOptionPane.PLAIN_MESSAGE, 									// message type
										null, 														// icon
										null, 														// options
										null														// initial default value
								);
								if(domainValue != null && !domainValue.isEmpty()) {
									monitoredList.add(f.getName() + "(" + domainValue + ")");
								} else {
									throw new Exception();
								}
							}
						}
					}
				} catch (Exception e) {
					monitoredList = null;
					JOptionPane.showMessageDialog(contentPane, "Error: check the model and the input!", "Error", JOptionPane.ERROR_MESSAGE);
				}			
			}
		}
		return monitoredList;
	}
	
	private Map<String, String> getInput(List<String> monitoredList, boolean auto) {
		Map<String, String> input = new HashMap<>();
		Map<String, Object[]> enumDomainFunction = new HashMap<>();
		String inputValue = new String();
		Object[] options;
		
		if(monitoredList == null || monitoredList.isEmpty()) {
			return null;
		}
		
		if(!currentLoadedModel.equals("")) {
			File asmFile = new File(currentLoadedModel);
			if(asmFile.exists()) {
				AsmCollection asm;
				try {
					asm = ASMParser.setUpReadAsm(asmFile);
					for(int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
						Function f = asm.getMain().getHeaderSection().getSignature().getFunction().get(i);
						if(monitoredList.contains(f.getName())) {
							if(f.getCodomain() != null) // supporto per le funzioni 0-arie: solo codominio
								enumDomainFunction.put(f.getName(), getEnumDomain(f.getCodomain().getName()).toArray());
						}
					}
				} catch (Exception e) {
					//e.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, "Error: asm parsing error!", "Error", JOptionPane.ERROR_MESSAGE);
				}			
			}
		}
		
		for(String monitored: monitoredList) {
			options = enumDomainFunction.get(monitored);
			if(options != null) {
				if(options.length == 0) {
					options = null;
				}
			}
			if(auto && options != null) {
				Random seed = new Random();
				inputValue = (String) options[seed.nextInt(options.length)];
				System.out.println("Generated input for '" + monitored + "': " + inputValue);
			} else {
				
				inputValue = (String) JOptionPane.showInputDialog(
						contentPane, 								// parent component
						"Insert " + monitored + " value:", 			// message
						"Input", 									// title
						JOptionPane.PLAIN_MESSAGE, 					// message type
						null, 										// icon
						options, 									// options
						null										// initial default value
				);
			}
			input.put(monitored, inputValue);
		}
		return input;
	}
	
	/**
	 * Helper function to retrieve possible enum values from a limited
	 * enum domain from the currentLoadedModel asm source file.
	 * @param domainName: the name of the enum domain.
	 * @return an ArrayList of String which are the possible values for the enum domain (domainName).
	 */
	private ArrayList<String> getEnumDomain(String domainName) {
		ArrayList<String> values = new ArrayList<>();
		ArrayList<String> enumDomainContent = new ArrayList<>();
		BufferedReader reader;
		String[] valuesArray;
		Pattern domainPattern = Pattern.compile("\\{.*?\\}");
		//Pattern enumPattern = Pattern.compile(".*?\\|.*|[^\\||^\" \"]*");
		Matcher matcher;
		boolean commented = false;
		
		if(!currentLoadedModel.equals("")) {
			File asmFile = new File(currentLoadedModel);
			if(asmFile.exists()) {
				//AsmCollection asm;
				try {
					String line = "";
					reader = new BufferedReader(new FileReader(asmFile));
					while((line = reader.readLine()) != null) {
						if(line.contains("/*")) {
							commented = true;
						}
						if(line.contains("*/")) {
							commented = false;
						}
						if(line.contains("enum") && line.contains("domain") && line.contains(domainName) && !line.startsWith("//") && !commented) {
							line = line.trim();
							enumDomainContent.add(line);
							// DEBUG: System.out.println("\n" + line + "\n");
						}	
					}
					for(String enumDomain: enumDomainContent) {
						matcher = domainPattern.matcher(enumDomain);
						// DEBUG: System.out.println(enumDomain);
						if(matcher.find()) {
							enumDomain = matcher.group().trim();
							enumDomain = enumDomain.replaceAll(" ", "");
							enumDomain = enumDomain.replaceAll("\\{", "");
							enumDomain = enumDomain.replaceAll("\\}", "");
							// DEBUG: System.out.println("\n" + enumDomain + "\n");
							if(enumDomain.contains("|")) {
								valuesArray = enumDomain.split("\\|");
								for(String value: valuesArray) {
									// DEBUG: System.out.println(value);
									values.add(value);
								}
							} else {
								values.add(enumDomain);
							}
						}
					}
					reader.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(contentPane, "Error: parsing error!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		return values;
	}
	
	public static int getMaxInstances() {
		return SimGUI.currentMaxInstances;
	}
}



