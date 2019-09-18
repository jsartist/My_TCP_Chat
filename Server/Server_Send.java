package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map.Entry;

public class Server_Send extends Thread {
 private Socket socket;
 private String msg;

 public Server_Send(String msg) {
  this.msg = msg;
 }

 public void run() {
  DataOutputStream dout;
  try {
   for (Entry<String, Socket> entry:Server.map.entrySet()) {
    socket = entry.getValue();
    dout = new DataOutputStream(socket.getOutputStream());
    dout.writeUTF(msg);
   }
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }
}