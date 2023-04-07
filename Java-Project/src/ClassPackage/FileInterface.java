package ClassPackage;

import java.util.ArrayList;

public interface FileInterface {

    public void closeAll();
    
    public int readFeatures(ArrayList<String> features);
    public int readLabels(ArrayList<String> labels);
    public String readData();
}
