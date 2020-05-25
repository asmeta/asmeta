package org.asmeta.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.asmeta.assertion_catalog.Frame;
import org.asmeta.runtime_container.ContainerRT;

public class GuiTest {
	
	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();
	static String model = "";
	File file;
	Scanner input;
	
	public List<String> refreshInvariants() throws Exception
	{
		List<String> final_list = new ArrayList<String>();
		ContainerRT imp = ContainerRT.getInstance();
		imp.init(1);
		int id = imp.startExecution(model);
		if(id>0)
			return final_list = imp.viewListInvariant(id);
		else {
			return null;
		}
	}
	
	public String chooseModel() throws Exception {
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			file = fileChooser.getSelectedFile();
			input = new Scanner(file);
			
			while(input.hasNext()) {
				sb.append(input.nextLine());
				sb.append("\n");
			}
			model = file.getAbsolutePath();
			input.close();
		}
		else
		{
			sb.append("");
		}
		return sb.toString();
	}
	
	public static void addInvariant(String s) throws Exception {
		boolean result;
		ContainerRT imp = ContainerRT.getInstance();
		imp.init(1);
		int id = imp.startExecution(model);
		result = imp.addInvariant(id,s);
		if(result)
		{
			Frame.showInvariants();
			Frame.setAllEnabled(1);
		}
		else {
			JOptionPane.showMessageDialog(null, "An error has occurred");
		}
	}
	
	public static void updateInvariant(String new_invariant, String old_invariant) throws Exception {
		   boolean result;
		   ContainerRT imp = ContainerRT.getInstance();
		   imp.init(1);
		   int id = imp.startExecution(model);
		   result = imp.updateInvariant(id,new_invariant,old_invariant);
		   if(result)
			{
				Frame.showInvariants();
				Frame.setAllEnabled(1);
			}
			else {
				JOptionPane.showMessageDialog(null, "An error has occurred");
			}
		  }
	
	
	
	public static void removeInvariant(String s) throws Exception {
		boolean result;
		ContainerRT imp = ContainerRT.getInstance();
		imp.init(1);
		int id = imp.startExecution(model);
		result = imp.removeInvariant(id,s);
		if(result)
		{
			Frame.showInvariants();
			Frame.setAllEnabled(1);
		}
		else {
			JOptionPane.showMessageDialog(null, "An error has occurred");
		}
	}
	
	public String getModel() 
	{
		return model.toString();
	}
	
	
}
