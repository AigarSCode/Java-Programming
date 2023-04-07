package ClassPackage;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class logWriter {
    private PrintWriter fileWriter;
    private File logFile;
    private DateTimeFormatter timeFormat;
    LocalDateTime timeNow;
    

    // Make the Log File and open the Writer
    public logWriter(){

        // Make the Log File
        try {
            logFile = new File("log.txt");
            fileWriter = new PrintWriter(logFile);
            timeFormat = DateTimeFormatter.ofPattern("dd/mm/yyyy hh:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }

        writeLog("Instantiated LogWriter");
    }


    // Write the log to the file including the current time and date
    // https://www.javatpoint.com/java-get-current-date
    public void writeLog(String s){
        String line = timeFormat.format( LocalDateTime.now() ) + " || ";
        line += s + " |";

        fileWriter.println(line);
    }
    

    // Delete a file called fileName
    public void deleteLogFile(){
        
        try{
            if (logFile.delete()){
                System.out.println("Successful Delete!");
            }
            else{
                System.out.println("Failed to Delete!");
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    // Getter
    public PrintWriter getfileWriter(){
        return fileWriter;
    }

    
}
