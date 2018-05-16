public class BoxData {
    int TagID;
    boolean OnOffSwitch;
    int Volume;
    long Frequency;
    Triangulation TriangulationAnchor1;
    Triangulation TriangulationAnchor2;
    Triangulation TriangulationAnchor3;
    Triangulation TriangulationAnchor4;

    public BoxData(int tagID , boolean onOffSwitch, int volume, long frequency, Triangulation anchor1, Triangulation anchor2,Triangulation anchor3,Triangulation anchor4){
        this.TagID = tagID;
        this.OnOffSwitch = onOffSwitch;
        this.Volume = volume;
        this.Frequency = frequency;
        this.TriangulationAnchor1 = anchor1;
        this.TriangulationAnchor2 = anchor2;
        this.TriangulationAnchor3 = anchor3;
        this.TriangulationAnchor4 = anchor4;
    }
}
