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

public class Hemodialysis_ref4_forMC_Win {

	Hemodialysis_ref4_forMC esecuzione;
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
							Hemodialysis_ref4_forMC_Win window = new Hemodialysis_ref4_forMC_Win();
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
public Hemodialysis_ref4_forMC_Win() {

		esecuzione = new Hemodialysis_ref4_forMC();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
private void initialize() {

		//Pannello principale
		frame = new JFrame();
		//Titolo 
		frame.setTitle("Hemodialysis_ref4_forMC ASM -> Java");
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

		JLabel VariabileControlled_Text_1 = new JLabel("phaseTherapy");
		VariabileControlled_Text_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_1 );

		JTextField VariabileControlled_Valore_1 = new JTextField();
		VariabileControlled_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_1.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_1);
		VariabileControlled_Valore_1.setColumns(10);

		JLabel VariabileControlled_Text_2 = new JLabel("modePreparation");
		VariabileControlled_Text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_2 );

		JTextField VariabileControlled_Valore_2 = new JTextField();
		VariabileControlled_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_2.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_2);
		VariabileControlled_Valore_2.setColumns(10);

		JLabel VariabileControlled_Text_3 = new JLabel("modeInitiation");
		VariabileControlled_Text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_3 );

		JTextField VariabileControlled_Valore_3 = new JTextField();
		VariabileControlled_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_3.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_3);
		VariabileControlled_Valore_3.setColumns(10);

		JLabel VariabileControlled_Text_4 = new JLabel("modeEnding");
		VariabileControlled_Text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_4 );

		JTextField VariabileControlled_Valore_4 = new JTextField();
		VariabileControlled_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_4.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_4);
		VariabileControlled_Valore_4.setColumns(10);

		JLabel VariabileControlled_Text_5 = new JLabel("rinsingParam");
		VariabileControlled_Text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_5 );

		JTextField VariabileControlled_Valore_5 = new JTextField();
		VariabileControlled_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_5.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_5);
		VariabileControlled_Valore_5.setColumns(10);

		JLabel VariabileControlled_Text_6 = new JLabel("tubingPhase");
		VariabileControlled_Text_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_6 );

		JTextField VariabileControlled_Valore_6 = new JTextField();
		VariabileControlled_Valore_6.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_6.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_6);
		VariabileControlled_Valore_6.setColumns(10);

		JLabel VariabileControlled_Text_7 = new JLabel("treatmentParam");
		VariabileControlled_Text_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_7 );

		JTextField VariabileControlled_Valore_7 = new JTextField();
		VariabileControlled_Valore_7.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_7.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_7);
		VariabileControlled_Valore_7.setColumns(10);

		JLabel VariabileControlled_Text_8 = new JLabel("rinsePhase");
		VariabileControlled_Text_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_8 );

		JTextField VariabileControlled_Valore_8 = new JTextField();
		VariabileControlled_Valore_8.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_8.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_8);
		VariabileControlled_Valore_8.setColumns(10);

		JLabel VariabileControlled_Text_9 = new JLabel("patientPhase");
		VariabileControlled_Text_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_9 );

		JTextField VariabileControlled_Valore_9 = new JTextField();
		VariabileControlled_Valore_9.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_9.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_9);
		VariabileControlled_Valore_9.setColumns(10);

		JLabel VariabileControlled_Text_10 = new JLabel("therapyPhase");
		VariabileControlled_Text_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_10 );

		JTextField VariabileControlled_Valore_10 = new JTextField();
		VariabileControlled_Valore_10.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_10.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_10);
		VariabileControlled_Valore_10.setColumns(10);

		JLabel VariabileControlled_Text_11 = new JLabel("reinfusionPhase");
		VariabileControlled_Text_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_11 );

		JTextField VariabileControlled_Valore_11 = new JTextField();
		VariabileControlled_Valore_11.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_11.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_11);
		VariabileControlled_Valore_11.setColumns(10);

		JLabel VariabileControlled_Text_12 = new JLabel("machine_state");
		VariabileControlled_Text_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_12 );

		JTextField VariabileControlled_Valore_12 = new JTextField();
		VariabileControlled_Valore_12.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_12.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_12);
		VariabileControlled_Valore_12.setColumns(10);

		JLabel VariabileControlled_Text_13 = new JLabel("signal_lamp");
		VariabileControlled_Text_13.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_13 );

		JTextField VariabileControlled_Valore_13 = new JTextField();
		VariabileControlled_Valore_13.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_13.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_13);
		VariabileControlled_Valore_13.setColumns(10);

		JLabel VariabileControlled_Text_14 = new JLabel("arterialBolusPhase");
		VariabileControlled_Text_14.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_14 );

		JTextField VariabileControlled_Valore_14 = new JTextField();
		VariabileControlled_Valore_14.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_14.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_14);
		VariabileControlled_Valore_14.setColumns(10);

		JLabel VariabileControlled_Text_15 = new JLabel("bp_status");
		VariabileControlled_Text_15.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_15 );

		JTextField VariabileControlled_Valore_15 = new JTextField();
		VariabileControlled_Valore_15.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_15.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_15);
		VariabileControlled_Valore_15.setColumns(10);

		JLabel VariabileControlled_Text_16 = new JLabel("activation_h_contr");
		VariabileControlled_Text_16.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_16 );

		JTextField VariabileControlled_Valore_16 = new JTextField();
		VariabileControlled_Valore_16.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_16.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_16);
		VariabileControlled_Valore_16.setColumns(10);

		JLabel VariabileControlled_Text_17 = new JLabel("art_connected_contr");
		VariabileControlled_Text_17.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_17 );

		JTextField VariabileControlled_Valore_17 = new JTextField();
		VariabileControlled_Valore_17.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_17.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_17);
		VariabileControlled_Valore_17.setColumns(10);

		JLabel VariabileControlled_Text_18 = new JLabel("ven_connected_contr");
		VariabileControlled_Text_18.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_18 );

		JTextField VariabileControlled_Valore_18 = new JTextField();
		VariabileControlled_Valore_18.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_18.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_18);
		VariabileControlled_Valore_18.setColumns(10);

		JLabel VariabileControlled_Text_19 = new JLabel("bicarbonate_status");
		VariabileControlled_Text_19.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_19 );

		JTextField VariabileControlled_Valore_19 = new JTextField();
		VariabileControlled_Valore_19.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_19.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_19);
		VariabileControlled_Valore_19.setColumns(10);

		JLabel VariabileControlled_Text_20 = new JLabel("heparin_running");
		VariabileControlled_Text_20.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_20 );

		JTextField VariabileControlled_Valore_20 = new JTextField();
		VariabileControlled_Valore_20.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_20.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_20);
		VariabileControlled_Valore_20.setColumns(10);

		JLabel VariabileControlled_Text_21 = new JLabel("ap_limits_set");
		VariabileControlled_Text_21.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_21 );

		JTextField VariabileControlled_Valore_21 = new JTextField();
		VariabileControlled_Valore_21.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_21.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_21);
		VariabileControlled_Valore_21.setColumns(10);

		JLabel VariabileControlled_Text_22 = new JLabel("vp_limits_set");
		VariabileControlled_Text_22.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_22 );

		JTextField VariabileControlled_Valore_22 = new JTextField();
		VariabileControlled_Valore_22.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_22.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_22);
		VariabileControlled_Valore_22.setColumns(10);

		JLabel VariabileControlled_Text_23 = new JLabel("treatment_min_uf_rate_contr");
		VariabileControlled_Text_23.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_23 );

		JTextField VariabileControlled_Valore_23 = new JTextField();
		VariabileControlled_Valore_23.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_23.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_23);
		VariabileControlled_Valore_23.setColumns(10);

		JLabel VariabileControlled_Text_24 = new JLabel("empty_dialyzer");
		VariabileControlled_Text_24.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_24 );

		JTextField VariabileControlled_Valore_24 = new JTextField();
		VariabileControlled_Valore_24.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_24.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_24);
		VariabileControlled_Valore_24.setColumns(10);

		JLabel VariabileControlled_Text_25 = new JLabel("bf_err_vp_low");
		VariabileControlled_Text_25.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_25 );

		JTextField VariabileControlled_Valore_25 = new JTextField();
		VariabileControlled_Valore_25.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_25.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_25);
		VariabileControlled_Valore_25.setColumns(10);

		JLabel VariabileControlled_Text_26 = new JLabel("reset_err_pres_vp_low");
		VariabileControlled_Text_26.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_26 );

		JTextField VariabileControlled_Valore_26 = new JTextField();
		VariabileControlled_Valore_26.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_26.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_26);
		VariabileControlled_Valore_26.setColumns(10);

		JLabel VariabileControlled_Text_27 = new JLabel("bf_err_vp_up");
		VariabileControlled_Text_27.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_27 );

		JTextField VariabileControlled_Valore_27 = new JTextField();
		VariabileControlled_Valore_27.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_27.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_27);
		VariabileControlled_Valore_27.setColumns(10);

		JLabel VariabileControlled_Text_28 = new JLabel("reset_err_pres_vp_up");
		VariabileControlled_Text_28.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_28 );

		JTextField VariabileControlled_Valore_28 = new JTextField();
		VariabileControlled_Valore_28.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_28.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_28);
		VariabileControlled_Valore_28.setColumns(10);

		JLabel VariabileControlled_Text_29 = new JLabel("bf_err_ap_up");
		VariabileControlled_Text_29.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_29 );

		JTextField VariabileControlled_Valore_29 = new JTextField();
		VariabileControlled_Valore_29.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_29.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_29);
		VariabileControlled_Valore_29.setColumns(10);

		JLabel VariabileControlled_Text_30 = new JLabel("reset_err_pres_ap_up");
		VariabileControlled_Text_30.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_30 );

		JTextField VariabileControlled_Valore_30 = new JTextField();
		VariabileControlled_Valore_30.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_30.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_30);
		VariabileControlled_Valore_30.setColumns(10);

		JLabel VariabileControlled_Text_31 = new JLabel("bf_err_ap_low");
		VariabileControlled_Text_31.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_31 );

		JTextField VariabileControlled_Valore_31 = new JTextField();
		VariabileControlled_Valore_31.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_31.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_31);
		VariabileControlled_Valore_31.setColumns(10);

		JLabel VariabileControlled_Text_32 = new JLabel("reset_err_pres_ap_low");
		VariabileControlled_Text_32.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_32 );

		JTextField VariabileControlled_Valore_32 = new JTextField();
		VariabileControlled_Valore_32.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_32.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_32);
		VariabileControlled_Valore_32.setColumns(10);

		JLabel VariabileControlled_Text_33 = new JLabel("bf_err_vp_low_conn");
		VariabileControlled_Text_33.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_33 );

		JTextField VariabileControlled_Valore_33 = new JTextField();
		VariabileControlled_Valore_33.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_33.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_33);
		VariabileControlled_Valore_33.setColumns(10);

		JLabel VariabileControlled_Text_34 = new JLabel("reset_err_pres_vp_low_conn");
		VariabileControlled_Text_34.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_34 );

		JTextField VariabileControlled_Valore_34 = new JTextField();
		VariabileControlled_Valore_34.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_34.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_34);
		VariabileControlled_Valore_34.setColumns(10);

		JLabel VariabileControlled_Text_35 = new JLabel("bf_err_ap_low_conn");
		VariabileControlled_Text_35.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_35 );

		JTextField VariabileControlled_Valore_35 = new JTextField();
		VariabileControlled_Valore_35.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_35.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_35);
		VariabileControlled_Valore_35.setColumns(10);

		JLabel VariabileControlled_Text_36 = new JLabel("reset_err_pres_ap_low_conn");
		VariabileControlled_Text_36.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_36 );

		JTextField VariabileControlled_Valore_36 = new JTextField();
		VariabileControlled_Valore_36.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_36.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_36);
		VariabileControlled_Valore_36.setColumns(10);

		JLabel VariabileControlled_Text_37 = new JLabel("check_ap");
		VariabileControlled_Text_37.setHorizontalAlignment(SwingConstants.CENTER);
		panel_dx.add(VariabileControlled_Text_37 );

		JTextField VariabileControlled_Valore_37 = new JTextField();
		VariabileControlled_Valore_37.setHorizontalAlignment(SwingConstants.CENTER);
		VariabileControlled_Valore_37.setEditable(false);
		panel_dx.add(VariabileControlled_Valore_37);
		VariabileControlled_Valore_37.setColumns(10);

		JLabel VariabileMonitored_text_1 = new JLabel("auto_test_end");
		VariabileMonitored_text_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_1);

		JTextField VariabileMonitored_Valore_1 = new JTextField();
		VariabileMonitored_Valore_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_1);
		VariabileMonitored_Valore_1.setColumns(10);

		try {
			VariabileMonitored_Valore_1.setText(esecuzione.auto_test_end.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_1.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_2 = new JLabel("conn_concentrate");
		VariabileMonitored_text_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_2);

		JTextField VariabileMonitored_Valore_2 = new JTextField();
		VariabileMonitored_Valore_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_2);
		VariabileMonitored_Valore_2.setColumns(10);

		try {
			VariabileMonitored_Valore_2.setText(esecuzione.conn_concentrate.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_2.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_3 = new JLabel("heparin_prepared");
		VariabileMonitored_text_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_3);

		JTextField VariabileMonitored_Valore_3 = new JTextField();
		VariabileMonitored_Valore_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_3);
		VariabileMonitored_Valore_3.setColumns(10);

		try {
			VariabileMonitored_Valore_3.setText(esecuzione.heparin_prepared.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_3.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_4 = new JLabel("dialyzer_drained");
		VariabileMonitored_text_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_4);

		JTextField VariabileMonitored_Valore_4 = new JTextField();
		VariabileMonitored_Valore_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_4);
		VariabileMonitored_Valore_4.setColumns(10);

		try {
			VariabileMonitored_Valore_4.setText(esecuzione.dialyzer_drained.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_4.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_5 = new JLabel("cartridge_emtpy");
		VariabileMonitored_text_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_5);

		JTextField VariabileMonitored_Valore_5 = new JTextField();
		VariabileMonitored_Valore_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_5);
		VariabileMonitored_Valore_5.setColumns(10);

		try {
			VariabileMonitored_Valore_5.setText(esecuzione.cartridge_emtpy.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_5.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_6 = new JLabel("passedHeparinTime");
		VariabileMonitored_text_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_6);

		JTextField VariabileMonitored_Valore_6 = new JTextField();
		VariabileMonitored_Valore_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_6);
		VariabileMonitored_Valore_6.setColumns(10);

		try {
			VariabileMonitored_Valore_6.setText(esecuzione.passedHeparinTime.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_6.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_7 = new JLabel("passed5Min");
		VariabileMonitored_text_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_7);

		JTextField VariabileMonitored_Valore_7 = new JTextField();
		VariabileMonitored_Valore_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_7);
		VariabileMonitored_Valore_7.setColumns(10);

		try {
			VariabileMonitored_Valore_7.setText(esecuzione.passed5Min.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_7.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_8 = new JLabel("passedTherapyTime");
		VariabileMonitored_text_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_8);

		JTextField VariabileMonitored_Valore_8 = new JTextField();
		VariabileMonitored_Valore_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_8);
		VariabileMonitored_Valore_8.setColumns(10);

		try {
			VariabileMonitored_Valore_8.setText(esecuzione.passedTherapyTime.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_8.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_9 = new JLabel("passed120Sec");
		VariabileMonitored_text_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_9);

		JTextField VariabileMonitored_Valore_9 = new JTextField();
		VariabileMonitored_Valore_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_9);
		VariabileMonitored_Valore_9.setColumns(10);

		try {
			VariabileMonitored_Valore_9.setText(esecuzione.passed120Sec.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_9.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_10 = new JLabel("passed10Sec");
		VariabileMonitored_text_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_10);

		JTextField VariabileMonitored_Valore_10 = new JTextField();
		VariabileMonitored_Valore_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_10);
		VariabileMonitored_Valore_10.setColumns(10);

		try {
			VariabileMonitored_Valore_10.setText(esecuzione.passed10Sec.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_10.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_11 = new JLabel("passed3Sec");
		VariabileMonitored_text_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_11);

		JTextField VariabileMonitored_Valore_11 = new JTextField();
		VariabileMonitored_Valore_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_11);
		VariabileMonitored_Valore_11.setColumns(10);

		try {
			VariabileMonitored_Valore_11.setText(esecuzione.passed3Sec.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_11.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_12 = new JLabel("passed1Sec");
		VariabileMonitored_text_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_12);

		JTextField VariabileMonitored_Valore_12 = new JTextField();
		VariabileMonitored_Valore_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_12);
		VariabileMonitored_Valore_12.setColumns(10);

		try {
			VariabileMonitored_Valore_12.setText(esecuzione.passed1Sec.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_12.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_13 = new JLabel("passed1Msec");
		VariabileMonitored_text_13.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_13);

		JTextField VariabileMonitored_Valore_13 = new JTextField();
		VariabileMonitored_Valore_13.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_13);
		VariabileMonitored_Valore_13.setColumns(10);

		try {
			VariabileMonitored_Valore_13.setText(esecuzione.passed1Msec.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_13.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_14 = new JLabel("bp_rate_rinsing_150");
		VariabileMonitored_text_14.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_14);

		JTextField VariabileMonitored_Valore_14 = new JTextField();
		VariabileMonitored_Valore_14.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_14);
		VariabileMonitored_Valore_14.setColumns(10);

		try {
			VariabileMonitored_Valore_14.setText(esecuzione.bp_rate_rinsing_150.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_14.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_15 = new JLabel("av_tubes_conn");
		VariabileMonitored_text_15.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_15);

		JTextField VariabileMonitored_Valore_15 = new JTextField();
		VariabileMonitored_Valore_15.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_15);
		VariabileMonitored_Valore_15.setColumns(10);

		try {
			VariabileMonitored_Valore_15.setText(esecuzione.av_tubes_conn.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_15.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_16 = new JLabel("all_comp_conn");
		VariabileMonitored_text_16.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_16);

		JTextField VariabileMonitored_Valore_16 = new JTextField();
		VariabileMonitored_Valore_16.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_16);
		VariabileMonitored_Valore_16.setColumns(10);

		try {
			VariabileMonitored_Valore_16.setText(esecuzione.all_comp_conn.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_16.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_17 = new JLabel("saline_levels_set");
		VariabileMonitored_text_17.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_17);

		JTextField VariabileMonitored_Valore_17 = new JTextField();
		VariabileMonitored_Valore_17.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_17);
		VariabileMonitored_Valore_17.setColumns(10);

		try {
			VariabileMonitored_Valore_17.setText(esecuzione.saline_levels_set.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_17.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_18 = new JLabel("blood_line_insert");
		VariabileMonitored_text_18.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_18);

		JTextField VariabileMonitored_Valore_18 = new JTextField();
		VariabileMonitored_Valore_18.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_18);
		VariabileMonitored_Valore_18.setColumns(10);

		try {
			VariabileMonitored_Valore_18.setText(esecuzione.blood_line_insert.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_18.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_19 = new JLabel("bp_fill_fluid");
		VariabileMonitored_text_19.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_19);

		JTextField VariabileMonitored_Valore_19 = new JTextField();
		VariabileMonitored_Valore_19.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_19);
		VariabileMonitored_Valore_19.setColumns(10);

		try {
			VariabileMonitored_Valore_19.setText(esecuzione.bp_fill_fluid.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_19.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_20 = new JLabel("av_ends_conn");
		VariabileMonitored_text_20.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_20);

		JTextField VariabileMonitored_Valore_20 = new JTextField();
		VariabileMonitored_Valore_20.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_20);
		VariabileMonitored_Valore_20.setColumns(10);

		try {
			VariabileMonitored_Valore_20.setText(esecuzione.av_ends_conn.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_20.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_21 = new JLabel("activation_h");
		VariabileMonitored_text_21.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_21);

		JTextField VariabileMonitored_Valore_21 = new JTextField();
		VariabileMonitored_Valore_21.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_21);
		VariabileMonitored_Valore_21.setColumns(10);

		try {
			VariabileMonitored_Valore_21.setText(esecuzione.activation_h.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_21.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_22 = new JLabel("dialyzer_connected");
		VariabileMonitored_text_22.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_22);

		JTextField VariabileMonitored_Valore_22 = new JTextField();
		VariabileMonitored_Valore_22.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_22);
		VariabileMonitored_Valore_22.setColumns(10);

		try {
			VariabileMonitored_Valore_22.setText(esecuzione.dialyzer_connected.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_22.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_23 = new JLabel("arterial_chamber_filled");
		VariabileMonitored_text_23.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_23);

		JTextField VariabileMonitored_Valore_23 = new JTextField();
		VariabileMonitored_Valore_23.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_23);
		VariabileMonitored_Valore_23.setColumns(10);

		try {
			VariabileMonitored_Valore_23.setText(esecuzione.arterial_chamber_filled.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_23.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_24 = new JLabel("venous_chamber_fill");
		VariabileMonitored_text_24.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_24);

		JTextField VariabileMonitored_Valore_24 = new JTextField();
		VariabileMonitored_Valore_24.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_24);
		VariabileMonitored_Valore_24.setColumns(10);

		try {
			VariabileMonitored_Valore_24.setText(esecuzione.venous_chamber_fill.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_24.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_25 = new JLabel("dialyzer_filled");
		VariabileMonitored_text_25.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_25);

		JTextField VariabileMonitored_Valore_25 = new JTextField();
		VariabileMonitored_Valore_25.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_25);
		VariabileMonitored_Valore_25.setColumns(10);

		try {
			VariabileMonitored_Valore_25.setText(esecuzione.dialyzer_filled.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_25.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_26 = new JLabel("art_connected");
		VariabileMonitored_text_26.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_26);

		JTextField VariabileMonitored_Valore_26 = new JTextField();
		VariabileMonitored_Valore_26.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_26);
		VariabileMonitored_Valore_26.setColumns(10);

		try {
			VariabileMonitored_Valore_26.setText(esecuzione.art_connected.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_26.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_27 = new JLabel("blood_flow_conn_set");
		VariabileMonitored_text_27.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_27);

		JTextField VariabileMonitored_Valore_27 = new JTextField();
		VariabileMonitored_Valore_27.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_27);
		VariabileMonitored_Valore_27.setColumns(10);

		try {
			VariabileMonitored_Valore_27.setText(esecuzione.blood_flow_conn_set.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_27.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_28 = new JLabel("blood_flow_conn_reset");
		VariabileMonitored_text_28.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_28);

		JTextField VariabileMonitored_Valore_28 = new JTextField();
		VariabileMonitored_Valore_28.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_28);
		VariabileMonitored_Valore_28.setColumns(10);

		try {
			VariabileMonitored_Valore_28.setText(esecuzione.blood_flow_conn_reset.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_28.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_29 = new JLabel("param_press_reset");
		VariabileMonitored_text_29.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_29);

		JTextField VariabileMonitored_Valore_29 = new JTextField();
		VariabileMonitored_Valore_29.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_29);
		VariabileMonitored_Valore_29.setColumns(10);

		try {
			VariabileMonitored_Valore_29.setText(esecuzione.param_press_reset.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_29.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_30 = new JLabel("blood_on_VRD");
		VariabileMonitored_text_30.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_30);

		JTextField VariabileMonitored_Valore_30 = new JTextField();
		VariabileMonitored_Valore_30.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_30);
		VariabileMonitored_Valore_30.setColumns(10);

		try {
			VariabileMonitored_Valore_30.setText(esecuzione.blood_on_VRD.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_30.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_31 = new JLabel("conn_infuse_set_volume");
		VariabileMonitored_text_31.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_31);

		JTextField VariabileMonitored_Valore_31 = new JTextField();
		VariabileMonitored_Valore_31.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_31);
		VariabileMonitored_Valore_31.setColumns(10);

		try {
			VariabileMonitored_Valore_31.setText(esecuzione.conn_infuse_set_volume.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_31.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_32 = new JLabel("ven_connected");
		VariabileMonitored_text_32.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_32);

		JTextField VariabileMonitored_Valore_32 = new JTextField();
		VariabileMonitored_Valore_32.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_32);
		VariabileMonitored_Valore_32.setColumns(10);

		try {
			VariabileMonitored_Valore_32.setText(esecuzione.ven_connected.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_32.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_33 = new JLabel("treatment_min_uf_rate");
		VariabileMonitored_text_33.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_33);

		JTextField VariabileMonitored_Valore_33 = new JTextField();
		VariabileMonitored_Valore_33.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_33);
		VariabileMonitored_Valore_33.setColumns(10);

		try {
			VariabileMonitored_Valore_33.setText(esecuzione.treatment_min_uf_rate.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_33.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_34 = new JLabel("interrupt_dialysis");
		VariabileMonitored_text_34.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_34);

		JTextField VariabileMonitored_Valore_34 = new JTextField();
		VariabileMonitored_Valore_34.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_34);
		VariabileMonitored_Valore_34.setColumns(10);

		try {
			VariabileMonitored_Valore_34.setText(esecuzione.interrupt_dialysis.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_34.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_35 = new JLabel("start_arterial_bolus");
		VariabileMonitored_text_35.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_35);

		JTextField VariabileMonitored_Valore_35 = new JTextField();
		VariabileMonitored_Valore_35.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_35);
		VariabileMonitored_Valore_35.setColumns(10);

		try {
			VariabileMonitored_Valore_35.setText(esecuzione.start_arterial_bolus.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_35.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_36 = new JLabel("saline_solution_conn");
		VariabileMonitored_text_36.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_36);

		JTextField VariabileMonitored_Valore_36 = new JTextField();
		VariabileMonitored_Valore_36.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_36);
		VariabileMonitored_Valore_36.setColumns(10);

		try {
			VariabileMonitored_Valore_36.setText(esecuzione.saline_solution_conn.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_36.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_37 = new JLabel("art_bolus_volume_set");
		VariabileMonitored_text_37.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_37);

		JTextField VariabileMonitored_Valore_37 = new JTextField();
		VariabileMonitored_Valore_37.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_37);
		VariabileMonitored_Valore_37.setColumns(10);

		try {
			VariabileMonitored_Valore_37.setText(esecuzione.art_bolus_volume_set.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_37.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_38 = new JLabel("art_removed");
		VariabileMonitored_text_38.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_38);

		JTextField VariabileMonitored_Valore_38 = new JTextField();
		VariabileMonitored_Valore_38.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_38);
		VariabileMonitored_Valore_38.setColumns(10);

		try {
			VariabileMonitored_Valore_38.setText(esecuzione.art_removed.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_38.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_39 = new JLabel("saline_conn");
		VariabileMonitored_text_39.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_39);

		JTextField VariabileMonitored_Valore_39 = new JTextField();
		VariabileMonitored_Valore_39.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_39);
		VariabileMonitored_Valore_39.setColumns(10);

		try {
			VariabileMonitored_Valore_39.setText(esecuzione.saline_conn.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_39.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_40 = new JLabel("saline_on_VRD");
		VariabileMonitored_text_40.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_40);

		JTextField VariabileMonitored_Valore_40 = new JTextField();
		VariabileMonitored_Valore_40.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_40);
		VariabileMonitored_Valore_40.setColumns(10);

		try {
			VariabileMonitored_Valore_40.setText(esecuzione.saline_on_VRD.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_40.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_41 = new JLabel("new_saline_reinfusion");
		VariabileMonitored_text_41.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_41);

		JTextField VariabileMonitored_Valore_41 = new JTextField();
		VariabileMonitored_Valore_41.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_41);
		VariabileMonitored_Valore_41.setColumns(10);

		try {
			VariabileMonitored_Valore_41.setText(esecuzione.new_saline_reinfusion.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_41.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_42 = new JLabel("volume_saline_inf_400");
		VariabileMonitored_text_42.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_42);

		JTextField VariabileMonitored_Valore_42 = new JTextField();
		VariabileMonitored_Valore_42.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_42);
		VariabileMonitored_Valore_42.setColumns(10);

		try {
			VariabileMonitored_Valore_42.setText(esecuzione.volume_saline_inf_400.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_42.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_43 = new JLabel("ven_removed");
		VariabileMonitored_text_43.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_43);

		JTextField VariabileMonitored_Valore_43 = new JTextField();
		VariabileMonitored_Valore_43.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_43);
		VariabileMonitored_Valore_43.setColumns(10);

		try {
			VariabileMonitored_Valore_43.setText(esecuzione.ven_removed.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_43.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_44 = new JLabel("reset_alarm");
		VariabileMonitored_text_44.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_44);

		JTextField VariabileMonitored_Valore_44 = new JTextField();
		VariabileMonitored_Valore_44.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_44);
		VariabileMonitored_Valore_44.setColumns(10);

		try {
			VariabileMonitored_Valore_44.setText(esecuzione.reset_alarm.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_44.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_45 = new JLabel("current_art_bolus_volume");
		VariabileMonitored_text_45.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_45);

		JTextField VariabileMonitored_Valore_45 = new JTextField();
		VariabileMonitored_Valore_45.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_45);
		VariabileMonitored_Valore_45.setColumns(10);

		try {
			VariabileMonitored_Valore_45.setText(esecuzione.current_art_bolus_volume.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_45.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_46 = new JLabel("update_blood_flow");
		VariabileMonitored_text_46.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_46);

		JTextField VariabileMonitored_Valore_46 = new JTextField();
		VariabileMonitored_Valore_46.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_46);
		VariabileMonitored_Valore_46.setColumns(10);

		try {
			VariabileMonitored_Valore_46.setText(esecuzione.update_blood_flow.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_46.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_47 = new JLabel("current_temp");
		VariabileMonitored_text_47.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_47);

		JTextField VariabileMonitored_Valore_47 = new JTextField();
		VariabileMonitored_Valore_47.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_47);
		VariabileMonitored_Valore_47.setColumns(10);

		try {
			VariabileMonitored_Valore_47.setText(esecuzione.current_temp.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_47.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_48 = new JLabel("current_bp_flow_less_70");
		VariabileMonitored_text_48.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_48);

		JTextField VariabileMonitored_Valore_48 = new JTextField();
		VariabileMonitored_Valore_48.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_48);
		VariabileMonitored_Valore_48.setColumns(10);

		try {
			VariabileMonitored_Valore_48.setText(esecuzione.current_bp_flow_less_70.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_48.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_49 = new JLabel("current_vp");
		VariabileMonitored_text_49.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_49);

		JTextField VariabileMonitored_Valore_49 = new JTextField();
		VariabileMonitored_Valore_49.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_49);
		VariabileMonitored_Valore_49.setColumns(10);

		try {
			VariabileMonitored_Valore_49.setText(esecuzione.current_vp.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_49.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_50 = new JLabel("current_ap");
		VariabileMonitored_text_50.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_50);

		JTextField VariabileMonitored_Valore_50 = new JTextField();
		VariabileMonitored_Valore_50.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_50);
		VariabileMonitored_Valore_50.setColumns(10);

		try {
			VariabileMonitored_Valore_50.setText(esecuzione.current_ap.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_50.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_51 = new JLabel("fill_blood_vol_passed_up_limit");
		VariabileMonitored_text_51.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_51);

		JTextField VariabileMonitored_Valore_51 = new JTextField();
		VariabileMonitored_Valore_51.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_51);
		VariabileMonitored_Valore_51.setColumns(10);

		try {
			VariabileMonitored_Valore_51.setText(esecuzione.fill_blood_vol_passed_up_limit.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_51.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_52 = new JLabel("current_air_vol");
		VariabileMonitored_text_52.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_52);

		JTextField VariabileMonitored_Valore_52 = new JTextField();
		VariabileMonitored_Valore_52.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_52);
		VariabileMonitored_Valore_52.setColumns(10);

		try {
			VariabileMonitored_Valore_52.setText(esecuzione.current_air_vol.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_52.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_53 = new JLabel("currentSAD");
		VariabileMonitored_text_53.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_53);

		JTextField VariabileMonitored_Valore_53 = new JTextField();
		VariabileMonitored_Valore_53.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_53);
		VariabileMonitored_Valore_53.setColumns(10);

		try {
			VariabileMonitored_Valore_53.setText(esecuzione.currentSAD.Value.value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_53.setText("Insert Integer");
		}

		JLabel VariabileMonitored_text_54 = new JLabel("uf_rate_passed_max_uf_rate");
		VariabileMonitored_text_54.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_54);

		JTextField VariabileMonitored_Valore_54 = new JTextField();
		VariabileMonitored_Valore_54.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_54);
		VariabileMonitored_Valore_54.setColumns(10);

		try {
			VariabileMonitored_Valore_54.setText(esecuzione.uf_rate_passed_max_uf_rate.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_54.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_55 = new JLabel("uf_volume_passed_max_uf_volume");
		VariabileMonitored_text_55.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_55);

		JTextField VariabileMonitored_Valore_55 = new JTextField();
		VariabileMonitored_Valore_55.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_55);
		VariabileMonitored_Valore_55.setColumns(10);

		try {
			VariabileMonitored_Valore_55.setText(esecuzione.uf_volume_passed_max_uf_volume.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_55.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_56 = new JLabel("detected_blood_flow");
		VariabileMonitored_text_56.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_56);

		JTextField VariabileMonitored_Valore_56 = new JTextField();
		VariabileMonitored_Valore_56.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_56);
		VariabileMonitored_Valore_56.setColumns(10);

		try {
			VariabileMonitored_Valore_56.setText(esecuzione.detected_blood_flow.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_56.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_57 = new JLabel("bp_rotates_back");
		VariabileMonitored_text_57.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_57);

		JTextField VariabileMonitored_Valore_57 = new JTextField();
		VariabileMonitored_Valore_57.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_57);
		VariabileMonitored_Valore_57.setColumns(10);

		try {
			VariabileMonitored_Valore_57.setText(esecuzione.bp_rotates_back.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_57.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_58 = new JLabel("detect_bicarbonate");
		VariabileMonitored_text_58.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_58);

		JTextField VariabileMonitored_Valore_58 = new JTextField();
		VariabileMonitored_Valore_58.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_58);
		VariabileMonitored_Valore_58.setColumns(10);

		try {
			VariabileMonitored_Valore_58.setText(esecuzione.detect_bicarbonate.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_58.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_59 = new JLabel("reverse_dir_heparin");
		VariabileMonitored_text_59.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_59);

		JTextField VariabileMonitored_Valore_59 = new JTextField();
		VariabileMonitored_Valore_59.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_59);
		VariabileMonitored_Valore_59.setColumns(10);

		try {
			VariabileMonitored_Valore_59.setText(esecuzione.reverse_dir_heparin.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_59.setText("Insert Boolean");
		}

		JLabel VariabileMonitored_text_60 = new JLabel("uf_dir_backwards");
		VariabileMonitored_text_60.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_text_60);

		JTextField VariabileMonitored_Valore_60 = new JTextField();
		VariabileMonitored_Valore_60.setHorizontalAlignment(SwingConstants.CENTER);
		panel_sx.add(VariabileMonitored_Valore_60);
		VariabileMonitored_Valore_60.setColumns(10);

		try {
			VariabileMonitored_Valore_60.setText(esecuzione.uf_dir_backwards.Value.toString());
		}
		catch (NullPointerException errM1)
		{
			VariabileMonitored_Valore_60.setText("Insert Boolean");
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
							VariabileControlled_Valore_1.setText(esecuzione.phaseTherapy.oldValue.name());
						} catch( NullPointerException errC1)
						{
							VariabileControlled_Valore_1.setText("null");
						}

						try {
							VariabileControlled_Valore_2.setText(esecuzione.modePreparation.oldValue.name());
						} catch( NullPointerException errC2)
						{
							VariabileControlled_Valore_2.setText("null");
						}

						try {
							VariabileControlled_Valore_3.setText(esecuzione.modeInitiation.oldValue.name());
						} catch( NullPointerException errC3)
						{
							VariabileControlled_Valore_3.setText("null");
						}

						try {
							VariabileControlled_Valore_4.setText(esecuzione.modeEnding.oldValue.name());
						} catch( NullPointerException errC4)
						{
							VariabileControlled_Valore_4.setText("null");
						}

						try {
							VariabileControlled_Valore_5.setText(esecuzione.rinsingParam.oldValue.name());
						} catch( NullPointerException errC5)
						{
							VariabileControlled_Valore_5.setText("null");
						}

						try {
							VariabileControlled_Valore_6.setText(esecuzione.tubingPhase.oldValue.name());
						} catch( NullPointerException errC6)
						{
							VariabileControlled_Valore_6.setText("null");
						}

						try {
							VariabileControlled_Valore_7.setText(esecuzione.treatmentParam.oldValue.name());
						} catch( NullPointerException errC7)
						{
							VariabileControlled_Valore_7.setText("null");
						}

						try {
							VariabileControlled_Valore_8.setText(esecuzione.rinsePhase.oldValue.name());
						} catch( NullPointerException errC8)
						{
							VariabileControlled_Valore_8.setText("null");
						}

						try {
							VariabileControlled_Valore_9.setText(esecuzione.patientPhase.oldValue.name());
						} catch( NullPointerException errC9)
						{
							VariabileControlled_Valore_9.setText("null");
						}

						try {
							VariabileControlled_Valore_10.setText(esecuzione.therapyPhase.oldValue.name());
						} catch( NullPointerException errC10)
						{
							VariabileControlled_Valore_10.setText("null");
						}

						try {
							VariabileControlled_Valore_11.setText(esecuzione.reinfusionPhase.oldValue.name());
						} catch( NullPointerException errC11)
						{
							VariabileControlled_Valore_11.setText("null");
						}

						try {
							VariabileControlled_Valore_12.setText(esecuzione.machine_state.oldValue.name());
						} catch( NullPointerException errC12)
						{
							VariabileControlled_Valore_12.setText("null");
						}

						try {
							VariabileControlled_Valore_13.setText(esecuzione.signal_lamp.oldValue.name());
						} catch( NullPointerException errC13)
						{
							VariabileControlled_Valore_13.setText("null");
						}

						try {
							VariabileControlled_Valore_14.setText(esecuzione.arterialBolusPhase.oldValue.name());
						} catch( NullPointerException errC14)
						{
							VariabileControlled_Valore_14.setText("null");
						}

						try {
							VariabileControlled_Valore_15.setText(esecuzione.bp_status.oldValue.name());
						} catch( NullPointerException errC15)
						{
							VariabileControlled_Valore_15.setText("null");
						}

						try {
							VariabileControlled_Valore_16.setText(esecuzione.activation_h_contr.get().toString());
						} catch( NullPointerException errC16)
						{
							VariabileControlled_Valore_16.setText("null");
						}

						try {
							VariabileControlled_Valore_17.setText(esecuzione.art_connected_contr.get().toString());
						} catch( NullPointerException errC17)
						{
							VariabileControlled_Valore_17.setText("null");
						}

						try {
							VariabileControlled_Valore_18.setText(esecuzione.ven_connected_contr.get().toString());
						} catch( NullPointerException errC18)
						{
							VariabileControlled_Valore_18.setText("null");
						}

						try {
							VariabileControlled_Valore_19.setText(esecuzione.bicarbonate_status.get().toString());
						} catch( NullPointerException errC19)
						{
							VariabileControlled_Valore_19.setText("null");
						}

						try {
							VariabileControlled_Valore_20.setText(esecuzione.heparin_running.get().toString());
						} catch( NullPointerException errC20)
						{
							VariabileControlled_Valore_20.setText("null");
						}

						try {
							VariabileControlled_Valore_21.setText(esecuzione.ap_limits_set.get().toString());
						} catch( NullPointerException errC21)
						{
							VariabileControlled_Valore_21.setText("null");
						}

						try {
							VariabileControlled_Valore_22.setText(esecuzione.vp_limits_set.get().toString());
						} catch( NullPointerException errC22)
						{
							VariabileControlled_Valore_22.setText("null");
						}

						try {
							VariabileControlled_Valore_23.setText(esecuzione.treatment_min_uf_rate_contr.get().toString());
						} catch( NullPointerException errC23)
						{
							VariabileControlled_Valore_23.setText("null");
						}

						try {
							VariabileControlled_Valore_24.setText(esecuzione.empty_dialyzer.get().toString());
						} catch( NullPointerException errC24)
						{
							VariabileControlled_Valore_24.setText("null");
						}

						try {
							VariabileControlled_Valore_25.setText(esecuzione.bf_err_vp_low.get().toString());
						} catch( NullPointerException errC25)
						{
							VariabileControlled_Valore_25.setText("null");
						}

						try {
							VariabileControlled_Valore_26.setText(esecuzione.reset_err_pres_vp_low.get().toString());
						} catch( NullPointerException errC26)
						{
							VariabileControlled_Valore_26.setText("null");
						}

						try {
							VariabileControlled_Valore_27.setText(esecuzione.bf_err_vp_up.get().toString());
						} catch( NullPointerException errC27)
						{
							VariabileControlled_Valore_27.setText("null");
						}

						try {
							VariabileControlled_Valore_28.setText(esecuzione.reset_err_pres_vp_up.get().toString());
						} catch( NullPointerException errC28)
						{
							VariabileControlled_Valore_28.setText("null");
						}

						try {
							VariabileControlled_Valore_29.setText(esecuzione.bf_err_ap_up.get().toString());
						} catch( NullPointerException errC29)
						{
							VariabileControlled_Valore_29.setText("null");
						}

						try {
							VariabileControlled_Valore_30.setText(esecuzione.reset_err_pres_ap_up.get().toString());
						} catch( NullPointerException errC30)
						{
							VariabileControlled_Valore_30.setText("null");
						}

						try {
							VariabileControlled_Valore_31.setText(esecuzione.bf_err_ap_low.get().toString());
						} catch( NullPointerException errC31)
						{
							VariabileControlled_Valore_31.setText("null");
						}

						try {
							VariabileControlled_Valore_32.setText(esecuzione.reset_err_pres_ap_low.get().toString());
						} catch( NullPointerException errC32)
						{
							VariabileControlled_Valore_32.setText("null");
						}

						try {
							VariabileControlled_Valore_33.setText(esecuzione.bf_err_vp_low_conn.get().toString());
						} catch( NullPointerException errC33)
						{
							VariabileControlled_Valore_33.setText("null");
						}

						try {
							VariabileControlled_Valore_34.setText(esecuzione.reset_err_pres_vp_low_conn.get().toString());
						} catch( NullPointerException errC34)
						{
							VariabileControlled_Valore_34.setText("null");
						}

						try {
							VariabileControlled_Valore_35.setText(esecuzione.bf_err_ap_low_conn.get().toString());
						} catch( NullPointerException errC35)
						{
							VariabileControlled_Valore_35.setText("null");
						}

						try {
							VariabileControlled_Valore_36.setText(esecuzione.reset_err_pres_ap_low_conn.get().toString());
						} catch( NullPointerException errC36)
						{
							VariabileControlled_Valore_36.setText("null");
						}

						try {
							VariabileControlled_Valore_37.setText(esecuzione.check_ap.get().toString());
						} catch( NullPointerException errC37)
						{
							VariabileControlled_Valore_37.setText("null");
						}

						//"traduzione" della funzione askMonitored(esecuzione) del file _Exe

						if(VariabileMonitored_Valore_1.getText().contentEquals("true"))
						esecuzione.auto_test_end.Value = true;
						else
						esecuzione.auto_test_end.Value = false;

						if(VariabileMonitored_Valore_2.getText().contentEquals("true"))
						esecuzione.conn_concentrate.Value = true;
						else
						esecuzione.conn_concentrate.Value = false;

						if(VariabileMonitored_Valore_3.getText().contentEquals("true"))
						esecuzione.heparin_prepared.Value = true;
						else
						esecuzione.heparin_prepared.Value = false;

						if(VariabileMonitored_Valore_4.getText().contentEquals("true"))
						esecuzione.dialyzer_drained.Value = true;
						else
						esecuzione.dialyzer_drained.Value = false;

						if(VariabileMonitored_Valore_5.getText().contentEquals("true"))
						esecuzione.cartridge_emtpy.Value = true;
						else
						esecuzione.cartridge_emtpy.Value = false;

						if(VariabileMonitored_Valore_6.getText().contentEquals("true"))
						esecuzione.passedHeparinTime.Value = true;
						else
						esecuzione.passedHeparinTime.Value = false;

						if(VariabileMonitored_Valore_7.getText().contentEquals("true"))
						esecuzione.passed5Min.Value = true;
						else
						esecuzione.passed5Min.Value = false;

						if(VariabileMonitored_Valore_8.getText().contentEquals("true"))
						esecuzione.passedTherapyTime.Value = true;
						else
						esecuzione.passedTherapyTime.Value = false;

						if(VariabileMonitored_Valore_9.getText().contentEquals("true"))
						esecuzione.passed120Sec.Value = true;
						else
						esecuzione.passed120Sec.Value = false;

						if(VariabileMonitored_Valore_10.getText().contentEquals("true"))
						esecuzione.passed10Sec.Value = true;
						else
						esecuzione.passed10Sec.Value = false;

						if(VariabileMonitored_Valore_11.getText().contentEquals("true"))
						esecuzione.passed3Sec.Value = true;
						else
						esecuzione.passed3Sec.Value = false;

						if(VariabileMonitored_Valore_12.getText().contentEquals("true"))
						esecuzione.passed1Sec.Value = true;
						else
						esecuzione.passed1Sec.Value = false;

						if(VariabileMonitored_Valore_13.getText().contentEquals("true"))
						esecuzione.passed1Msec.Value = true;
						else
						esecuzione.passed1Msec.Value = false;

						if(VariabileMonitored_Valore_14.getText().contentEquals("true"))
						esecuzione.bp_rate_rinsing_150.Value = true;
						else
						esecuzione.bp_rate_rinsing_150.Value = false;

						if(VariabileMonitored_Valore_15.getText().contentEquals("true"))
						esecuzione.av_tubes_conn.Value = true;
						else
						esecuzione.av_tubes_conn.Value = false;

						if(VariabileMonitored_Valore_16.getText().contentEquals("true"))
						esecuzione.all_comp_conn.Value = true;
						else
						esecuzione.all_comp_conn.Value = false;

						if(VariabileMonitored_Valore_17.getText().contentEquals("true"))
						esecuzione.saline_levels_set.Value = true;
						else
						esecuzione.saline_levels_set.Value = false;

						if(VariabileMonitored_Valore_18.getText().contentEquals("true"))
						esecuzione.blood_line_insert.Value = true;
						else
						esecuzione.blood_line_insert.Value = false;

						if(VariabileMonitored_Valore_19.getText().contentEquals("true"))
						esecuzione.bp_fill_fluid.Value = true;
						else
						esecuzione.bp_fill_fluid.Value = false;

						if(VariabileMonitored_Valore_20.getText().contentEquals("true"))
						esecuzione.av_ends_conn.Value = true;
						else
						esecuzione.av_ends_conn.Value = false;

						if(VariabileMonitored_Valore_21.getText().contentEquals("true"))
						esecuzione.activation_h.Value = true;
						else
						esecuzione.activation_h.Value = false;

						if(VariabileMonitored_Valore_22.getText().contentEquals("true"))
						esecuzione.dialyzer_connected.Value = true;
						else
						esecuzione.dialyzer_connected.Value = false;

						if(VariabileMonitored_Valore_23.getText().contentEquals("true"))
						esecuzione.arterial_chamber_filled.Value = true;
						else
						esecuzione.arterial_chamber_filled.Value = false;

						if(VariabileMonitored_Valore_24.getText().contentEquals("true"))
						esecuzione.venous_chamber_fill.Value = true;
						else
						esecuzione.venous_chamber_fill.Value = false;

						if(VariabileMonitored_Valore_25.getText().contentEquals("true"))
						esecuzione.dialyzer_filled.Value = true;
						else
						esecuzione.dialyzer_filled.Value = false;

						if(VariabileMonitored_Valore_26.getText().contentEquals("true"))
						esecuzione.art_connected.Value = true;
						else
						esecuzione.art_connected.Value = false;

						if(VariabileMonitored_Valore_27.getText().contentEquals("true"))
						esecuzione.blood_flow_conn_set.Value = true;
						else
						esecuzione.blood_flow_conn_set.Value = false;

						if(VariabileMonitored_Valore_28.getText().contentEquals("true"))
						esecuzione.blood_flow_conn_reset.Value = true;
						else
						esecuzione.blood_flow_conn_reset.Value = false;

						if(VariabileMonitored_Valore_29.getText().contentEquals("true"))
						esecuzione.param_press_reset.Value = true;
						else
						esecuzione.param_press_reset.Value = false;

						if(VariabileMonitored_Valore_30.getText().contentEquals("true"))
						esecuzione.blood_on_VRD.Value = true;
						else
						esecuzione.blood_on_VRD.Value = false;

						if(VariabileMonitored_Valore_31.getText().contentEquals("true"))
						esecuzione.conn_infuse_set_volume.Value = true;
						else
						esecuzione.conn_infuse_set_volume.Value = false;

						if(VariabileMonitored_Valore_32.getText().contentEquals("true"))
						esecuzione.ven_connected.Value = true;
						else
						esecuzione.ven_connected.Value = false;

						if(VariabileMonitored_Valore_33.getText().contentEquals("true"))
						esecuzione.treatment_min_uf_rate.Value = true;
						else
						esecuzione.treatment_min_uf_rate.Value = false;

						if(VariabileMonitored_Valore_34.getText().contentEquals("true"))
						esecuzione.interrupt_dialysis.Value = true;
						else
						esecuzione.interrupt_dialysis.Value = false;

						if(VariabileMonitored_Valore_35.getText().contentEquals("true"))
						esecuzione.start_arterial_bolus.Value = true;
						else
						esecuzione.start_arterial_bolus.Value = false;

						if(VariabileMonitored_Valore_36.getText().contentEquals("true"))
						esecuzione.saline_solution_conn.Value = true;
						else
						esecuzione.saline_solution_conn.Value = false;

						if(VariabileMonitored_Valore_37.getText().contentEquals("true"))
						esecuzione.art_bolus_volume_set.Value = true;
						else
						esecuzione.art_bolus_volume_set.Value = false;

						if(VariabileMonitored_Valore_38.getText().contentEquals("true"))
						esecuzione.art_removed.Value = true;
						else
						esecuzione.art_removed.Value = false;

						if(VariabileMonitored_Valore_39.getText().contentEquals("true"))
						esecuzione.saline_conn.Value = true;
						else
						esecuzione.saline_conn.Value = false;

						if(VariabileMonitored_Valore_40.getText().contentEquals("true"))
						esecuzione.saline_on_VRD.Value = true;
						else
						esecuzione.saline_on_VRD.Value = false;

						if(VariabileMonitored_Valore_41.getText().contentEquals("true"))
						esecuzione.new_saline_reinfusion.Value = true;
						else
						esecuzione.new_saline_reinfusion.Value = false;

						if(VariabileMonitored_Valore_42.getText().contentEquals("true"))
						esecuzione.volume_saline_inf_400.Value = true;
						else
						esecuzione.volume_saline_inf_400.Value = false;

						if(VariabileMonitored_Valore_43.getText().contentEquals("true"))
						esecuzione.ven_removed.Value = true;
						else
						esecuzione.ven_removed.Value = false;

						if(VariabileMonitored_Valore_44.getText().contentEquals("true"))
						esecuzione.reset_alarm.Value = true;
						else
						esecuzione.reset_alarm.Value = false;

						try
						{
							esecuzione.current_art_bolus_volume_supporto.value = Integer.parseInt(VariabileMonitored_Valore_45.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.current_art_bolus_volume_supporto.value = 0;
						}

						esecuzione.current_art_bolus_volume.set(esecuzione.current_art_bolus_volume_supporto);

						if(VariabileMonitored_Valore_46.getText().contentEquals("true"))
						esecuzione.update_blood_flow.Value = true;
						else
						esecuzione.update_blood_flow.Value = false;

						try
						{
							esecuzione.current_temp_supporto.value = Integer.parseInt(VariabileMonitored_Valore_47.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.current_temp_supporto.value = 0;
						}

						esecuzione.current_temp.set(esecuzione.current_temp_supporto);

						if(VariabileMonitored_Valore_48.getText().contentEquals("true"))
						esecuzione.current_bp_flow_less_70.Value = true;
						else
						esecuzione.current_bp_flow_less_70.Value = false;

						try
						{
							esecuzione.current_vp_supporto.value = Integer.parseInt(VariabileMonitored_Valore_49.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.current_vp_supporto.value = 0;
						}

						esecuzione.current_vp.set(esecuzione.current_vp_supporto);

						try
						{
							esecuzione.current_ap_supporto.value = Integer.parseInt(VariabileMonitored_Valore_50.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.current_ap_supporto.value = 0;
						}

						esecuzione.current_ap.set(esecuzione.current_ap_supporto);

						if(VariabileMonitored_Valore_51.getText().contentEquals("true"))
						esecuzione.fill_blood_vol_passed_up_limit.Value = true;
						else
						esecuzione.fill_blood_vol_passed_up_limit.Value = false;

						try
						{
							esecuzione.current_air_vol_supporto.value = Integer.parseInt(VariabileMonitored_Valore_52.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.current_air_vol_supporto.value = 0;
						}

						esecuzione.current_air_vol.set(esecuzione.current_air_vol_supporto);

						try
						{
							esecuzione.currentSAD_supporto.value = Integer.parseInt(VariabileMonitored_Valore_53.getText().toString());
						}
						catch ( NumberFormatException err)
						{
							esecuzione.currentSAD_supporto.value = 0;
						}

						esecuzione.currentSAD.set(esecuzione.currentSAD_supporto);

						if(VariabileMonitored_Valore_54.getText().contentEquals("true"))
						esecuzione.uf_rate_passed_max_uf_rate.Value = true;
						else
						esecuzione.uf_rate_passed_max_uf_rate.Value = false;

						if(VariabileMonitored_Valore_55.getText().contentEquals("true"))
						esecuzione.uf_volume_passed_max_uf_volume.Value = true;
						else
						esecuzione.uf_volume_passed_max_uf_volume.Value = false;

						if(VariabileMonitored_Valore_56.getText().contentEquals("true"))
						esecuzione.detected_blood_flow.Value = true;
						else
						esecuzione.detected_blood_flow.Value = false;

						if(VariabileMonitored_Valore_57.getText().contentEquals("true"))
						esecuzione.bp_rotates_back.Value = true;
						else
						esecuzione.bp_rotates_back.Value = false;

						if(VariabileMonitored_Valore_58.getText().contentEquals("true"))
						esecuzione.detect_bicarbonate.Value = true;
						else
						esecuzione.detect_bicarbonate.Value = false;

						if(VariabileMonitored_Valore_59.getText().contentEquals("true"))
						esecuzione.reverse_dir_heparin.Value = true;
						else
						esecuzione.reverse_dir_heparin.Value = false;

						if(VariabileMonitored_Valore_60.getText().contentEquals("true"))
						esecuzione.uf_dir_backwards.Value = true;
						else
						esecuzione.uf_dir_backwards.Value = false;

						//Aggiornamento dell'ASM
						esecuzione.UpdateASM();

					}});
	}

}

