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

    JButton selectFileButton, learnButton, clearModelButton;

    JTextArea displayArea;

    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());


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

        displayArea = new JTextArea(25,50);
        displayArea.setLineWrap(true);

        // Text
        selectFileButton.setText("Select File");
        learnButton.setText("Learn!");
        clearModelButton.setText("Clear model");

        // Tool Tips
        selectFileButton.setToolTipText("Select a csv file");
        learnButton.setToolTipText("Learn using the file provided");
        clearModelButton.setToolTipText("Delete all learned data!");

        // Panels and their buttons
        topPanel.add(selectFileButton);
        topPanel.add(learnButton);

        midPanel.add(displayArea);
        displayArea.setSize(50, 50);

        bottomPanel.add(clearModelButton);

        // Listeners for buttons
        selectFileButton.addActionListener(this);
        learnButton.addActionListener(this);
        clearModelButton.addActionListener(this);
    }



    // All actions to be performed after clicking a button.
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectFileButton){
            chooseFile();
        }
        else if(e.getSource() == learnButton){

        }
        else if(e.getSource() == clearModelButton){
            // Confirmation needed before clearing the learned data

        }
    }

    // Action when the window is closed
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Window Closed");
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

    // Display to text area
    public void displayToTextArea(String s){
        displayArea.append(s + ", ");

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
