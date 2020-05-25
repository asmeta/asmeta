package org.asmeta.test;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestRuntimeParser {

	public static void main(String[] args) {
		System.out.println("input: " + String.join(" ", args));
		Scanner scanTest = new Scanner (String.join(" ", args));
		System.out.println(scanTest.next()); //nome funzione da usare
		
		Pattern p;
		Matcher m;
		//System.out.println(scanTest.next("/(-id\\s\\d*)/g"));
		
		//parsing id
		p = Pattern.compile("(-id\\s\\d+)");
		m = p.matcher(String.join(" ", args));
		if (m.find())
			System.out.println(m.group(1)); 
		//parsing max
		p = Pattern.compile("(-max\\s\\d+)");
		m = p.matcher(String.join(" ", args));
		if (m.find())
			System.out.println(m.group(1));
		//parsing modelpath
		p = Pattern.compile("(-modelpath\\s\'.[^\']+\')");
		m = p.matcher(String.join(" ", args));
		if (m.find())
			System.out.println(m.group(1));
	}

}
