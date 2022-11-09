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

public class FLIP_FLOP_0_Win {

    FLIP_FLOP_0 esecuzione;
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
					FLIP_FLOP_0_Win window = new FLIP_FLOP_0_Win();
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
	public FLIP_FLOP_0_Win() {
		
		esecuzione = new FLIP_FLOP_0();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		    //Pannello principale
		    frame = new JFrame();
			//Titolo 
			frame.setTitle("FLIP_FLOP_0 ASM -> Java");
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
			
			
			JLabel VariabileControlled_Text_1 = new JLabel("ctl_state");
			VariabileControlled_Text_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_dx.add(VariabileControlled_Text_1 );
			
			JTextField VariabileControlled_Valore_1 = new JTextField();
			VariabileControlled_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
			VariabileControlled_Valore_1.setEditable(false);
			panel_dx.add(VariabileControlled_Valore_1);
			VariabileControlled_Valore_1.setColumns(10);
			
			
			JLabel VariabileMonitored_text_1 = new JLabel("high");
			VariabileMonitored_text_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_sx.add(VariabileMonitored_text_1);
			
			JTextField VariabileMonitored_Valore_1 = new JTextField();
			VariabileMonitored_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_sx.add(VariabileMonitored_Valore_1);
			VariabileMonitored_Valore_1.setColumns(10);
			
			
					try {
					VariabileMonitored_Valore_1.setText(esecuzione.high.Value.toString());
					}
					catch (NullPointerException errM1)
					{
						VariabileMonitored_Valore_1.setText("Insert Boolean");
					}
			
			JLabel VariabileMonitored_text_2 = new JLabel("low");
			VariabileMonitored_text_2.setHorizontalAlignment(SwingConstants.CENTER);
			panel_sx.add(VariabileMonitored_text_2);
			
			JTextField VariabileMonitored_Valore_2 = new JTextField();
			VariabileMonitored_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
			panel_sx.add(VariabileMonitored_Valore_2);
			VariabileMonitored_Valore_2.setColumns(10);
			
			
					try {
					VariabileMonitored_Valore_2.setText(esecuzione.low.Value.toString());
					}
					catch (NullPointerException errM1)
					{
						VariabileMonitored_Valore_2.setText("Insert Boolean");
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
											
											
											
				try{
											
				VariabileControlled_Valore_1.setText(esecuzione.ctl_state.get().value.toString());
											
				} catch( NullPointerException errC1)
											
				{
											
				 VariabileControlled_Valore_1.setText("null");
											
								    		}
											
											
				
										
				
				//"traduzione" della funzione askMonitored(esecuzione) del file _Exe
				   
				   if(VariabileMonitored_Valore_1.getText().contentEquals("true"))
				   esecuzione.high.Value = true;
				   else
				   	esecuzione.high.Value = false;
				   	
				   
				   if(VariabileMonitored_Valore_2.getText().contentEquals("true"))
				   esecuzione.low.Value = true;
				   else
				   	esecuzione.low.Value = false;
				   	
				   
				   //Aggiornamento dell'ASM
				   esecuzione.UpdateASM();
		
		    }});
	}

}

