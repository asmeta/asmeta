package org.asmeta.assertion_catalog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;


import org.asmeta.runtime_container.IModelAdaptation;
import org.asmeta.runtime_container.InvariantData;
import org.asmeta.simulationUI.SimGUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 */
public class InvariantGUI {
	public static JFrame frame;
	static List<String> invariantList = new ArrayList<String>();
	//static DefaultListModel<String> model = new DefaultListModel<>();
	static InvariantManager StartGui = new InvariantManager();
	static String selectedInvariant;
	static boolean success;
	static ListSelectionModel cellSelectionModel;
	static String selectedData;
	
	//////////////COMPONENTS//////////////////////
	//static JList<String> list = new JList<>();
	static JButton edit;
	static JButton refresh;
	static JTextPane modelpath;
	static JButton remove;
	static JButton add;
	static JScrollPane scrollPane;
	//static DefaultTableModel mod;
	static JMenuBar menuBar;
	static JMenuItem selectModelMenuItem;
	static JLabel lblModel;
	static ButtonGroup fontSizeGroup;
	//static JTable table;
	static JMenu invManagerMenu;
	static JMenu windowMenu;
	static JCheckBoxMenuItem darkModeCheckItem;
	static JMenu fontSizeMenu;
	static JRadioButtonMenuItem _12fontRadioItem;
	static JRadioButtonMenuItem _18fontRadioItem;
	static JRadioButtonMenuItem _16fontRadioItem;
	static JRadioButtonMenuItem _14fontRadioItem;
	
	private static JTable table;
	private static JTable jTable;
	private static int row = 0;
	private static int column = 0;
	
	//////////////CONTAINER//////////////////////
	static InvariantData invManager;
	static IModelAdaptation containerInstance;
	static int currentLoadedID;
	static String currentLoadedModel;
	
	public static void main(IModelAdaptation contInstanc) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvariantGUI window = new InvariantGUI(contInstanc);
					InvariantGUI.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	

	/**
	 * Create the application.
	 */
	public InvariantGUI(IModelAdaptation contInstanc) {
		initialize();
		containerInstance=contInstanc;
		currentLoadedID=-99;
		currentLoadedModel="";
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public InvariantGUI(IModelAdaptation containerInstance, int currentLoadedID, String currentLoadedModel){
		initialize();
		InvariantGUI.containerInstance=containerInstance;
		InvariantGUI.currentLoadedID=currentLoadedID;
		InvariantGUI.currentLoadedModel=currentLoadedModel;
		showInvariants();
		//modelpath.setText(currentLoadedModel);
		if (currentLoadedModel.indexOf("\\")>=0)
			modelpath.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
		else
			modelpath.setText(currentLoadedModel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImages(SimGUI.icons);
		frame.setBounds(100, 100, 674, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Assertion Catalog");
		frame.setMinimumSize(new Dimension(640, 300));
		frame.setLocationRelativeTo(SimGUI.contentPane);
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		
		// Handle window frame dynamic resizing of components
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
					int frameWidth = e.getComponent().getWidth();
					int frameHeight = e.getComponent().getHeight();
					
					// Handle menuBar resizing
					menuBar.setBounds(new Rectangle(0, 0, frameWidth - 16, 22));
								
					// Handle modelpath and lblModel resizing and replacement
					modelpath.setBounds(new Rectangle(35, 55, frameWidth - 101, 22));
					lblModel.setBounds(new Rectangle(35, 30, 586, 22));
					
					// Handle edit, add, remove, refresh resizing and replacement
					edit.setBounds(new Rectangle(333, frameHeight - 128, 134, 40));
					remove.setBounds(new Rectangle(189, frameHeight - 128, 134, 40));
					add.setBounds(new Rectangle(45, frameHeight - 128, 134, 40));
					refresh.setBounds(new Rectangle(477, frameHeight - 128, 134, 40));
					
					// Handle scrollPane resizing
					scrollPane.setBounds(new Rectangle(35, 90, frameWidth - 101, frameHeight - 243));
			}

			@Override
			public void componentMoved(ComponentEvent e) { return; }
			
			@Override
			public void componentShown(ComponentEvent e) { return; }
			
			@Override
			public void componentHidden(ComponentEvent e) { return; }
		});
		
		
		/////////SET COMPONENTS ON THE FRAME
		modelpath = new JTextPane();
		modelpath.setText("No simulation loaded");
		modelpath.setEditable(false);
		modelpath.setBounds(35, 57, 586, 22);
		modelpath.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		frame.getContentPane().add(modelpath);
		if(!SimGUI.darkMode) {
			modelpath.setBackground(Color.WHITE);
		} else {
			modelpath.setBackground(new Color(40, 40, 40));
		}
		
		lblModel = new JLabel("Loaded model:");
		lblModel.setFont(new Font("Segoe UI", Font.BOLD, SimGUI.fontSize));
		//lblModel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblModel.setHorizontalAlignment(SwingConstants.LEFT);
		lblModel.setBounds(35, 30, 586, 22);
		frame.getContentPane().add(lblModel);
		
		refresh = new JButton("Refresh",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/refresh.png")));
		refresh.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		refresh.setEnabled(false);
		refresh.setBounds(477, 362, 134, 40);
		frame.getContentPane().add(refresh);
		
		remove = new JButton("Remove",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/remove.png")));
		remove.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		remove.setBounds(189, 362, 134, 40);
		frame.getContentPane().add(remove);
		
		add = new JButton("Add",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/add.png")));
		add.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		add.setBounds(45, 362, 134, 40);
		frame.getContentPane().add(add);
		
		edit = new JButton("Edit",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/edit.png")));
		edit.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		edit.setBounds(333, 362, 134, 40);
		frame.getContentPane().add(edit);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 90, 586, 247);
		frame.getContentPane().add(scrollPane);
		
		jTable = new JTable();
		table = new JTable();
		table.setFont(new Font("Consolas", Font.PLAIN, SimGUI.fontSize + 1));
		if(!SimGUI.darkMode) {
			table.setBackground(Color.WHITE);
		} else {
			table.setBackground(new Color(40, 40, 40));
		}
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
		scrollPane.setViewportView(table);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 671, 22);
		frame.getContentPane().add(menuBar);
		if(!SimGUI.darkMode) {
			menuBar.setBackground(new Color(227, 227, 227));
		} else {
			menuBar.setBorder(null);
			menuBar.setBackground(new Color(40, 40, 40));
		}
		
		invManagerMenu = new JMenu("File");
		invManagerMenu.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		menuBar.add(invManagerMenu);
		
		selectModelMenuItem = new JMenuItem("Select model");
		selectModelMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		invManagerMenu.add(selectModelMenuItem);
		
		windowMenu = new JMenu("Window");
		windowMenu.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		menuBar.add(windowMenu);
		
		darkModeCheckItem = new JCheckBoxMenuItem("Dark mode");
		darkModeCheckItem.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		windowMenu.add(darkModeCheckItem);
		darkModeCheckItem.setState(SimGUI.darkMode);
		
		fontSizeMenu = new JMenu("Font size");
		fontSizeMenu.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		windowMenu.add(fontSizeMenu);
		
		fontSizeGroup = new ButtonGroup();
		
		_12fontRadioItem = new JRadioButtonMenuItem("12");
		_12fontRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		fontSizeMenu.add(_12fontRadioItem);
		fontSizeGroup.add(_12fontRadioItem);
		
		_14fontRadioItem = new JRadioButtonMenuItem("14");
		_14fontRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		fontSizeMenu.add(_14fontRadioItem);
		fontSizeGroup.add(_14fontRadioItem);
		
		_16fontRadioItem = new JRadioButtonMenuItem("16");
		_16fontRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		fontSizeMenu.add(_16fontRadioItem);
		fontSizeGroup.add(_16fontRadioItem);
		
		_18fontRadioItem = new JRadioButtonMenuItem("18");
		_18fontRadioItem.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		fontSizeMenu.add(_18fontRadioItem);
		fontSizeGroup.add(_18fontRadioItem);
		
		switch(SimGUI.fontSize) {
			case 14: _14fontRadioItem.setSelected(true); break;
			case 16: _16fontRadioItem.setSelected(true); break;
			case 18: _18fontRadioItem.setSelected(true); break;
			default: _12fontRadioItem.setSelected(true);
		}
	
		_12fontRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimGUI.setProperty("fontSize", "12");
				JOptionPane.showMessageDialog(getContentPane(), "Re-open the application to apply the new font size!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
	
		_14fontRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimGUI.setProperty("fontSize", "14");
				JOptionPane.showMessageDialog(getContentPane(), "Re-open the application to apply the new font size!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
	
		_16fontRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimGUI.setProperty("fontSize", "16");
				JOptionPane.showMessageDialog(getContentPane(), "Re-open the application to apply the new font size!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
	
		_18fontRadioItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimGUI.setProperty("fontSize", "18");
				JOptionPane.showMessageDialog(getContentPane(), "Re-open the application to apply the new font size!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		
		darkModeCheckItem.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				if(darkModeCheckItem.getState()) {
					SimGUI.setProperty("mode", "dark");
					JOptionPane.showMessageDialog(getContentPane(), "Re-open the application to apply the dark mode!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
				} else {
					SimGUI.setProperty("mode", "light");
					JOptionPane.showMessageDialog(getContentPane(), "Re-open the application to disable the dark mode!", "Mode", JOptionPane.INFORMATION_MESSAGE, null);
				}
			}
		});
		
		
		/////////SET BUTTONS AND MENU ACTIONS
		selectModelMenuItem.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   try {	
					   Map<Integer, String> ids = containerInstance.getLoadedIDs();
					   LoadComboItem ci=null;
					   if (!ids.isEmpty()) {
						   setAllEnabled(1);
						   ci = new LoadSelectedSimulation(ids).showDialog();
						   //JOptionPane.showMessageDialog(null, ci.getStr());
					   }
					   if (ci!=null) {
						   currentLoadedID = ci.getInt();
						   currentLoadedModel = ci.getStr();
						   if(!currentLoadedModel.isEmpty() && currentLoadedModel.indexOf(".asm")!=-1 && showInvariants())
						   {
							   add.setEnabled(true);
							   refresh.setEnabled(true);
							   
							   if (currentLoadedModel.indexOf("\\")>=0)
								   modelpath.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
							   else
								   modelpath.setText(currentLoadedModel);
						   }
						   else if(currentLoadedModel.indexOf(".asm")==-1 && !currentLoadedModel.isEmpty())
							   JOptionPane.showMessageDialog(getContentPane(), "Error: wrong extension!", "Error", JOptionPane.ERROR_MESSAGE);
					   }
				   } catch (Exception ex) {
			    		if(ex instanceof java.io.FileNotFoundException)
				    		JOptionPane.showMessageDialog(getContentPane(), "Error: no file selected!", "Error", JOptionPane.ERROR_MESSAGE);
				    	else if(ex instanceof java.lang.NullPointerException) 
				    		JOptionPane.showMessageDialog(getContentPane(), "Error: parser error!", "Error", JOptionPane.ERROR_MESSAGE);  	
				    	else{
				    		ex.printStackTrace();
				    	}
				   }
			   }
		});
		
		table_creationoptions();
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {			
					setAllEnabled(1);
					new AddDialog(invManager).setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setAllEnabled(1);
					new RemoveDialog(selectedInvariant).setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setAllEnabled(1);
					//new AddUpdateDialog(selectedData).setVisible(true);
					if(table.getSelectedColumn() == 0) {
						if(selectedData.trim().equals("<No name>".trim())) 
							new EditDialog("","NAME",selectedInvariant,invManager).setVisible(true);
						else 
							new EditDialog(selectedData,"NAME",selectedInvariant,invManager).setVisible(true);
					}
					else if(table.getSelectedColumn() == 1) 
						new EditDialog(selectedData,"OVER",selectedInvariant,invManager).setVisible(true);
					else 
						new EditDialog(selectedData,"CONTENT",selectedInvariant,invManager).setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInvariants();
			}
		});

	}
	
	public static boolean showInvariants() {
		boolean success = true;
		try {
			setAllEnabled(1);
			//model.removeAllElements();
			
			invManager = StartGui.refreshInvariants(containerInstance, currentLoadedID);
			if (invManager==null) {	//if the current selected sim has been terminated the gui resets
				currentLoadedID=-99;
				currentLoadedModel="";
				setAllEnabled(0);
				((DefaultTableModel) table.getModel()).setRowCount(0);
				modelpath.setText("");
			}else {
				invariantList=invManager.getinvarList();
				
				System.out.println(invariantList);
				if(invariantList==null)
				{
					success=false;
					add.setEnabled(false);
					refresh.setEnabled(false);
				}
				
				//list.setModel(model);
				
				table_creationoptions();
			    
			    
				int i=0;
				while(i<invariantList.size())
				{	
					
					//model.addElement(invariantList.get(i));
					Object [] fila = new Object[3];
					if(invariantList.get(i).trim().substring(10).startsWith("inv_"))
						fila[0] = invariantList.get(i).trim().substring(10,invariantList.get(i).trim().indexOf("over")-1).trim();
					else
						fila[0] = "<No name>";
					fila[1] = invariantList.get(i).substring(invariantList.get(i).trim().indexOf("over")+5,invariantList.get(i).trim().indexOf(':')).trim();
					fila[2] = invariantList.get(i).trim().substring(invariantList.get(i).trim().indexOf(':')+1).trim();
					((DefaultTableModel) table.getModel()).addRow(fila);
					i++;
				}
				
				
				table.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseReleased(MouseEvent e) {
				    	if (e.getClickCount() == 1) {
				            jTable = (JTable)e.getSource();
				            row = jTable.getSelectedRow();
				            column = jTable.getSelectedColumn();
				            selectedData = (String)jTable.getValueAt(row, column);
				            if(((String)jTable.getValueAt(row, 0)).equals("<No name>".trim()))
				            	selectedInvariant = "invariant over "+
					            		(String)jTable.getValueAt(row, 1)+":"+(String)jTable.getValueAt(row, 2);
				            else
					            selectedInvariant = "invariant "+(String)jTable.getValueAt(row, 0)+" over "+
					            		(String)jTable.getValueAt(row, 1)+":"+(String)jTable.getValueAt(row, 2);
				            //System.out.println(selectedInvariant);
				            edit.setEnabled(true);
							remove.setEnabled(true);
				            //System.out.println("DATO: "+valueInCell);
				        }
				    }
				});
				
				
				/*list.getSelectionModel().addListSelectionListener(new ListSelectionListener () {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						selectedInvariant = list.getSelectedValue();
						edit.setEnabled(true);
						remove.setEnabled(true);
					}
					});*/
					setAllEnabled(1);
				}
		     } catch (Exception ex) {
				if(ex instanceof java.io.FileNotFoundException)
					JOptionPane.showMessageDialog(modelpath, "Error: no file selected!", "Error", JOptionPane.ERROR_MESSAGE);
				else if(ex instanceof java.lang.NullPointerException) {
					success=false;
					if(!modelpath.getText().isEmpty())
						JOptionPane.showMessageDialog(modelpath, "Error: parser error!", "Error", JOptionPane.ERROR_MESSAGE);
					modelpath.setText("");
					setAllEnabled(1);
				}
				else {
					ex.printStackTrace();
				}
			}
		return success;
	}
	
	public static void setAllEnabled(int val) {
		if(edit != null && refresh != null && add != null && table != null) {
			edit.setEnabled(false);
			if(val==0) {
				refresh.setEnabled(false);
				add.setEnabled(false);
				if(InvariantGUI.containerInstance.getLoadedIDs().isEmpty()) {
					selectModelMenuItem.setEnabled(false);
				}
				for(MouseListener mouseListener : table.getMouseListeners()) {
					table.removeMouseListener(mouseListener);
				}
			}
			remove.setEnabled(false);
		}
	}
	
	/*private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}*/
	
	
	public static void table_creationoptions()
	{
		//mod.setRowCount(0);
		table.setModel(new DefaultTableModel (new Object[][] {},new String[] {"Name","Over","Content"})
		{
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		table.getTableHeader().setReorderingAllowed(false); 
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		//table.getColumnModel().getColumn(1).setPreferredWidth(100);
		//table.getColumnModel().getColumn(2).setPreferredWidth(scrollPane.getWidth()-
		//		table.getColumnModel().getColumn(0).getWidth()-table.getColumnModel().getColumn(1).getWidth());
		table.setCellSelectionEnabled(true);
		cellSelectionModel = table.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    
	    //table.getColumnModel().getColumn(0).setResizable(false);
	    //table.getColumnModel().getColumn(1).setResizable(false);
	    //table.getColumnModel().getColumn(2).setResizable(false);
	    table.getTableHeader().setReorderingAllowed(false);
	    
	    ///////DISABLE MULTIPLE SELECTION
	    table.setColumnSelectionAllowed(false);
	    table.setRowSelectionAllowed(false);
	    
	    //////DISABLE KEYBOARD ARROWS
	    InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	    im.put(KeyStroke.getKeyStroke("DOWN"), "none");
	    im.put(KeyStroke.getKeyStroke("UP"), "none");
	    im.put(KeyStroke.getKeyStroke("RIGHT"), "none");
	    im.put(KeyStroke.getKeyStroke("LEFT"), "none");
	    
	}
	
	public static void setAddRefreshEnabled() {
		add.setEnabled(true);
		refresh.setEnabled(true);
	}
	
	public static JPanel getContentPane() {
		if(frame != null) {
			return (JPanel) frame.getContentPane();
		} else {
			return null;
		}
	}
	
	public static int getCurrentLoadedID() {
		return InvariantGUI.currentLoadedID;
	}
}
