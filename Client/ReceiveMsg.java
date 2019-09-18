package Client;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ReceiveMsg extends Thread {
 private Socket socket;
 private DataInputStream dio;

 public ReceiveMsg(Socket socket) {
  this.socket = socket;
 }

 public void run() {
  String msg;
  try {
   dio = new DataInputStream(socket.getInputStream());
   while (true) {
    msg = dio.readUTF();
    MainClient_GUI.area.append(msg + "\n");
    MainClient_GUI.area.setCaretPosition(MainClient_GUI.area.getDocument().getLength());
   }
  } catch (EOFException e) {
   System.out.println("Receive Thread 종료");
  } catch (SocketException e) {
   System.out.println("Receive Thread 종료");
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }
}
