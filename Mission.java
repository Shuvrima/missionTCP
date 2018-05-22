package missionTCP;

//Name: Shuvrima Alam, UTA ID: 1001085726
//Name: Syed Zaim Zanaruddin, UTA ID:1001106858
//Date: 05/08/2018
import java.io.IOException;

public class Mission {

    public static void main (String[] args) throws IOException {
        System.out.println("Routers: ");

        // Router A
        System.out.printf("Router A has been initialized, Port: %d\n", 8080);
        Router A = new Router("A", 8080, 1111);
        Thread threadA= new Thread(A);
        threadA.start();

        // Router B
        System.out.printf("Router B has been initialized, Port: %d\n", 8081);
        Router B = new Router("B", 8081, 8080);
        Thread threadB = new Thread(B);
        threadB.start();

        // Router C
        System.out.printf("Router C has been initialized, Port: %d\n", 8082);
        Router C = new Router("C", 8082, 8080);
        Thread threadC = new Thread(C);
        threadC.start();

        // Router D
        System.out.printf("Router D has been initialized, Port: %d\n", 8083);
        Router D = new Router("D", 8083, 8082);
        Thread threadD = new Thread(D);
        threadD.start();

        // Router E
        System.out.printf("Router E has been initialized, Port: %d\n", 8084);
        Router E = new Router("E", 8084, 8080);
        Thread threadE = new Thread(E);
        threadE.start();

        // Router F
        System.out.printf("Router F has been initialized, Port: %d\n", 8085);
        Router F = new Router("F", 8085, 8086);
        Thread threadF = new Thread(F);
        threadF.start();


        // Router L
        System.out.printf("Router L has been initialized, Port: %d\n", 8086);
        Router L = new Router("L", 8086, 8081);
        Thread threadL = new Thread(L);
        threadL.start();


        // Router G
        System.out.printf("Router G has been initialized, Port: %d\n", 8087);
        Router G = new Router("G", 8087, 8084);
        Thread threadG = new Thread(G);
        threadG.start();

        System.out.println();

    }
}
