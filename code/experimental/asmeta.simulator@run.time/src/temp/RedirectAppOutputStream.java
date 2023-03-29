package temp;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.asmeta.runtime_commander.Parser;
import org.asmeta.runtime_composer.Composition;
import org.asmeta.runtime_commander.Commander;
import org.asmeta.simulator.UpdateSet;

public class RedirectAppOutputStream {

    // This synchronized class will be used to write to the console window
    public class SynchronizedByteArrayOutputStreamWrapper
            extends OutputStream {
        // The console will be synchronized through a monitor.
        // WARNING! This could delay the code trying to write to the console!
        private final Object monitor = new Object();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        //byte[] array= new byte[100];
        //ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);

        @Override
        public void write(int data) throws IOException {
            synchronized (monitor) {
                byteArrayOutputStream.write(data);
       
            }
        }

        public byte[] readEmpty() {
            byte[] bufferContent;  
            synchronized(monitor) {
                bufferContent = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.reset();
            }
            return bufferContent;
        }
    }
    
    
    public class InputStreamHandlerThread extends Thread {

        private JTextArea txt;
        public InputStreamHandlerThread(JTextArea txt) {
            this.txt = txt;
            start();
        }

        @Override
        public void run() {
            System.setIn(new FakeInputStream());
        }

        class FakeInputStream extends InputStream {
            private int indx = 0;

            @Override
            public int read() throws IOException {
                if (indx == txt.getText().length()) {
                    indx = 0;
                    try {
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int byt = txt.getText().getBytes()[indx];
                indx++;
                return byt;

            }
        }
    }
    

    public void guiConsoleTest() throws Exception {
        System.out.println("Normal java console output");

        // Remember old output stream (optional)
        PrintStream stdout = System.out;
        stdout.println("Starting gui for console output"); // Still works
        // Stream for output to gui
        SynchronizedByteArrayOutputStreamWrapper rawout = new SynchronizedByteArrayOutputStreamWrapper();
        // Set new stream for System.out
        System.setOut(new PrintStream(rawout, true));
        //System.setIn(input);
        // Demo gui
        
        
        //Reader input=textArea.getText();
        //BufferedReader in = new BufferedReader(input);
        
        //JFrame windowIn = new JFrame("IN");           
        //JTextField inArea = new JTextField();
        //windowIn.add(inArea);
        //windowIn.setSize(500, 100);
        //windowIn.setVisible(true);
        
        JFrame windowOut = new JFrame("OUT");
        JTextArea textArea = new JTextArea();
        windowOut.add(new JScrollPane(textArea));
        windowOut.setSize(500, 500);
        windowOut.setVisible(true);
        
        ArrayList<String> lines=new ArrayList<>();
        
        //InputStreamHandlerThread inputStreamHandler = new InputStreamHandlerThread(textArea);
       
              	
        Thread consoleThreadOut = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                String pendingConsoleOutput = new String(rawout.readEmpty());
                //stdout.println(pendingConsoleOutput);
                textArea.append(pendingConsoleOutput);
                //System.out.println(pendingConsoleOutput);
                lines.add(pendingConsoleOutput);
                
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            	
            }
        });
        
        // Console thread
		/*
		 * Thread consoleThreadIn = new Thread(() -> { while
		 * (!Thread.currentThread().isInterrupted()) { inArea.addKeyListener(new
		 * KeyAdapter(){
		 * 
		 * @Override public void keyTyped(KeyEvent a) { if(a.getKeyChar()== '\n') {
		 * inputStreamHandler.notify(); } } });
		 * 
		 * } try { Thread.sleep(1); } catch (InterruptedException e) {
		 * 
		 * } });
		 */
        //consoleThread.setPriority(0);
        consoleThreadOut.start();
        //consoleThreadIn.start();
    		// {
			
		
			//in.readLine();
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//Commander.asmCompositions.get(1).eval();
		String path = "examples/testUnbound/";
		String path2 = "examples/trafficLightCoSim/";
        String a="( ".concat(path2+"controller.asm").concat(" | ").concat("( ").concat(path2+"trafficlightA.asm").concat(" || ").concat(path2+"trafficlightB.asm").concat(" )").concat(" )");
		System.out.println(a);
		Parser asm=new Parser(a);
		Composition asmI = asm.toComposition();
		UpdateSet appo=asmI.eval();
       
        //Clean up and exit
        consoleThreadOut.interrupt();
        try {
            consoleThreadOut.join();
        } catch (InterruptedException e) {
        }
        for(String i:lines) {
			textArea.append(i);
		}
        
		/*
		 * consoleThreadIn.interrupt(); try { consoleThreadIn.join(); } catch
		 * (InterruptedException e) { } for(String i:lines) { textArea.append(i); }
		 */
        
        //window.dispose();
    }

    public static void main(String[] args) throws Exception {
        new RedirectAppOutputStream().guiConsoleTest();
    }
}
