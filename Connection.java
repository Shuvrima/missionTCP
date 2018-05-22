//Name: Shuvrima Alam, UTA ID: 1001085726
//Name: Syed Zaim Zanaruddin, UTA ID:1001106858
//Date: 05/08/2018

package missionTCP;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;



public class Connection implements Runnable{
    Socket socket;
    int previousPort;

    public Connection(Socket socket, int previousPort) {
        this.socket = socket;
        this.previousPort = previousPort;
    }

    public void run() {
        try {

            while (true) {
                // Client Packet
                ObjectInputStream clientInput = new ObjectInputStream(socket.getInputStream());
                PacketData clientPacket = (PacketData) clientInput.readObject();

                //Print to Console Information about Packet
                System.out.printf("Source Agent ID: %d\n", clientPacket.srcPort);
                System.out.printf("Destination Agent ID: %d\n", clientPacket.destPort);
                System.out.printf("The Sequence Number: %d\n", clientPacket.seqNumber);
                System.out.printf("The Acknowledgement Number: %d\n", clientPacket.ackNumber);
                System.out.printf("DPR: %b\n",clientPacket.DRP);
                System.out.printf("TER: %b\n",clientPacket.TER);
                System.out.printf("URG: %b\n",clientPacket.URG);
                System.out.printf("ACK: %b\n",clientPacket.ACK);
                System.out.printf("RST: %b\n",clientPacket.RST);
                System.out.printf("SYN: %b\n",clientPacket.SYN);
                System.out.printf("FIN: %b\n",clientPacket.FIN);
                System.out.printf("Data: %s\n", clientPacket.data);
                System.out.println();


                // Server Packet
                Socket serverSocket = new Socket();        //Creates a new Socket
                try {
                    serverSocket.connect(new InetSocketAddress("127.0.0.1", previousPort));
                    ObjectOutputStream serverOutput = new ObjectOutputStream(serverSocket.getOutputStream());
                    serverOutput.writeObject(clientPacket); // Get the packet from the client and gives it to server
                    serverOutput.flush();


                    ObjectInputStream serverInput = new ObjectInputStream(serverSocket.getInputStream());
                    PacketData serverPacket = (PacketData) serverInput.readObject();

                    //Print to Console Information about Packet
                    System.out.printf("Source Agent ID: %d\n", serverPacket.srcPort);
                    System.out.printf("Destination Agent ID: %d\n", serverPacket.destPort);
                    System.out.printf("The Sequence Number: %d\n", serverPacket.seqNumber);
                    System.out.printf("The Acknowledgement Number: %d\n", serverPacket.ackNumber);
                    System.out.printf("DPR: %b\n",serverPacket.DRP);
                    System.out.printf("TER: %b\n",serverPacket.TER);
                    System.out.printf("URG: %b\n",serverPacket.URG);
                    System.out.printf("ACK: %b\n",serverPacket.ACK);
                    System.out.printf("RST: %b\n",serverPacket.RST);
                    System.out.printf("SYN: %b\n",serverPacket.SYN);
                    System.out.printf("FIN: %b\n",serverPacket.FIN);
                    System.out.printf("Data: %s\n", serverPacket.data);
                    System.out.println();

                    ObjectOutputStream clientOutput= new ObjectOutputStream(socket.getOutputStream());
                    clientOutput.writeObject(serverPacket); //Gets the Packet and brings it to client
                    clientOutput.flush();
                    System.out.println();
                }
                catch (Exception e) {
                    System.out.println("Can not establish connection with server");
                    System.exit(0);
                }

            }
        } catch (Exception e) {
            System.out.println("Ended connection with client \n");
        }
    }





}