JSONObject jsonLoaded;
Bubble[] tags;

void setup() {
  size(400, 400);
  loadData();
}

void draw(){
  background(255);
  for(Bubble b: tags){
    b.display();
  }
  textAlign(LEFT);
  fill(0);
}

void loadData() {
  // Load JSON file
  // Temporary full path until path problem resolved.
  jsonLoaded = loadJSONObject("../data.json");

  JSONArray onOffBoxData = jsonLoaded.getJSONArray("onOffBoxData");
  print(onOffBoxData);
  // The size of the array of Bubble objects is determined by the total XML elements named "bubble"
  tags = new Bubble[onOffBoxData.size()]; 

  for (int i = 0; i < onOffBoxData.size(); i++) {
    // Get each object in the array
    JSONObject box = onOffBoxData.getJSONObject(i); 
    // Get a position object
    JSONObject position = box.getJSONObject("Triangulation");
    // Get x,y from position
    int x = position.getInt("Anchor1");
    int y = position.getInt("Anchor2");
    int z = position.getInt("Anchor3");
    
    // Get diamter and label
    float diameter = box.getFloat("Frequency");
    String label = "Tag " + str(box.getInt("TagID"));

    // Put object in array
    rectMode(CENTER);
    fill(255, 178, 79);
    rect(x, y, 40, 40);
    fill(50);
    text(label, x, y);
    textAlign(CENTER, CENTER);
    //tags[i] = new Bubble(x, y, diameter, label);
  }
}