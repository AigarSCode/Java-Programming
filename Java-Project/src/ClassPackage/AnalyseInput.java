package ClassPackage;

import java.util.ArrayList;

public class AnalyseInput {
    private String[] inputStr;
    private LearnData learnData;
    public boolean answer;


    // Make an instance of this object that has access to the LearnData Instance
    public AnalyseInput(LearnData ld, String[] input){
        this.learnData = ld;
        this.inputStr = input;
    }


    // Take the user input and calculate the probability and return prob
    public double calculateProb(){
        double yesProb = 0;
        double noProb = 0;
        double tempProb = 1;
        int j = 0;

        ArrayList<Double> probYes = learnData.getProbGivenYes();
        ArrayList<Double> probNo = learnData.getProbGivenNo();

        // Get probabilities for the user input
        while(true){
            j = 0;
            tempProb = 1;

            // Go through the probability array and pick out ones the user chose
            for(int i = 0; i < inputStr.length; i++){
                if( inputStr[i].equals(learnData.typesArray[j]) ){
                    tempProb =  ( tempProb * probYes.get(j) );
                    j += 2;
                }
                else{
                    tempProb =  ( tempProb * probYes.get(j + 1) );

                    j += 2;
                }
            }

            // Get the probabilities of the first label and multiply result
            yesProb = (tempProb * ( learnData.getProbLabel().get(0) ) );
            break;
        }

        // Get probabilities for the user input
        while(true){
            j = 0;
            tempProb = 1;

            // Go through the probability array and pick out ones the user chose
            for(int i = 0; i < inputStr.length; i++){
                if( inputStr[i].equals(learnData.typesArray[j]) ){
                    tempProb =  ( tempProb * probNo.get(j) );
                    j += 2;
                }
                else{
                    tempProb =  ( tempProb * probNo.get(j + 1) );

                    j += 2;
                }
            }

            // Get the probabilities of the first label and multiply result
            noProb = (tempProb * ( learnData.getProbLabel().get(1) ) );
            break;
        }

        // Return the largest Probability as the choice
        if(yesProb > noProb){
            answer = true;
            return yesProb;
        }
        else{
            answer = false;
            return noProb;  
        }
        
    }
    
}
