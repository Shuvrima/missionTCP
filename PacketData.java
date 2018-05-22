//Name: Shuvrima Alam, UTA ID: 1001085726
//Name: Syed Zaim Zanaruddin, UTA ID:1001106858
//Date: 05/08/2018
package missionTCP;

import java.io.Serializable;
import java.util.Random;


public class PacketData implements Serializable {

    //Packet Data Initialization
    public int srcPort;
    public int destPort;
    public String agent;
    public String data;
    public String authCode;
    public int seqNumber;
    public int ackNumber;
    public boolean DRP = false;
    public boolean TER = false;
    public boolean URG = false;
    public boolean ACK = false;
    public boolean RST = false;
    public boolean FIN = false;
    public boolean SYN;

    public PacketData(int src, int des) {
        this.srcPort = src;
        this.destPort = des;
        this.seqNumber = setSequenceNumber();
        this.SYN = true;
    }


    public boolean runPacket(PacketData packet) {
        this.ACK = true;
        this.ackNumber= packet.seqNumber + 1;
        if (packet.ACK == true)
            this.seqNumber = packet.ackNumber;
        return this.ACK;
    }

    public int setSequenceNumber() {
        Random rand = new Random();
        return rand.nextInt(1000) + 1;
    }


    // according to instructions rst flag sets the ter
    public boolean setRST (boolean setter) {
        this.TER = setter;
        this.RST = setter;
        return this.RST;
    }

}
