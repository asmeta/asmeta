package org.asmeta.assertion_catalog;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 457, 171);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(RemoveDialog.DISPOSE_ON_CLOSE);
		JLabel sure_message = new JLabel("Are you sure?");
		sure_message.setBounds(168, 13, 89, 29);
		contentPane.add(sure_message);
		this.setTitle("Remove Invariant");
		
		JButton yes = new JButton("Yes",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/yes.png")));
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					InvariantManager.removeInvariant(s,InvariantGUI.containerInstance,InvariantGUI.currentLoadedID);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		yes.setBounds(66, 61, 114, 37);
		contentPane.add(yes);
		
		JButton no = new JButton("No",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/cancel.png")));
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InvariantGUI.setAddRefreshEnabled();
				dispose();
			}
		});
		no.setBounds(248, 61, 114, 37);
		contentPane.add(no);
	}

}