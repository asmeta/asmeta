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

public class ATM3_Win {

	ATM3 esecuzione;
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
							ATM3_Win window = new ATM3_Win();
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
public ATM3_Win() {

		esecuzione = new ATM3();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
private void initialize() {

		//Pannello principale
		frame = new JFrame();
		//Titolo 
		frame.setTitle("ATM3 ASM -> Java");
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

		JLabel VariabileControlled_Text_2 = new JLabel("currCard");
		VariabileControlled_Text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_2 );

		JTextField VariabileControlled_Valore_2 = new JTextField();
		VariabileControlled_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_2);
		VariabileControlled_Valore_2.setColumns(10);

		JLabel VariabileControlled_Text_3 = new JLabel("atmState");
		VariabileControlled_Text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_3 );

		JTextField VariabileControlled_Valore_3 = new JTextField();
		VariabileControlled_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_3);
		VariabileControlled_Valore_3.setColumns(10);

		JLabel VariabileControlled_Text_4 = new JLabel("moneyLeft");
		VariabileControlled_Text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4 );

		JTextField VariabileControlled_Valore_4 = new JTextField();
		VariabileControlled_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4);
		VariabileControlled_Valore_4.setColumns(10);

		JLabel VariabileControlled_Text_5 = new JLabel("numOfBalanceChecks");
		VariabileControlled_Text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_5 );

		JTextField VariabileControlled_Valore_5 = new JTextField();
		VariabileControlled_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_5.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_5);
		VariabileControlled_Valore_5.setColumns(10);

		System.out.println("\nElenco valori per insertedCard :");
		for(int i=0; i< esecuzione.NumCard_lista.size(); i++)
		System.out.println( (i+1) + " -> " +esecuzione.NumCard_lista.get(i));

		JLabel VariabileMonitored_text_1 = new JLabel("insertedCard");
		VariabileMonitored_text_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_1);

		JTextField VariabileMonitored_Valore_1 = new JTextField();
		VariabileMonitored_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_1);
		VariabileMonitored_Valore_1.setColumns(10);

		try {
			VariabileMonitored_Valore_1.setText(esecuzione.insertedCard.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_1.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_2 = new JLabel("insertedPin");
		VariabileMonitored_text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_2);

		JTextField VariabileMonitored_Valore_2 = new JTextField();
		VariabileMonitored_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_2);
		VariabileMonitored_Valore_2.setColumns(10);

		try {
			VariabileMonitored_Valore_2.setText(esecuzione.insertedPin.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_2.setText("Insert Integer");
		}

		System.out.println("\nElenco valori per selectedService :");
		for(int i=0; i< esecuzione.Service_lista.size(); i++)
		System.out.println( (i+1) + " -> " +esecuzione.Service_lista.get(i));

		JLabel VariabileMonitored_text_3 = new JLabel("selectedService");
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

		JLabel VariabileMonitored_text_4 = new JLabel("insertMoneySizeStandard");
		VariabileMonitored_text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_4);

		JTextField VariabileMonitored_Valore_4 = new JTextField();
		VariabileMonitored_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_4);
		VariabileMonitored_Valore_4.setColumns(10);

		try {
			VariabileMonitored_Valore_4.setText(esecuzione.insertMoneySizeStandard.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_4.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_5 = new JLabel("insertMoneySizeOther");
		VariabileMonitored_text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_5);

		JTextField VariabileMonitored_Valore_5 = new JTextField();
		VariabileMonitored_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_5);
		VariabileMonitored_Valore_5.setColumns(10);

		try {
			VariabileMonitored_Valore_5.setText(esecuzione.insertMoneySizeOther.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_5.setText("Insert Integer");
		}

		System.out.println("\nElenco valori per standardOrOther :");
		for(int i=0; i< esecuzione.MoneySizeSelection_lista.size(); i++)
		System.out.println( (i+1) + " -> " +esecuzione.MoneySizeSelection_lista.get(i));

		JLabel VariabileMonitored_text_6 = new JLabel("standardOrOther");
		VariabileMonitored_text_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_6);

		JTextField VariabileMonitored_Valore_6 = new JTextField();
		VariabileMonitored_Valore_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_6);
		VariabileMonitored_Valore_6.setColumns(10);

		try {
			VariabileMonitored_Valore_6.setText("Insert Integer");
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_6.setText("Insert Integer");
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

						try {
							VariabileControlled_Valore_2.setText(esecuzione.atmState.oldValue.name());
						} catch( NullPointerException errC2)
						{
							VariabileControlled_Valore_2.setText("null");
						}

						try {
							VariabileControlled_Valore_3.setText(esecuzione.moneyLeft.get().toString());
						} catch( NullPointerException errC3)
						{
							VariabileControlled_Valore_3.setText("null");
						}

						try {
							VariabileControlled_Valore_4.setText(esecuzione.numOfBalanceChecks.get().toString());
						} catch( NullPointerException errC4)
						{
							VariabileControlled_Valore_4.setText("null");
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
						esecuzione.insertedCard.set(esecuzione.NumCard_Class.get(0));
						else
						esecuzione.insertedCard.set(esecuzione.NumCard_Class.get(sup1-1));

						try
						{
							esecuzione.insertedPin.Value = Integer.parseInt(VariabileMonitored_Valore_2.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.insertedPin.Value = 0;
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

						if(sup3<= 0 || sup3> esecuzione.Service_lista.size())
						esecuzione.selectedService.set(esecuzione.Service_lista.get(0));
						else
						esecuzione.selectedService.set(esecuzione.Service_lista.get(sup3-1));

						try
						{
							esecuzione.insertMoneySizeStandard_supporto.value = Integer.parseInt(VariabileMonitored_Valore_4.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.insertMoneySizeStandard_supporto.value = 0;
						}

						esecuzione.insertMoneySizeStandard.set(esecuzione.insertMoneySizeStandard_supporto);

						try
						{
							esecuzione.insertMoneySizeOther.Value = Integer.parseInt(VariabileMonitored_Valore_5.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.insertMoneySizeOther.Value = 0;
						}

						int sup6;
						try
						{
							sup6 = Integer.parseInt(VariabileMonitored_Valore_6.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							sup6 = 1;
						}

						if(sup6<= 0 || sup6> esecuzione.MoneySizeSelection_lista.size())
						esecuzione.standardOrOther.set(esecuzione.MoneySizeSelection_lista.get(0));
						else
						esecuzione.standardOrOther.set(esecuzione.MoneySizeSelection_lista.get(sup6-1));

						//Aggiornamento dell'ASM
						esecuzione.UpdateASM();

					}});
	}

}

