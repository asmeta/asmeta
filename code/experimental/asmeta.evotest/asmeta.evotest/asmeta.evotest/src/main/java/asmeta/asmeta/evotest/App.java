package asmeta.asmeta.evotest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.asmeta.asm2java.main.Asmeta2JavaCLI;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
    	// translate to java
    	Asmeta2JavaCLI.main(new String[]{"ex.asm"});
		// executing evouite
    	List<String> commands = Arrays.asList("java8", "-jar", "evosuite.jar");
    	ProcessBuilder pb = new ProcessBuilder(commands);
    	pb.start();
    	// traduci da Junit ad avalla
    	// TODO
    }
}
