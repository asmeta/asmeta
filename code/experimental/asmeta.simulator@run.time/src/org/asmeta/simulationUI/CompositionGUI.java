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
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.asmeta.runtime_composer.AsmetaModel;
import org.asmeta.runtime_composer.CompositionManager;
import org.asmeta.runtime_composer.CompositionRunType;
import org.asmeta.runtime_composer.CompositionTreeNode;
import org.asmeta.runtime_container.SimulationContainer;

public class CompositionGUI extends JFrame {
	static JTabbedPane tabbedPane;
	static SimulationContainer containerInstance;
	static CompositionManager compositionManager;
	static int modelCounter;

	private static Map<AsmetaModel, CompositionPanel> compositionTabs;

	/**
	 * Launch the application.
	 */
	public static void main(SimulationContainer contInstance, CompositionTreeNode compositionTree, JPanel contentPane) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompositionGUI window = new CompositionGUI(contInstance, compositionTree, contentPane);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize tabs (CompositionPanel) of the tabbed pane.
	 */
	public CompositionGUI(SimulationContainer contInstance, CompositionTreeNode compositionTree, JPanel contentPane) {
		containerInstance = contInstance;
		compositionTabs = new HashMap<AsmetaModel, CompositionPanel>();
		compositionManager = new CompositionManager(compositionTree, SimGUI.simConsole, true,
				CompositionRunType.SimGUI, contentPane);
		modelCounter = 0;

		setTitle("Composition Monitor");
		setIconImages(SimGUI.icons);
		setBounds(100, 100, 700, 545);
		setMinimumSize(new Dimension(650, 350));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(SimGUI.contentPane);

		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));

		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		setContentPane(tabbedPane);

		for (int i = 1; i < compositionTree.getAllChildrenModels().size(); i++) {
			AsmetaModel compositionModel = compositionManager.getModelFromModelList(
					compositionTree.getAllChildrenModels().get(i).getModelName(), contInstance.getSimulatorId());
			if (compositionModel != null) {
				addTab(compositionModel.getModelId(), compositionModel.getSimulatorId());
			}
		}

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				return;
			}

			@Override
			public void windowIconified(WindowEvent e) {
				return;
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				return;
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				return;
			}

			@Override
			public void windowClosing(WindowEvent e) {
				SimGUI.loadedIDs = new ArrayList<>(containerInstance.getLoadedIDs().keySet());
				if (SimGUI.loadedIDs.size() > 1) {
					SimGUI.compositionMenuItem.setEnabled(true);
					SimGUI.currentSimulationMenuItem.setEnabled(true);
					SimGUI.compositionTypeMenu.setEnabled(true);
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				return;
			}

			@Override
			public void windowActivated(WindowEvent e) {
				return;
			}
		});
	}

	public static JTabbedPane getConPane() {
		return tabbedPane;
	}

	public static CompositionPanel getTab(int tabModelID, int tabSimContainerID) {
		if (compositionTabs != null && !compositionTabs.isEmpty() && compositionManager != null) {
			return compositionTabs.get(compositionManager.getModelFromModelList(tabModelID, tabSimContainerID));
		}
		return null;
	}

	public static void addTab(int tabModelID, int tabSimContainerID) {
		if (tabbedPane != null) {
			modelCounter++;
			CompositionPanel newTab = new CompositionPanel(
					compositionManager.getModelFromModelList(tabModelID, tabSimContainerID));
			tabbedPane.addTab("Model #" + modelCounter, newTab);
			compositionTabs.put(newTab.currentModel, newTab);
		}
	}

	public static void removeTab(int tabModelID, int tabSimContainerID) {
		if (compositionTabs != null && !compositionTabs.isEmpty() && compositionManager != null) {
			compositionTabs.remove(compositionManager.getModelFromModelList(tabModelID, tabSimContainerID));
		}
	}

	public static void removeTab(CompositionPanel tab) {
		if (compositionTabs != null && !compositionTabs.isEmpty() && compositionManager != null) {
			compositionTabs.remove(tab.currentModel);
		}
	}

	public static Map<AsmetaModel, CompositionPanel> getCompositionTabs() {
		return compositionTabs;
	}

	/**
	 * Helper function to get the asm model filename from its absolute path. If the
	 * absolute path format is incorrect the function returns the absolute path
	 * unchanged.
	 * 
	 * @param path: the absolute path of the asm model
	 * @return: the filename of the asm model
	 */
	public static String clearPath(String path) {
		if (!path.isEmpty() && path.indexOf(".asm") != -1 && path.indexOf("\\") >= 0) {
			return (path.substring(path.lastIndexOf("\\") + 1));
		} else if (!path.isEmpty() && path.indexOf(".asm") != -1 && path.indexOf("/") >= 0) {
			return (path.substring(path.lastIndexOf("/") + 1));
		} else if (path.indexOf(".asm") == -1 && !path.isEmpty()) {
			JOptionPane.showMessageDialog(tabbedPane, "Error: wrong extension!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return path;
	}
}
