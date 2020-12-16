package org.asmeta.framework.enforcerAirConditioner;

import java.io.*;
public class mainExternal
{
public static void main(String[] args) throws IOException {
if (args.length != 1) {
System.err.println("Usage: java OSProcess <command>");
System.exit(0);
}
// args[0] is the command that is run in a separate process
ProcessBuilder pb = new ProcessBuilder(args[0]);
Process process = pb.start();
// obtain the input stream
InputStream is = process.getInputStream();
InputStreamReader isr = new InputStreamReader(is);
BufferedReader br = new BufferedReader(isr);
// read the output of the process
String line;
while ( (line = br.readLine()) != null)
System.out.println(line);
br.close();
}
}