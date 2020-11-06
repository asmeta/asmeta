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

public class testDefinition_Win {

	testDefinition esecuzione;
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
							testDefinition_Win window = new testDefinition_Win();
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
public testDefinition_Win() {

		esecuzione = new testDefinition();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
private void initialize() {

		//Pannello principale
		frame = new JFrame();
		//Titolo 
		frame.setTitle("testDefinition ASM -> Java");
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

		JLabel VariabileControlled_Text_2 = new JLabel("Till");
		VariabileControlled_Text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_2 );

		JTextField VariabileControlled_Valore_2 = new JTextField();
		VariabileControlled_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_2);
		VariabileControlled_Valore_2.setColumns(10);

		JLabel VariabileControlled_Text_3 = new JLabel("Card");
		VariabileControlled_Text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_3 );

		JTextField VariabileControlled_Valore_3 = new JTextField();
		VariabileControlled_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_3);
		VariabileControlled_Valore_3.setColumns(10);

		JLabel VariabileControlled_Text_4 = new JLabel("Date");
		VariabileControlled_Text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4 );

		JTextField VariabileControlled_Valore_4 = new JTextField();
		VariabileControlled_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4);
		VariabileControlled_Valore_4.setColumns(10);

		JLabel VariabileControlled_Text_5 = new JLabel("Account");
		VariabileControlled_Text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_5 );

		JTextField VariabileControlled_Valore_5 = new JTextField();
		VariabileControlled_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_5.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_5);
		VariabileControlled_Valore_5.setColumns(10);

		JLabel VariabileControlled_Text_6 = new JLabel("seconds");
		VariabileControlled_Text_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_6 );

		JTextField VariabileControlled_Valore_6 = new JTextField();
		VariabileControlled_Valore_6.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_6.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_6);
		VariabileControlled_Valore_6.setColumns(10);

		JLabel VariabileControlled_Text_7 = new JLabel("minutes");
		VariabileControlled_Text_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_7 );

		JTextField VariabileControlled_Valore_7 = new JTextField();
		VariabileControlled_Valore_7.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_7.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_7);
		VariabileControlled_Valore_7.setColumns(10);

		JLabel VariabileControlled_Text_8 = new JLabel("hours");
		VariabileControlled_Text_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_8 );

		JTextField VariabileControlled_Valore_8 = new JTextField();
		VariabileControlled_Valore_8.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_8.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_8);
		VariabileControlled_Valore_8.setColumns(10);

		JLabel VariabileControlled_Text_9 = new JLabel("available");
		VariabileControlled_Text_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_9 );

		JTextField VariabileControlled_Valore_9 = new JTextField();
		VariabileControlled_Valore_9.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_9.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_9);
		VariabileControlled_Valore_9.setColumns(10);

		JLabel VariabileControlled_Text_9_1 = new JLabel(esecuzione.Product_lista.get(0).toString());
		VariabileControlled_Text_9_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_9_1);

		JTextField VariabileControlled_Valore_9_1 = new JTextField();
		VariabileControlled_Valore_9_1.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_9_1.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_9_1);
		VariabileControlled_Valore_9_1.setColumns(10);

		JLabel VariabileControlled_Text_9_2 = new JLabel(esecuzione.Product_lista.get(1).toString());
		VariabileControlled_Text_9_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_9_2);

		JTextField VariabileControlled_Valore_9_2 = new JTextField();
		VariabileControlled_Valore_9_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_9_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_9_2);
		VariabileControlled_Valore_9_2.setColumns(10);

		JLabel VariabileControlled_Text_9_3 = new JLabel(esecuzione.Product_lista.get(2).toString());
		VariabileControlled_Text_9_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_9_3);

		JTextField VariabileControlled_Valore_9_3 = new JTextField();
		VariabileControlled_Valore_9_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_9_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_9_3);
		VariabileControlled_Valore_9_3.setColumns(10);

		JLabel VariabileControlled_Text_10 = new JLabel("coins");
		VariabileControlled_Text_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_10 );

		JTextField VariabileControlled_Valore_10 = new JTextField();
		VariabileControlled_Valore_10.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_10.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_10);
		VariabileControlled_Valore_10.setColumns(10);

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

						String Till_val="";
						for(int i=0; i< esecuzione.Till_lista.size(); i++)
						if(i!= esecuzione.Till_lista.size()-1)
						Till_val += esecuzione.Till_lista.get(i) +", ";
						else
						Till_val += esecuzione.Till_lista.get(i);

						try {
							VariabileControlled_Valore_2.setText(Till_val);
						} catch( NullPointerException errC2)
						{
							VariabileControlled_Valore_2.setText("null");
						}

						String Card_val="";
						for(int i=0; i< esecuzione.Card_lista.size(); i++)
						if(i!= esecuzione.Card_lista.size()-1)
						Card_val += esecuzione.Card_lista.get(i) +", ";
						else
						Card_val += esecuzione.Card_lista.get(i);

						try {
							VariabileControlled_Valore_3.setText(Card_val);
						} catch( NullPointerException errC3)
						{
							VariabileControlled_Valore_3.setText("null");
						}

						String Date_val="";
						for(int i=0; i< esecuzione.Date_lista.size(); i++)
						if(i!= esecuzione.Date_lista.size()-1)
						Date_val += esecuzione.Date_lista.get(i) +", ";
						else
						Date_val += esecuzione.Date_lista.get(i);

						try {
							VariabileControlled_Valore_4.setText(Date_val);
						} catch( NullPointerException errC4)
						{
							VariabileControlled_Valore_4.setText("null");
						}

						String Account_val="";
						for(int i=0; i< esecuzione.Account_lista.size(); i++)
						if(i!= esecuzione.Account_lista.size()-1)
						Account_val += esecuzione.Account_lista.get(i) +", ";
						else
						Account_val += esecuzione.Account_lista.get(i);

						try {
							VariabileControlled_Valore_5.setText(Account_val);
						} catch( NullPointerException errC5)
						{
							VariabileControlled_Valore_5.setText("null");
						}

						try {
							VariabileControlled_Valore_6.setText(esecuzione.seconds.get().value.toString());
						} catch( NullPointerException errC6)
						{
							VariabileControlled_Valore_6.setText("null");
						}

						try {
							VariabileControlled_Valore_7.setText(esecuzione.minutes.get().value.toString());
						} catch( NullPointerException errC7)
						{
							VariabileControlled_Valore_7.setText("null");
						}

						try {
							VariabileControlled_Valore_8.setText(esecuzione.hours.get().value.toString());
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
							VariabileControlled_Valore_9_1.setText(esecuzione.available.oldValues.get(esecuzione.Product_lista.get(0)).value.toString());
						} catch( NullPointerException errC09)
						{
							VariabileControlled_Text_9_1.setText("null");
						}

						try {
							VariabileControlled_Valore_9_2.setText(esecuzione.available.oldValues.get(esecuzione.Product_lista.get(1)).value.toString());
						} catch( NullPointerException errC09)
						{
							VariabileControlled_Text_9_1.setText("null");
						}

						try {
							VariabileControlled_Valore_9_3.setText(esecuzione.available.oldValues.get(esecuzione.Product_lista.get(2)).value.toString());
						} catch( NullPointerException errC09)
						{
							VariabileControlled_Text_9_1.setText("null");
						}

						try {
							VariabileControlled_Valore_9.setText(esecuzione.coins.get().value.toString());
						} catch( NullPointerException errC9)
						{
							VariabileControlled_Valore_9.setText("null");
						}

						//"traduzione" della funzione askMonitored(esecuzione) del file _Exe

						//Aggiornamento dell'ASM
						esecuzione.UpdateASM();

					}});
	}

}

