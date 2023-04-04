package ClassPackage;

import java.util.ArrayList;

public class LearnData {
    FileManager fm = new FileManager("MLData.csv", null);
    protected String activeLine;

    ArrayList<String> featureArray = new ArrayList<String>();
    ArrayList<String> labelArray = new ArrayList<String>();

    public void makeLabelandFeatureArrays(){
        // Number of features and labels returned
        // All the names are put into the arraylists
        int noFeatures = fm.readFeatures(featureArray);
        int noLabels = fm.readLabels(labelArray);
    }
    
}
