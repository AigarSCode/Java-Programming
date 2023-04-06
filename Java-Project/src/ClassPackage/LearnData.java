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
    public int[] labelCount = {0,0};
    public int[] countGivenYes = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    public int[] countGivenNo = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    private ArrayList<Double> probGivenYes = new ArrayList<Double>();
    private ArrayList<Double> probGivenNo = new ArrayList<Double>();
    private ArrayList<Double> probLabel = new ArrayList<Double>(2);

    public int noFeatures;
    public int noLabels;
    

    // Constructor
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

    public void calcProbs(){
        // Calculate Initial Label Probs
        for(int i = 0; i < labelCount.length; i++){
            probLabel.add( ( (double)labelCount[i] / dataCount ) );
        }


        // Calculate probabilities given Yes
        while(true){
            for(int j = 0; j < countGivenYes.length; j++){
                probGivenYes.add( (double)( countGivenYes[j] / labelCount[0] ) );
            }
            break;
        }

        // Calculate probabilities given No
        while(true){
            for(int j = 0; j < countGivenNo.length; j++){
                probGivenNo.add( (double)( countGivenNo[j] / labelCount[1] ) );
            }
            break;
        }
    }


    // Getters
    public ArrayList<Double> getProbGivenYes(){
        return probGivenYes;
    }

    public ArrayList<Double> getProbGivenNo(){
        return probGivenNo;
    }

    public ArrayList<Double> getProbLabel(){
        return probLabel;
    }
}