package ClassPackage;

import java.util.ArrayList;

/*
 * Analyse Input Class, used to Calculate the probability of
 * a person becomming an Entrepreneur from the User Input through the GUI.
 * Uses the LearnData class before it so all the probabilities are worked out
 * 
 * Author: Aigars Semjonovs
 * Date: April 2023
 */


public class AnalyseInput {
    private LearnData learnData;
    public boolean answer;


    // Constructor
    // Make an instance of this object that has access to the LearnData Instance
    public AnalyseInput(LearnData ld){
        this.learnData = ld;
        learnData.fm.writeLog("Instantiated AnalyseInput");
    }


    // Take the user input and calculate the probability and return prob
    public double calculateProb(String[] inputStr){
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
                if( inputStr[i].equals(learnData.typesArray[i]) ){
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
                if( inputStr[i].equals(learnData.typesArray[i]) ){
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

        // Return the largest Probability as the choice (In Percent)
        if(yesProb > noProb){
            answer = true;
            yesProb = ( (yesProb) / (yesProb + noProb) );
            yesProb = yesProb * 100.0;

            learnData.fm.writeLog("Calculated Probabilities from User input");

            return yesProb;
        }
        else{
            answer = false;
            noProb = ( (noProb) / (noProb + yesProb) );
            noProb = noProb * 100.0;

            learnData.fm.writeLog("Calculated Probabilities from User input");

            return noProb;  
        }
    }// End calculateProb
    
}// End AnalyseInput
