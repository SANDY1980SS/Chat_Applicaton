package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;

/**
 *
 * @author iesha
 */
public class Client {
  private JFrame clientframe;
  private JTextArea ta;
  private JTextField tf;
  private   JScrollPane scrollPane;
  private Socket socket;
  private DataInputStream dis;
  private DataOutputStream dos;
  //--------------------------------Thread Created--------------------------------------------
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
  //------------------------------------------------------------------------------------------
  
  String ipaddress;
    Client(){
        
    ipaddress = JOptionPane.showInputDialog("Enter IP Address"); //ye input lene ke liye hai..
    
    if(ipaddress != null){
        if(!ipaddress.equals("")){
            connectToServer();
    clientframe = new JFrame("Client");
    clientframe.setSize(500, 500);
    clientframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    ta = new JTextArea();
    ta.setEditable(false);
    Font font = new Font("Arial",1,16);
    ta.setFont(font);
    scrollPane = new JScrollPane(ta);
    clientframe.add(scrollPane);
    
    tf = new JTextField();
    tf.addActionListener(new ActionListener(){
     @Override
     public void actionPerformed(ActionEvent e){
         sendMessage(tf.getText());
         ta.append(tf.getText()+"\n");
         tf.setText("");
    }
    });
    clientframe.add(tf,BorderLayout.SOUTH);
    
    
    clientframe.setVisible(true);
        }
    }
 }

    public void connectToServer(){
        try {
            socket = new Socket(ipaddress, 1111);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     public void setIoStream(){
         thread.start();
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
            showMessage("Server: "+message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
       public void showMessage(String message){
    ta.append(message+"\n");
    
    }
    
}
