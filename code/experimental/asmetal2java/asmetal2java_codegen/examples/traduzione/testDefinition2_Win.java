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

public class testDefinition2_Win {

	testDefinition2 esecuzione;
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
							testDefinition2_Win window = new testDefinition2_Win();
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
public testDefinition2_Win() {

		esecuzione = new testDefinition2();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
private void initialize() {

		//Pannello principale
		frame = new JFrame();
		//Titolo 
		frame.setTitle("testDefinition2 ASM -> Java");
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

		JLabel VariabileControlled_Text_1 = new JLabel("Lift");
		VariabileControlled_Text_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_1 );

		JTextField VariabileControlled_Valore_1 = new JTextField();
		VariabileControlled_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_1.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_1);
		VariabileControlled_Valore_1.setColumns(10);

		JLabel VariabileControlled_Text_2 = new JLabel("numA");
		VariabileControlled_Text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_2 );

		JTextField VariabileControlled_Valore_2 = new JTextField();
		VariabileControlled_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_2);
		VariabileControlled_Valore_2.setColumns(10);

		JLabel VariabileControlled_Text_3 = new JLabel("numB");
		VariabileControlled_Text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_3 );

		JTextField VariabileControlled_Valore_3 = new JTextField();
		VariabileControlled_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_3);
		VariabileControlled_Valore_3.setColumns(10);

		JLabel VariabileControlled_Text_4 = new JLabel("available");
		VariabileControlled_Text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4 );

		JTextField VariabileControlled_Valore_4 = new JTextField();
		VariabileControlled_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4);
		VariabileControlled_Valore_4.setColumns(10);

		JLabel VariabileControlled_Text_4_1 = new JLabel(esecuzione.Product_lista.get(0).toString());
		VariabileControlled_Text_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4_1);

		JTextField VariabileControlled_Valore_4_1 = new JTextField();
		VariabileControlled_Valore_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4_1.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4_1);
		VariabileControlled_Valore_4_1.setColumns(10);

		JLabel VariabileControlled_Text_4_2 = new JLabel(esecuzione.Product_lista.get(1).toString());
		VariabileControlled_Text_4_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4_2);

		JTextField VariabileControlled_Valore_4_2 = new JTextField();
		VariabileControlled_Valore_4_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4_2);
		VariabileControlled_Valore_4_2.setColumns(10);

		JLabel VariabileControlled_Text_4_3 = new JLabel(esecuzione.Product_lista.get(2).toString());
		VariabileControlled_Text_4_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4_3);

		JTextField VariabileControlled_Valore_4_3 = new JTextField();
		VariabileControlled_Valore_4_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4_3);
		VariabileControlled_Valore_4_3.setColumns(10);

		JLabel VariabileControlled_Text_5 = new JLabel("coins");
		VariabileControlled_Text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_5 );

		JTextField VariabileControlled_Valore_5 = new JTextField();
		VariabileControlled_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_5.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_5);
		VariabileControlled_Valore_5.setColumns(10);

		JLabel VariabileControlled_Text_6 = new JLabel("position");
		VariabileControlled_Text_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_6 );

		JTextField VariabileControlled_Valore_6 = new JTextField();
		VariabileControlled_Valore_6.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_6.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_6);
		VariabileControlled_Valore_6.setColumns(10);

		JLabel VariabileControlled_Text_6_1 = new JLabel(esecuzione.Actors_lista.get(0).toString());
		VariabileControlled_Text_6_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_6_1);

		JTextField VariabileControlled_Valore_6_1 = new JTextField();
		VariabileControlled_Valore_6_1.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_6_1.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_6_1);
		VariabileControlled_Valore_6_1.setColumns(10);

		JLabel VariabileControlled_Text_6_2 = new JLabel(esecuzione.Actors_lista.get(1).toString());
		VariabileControlled_Text_6_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_6_2);

		JTextField VariabileControlled_Valore_6_2 = new JTextField();
		VariabileControlled_Valore_6_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_6_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_6_2);
		VariabileControlled_Valore_6_2.setColumns(10);

		JLabel VariabileControlled_Text_6_3 = new JLabel(esecuzione.Actors_lista.get(2).toString());
		VariabileControlled_Text_6_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_6_3);

		JTextField VariabileControlled_Valore_6_3 = new JTextField();
		VariabileControlled_Valore_6_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_6_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_6_3);
		VariabileControlled_Valore_6_3.setColumns(10);

		JLabel VariabileControlled_Text_6_4 = new JLabel(esecuzione.Actors_lista.get(3).toString());
		VariabileControlled_Text_6_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_6_4);

		JTextField VariabileControlled_Valore_6_4 = new JTextField();
		VariabileControlled_Valore_6_4.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_6_4.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_6_4);
		VariabileControlled_Valore_6_4.setColumns(10);

		JLabel VariabileControlled_Text_7 = new JLabel("num_fibo");
		VariabileControlled_Text_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_7 );

		JTextField VariabileControlled_Valore_7 = new JTextField();
		VariabileControlled_Valore_7.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_7.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_7);
		VariabileControlled_Valore_7.setColumns(10);

		JLabel VariabileControlled_Text_8 = new JLabel("indice");
		VariabileControlled_Text_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_8 );

		JTextField VariabileControlled_Valore_8 = new JTextField();
		VariabileControlled_Valore_8.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_8.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_8);
		VariabileControlled_Valore_8.setColumns(10);

		JLabel VariabileControlled_Text_9 = new JLabel("succ_fibo");
		VariabileControlled_Text_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_9 );

		JTextField VariabileControlled_Valore_9 = new JTextField();
		VariabileControlled_Valore_9.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_9.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_9);
		VariabileControlled_Valore_9.setColumns(10);

		JLabel VariabileControlled_Text_10 = new JLabel("ctl_state");
		VariabileControlled_Text_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_10 );

		JTextField VariabileControlled_Valore_10 = new JTextField();
		VariabileControlled_Valore_10.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_10.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_10);
		VariabileControlled_Valore_10.setColumns(10);

		JLabel VariabileControlled_Text_11 = new JLabel("doors");
		VariabileControlled_Text_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_11 );

		JTextField VariabileControlled_Valore_11 = new JTextField();
		VariabileControlled_Valore_11.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_11.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_11);
		VariabileControlled_Valore_11.setColumns(10);

		JLabel VariabileControlled_Text_12 = new JLabel("gears");
		VariabileControlled_Text_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_12 );

		JTextField VariabileControlled_Valore_12 = new JTextField();
		VariabileControlled_Valore_12.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_12.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_12);
		VariabileControlled_Valore_12.setColumns(10);

		JLabel VariabileControlled_Text_13 = new JLabel("generalElectroValve");
		VariabileControlled_Text_13.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_13 );

		JTextField VariabileControlled_Valore_13 = new JTextField();
		VariabileControlled_Valore_13.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_13.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_13);
		VariabileControlled_Valore_13.setColumns(10);

		JLabel VariabileControlled_Text_14 = new JLabel("openDoorsElectroValve");
		VariabileControlled_Text_14.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_14 );

		JTextField VariabileControlled_Valore_14 = new JTextField();
		VariabileControlled_Valore_14.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_14.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_14);
		VariabileControlled_Valore_14.setColumns(10);

		JLabel VariabileControlled_Text_15 = new JLabel("closeDoorsElectroValve");
		VariabileControlled_Text_15.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_15 );

		JTextField VariabileControlled_Valore_15 = new JTextField();
		VariabileControlled_Valore_15.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_15.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_15);
		VariabileControlled_Valore_15.setColumns(10);

		JLabel VariabileControlled_Text_16 = new JLabel("retractGearsElectroValve");
		VariabileControlled_Text_16.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_16 );

		JTextField VariabileControlled_Valore_16 = new JTextField();
		VariabileControlled_Valore_16.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_16.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_16);
		VariabileControlled_Valore_16.setColumns(10);

		JLabel VariabileControlled_Text_17 = new JLabel("extendGearsElectroValve");
		VariabileControlled_Text_17.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_17 );

		JTextField VariabileControlled_Valore_17 = new JTextField();
		VariabileControlled_Valore_17.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_17.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_17);
		VariabileControlled_Valore_17.setColumns(10);

		JLabel VariabileControlled_Text_18 = new JLabel("controlledfunction");
		VariabileControlled_Text_18.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_18 );

		JTextField VariabileControlled_Valore_18 = new JTextField();
		VariabileControlled_Valore_18.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_18.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_18);
		VariabileControlled_Valore_18.setColumns(10);

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

		JLabel VariabileMonitored_text_3 = new JLabel("monitoredfunction");
		VariabileMonitored_text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_3);

		JTextField VariabileMonitored_Valore_3 = new JTextField();
		VariabileMonitored_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_3);
		VariabileMonitored_Valore_3.setColumns(10);

		try {
			VariabileMonitored_Valore_3.setText(esecuzione.monitoredfunction.Value.toString());
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

						String Lift_val="";
						for(int i=0; i< esecuzione.Lift_lista.size(); i++)
						if(i!= esecuzione.Lift_lista.size()-1)
						Lift_val += esecuzione.Lift_lista.get(i) +", ";
						else
						Lift_val += esecuzione.Lift_lista.get(i);

						try {
							VariabileControlled_Valore_1.setText(Lift_val);
						} catch( NullPointerException errC1)
						{
							VariabileControlled_Valore_1.setText("null");
						}

						try {
							VariabileControlled_Valore_2.setText(esecuzione.numA.get().toString());
						} catch( NullPointerException errC2)
						{
							VariabileControlled_Valore_2.setText("null");
						}

						try {
							VariabileControlled_Valore_3.setText(esecuzione.numB.get().toString());
						} catch( NullPointerException errC3)
						{
							VariabileControlled_Valore_3.setText("null");
						}

						try {
							VariabileControlled_Valore_4.setText("");
						} catch( NullPointerException errC4)
						{
							VariabileControlled_Valore_4.setText("null");
						}

						try {
							VariabileControlled_Valore_4_1.setText(esecuzione.available.oldValues.get(esecuzione.Product_lista.get(0)).value.toString());
						} catch( NullPointerException errC04)
						{
							VariabileControlled_Text_4_1.setText("null");
						}

						try {
							VariabileControlled_Valore_4_2.setText(esecuzione.available.oldValues.get(esecuzione.Product_lista.get(1)).value.toString());
						} catch( NullPointerException errC04)
						{
							VariabileControlled_Text_4_1.setText("null");
						}

						try {
							VariabileControlled_Valore_4_3.setText(esecuzione.available.oldValues.get(esecuzione.Product_lista.get(2)).value.toString());
						} catch( NullPointerException errC04)
						{
							VariabileControlled_Text_4_1.setText("null");
						}

						try {
							VariabileControlled_Valore_4.setText(esecuzione.coins.get().value.toString());
						} catch( NullPointerException errC4)
						{
							VariabileControlled_Valore_4.setText("null");
						}

						try {
							VariabileControlled_Valore_5.setText("");
						} catch( NullPointerException errC5)
						{
							VariabileControlled_Text_5.setText("null");
						}

						try {
							VariabileControlled_Valore_5_1.setText(esecuzione.position.oldValues.get(esecuzione.Actors_lista.get(0)).toString());
						} catch( NullPointerException errC05)
						{
							VariabileControlled_Valore_5_1.setText("null");
						}

						try {
							VariabileControlled_Valore_5_2.setText(esecuzione.position.oldValues.get(esecuzione.Actors_lista.get(1)).toString());
						} catch( NullPointerException errC05)
						{
							VariabileControlled_Valore_5_2.setText("null");
						}
						try {
							VariabileControlled_Valore_5_3.setText(esecuzione.position.oldValues.get(esecuzione.Actors_lista.get(2)).toString());
						} catch( NullPointerException errC05)
						{
							VariabileControlled_Valore_5_3.setText("null");
						}

						try {
							VariabileControlled_Valore_5_4.setText(esecuzione.position.oldValues.get(esecuzione.Actors_lista.get(3)).toString());
						} catch( NullPointerException errC05)
						{
							VariabileControlled_Valore_5_4.setText("null");
						}

						try {
							VariabileControlled_Valore_6.setText(esecuzione.num_fibo.get().toString());
						} catch( NullPointerException errC6)
						{
							VariabileControlled_Valore_6.setText("null");
						}

						try {
							VariabileControlled_Valore_7.setText(esecuzione.indice.get().toString());
						} catch( NullPointerException errC7)
						{
							VariabileControlled_Valore_7.setText("null");
						}

						try {
							VariabileControlled_Valore_8.setText(esecuzione.succ_fibo.get().toString());
						} catch( NullPointerException errC8)
						{
							VariabileControlled_Valore_8.setText("null");
						}

						try {
							VariabileControlled_Valore_9.setText(esecuzione.ctl_state.get().value.toString());
						} catch( NullPointerException errC9)
						{
							VariabileControlled_Valore_9.setText("null");
						}

						try {
							VariabileControlled_Valore_10.setText(esecuzione.doors.oldValue.name());
						} catch( NullPointerException errC10)
						{
							VariabileControlled_Valore_10.setText("null");
						}

						try {
							VariabileControlled_Valore_11.setText(esecuzione.gears.oldValue.name());
						} catch( NullPointerException errC11)
						{
							VariabileControlled_Valore_11.setText("null");
						}

						try {
							VariabileControlled_Valore_12.setText(esecuzione.generalElectroValve.get().toString());
						} catch( NullPointerException errC12)
						{
							VariabileControlled_Valore_12.setText("null");
						}

						try {
							VariabileControlled_Valore_13.setText(esecuzione.openDoorsElectroValve.get().toString());
						} catch( NullPointerException errC13)
						{
							VariabileControlled_Valore_13.setText("null");
						}

						try {
							VariabileControlled_Valore_14.setText(esecuzione.closeDoorsElectroValve.get().toString());
						} catch( NullPointerException errC14)
						{
							VariabileControlled_Valore_14.setText("null");
						}

						try {
							VariabileControlled_Valore_15.setText(esecuzione.retractGearsElectroValve.get().toString());
						} catch( NullPointerException errC15)
						{
							VariabileControlled_Valore_15.setText("null");
						}

						try {
							VariabileControlled_Valore_16.setText(esecuzione.extendGearsElectroValve.get().toString());
						} catch( NullPointerException errC16)
						{
							VariabileControlled_Valore_16.setText("null");
						}

						try {
							VariabileControlled_Valore_17.setText(esecuzione.controlledfunction.get().toString());
						} catch( NullPointerException errC17)
						{
							VariabileControlled_Valore_17.setText("null");
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

						try
						{
							esecuzione.monitoredfunction.Value = Integer.parseInt(VariabileMonitored_Valore_3.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.monitoredfunction.Value = 0;
						}

						//Aggiornamento dell'ASM
						esecuzione.UpdateASM();

					}});
	}

}

