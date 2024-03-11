public class Main {
    public static void main(String[] args) {
        // Create and start Peer 1 on port 8080
        Peer peer1 = new Peer(8080);
        peer1.start();

        // Create and start Peer 2 on port 9090
        Peer peer2 = new Peer(9090);
        peer2.start();

        // Peer 1 sends a message to Peer 2
        peer1.sendMessage("localhost", 9090, "Hello from Peer 1");

        // Peer 2 sends a message to Peer 1
        peer2.sendMessage("localhost", 8080, "Hello from Peer 2");
    }
}