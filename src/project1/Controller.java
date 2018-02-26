package project1;

public class Controller {
    
    private static UDPClient client;
    private static UDPServer server;

    public static void main(String[] args) {
        
        client = new UDPClient("localhost", 10);
        server = new UDPServer();
        client.start();
        server.start();
        
        long startTime = client.returnStartTime();
        long endTime = System.currentTimeMillis();
        
        while(startTime == 0) {
            startTime = client.returnStartTime();
        }
        
        long totalDuration = endTime - startTime;
        
        System.out.println("The start time: " + startTime);
        
        boolean hasTimeExpired = false;
        while(!hasTimeExpired) {
            if (totalDuration > 300) {
                System.out.println("The client has been interrupted: Could not reach host!");
                client.interrupt();
                hasTimeExpired = true;
            }
            else {
                endTime = System.currentTimeMillis();
                totalDuration = endTime - startTime;
            }
        }
        
        System.exit(0);
        
    }
    
}
