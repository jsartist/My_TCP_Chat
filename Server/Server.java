package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class Server {
 private ServerSocket serversocket = null;
 private Socket socket;
 static HashMap<String, Socket> map = new HashMap<String, Socket>();
 
 static int User_C = 0;

 public void Acc() throws IOException {
  serversocket = new ServerSocket(7000);
  while (true) {
   socket = serversocket.accept();
   new Server_Thread_Rec(socket).start();
  }
 }
 public static void main(String[] args) throws IOException {
  Server Administrator = new Server();
  Administrator.Acc();
 }
}