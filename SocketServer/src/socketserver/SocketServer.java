
package socketserver;

import java.net.*;
import java.io.*;

public class SocketServer {

   public static void main(String[] args) {
      System.out.println("Server started...");
      try{
         ServerSocket servSocket = new ServerSocket(5000);
         Socket sock = servSocket.accept();
         PrintWriter writer = new PrintWriter(sock.getOutputStream());    
         for(int i = 1; i <= 10; i++) {
            Thread.sleep(1000); 
            System.out.println("to be sent " + i + "...");
            writer.println("message " + i + "...");
            writer.flush();
         }
         writer.close();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
}
