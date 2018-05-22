//Name: Shuvrima Alam, UTA ID: 1001085726
//Name: Syed Zaim Zanaruddin, UTA ID:1001106858
//Date: 05/08/2018
package missionTCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HQ {
    public static void main (String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket (9999);

        while(true){
            Socket socketConnection = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socketConnection.getInputStream());

            PacketData packet = (PacketData) objectInputStream.readObject();
            System.out.printf("Message: %s\n", packet.data);

            PacketData HQ = new PacketData(2000, 9999);
            ObjectOutputStream out = new ObjectOutputStream(socketConnection.getOutputStream());
            HQ.data = "Mission Successful.";
            System.out.println("Head Quarters to Jan sent: " + HQ.data);
            out.writeObject(HQ);
            out.flush();
        }
    }
}
