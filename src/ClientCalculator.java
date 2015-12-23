import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ClientCalculator implements Runnable {
	public volatile static boolean requestForCalculation = false;
	public volatile static boolean prozorPrvi= false;
	public volatile static boolean prozorDrugi= false;
	private static GUICalculatorStart frame; 
	private Socket clientSocketControl=null;
	private PrintStream outputToServer=null;
	private BufferedReader inputToServer=null;
	private volatile static boolean fileTransfered=false;
	private volatile static String result;
	private GUICalculatorConnected prozor2;
	
	

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
			prozor2 = new GUICalculatorConnected();
			prozor2.setLocationRelativeTo(frame);
			prozor2.setVisible(true);
			prozor2.requestFocus();//??????????????????????/
			frame.setEnabled(false);
			prozorDrugi=true;
			Lock lockcode = new ReentrantLock();
			prozor2.resultWrite("nesto");
			while(true){ 
				if(!prozorDrugi){
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
				//lockcode.lock();
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
				//lockcode.unlock();
			}
			
			
		} catch (UnknownHostException e) {
			frame.consoleWrite("Veza sa serverom je prekinuta");
			frame.setEnabled(true);
			prozor2.dispose();
		} catch (IOException e) {
			frame.consoleWrite("Konekcija sa serverom nije uspela");
			frame.setEnabled(true);
			prozor2.dispose();
		}
		
		
	}
		
		
	 	public static void alertResponse(String a){
	 		
	 		result=a;
	 		fileTransfered=true;
	 		
	 	}

}
