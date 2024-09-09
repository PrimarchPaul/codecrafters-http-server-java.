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

      // Read the request from the client
      InputStream input = clientSocket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      String line = reader.readLine();
      System.out.println(line);
      String[] httpRequest = line.split(" ");



      OutputStream output = clientSocket.getOutputStream();
      if (httpRequest[1].equals("/")) {
        output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
      } else if (httpRequest[1].startsWith("/echo/")) {
        String reqMsg = httpRequest[1].substring(6);
        String header = String.format(
                "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",
                reqMsg.length(), reqMsg);
        clientSocket.getOutputStream().write(header.getBytes());
      }else if (httpRequest[1].startsWith("/user-agent")) {
        String userAgent = null;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
          if (line.startsWith("User-Agent:")) {
            userAgent = line.substring("User-Agent:".length()).trim();
          }
        }
        String header = String.format(
                "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",
                userAgent.length(), userAgent);
        clientSocket.getOutputStream().write(header.getBytes());
      }else {
        output.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
      }

    } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
    }
  }
}
