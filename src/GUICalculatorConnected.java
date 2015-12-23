import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GUICalculatorConnected extends JFrame {

	private JPanel contentPane;
	private JButton btnPosaljiZahtev;
	private JButton btnIzadji;
	private JLabel lblOdabratiOperaciju;
	private JRadioButton rdbtnSabiranje;
	private JRadioButton rdbtnOduzimanje;
	private JRadioButton rdbtnMnozenje;
	private JRadioButton rdbtnDeljenje;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JLabel lblUnetiOvdeBrojeve;
	private JTextField textField_1;
	private JButton btnUnesi;
	private JTextPane textPane;
	
	
	
	/**
	 * Lista brojeva za racunjanje
	 */
	public static LinkedList<Broj> nizBrojeva = new LinkedList<Broj>();
	/**
	 * odabrani znak
	 */
	public static String znak="+";
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUICalculatorConnected frame = new GUICalculatorConnected();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUICalculatorConnected() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			synchronized public void windowClosed(WindowEvent arg0) {
				ClientCalculator.prozorDrugi=false;
				dispose();
			}
			@Override
			synchronized public void windowClosing(WindowEvent arg0) {
				ClientCalculator.prozorDrugi=false;
				dispose();
			}
		});
		setTitle("Calculator - Connected to Server");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnPosaljiZahtev());
		contentPane.add(getBtnIzadji());
		contentPane.add(getLblOdabratiOperaciju());
		contentPane.add(getRdbtnSabiranje());
		contentPane.add(getRdbtnOduzimanje());
		contentPane.add(getRdbtnMnozenje());
		contentPane.add(getRdbtnDeljenje());
		contentPane.add(getTextField());
		contentPane.add(getLblUnetiOvdeBrojeve());
		contentPane.add(getTextField_1());
		contentPane.add(getBtnUnesi());
		contentPane.add(getTextPane());
		contentPane.add(getBtnNewButton());
	}
	private JButton getBtnPosaljiZahtev() {
		if (btnPosaljiZahtev == null) {
			btnPosaljiZahtev = new JButton("Posalji zahtev");
			btnPosaljiZahtev.addActionListener(new ActionListener() {
				synchronized public void actionPerformed(ActionEvent arg0) {
					if(nizBrojeva.isEmpty()){
						JOptionPane.showMessageDialog(null,
							    "Niste uneli ni 1 broj.. ",
							    "Greska pri unosu!",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
					ClientCalculator.requestForCalculation=true;
				}
			});
			btnPosaljiZahtev.setBounds(10, 227, 200, 23);
		}
		return btnPosaljiZahtev;
	}
	private JButton getBtnIzadji() {
		if (btnIzadji == null) {
			btnIzadji = new JButton("Izadji");
			btnIzadji.addActionListener(new ActionListener() {
				synchronized public void  actionPerformed(ActionEvent arg0) {
					ClientCalculator.prozorDrugi=false;
					dispose();
					
				}
			});
			btnIzadji.setBounds(224, 227, 200, 23);
		}
		return btnIzadji;
	}
	private JLabel getLblOdabratiOperaciju() {
		if (lblOdabratiOperaciju == null) {
			lblOdabratiOperaciju = new JLabel("Odabrati operaciju :");
			lblOdabratiOperaciju.setBounds(10, 11, 200, 14);
		}
		return lblOdabratiOperaciju;
	}
	private JRadioButton getRdbtnSabiranje() {
		if (rdbtnSabiranje == null) {
			rdbtnSabiranje = new JRadioButton("Sabiranje");
			rdbtnSabiranje.setSelected(true);
			rdbtnSabiranje.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					znak="+";
				}
			});
			buttonGroup.add(rdbtnSabiranje);
			rdbtnSabiranje.setBounds(10, 32, 109, 23);
		}
		return rdbtnSabiranje;
	}
	private JRadioButton getRdbtnOduzimanje() {
		if (rdbtnOduzimanje == null) {
			rdbtnOduzimanje = new JRadioButton("Oduzimanje");
			rdbtnOduzimanje.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					znak="-";
				}
			});
			buttonGroup.add(rdbtnOduzimanje);
			rdbtnOduzimanje.setBounds(10, 58, 109, 23);
		}
		return rdbtnOduzimanje;
	}
	private JRadioButton getRdbtnMnozenje() {
		if (rdbtnMnozenje == null) {
			rdbtnMnozenje = new JRadioButton("Mnozenje");
			rdbtnMnozenje.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					znak="*";
				}
			});
			buttonGroup.add(rdbtnMnozenje);
			rdbtnMnozenje.setBounds(10, 84, 109, 23);
		}
		return rdbtnMnozenje;
	}
	private JRadioButton getRdbtnDeljenje() {
		if (rdbtnDeljenje == null) {
			rdbtnDeljenje = new JRadioButton("Deljenje");
			rdbtnDeljenje.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					znak="/";
				}
			});
			buttonGroup.add(rdbtnDeljenje);
			rdbtnDeljenje.setBounds(10, 110, 109, 23);
		}
		return rdbtnDeljenje;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setEditable(false);
			textField.setBounds(10, 182, 414, 34);
			textField.setColumns(10);
		}
		return textField;
	}
	private JLabel getLblUnetiOvdeBrojeve() {
		if (lblUnetiOvdeBrojeve == null) {
			lblUnetiOvdeBrojeve = new JLabel("Uneti ovde brojeve:");
			lblUnetiOvdeBrojeve.setBounds(224, 11, 200, 14);
		}
		return lblUnetiOvdeBrojeve;
	}
	
	private JTextField getTextField_1() {
		if (textField_1 == null) {
			textField_1 = new JTextField();
			textField_1.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent arg0) {
					char c=arg0.getKeyChar();
					if(!Character.isDigit(c)){
						if(!(c=='.'))
						arg0.consume();
					}
				}
			});
			textField_1.setBounds(224, 32, 200, 23);
			textField_1.setColumns(10);
		}
		return textField_1;
	}
	private JButton getBtnUnesi() {
		if (btnUnesi == null) {
			btnUnesi = new JButton("Unesi");
			btnUnesi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textField_1.getText().isEmpty() || textField_1.getText().startsWith(".") || textField_1.getText().startsWith(" ")
							|| textField_1.getText().indexOf(".")!=textField_1.getText().lastIndexOf(".")){
						JOptionPane.showMessageDialog(null,
							    "Pogresno unet broj,moguce koristiti cifre 0-9 i znak '.' \t !\n Moze imati samo jednu decimalni zarez! ",
							    "Greska pri unosu!",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
					double a= Double.parseDouble(textField_1.getText());
					Broj noviBr = new Broj(a);
					nizBrojeva.addFirst(noviBr);
					textField_1.setText(null);
					textPane.setText(a+","+textPane.getText());
					
				}
			});
			btnUnesi.setBounds(224, 66, 99, 23);
		}
		return btnUnesi;
	}
	private JTextPane getTextPane() {
		if (textPane == null) {
			textPane = new JTextPane();
			textPane.setBounds(224, 95, 200, 76);
		}
		/*String tekst="";
		for (int i = 0; i < nizBrojeva.size(); i++) {
			tekst=nizBrojeva.get(i).getBroj()+","+tekst;
		}
		textPane.setText(tekst);*/
		return textPane;
	}
	public void resultWrite(String a){
		textField.setText(a);
		reset();
		
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Reset");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					reset();
					//znak="+";
					//rdbtnSabiranje.setSelected(true);
				}
			});
			btnNewButton.setBounds(325, 66, 99, 23);
		}
		return btnNewButton;
	}
	public void reset(){
		textField.setText(null);
		textField_1.setText(null);
		textPane.setText(null);
		nizBrojeva.clear();
		znak="+";
		rdbtnSabiranje.setSelected(true);
	}
}
