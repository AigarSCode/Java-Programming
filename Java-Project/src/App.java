import ClassPackage.*;

/*
 * Main Control Class of the entire program
 */


public class App {
    public static LearnData ld;

    public static void main(String[] args){
        
        //GUI mainGUI = new GUI();

        // Will get filename from input window
        ld = new LearnData("C:\\Users\\Aigar\\Java-Programming Repo\\Java-Programming\\Java-Project\\MLdata.csv");

        ld.makeFeatureandLabelArrays();
    }
}
