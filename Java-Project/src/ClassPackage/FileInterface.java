package ClassPackage;

public interface FileInterface {

    public void deleteFile(String fileName);
    public String readNext();
    public void writeNextCSV(String fileName, String next);
    public void writeNextLog(String fileName, String nextLine);

}
