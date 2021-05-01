package org.asmeta.assertion_catalog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.asmeta.simulationUI.SimGUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Font;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadSelectedSimulation extends JDialog {
	private static JPanel contentPane;
	static JComboBox<LoadComboItem> comboBox;
	static JButton btnLoad;
	static JButton btnCancel;
	static JLabel lblText;
	
	private LoadComboItem ret = null;
	
	public LoadComboItem showDialog() {
		setVisible(true);
		return ret;
	}
	
	public LoadSelectedSimulation(Map<Integer, String> ids) {
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		setResizable(false);
		setIconImages(SimGUI.icons);
		setModal(true);
		setTitle("Load simulation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 212);
		setLocationRelativeTo(InvariantGUI.getContentPane());
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox<LoadComboItem>();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		comboBox.setBounds(36, 56, 360, 30);
		for(Map.Entry<Integer, String> i : ids.entrySet()) {
			comboBox.addItem(new LoadComboItem(i.getKey(),i.getValue()));
		}
		contentPane.add(comboBox);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(299, 127, 97, 25);
		contentPane.add(btnCancel);
		
		btnLoad = new JButton("Load");
		btnLoad.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret=(LoadComboItem)comboBox.getSelectedItem();
				setVisible(false);
				dispose();
			}
		});
		btnLoad.setBounds(190, 127, 97, 25);
		contentPane.add(btnLoad);
		
		lblText = new JLabel("Loaded simulations:");
		lblText.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize + 2));
		lblText.setBounds(36, 26, 360, 22);
		contentPane.add(lblText);
		
	}
}