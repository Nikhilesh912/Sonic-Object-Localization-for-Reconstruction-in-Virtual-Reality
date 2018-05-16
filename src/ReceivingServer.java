import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mongodb.util.JSON;
import jdk.nashorn.internal.runtime.ECMAErrors;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceivingServer {
    DatagramSocket datagramSocket;
    DataAccess dataAccess;

    public ReceivingServer(){
        try {
            datagramSocket = new DatagramSocket(40000);
            dataAccess = new DataAccess();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ReceiveData(){
        try {
            while(true) {
                byte[] buffer = new byte[100000];
                // receiving data from other processes
                DatagramPacket dpRec = new DatagramPacket(buffer, buffer.length);
                System.out.println("Ready to receive data from client");
                datagramSocket.receive(dpRec);
                String receivedData = new String(dpRec.getData(), 0, dpRec.getLength());
                System.out.println(receivedData);
                Gson gson = new Gson();
                JsonElement element = gson.fromJson(receivedData, JsonElement.class);
                dataAccess.Save(receivedData, element.getAsJsonObject().get("TagID").getAsInt());
                Thread.sleep(2000);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ReceivingServer rs = new ReceivingServer();

        Thread receive = new Thread(()->{
                rs.ReceiveData();
        });

        receive.start();
    }
}
