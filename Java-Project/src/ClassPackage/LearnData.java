package ClassPackage;

import java.util.ArrayList;


public class LearnData {
    protected FileManager fm;
    protected String activeLine;
    protected int dataCount;

    public ArrayList<String> featureArray;
    public ArrayList<String> labelArray;
    public ArrayList<String> dataTypeArray;
    public ArrayList<Integer> typeCountArray;
    public int noFeatures;
    public int noLabels;
    

    // Constructor
    // Example of method overloading
    public LearnData(String fileName){
        fm = new FileManager(fileName);

        featureArray = new ArrayList<String>();
        labelArray = new ArrayList<String>();

        // Read the first bit of data
        // Features, Labels, Counts
        makeFeatureandLabelArrays();
        // Reads only one line and make the array with the data types
        makeDataTypeArrayList(fm.readData());

    }

    //
    // Check if this is even needed
    //
    public LearnData(String fileName, int featureNo, int labelNo){
        fm = new FileManager(fileName);

        featureArray = new ArrayList<String>(featureNo);
        labelArray = new ArrayList<String>(labelNo);

        // Read the first bit of data
        // Features, Labels, Counts
        makeFeatureandLabelArrays();
        // Reads only one line and make the array with the data types
        makeDataTypeArrayList(fm.readData());
    }



    // Number of features and labels returned
    // All the names are put into the arraylists
    public void makeFeatureandLabelArrays(){
        noFeatures = fm.readFeatures(featureArray);
        noLabels = fm.readLabels(labelArray);

        dataCount = fm.countLines();
    }
    

    // Take the first line of data an use that as the types to expect
    // Only one from each needed, If its 'yes' then 'no' has to be the other
    public void makeDataTypeArrayList(String types){
        int total = noFeatures + noLabels;
        types = types.toLowerCase();

        String[] s = types.split("\\s*,\\s*");

        for(int i = 0; i < total; i++){
            dataTypeArray.add(s[i]);
        }
    }


    // Reads in all the types such as Yes/No and Urban/Rural
    // This uses an array List within an array List to store 
    // all types within an arraylist that is indexed the same
    // as featureArray and labelArray
    public void readAllDataTypes(){


    }
    
}


// THIS GETS ALL THE DATA LINES BACK
    // String s;
    // int i = 0;
    // int count = fm.countLines();
    // count = count - 3;
    // while(i != count){
    //     s = fm.readData();
    //     System.out.println(s);
    //     i++;
    // }
// /////////////////////////////////////