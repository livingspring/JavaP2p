public class P2 {
    public static void main(String[] args) {


        // Create and start Peer 2 on port 9090
        Peer peer2 = new Peer(9090);
        peer2.start();

 

        // Peer 2 sends a message to Peer 1
        peer2.sendMessage("localhost", 8080, "Hello from Peer 2");
    }
}
