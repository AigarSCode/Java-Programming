package ClassPackage;

import java.util.ArrayList;

public class AnalyseInput {
    private String inputStr;
    private LearnData learnData;


    // Make an instance of this object that has access to the LearnData Instance
    public AnalyseInput(LearnData ld, String input){
        this.inputStr = input;
        this.learnData = ld;
    }


    // Take the user input and calculate the probability and return prob
    public double calculateProb(){
        double finalProb = 0;
        ArrayList<Double> probYes = learnData.getProbGivenYes();
        ArrayList<Double> probNo = learnData.getProbGivenNo();


        
        

        return finalProb;
    }
    
}
