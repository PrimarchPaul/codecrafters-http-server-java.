import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import httpServer.*;

public class Main {
  static ServerSocket serverSocket;
  public static void main(String[] args) {


    try {
      serverSocket = new ServerSocket(4221);
      while (true) {
        new HTTPServer(serverSocket.accept());
      }

      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);

    } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
    }
  }
}
