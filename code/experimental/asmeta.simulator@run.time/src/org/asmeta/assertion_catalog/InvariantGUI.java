package org.asmeta.assertion_catalog;

import java.awt.EventQueue;


import org.asmeta.runtime_container.IModelAdaptation;
import org.asmeta.runtime_container.InvariantData;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class InvariantGUI {

	private JFrame frame;
	static List<String> list_invariant = new ArrayList<String>();
	//static DefaultListModel<String> model = new DefaultListModel<>();
	static InvariantManager StartGui = new InvariantManager();
	static String selected_invariant;
	boolean success;
	static ListSelectionModel cellSelectionModel;
	static String selectedData;
	Font font = new Font(Font.DIALOG, 15, 15);
	
	//////////////COMPONENTS//////////////////////
	//static JList<String> list = new JList<>();
	static JButton edit;
	static JButton refresh;
	static JTextPane modelpath;
	static JButton remove;
	static JButton add;
	static JButton upload;
	static JScrollPane scrollPane;
	private static JTable table;
	static DefaultTableModel mod;
	//static JTable table;
	
	static JTable jTable = new JTable();
	static int row = 0;
	static int column = 0;
	
	//////////////CONTAINER//////////////////////
	static InvariantData inv_manager;
	static IModelAdaptation containerInstance;
	static int currentLoadedID;
	static String currentLoadedModel;
	
	
	
	/**
	 * Launch the applile() {
			public void 
	/**
	 * Launch the applile() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/ 
	public static void main(IModelAdaptation contInstanc) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvariantGUI window = new InvariantGUI(contInstanc);
					window.frame.setVisible(true);
					
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
		frame.setResizable(false);
		frame.setBounds(100, 100, 752, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Assertion Catalog");
		
		/////////SET VISIBILITY OF BUTTONS
		//setAllEnabled(0);
		
		
		/////////SET COMPONENTS ON THE FRAME
		modelpath = new JTextPane();
		modelpath.setEditable(false);
		modelpath.setBounds(35, 26, 476, 28);
		modelpath.setFont(font);
		frame.getContentPane().add(modelpath);
		refresh = new JButton("REFRESH",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/refresh.png")));
		refresh.setEnabled(false);
		refresh.setBounds(543, 352, 134, 40);
		frame.getContentPane().add(refresh);
		remove = new JButton("REMOVE",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/remove.png")));
		remove.setBounds(221, 352, 134, 40);
		frame.getContentPane().add(remove);
		
		add = new JButton("ADD",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/add.png")));
		add.setBounds(60, 352, 134, 40);
		
		frame.getContentPane().add(add);
		
		
		edit = new JButton("EDIT",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/edit.png")));
		edit.setBounds(383, 352, 134, 40);
		frame.getContentPane().add(edit);
		//list.setToolTipText("");
		//list.setBounds(557, 80, 144, 247);
		//frame.getContentPane().add(list);
		upload = new JButton("UPLOAD MODEL",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/upload.png")));
		upload.setBounds(523, 13, 186, 54);
		frame.getContentPane().add(upload);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 80, 674, 247);
		frame.getContentPane().add(scrollPane);
		
		mod = new DefaultTableModel();
		jTable = new JTable();
		table = new JTable(mod);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
		
		scrollPane.setViewportView(table);
		
		
		
		/////////////////////TABLE CREATION AND OPTIONS 
		
		table_creationoptions();
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setBounds(23, 98, 497, 229);
		
		
		/////////SET BUTTONS ACTIONS
		upload.addActionListener(new ActionListener() {
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
				      //modelpath.setText(currentLoadedModel);
	    				if (currentLoadedModel.indexOf("\\")>=0)
							modelpath.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
						else
							modelpath.setText(currentLoadedModel);
				     }
	    			else if(currentLoadedModel.indexOf(".asm")==-1 && !currentLoadedModel.isEmpty())
				    	JOptionPane.showMessageDialog(null, "WRONG EXTENSION");
				    }
	    		}
			    //
		    	
			     //StartGui.chooseModel();
			     //String checkmodel = StartGui.getModel();
			     //new loadingbar().setVisible(true);
			     //modelpath.setText(StartGui.getModel());
			     
			     /*if(!checkmodel.isEmpty() && checkmodel.indexOf(".asm")!=-1 && showInvariants())
			     {
			      add.setEnabled(true);
			      refresh.setEnabled(true);
			      modelpath.setText(StartGui.getModel());
			     }*/
			     /*if(checkmodel.indexOf(".asm")==-1 && !checkmodel.isEmpty())
			    	JOptionPane.showMessageDialog(null, "WRONG EXTENSION");
			    }*/
			    catch (Exception ex) {
				    if(ex instanceof java.io.FileNotFoundException)
				    	JOptionPane.showMessageDialog(null, "NO FILE SELECTED");
				    else if(ex instanceof java.lang.NullPointerException) 
				    	JOptionPane.showMessageDialog(null, "PARSER ERROR");  	
				    else{
				    	ex.printStackTrace();
				    }
			    }
			   }
			  });
		
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {			
					setAllEnabled(1);
					new AddDialog(inv_manager).setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setAllEnabled(1);
					new RemoveDialog(selected_invariant).setVisible(true);
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
							new EditDialog("","NAME",selected_invariant,inv_manager).setVisible(true);
						else 
							new EditDialog(selectedData,"NAME",selected_invariant,inv_manager).setVisible(true);
					}
					else if(table.getSelectedColumn() == 1) 
						new EditDialog(selectedData,"OVER",selected_invariant,inv_manager).setVisible(true);
					else 
						new EditDialog(selectedData,"CONTENT",selected_invariant,inv_manager).setVisible(true);
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
			
			inv_manager = StartGui.refreshInvariants(containerInstance, currentLoadedID);
			if (inv_manager==null) {	//if the current selected sim has been terminated the gui resets
				currentLoadedID=-99;
				currentLoadedModel="";
				setAllEnabled(0);
				((DefaultTableModel) table.getModel()).setRowCount(0);
				modelpath.setText("");
			}else {
				list_invariant=inv_manager.getinvarList();
				
				System.out.println(list_invariant);
				if(list_invariant==null)
				{
					success=false;
					add.setEnabled(false);
					refresh.setEnabled(false);
				}
				
				//list.setModel(model);
				
				table_creationoptions();
			    
			    
				int i=0;
				while(i<list_invariant.size())
				{	
					
					//model.addElement(list_invariant.get(i));
					Object [] fila = new Object[3];
					if(list_invariant.get(i).trim().substring(10).startsWith("inv_"))
						fila[0] = list_invariant.get(i).trim().substring(10,list_invariant.get(i).trim().indexOf("over")-1).trim();
					else
						fila[0] = "<No name>";
					fila[1] = list_invariant.get(i).substring(list_invariant.get(i).trim().indexOf("over")+5,list_invariant.get(i).trim().indexOf(':')).trim();
					fila[2] = list_invariant.get(i).trim().substring(list_invariant.get(i).trim().indexOf(':')+1).trim();
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
				            	selected_invariant = "invariant over "+
					            		(String)jTable.getValueAt(row, 1)+":"+(String)jTable.getValueAt(row, 2);
				            else
					            selected_invariant = "invariant "+(String)jTable.getValueAt(row, 0)+" over "+
					            		(String)jTable.getValueAt(row, 1)+":"+(String)jTable.getValueAt(row, 2);
				            //System.out.println(selected_invariant);
				            edit.setEnabled(true);
							remove.setEnabled(true);
				            //System.out.println("DATO: "+valueInCell);
				        }
				    	}
				    });
				
				
				/*list.getSelectionModel().addListSelectionListener(new ListSelectionListener () {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						selected_invariant = list.getSelectedValue();
						edit.setEnabled(true);
						remove.setEnabled(true);
					}
					});*/
					setAllEnabled(1);
				}
		     } catch (Exception ex) {
				if(ex instanceof java.io.FileNotFoundException)
					JOptionPane.showMessageDialog(null, "NO FILE SELECTED");
				else if(ex instanceof java.lang.NullPointerException) {
					success=false;
					if(!modelpath.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "PARSER ERROR");
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
		edit.setEnabled(false);
		if(val==0) {
			refresh.setEnabled(false);
			add.setEnabled(false);
		}
		remove.setEnabled(false);
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
		mod.setRowCount(0);
		table.setModel(new DefaultTableModel (new Object[][] {},new String[] {"Name","Over","Content"})
		{
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		table.getTableHeader().setReorderingAllowed(false); 
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(scrollPane.getWidth()-
				table.getColumnModel().getColumn(0).getWidth()-table.getColumnModel().getColumn(1).getWidth());
		table.setCellSelectionEnabled(true);
		cellSelectionModel = table.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    
	    //////COLUMN SIZE 
	    table.getColumnModel().getColumn(0).setResizable(false);
	    table.getColumnModel().getColumn(1).setResizable(false);
	    table.getColumnModel().getColumn(2).setResizable(false);
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
	
	public static void setAddRefreshEnabled()
	{
		add.setEnabled(true);
		refresh.setEnabled(true);
	}
}
