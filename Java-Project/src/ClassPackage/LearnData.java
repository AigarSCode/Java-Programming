package ClassPackage;

import java.util.ArrayList;

public class LearnData {
    FileManager fm = new FileManager("C:\\Users\\Aigar\\Desktop\\MLdata.csv");
    protected String activeLine;

    ArrayList<String> featureArray = new ArrayList<String>();
    ArrayList<String> labelArray = new ArrayList<String>();

    public void makeLabelandFeatureArrays(){
        // Number of features and labels returned
        // All the names are put into the arraylists
        int noFeatures = fm.readFeatures(featureArray);
        int noLabels = fm.readLabels(labelArray);

        System.out.println(fm.countLines());

        System.out.println("AFETR FIRST COUNT / BEFORE FEATURES");

        System.out.println(noFeatures);
        for(String i: featureArray){
            System.out.println(i);
        }

        System.out.println("\n\n FEATURE ARRAY AND NUM ABOVE, LABELS AND NUM BELOW\n\n");

        System.out.println(noLabels);
        for(String i: labelArray){
            System.out.println(i);
        }



    }
    
}
