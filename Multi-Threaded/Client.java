import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Client {

    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try {
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address, port);
                    try (
                        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ) {
                        toSocket.println("Hello from Client "+ socket.getLocalSocketAddress());
                        String line = fromSocket.readLine();
                        System.out.println("Response from server : "+ line);
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }   
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();
        for(int i=0; i<100; i++) {
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            }
            catch(Exception e) {
                return;
            }
        }

    }
}