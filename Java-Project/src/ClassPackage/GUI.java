package ClassPackage;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame implements ActionListener{
    JFrame mainFrame;
    JPanel topPanel, midPanel, bottomPanel;

    JButton selectFileButton, learnButton, clearModelButton;

    JTextField displayArea;


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

        // Layouts of the panels
        mainFrame.add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new FlowLayout());
        mainFrame.add(midPanel, BorderLayout.CENTER);
        midPanel.setLayout(new FlowLayout());
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new FlowLayout());

        // Buttons and Text area
        selectFileButton = new JButton();
        learnButton = new JButton();
        clearModelButton = new JButton();

        displayArea = new JTextField();

        selectFileButton.setText("Select File");
        learnButton.setText("Learn!");
        clearModelButton.setText("Clear model");

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
            //Look at docs for FileChooser
        }
        else if(e.getSource() == learnButton){

        }
        else if(e.getSource() == clearModelButton){
            // Confirmation needed before clearing the learned data

        }
    }
    
}
