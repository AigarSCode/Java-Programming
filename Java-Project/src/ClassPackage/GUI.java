package ClassPackage;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/*
 * GUI class used to Setup the GUI, the program is controled through the GUI
 * Acts as a Parent class for every other Class (it makes instances of the necessary classes)
 * 
 * Author: Aigars Semjonovs
 * Date: April 2023
 */


public class GUI extends JFrame implements ActionListener, WindowListener{
    File chosenFile;
    // Open the working directory instead of the default directory
    // https://stackoverflow.com/questions/21534515/jfilechooser-open-in-current-directory
    File workingDir = new File(System.getProperty("user.dir"));
    boolean hasReset = true;


    JFrame mainFrame;
    JPanel topPanel, midPanel, bottomPanel;
    JButton selectFileButton, learnButton, clearModelButton, inputProbs;
    JTextArea displayArea;

    JLabel label1, label2, label3, label4, label5;
    JTextField field1, field2, field3, field4, field5;

    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());

    // All other classes that will be used
    LearnData learnData;
    FileManager fileManager;
    AnalyseInput userInput;


    // Constructor
    // Main Constructor for the GUI
    public GUI(){

        // Initialization of the frame and panels
        mainFrame = new JFrame("Main Window");
        topPanel = new JPanel();
        midPanel = new JPanel();
        bottomPanel = new JPanel();

        // Main Frame Config
        mainFrame.setVisible(true);
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.addWindowListener(this);
        

        // Layouts of the panels
        mainFrame.add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.lightGray);

        mainFrame.add(midPanel, BorderLayout.CENTER);
        midPanel.setLayout(new FlowLayout());
        midPanel.setBackground(Color.lightGray);

        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.lightGray);

        // Buttons and Text area
        selectFileButton = new JButton();
        learnButton = new JButton();
        clearModelButton = new JButton();
        inputProbs = new JButton();

        displayArea = new JTextArea(25,50);
        displayArea.setLineWrap(true);

        // Text
        selectFileButton.setText("Select File");
        learnButton.setText("Learn!");
        clearModelButton.setText("Clear model");
        inputProbs.setText("Get Prediction");

        // Tool Tips
        selectFileButton.setToolTipText("Select a csv file");
        learnButton.setToolTipText("Learn using the file provided");
        clearModelButton.setToolTipText("Delete all learned data!");
        inputProbs.setToolTipText("Input info to get Probability");

        // Panels and their buttons
        topPanel.add(selectFileButton);
        topPanel.add(learnButton);
        topPanel.add(inputProbs);

        midPanel.add(displayArea);
        displayArea.setSize(50, 50);

        bottomPanel.add(clearModelButton);

        // Listeners for buttons
        selectFileButton.addActionListener(this);
        learnButton.addActionListener(this);
        clearModelButton.addActionListener(this);
        inputProbs.addActionListener(this);
    }// End GUI




    // All actions to be performed after clicking a button.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Choose a file
        if(e.getSource() == selectFileButton){
            chooseFile();
        }
        // Learn the Data
        else if(e.getSource() == learnButton){
            if(chosenFile == null){
                JOptionPane.showMessageDialog(null, "Please choose a file first");
            }
            else{
                displayToTextArea("Started Learning Process");
                learnData = new LearnData(chosenFile.getAbsolutePath());
                userInput = new AnalyseInput(learnData);
                // Remove Reset flag
                hasReset = false;
                learnData.fm.writeLog("Completed Learn Data Operations");
            }
        }
        // Clear the model (With confirmation)
        else if(e.getSource() == clearModelButton){
            if(learnData == null){
                displayToTextArea("There is nothing to clear");
                JOptionPane.showMessageDialog(null, "Please click the learn button first");
            }
            else{
                // Get Confirmation
                confirmWindow();
            }
        }
        // Input User data and Display results
        else if(e.getSource() == inputProbs){
            // Error checking before calling Input and AnalyseInput
            //if(userInput == null){
            if(hasReset == true){
                JOptionPane.showMessageDialog(null, "Please click the learn button first");
            }
            else{
                learnData.fm.writeLog("Clicked InputProbs");
                String[] s;
                if( (s = getUserString()) == null ){
                    JOptionPane.showMessageDialog(null,"Empty Input, cannot calculate!");
                }
                else{
                    displayToTextArea("Calculating Result!");
                    double result = userInput.calculateProb(s);
                    String res = resultString(result);

                    JOptionPane.showMessageDialog(null, res);
                }
            }
        }
    }// End actionPerformed


    // Action when the window is closed
    // Close all writers and scanners
    @Override
    public void windowClosing(WindowEvent e) {
        if(learnData != null){
            learnData.fm.writeLog("Closed the window");
            learnData.fm.closeAll();
        }
        System.out.println("Window Closed");
        System.exit(0);
    } 


    // Display to text area
    public void displayToTextArea(String s){
        displayArea.append("> " + s + "\n");

        if(learnData != null){
            learnData.fm.writeLog("Displayed to Text Area");
        }
    }


    /*
    * Letting the user choose a file to open and limiting to only csv files
    * https://mkyong.com/swing/java-swing-jfilechooser-example
    */
    public void chooseFile(){
        
        fileChooser.setDialogTitle("Select a csv file");
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Adding a filter that will only allow csv files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.addChoosableFileFilter(filter);

        // Working directory that will be displayed
        fileChooser.setCurrentDirectory(workingDir);


        int statusVal = fileChooser.showOpenDialog(null);

        if(statusVal == JFileChooser.APPROVE_OPTION){
            chosenFile = fileChooser.getSelectedFile();
            displayToTextArea("The file you chose is: " + chosenFile.getAbsolutePath());
        }
        else{
            displayToTextArea("You did not Choose a file!");
        }

    }// End chooseFile


    // Displaying a confirmation window to the user
    // https://mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
    public void confirmWindow(){
        if(learnData != null){
            learnData.fm.writeLog("Displayed Confirmation Window");
        }
        
        int choice = JOptionPane.showConfirmDialog(null,"Delete Model Data?");

        if(choice == 0){
            displayToTextArea("Deleting All Model Data!");
            learnData.clearLearnedData();
            // Set flag has reset
            hasReset = true;
        }
        else{
            displayToTextArea("Not Deleting Data");
        }
    }// End confirmWindow


    // Getting user input through another window
    // https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
    public String[] getUserString(){
        learnData.fm.writeLog("Displayed Input Window");

        String inputString = "";
        String[] resultString = null;

        JPanel basePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JTextArea textArea = new JTextArea();

        // Text Fields
        field1 = new JTextField();
        field2 = new JTextField();
        field3 = new JTextField();
        field4 = new JTextField();
        field5 = new JTextField();
        
        // This made the UI work dont touch
        basePanel.add(inputPanel, BorderLayout.SOUTH);
        basePanel.add(topPanel, BorderLayout.NORTH);

        topPanel.add(textArea);
        textArea.setSize(300,300);
        textArea.setLineWrap(true);
        inputPanel.add(topPanel);

        //Display all the Features for the user to see
        for(int i = 0; i < learnData.featureArray.size(); i++){
            String s = ((i+1) + ". " + learnData.featureArray.get(i));
            textArea.append(s + "\n");
        }

        // Text fields and their labels
        label1 = new JLabel("1:");
        inputPanel.add(label1);
        inputPanel.add(field1);
        field1.setText("Female/Male");

        label2 = new JLabel("2:");
        inputPanel.add(label2);
        inputPanel.add(field2);
        field2.setText("Yes/No");

        label3 = new JLabel("3:");
        inputPanel.add(label3);
        inputPanel.add(field3);
        field3.setText("Yes/No");

        label4 = new JLabel("4:");
        inputPanel.add(label4);
        inputPanel.add(field4);
        field4.setText("Urban/Rural");

        label5 = new JLabel("5:");
        inputPanel.add(label5);
        inputPanel.add(field5);
        field5.setText("Yes/No");

        // Displaying the Menu itself
        int choice = JOptionPane.showConfirmDialog(null, basePanel, "Enter Values", JOptionPane.OK_CANCEL_OPTION);

        if(choice == JOptionPane.OK_OPTION){
            inputString += field1.getText() + ",";
            inputString += field2.getText() + ",";
            inputString += field3.getText() + ",";
            inputString += field4.getText() + ",";
            inputString += field5.getText();
            
            inputString = inputString.toLowerCase();
            resultString = inputString.split("\\s*,\\s*");
        }

        learnData.fm.writeLog("Got User Input");

        return resultString;
    }// End getUserString

    // Just makes the result string more readable
    public String resultString(double prob){
        String s = "";
        if(userInput.answer == true){
            s += "This could be a future Entrepreneur! \n";
        }
        else{
            s += "This may not be a future Entrepreneur! \n";
        }

        s += "Probability is: " + String.format("%.2f", prob) + "%";

        learnData.fm.writeLog("Displayed Result and Probability");

        return s;

    }// End resultString
    

    

    


    //////////////////////////////////////////////////////////////////
    // UN-USED METHODS FROM WINDOWLISTENER
    //////////////////////////////////////////////////////////////////
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
    
}// End GUI
