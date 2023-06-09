package ClassPackage;

import java.util.ArrayList;

/*
 * LearnData class, this class uses FileManager Class to read:
 * The Features, Labels and all the data points from a CSV file
 * 
 * Author: Aigars Semjonovs
 * Date: April 2023
 */


public class LearnData {
    protected FileManager fm;
    protected String activeLine;
    protected int dataCount;

    // Public so they can be accessed
    public ArrayList<String> featureArray;
    public ArrayList<String> labelArray;
    public ArrayList<String> dataTypeArray;

    
    // Here is when I give up finding a way to dynamically get the variations in the types
    // This is to what the method countOccurances compares to, these values are first in the count arrays
    public final String[] typesArray = {"male", "yes", "yes", "urban", "yes", "yes"};

    // They start at one because if they started at 0 the fianl probability will be 0
    //                      {yes, no}
    public int[] labelCount = {0,0};
    // Idea here is to place 2 states of each feature in one array
    // {male, female,| yes, no,| yes, no,| urban, rural,| yes, no}
    public int[] countGivenYes = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    public int[] countGivenNo = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    // These hold the probabilities of each feature the same way as above
    private ArrayList<Double> probGivenYes = new ArrayList<Double>();
    private ArrayList<Double> probGivenNo = new ArrayList<Double>();
    private ArrayList<Double> probLabel = new ArrayList<Double>(2);

    // Used for dyanmic reading
    public int noFeatures;
    public int noLabels;
    public boolean hasReset = false;
    

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

        // Calculate the probabilities for each feature against each Label
        calcProbs();

        fm.writeLog("Instantiated LearnData");

    }// End LearnData


    // Attempted Dynamic reading
    // Number of features and labels returned
    // All the names are put into the arraylists
    public void makeFeatureandLabelArrays(){
        noFeatures = fm.readFeatures(featureArray);
        noLabels = fm.readLabels(labelArray);

        dataCount = ( fm.countLines() ) - 3;
    }// End makeFeatureandLabelArrays
    

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
    }// End makeDataTypeArrayList



    // Read all the data from the csv file and count occurances separated by the label
    // e.g. Counts for features | Yes and features | No
    public void countOccurances(){
        int total = 0;
        int j;
        String s;
    
        // Read while dataCount is not reached (total data lines)
        while(total != dataCount){
            j = 0;

            s = fm.readData();
            s = s.toLowerCase();

            String[] splitStr = s.split("\\s*,\\s*");

            // Calculate the counts
            // First check the label then add count to correct array
            // Checking the last item against the last label                                               Urban   Rural
            // Each feature has two states e.g. Urban/Rural. The counts are put into an array in order e.g. {23  ,  45} for each feature
            if( splitStr[ (splitStr.length - 1) ].equals(typesArray[ (typesArray.length - 1) ]) ){
                labelCount[0]++;

                for(int i = 0; i < (splitStr.length - 1); i++){

                    // Increment count for one of the 2 states of the featrue
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
                
                    // Increment count to get one of the 2 states of the featrue
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
        
        fm.writeLog("Completed all CSV reads");
    }// End countOccurances



    // This will calculate all probabilities for all features given the labels
    // e.g. P(Yes | Urban) , P(No | Urban)
    public void calcProbs(){
        // Calculate Initial Label Probs
        for(int i = 0; i < labelCount.length; i++){
            probLabel.add( ( (double)labelCount[i] / dataCount ) );
        }


        // Calculate probabilities given Yes
        while(true){
            for(int j = 0; j < countGivenYes.length; j++){
                probGivenYes.add( (double)( countGivenYes[j] / (double)labelCount[0] ) );
            }
            break;
        }


        // Calculate probabilities given No
        while(true){
            for(int j = 0; j < countGivenNo.length; j++){
                probGivenNo.add( (double)( countGivenNo[j] / (double)labelCount[1] ) );
            }
            break;
        }

        fm.writeLog("Calculated All Probabilities");
    }// End calcProbs


    // Clear all the Learned Data
    public void clearLearnedData(){
        for(int i = 0; i < labelCount.length; i++){
            labelCount[i] = 0;
        }
        for(int i = 0; i < countGivenYes.length; i++){
            countGivenYes[i] = 1;
            countGivenNo[i] = 1;
        }

        probGivenYes = new ArrayList<Double>();
        probGivenNo = new ArrayList<Double>();
        probLabel = new ArrayList<Double>(2);

        noFeatures = noLabels = 0;

        featureArray = new ArrayList<String>();
        labelArray = new ArrayList<String>();
        dataTypeArray = new ArrayList<String>();

        fm.setOpen(false);
        fm.setreadTypes(false);
        fm.setline(0);
        fm.writeLog("Cleared All Learned Data");
    }// End clearLearnedData



    // Getters
    public ArrayList<Double> getProbGivenYes(){
        fm.writeLog("Returned probGivenYes");
        return probGivenYes;
    }

    public ArrayList<Double> getProbGivenNo(){
        fm.writeLog("Returned probGivenNo");
        return probGivenNo;
    }

    public ArrayList<Double> getProbLabel(){
        fm.writeLog("Returned probLabel");
        return probLabel;
    }
}// End LearnData