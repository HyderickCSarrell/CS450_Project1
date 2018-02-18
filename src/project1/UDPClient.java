package project1;

import java.net.*;
import java.io.*;
import java.util.*;


public class UDPClient extends Thread{
    
    private String hostName;
    private String message;
    
    public UDPClient(String hostNameInput, String messageInput, int NumOfThreads) {
        hostName = hostNameInput;
        message = messageInput;
        
    }
    
    public void run() {
        try {
            
            // Create a datagram socket, look for the first available port
            DatagramSocket socket = new DatagramSocket();
            
            System.out.println("Using local port: " + socket.getLocalPort());
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            PrintStream pOut = new PrintStream(bOut);
            pOut.print(message);
            
            //convert printstream to byte array
            byte[] bArray = bOut.toByteArray();
            
            //Create a datagram packet, containing a maximum buffer of 256 bytes
            DatagramPacket packet = new DatagramPacket(bArray, bArray.length);
            
            System.out.println("Looking for hostname" + hostName);
            
            //get the InetAddress object
            InetAddress remote_addr = InetAddress.getByName(hostName);
            
            //check its IP number
            System.out.println("Hostname has IP address =" + remote_addr.getHostAddress());
            
            //configure the DataGrampacket
            packet.setAddress(remote_addr);
            packet.setPort(2000);
            
            //send the packet
            socket.send(packet);
            
            System.out.println("Packet sent at!" + new Date());
            
            // Display packet information
            System.out.println("Sent by :" + remote_addr.getHostAddress());
            System.out.println("Send from: " + packet.getPort());
            
        }
        catch(UnknownHostException ue){
            System.out.println("Unknown host"+ hostName);
        }
        catch(IOException e) {
            System.out.println("Error-" + e);
        }
    }
}
