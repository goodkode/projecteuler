
package basicserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class BasicServer {
   ArrayList clientOutputStreams;
   ServerSocket servSock;

   public static void main(String[] args) {
      BasicServer server = new BasicServer();
      server.go();
   }
   
   public void go() {
      clientOutputStreams = new ArrayList();
      try{
         servSock = new ServerSocket(5000);
         Thread serverHB = new Thread(new ServerHeartBeat());
         serverHB.start();
         while(true) {
            Socket clientSock = servSock.accept();
            PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
            clientOutputStreams.add(writer);
            Thread clientHandler = new Thread(new ClientHandler(clientSock));
            clientHandler.start();
         }
      }
      catch(Exception e) {e.printStackTrace();}
   }
   
   public class ServerHeartBeat implements Runnable {
      public void run() {
         try{
            for(int i = 1; i <= 20; i++) {
               System.out.println("-heartbeat-" + i + "-");
               broadcast("-heartbeat-" + i + "-");
               Thread.sleep(5000);
            }
         }
         catch(Exception ex) {ex.printStackTrace();}
      }
   }
   
   public class ClientHandler implements Runnable {
      Socket clientSock;
      BufferedReader reader;
      
      public ClientHandler(Socket clientSock) {
         try{
            this.clientSock = clientSock;
            reader = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
            System.out.println("** New: " + clientSock.toString() + "**");
         }
         catch(Exception ex) {ex.printStackTrace();}
      }
      
      public void run() {
         String msg;
         try{
            while((msg = reader.readLine()) != null) {
               System.out.println("received: " + msg);
               broadcast(msg);
            }
         }
         catch(Exception ex) {ex.printStackTrace();}
      }
   }
   
   public void broadcast(String msg) {
      Iterator it = clientOutputStreams.iterator();
      while(it.hasNext()) {
         try {
            PrintWriter writer = (PrintWriter) it.next();
            writer.println(msg);
            writer.flush();
         }
         catch(Exception ex) {ex.printStackTrace();}
      }
   }
}
