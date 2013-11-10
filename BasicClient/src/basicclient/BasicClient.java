
package basicclient;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class BasicClient {
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;
    String sendText = "";
    String getText;

   public static void main(String[] args) {
      BasicClient client = new BasicClient();
      client.setupNetworking(5000);
      client.go();
   }
   
   public void setupNetworking(int port) {
      try{
         socket = new Socket("127.0.0.1", port);
         reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         writer = new PrintWriter(socket.getOutputStream());
         System.out.println("*** networking established...");
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   public void go() {
      Runnable incoming = new IncomingReader();
      Runnable outgoing = new OutgoingWriter();
      Thread incomingThread = new Thread(incoming);
      Thread outgoingThread = new Thread(outgoing);
      incomingThread.start();
      outgoingThread.start(); 
   }
   
   public class IncomingReader implements Runnable {
      public void run() {
         try{
            while((getText = reader.readLine()) != null) {
               System.out.println("Received: " + getText);
            }
         }
         catch(Exception e) {
            e.printStackTrace();
         }
      }
   }
   
   public class OutgoingWriter implements Runnable {
      public void run() {
         while(sendText != null) {
         sendText = JOptionPane.showInputDialog(null, "Message to send:");
         writer.println(sendText);
         writer.flush();
         }
         System.exit(0);
      }
   }
}
