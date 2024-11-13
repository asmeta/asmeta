package org.asmeta.asm2java.main

import asmeta.structure.Asm
import java.util.List
import asmeta.transitionrules.basictransitionrules.Rule
import org.junit.Assert
import java.util.ArrayList
import org.asmeta.asm2java.SeqRuleCollector
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.ControlledFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.MonitoredFunction

class JavaWindowGenerator extends AsmToJavaGenerator {

	def compileAndWrite(Asm asm, String writerPath, TranslatorOptions userOptions) {
		Assert.assertTrue(writerPath.endsWith(".java"));
		compileAndWrite(asm, writerPath, "JAVA", userOptions)
	}

	// all the rules that must translate in two versions seq and not seq
	// if null, translate all
	List<Rule> seqCalledRules;

	String supp

	int NvarC = 1;
	int NvarM = 1;

	override compileAsm(Asm asm) {
		// collect alla the seq rules if required
		if (options.optimizeSeqMacroRule) {
			seqCalledRules = new ArrayList
			for (r : asm.bodySection.ruleDeclaration)
				seqCalledRules.addAll(new SeqRuleCollector(false).visit(r))
		}
		//
		val asmName = asm.name

		// TODO fix include list
		return '''
			import java.awt.EventQueue;
			import java.awt.event.ActionEvent;
			import java.awt.event.ActionListener;
			import javax.swing.JFrame;
			import javax.swing.JLabel;
			import javax.swing.JTextField;
			import javax.swing.BoxLayout;
			import javax.swing.JPanel;
			import java.awt.Color;
			import javax.swing.border.LineBorder;
			import javax.swing.JButton;
			import java.awt.GridLayout;
			import javax.swing.JScrollPane;
			import javax.swing.ScrollPaneConstants;
			import javax.swing.SwingConstants;
			
			public class «asm.name»_Win {
			
			    «asm.name» esecuzione;
			    int stato = 0;
			    JFrame frame;
			    JPanel panel_destro = new JPanel();
			    JPanel panel_sinistro = new JPanel();
			    
			    private JPanel panelMonitored;
			    JLabel lblMonitored;
			    JButton btnEsegui;
			    
			    private JPanel panelControlled;
			    JLabel lblControlled;
			    	
			    private JScrollPane scrollPannel_sx;
			    private JScrollPane scrollPannel_dx;
			    
			    private JPanel panel_dx;
			    private JPanel panel_sx;
			    	
			    JTextField txtS = new JTextField();
			
			
				/**
				 * Launch the application.
				 */
				public static void main(String[] args) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								«asm.name»_Win window = new «asm.name»_Win();
								window.frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			
				/**
				 * Create the application.
				 */
				public «asm.name»_Win() {
					
					esecuzione = new «asm.name»();
					initialize();
				}
			
				/**
				 * Initialize the contents of the frame.
				 */
				private void initialize() {
					
					    //Pannello principale
					    frame = new JFrame();
						//Titolo 
						frame.setTitle("«asm.name» ASM -> Java");
						frame.setBounds(100, 100, 1500, 1000);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
						
						//Parte relativa alle monitored
						panel_sinistro.setBorder(new LineBorder(new Color(0, 0, 0)));
						panel_sinistro.setBackground(Color.WHITE);
						frame.getContentPane().add(panel_sinistro);
						panel_sinistro.setLayout(null);
						
						panelMonitored = new JPanel();
						panelMonitored.setBorder(new LineBorder(new Color(0, 0, 0)));
						panelMonitored.setBounds(0, 0, 741, 82);
						panel_sinistro.add(panelMonitored);
						panelMonitored.setLayout(null);
						
						btnEsegui = new JButton("Avvia");
						btnEsegui.setBounds(27, 13, 103, 46);
						panelMonitored.add(btnEsegui);
						
						lblMonitored = new JLabel("Monitored");
						lblMonitored.setBounds(326, 28, 293, 16);
						panelMonitored.add(lblMonitored);
						
						scrollPannel_sx = new JScrollPane();
						scrollPannel_sx.setToolTipText("");
						scrollPannel_sx.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						scrollPannel_sx.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						scrollPannel_sx.setBounds(0, 82, 741, 871);
						panel_sinistro.add(scrollPannel_sx);
						
						panel_sx = new JPanel();
						scrollPannel_sx.setViewportView(panel_sx);
						panel_sx.setLayout(new GridLayout(0, 2, 5, 25));
						
						//Parte relativa alle controlled
						panel_destro.setBackground(Color.WHITE);
						panel_destro.setBorder(new LineBorder(new Color(0, 0, 0)));
						frame.getContentPane().add(panel_destro);
						panel_destro.setLayout(null);
						
						panelControlled = new JPanel();
						panelControlled.setBorder(new LineBorder(new Color(0, 0, 0)));
						panelControlled.setLayout(null);
						panelControlled.setBounds(0, 0, 741, 82);
						panel_destro.add(panelControlled);
						
						lblControlled = new JLabel("Controlled");
						lblControlled.setBounds(345, 32, 58, 16);
						panelControlled.add(lblControlled);
						
						scrollPannel_dx = new JScrollPane();
						scrollPannel_dx.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						scrollPannel_dx.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						scrollPannel_dx.setBounds(0, 80, 741, 873);
						panel_destro.add(scrollPannel_dx);
						
						panel_dx = new JPanel();
						scrollPannel_dx.setViewportView(panel_dx);
						panel_dx.setLayout(new GridLayout(0, 2, 5, 25));
						
						
						//funzione per la dichiarazione iniziale delle variabili
						
						«ControlledDeclaration(asm)»
						
						«MonitoredDeclaration(asm)»
						
						btnEsegui.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							//Parte relativa alla stampa a video dei valori dell'ASM
							
							txtS.setEditable(false);
							txtS.setText("S " + stato);
							stato++;
							txtS.setBounds(137, 7, 40, 22);
							panelMonitored.add(txtS);
							txtS.setColumns(10);
							
							btnEsegui.setText("Esegui");
							
							//"traduzione" della funzione printControlled(esecuzione) del file _Exe
							«ControlledUpdate(asm)»
							
							//"traduzione" della funzione askMonitored(esecuzione) del file _Exe
							   «MonitoredUpdate(asm)»
							   
							   //Aggiornamento dell'ASM
							   esecuzione.updateASM();
					
					    }});
				}
			
			}
		'''

	}

	def ControlledDeclaration(Asm asm) {

		var sb = new StringBuffer;

		for (dd : asm.headerSection.signature.domain) {
			if (dd instanceof AbstractTd) {

				sb.append('''
					
							JLabel VariabileControlled_Text_«NvarC» = new JLabel("«dd.name»");
							VariabileControlled_Text_«NvarC».setHorizontalAlignment(SwingConstants.CENTER);
							panel_dx.add(VariabileControlled_Text_«NvarC» );
							
							JTextField VariabileControlled_Valore_«NvarC» = new JTextField();
							VariabileControlled_Valore_«NvarC».setHorizontalAlignment(SwingConstants.CENTER);
							VariabileControlled_Valore_«NvarC».setEditable(false);
							panel_dx.add(VariabileControlled_Valore_«NvarC»);
							VariabileControlled_Valore_«NvarC».setColumns(10);
							
						''')

				NvarC++;
			}

		}

		for (fd : asm.headerSection.signature.function) {

			if (fd instanceof ControlledFunction) {
				if (fd.domain === null) {
					sb.append('''
						
						JLabel VariabileControlled_Text_«NvarC» = new JLabel("«fd.name»");
						VariabileControlled_Text_«NvarC».setHorizontalAlignment(SwingConstants.CENTER);
						panel_dx.add(VariabileControlled_Text_«NvarC» );
						
						JTextField VariabileControlled_Valore_«NvarC» = new JTextField();
						VariabileControlled_Valore_«NvarC».setHorizontalAlignment(SwingConstants.CENTER);
						VariabileControlled_Valore_«NvarC».setEditable(false);
						panel_dx.add(VariabileControlled_Valore_«NvarC»);
						VariabileControlled_Valore_«NvarC».setColumns(10);
						
					''')

					NvarC++

				} else {
					if ((fd.domain instanceof EnumTd && fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
							
							JLabel VariabileControlled_Text_«NvarC» = new JLabel("«fd.name»");
							VariabileControlled_Text_«NvarC».setHorizontalAlignment(SwingConstants.CENTER);
							panel_dx.add(VariabileControlled_Text_«NvarC» );
							
							JTextField VariabileControlled_Valore_«NvarC» = new JTextField();
							VariabileControlled_Valore_«NvarC».setHorizontalAlignment(SwingConstants.CENTER);
							VariabileControlled_Valore_«NvarC».setEditable(false);
							panel_dx.add(VariabileControlled_Valore_«NvarC»);
							VariabileControlled_Valore_«NvarC».setColumns(10);
							
							
							 JLabel VariabileControlled_Text_«NvarC»_1 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(0).toString());
							 VariabileControlled_Text_«NvarC»_1.setHorizontalAlignment(SwingConstants.CENTER);
							 panel_dx.add(VariabileControlled_Text_«NvarC»_1);
							 
							 JTextField VariabileControlled_Valore_«NvarC»_1 = new JTextField();
							 VariabileControlled_Valore_«NvarC»_1.setHorizontalAlignment(SwingConstants.CENTER);
							 VariabileControlled_Valore_«NvarC»_1.setEditable(false);
							 panel_dx.add(VariabileControlled_Valore_«NvarC»_1);
							 VariabileControlled_Valore_«NvarC»_1.setColumns(10);
							
							 JLabel VariabileControlled_Text_«NvarC»_2 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(1).toString());
							 VariabileControlled_Text_«NvarC»_2.setHorizontalAlignment(SwingConstants.CENTER);
							 panel_dx.add(VariabileControlled_Text_«NvarC»_2);
							 
							 JTextField VariabileControlled_Valore_«NvarC»_2 = new JTextField();
							 VariabileControlled_Valore_«NvarC»_2.setHorizontalAlignment(SwingConstants.CENTER);
							 VariabileControlled_Valore_«NvarC»_2.setEditable(false);
							 panel_dx.add(VariabileControlled_Valore_«NvarC»_2);
							 VariabileControlled_Valore_«NvarC»_2.setColumns(10);			
							 
							 JLabel VariabileControlled_Text_«NvarC»_3 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(2).toString());
							 VariabileControlled_Text_«NvarC»_3.setHorizontalAlignment(SwingConstants.CENTER);
							 panel_dx.add(VariabileControlled_Text_«NvarC»_3);
							 
							 JTextField VariabileControlled_Valore_«NvarC»_3 = new JTextField();
							 VariabileControlled_Valore_«NvarC»_3.setHorizontalAlignment(SwingConstants.CENTER);
							 VariabileControlled_Valore_«NvarC»_3.setEditable(false);
							 panel_dx.add(VariabileControlled_Valore_«NvarC»_3);
							 VariabileControlled_Valore_«NvarC»_3.setColumns(10);
							
						''')
						NvarC++;

					}

					if ((fd.codomain instanceof EnumTd && fd.domain instanceof EnumTd)) {
						sb.append('''
							
							JLabel VariabileControlled_Text_«NvarC» = new JLabel("«fd.name»");
							VariabileControlled_Text_«NvarC».setHorizontalAlignment(SwingConstants.CENTER);
							panel_dx.add(VariabileControlled_Text_«NvarC» );
							
							JTextField VariabileControlled_Valore_«NvarC» = new JTextField();
							VariabileControlled_Valore_«NvarC».setHorizontalAlignment(SwingConstants.CENTER);
							VariabileControlled_Valore_«NvarC».setEditable(false);
							panel_dx.add(VariabileControlled_Valore_«NvarC»);
							VariabileControlled_Valore_«NvarC».setColumns(10);
							
							
							 JLabel VariabileControlled_Text_«NvarC»_1 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(0).toString());
							 VariabileControlled_Text_«NvarC»_1.setHorizontalAlignment(SwingConstants.CENTER);
							 panel_dx.add(VariabileControlled_Text_«NvarC»_1);
							 
							 JTextField VariabileControlled_Valore_«NvarC»_1 = new JTextField();
							 VariabileControlled_Valore_«NvarC»_1.setHorizontalAlignment(SwingConstants.CENTER);
							 VariabileControlled_Valore_«NvarC»_1.setEditable(false);
							 panel_dx.add(VariabileControlled_Valore_«NvarC»_1);
							 VariabileControlled_Valore_«NvarC»_1.setColumns(10);
							
							 JLabel VariabileControlled_Text_«NvarC»_2 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(1).toString());
							 VariabileControlled_Text_«NvarC»_2.setHorizontalAlignment(SwingConstants.CENTER);
							 panel_dx.add(VariabileControlled_Text_«NvarC»_2);
							 
							 JTextField VariabileControlled_Valore_«NvarC»_2 = new JTextField();
							 VariabileControlled_Valore_«NvarC»_2.setHorizontalAlignment(SwingConstants.CENTER);
							 VariabileControlled_Valore_«NvarC»_2.setEditable(false);
							 panel_dx.add(VariabileControlled_Valore_«NvarC»_2);
							 VariabileControlled_Valore_«NvarC»_2.setColumns(10);
							 
							 JLabel VariabileControlled_Text_«NvarC»_3 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(2).toString());
							 VariabileControlled_Text_«NvarC»_3.setHorizontalAlignment(SwingConstants.CENTER);
							 panel_dx.add(VariabileControlled_Text_«NvarC»_3);
							 
							 JTextField VariabileControlled_Valore_«NvarC»_3 = new JTextField();
							 VariabileControlled_Valore_«NvarC»_3.setHorizontalAlignment(SwingConstants.CENTER);
							 VariabileControlled_Valore_«NvarC»_3.setEditable(false);
							 panel_dx.add(VariabileControlled_Valore_«NvarC»_3);
							 VariabileControlled_Valore_«NvarC»_3.setColumns(10);
							 
							 JLabel VariabileControlled_Text_«NvarC»_4 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(3).toString());
							 VariabileControlled_Text_«NvarC»_4.setHorizontalAlignment(SwingConstants.CENTER);
							 panel_dx.add(VariabileControlled_Text_«NvarC»_4);
											    		 
							 JTextField VariabileControlled_Valore_«NvarC»_4 = new JTextField();
							 VariabileControlled_Valore_«NvarC»_4.setHorizontalAlignment(SwingConstants.CENTER);
							 VariabileControlled_Valore_«NvarC»_4.setEditable(false);
							 panel_dx.add(VariabileControlled_Valore_«NvarC»_4);
							 VariabileControlled_Valore_«NvarC»_4.setColumns(10);
							
						''')

						NvarC++;
					}

				}
			}

		}

		NvarC = 1;
		return sb.toString

	}

	def MonitoredDeclaration(Asm asm) {

		var sb = new StringBuffer;

		for (fd : asm.headerSection.signature.function) {

			if (fd instanceof MonitoredFunction) {
				// Solo se il dominio » nullo, quindi funzioni che ricadono nella struttura zero<Valore> 
				if (fd.domain === null) {
					// Caso relativo alle variabili booleane non concrete
					if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
							JLabel VariabileMonitored_text_«NvarM» = new JLabel("«fd.name»");
							VariabileMonitored_text_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»);
							
							JTextField VariabileMonitored_Valore_«NvarM» = new JTextField();
							VariabileMonitored_Valore_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»);
							VariabileMonitored_Valore_«NvarM».setColumns(10);
							
							
									try {
									VariabileMonitored_Valore_«NvarM».setText(esecuzione.«fd.name».Value.toString());
									}
									catch (NullPointerException errM1)
									{
										VariabileMonitored_Valore_«NvarM».setText("Insert Boolean");
									}
							
						''')

						NvarM++;

					}

					if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
							JLabel VariabileMonitored_text_«NvarM» = new JLabel("«fd.name»");
							VariabileMonitored_text_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»);
							
							JTextField VariabileMonitored_Valore_«NvarM» = new JTextField();
							VariabileMonitored_Valore_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»);
							VariabileMonitored_Valore_«NvarM».setColumns(10);
							
									try {
									VariabileMonitored_Valore_«NvarM».setText(esecuzione.«fd.name».Value.toString());
									}
									catch (NullPointerException errM1)
									{
										VariabileMonitored_Valore_«NvarM».setText("Insert Integer");
									}
							
						''')

						NvarM++;

					}

					if (fd.codomain instanceof EnumTd) {
						sb.append('''
							
							System.out.println("\nElenco valori per «fd.name» :");
							for(int i=0; i< esecuzione.«fd.codomain.name»_elemsList.size(); i++)
							System.out.println( (i+1) + " -> " +esecuzione.«fd.codomain.name»_elemsList.get(i));
							
							JLabel VariabileMonitored_text_«NvarM» = new JLabel("«fd.name»");
							VariabileMonitored_text_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»);
							
							JTextField VariabileMonitored_Valore_«NvarM» = new JTextField();
							VariabileMonitored_Valore_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»);
							VariabileMonitored_Valore_«NvarM».setColumns(10);				    		
							
									try {
									VariabileMonitored_Valore_«NvarM».setText("Insert Integer");
									}
									catch (NullPointerException errM1)
									{
										VariabileMonitored_Valore_«NvarM».setText("Insert Integer");
									}
							
						''')

						NvarM++;

					}

					if (fd.codomain instanceof ConcreteDomain) {
						sb.append('''
							
							JLabel VariabileMonitored_text_«NvarM» = new JLabel("«fd.name»");
							VariabileMonitored_text_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»);
							
							JTextField VariabileMonitored_Valore_«NvarM» = new JTextField();
							VariabileMonitored_Valore_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»);
							VariabileMonitored_Valore_«NvarM».setColumns(10);				    		
							
									try {
									VariabileMonitored_Valore_«NvarM».setText(esecuzione.«fd.name».Value.value.toString());
									}
									catch (NullPointerException errM1)
									{
										VariabileMonitored_Valore_«NvarM».setText("Insert Integer");
									}
							
						''')

						NvarM++;

					}
					if (fd.codomain instanceof AbstractTd) {
						sb.append('''
							
							System.out.println("\nElenco valori per «fd.name» :");
							for(int i=0; i< esecuzione.«fd.codomain.name»_elemsList.size(); i++)
							System.out.println( (i+1) + " -> " +esecuzione.«fd.codomain.name»_elemsList.get(i));
							
							
							JLabel VariabileMonitored_text_«NvarM» = new JLabel("«fd.name»");
							VariabileMonitored_text_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»);
							
							JTextField VariabileMonitored_Valore_«NvarM» = new JTextField();
							VariabileMonitored_Valore_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»);
							VariabileMonitored_Valore_«NvarM».setColumns(10);				    		
							
							try {
									VariabileMonitored_Valore_«NvarM».setText(esecuzione.«fd.name».Value.toString());
									}
									catch (NullPointerException errM1)
									{
										VariabileMonitored_Valore_«NvarM».setText("Insert Integer");
									}
							
						''')

						NvarM++;

					}

				} else {

					if (fd.domain instanceof ConcreteDomain && fd.codomain.name.equals("Boolean")) {

						sb.append('''
							
							JLabel VariabileMonitored_text_«NvarM» = new JLabel("«fd.name»");
							VariabileMonitored_text_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»);
												   						   	
							JTextField VariabileMonitored_Valore_«NvarM» = new JTextField();
							VariabileMonitored_Valore_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»);
							VariabileMonitored_Valore_«NvarM».setEditable(false);
							VariabileMonitored_Valore_«NvarM».setColumns(10);
							
							
							JLabel VariabileMonitored_text_«NvarM»_1 = new JLabel(esecuzione.«fd.domain.name»_elems.get(0).toString());
							VariabileMonitored_text_«NvarM»_1.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»_1);
												   	
							JTextField VariabileMonitored_Valore_«NvarM»_1 = new JTextField();
							VariabileMonitored_Valore_«NvarM»_1.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»_1);
							VariabileMonitored_Valore_«NvarM»_1.setColumns(10);
												   	
							try {
									VariabileMonitored_Valore_«NvarM»_1.setText("Insert Boolean");
								}
							catch (NullPointerException errM1)
								{
									VariabileMonitored_Valore_«NvarM»_1.setText("Insert Boolean");
							}
							
							JLabel VariabileMonitored_text_«NvarM»_2 = new JLabel(esecuzione.«fd.domain.name»_elems.get(1).toString());
							VariabileMonitored_text_«NvarM»_2.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»_2);
												   	
							JTextField VariabileMonitored_Valore_«NvarM»_2 = new JTextField();
							VariabileMonitored_Valore_«NvarM»_2.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»_2);
							VariabileMonitored_Valore_«NvarM»_2.setColumns(10);
							
							try {
									VariabileMonitored_Valore_«NvarM»_2.setText("Insert Boolean");
								}
							catch (NullPointerException errM1)
								{
									VariabileMonitored_Valore_«NvarM»_2.setText("Insert Boolean");
									  }
							
						''')

						NvarM++

					}

					if (fd.domain instanceof EnumTd && fd.codomain.name.equals("Boolean")) {

						sb.append('''
							
							JLabel VariabileMonitored_text_«NvarM» = new JLabel("«fd.name»");
							VariabileMonitored_text_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»);
												   						   	
							JTextField VariabileMonitored_Valore_«NvarM» = new JTextField();
							VariabileMonitored_Valore_«NvarM».setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»);
							VariabileMonitored_Valore_«NvarM».setEditable(false);
							VariabileMonitored_Valore_«NvarM».setColumns(10);
							
							
							
							JLabel VariabileMonitored_text_«NvarM»_1 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(0).toString());
							VariabileMonitored_text_«NvarM»_1.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»_1);
												   	
							JTextField VariabileMonitored_Valore_«NvarM»_1 = new JTextField();
							VariabileMonitored_Valore_«NvarM»_1.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»_1);
							VariabileMonitored_Valore_«NvarM»_1.setColumns(10);
							
							try {
									VariabileMonitored_Valore_«NvarM»_1.setText("Insert Boolean");
								}
							catch (NullPointerException errM1)
								{
									VariabileMonitored_Valore_«NvarM»_1.setText("Insert Boolean");
								}
							
							JLabel VariabileMonitored_text_«NvarM»_2 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(1).toString());
							VariabileMonitored_text_«NvarM»_2.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»_2);
												   	
							JTextField VariabileMonitored_Valore_«NvarM»_2 = new JTextField();
							VariabileMonitored_Valore_«NvarM»_2.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»_2);
							VariabileMonitored_Valore_«NvarM»_2.setColumns(10);
							
							try {
									VariabileMonitored_Valore_«NvarM»_2.setText("Insert Boolean");
								}
							catch (NullPointerException errM1)
								{
									VariabileMonitored_Valore_«NvarM»_2.setText("Insert Boolean");
								}					   	
							
							JLabel VariabileMonitored_text_«NvarM»_3 = new JLabel(esecuzione.«fd.domain.name»_elemsList.get(2).toString());
							VariabileMonitored_text_«NvarM»_3.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_text_«NvarM»_3);
												   	
							JTextField VariabileMonitored_Valore_«NvarM»_3 = new JTextField();
							VariabileMonitored_Valore_«NvarM»_3.setHorizontalAlignment(SwingConstants.CENTER);
							panel_sx.add(VariabileMonitored_Valore_«NvarM»_3);
							VariabileMonitored_Valore_«NvarM»_3.setColumns(10);
							
							try {
									VariabileMonitored_Valore_«NvarM»_3.setText("Insert Boolean");
								}
							catch (NullPointerException errM1)
								{
									VariabileMonitored_Valore_«NvarM»_3.setText("Insert Boolean");
								}					   	
							''')

						NvarM++
					}

				}

			}

		}

		NvarM = 1;

		return sb.toString

	}

	def ControlledUpdate(Asm asm) {

		var sb = new StringBuffer;

		for (dd : asm.headerSection.signature.domain) {
			if (dd instanceof AbstractTd) {

				// Caso particolare da gestire
				sb.append('''
					
							String «dd.name»_val="";
							for(int i=0 ; i< esecuzione.«dd.name»_elemsList.size(); i++)
							if(i!= esecuzione.«dd.name»_elemsList.size()-1)
							«dd.name»_val += esecuzione.«dd.name»_elemsList.get(i) +", ";
							else
							«dd.name»_val += esecuzione.«dd.name»_elemsList.get(i);	
					
							try{
							VariabileControlled_Valore_«NvarC».setText(«dd.name»_val);
							} catch( NullPointerException errC«NvarC»)
							{
							 VariabileControlled_Valore_«NvarC».setText("null");
							}
							
						''')

				NvarC++;

			}
		}

		for (fd : asm.headerSection.signature.function) {

			// Studio dei casi controlled con il dominio nullo, quindi funzioni che ricadono nella struttura zeroC<Valore>
			if (fd instanceof ControlledFunction) {
				if (fd.domain === null) {
					if (fd.codomain instanceof ConcreteDomain) {
						sb.append('''
							
							
							
try{
							
VariabileControlled_Valore_«NvarC».setText(esecuzione.«fd.name».get().value.toString());
							
} catch( NullPointerException errC«NvarC»)
							
{
							
 VariabileControlled_Valore_«NvarC».setText("null");
							
				    		}
							
							

						
''')

						NvarC++;

					}
					if (fd.codomain.name.equals("Integer") || fd.codomain.name.equals("Boolean") ||
						fd.codomain.name.equals("String")) {
						sb.append('''
							
							
							try{
							VariabileControlled_Valore_«NvarC».setText(esecuzione.«fd.name».get().toString());
							} catch( NullPointerException errC«NvarC»)
							{
							 VariabileControlled_Valore_«NvarC».setText("null");
							}
							
							
						''')

						NvarC++;

					}
					if (fd.codomain instanceof MapDomain) {
						sb.append('''
							
							
							try{
							VariabileControlled_Valore_«NvarC».setText(esecuzione.«fd.name».get().toString());
							} catch( NullPointerException errC«NvarC»)
							{
							 VariabileControlled_Valore_«NvarC».setText("null");
							}
							
						''')

						NvarC++;

					}

					if (fd.codomain instanceof SequenceDomain) {
						sb.append('''
							
							
							try{
							VariabileControlled_Valore_«NvarC».setText(esecuzione.«fd.name».get().toString());
							} catch( NullPointerException errC«NvarC»)
							{
							 VariabileControlled_Valore_«NvarC».setText("null");
							}
							
						''')

						NvarC++;

					}
					if (fd.codomain instanceof EnumTd) {
						sb.append('''
							
							
							try{
							VariabileControlled_Valore_«NvarC».setText(esecuzione.«fd.name».oldValue.name());
							} catch( NullPointerException errC«NvarC»)
							{
							VariabileControlled_Valore_«NvarC».setText("null");
							}
							
						''')

						NvarC++;

					}
				} else {

					if (fd.domain instanceof EnumTd && fd.codomain instanceof ConcreteDomain) {
						sb.append('''
							
							
							try{
							VariabileControlled_Valore_«NvarC».setText("");
							} catch( NullPointerException errC«NvarC»)
							{
							VariabileControlled_Valore_«NvarC».setText("null");
							}
							
							
							 try{
							 VariabileControlled_Valore_«NvarC»_1.setText(esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(0)).value.toString());
							 } catch( NullPointerException errC0«NvarC»)
							 {
							  VariabileControlled_Text_«NvarC»_1.setText("null");
							 }
							
							 try{
							 VariabileControlled_Valore_«NvarC»_2.setText(esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(1)).value.toString());
							 } catch( NullPointerException errC0«NvarC»)
							 {
							 VariabileControlled_Text_«NvarC»_1.setText("null");
							 }
							 
							 try{
							 VariabileControlled_Valore_«NvarC»_3.setText(esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(2)).value.toString());
							 } catch( NullPointerException errC0«NvarC»)
							 {
							  VariabileControlled_Text_«NvarC»_1.setText("null");
							 }
						''')

					}

					if (fd.domain instanceof EnumTd && fd.codomain instanceof EnumTd) {
						sb.append('''
							
							
							try{
							VariabileControlled_Valore_«NvarC».setText("");
							} catch( NullPointerException errC«NvarC»)
							{
							VariabileControlled_Text_«NvarC».setText("null");
							}
							
							
							 try{
							 VariabileControlled_Valore_«NvarC»_1.setText(esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(0)).toString());
							 } catch( NullPointerException errC0«NvarC»)
							 {
							  VariabileControlled_Valore_«NvarC»_1.setText("null");
							 }
							
							 try{
							 VariabileControlled_Valore_«NvarC»_2.setText(esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(1)).toString());
							 } catch( NullPointerException errC0«NvarC»)
							 {
							 VariabileControlled_Valore_«NvarC»_2.setText("null");
							 }
							 try{
							 VariabileControlled_Valore_«NvarC»_3.setText(esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(2)).toString());
							 } catch( NullPointerException errC0«NvarC»)
							 {
							  VariabileControlled_Valore_«NvarC»_3.setText("null");
							 }
							
							 try{
							 VariabileControlled_Valore_«NvarC»_4.setText(esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(3)).toString());
							 } catch( NullPointerException errC0«NvarC»)
							 {
							  VariabileControlled_Valore_«NvarC»_4.setText("null");
							 }
						''')

						NvarC++

					}

				}
			}

		}

		NvarC = 1;
		return sb.toString
	}

	def MonitoredUpdate(Asm asm) {

		var sb = new StringBuffer;

		for (fd : asm.headerSection.signature.function) {

			if (fd instanceof MonitoredFunction) {
				// Solo se il dominio » nullo, quindi funzioni che ricadono nella struttura zero<Valore> 
				if (fd.domain === null) {
					// Caso relativo alle variabili booleane non concrete
					if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
							
							if(VariabileMonitored_Valore_«NvarM».getText().contentEquals("true"))
							esecuzione.«fd.name».Value = true;
							else
								esecuzione.«fd.name».Value = false;
								
						''')
						NvarM++
					}

					if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
							
							try
							{
								esecuzione.«fd.name».Value = Integer.parseInt(VariabileMonitored_Valore_«NvarM».getText().toString());
							}
							catch ( NumberFormatException err)
							{
								esecuzione.«fd.name».Value = 0;
							}
							
							''')
						NvarM++
					}

					if (fd.codomain instanceof EnumTd) {
						sb.append('''
							
							int sup«NvarM»;
							try
							{
							    sup«NvarM» = Integer.parseInt(VariabileMonitored_Valore_«NvarM».getText().toString());
							}
							catch ( NumberFormatException err)
							{
								sup«NvarM» = 1;
							}
							
							if(sup«NvarM»<= 0 || sup«NvarM»> esecuzione.«fd.codomain.name»_elemsList.size())
							esecuzione.«fd.name».set(esecuzione.«fd.codomain.name»_elemsList.get(0));
							else
							esecuzione.«fd.name».set(esecuzione.«fd.codomain.name»_elemsList.get(sup«NvarM»-1));
							        
						''')
						NvarM++
					}

					if (fd.codomain instanceof ConcreteDomain) {
						sb.append('''
							try
							{
								esecuzione.«fd.name»_supporto.value = Integer.parseInt(VariabileMonitored_Valore_«NvarM».getText().toString());
							}
							catch ( NumberFormatException err)
							{
							     esecuzione.«fd.name»_supporto.value = 0;
							}
							
							
							esecuzione.«fd.name».set(esecuzione.«fd.name»_supporto);
							
							
							
						''')
						NvarM++
					}

					if (fd.codomain instanceof AbstractTd) {
						sb.append('''
							
							int sup«NvarM»;
							try
							{
							    sup«NvarM» = Integer.parseInt(VariabileMonitored_Valore_«NvarM».getText().toString());
							}
							catch ( NumberFormatException err)
							{
								sup«NvarM» = 1;
							}
							
							if(sup«NvarM»<= 0 || sup«NvarM»> esecuzione.«fd.codomain.name»_Class.size())
							esecuzione.«fd.name».set(esecuzione.«fd.codomain.name»_Class.get(0));
							else
							esecuzione.«fd.name».set(esecuzione.«fd.codomain.name»_Class.get(sup«NvarM»-1));
							        
						''')
						NvarM++
					}

				} else {

					if (fd.domain instanceof ConcreteDomain && fd.codomain.name.equals("Boolean")) {
						sb.append('''
							
							if(VariabileMonitored_Valore_«NvarM»_1.getText().contentEquals("true"))
							esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elem,true);
							else
							esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elem,false);
								
						''')

						sb.append('''
							
							if(VariabileMonitored_Valore_«NvarM»_2.getText().contentEquals("true"))
							esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elem,true);
							else
							esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elem,false);
								
						''')

					}

					if (fd.domain instanceof EnumTd && fd.codomain.name.equals("Boolean")) {

						sb.append('''
							
							if(VariabileMonitored_Valore_«NvarM»_1.getText().contentEquals("true"))
							esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elemsList.get(0), true);
							else
								esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elemsList.get(0), false);
								
						''')

						sb.append('''
							
							if(VariabileMonitored_Valore_«NvarM»_2.getText().contentEquals("true"))
							esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elemsList.get(1), true);
							else
								esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elemsList.get(1), false);
								
						''')

						sb.append('''
							
							if(VariabileMonitored_Valore_«NvarM»_3.getText().contentEquals("true"))
							esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elemsList.get(2), true);
							else
								esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elemsList.get(2), false);
								
						''')

					}

				}

			}

		}

		NvarM = 1
		return sb.toString

	}
}
