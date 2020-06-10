package org.asmeta.assertion_catalog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Font;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadSelectedSimulation extends JDialog {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	private ComboItem ret=null;
	public ComboItem showDialog() {
		setVisible(true);
		return ret;
	}
	
	public LoadSelectedSimulation(Map<Integer, String> ids) {
		setModal(true);
		setTitle("Load simulation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 212);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(36, 56, 360, 30);
		for(Map.Entry<Integer, String> i : ids.entrySet()) {
			comboBox.addItem(new ComboItem(i.getKey(),i.getValue()));
		}
		contentPane.add(comboBox);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(299, 127, 97, 25);
		contentPane.add(btnCancel);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret=(ComboItem)comboBox.getSelectedItem();
				setVisible(false);
				dispose();
			}
		});
		btnLoad.setBounds(190, 127, 97, 25);
		contentPane.add(btnLoad);
		
		JLabel lblLabel = new JLabel("Loaded simulations:");
		lblLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLabel.setBounds(36, 26, 153, 16);
		contentPane.add(lblLabel);
		
	}
}

class ComboItem
{
    private int i;
    private String s;

    public ComboItem(int id, String model)
    {
        this.i = id;
        this.s = model;
    }

    @Override
    public String toString()
    {
        return "ID: "+i+" Model: "+showName();
    }
    
    private String showName() {
    	return s.substring(s.lastIndexOf('/')+1, s.length());
    }
    
    public int getInt()
    {
        return i;
    }

    public String getStr()
    {
        return s;
    }
}