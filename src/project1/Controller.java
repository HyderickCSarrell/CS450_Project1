package project1;

public class Controller {
    
    private static UDPClient client;

    public static void main(String[] args) {
        
        client = new UDPClient("localhost", 10);
        client.start();
        
        long startTime = client.returnStartTime();
        long endTime = System.currentTimeMillis();
        long totalDuration = endTime - startTime;
        
        
        while(!client.returnPacketStatus()) {
            
        }
        
    }
    
}
