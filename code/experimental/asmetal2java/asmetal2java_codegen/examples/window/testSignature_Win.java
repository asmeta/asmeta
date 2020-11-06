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

public class testSignature_Win {

	testSignature esecuzione;
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
							testSignature_Win window = new testSignature_Win();
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
public testSignature_Win() {

		esecuzione = new testSignature();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
private void initialize() {

		//Pannello principale
		frame = new JFrame();
		//Titolo 
		frame.setTitle("testSignature ASM -> Java");
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

		JLabel VariabileControlled_Text_1 = new JLabel("NumCard");
		VariabileControlled_Text_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_1 );

		JTextField VariabileControlled_Valore_1 = new JTextField();
		VariabileControlled_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_1.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_1);
		VariabileControlled_Valore_1.setColumns(10);

		JLabel VariabileControlled_Text_2 = new JLabel("Sfortuna");
		VariabileControlled_Text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_2 );

		JTextField VariabileControlled_Valore_2 = new JTextField();
		VariabileControlled_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_2);
		VariabileControlled_Valore_2.setColumns(10);

		JLabel VariabileControlled_Text_3 = new JLabel("Dinam");
		VariabileControlled_Text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_3 );

		JTextField VariabileControlled_Valore_3 = new JTextField();
		VariabileControlled_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_3);
		VariabileControlled_Valore_3.setColumns(10);

		JLabel VariabileControlled_Text_4 = new JLabel("dominioC1");
		VariabileControlled_Text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4 );

		JTextField VariabileControlled_Valore_4 = new JTextField();
		VariabileControlled_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4);
		VariabileControlled_Valore_4.setColumns(10);

		JLabel VariabileControlled_Text_5 = new JLabel("dominioC2");
		VariabileControlled_Text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_5 );

		JTextField VariabileControlled_Valore_5 = new JTextField();
		VariabileControlled_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_5.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_5);
		VariabileControlled_Valore_5.setColumns(10);

		JLabel VariabileControlled_Text_6 = new JLabel("dominioC3");
		VariabileControlled_Text_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_6 );

		JTextField VariabileControlled_Valore_6 = new JTextField();
		VariabileControlled_Valore_6.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_6.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_6);
		VariabileControlled_Valore_6.setColumns(10);

		JLabel VariabileControlled_Text_7 = new JLabel("dominioC4");
		VariabileControlled_Text_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_7 );

		JTextField VariabileControlled_Valore_7 = new JTextField();
		VariabileControlled_Valore_7.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_7.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_7);
		VariabileControlled_Valore_7.setColumns(10);

		JLabel VariabileControlled_Text_8 = new JLabel("dominioC5");
		VariabileControlled_Text_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_8 );

		JTextField VariabileControlled_Valore_8 = new JTextField();
		VariabileControlled_Valore_8.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_8.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_8);
		VariabileControlled_Valore_8.setColumns(10);

		JLabel VariabileControlled_Text_9 = new JLabel("dominioC6");
		VariabileControlled_Text_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_9 );

		JTextField VariabileControlled_Valore_9 = new JTextField();
		VariabileControlled_Valore_9.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_9.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_9);
		VariabileControlled_Valore_9.setColumns(10);

		JLabel VariabileControlled_Text_10 = new JLabel("funAbC");
		VariabileControlled_Text_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_10 );

		JTextField VariabileControlled_Valore_10 = new JTextField();
		VariabileControlled_Valore_10.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_10.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_10);
		VariabileControlled_Valore_10.setColumns(10);

		JLabel VariabileControlled_Text_11 = new JLabel("funC1");
		VariabileControlled_Text_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_11 );

		JTextField VariabileControlled_Valore_11 = new JTextField();
		VariabileControlled_Valore_11.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_11.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_11);
		VariabileControlled_Valore_11.setColumns(10);

		JLabel VariabileControlled_Text_12 = new JLabel("funC2");
		VariabileControlled_Text_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_12 );

		JTextField VariabileControlled_Valore_12 = new JTextField();
		VariabileControlled_Valore_12.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_12.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_12);
		VariabileControlled_Valore_12.setColumns(10);

		JLabel VariabileControlled_Text_13 = new JLabel("funC3");
		VariabileControlled_Text_13.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_13 );

		JTextField VariabileControlled_Valore_13 = new JTextField();
		VariabileControlled_Valore_13.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_13.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_13);
		VariabileControlled_Valore_13.setColumns(10);

		JLabel VariabileControlled_Text_14 = new JLabel("funC4");
		VariabileControlled_Text_14.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_14 );

		JTextField VariabileControlled_Valore_14 = new JTextField();
		VariabileControlled_Valore_14.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_14.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_14);
		VariabileControlled_Valore_14.setColumns(10);

		JLabel VariabileControlled_Text_15 = new JLabel("funC6");
		VariabileControlled_Text_15.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_15 );

		JTextField VariabileControlled_Valore_15 = new JTextField();
		VariabileControlled_Valore_15.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_15.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_15);
		VariabileControlled_Valore_15.setColumns(10);

		JLabel VariabileControlled_Text_15_1 = new JLabel(esecuzione.Color_lista.get(0).toString());
		VariabileControlled_Text_15_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_15_1);

		JTextField VariabileControlled_Valore_15_1 = new JTextField();
		VariabileControlled_Valore_15_1.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_15_1.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_15_1);
		VariabileControlled_Valore_15_1.setColumns(10);

		JLabel VariabileControlled_Text_15_2 = new JLabel(esecuzione.Color_lista.get(1).toString());
		VariabileControlled_Text_15_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_15_2);

		JTextField VariabileControlled_Valore_15_2 = new JTextField();
		VariabileControlled_Valore_15_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_15_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_15_2);
		VariabileControlled_Valore_15_2.setColumns(10);

		JLabel VariabileControlled_Text_15_3 = new JLabel(esecuzione.Color_lista.get(2).toString());
		VariabileControlled_Text_15_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_15_3);

		JTextField VariabileControlled_Valore_15_3 = new JTextField();
		VariabileControlled_Valore_15_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_15_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_15_3);
		VariabileControlled_Valore_15_3.setColumns(10);

		JLabel VariabileControlled_Text_16 = new JLabel("bool");
		VariabileControlled_Text_16.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_16 );

		JTextField VariabileControlled_Valore_16 = new JTextField();
		VariabileControlled_Valore_16.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_16.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_16);
		VariabileControlled_Valore_16.setColumns(10);

		System.out.println("\nElenco valori per funAbM :");
		for(int i=0; i< esecuzione.NumCard_lista.size(); i++)
		System.out.println( (i+1) + " -> " +esecuzione.NumCard_lista.get(i));

		JLabel VariabileMonitored_text_1 = new JLabel("funAbM");
		VariabileMonitored_text_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_1);

		JTextField VariabileMonitored_Valore_1 = new JTextField();
		VariabileMonitored_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_1);
		VariabileMonitored_Valore_1.setColumns(10);

		try {
			VariabileMonitored_Valore_1.setText(esecuzione.funAbM.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_1.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_2 = new JLabel("funM1");
		VariabileMonitored_text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_2);

		JTextField VariabileMonitored_Valore_2 = new JTextField();
		VariabileMonitored_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_2);
		VariabileMonitored_Valore_2.setColumns(10);

		try {
			VariabileMonitored_Valore_2.setText(esecuzione.funM1.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_2.setText("Insert Integer");
		}

		System.out.println("\nElenco valori per funM2 :");
		for(int i=0; i< esecuzione.Color_lista.size(); i++)
		System.out.println( (i+1) + " -> " +esecuzione.Color_lista.get(i));

		JLabel VariabileMonitored_text_3 = new JLabel("funM2");
		VariabileMonitored_text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_3);

		JTextField VariabileMonitored_Valore_3 = new JTextField();
		VariabileMonitored_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_3);
		VariabileMonitored_Valore_3.setColumns(10);

		try {
			VariabileMonitored_Valore_3.setText("Insert Integer");
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_3.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_4 = new JLabel("funM3");
		VariabileMonitored_text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_4);

		JTextField VariabileMonitored_Valore_4 = new JTextField();
		VariabileMonitored_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_4);
		VariabileMonitored_Valore_4.setColumns(10);

		try {
			VariabileMonitored_Valore_4.setText(esecuzione.funM3.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_4.setText("Insert Integer");
		}

		System.out.println("\nElenco valori per funM4 :");
		for(int i=0; i< esecuzione.NumCard_lista.size(); i++)
		System.out.println( (i+1) + " -> " +esecuzione.NumCard_lista.get(i));

		JLabel VariabileMonitored_text_5 = new JLabel("funM4");
		VariabileMonitored_text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_5);

		JTextField VariabileMonitored_Valore_5 = new JTextField();
		VariabileMonitored_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_5);
		VariabileMonitored_Valore_5.setColumns(10);

		try {
			VariabileMonitored_Valore_5.setText(esecuzione.funM4.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_5.setText("Insert Integer");
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

						String NumCard_val="";
						for(int i=0; i< esecuzione.NumCard_lista.size(); i++)
						if(i!= esecuzione.NumCard_lista.size()-1)
						NumCard_val += esecuzione.NumCard_lista.get(i) +", ";
						else
						NumCard_val += esecuzione.NumCard_lista.get(i);

						try {
							VariabileControlled_Valore_1.setText(NumCard_val);
						} catch( NullPointerException errC1)
						{
							VariabileControlled_Valore_1.setText("null");
						}

						String Sfortuna_val="";
						for(int i=0; i< esecuzione.Sfortuna_lista.size(); i++)
						if(i!= esecuzione.Sfortuna_lista.size()-1)
						Sfortuna_val += esecuzione.Sfortuna_lista.get(i) +", ";
						else
						Sfortuna_val += esecuzione.Sfortuna_lista.get(i);

						try {
							VariabileControlled_Valore_2.setText(Sfortuna_val);
						} catch( NullPointerException errC2)
						{
							VariabileControlled_Valore_2.setText("null");
						}

						String Dinam_val="";
						for(int i=0; i< esecuzione.Dinam_lista.size(); i++)
						if(i!= esecuzione.Dinam_lista.size()-1)
						Dinam_val += esecuzione.Dinam_lista.get(i) +", ";
						else
						Dinam_val += esecuzione.Dinam_lista.get(i);

						try {
							VariabileControlled_Valore_3.setText(Dinam_val);
						} catch( NullPointerException errC3)
						{
							VariabileControlled_Valore_3.setText("null");
						}

						try {
							VariabileControlled_Valore_4.setText(esecuzione.dominioC1.get().toString());
						} catch( NullPointerException errC4)
						{
							VariabileControlled_Valore_4.setText("null");
						}

						try {
							VariabileControlled_Valore_5.setText(esecuzione.dominioC6.get().toString());
						} catch( NullPointerException errC5)
						{
							VariabileControlled_Valore_5.setText("null");
						}

						try {
							VariabileControlled_Valore_6.setText(esecuzione.funC1.get().toString());
						} catch( NullPointerException errC6)
						{
							VariabileControlled_Valore_6.setText("null");
						}

						try {
							VariabileControlled_Valore_7.setText(esecuzione.funC2.oldValue.name());
						} catch( NullPointerException errC7)
						{
							VariabileControlled_Valore_7.setText("null");
						}

						try {
							VariabileControlled_Valore_8.setText(esecuzione.funC3.get().value.toString());
						} catch( NullPointerException errC8)
						{
							VariabileControlled_Valore_8.setText("null");
						}

						try {
							VariabileControlled_Valore_9.setText("");
						} catch( NullPointerException errC9)
						{
							VariabileControlled_Valore_9.setText("null");
						}

						try {
							VariabileControlled_Valore_9_1.setText(esecuzione.funC6.oldValues.get(esecuzione.Color_lista.get(0)).value.toString());
						} catch( NullPointerException errC09)
						{
							VariabileControlled_Text_9_1.setText("null");
						}

						try {
							VariabileControlled_Valore_9_2.setText(esecuzione.funC6.oldValues.get(esecuzione.Color_lista.get(1)).value.toString());
						} catch( NullPointerException errC09)
						{
							VariabileControlled_Text_9_1.setText("null");
						}

						try {
							VariabileControlled_Valore_9_3.setText(esecuzione.funC6.oldValues.get(esecuzione.Color_lista.get(2)).value.toString());
						} catch( NullPointerException errC09)
						{
							VariabileControlled_Text_9_1.setText("null");
						}

						try {
							VariabileControlled_Valore_9.setText(esecuzione.bool.get().toString());
						} catch( NullPointerException errC9)
						{
							VariabileControlled_Valore_9.setText("null");
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

						if(sup1<= 0 || sup1> esecuzione.NumCard_Class.size())
						esecuzione.funAbM.set(esecuzione.NumCard_Class.get(0));
						else
						esecuzione.funAbM.set(esecuzione.NumCard_Class.get(sup1-1));

						try
						{
							esecuzione.funM1.Value = Integer.parseInt(VariabileMonitored_Valore_2.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.funM1.Value = 0;
						}

						int sup3;
						try
						{
							sup3 = Integer.parseInt(VariabileMonitored_Valore_3.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							sup3 = 1;
						}

						if(sup3<= 0 || sup3> esecuzione.Color_lista.size())
						esecuzione.funM2.set(esecuzione.Color_lista.get(0));
						else
						esecuzione.funM2.set(esecuzione.Color_lista.get(sup3-1));

						try
						{
							esecuzione.funM3_supporto.value = Integer.parseInt(VariabileMonitored_Valore_4.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.funM3_supporto.value = 0;
						}

						esecuzione.funM3.set(esecuzione.funM3_supporto);

						int sup5;
						try
						{
							sup5 = Integer.parseInt(VariabileMonitored_Valore_5.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							sup5 = 1;
						}

						if(sup5<= 0 || sup5> esecuzione.NumCard_Class.size())
						esecuzione.funM4.set(esecuzione.NumCard_Class.get(0));
						else
						esecuzione.funM4.set(esecuzione.NumCard_Class.get(sup5-1));

						//Aggiornamento dell'ASM
						esecuzione.UpdateASM();

					}});
	}

}

