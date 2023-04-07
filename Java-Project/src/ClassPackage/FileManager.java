package ClassPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * File Manager class, This class is the reader class of the entire program.
 * It is used to Read Features, Read Labels, Read Data, Count Lines. 
 * It is what reads the CSV file that gets passed to it when an instance is made
 * 
 * Author: Aigars Semjonovs
 * Date: April 2023
 */


public class FileManager extends logWriter implements FileInterface{
    protected File activeFile;
    protected Scanner fileScanner;
    protected Scanner dataScanner;
    
    // Flags and counter
    protected static boolean open = false;
    protected static boolean readTypes = false;
    protected static int line = 0;

    // Constructor
    // Open the file called fileName 
    public FileManager(String fileName){
        super();

        try {
            // File Must be in the project directory not in src
            activeFile = new File(fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        writeLog("Instantiated FileManager");

    }// End FileManager


    
    // Close all readers and writers
    public void closeAll(){
        fileScanner.close();
        PrintWriter pw = getfileWriter();
        writeLog("Closed Readers and Writers");
        pw.close();
    }// End closeAll
    
    
    // Used to read the Features of the csv and return how many
    // Assuming the features and their names are in the first and second line
    // and are in order i.e. (feature feature label) not (feature, label, feature)
    public int readFeatures(ArrayList<String> features){
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
        s = s.toLowerCase();

        // Removing white space before/after comma
        // https://stackoverflow.com/questions/41953388/java-split-and-trim-in-one-shot
        temp = s.split("\\s*,\\s*");

        for(String i: temp){
            if(i.equals("feature")){
                count++;
            }
        }

        // Split and Place names into the String array list
        s = "";
        temp = new String[0];
        s = fileScanner.nextLine();
        s.toLowerCase();

        temp = s.split("\\s*,\\s*");

        for(int i = 0; i < count; i++){
            features.add(temp[i]);
        }

        // Close the scanner
        fileScanner.close();
        writeLog("Read Features and Closed FileScanner");

        return count;
    }// End readFeatures


    
    // Used to read the labels of the csv and return how many
    // Assuming the labels and their names are in the first and second line
    // and are in order i.e. (feature, feature, label) not (feature, label, feature)
    public int readLabels(ArrayList<String> labels){
        int count = 0, pos = 0;
        String s;
        String[] temp;

        // Open the file with the scanner
        try {
            fileScanner = new Scanner(activeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        s = fileScanner.nextLine();
        s = s.toLowerCase();

        temp = s.split("\\s*,\\s*");

        for(String i: temp){
            if(i.equals("label")){
                count++;
            }
        }
        // Find first occurance for later
        for(int i = 0; i < temp.length; i++){
            if(temp[i].equals("label")){
                pos = i;
                break;
            }
        }

        // Split and place names into the String array list
        s = "";
        temp = new String[0];
        s = fileScanner.nextLine();

        temp = s.split("\\s*,\\s*");

        // Put last label names into string array
        for(int i = pos; i < temp.length; i++){
            labels.add( temp[i] );
        }

        // Close the scanner
        fileScanner.close();

        writeLog("Read Labels and Closed FileScanner");

        return count;
    }// End readLabels



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
        // Tried to do it dynamically and failed
        while(line < 3){
            s = dataScanner.nextLine();

            line++;
        }

        

        // Reading while there are lines and skipping any empty lines
        while(dataScanner.hasNextLine()){

            s = dataScanner.nextLine();
            // For reading the data Types
            if(readTypes == false){
                readTypes = true;
                return s;
            }


            if(s.contains(",,")){
                continue;
            }
            else{
                s.toLowerCase();
                return s;
            }
        }

        return s;
    }// End readData

    // Count the number of lines in the entire csv file
    public int countLines(){
        int count = 0;

        try {
            fileScanner = new Scanner(activeFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(fileScanner.hasNextLine()){
            if(fileScanner.nextLine() != null){
                count++;
            }
        }

        fileScanner.close();

        writeLog("Counted Lines of the CSV file");
        
        return count;
    }// End countLines

    // Setters
    public void setOpen(boolean val){
        open = val;
    }

    public void setreadTypes(boolean val){
        readTypes = val;
    }

    public void setline(int val){
        line = val;
    }
    
}// End FileManager
