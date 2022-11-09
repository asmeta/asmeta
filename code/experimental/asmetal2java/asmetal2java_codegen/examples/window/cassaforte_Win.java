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

public class cassaforte_Win {

    cassaforte esecuzione;
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
					cassaforte_Win window = new cassaforte_Win();
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
	public cassaforte_Win() {
		
		esecuzione = new cassaforte();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		    //Pannello principale
		    frame = new JFrame();
			//Titolo 
			frame.setTitle("cassaforte ASM -> Java");
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
			
			
			
			JLabel VariabileMonitored_text_1 = new JLabel("switchSensore");
			VariabileMonitored_text_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_sx.add(VariabileMonitored_text_1);
			
			JTextField VariabileMonitored_Valore_1 = new JTextField();
			VariabileMonitored_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_sx.add(VariabileMonitored_Valore_1);
			VariabileMonitored_Valore_1.setColumns(10);				    		
			
					try {
					VariabileMonitored_Valore_1.setText(esecuzione.switchSensore.Value.value.toString());
					}
					catch (NullPointerException errM1)
					{
						VariabileMonitored_Valore_1.setText("Insert Integer");
					}
			
			
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
				
				//"traduzione" della funzione askMonitored(esecuzione) del file _Exe
				   try
				   {
				   	esecuzione.switchSensore_supporto.value = Integer.parseInt(VariabileMonitored_Valore_1.getText().toString());
				   }
				   catch ( NumberFormatException err)
				   {
				        esecuzione.switchSensore_supporto.value = 0;
				   }
				   
				   
				   esecuzione.switchSensore.set(esecuzione.switchSensore_supporto);
				   
				   
				   
				   
				   //Aggiornamento dell'ASM
				   esecuzione.UpdateASM();
		
		    }});
	}

}

