package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;

public class Server_Thread_Rec extends Thread {
 private Socket socket;
 private DataInputStream din;
 private DataOutputStream dout;
 private Server_DB db;
 private String name = " ";
 private Server_Send send;
 private Server_Send ex;
 
 public Server_Thread_Rec(Socket socket) throws IOException {
  this.socket = socket;
  db = new Server_DB();
 }

 public void Token(String msg) throws IOException {
  StringTokenizer to = new StringTokenizer(msg, "　");
  String time = to.nextToken();
  String name = to.nextToken();
  String chat = to.nextToken("");
  db.setting(time, name, chat);
 }

 public String checkN(DataOutputStream dout, DataInputStream din) throws IOException {
  String Nick;
  while (true) {
   Nick = din.readUTF();
   if (Server.map.containsKey(Nick)) {
    dout.writeUTF("NOT");
   } else {
    dout.writeUTF("　");
    Server.User_C++;
    Server.map.put(Nick, socket);
    return Nick;
   }
  }
 }
 
 public void run() {
  try {
   din = new DataInputStream(socket.getInputStream());
   dout = new DataOutputStream(socket.getOutputStream());
   name = checkN(dout, din);
   String Hello = din.readUTF();
   System.out.println(Hello);
   Server_Send hello = new Server_Send(Hello);
   hello.start();
   
   while (true) {
    String msg = din.readUTF();
    if (msg.equals("exit")) {
     System.out.println(name + " 접속종료");
     Server.User_C--;
     Server.map.remove(name);
     ex = new Server_Send(name + " 접속종료");
     ex.start();
     break;
    } else if (msg.equals("who!")) {
     dout.writeUTF("현재 참여자 목록: " + Server.map.keySet() + "  총 " + Server.User_C + " 명");
    } else {
     System.out.println(msg);
     send =  new Server_Send(msg);
     send.start();
     Token(msg);
    }
   }
  } catch (SocketException e) {
   try {
    System.out.println(name + " 강제종료");
    Server.User_C--;
    Server.map.remove(name);
    ex = new Server_Send(name + " 강제종료");
    ex.start();

   } catch (NullPointerException e1) {
    Server.User_C--;
   }
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   try {
    socket.close();
   } catch (IOException e) {
    e.printStackTrace();
   }
  }
 }
}