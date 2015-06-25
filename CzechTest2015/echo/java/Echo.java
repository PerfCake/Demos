import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Echo {
   ServerSocket serverSocket;

   public Echo() {
   }

   public static void main(String[] args) {
      Echo es = new Echo();
      System.out.println("Java server listening at 12321, hit ^C to terminate...");
      es.start();
   }

   public void start() {
      try {
         serverSocket = new ServerSocket(12321);
         while (true) {
            Thread clientThread = new Thread(new ClientHandler(serverSocket.accept()));
            clientThread.start();
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            System.out.println("closing server socket");
            serverSocket.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

   }
}

class ClientHandler implements Runnable {
   private static int numConnections;
   Socket clientSocket;

   public ClientHandler(Socket s) {
      clientSocket = s;
   }

   public void run() {
      PrintWriter out = null;
      BufferedReader in = null;
      try {
         out = new PrintWriter(clientSocket.getOutputStream(), true);
         in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         String inputLine, outputLine;
         while ((inputLine = in.readLine()) != null) {
            outputLine = new StringBuilder(inputLine).reverse().toString();
            out.write(outputLine + "\n");
            out.flush();
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         out.close();
         try {
            in.close();
            clientSocket.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}