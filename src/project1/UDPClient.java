package project1;

import java.net.*;
import java.io.*;
import java.util.*;


public class UDPClient extends Thread{
    
    private String hostName;
    private String message;
    private int numOfIterations;
    private Random uniqueSeqGenerator;
    private int uniqueSeqNum;
    private long startTime;
    private boolean[] wasPacketRecieved;
    public byte[] packetRecievedMessage;
    
    public UDPClient(String hostNameInput, int numOfThreads) {
        hostName = hostNameInput;
        numOfIterations = numOfThreads;
        uniqueSeqGenerator = new Random();
        wasPacketRecieved = new boolean[numOfThreads];
        packetRecievedMessage = new byte[5];
        packetRecievedMessage[0] = 25;
        
    }
    
    public void run() {
        try {
            for(int i = 0; i < numOfIterations; i++) {
                
                
                uniqueSeqNum = uniqueSeqGenerator.nextInt();
                // Create a datagram socket, look for the first available port
                DatagramSocket socket = new DatagramSocket();

                //System.out.println("Using local port: " + socket.getLocalPort());
                ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                PrintStream pOut = new PrintStream(bOut);
                pOut.print(uniqueSeqNum);

                //convert printstream to byte array
                byte[] bArray = bOut.toByteArray();

                //Create a datagram packet, containing a maximum buffer of 256 bytes
                DatagramPacket packet = new DatagramPacket(bArray, bArray.length);

                //System.out.println("Looking for hostname" + hostName);

                //get the InetAddress object
                InetAddress remote_addr = InetAddress.getByName(hostName);

                //check its IP number
                //System.out.println("Hostname has IP address =" + remote_addr.getHostAddress());

                //configure the DataGrampacket
                packet.setAddress(remote_addr);
                packet.setPort(2000);

                //send the packet
                socket.send(packet);
                startTime = System.currentTimeMillis();
                
                
                System.out.println("Packet sent at!" + new Date());

                // Display packet information
                System.out.println("Sent by :" + remote_addr.getHostAddress());
                System.out.println("Send from: " + packet.getPort());
                
                //Creates array to recieve acknowlegement
                byte[] ackArray = new byte[256];
                DatagramPacket ackPacket = new DatagramPacket(ackArray, ackArray.length);
                socket.receive(ackPacket);
                
                if (ackPacket.getData()[0] == packet.getData()[0]) {
                    wasPacketRecieved[i] = true;
                    startTime = 0;
                }
            }
            
        }
        catch(UnknownHostException ue){
            System.out.println("Unknown host"+ hostName);
        }
        catch(IOException e) {
            System.out.println("Error-" + e);
        }
    }
    
    public long returnStartTime() {
        return startTime;
    }
    
       public boolean returnPacketStatus(int count) {
        return wasPacketRecieved[count];
    }
}
