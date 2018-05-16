import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import processing.core.PApplet;
import processing.data.JSONObject;
import processing.data.JSONArray;

public class Verification extends PApplet{
    JSONObject jsonLoaded;
    Bubble[] tags;
    int canvasX = 750;
    int canvasY = 750;
    int anchorSizeX = 90;
    int anchorSizeY = 50;
    int tagSizeX = 80;
    int tagSizeY = 70;

    int Anchor1XCoordinate = 46;
    int Anchor1YCoordinate = 25;
    int Anchor2XCoordinate = canvasX-46;
    int Anchor2YCoordinate = 25;
    int Anchor3XCoordinate = canvasX-46;
    int Anchor3YCoordinate = canvasY-25;
    int Anchor4XCoordinate = 46;
    int Anchor4YCoordinate = canvasY-25;
    int numberOfTags = 26;

    int posX [] = new int[numberOfTags];
    int posY [] = new int[numberOfTags];
    String labels [] = new String[numberOfTags];
    int volumes [] = new int[numberOfTags];
    int frequencies [] = new int[numberOfTags];

    boolean firstRun = true;

    public void settings(){
        size(canvasX, canvasY);
    }

    public void setup(){
        background(237, 235, 234);
    }

    public void draw(){
        background(237, 235, 234);
        loadData();
        RenderTags();
        RenderAnchor();
        delay(5000);
    }

    void loadData() {
        System.out.println("Get recent data");
        DataAccess da = new DataAccess();
        JsonArray onOffBoxData = da.GetAllRecords();

        // The size of the array of Bubble objects is determined by the total XML elements named "bubble"
        tags = new Bubble[onOffBoxData.size()];

        for (JsonElement obj: onOffBoxData) {
            // Get each object in the array
            JsonObject box = obj.getAsJsonObject();
            // Get a position object
            JsonObject position = box.getAsJsonObject("Triangulation");
            // Get x,y from position
            JsonObject Anchor1 = box.getAsJsonObject("TriangulationAnchor1");
            JsonObject Anchor2 = box.getAsJsonObject("TriangulationAnchor2");
            JsonObject Anchor3 = box.getAsJsonObject("TriangulationAnchor3");
            JsonObject Anchor4 = box.getAsJsonObject("TriangulationAnchor4");
            // Get x,y from position
            int x = Anchor1.get("X").getAsInt() + anchorSizeX;
            int y = Anchor1.get("Y").getAsInt() + anchorSizeY;
            int z = Anchor1.get("Z").getAsInt();

            int id = box.get("TagID").getAsInt();
            String label = "Tag " + id;
            labels[id] = label;

            posX[id] = x;
            posY[id] = y;

            // set volume
            volumes[id] = box.get("Volume").getAsInt();
            frequencies[id] = box.get("Frequency").getAsInt();
        }
    }

    public void RenderTags(){
        System.out.println("Rendering the tags");
        for (int i = 1; i < labels.length; i++) {
            float distance2 = dist(posX[i], posY[i], Anchor2XCoordinate, Anchor2XCoordinate);
            float distance3 = dist(posX[i], posY[i], Anchor3XCoordinate, Anchor3XCoordinate);
            float distance4 = dist(posX[i], posY[i], Anchor4XCoordinate, Anchor4XCoordinate);

            line(posX[i], posY[i], Anchor2XCoordinate, Anchor2YCoordinate);

            // Render the tags
            rectMode(CENTER);
            fill(255, 178, 79);
            rect(posX[i], posY[i], tagSizeX, tagSizeY);
            fill(50);

            textAlign(CENTER, BOTTOM);
            textSize(14);
            text(labels[i], posX[i], posY[i]-10);

            textAlign(LEFT, BOTTOM);
            textSize(11);
            text("Pos: " + posX[i] + "," + posY[i], posX[i]-35, posY[i]+5);

            textAlign(LEFT, BOTTOM);
            textSize(11);
            text("2: " + distance2, posX[i]-35, posY[i]+15);

            textAlign(LEFT, BOTTOM);
            textSize(11);
            text("3: " + distance3, posX[i]-35, posY[i]+25);

            textAlign(LEFT, BOTTOM);
            textSize(11);
            text("4: " + distance4, posX[i]-35, posY[i]+35);
        }
    }

    public void RenderAnchor(){
        rectMode(CENTER);
        fill(244, 95, 66);
        rect(Anchor1XCoordinate, Anchor1YCoordinate, anchorSizeX, anchorSizeY);
        fill(50);
        textAlign(CENTER, CENTER);
        text("Anchor 1", Anchor1XCoordinate, Anchor1YCoordinate);

        rectMode(CENTER);
        fill(244, 95, 66);
        rect(Anchor2XCoordinate, Anchor2YCoordinate, anchorSizeX, anchorSizeY);
        fill(50);
        textAlign(CENTER, CENTER);
        text("Anchor 2", Anchor2XCoordinate, Anchor2YCoordinate);

        rectMode(CENTER);
        fill(244, 95, 66);
        rect(Anchor3XCoordinate, Anchor3YCoordinate, anchorSizeX, anchorSizeY);
        fill(50);
        textAlign(CENTER, CENTER);
        text("Anchor 3", Anchor3XCoordinate, Anchor3YCoordinate);

        rectMode(CENTER);
        fill(244, 95, 66);
        rect(Anchor4XCoordinate, Anchor4YCoordinate, anchorSizeX, anchorSizeY);
        fill(50);
        textAlign(CENTER, CENTER);
        text("Anchor 4", Anchor4XCoordinate, Anchor4YCoordinate);
    }

    public static void main(String[] args) {
        DataAccess da = new DataAccess();
        da.updateDistances();
        PApplet.main("Verification");
    }
}
