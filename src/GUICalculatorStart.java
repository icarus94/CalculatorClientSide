import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class GUICalculatorStart extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnPoveziSeSa;
	private JButton btnIzadji;
	private JLabel lblAplikacijaZaPovezivanje;
	private JScrollPane scrollPane;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUICalculatorStart frame = new GUICalculatorStart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public GUICalculatorStart() {
		setResizable(false);
		setTitle("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel());
		contentPane.add(getScrollPane(), BorderLayout.SOUTH);
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getBtnPoveziSeSa());
			panel.add(getBtnIzadji());
			panel.add(getLblAplikacijaZaPovezivanje());
		}
		return panel;
	}
	private JButton getBtnPoveziSeSa() {
		if (btnPoveziSeSa == null) {
			btnPoveziSeSa = new JButton("Povezi se sa serverom");
			btnPoveziSeSa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new Thread(new ClientCalculator()).start();
					oneAtATimeOFF();
				}
			});
			
			
			btnPoveziSeSa.setPreferredSize(new Dimension(140, 23));
			btnPoveziSeSa.setMinimumSize(new Dimension(140, 23));
			btnPoveziSeSa.setMaximumSize(new Dimension(140, 23));
			btnPoveziSeSa.setBounds(10, 46, 152, 23);
		}
		return btnPoveziSeSa;
	}
	public void oneAtATimeOFF(){
		btnPoveziSeSa.setEnabled(false);
	}
	public void oneAtATimeON(){
		btnPoveziSeSa.setEnabled(true);
	}
	private JButton getBtnIzadji() {
		if (btnIzadji == null) {
			btnIzadji = new JButton("Izadji");
			btnIzadji.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
			btnIzadji.setBounds(234, 46, 140, 23);
		}
		return btnIzadji;
	}
	private JLabel getLblAplikacijaZaPovezivanje() {
		if (lblAplikacijaZaPovezivanje == null) {
			lblAplikacijaZaPovezivanje = new JLabel("Aplikacija za povezivanje na server kalkulator");
			lblAplikacijaZaPovezivanje.setHorizontalAlignment(SwingConstants.CENTER);
			lblAplikacijaZaPovezivanje.setBounds(10, 11, 364, 14);
		}
		return lblAplikacijaZaPovezivanje;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBorder(new LineBorder(new Color(130, 135, 144), 1, true));
			scrollPane.setPreferredSize(new Dimension(150, 150));
			scrollPane.setViewportView(getTextPane());
		}
		return scrollPane;
	}
	private JTextPane getTextPane() {
		if (textPane == null) {
			textPane = new JTextPane();
			textPane.setEditable(false);
		}
		return textPane;
	}
	public void consoleWrite(String a){
		textPane.setText(textPane.getText()+"\n"+a);
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}

}
