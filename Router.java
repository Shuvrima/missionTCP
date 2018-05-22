
package missionTCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Router implements Runnable {

    //Initialize various variables
    String routerName;
    int portNumber;
    int previousPort;

    ServerSocket serverSocket;

    public Router(String routerName, int portNumber, int previousPort) throws IOException {
        this.routerName = routerName;
        this.portNumber = portNumber;
        this.previousPort = previousPort;
        this.serverSocket = new ServerSocket(portNumber);
    }

    public void run() {
        try {
            while(true){
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, previousPort);
                Thread thread = new Thread (connection);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("Could not Initialize a new router");
        }
    }



}
