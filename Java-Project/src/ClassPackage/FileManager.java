package ClassPackage;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager implements FileInterface{
    protected File activeFile;
    protected Scanner fileScanner;
    protected PrintWriter fileWriter;
    protected File outFile;
    protected File logFile;

    // Constructor
    public FileManager(String fileName){

        try {
            // File Must be in the project directory not in src
            activeFile = new File(fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

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


    // Return the next String token from active file
    public String readNext(){
        String s = "";

        try {
            fileScanner = new Scanner(activeFile);

        } catch (Exception e) {
            e.printStackTrace();
        }


        if(fileScanner.hasNext()){
            s = fileScanner.next(",");
        }
        else{
            System.out.println("Nothing left!");
        }
       
        fileScanner.close();
        return s;
    }


    // Writes an input string out to a file
    public void writeNextCSV(String fileName, String next){

    }


    // Writes an input line into a log file and makes it if it doesnt exist
    public void writeNextLog(String filename, String nextLine){

    }

    
    
}// End FileManager
