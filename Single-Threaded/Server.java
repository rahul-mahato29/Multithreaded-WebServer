import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {

    public void run() throws IOException { 
        int port = 8081;
        int i = 0;
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(10000);
            while(true) {
                try {
                    i++;
                    System.out.println("Server is running : "+ i);
                    Socket acceptedConnection = socket.accept(); 
                    System.out.println("Connection accepted from client : "+acceptedConnection.getRemoteSocketAddress());
                    //from-server
                    PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
                    //from-client
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
    
                    toClient.println("Hello from server");
    
                    //close resources
                    toClient.close();
                    fromClient.close();
                    acceptedConnection.close();
                }
                catch(IOException e) {
                    e.printStackTrace(); 
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}