package ClassPackage;

import java.util.ArrayList;

public interface FileInterface {

    public void closeAll();
    public void deleteFile(String fileName);
    
    public int readFeatures(ArrayList<String> features);
    public int readLabels(ArrayList<String> labels);
    public String readData();

    public void writeNextCSV(String fileName, String next);
    public void writeNextLog(String nextLine);

}
