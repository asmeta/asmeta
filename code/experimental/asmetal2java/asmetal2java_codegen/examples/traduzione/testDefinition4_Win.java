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

public class testDefinition4_Win {

	testDefinition4 esecuzione;
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
							testDefinition4_Win window = new testDefinition4_Win();
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
public testDefinition4_Win() {

		esecuzione = new testDefinition4();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
private void initialize() {

		//Pannello principale
		frame = new JFrame();
		//Titolo 
		frame.setTitle("testDefinition4 ASM -> Java");
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

		JLabel VariabileControlled_Text_1 = new JLabel("waterpressure");
		VariabileControlled_Text_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_1 );

		JTextField VariabileControlled_Valore_1 = new JTextField();
		VariabileControlled_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_1.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_1);
		VariabileControlled_Valore_1.setColumns(10);

		JLabel VariabileControlled_Text_2 = new JLabel("pressure");
		VariabileControlled_Text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_2 );

		JTextField VariabileControlled_Valore_2 = new JTextField();
		VariabileControlled_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_2);
		VariabileControlled_Valore_2.setColumns(10);

		JLabel VariabileControlled_Text_3 = new JLabel("overridden");
		VariabileControlled_Text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_3 );

		JTextField VariabileControlled_Valore_3 = new JTextField();
		VariabileControlled_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_3);
		VariabileControlled_Valore_3.setColumns(10);

		JLabel VariabileControlled_Text_4 = new JLabel("safetyInjection");
		VariabileControlled_Text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4 );

		JTextField VariabileControlled_Valore_4 = new JTextField();
		VariabileControlled_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4);
		VariabileControlled_Valore_4.setColumns(10);

		JLabel VariabileControlled_Text_5 = new JLabel("phase");
		VariabileControlled_Text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_5 );

		JTextField VariabileControlled_Valore_5 = new JTextField();
		VariabileControlled_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_5.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_5);
		VariabileControlled_Valore_5.setColumns(10);

		System.out.println("\nElenco valori per reset :");
		for(int i=0; i< esecuzione.Switch_lista.size(); i++)
		System.out.println( (i+1) + " -> " +esecuzione.Switch_lista.get(i));

		JLabel VariabileMonitored_text_1 = new JLabel("reset");
		VariabileMonitored_text_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_1);

		JTextField VariabileMonitored_Valore_1 = new JTextField();
		VariabileMonitored_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_1);
		VariabileMonitored_Valore_1.setColumns(10);

		try {
			VariabileMonitored_Valore_1.setText("Insert Integer");
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_1.setText("Insert Integer");
		}

		System.out.println("\nElenco valori per block :");
		for(int i=0; i< esecuzione.Switch_lista.size(); i++)
		System.out.println( (i+1) + " -> " +esecuzione.Switch_lista.get(i));

		JLabel VariabileMonitored_text_2 = new JLabel("block");
		VariabileMonitored_text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_2);

		JTextField VariabileMonitored_Valore_2 = new JTextField();
		VariabileMonitored_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_2);
		VariabileMonitored_Valore_2.setColumns(10);

		try {
			VariabileMonitored_Valore_2.setText("Insert Integer");
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_2.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_3 = new JLabel("delta");
		VariabileMonitored_text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_3);

		JTextField VariabileMonitored_Valore_3 = new JTextField();
		VariabileMonitored_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_3);
		VariabileMonitored_Valore_3.setColumns(10);

		try {
			VariabileMonitored_Valore_3.setText(esecuzione.delta.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_3.setText("Insert Integer");
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

						try {
							VariabileControlled_Valore_1.setText(esecuzione.waterpressure.get().value.toString());
						} catch( NullPointerException errC1)
						{
							VariabileControlled_Valore_1.setText("null");
						}

						try {
							VariabileControlled_Valore_2.setText(esecuzione.pressure.oldValue.name());
						} catch( NullPointerException errC2)
						{
							VariabileControlled_Valore_2.setText("null");
						}

						try {
							VariabileControlled_Valore_3.setText(esecuzione.overridden.get().toString());
						} catch( NullPointerException errC3)
						{
							VariabileControlled_Valore_3.setText("null");
						}

						try {
							VariabileControlled_Valore_4.setText(esecuzione.safetyInjection.oldValue.name());
						} catch( NullPointerException errC4)
						{
							VariabileControlled_Valore_4.setText("null");
						}

						try {
							VariabileControlled_Valore_5.setText(esecuzione.phase.oldValue.name());
						} catch( NullPointerException errC5)
						{
							VariabileControlled_Valore_5.setText("null");
						}

						//"traduzione" della funzione askMonitored(esecuzione) del file _Exe

						int sup1;
						try
						{
							sup1 = Integer.parseInt(VariabileMonitored_Valore_1.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							sup1 = 1;
						}

						if(sup1<= 0 || sup1> esecuzione.Switch_lista.size())
						esecuzione.reset.set(esecuzione.Switch_lista.get(0));
						else
						esecuzione.reset.set(esecuzione.Switch_lista.get(sup1-1));

						int sup2;
						try
						{
							sup2 = Integer.parseInt(VariabileMonitored_Valore_2.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							sup2 = 1;
						}

						if(sup2<= 0 || sup2> esecuzione.Switch_lista.size())
						esecuzione.block.set(esecuzione.Switch_lista.get(0));
						else
						esecuzione.block.set(esecuzione.Switch_lista.get(sup2-1));

						try
						{
							esecuzione.delta_supporto.value = Integer.parseInt(VariabileMonitored_Valore_3.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.delta_supporto.value = 0;
						}

						esecuzione.delta.set(esecuzione.delta_supporto);

						//Aggiornamento dell'ASM
						esecuzione.UpdateASM();

					}});
	}

}

