package serverr;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Server {
    private JFrame serverframe;
    private JTextArea ta;
    private JScrollPane scrollPane;
    private JTextField tf;
    private ServerSocket serverSocket;
    private InetAddress inet_aAddress; // Ip address ke liye hai ye class
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    //-----------------------------------Thread Created------------------------------------------
    Thread thread = new Thread()
    {
      public void run()
      {
      while(true)
      {
          readMessage();
      }
      }
    };
    //-------------------------------------------------------------------------------------------
    Server(){
         serverframe = new JFrame("Server");
         serverframe.setSize(500, 500);
         serverframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         ta = new JTextArea();
         ta.setEditable(false);
         Font font = new Font("Arial",1,16);
         ta.setFont(font);
         scrollPane = new JScrollPane(ta);
         serverframe.add(scrollPane);
         
         tf = new JTextField();
         tf.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e){
                 sendMessage(tf.getText());
                 ta.append(tf.getText()+"\n");
                 tf.setText("");
         }
      });
         tf.setEditable(false);
         serverframe.add(tf,BorderLayout.SOUTH); // ye last me ekdam add krne ke liye use hota hai...
         
         
         
         serverframe.setVisible(true);
    }
    public void waitingForClient(){
        try {
            String ipaddress = getIpAddress();
             serverSocket = new ServerSocket(1111);
             ta.setText("To connect with server, please provide IP Address: "+ipaddress);
             socket = serverSocket.accept();
             ta.setText("Client Connected\n");
             ta.append("<------------------------------------------->\n");
             
             tf.setEditable(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String getIpAddress(){
        String ip_address="";
        try {
            inet_aAddress = InetAddress.getLocalHost(); //ip address lene ke liye hai ye
     ip_address = inet_aAddress.getHostAddress();
        System.out.println(ip_address);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ip_address;
    }
    
    public void setIoStream(){
       
        try {
            
         dis = new DataInputStream(socket.getInputStream());
         dos = new DataOutputStream(socket.getOutputStream());
            
        } catch (Exception e) {
            System.out.println(e);
        }
         thread.start(); 
    }
    public void sendMessage(String message)
    {
        try {
            
        dos.writeUTF(message);
        dos.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public void readMessage()
    {
        try {
          String message = dis.readUTF();
            showMessage("Client: "+message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    public void showMessage(String message){
    ta.append(message+"\n");
    
    }
   
}
