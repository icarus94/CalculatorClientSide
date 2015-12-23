import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientCalculator implements Runnable {
	public static boolean requestForCalculation = false;
	public static boolean prozorPrvi= false;
	public static boolean prozorDrugi= false;
	private static GUICalculatorStart frame; 
	private Socket clientSocketControl=null;
	private PrintStream outputToServer=null;
	private BufferedReader inputToServer=null;
	private static boolean fileTransfered=false;
	private static String result;
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUICalculatorStart();
					frame.setVisible(true);
					frame.consoleWrite("Dobrodosli :) \n");
					prozorPrvi=true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 	public void run() {
		frame.consoleWrite("Povezivanje sa serverom...");
		try {
			clientSocketControl = new Socket("localhost", 1908);
			outputToServer = new PrintStream(clientSocketControl.getOutputStream());
			inputToServer = new BufferedReader(new InputStreamReader(clientSocketControl.getInputStream()));
			frame.consoleWrite(inputToServer.readLine());
			GUICalculatorConnected prozor2 = new GUICalculatorConnected();
			prozor2.setLocationRelativeTo(frame);
			prozor2.setVisible(true);
			prozor2.requestFocus();//??????????????????????/
			frame.setEnabled(false);
			prozorDrugi=true;
			while(true){ 
				if(!prozorDrugi){
					System.out.println("proso");
					outputToServer.println("exit");
					String a;
					/*while((a=inputToServer.readLine())!=null){
						frame.consoleWrite(a);
					}*/
					inputToServer.close();
					outputToServer.close();
					clientSocketControl.close();
					frame.setEnabled(true);
					return;
				}
				if(requestForCalculation){
					outputToServer.println("need_to_calculate");
					requestForCalculation=false;
					while(true){
						if(inputToServer.readLine().contains("approved")){
							new ClientTransferFileThread(prozor2.znak, prozor2.nizBrojeva).start();
							break;
						}
					}
				}
				if(fileTransfered){
					fileTransfered=false;
					prozor2.resultWrite(result);
				}
			}
			
			
		} catch (UnknownHostException e) {
			frame.consoleWrite("Veza sa serverom je prekinuta");
		} catch (IOException e) {
			frame.consoleWrite("Konekcija sa serverom nije uspela");
		}
		
		
	}
	 	synchronized public static void alertResponse(String a){
	 		result=a;
	 		fileTransfered=true;
	 	}

}
