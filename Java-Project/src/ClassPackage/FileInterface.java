package ClassPackage;

public interface FileInterface {

    public void deleteFile(String fileName);
    
    public int readFeatures(String[] features);
    public int readLabels(String[] labels);
    public String[] readData();

    public void writeNextCSV(String fileName, String next);
    public void writeNextLog(String nextLine);

}
