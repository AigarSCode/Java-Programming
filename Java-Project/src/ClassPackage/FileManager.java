package ClassPackage;

import java.io.File;
import java.util.Scanner;

public class FileManager {
    protected File csvfile;
    protected Scanner fileScanner;

    // Constructor
    public FileManager(String fileName){

        try {
            // File Must be in the project directory not in src
            csvfile = new File(fileName);

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
            fileScanner = new Scanner(csvfile);

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

    
    
}
