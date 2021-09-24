/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class Utility {
	
	private static Properties properties;
	
  	static{
		try {
			if (properties == null){
				properties = new Properties();
				properties.load(new FileInputStream(Enforcer.getConfigFile()));
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getProperty (String key){
		String result = properties.getProperty(key); 
		if (result == null)
			  throw new IllegalArgumentException(key.toUpperCase() + " name not found!");
		return result;		
	}	

	public static String getProperty (String propertiesFile, String key){
		
		String result = properties.getProperty(propertiesFile+key); 
		if (result == null)
			  throw new IllegalArgumentException(key.toUpperCase() + " name not found!");
		return result;		
	}	

	
	public static void exportToFile(String fileName, String output, boolean append){
		try {
			FileWriter writer = new FileWriter(fileName, append);
			writer.append(output +"\n");
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String readFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (!file.exists())
			throw new FileNotFoundException("File "+ fileName +" does not exist!. Please fix this error.\n");
		
		
		StringBuilder model = new StringBuilder(100);
		BufferedReader bfr = null;

		try {
			bfr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bfr.readLine()) != null) {
				model.append(line + "\n");
			}
			return model.toString();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}	


	
	public static boolean fileExists(String fileName, boolean isDirectory){
		File file = new File(fileName);
		if (!file.exists())
			return false;
		if (isDirectory && !file.isDirectory())
			return false;
		return true;
	}

	
	public static void fileCreate(String fileName, boolean isDirectory) throws IOException{
		File file = new File(fileName);
		if (!file.exists()){
			if (isDirectory)
				Files.createDirectory(Paths.get(fileName));
			else
				Files.createFile(Paths.get(fileName));
		}
	}
	
	


}
