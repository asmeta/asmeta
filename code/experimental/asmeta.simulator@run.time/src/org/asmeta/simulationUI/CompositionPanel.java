package org.asmeta.simulationUI;

/**
 * @author Michele Zenoni
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import org.asmeta.runtime_composer.AsmetaModel;

public class CompositionPanel extends JPanel {
	JLabel lblModel;
	JTextPane textPaneModel;
	JTextPane textPaneModelID;
	JScrollPane scrollPane;
	JTextArea textAreaLog;
	JLabel lblModelID;
	JButton btnSave;
	JButton btnInvManager;
	JButton btnClear;
	
	AsmetaModel currentModel;
	
	public CompositionPanel(AsmetaModel model) {
		currentModel = model;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		
		lblModel = new JLabel("Current model:");
		lblModel.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblModel.setHorizontalAlignment(SwingConstants.LEFT);
		lblModel.setBounds(47, 11, 191, 22);
		add(lblModel);
		
		lblModelID = new JLabel("Model ID:");
		lblModelID.setHorizontalAlignment(SwingConstants.LEFT);
		lblModelID.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblModelID.setBounds(591, 11, 78, 22);
		add(lblModelID);
		
		textPaneModel = new JTextPane();
		textPaneModel.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		if(!SimGUI.darkMode) {
			textPaneModel.setBackground(Color.WHITE);
		} else {
			textPaneModel.setBackground(new Color(40, 40, 40));
		}
		textPaneModel.setText("No model loaded");
		textPaneModel.setBounds(47, 38, 511, 22);
		textPaneModel.setEditable(false);
		add(textPaneModel);

		textPaneModelID = new JTextPane();
		textPaneModelID.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		textPaneModelID.getStyledDocument().setParagraphAttributes(0, textPaneModelID.getStyledDocument().getLength(), center, false);
		if(!SimGUI.darkMode) {
			textPaneModelID.setBackground(Color.WHITE);
		} else {
			textPaneModelID.setBackground(new Color(40, 40, 40));
		}
		textPaneModelID.setText("X");
		textPaneModelID.setBounds(581, 38, 78, 22);
		textPaneModelID.setEditable(false);
		add(textPaneModelID);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 71, 592, 308);
		add(scrollPane);
		
		textAreaLog = new JTextArea();
		textAreaLog.setEditable(false);
		textAreaLog.setFont(new Font("Consolas", Font.PLAIN, SimGUI.fontSize + 1));
		if(!SimGUI.darkMode) {
			textAreaLog.setBackground(Color.WHITE);
		} else {
			textAreaLog.setBackground(new Color(40, 40, 40));
		}
		textAreaLog.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret)textAreaLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textAreaLog);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnSave.setEnabled(false);
		btnSave.setBounds(187, 402, 120, 40);
		add(btnSave);
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnClear.setEnabled(false);
		btnClear.setBounds(317, 402, 120, 40);
		add(btnClear);
		
		btnInvManager = new JButton("Invariants");
		btnInvManager.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnInvManager.setEnabled(false);
		btnInvManager.setBounds(57, 402, 120, 40);
		add(btnInvManager);
		
		if(currentModel != null && currentModel.getModelId() >= 1 && CompositionGUI.containerInstance != null) {
			textPaneModel.setText(currentModel.getModelName());
			textPaneModelID.setText(Integer.toString(currentModel.getModelId()));
			
			btnInvManager.setEnabled(true);
			btnSave.setEnabled(true);
			btnClear.setEnabled(true);
		}
		
		// Handle window frame dynamic resizing of components
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				int tabWidth = e.getComponent().getWidth();
				int tabHeight = e.getComponent().getHeight();
				
				// Handle lblModel, lblReceiver, lblModelID and lblReceiverID resizing
				lblModel.setBounds(new Rectangle(47, 11, 191, 22));
				lblModelID.setBounds(new Rectangle(tabWidth - 115, 11, 78, 22));
				
				// Handle textPaneModel, textPaneModelID, textPaneReceiver and textPaneReceiverID resizing
				textPaneModel.setBounds(new Rectangle(47, 38, tabWidth - 195, 22));
				textPaneModelID.setBounds(new Rectangle(tabWidth - 125, 38, 78, 22));
					
				// Handle scrollPane resizing
				scrollPane.setBounds(new Rectangle(47, 69, tabWidth - 92, tabHeight - 165));
				
				// Handle btnInvManager, btnSave and btnClear resizing and replacement
				if(tabWidth < 989) {
					btnInvManager.setBounds(new Rectangle(57, tabHeight - 71, 120, 40));
					btnSave.setBounds(new Rectangle(187, tabHeight - 71, 120, 40));
					btnClear.setBounds(new Rectangle(317, tabHeight - 71, 120, 40));
				} else {
					btnInvManager.setBounds(new Rectangle(57, tabHeight - 71, 120, 50));
					btnSave.setBounds(new Rectangle(197, tabHeight - 71, 120, 50));
					btnClear.setBounds(new Rectangle(327, tabHeight - 71, 120, 50));
				}
			}
			
			@Override
			public void componentMoved(ComponentEvent e) { return; }
					
			@Override
			public void componentShown(ComponentEvent e) { return; }

			@Override
			public void componentHidden(ComponentEvent e) { return; }
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentModel.getModelId() >= 1 && textAreaLog.getText() != null) {
					if(JOptionPane.showConfirmDialog(CompositionGUI.getConPane(), 
												  	 "Do you want to save the current simulation output?",
												  	 "Save",
												  	 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						btnSave.doClick();
					}
					textAreaLog.setText("");
				}
				return;
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
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
				if(textAreaLog.getText() != null && currentModel.getModelId() >= 1) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
					fileChooser.setApproveButtonText("Save");
					fileChooser.setSelectedFile(new File("composed_simulation_output.txt"));
					
					String filePath;
					File outputFile;
					FileWriter writer;
					StringBuilder infoData = new StringBuilder();
					SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					SimpleDateFormat dateTimeFormatter_ = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
					
					if(fileChooser.showSaveDialog(CompositionGUI.getConPane()) == JFileChooser.APPROVE_OPTION) {
						try {
							filePath = fileChooser.getSelectedFile().getAbsolutePath();
							outputFile = new File(toTxt(filePath));
							
							if(outputFile.isDirectory()) {
								throw new FileNotFoundException();
							}
							if(outputFile.exists()) {
								if(JOptionPane.showConfirmDialog(CompositionGUI.getConPane(), 
																 "Do you want to overwrite the existing file?", 
																 "Overwrite", 
																 JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
									outputFile = new File(fileChooser.getSelectedFile().getParent(), 
														  toTxt("composed_simulation_output_" + dateTimeFormatter_.format(new Date())));
								}
							}
							outputFile.createNewFile();
							infoData.append("Simulation Output timestamp: " + dateTimeFormatter.format(new Date()) + "\n");
							infoData.append("Model path: " + currentModel.getModelPath() + "\n");
							infoData.append("Simulation ID: " + Integer.toString(currentModel.getModelId()) + "\n");
							infoData.append("------------------------------------------------\n\n");
							
							writer = new FileWriter(outputFile);
							writer.write(infoData.toString());
							writer.write(textAreaLog.getText());
							writer.close();
							JOptionPane.showMessageDialog(CompositionGUI.getConPane(), "Composed simulation output saved to file!", "Success", JOptionPane.INFORMATION_MESSAGE);
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(CompositionGUI.getConPane(), "Error: could not save the file!", "Error", JOptionPane.ERROR_MESSAGE);
							//ex.printStackTrace();
						}
					} else {
						fileChooser.cancelSelection();
					}
				}
			}
		});
		
		btnInvManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentModel.getModelId() < 1)
					JOptionPane.showMessageDialog(CompositionGUI.getConPane(), "Error: no simulation selected!", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					InvariantGUI invGUI = new InvariantGUI(CompositionGUI.containerInstance, currentModel.getModelId(), currentModel.getModelName());
					invGUI.setVisible();
					InvariantGUI.frame.setLocationRelativeTo(CompositionGUI.getConPane());
				}	
			}
		});
	}
}
