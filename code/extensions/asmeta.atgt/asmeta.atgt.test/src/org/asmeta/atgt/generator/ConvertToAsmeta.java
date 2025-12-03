package org.asmeta.atgt.generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ConvertToAsmeta {

	@Test
	public void convertPHDToASMETAFlat() throws Exception {
		BufferedReader fin = new BufferedReader(new FileReader("sequences_asm_v4.txt"));
		String s = "";
		int i=0;
		for (; (s=fin.readLine())!=null; i++) {
			if (i>0) {
				if (s.startsWith("Set of Sequences :")) {
					break;
				}
				s = s.toUpperCase();
				String[] st = s.split(",\t");
				System.out.println("rule r_"+i+" = if status = "+st[0]+" and transition = "+st[1]
						//+ "\n\t\t\t"
						+" then par status := "+st[2]+" message := MSG_"+st[3].replace(" ", "_")+" endpar endif");
			}
		}
		fin.close();
		for (int j=1; j<i; j++) {
			System.out.print("r_"+j+"[] ");
		}
	}

	public String getASMETAFlatFromFSM(String fsmPath, String asmName) throws Exception {
		Set<String> states = new HashSet<>();
		Set<String> transitions = new HashSet<>();
		Set<String> messages = new HashSet<>();
		String res = "";
		BufferedReader fin = new BufferedReader(new FileReader(fsmPath));
		String s = "";
		int i=0;
		for (; (s=fin.readLine())!=null; i++) {
			if (i>0) {
				if (s.startsWith("Set of Sequences :")) {
					break;
				}
				s = s.toUpperCase();
				String[] st = s.split(",\t");
				System.out.println(Arrays.toString(st)+" "+s+" "+i);
				String state = st[0].trim(), transition = st[1].trim(), message = "MSG_"+st[3].replace(" ", "_").trim();
				states.add(state);
				transitions.add(transition);
				messages.add(message);
				res+= "rule r_"+i+" = if status = "+state+" and transition = "+transition
						//+ "\n\t\t\t"
						+" then par status := "+st[2]+" message := "+message+" endpar endif\n";
			}
		}
		fin.close();
		String header = "asm "+asmName+"\n" +
				"\n" +
				"import StandardLibrary\n" +
				"import CTLlibrary\n" +
				"\n" +
				"signature:\n" +
				"	// DOMAINS\n" +
				"	enum domain Status = "+splitList(states, " | ")+"\n"+
				"	enum domain Transition = "+splitList(transitions, " | ")+"\n"+
				"	enum domain Message = "+splitList(messages, " | ")+"\n"+
				"// FUNCTIONS\n" +
				"	controlled status: Status\n" +
				"	monitored transition: Transition //row chosen by the user\n" +
				"	controlled message: Message\n" +
				"	\n" +
				"definitions:\n" +
				"	// DOMAIN DEFINITIONS\n" +
				"	\n" +
				"	";


		res = header+res;
		res += "main rule r_Main = par ";
		for (int j=1; j<i; j++) {
			res+="r_"+j+"[] ";
		}
		res += "endpar\n\n";
		res+="// INITIAL STATE\n" +
				"default init s0:\n" +
				"	function status = UNASSOCIATED\n" +
				"	function transition = RX_ABRT\n" +
				"";
		return res;
	}

	public static String splitList(Collection<String> l, String sep) {
		String res = "{";
		for (String s : l) {
			res += s+sep;
		}
		return res.substring(0, res.length()-sep.length())+"}";
	}

	@Test
	public void convertPHDToASMETAStruct() throws Exception {
		BufferedReader fin = new BufferedReader(new FileReader("antidote-manager.txt"));
		String s = "";
		String status = "";
		String mainRule = "main rule r_Main =\n par\n";
		String rule = "";
		for (int i=0; (s=fin.readLine())!=null; i++) {
			if (i>0) {
				s = s.toUpperCase();
				String[] st = s.split(",\t");
				if (!status.equals(st[0])){
					status = st[0];
					System.out.println(rule + "\nendpar");
					mainRule+= "\nif status = "+st[0]+" then r_" + st[0] + "[] endif\n";
					rule = "rule r_"+st[0] + "=\n\tpar\n\t";
				}
				rule+= ("if transition = "+st[1]
						//+ "\n\t\t\t"
						+" then par status := "+st[2]+" message := MSG_"+st[3].replace(" ", "_")+" endpar endif\n");
			}
		}
		fin.close();
		System.out.println(rule + "\nendpar");
		System.out.println(mainRule+ "\nendpar");
	}
}
