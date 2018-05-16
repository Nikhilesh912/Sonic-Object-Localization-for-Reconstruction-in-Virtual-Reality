import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.util.JSON;

import java.io.FileWriter;

public class FileAccess {

    public void writeToFile(JsonArray data){
        if (data.size() > 0) {
            try (FileWriter writer = new FileWriter("data.json")) {
                System.out.println(data);
                Gson gson = new GsonBuilder().create();
                JsonObject dataToWrite = new JsonObject();
                dataToWrite.add("onOffBoxData", data);
                gson.toJson(dataToWrite, writer);
                System.out.println("Writing completed");
                writer.close();
                Thread.sleep(4000);
            } catch (Exception e) {
                System.out.println("Exception in writing data");
                e.printStackTrace();
            }
        } else {
            System.out.println("No data to write");
        }
    }

    public static void main(String[] args) {
        DataAccess dataAccess = new DataAccess();
        FileAccess fileAccess = new FileAccess();
//        fileAccess.writeToFile(dataAccess.GetAllRecords());
        Thread sending = new Thread(()->{
            while (true)
                fileAccess.writeToFile(dataAccess.GetAllRecords());
        });
        sending.start();
    }
}
