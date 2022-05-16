package org.asmeta.nusmv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

class AsmInfo {
	String outputRunNuSMV;

	AsmInfo(String asm) throws Exception {
		AsmetaSMV as = new AsmetaSMV(asm);
		as.translation();
		as.createNuSMVfile();
		String[] cmdarray = { "NuSMV", "-is", "-r", as.smvFileName};
		as.runNuSMV(Arrays.asList(cmdarray));
		outputRunNuSMV = as.getOutput(Arrays.asList(cmdarray));
	}

	void getNumberOfReachableStates() {
		System.out.println(outputRunNuSMV);
		int j = outputRunNuSMV.indexOf("system diameter: ");
		String[] str = outputRunNuSMV.substring(j).split("[ \n]");
		String diameter = str[2];
		String reachableState = str[5];
		String reachableStatePower = str[6].replaceAll("[()]", "");
		String[] reachableStatePowerBaseEsp = reachableStatePower.split("\\^");
		String totalState = str[9];
		String totalStatePower = str[10].replaceAll("[()]", "");
		String[] totalStatePowerBaseEsp = totalStatePower.split("\\^");
		System.out.println("Diameter " + diameter);
		System.out.println("There are " + reachableState 
				+ " reachable states (" + reachableStatePowerBaseEsp[0] +
				"^" + reachableStatePowerBaseEsp[1] + ")");
		System.out.println("There are " + totalState
				+ " total states (" + totalStatePowerBaseEsp[0] +
				"^" + totalStatePowerBaseEsp[1] + ")");
	}

	public static void main(String[] args) throws Exception {
		//(new AsmInfo("examples/abstract.asm")).getNumberOfReachableStates();
		//(new AsmInfo("examples/taxi.asm")).getNumberOfReachableStates();
		foo();
	}
	
	static String foo() throws IOException {
		String[] cmdarray = {"NuSMV","-int","examples/abstract.smv"};
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(cmdarray);
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = proc.getInputStream();
		InputStream errorStream = proc.getErrorStream();
		//OutputStream outputStream = proc.getOutputStream();
		InputStreamReader inputStreamR = new InputStreamReader(inputStream);
		InputStreamReader errorStreamR = new InputStreamReader(errorStream);
		//OutputStreamWriter outputStreamW = new OutputStreamWriter(outputStream);
		BufferedReader brInput = new BufferedReader(inputStreamR);
		BufferedReader brError = new BufferedReader(errorStreamR);
		//BufferedWriter bWOutput = new BufferedWriter(outputStreamW);
		String str = brInput.readLine();
		int i=0;
		while (str != null) {
			sb.append(str + "\n");
			str = brInput.readLine();
			//System.out.println("-"+str+"-");
			i++;
			/*if(i==11) {
				System.out.println("go");
				bWOutput.write("go");
				bWOutput.newLine();
				bWOutput.flush();
				
				
			}
			if(i==12) {
				System.out.println("show_vars");
				bWOutput.write("show_vars");
				bWOutput.newLine();
				bWOutput.flush();
				
				
			}*/
		}
		str = brError.readLine();
		//boolean error;
		if (str != null) {
			//error = true;
			sb.append("\nNuSMV has outputted the following output:\n");
		} else {
			//error = false;
		}
		while (str != null) {
			sb.append(str + "\n");
			str = brError.readLine();
		}
		// if(error) throw new Exception(sb.toString());
		brInput.close();
		brError.close();
		return sb.toString();
	}
}