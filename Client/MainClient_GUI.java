package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;

public class MainClient_GUI extends JFrame {
 private final String IP = "123.123.123.123";
 private final int PORT = 7000;
 private JPanel panel; // panel
 static JTextArea area; // Text Area
 private JScrollPane scroll; // Text Scroll
 private JTextField field; // Text Field
 private JButton button; // Enter
 private DataOutputStream dout;
 private DataInputStream din;
 private Socket socket;
 private String Nick;

 Scanner sc = new Scanner(System.in, "euc-kr");

 public MainClient_GUI() {

  // JFrame 제목
  super("Chat");

  // 소켓
  try {
   socket = new Socket(IP, PORT);
   System.out.println("접속성공");
   dout = new DataOutputStream(socket.getOutputStream());
   din = new DataInputStream(socket.getInputStream());
   MakeNick(dout, din);
   dout.writeUTF(Nick + " 님이 서버에 참여하셨습니다.");
  } catch (UnknownHostException e2) {
   e2.printStackTrace();
  } catch (IOException e) {
   System.out.println("서버가 닫혀있습니다.");
   System.exit(1);
  }

  // JFrame 크기
  setSize(600, 800);

  // X 활성화
  setDefaultCloseOperation(EXIT_ON_CLOSE);

  // panel 생성, 레이아웃은 사용자지정으로 하겠다.
  panel = new JPanel();
  panel.setLayout(null);

  // Area 생성 및 크기와 위치 설정, 스크롤활성화
  area = new JTextArea();
  area.setEnabled(false);
  area.setLineWrap(true);
  Font font = new Font("고딕", Font.BOLD, 15);
  area.setFont(font);
  area.setBackground(Color.DARK_GRAY);
  scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  area.setBounds(50, 50, 480, 550);
  scroll.setBounds(50, 50, 480, 550);

  // Button 생성 및 크기와 위치 설정
  button = new JButton("보내기");
  button.setBounds(450, 600, 80, 40);

  // textfield Action
  Action ok = new AbstractAction() {

   @Override
   public void actionPerformed(ActionEvent e) {
    String msg = field.getText();
    try {
     dout = new DataOutputStream(socket.getOutputStream());

     if (msg.equals("exit")) {
      dout.writeUTF(msg);
     } else if (msg.equals("who!")) {
      dout.writeUTF(msg);
     } else {
      Date today = new Date();
      SimpleDateFormat time = new SimpleDateFormat("a hh:mm:ss");
      dout.writeUTF("[ " + time.format(today) + " ]　" + Nick + "　: " + msg);
     }

    } catch (SocketException se) {
     System.out.println("서버가 닫혀있습니다.");
     System.out.println("클라이언트를 종료합니다.");
    } catch (IOException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    field.setText("");
   }
  };

  // Field 생성 및 크기와 위치 설정
  KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
  field = new JTextField();
  field.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "ENTER");
  field.getActionMap().put("ENTER", ok);
  field.setBounds(50, 600, 400, 40);
  field.setEnabled(true);

  // button ActionListener
  button.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    String msg = field.getText();
    try {
     dout = new DataOutputStream(socket.getOutputStream());

     if (msg.equals("exit")) {
      dout.writeUTF(msg);
     } else if (msg.equals("who!")) {
      dout.writeUTF(msg);
     } else {
      Date today = new Date();
      SimpleDateFormat time = new SimpleDateFormat("a hh:mm:ss");
      dout.writeUTF("[ " + time.format(today) + " ]　" + Nick + "　: " + msg);
     }

    } catch (SocketException se) {
     System.out.println("서버가 닫혀있습니다.");
     System.out.println("클라이언트를 종료합니다.");
    } catch (IOException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    field.setText("");
   }
  });
  
  //메뉴바
  JMenuBar menubar = new JMenuBar();
  JMenu menu = new JMenu("Menu");
  JMenuItem menuItem;
  menubar.add(menu);
  menuItem = new JMenuItem("로그");
  menu.add(menuItem);
  setJMenuBar(menubar);
  // panel을 이용해 붙이기
  panel.add(field);
  panel.add(scroll);
  panel.add(button);
  
  //메뉴 리스너
  menuItem.addActionListener(new ActionListener() {
   
   @Override
   public void actionPerformed(ActionEvent e) {
    if(e.getSource() == menuItem) {
     Runnable run = new Log();
     Thread th = new Thread(run);
     th.start();
     
    }
   }
  });
  
  // panel을 frame에 붙이기
  add(panel);

  // 띄우기
  setVisible(true);

  new receiveMsg(socket).start();
 }

 public void MakeNick(DataOutputStream dout, DataInputStream din) throws IOException {
  String eq = "NOT";
  String cat;
  while (true) {
   System.out.print("닉네임 입력: ");
   Nick = sc.nextLine();
   dout.writeUTF(Nick);
   cat = din.readUTF();
   if (cat.equals(eq)) {
    System.out.println("이미 사용중인 닉네임입니다.");
   } else {

    System.out.println("입장완료");
    break;
   }
  }
 }

 public static void main(String[] args) {
  MainClient_GUI gui = new MainClient_GUI();
 }
}