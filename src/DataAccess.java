import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.util.JSON;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class DataAccess {

    MongoClient mongoClient;
    MongoDatabase db;

    public DataAccess(){
        // To connect to mongodb server
        mongoClient = new MongoClient("localhost", 27017);
        // Now connect to your databases
        db = mongoClient.getDatabase("OnOffBox");
        System.out.println("Connection to database successfully");
    }

    public void Save(String receviedDataObject, int tagID){
        try {
            MongoCollection BoxData = db.getCollection("BoxData");

            System.out.println("Writing to Database");
            UpdateOptions options = new UpdateOptions().upsert(true);
            DBObject data = (DBObject) JSON.parse(receviedDataObject);
            BasicDBObject filter = new BasicDBObject().append("_id", tagID);
            BoxData.replaceOne(filter, (BasicDBObject) data, options);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public JsonArray GetAllRecords(){
        JsonArray records = new JsonArray();
        try{
            MongoCollection BoxData = db.getCollection("BoxData");
            MongoCursor<Document> docs =  BoxData.find().iterator();
            JsonParser parser = new JsonParser();
            while(docs.hasNext()){
                records.add(parser.parse(docs.next().toJson()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return records;
    }

    public void updateDistances(){
        MongoCollection BoxData = db.getCollection("BoxData");
        MongoCursor<Document> docs =  BoxData.find().iterator();
        JsonParser parser = new JsonParser();
        while(docs.hasNext()){
            Document data = docs.next();
            Document t1 = (Document) data.get("TriangulationAnchor1");
            int x1 = t1.getInteger("X");
            int y1 = t1.getInteger("Y");

            Document t2 = (Document) data.get("TriangulationAnchor2");
            int x2 = t2.getInteger("X");
            int y2 = t2.getInteger("Y");

            Document t3 = (Document) data.get("TriangulationAnchor3");
            int x3 = t3.getInteger("X");
            int y3 = t3.getInteger("Y");

            Document t4 = (Document) data.get("TriangulationAnchor4");
            int x4 = t4.getInteger("X");
            int y4 = t4.getInteger("Y");

            double d2 = distance(x1, y1, x2, y2);
            double d3 = distance(x1, y1, x3, y3);
            double d4 = distance(x1, y1, x4, y4);

            Document dist2 = new Document("d2", d2);
            Document dist3 = new Document("d3", d3);
            Document dist4 = new Document("d4", d4);

            ArrayList<Document> distances = new ArrayList<>();
            distances.add(dist2);
            distances.add(dist3);
            distances.add(dist4);

            data.append("distances", distances);

            UpdateOptions options = new UpdateOptions().upsert(true);
            BasicDBObject filter = new BasicDBObject().append("_id", data.getInteger("TagID"));
            BoxData.replaceOne(filter, data, options);
        }
    }
    double distance(int x1, int y1, int x2, int y2)
    {
        // Calculating distance
        return Math.sqrt(Math.pow(x2 - x1, 2) +
                Math.pow(y2 - y1, 2) * 1.0);
    }
}
