package org.asmeta.simulationUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
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

import org.asmeta.runtime_container.IModelAdaptation;

public class CompositionGUI extends JFrame {

	private static JPanel contentPane;
	static JLabel lblSender;
	static JTextPane textPaneSender;
	static JLabel lblSimID;
	static JTextPane textPaneSenderID;
	static JScrollPane scrollPane;
	static JTextArea textAreaLog;
	static JTextPane textPaneReceiver;
	static JTextPane textPaneReceiverID;
	static JLabel lblReceiver;
	static JLabel lblSenderID;
	static JLabel lblReceiverID;
	
	static IModelAdaptation containerInstance;
	static int senderID;
	static int receiverID;

	/**
	 * Launch the application.
	 */
	public static void main(IModelAdaptation contInstance, int senderID, int receiverID) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompositionGUI window = new CompositionGUI(contInstance, senderID, receiverID);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CompositionGUI(IModelAdaptation contInstance, int senderID, int receiverID) {
		CompositionGUI.containerInstance = contInstance;
		CompositionGUI.senderID = senderID;
		CompositionGUI.receiverID = receiverID;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Composition Monitor");
		setIconImages(SimGUI.icons);
		setBounds(100, 100, 700, 490);
		setMinimumSize(new Dimension(650, 250));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(SimGUI.contentPane);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
	
		lblSender = new JLabel("Sender model:");
		lblSender.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblSender.setHorizontalAlignment(SwingConstants.LEFT);
		lblSender.setBounds(47, 18, 191, 22);
		contentPane.add(lblSender);
		
		lblReceiver = new JLabel("Receiver model:");
		lblReceiver.setHorizontalAlignment(SwingConstants.LEFT);
		lblReceiver.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblReceiver.setBounds(360, 18, 191, 22);
		contentPane.add(lblReceiver);
		
		lblSenderID = new JLabel("ID:");
		lblSenderID.setHorizontalAlignment(SwingConstants.LEFT);
		lblSenderID.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblSenderID.setBounds(248, 18, 78, 22);
		contentPane.add(lblSenderID);
		
		lblReceiverID = new JLabel("ID:");
		lblReceiverID.setHorizontalAlignment(SwingConstants.LEFT);
		lblReceiverID.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		lblReceiverID.setBounds(561, 18, 78, 22);
		contentPane.add(lblReceiverID);
		
		textPaneSender = new JTextPane();
		textPaneSender.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		if(!SimGUI.darkMode) {
			textPaneSender.setBackground(Color.WHITE);
		} else {
			textPaneSender.setBackground(new Color(40, 40, 40));
		}
		textPaneSender.setText("No sender model loaded");
		textPaneSender.setBounds(47, 45, 191, 22);
		textPaneSender.setEditable(false);
		contentPane.add(textPaneSender);

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
		textPaneSenderID.setBounds(248, 45, 78, 22);
		textPaneSenderID.setEditable(false);
		contentPane.add(textPaneSenderID);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 83, 592, 308);
		contentPane.add(scrollPane);
		
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
		textPaneReceiver.setBounds(360, 45, 191, 22);
		contentPane.add(textPaneReceiver);
		
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
		textPaneReceiverID.setBounds(561, 45, 78, 22);
		contentPane.add(textPaneReceiverID);
		
		if(senderID >= 1 && receiverID >= 1 && containerInstance != null) {
			textPaneSender.setText(CompositionGUI.clearPath(containerInstance.getLoadedIDs().get(senderID)));
			textPaneSenderID.setText(Integer.toString(senderID));
			textPaneReceiver.setText(CompositionGUI.clearPath(containerInstance.getLoadedIDs().get(receiverID)));
			textPaneReceiverID.setText(Integer.toString(receiverID));
		}
		
		// Handle window frame dynamic resizing of components
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				int frameWidth = e.getComponent().getWidth();
				int frameHeight = e.getComponent().getHeight();
						
				// Handle lblSender, lblReceiver, lblSenderID and lblReceiverID resizing
				lblSender.setBounds(new Rectangle(47, 18, 191, 22));
				lblSenderID.setBounds(new Rectangle(Math.round(frameWidth/2 - 102), 18, 78, 22));
				lblReceiver.setBounds(new Rectangle(Math.round(frameWidth/2 + 10), 18, 191, 22));
				lblReceiverID.setBounds(new Rectangle(frameWidth - 139, 18, 78, 22));
				
				// Handle textPaneSender, textPaneSenderID, textPaneReceiver and textPaneReceiverID resizing
				textPaneSender.setBounds(new Rectangle(47, 45, Math.round(frameWidth/2 - 159), 22));
				textPaneSenderID.setBounds(new Rectangle(Math.round(frameWidth/2 - 102), 45, 78, 22));
				textPaneReceiver.setBounds(new Rectangle(Math.round(frameWidth/2 + 10), 45, Math.round(frameWidth/2 - 159), 22));
				textPaneReceiverID.setBounds(new Rectangle(frameWidth - 139, 45, 78, 22));
						
				// Handle scrollPane resizing
				scrollPane.setBounds(new Rectangle(47, 88, frameWidth - 108, frameHeight - 182));
			}
			@Override
			public void componentMoved(ComponentEvent e) { return; }
					
			@Override
			public void componentShown(ComponentEvent e) { return; }

			@Override
			public void componentHidden(ComponentEvent e) { return; }
		});
	}
	
	/**
	 * Helper function to get the asm model filename from its absolute path.
	 * If the absolute path format is incorrect the function returns 
	 * the absolute path unchanged. 
	 * 
	 * @param path: the absolute path of the asm model
	 * @return: the filename of the asm model 
	 */
	public static String clearPath(String path) {
		if(!path.isEmpty() && path.indexOf(".asm") != -1 && path.indexOf("\\") >= 0) {
			return (path.substring(path.lastIndexOf("\\") + 1));
		 } else if(path.indexOf(".asm")==-1 && !path.isEmpty()) {
			 JOptionPane.showMessageDialog(contentPane, "Error: wrong extension!", "Error", JOptionPane.ERROR_MESSAGE);
		 }
		return path;
	}
}
