package project1;

import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer extends Thread {
    
    private DatagramSocket socket;
    private DatagramPacket packet;
    private Random randomNumStream;
    private boolean wasServerPacketRecieved;
    
    public UDPServer() {
        wasServerPacketRecieved = false;
    }

    
    public void run() {
        try {
            
            //Create a datagram socket, bound to the specific port 2000
            socket = new DatagramSocket(2000);

            System.out.println("Bound to local port" + socket.getLocalPort());

            //Create a datagram packet, containing a maximum buffer of 256 bytes
            packet = new DatagramPacket(new byte[256], 256);
            
            randomNumStream = new Random();
            
            while(true) {
                //Receive a packet -remember by default this is a blocking operation
                socket.receive(packet);
                wasServerPacketRecieved = true;
                int randomNum = randomNumStream.nextInt();
                
                if (randomNum % 2 == 0) {
                    randomNum = randomNumStream.nextInt(201);
                    
                    sleep(randomNum);
                    socket.send(packet);
                }
                
                
                System.out.println("Packet received at " + new Date());
                //Display packet information
                InetAddress remote_addr = packet.getAddress();
                System.out.println("Sender: " + remote_addr.getHostAddress());
                System.out.println("from Port: " + packet.getPort());

                //Display packet contents, by reading from byte array

                ByteArrayInputStream bin = new ByteArrayInputStream(packet.getData());

                //Display only up to the length of the original UDP packet
                /*
                for(int i = 0; i < packet.getLength(); i++) {
                    int data = bin.read();
                    if(data == -1) {
                        break;
                    }
                    else System.out.print((char) data);
                }
                */
                //socket.close();

        }
        
    }
    catch (IOException e){System.out.println("Error-" + e);}
    catch (InterruptedException e){System.out.println("Error-" + e);}
    }
    
    public boolean returnWasPacketRecieved() {
        return wasServerPacketRecieved;
    }
    
    public int returnPacketData() {
        byte[] data = packet.getData();
        
        int sequenceNum = (int)data[0];
        return sequenceNum;
    }
}
