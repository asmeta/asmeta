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

public class CompositionPanel extends JPanel {
	JLabel lblSender;
	JTextPane textPaneSender;
	JLabel lblSimID;
	JTextPane textPaneSenderID;
	JScrollPane scrollPane;
	JTextArea textAreaLog;
	JTextPane textPaneReceiver;
	JTextPane textPaneReceiverID;
	JLabel lblReceiver;
	JLabel lblSenderID;
	JLabel lblReceiverID;
	JButton btnSave;
	JButton btnInvManager;
	JButton btnCompose;
	JButton btnClear;
	
	Composition currentComposition;
	
	public CompositionPanel(int senderID, int receiverID) {
		currentComposition = new Composition(senderID, receiverID);
		CompositionGUI.compositionContainer.addComposition(currentComposition);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		
		lblSender = new JLabel("Previous model:");
		lblSender.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblSender.setHorizontalAlignment(SwingConstants.LEFT);
		lblSender.setBounds(47, 11, 191, 22);
		add(lblSender);
		
		lblReceiver = new JLabel("Current model:");
		lblReceiver.setHorizontalAlignment(SwingConstants.LEFT);
		lblReceiver.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblReceiver.setBounds(360, 11, 191, 22);
		add(lblReceiver);
		
		lblSenderID = new JLabel("ID:");
		lblSenderID.setHorizontalAlignment(SwingConstants.LEFT);
		lblSenderID.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblSenderID.setBounds(248, 11, 78, 22);
		add(lblSenderID);
		
		lblReceiverID = new JLabel("ID:");
		lblReceiverID.setHorizontalAlignment(SwingConstants.LEFT);
		lblReceiverID.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblReceiverID.setBounds(561, 11, 78, 22);
		add(lblReceiverID);
		
		textPaneSender = new JTextPane();
		textPaneSender.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		if(!SimGUI.darkMode) {
			textPaneSender.setBackground(Color.WHITE);
		} else {
			textPaneSender.setBackground(new Color(40, 40, 40));
		}
		textPaneSender.setText("No sender model loaded");
		textPaneSender.setBounds(47, 38, 191, 22);
		textPaneSender.setEditable(false);
		add(textPaneSender);

		textPaneSenderID = new JTextPane();
		textPaneSenderID.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		textPaneSenderID.getStyledDocument().setParagraphAttributes(0, textPaneSenderID.getStyledDocument().getLength(), center, false);
		if(!SimGUI.darkMode) {
			textPaneSenderID.setBackground(Color.WHITE);
		} else {
			textPaneSenderID.setBackground(new Color(40, 40, 40));
		}
		textPaneSenderID.setText("X");
		textPaneSenderID.setBounds(248, 38, 78, 22);
		textPaneSenderID.setEditable(false);
		add(textPaneSenderID);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 69, 592, 308);
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
		
		textPaneReceiver = new JTextPane();
		textPaneReceiver.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		if(!SimGUI.darkMode) {
			textPaneReceiver.setBackground(Color.WHITE);
		} else {
			textPaneReceiver.setBackground(new Color(40, 40, 40));
		}
		textPaneReceiver.setText("No receiver model loaded");
		textPaneReceiver.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		textPaneReceiver.setEditable(false);
		textPaneReceiver.setBounds(360, 38, 191, 22);
		add(textPaneReceiver);
		
		textPaneReceiverID = new JTextPane();
		if(!SimGUI.darkMode) {
			textPaneReceiverID.setBackground(Color.WHITE);
		} else {
			textPaneReceiverID.setBackground(new Color(40, 40, 40));
		}
		textPaneReceiverID.setText("X");
		textPaneReceiverID.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		textPaneReceiverID.getStyledDocument().setParagraphAttributes(0, textPaneReceiverID.getStyledDocument().getLength(), center, false);
		textPaneReceiverID.setEditable(false);
		textPaneReceiverID.setBounds(561, 38, 78, 22);
		add(textPaneReceiverID);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnSave.setEnabled(false);
		btnSave.setBounds(317, 402, 120, 40);
		add(btnSave);
		
		btnCompose = new JButton("Compose");
		btnCompose.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnCompose.setEnabled(false);
		btnCompose.setBounds(187, 402, 120, 40);
		add(btnCompose);
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnClear.setEnabled(false);
		btnClear.setBounds(447, 402, 120, 40);
		add(btnClear);
		
		btnInvManager = new JButton("Invariants");
		btnInvManager.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnInvManager.setEnabled(false);
		btnInvManager.setBounds(57, 402, 120, 40);
		add(btnInvManager);
		
		if(senderID >= 1 && receiverID >= 1 && CompositionGUI.containerInstance != null) {
			textPaneSender.setText(CompositionGUI.clearPath(currentComposition.getSenderModel()));
			textPaneSenderID.setText(Integer.toString(senderID));
			textPaneReceiver.setText(CompositionGUI.clearPath(currentComposition.getReceiverModel()));
			textPaneReceiverID.setText(Integer.toString(receiverID));
			
			btnInvManager.setEnabled(true);
			btnSave.setEnabled(true);
			btnClear.setEnabled(true);
			
			if(SimGUI.getMaxInstances() >= 3) {
				btnCompose.setEnabled(true);
			}
		}
		
		// Handle window frame dynamic resizing of components
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				int tabWidth = e.getComponent().getWidth();
				int tabHeight = e.getComponent().getHeight();
				
				// Handle lblSender, lblReceiver, lblSenderID and lblReceiverID resizing
				lblSender.setBounds(new Rectangle(47, 11, 191, 22));
				lblSenderID.setBounds(new Rectangle(Math.round(tabWidth/2 - 94), 11, 78, 22));
				lblReceiver.setBounds(new Rectangle(Math.round(tabWidth/2 + 18), 11, 191, 22));
				lblReceiverID.setBounds(new Rectangle(tabWidth - 123, 11, 78, 22));
				
				// Handle textPaneSender, textPaneSenderID, textPaneReceiver and textPaneReceiverID resizing
				textPaneSender.setBounds(new Rectangle(47, 38, Math.round(tabWidth/2 - 151), 22));
				textPaneSenderID.setBounds(new Rectangle(Math.round(tabWidth/2 - 94), 38, 78, 22));
				textPaneReceiver.setBounds(new Rectangle(Math.round(tabWidth/2 + 18), 38, Math.round(tabWidth/2 - 151), 22));
				textPaneReceiverID.setBounds(new Rectangle(tabWidth - 123, 38, 78, 22));
					
				// Handle scrollPane resizing
				scrollPane.setBounds(new Rectangle(47, 69, tabWidth - 92, tabHeight - 165));
				
				// Handle btnInvManager, btnCompose, btnSave and btnClear resizing and replacement
				if(tabWidth < 989) {
					btnInvManager.setBounds(new Rectangle(57, tabHeight - 71, 120, 40));
					btnCompose.setBounds(new Rectangle(187, tabHeight - 71, 120, 40));
					btnSave.setBounds(new Rectangle(317, tabHeight - 71, 120, 40));
					btnClear.setBounds(new Rectangle(447, tabHeight - 71, 120, 40));
				} else {
					btnInvManager.setBounds(new Rectangle(57, tabHeight - 71, 120, 50));
					btnCompose.setBounds(new Rectangle(197, tabHeight - 71, 120, 50));
					btnSave.setBounds(new Rectangle(327, tabHeight - 71, 120, 50));
					btnClear.setBounds(new Rectangle(457, tabHeight - 71, 120, 50));
				}
			}
			
			@Override
			public void componentMoved(ComponentEvent e) { return; }
					
			@Override
			public void componentShown(ComponentEvent e) { 
				if(SimGUI.loadedIDs.size() <= 1) {
					btnCompose.setEnabled(false);
				}
			}

			@Override
			public void componentHidden(ComponentEvent e) { return; }
		});
		
		// Support multiple composition (chain)
		btnCompose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SimGUI.loadedIDs.remove((Object) currentComposition.getReceiverID());
					int newReceiverID = (int) JOptionPane.showInputDialog(CompositionGUI.getConPane(), 
																	   	  "  Select the ID of the model that will be\ncomposed with the current loaded model:",
																	   	  "Receiver ID",
																	   	  JOptionPane.QUESTION_MESSAGE,
																	   	  null,
																	   	  SimGUI.loadedIDs.toArray(),
																	   	  null);
					CompositionGUI.addTab(currentComposition.getReceiverID(), newReceiverID);
					if(SimGUI.loadedIDs.size() <= 1) {
						btnCompose.setEnabled(false);
					}
				} catch(Exception ex) {
					return;
				}
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentComposition.getSenderID() >= 1 && currentComposition.getReceiverID() >= 1 && textAreaLog.getText() != null) {
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
				if(currentComposition.getSenderID() >= 1 && textAreaLog.getText() != null && currentComposition.getReceiverID() >= 1) {
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
							infoData.append("Model path: " + currentComposition.getReceiverModel() + "\n");
							infoData.append("Simulation ID: " + Integer.toString(currentComposition.getReceiverID()) + "\n");
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
				if(currentComposition.getReceiverID() < 1)
					JOptionPane.showMessageDialog(CompositionGUI.getConPane(), "Error: no simulation selected!", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					InvariantGUI invGUI = new InvariantGUI(CompositionGUI.containerInstance, currentComposition.getReceiverID(), currentComposition.getReceiverModel());
					invGUI.setVisible();
					InvariantGUI.frame.setLocationRelativeTo(CompositionGUI.getConPane());
				}	
			}
		});
	}
}
