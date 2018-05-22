package missionTCP;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Chan {
    static String outputFile = "Chan-Ann.txt";
    public static void main (String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket();
        Ann.fileWriter(outputFile, "", false);
        try {
            socket.connect(new InetSocketAddress("127.0.0.1", 8084), 20000); //timeout value 20 secs
            System.out.println("Connection established between agent Chan and other agent.");
            System.out.println("\nShortest route: ");
            Routing t = new Routing();
            t.dijkstra( 4, 0);

        }
        catch (Exception e) {
            System.out.println("Can not establish server connection.");
            System.exit(0);
        }
        BufferedReader stdIn = new BufferedReader (new InputStreamReader(System.in));
        String fromClient;
        PacketData packet = new PacketData(0001, 1111);

        while(true) {
            try {
                fromClient = stdIn.readLine();
                packet.data = fromClient;
                packet.agent = "Chan";
                String output = packet.agent + " Sent: "+ fromClient + "\n";
                System.out.printf(output);
                fileWriter(outputFile, output, true);
                // To Router
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(packet);
                outputStream.flush();

                // From router
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                PacketData read = (PacketData) objectInputStream.readObject();
                packet.runPacket(read);

                // Print to screen and log
                String received = read.agent + " received Message: "  + read.data + "\n\n";
                System.out.print(received);
                fileWriter(outputFile, received, true);

                if (read.TER == true) {
                    String message = "Terminated connection with Ann... ";
                    System.out.println(message);
                    fileWriter(outputFile, message, true);
                    objectInputStream.close();
                    outputStream.close();
                    socket.close ();
                    System.exit(0);
                }

            }

            catch (Exception ex) {
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
