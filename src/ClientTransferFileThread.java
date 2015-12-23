import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;


public class ClientTransferFileThread extends Thread {
	String znak;
	Socket socketFileTransfer=null;
	LinkedList<Double> nizBrojeva = new LinkedList<Double>();
	BufferedReader inputToServer = null;
	PrintStream outputToServer = null;
	ObjectOutputStream dataToServer=null;
	String result=null;
	public ClientTransferFileThread(String znak,LinkedList<Double> nizBrojeva) {
		super();
		this.znak = znak;
		this.nizBrojeva = nizBrojeva;
	}
	synchronized public void run(){
		try {
			socketFileTransfer = new Socket("localhost", 1908);
			outputToServer = new PrintStream(socketFileTransfer.getOutputStream());
			inputToServer = new BufferedReader(new InputStreamReader(socketFileTransfer.getInputStream()));
			//dataToServer = new ObjectOutputStream(new BufferedOutputStream(socketFileTransfer.getOutputStream()));
			
			if(inputToServer.readLine().contains("operation"))
				outputToServer.println(znak);
			
			while(true){
				if(inputToServer.readLine().contains("numbers")){
					for (int i = 0; i < nizBrojeva.size(); i++) {
						outputToServer.println(nizBrojeva.get(i));
					}
					outputToServer.println("done");
					break;
				}
			}
			result=inputToServer.readLine();
			ClientCalculator.alertResponse(result);
	
			inputToServer.close();
			outputToServer.close();
			socketFileTransfer.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
