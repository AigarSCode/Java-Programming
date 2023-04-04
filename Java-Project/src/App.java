import ClassPackage.*;

/*
 * Main Control Class of the entire program
 */


public class App {
    public static void main(String[] args){
        
        //GUI mainGUI = new GUI();

        LearnData ld = new LearnData();

        ld.makeLabelandFeatureArrays();
    }
}
