
package socketclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClient {

   public static void main(String[] args) {
      System.out.println("Client started...");
      try{
         Socket sock = new Socket("127.0.0.1", 5000);
         InputStreamReader stream = new InputStreamReader(sock.getInputStream());
         BufferedReader reader = new BufferedReader(stream);          
         for(int i = 1; i <= 10; i++) {
            System.out.println("received: " + reader.readLine());            
            Thread.sleep(1000);
         }
         reader.close();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
}
