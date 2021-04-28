package org.asmeta.assertion_catalog;

import javax.swing.JPanel;
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
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public RemoveDialog(String s) {
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
		JLabel sure_message = new JLabel("Are you sure?");
		sure_message.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		sure_message.setBounds(116, 11, 80, 29);
		contentPane.add(sure_message);
		this.setTitle("Remove Invariant");
		
		JButton yes = new JButton("Yes",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/yes.png")));
		yes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
		
		JButton no = new JButton("No",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/cancel.png")));
		no.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
