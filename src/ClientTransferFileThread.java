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
	LinkedList<Broj> nizBrojeva = new LinkedList<Broj>();
	BufferedReader inputToServer = null;
	PrintStream outputToServer = null;
	ObjectOutputStream dataToServer=null;
	String result;
	public ClientTransferFileThread(String znak,LinkedList<Broj> nizBrojeva) {
		super();
		this.znak = znak;
		this.nizBrojeva = nizBrojeva;
	}
	public void run(){
		try {
			socketFileTransfer = new Socket("localhost", 13413);
			outputToServer = new PrintStream(socketFileTransfer.getOutputStream());
			inputToServer = new BufferedReader(new InputStreamReader(socketFileTransfer.getInputStream()));
			dataToServer = new ObjectOutputStream(new BufferedOutputStream(socketFileTransfer.getOutputStream()));
			while(true){
				if(inputToServer.readLine().contains("operation")){
					outputToServer.println(znak);
					break;
				}
			}
			while(true){
				if(inputToServer.readLine().contains("numbers")){
					dataToServer.writeObject(nizBrojeva);
					break;
				}
			}
			while((result=inputToServer.readLine())==null){
				
			}
			outputToServer.println("recived");
			ClientCalculator.alertResponse(result);
			socketFileTransfer.close();
			//da li treba nesto za ciscenje svih parametra
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
