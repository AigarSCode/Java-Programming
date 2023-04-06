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


public class GUI extends JFrame implements ActionListener, WindowListener{
    File chosenFile;
    // Open the working directory instead of the default directory
    // https://stackoverflow.com/questions/21534515/jfilechooser-open-in-current-directory
    File workingDir = new File(System.getProperty("user.dir"));


    JFrame mainFrame;
    JPanel topPanel, midPanel, bottomPanel;
    JButton selectFileButton, learnButton, clearModelButton, viewProbs, inputProbs;
    JTextArea displayArea;

    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());

    // All other classes that will be used
    LearnData learnData;
    FileManager fileManager;
    AnalyseInput userInput;


    // Main Constructor for the GUI
    public GUI(){

        // Initialization of the frame and panels
        mainFrame = new JFrame("Main Window");
        topPanel = new JPanel();
        midPanel = new JPanel();
        bottomPanel = new JPanel();

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
        viewProbs = new JButton();
        inputProbs = new JButton();

        displayArea = new JTextArea(25,50);
        displayArea.setLineWrap(true);

        // Text
        selectFileButton.setText("Select File");
        learnButton.setText("Learn!");
        clearModelButton.setText("Clear model");
        viewProbs.setText("View Probabilities");
        inputProbs.setText("Get Prediction");

        // Tool Tips
        selectFileButton.setToolTipText("Select a csv file");
        learnButton.setToolTipText("Learn using the file provided");
        clearModelButton.setToolTipText("Delete all learned data!");
        viewProbs.setToolTipText("View all learned probabilities");
        inputProbs.setToolTipText("Input info to get Probability");

        // Panels and their buttons
        topPanel.add(selectFileButton);
        topPanel.add(learnButton);
        topPanel.add(viewProbs);
        topPanel.add(inputProbs);

        midPanel.add(displayArea);
        displayArea.setSize(50, 50);

        bottomPanel.add(clearModelButton);

        // Listeners for buttons
        selectFileButton.addActionListener(this);
        learnButton.addActionListener(this);
        clearModelButton.addActionListener(this);
        viewProbs.addActionListener(this);
        inputProbs.addActionListener(this);
    }




    // All actions to be performed after clicking a button.
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectFileButton){
            chooseFile();
        }
        else if(e.getSource() == learnButton){
            learnData = new LearnData(chosenFile.getAbsolutePath());
        }
        else if(e.getSource() == clearModelButton){
            // Get Confirmation
            confirmWindow();
        }
        else if(e.getSource() == inputProbs){
            //
            // NEED TO CALL ANALYSE INPUT WITH THIS RETURN
            //
            getUserString();
        }
        else if(e.getSource() == viewProbs){
            //
            // NEED TO DISPLAY PROBABILITIES IN THE TEXT AREA
            //
        }
    }

    // Action when the window is closed
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Window Closed");
    }


    // Display to text area
    public void displayToTextArea(String s){
        displayArea.append(s + ", ");
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
        }
    }


    // Displaying a confirmation window to the user
    // https://mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
    public void confirmWindow(){
        int choice = JOptionPane.showConfirmDialog(null,"Delete Model Data?");

        if(choice == 0){
            displayToTextArea("Deleting Model Data!");
        }
        else{
            displayToTextArea("Not Deleting");
        }
    }


    // Getting user input through another window
    // https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
    public String[] getUserString(){
        JPanel inputPanel = new JPanel();
        inputPanel.setSize(400, 150);
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();
        JTextField field4 = new JTextField();
        JTextField field5 = new JTextField();
        String inputString = "";
        String[] resultString = {""};

        inputPanel.add(field1);
        field1.setText("Female/Male");
        inputPanel.add(field2);
        field2.setText("Yes/No");
        inputPanel.add(field3);
        field3.setText("Yes/No");
        inputPanel.add(field4);
        field4.setText("Urban/Rural");
        inputPanel.add(field5);
        field5.setText("Yes/No");

        int choice = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Values", JOptionPane.OK_CANCEL_OPTION);

        if(choice == JOptionPane.OK_OPTION){
            inputString += field1.getText() + ",";
            inputString += field2.getText() + ",";
            inputString += field3.getText() + ",";
            inputString += field4.getText() + ",";
            inputString += field5.getText();
            
            resultString = inputString.split("\\s*,\\s*");
        }

        return resultString;
    }
    

    

    


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
    
}
