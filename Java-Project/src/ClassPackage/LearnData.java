package ClassPackage;

import java.util.ArrayList;


public class LearnData {
    protected FileManager fm;
    protected String activeLine;
    protected int dataCount;

    public ArrayList<String> featureArray;
    public ArrayList<String> labelArray;
    public ArrayList<String> dataTypeArray;

    // Here is when I give up finding a way to dynamically get the variations in the types
    // This is to what the method countOccurances compares to, these values are first in the count arrays
    public static final String[] typesArray = {"male", "yes", "yes", "urban", "yes", "yes"};

    // They start at one because if they started at 0 the fianl probability will be 0
    public int[] labelCount = {1,1};
    public int[] countGivenYes = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    public int[] countGivenNo = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

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

        // Count the occurances of each value given yes or no
        countOccurances();

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

        dataCount = ( fm.countLines() ) - 3;
    }
    

    // Take the first line of data an use that as the types to expect
    // Only one from each needed, If its 'yes' then 'no' has to be the other
    public void makeDataTypeArrayList(String types){
        dataTypeArray = new ArrayList<String>();

        int total = noFeatures + noLabels;
        types = types.toLowerCase();

        String[] s = types.split("\\s*,\\s*");

        for(int i = 0; i < total; i++){
            dataTypeArray.add( s[i] );
        }
    }


    public void countOccurances(){
        int total = 0;
        int j;
        String s;
    
        while(total != dataCount){
            j = 0;

            s = fm.readData();
            s = s.toLowerCase();

            String[] splitStr = s.split("\\s*,\\s*");

            // Calculate the counts
            // First check the label then add count to correct array
            // Checking the last item against the last label
            if( splitStr[ (splitStr.length - 1) ].equals(typesArray[ (typesArray.length - 1) ]) ){
                labelCount[0]++;

                for(int i = 0; i < (splitStr.length - 1); i++){

                    if( splitStr[i].equals( typesArray[i] ) ){
                        countGivenYes[j]++;
                        j += 2;
                    }
                    else{
                        countGivenYes[j+1]++;
                        j += 2;
                    }
                }
                total++;
            }
            else{
                labelCount[1]++;

                for(int i = 0; i < (splitStr.length - 1); i++){
                
                    if( splitStr[i].equals( typesArray[i] ) ){
                        countGivenNo[j]++;
                        j += 2;
                    }
                    else{
                        countGivenNo[j+1]++;
                        j += 2;
                    }
                }
                total++;
            }
        }


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