package asmeta.asmeta.evotest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.asmeta.asm2java.main.Asmeta2JavaCLI;

import asmeta.junit2avalla.main.Junit2AvallaCLI;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
    	
    	String name = "\"input/RegistroDiCassav4.asm\"";
    	
    	// translate to java
    	Asmeta2JavaCLI.main(new String[]{"-input",name,"-output","evosuite-target","-mode","testGen","-Dcompiler=true"});
    	
		// executing evouite
        List<String> commands = Arrays.asList("cmd.exe", "/c", "java8", "-jar", "evosuite-1.0.6.jar", "-target", "evosuite-target", "-class", "RegistroDiCassav4_ATG",
        		"-criterion","LINE:BRANCH","-Dminimize=true","-Dassertion_strategy=all");

        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.inheritIO();  // per mostrare l'output nel console corrente
        try {
            Process process = pb.start();
            process.waitFor();  // Aspetta il completamento del processo
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        // translate to avalla
        Junit2AvallaCLI.main(new String[] {"-input","evosuite-tests/RegistroDiCassav4_ATG_ESTest.java"});

    	 
    }
}
