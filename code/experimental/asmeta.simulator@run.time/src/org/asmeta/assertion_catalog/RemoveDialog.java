package org.asmeta.assertion_catalog;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.asmeta.simulationUI.SimGUI;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class RemoveDialog extends JDialog {
	private static JPanel contentPane;
	static JButton no;
	static JButton yes;
	static JLabel sureMessage;
	
	/**
	 * Create the frame.
	 */
	public RemoveDialog(String s) {
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		setModal(true);
		setResizable(false);
		setIconImages(SimGUI.icons);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 171);
		setLocationRelativeTo(InvariantGUI.getContentPane());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(RemoveDialog.DISPOSE_ON_CLOSE);
		
		sureMessage = new JLabel("Are you sure?");
		sureMessage.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		sureMessage.setBounds(116, 11, 176, 29);
		contentPane.add(sureMessage);
		setTitle("Remove Invariant");
		
		yes = new JButton("Yes",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/yes.png")));
		yes.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					InvariantManager.removeInvariant(s,InvariantGUI.containerInstance,InvariantGUI.currentLoadedID);
					dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		yes.setBounds(10, 61, 114, 37);
		contentPane.add(yes);
		
		no = new JButton("No",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/cancel.png")));
		no.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InvariantGUI.setAddRefreshEnabled();
				dispose();
			}
		});
		no.setBounds(178, 61, 114, 37);
		contentPane.add(no);
	}

}
