import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class TagSimulation {
    int tagID = 0;
    static final int TagIDMin = 1;
    static final int TagIDMax = 25;
    static final int VolumeMin = 0;
    static final int VolumeMax = 10;
    static final long FrequncyMin = 0;
    static final long FrequencyMax = 100;
    static final long TriangulationMin = 0; // distance in meters
    static final long TriangulationMax = 600; // distance in meters

    DatagramSocket datagramSocket;
    String servername ="localhost";

    public TagSimulation(int portNumber){
        try {
            datagramSocket = new DatagramSocket(portNumber);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String synthesizeData(){
        int volume = ThreadLocalRandom.current().nextInt(VolumeMin, VolumeMax+1);
        long frequency = ThreadLocalRandom.current().nextLong(FrequncyMin, FrequencyMax+1);
        boolean onOffSwitch = Math.round( Math.random()) == 0 ? false : true;

        BoxData bd = new BoxData(tagID, onOffSwitch, volume, frequency,
                        new Triangulation(anchorData()), new Triangulation(anchorData()),
                        new Triangulation(anchorData()), new Triangulation(anchorData()));

        Gson object = new Gson();
        String data = object.toJson(bd);
        Gson prettyPrinter = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(prettyPrinter.toJson(bd));
        return data;

    }

    private long[] anchorData(){
        long[] traig = new long[3];
        traig[0] = ThreadLocalRandom.current().nextLong(TriangulationMin, TriangulationMax+1);
        traig[1] = ThreadLocalRandom.current().nextLong(TriangulationMin, TriangulationMax+1);
        traig[2] = ThreadLocalRandom.current().nextLong(TriangulationMin, TriangulationMax+1);
        return traig;
    }
    /**
     * Send the simulated string data to server which will act like a box sending data
     * @param
     */
    private void sendData(){
        try {
            while(true) {
                byte[] buffer = synthesizeData().getBytes();
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length,
                        InetAddress.getByName(servername), 40000);
                datagramSocket.send(dp);
                System.out.println("Data sent to " + servername);
                Thread.sleep(3000);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // get the tag from command line
        int tagNumber = Integer.parseInt(args[0]);
        TagSimulation tag = new TagSimulation(tagNumber+1025);
        tag.tagID = tagNumber;
        System.out.format("Starting simulation for tag %d \n", tag.tagID);
        Thread sending = new Thread(()->{
            tag.sendData();
        });
        sending.start();
    }
}
