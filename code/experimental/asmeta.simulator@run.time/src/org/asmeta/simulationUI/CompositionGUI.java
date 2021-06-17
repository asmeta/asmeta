package org.asmeta.simulationUI;

/**
 * @author Michele Zenoni
 */
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import org.asmeta.runtime_container.IModelExecution;
import org.asmeta.runtime_container.SimulationContainer;

public class CompositionGUI extends JFrame {
	static JTabbedPane tabbedPane;
	static SimulationContainer containerInstance;
	static CompositionContainer compositionContainer;
	static int compCounter;
	static CompositionType compType;
	
	private static Map<Composition, CompositionPanel> compositionTabs;
	
	/**
	 * Launch the application.
	 */
	public static void main(IModelExecution contInstance, int senderID, int receiverID) {
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
	public CompositionGUI(IModelExecution contInstance, int senderID, int receiverID) {
		containerInstance = (SimulationContainer) contInstance;
		compositionTabs = new HashMap<Composition, CompositionPanel>();
		if(compType == null) {
			compType = CompositionType.PIPE;
		}
		compositionContainer = new CompositionContainer(contInstance, compType, SimGUI.simConsole);
		CompositionPanel compositionPane = new CompositionPanel(senderID, receiverID);
		compCounter = 1;
		compositionTabs.put(compositionPane.currentComposition, compositionPane);
		
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
			public void windowOpened(WindowEvent e) { return; }
			
			@Override
			public void windowIconified(WindowEvent e) { return; }
			
			@Override
			public void windowDeiconified(WindowEvent e) { return; }
			
			@Override
			public void windowDeactivated(WindowEvent e) { return; }
			
			@Override
			public void windowClosing(WindowEvent e) {
				SimGUI.loadedIDs = new ArrayList<>(containerInstance.getLoadedIDs().keySet());
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
		if(!compositionTabs.isEmpty() && compositionTabs != null && compositionContainer != null) {
			return compositionTabs.get(compositionContainer.getComposition(tabSenderID, tabReceiverID));
		}
		return null;
	}
	
	public static void addTab(int tabSenderID, int tabReceiverID) {
		if(tabbedPane != null) {
			compCounter++;
			CompositionPanel newTab = new CompositionPanel(tabSenderID, tabReceiverID);
			tabbedPane.addTab("Composition " + compCounter, newTab);
			compositionTabs.put(newTab.currentComposition, newTab);
		}
	}
	
	public static void removeTab(int tabSenderID, int tabReceiverID) {
		if(compositionTabs != null && !compositionTabs.isEmpty() && compositionContainer != null) {
			compositionTabs.remove(compositionContainer.getComposition(tabSenderID, tabReceiverID));
		}
	}
	
	public static void removeTab(CompositionPanel tab) {
		if(compositionTabs != null && !compositionTabs.isEmpty() && compositionContainer != null) {
			compositionTabs.remove(tab.currentComposition);
		}
	}
	
	public static CompositionPanel getFirstTab() {
		if(compositionTabs != null && !compositionTabs.isEmpty() && compositionContainer != null) {
			return compositionTabs.get(compositionContainer.getFirstComposition());
		}
		return null;
	}
	
	public static Map<Composition, CompositionPanel> getCompositionTabs() {
		return compositionTabs;
	}
}
