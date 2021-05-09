package org.asmeta.simulationUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.asmeta.assertion_catalog.InvariantGUI;
import org.asmeta.runtime_container.IModelAdaptation;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class CompositionGUI extends JFrame {
	static JTabbedPane tabbedPane;
	static IModelAdaptation containerInstance;
	static int compCounter;
	
	private static ArrayList<CompositionPanel> tabs = new ArrayList<>();
	
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
	 * Initialize first tab (CompositionPanel) of the tabbed pane.
	 */
	public CompositionGUI(IModelAdaptation contInstance, int senderID, int receiverID) {
		CompositionGUI.containerInstance = contInstance;
		CompositionPanel compositionPane = new CompositionPanel(senderID, receiverID);
		compCounter = 1;
		tabs.add(compositionPane);
		
		setTitle("Composition Monitor");
		setIconImages(SimGUI.icons);
		setBounds(100, 100, 700, 545);
		setMinimumSize(new Dimension(650, 350));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(SimGUI.contentPane);
		
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Composition " + compCounter, getTab(senderID, receiverID));
		tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		setContentPane(tabbedPane);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) { return; }
			
			@Override
			public void windowDeiconified(WindowEvent e) { return; }
			
			@Override
			public void windowDeactivated(WindowEvent e) { return; }
			
			@Override
			public void windowClosing(WindowEvent e) {
				SimGUI.loadedIDs = new ArrayList<>(contInstance.getLoadedIDs().keySet());
				if(SimGUI.loadedIDs.size() > 1) {
					SimGUI.compositionMenuItem.setEnabled(true);
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) { return; }
			
			@Override
			public void windowActivated(WindowEvent e) { return; }
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
			 JOptionPane.showMessageDialog(tabbedPane, "Error: wrong extension!", "Error", JOptionPane.ERROR_MESSAGE);
		 }
		return path;
	}
	
	public static JTabbedPane getConPane() {
		return tabbedPane;
	}
	
	public static CompositionPanel getTab(int tabSenderID, int tabReceiverID) {
		if(!tabs.isEmpty()) {
			for(CompositionPanel tab: tabs) {
				if(tab.getSenderID() == tabSenderID && tab.getReceiverID() == tabReceiverID) {
					return tab;
				}
			}
		}
		return null;
	}
	
	public static void addTab(int tabSenderID, int tabReceiverID) {
		if(tabbedPane != null) {
			compCounter++;
			CompositionPanel newTab = new CompositionPanel(tabSenderID, tabReceiverID);
			tabbedPane.addTab("Composition " + compCounter, newTab);
			tabs.add(newTab);
		}
	}
	
	public static void removeTab(int tabSenderID, int tabReceiverID) {
		if(!tabs.isEmpty()) {
			for(CompositionPanel tab: tabs) {
				if(tab.getSenderID() == tabSenderID && tab.getReceiverID() == tabReceiverID) {
					tabs.remove(tab);
				}
			}
		}
	}
	
	public static void removeTab(CompositionPanel tab) {
		if(!tabs.isEmpty()) {
			tabs.remove(tab);
		}
	}
	
	public static CompositionPanel getFirstTab() {
		if(!tabs.isEmpty()) {
			return tabs.get(0);
		}
		return null;
	}
	
	public static ArrayList<CompositionPanel> getTabList() {
		return tabs;
	}
}
