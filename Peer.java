import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Peer {

    private final int port;
    private ServerSocket serverSocket;

    public Peer(int port) {
        this.port = port;
    }

    // Method to start the peer
    public void start() {
        new Thread(this::startServer).start();
    }

    // Method to start the server socket to accept incoming connections
    private void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleConnection(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to handle an incoming connection from another peer
    private void handleConnection(Socket socket) {
       try {
        // Get the InetAddress of the connected peer
        InetAddress peerAddress = socket.getInetAddress();
        
        // Get the port of the connected peer
        int peerPort = socket.getPort();

        // Read the message sent by the peer
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = in.readLine();
        
        // Print the received message along with peer's IP address and port
        System.out.println("Received message from " + peerAddress.getHostAddress() + ":" + peerPort + ": " + message);
        
        // Close the input stream and the socket
        in.close();
        socket.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    // Method to send a message to another peer
    public void sendMessage(String host, int port, String message) {
        try {
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            out.close();
            socket.close();
            System.out.println("Message sent to " + host + ":" + port + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to interact with the peer via the command line
    public void interact() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type a message to send (type 'exit' to quit):");
        try {
            String message;
            while (!(message = reader.readLine()).equals("exit")) {
                System.out.println("Sending message: " + message);
                // Assuming peer2's IP address and port are hardcoded for simplicity
                sendMessage("localhost", 9090, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main method to run the peer
    public static void main(String[] args) {
        // Create and start a peer on port 8080
        Peer peer = new Peer(8080);
        peer.start();
        peer.interact();
        // Example: sending a message to another peer
        // Replace "localhost" and 9090 with the host and port of the target peer
        peer.sendMessage("localhost", 9090, "Hello from Peer 1");
    }

}