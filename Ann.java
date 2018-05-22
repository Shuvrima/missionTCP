package missionTCP;
//Name: Shuvrima Alam, UTA ID: 1001085726
//Name: Syed Zaim Zanaruddin, UTA ID:1001106858
//Date: 05/08/2018
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Ann {
    static String chanOutputFile = "Ann-Chan.txt";
    static String janOutputFile = "Ann-Jan.txt";
    static int chan_timer = 6;

    public static void main (String[] args) throws IOException, ClassNotFoundException {

        ServerSocket annSocket = new ServerSocket (1111);
        fileWriter(chanOutputFile, "", false);
        fileWriter(janOutputFile, "", false);

        while(true){
            Socket socket = annSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Receive from clients
            PacketData packet = (PacketData) objectInputStream.readObject();


            String Received = "Message from " + packet.agent + ":" + packet.data + "\n";
            System.out.printf(Received);
            String outputFile = "";






            // RST FLAG termination
            if ((packet.agent).equalsIgnoreCase("Chan")) {
                chan_timer--;
                if (chan_timer <= 0){packet.setRST(true);}
                outputFile = chanOutputFile;
            }

            else if (packet.data.contains("FIN") || packet.data.equalsIgnoreCase("goodbye")) {
                String message = "Ann Connection terminated using FIN";
                System.out.println(message);
                fileWriter(janOutputFile, message, true);
                objectInputStream.close();
                socket.close();
                System.exit(0);
            }
            else {
                outputFile = janOutputFile;
            }

            fileWriter(outputFile, Received, true);

            // Sending to Router A
            PacketData send = new PacketData(1111, 0001);
            // Check for RST flag which indicates to close connection with client
            if (packet.RST == true && chan_timer<= 0) {
                System.out.println(send.URG=true);
                String terminateChan = "Terminating Connection with " + packet.agent + "...\n";
                System.out.println(terminateChan);
                fileWriter(outputFile, terminateChan, true);
                send.data = "TER";
                send.agent = "Ann";
                send.TER = true;
                send.RST = true;
                OpenSocket(socket, send);
                continue;
            }
            else {
                send.runPacket(packet);
                if (packet.data.contains("32Â° 43â€™ 22.77â€� N,97Â° 9â€™ 7.53â€� W")){
                    // responds with urgent
                    send.URG = true;
                    send.authCode = "PEPPER THE PEPPER";
                }
                if(packet.data.contains("CONGRATULATIONS WE FRIED DRY GREEN LEAVES")){
                    send.URG = true;
                    send.FIN = true;
                    send.data = "Congrats Meet me at this location: 32.76â€� N, -97.07â€� W";

                    //Send data
                    send.agent = "Ann";
                    String Sent = send.agent + " Message: " + send.data +"\n\n";
                    System.out.print(Sent);
                    fileWriter(outputFile, Sent, true);
                    OpenSocket(socket, send);

                    //End the Connection
                    
                    String message = "Ann Connection terminated";
                    System.out.println(message);
                    fileWriter(janOutputFile, message, true);
                    objectInputStream.close();
                    socket.close();
                    System.exit(0);
                }
                else {
                    Scanner scan = new Scanner(System.in);
                    send.data = scan.nextLine();
                }
                send.agent = "Ann";
                String Sent = send.agent + " Message: " + send.data +"\n\n";
                System.out.print(Sent);
                fileWriter(outputFile, Sent, true);
                OpenSocket(socket, send);
            }
        }
    }

    // Logs the communication between Ann and the clients (jan/chan)
    public static void fileWriter(String outputFile, String content, Boolean flag) throws IOException {
        FileWriter file = new FileWriter(outputFile, flag);
        file.write(content);
//        file.write(System.lineSeparator());
        file.close();
    }


    public static void OpenSocket(Socket socketConnection, PacketData send)  {

        Socket socket = new Socket();

        try {



            socket.connect(new InetSocketAddress("127.0.0.1", 8080), 20000); // 20 seconds

            ObjectOutputStream outputStream = new ObjectOutputStream(socketConnection.getOutputStream());
            outputStream.writeObject(send);
            outputStream.flush();
            socket.close();



        }catch (Exception  e ) {

            System.out.println("Connection could not be established.");
            System.exit(0);


        }

    }
}

