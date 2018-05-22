
package missionTCP;
//Name: Shuvrima Alam, UTA ID: 1001085726
//Name: Syed Zaim Zanaruddin, UTA ID:1001106858
//Date: 05/08/2018

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Jan {
    static String outputFile = "Jan-Ann.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket();
        Ann.fileWriter(outputFile, "", false);
        try {
            socket.connect(new InetSocketAddress("127.0.0.1", 8085), 20000);

            // Runs Dijkstra algorithm to find the shortest path
            System.out.println("\nShortest path from Jan to Ann");
            Routing t = new Routing();
            t.dijkstra(5, 0);


        } catch (Exception e) {
            System.out.println("Error in Creating Connection");
            System.exit(0);
        }


        //Create an input buffer to grab from console
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String input;
        PacketData packet = new PacketData(1000, 1111);

        while (true) {
            try {

                //Grabs input to compare
                input = stdIn.readLine();



                //Set the URG pointer when message is received
                if (input.contains("CONGRATULATIONS WE FRIED DRY GREEN LEAVES")) {
                    packet.URG = true;
                }

                //Sets the packet back to Jan
                packet.data = input;
                packet.agent = "Jan";
                String output = packet.agent + " Message: " + input + "\n";
                System.out.printf(output);
                fileWriter(outputFile, output, true);


                // To Router
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(packet);
                outputStream.flush();



                // From router
                ObjectInputStream objectStream = new ObjectInputStream(socket.getInputStream());
                PacketData read = (PacketData) objectStream.readObject();
                packet.runPacket(read);

                // Print to screen and log
                String received = read.agent + " Message Received: " + read.data + "\n\n";
                System.out.printf(received);
                fileWriter(outputFile, received, true);



                if(read.data.equalsIgnoreCase("Congrats Meet me at this location: 32.76â€� N, -97.07â€� W")){
                    packet.FIN =true;
                    String message = "Jan Connection terminated";
                    System.out.println(message);
                    fileWriter(outputFile, message, true);
                    outputStream.close();
                    socket.close();
                    System.exit(0);
                }



                // When ann sends pack execute, we let HQ to complete the mission
                if (read.data.equalsIgnoreCase("Execute")) {
                    Socket HeadQsocket = new Socket();
                    try {
                        HeadQsocket.connect(new InetSocketAddress("127.0.0.1", 9999));
                        System.out.println("Connection established.");
                    } catch (Exception e) {
                        System.out.println("Can not establish connection");
                        System.exit(0);
                    }

                    PacketData HQpacket = new PacketData(2000, 9999);
                    ObjectOutputStream out = new ObjectOutputStream(HeadQsocket.getOutputStream());
                    String message = "HQ the enemy is located 32Â° 43â€™ 22.77â€� N,97Â° 9â€™ 7.53â€� W";
                    HQpacket.data = message;
                    System.out.println("Jan to HQ sent: " + HQpacket.data);
                    fileWriter(outputFile, "Jan to HQ sent: " + message + "\n", true);
                    HQpacket.URG = true;
                    HQpacket.authCode = read.authCode;
                    out.writeObject(HQpacket);
                    outputStream.flush();

                    ObjectInputStream in = new ObjectInputStream(HeadQsocket.getInputStream());

                    PacketData HeadQread = (PacketData) in.readObject();
                    System.out.printf("Message: %s\n", HeadQread.data);
                    fileWriter(outputFile, "Message from HQ: " + HeadQread.data + "\n\n", true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }



    public static void fileWriter(String outputFile, String content, Boolean flag) throws IOException {
        FileWriter file = new FileWriter(outputFile, flag);
        file.write(content);
        file.close();
    }

}