package ClassPackage;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager implements FileInterface{
    protected File activeFile;
    protected Scanner fileScanner;
    protected Scanner dataScanner;
    protected PrintWriter fileWriter;
    protected File outFile;
    protected File logFile;

    protected static boolean open = false;
    protected static int line = 0;

    // Constructor
    public FileManager(String fileName, String logFileName){

        try {
            // File Must be in the project directory not in src
            activeFile = new File(fileName);
            logFile = new File(logFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    // Close all readers and writers
    public void closeAll(){
        fileScanner.close();
        fileWriter.close();
    }


    // Delete a file called fileName
    public void deleteFile(String fileName){
        
        try{
            File file = new File(fileName);

            if (file.delete()){
                System.out.println("Successful Delete!");
            }
            else{
                System.out.println("Failed to Delete!");
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    
    
    // Used to read the Features of the csv and return how many
    // Assuming the features and their names are in the first and second line
    // and are in order i.e. (feature feature label) not (feature, label, feature)
    public int readFeatures(String[] features){
        int count = 0;
        String s;
        String[] temp;

        // Open the file with the scanner
        try {
            fileScanner = new Scanner(activeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Counting how many Features
        s = fileScanner.nextLine();
        s.toLowerCase();

        // Removing white space before/after comma
        // https://stackoverflow.com/questions/41953388/java-split-and-trim-in-one-shot
        temp = s.split("\\s*,\\s*");

        for(String i: temp){
            if(i == "feature"){
                count++;
            }
        }

        // Placing names into the String array
        s = "";
        temp = new String[0];
        s = fileScanner.nextLine();
        s.toLowerCase();

        temp = s.split("\\s*,\\s*");

        for(int i = 0; i < count; i++){
            features[i] = temp[i];
        }

        // Close the scanner
        fileScanner.close();

        return count;
    }


    
    // Used to read the labels of the csv and return how many
    // Assuming the labels and their names are in the first and second line
    // and are in order i.e. (feature, feature, label) not (feature, label, feature)
    public int readLabels(String[] labels){
        int count = 0, index = 0, pos = 0;
        String s;
        String[] temp;

        // Open the file with the scanner
        try {
            fileScanner = new Scanner(activeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        s = fileScanner.nextLine();
        s.toLowerCase();

        temp = s.split("\\s*,\\s*");

        for(String i: temp){
            if(i == "label"){
                count++;
            }
        }
        // Find first occurance for later
        for(int i = temp.length; i < temp.length; i++){
            if(temp[i] == "label"){
                pos = i;
                break;
            }
        }

        // Placing names into the String array
        s = "";
        temp = new String[0];
        s = fileScanner.nextLine();
        s.toLowerCase();

        temp = s.split("\\s*,\\s*");

        // Put last label names into string array
        for(int i = pos; i < temp.length; i++){
            labels[index] = temp[i];
            index++;
        }

        // Close the scanner
        fileScanner.close();

        return count;
    }



    // Used to read line by line and close when finished
    public String readData(){
        String s = "";

        // Using a new scanner because when reading the data its better to 
        // open the file once an then just call this method to return the next line
        if(open != true){
            // Open the file with the scanner
            try {
                dataScanner = new Scanner(activeFile);
                open = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Skip the first lines and move to the data 
        while(line < 3){
            s = dataScanner.nextLine();

            line++;
        }

        while(dataScanner.hasNextLine()){

            s = dataScanner.nextLine();
            if(s.contains(",,")){
                continue;
            }
            else{
                s.toLowerCase();
                return s;
            }
        }

        return s;
    }



    // Writes an input string (full csv line) out to a file
    public void writeNextCSV(String fileName, String next){

    }



    // Writes an input line into a log file
    public void writeNextLog(String nextLine){

    }

    
    
}// End FileManager
