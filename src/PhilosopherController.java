import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class PhilosopherController implements ActionListener
{
    private JFrame frame;
    private JLabel currentInfo;
    private JTextField entryField;
    private PhilosopherAPI philosopherClient;
    private ThoughtsAPI schoolsClient;
    private JPanel philosophyPanel;
    private JPanel entryPanel;
    private JButton philosopherButton;
    private JButton schoolsButton;
    private JTextArea mainTextArea;
    private JFrame imageFrame;

    private ArrayList<Philosopher> philosophers;
    private ArrayList<Thoughts> schools;
    private boolean isPhilosopher;
    private boolean isSchools;


    public PhilosopherController()
    {
        frame = new JFrame("Philosopher Database");
        entryField = new JTextField();
        philosopherClient = new PhilosopherAPI();
        schoolsClient = new ThoughtsAPI();
        philosophyPanel = new JPanel();
        entryPanel = new JPanel();
        mainTextArea = new JTextArea();
        mainTextArea.setColumns(35);
        mainTextArea.setRows(30);
        mainTextArea.setEditable(false);
        mainTextArea.setOpaque(false);
        mainTextArea.setLineWrap(true);

        philosopherButton = new JButton("Philosophers");
        schoolsButton = new JButton("Schools");
        philosophers = philosopherClient.getPhilosophers();
        schools = schoolsClient.getSchools();
        isPhilosopher = false;
        isSchools = false;

        setupGUI();
    }

    private void setupGUI()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Philosopher Database");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.darkGray);

        JPanel logoWelcomePanel = new JPanel();
        logoWelcomePanel.add(welcomeLabel);
        //----------------------------------------------------------------
        JLabel philosopherLabel = new JLabel("Enter Philosopher: ");
        entryField = new JTextField(5);
        JPanel entryPanel2 = new JPanel();
        entryPanel2.add(philosopherLabel);
        entryPanel2.add(entryField);

        //----------------------------------------------------------------
        mainTextArea.setText("Welcome to the Philosophy Database!\nPress a button to get started :)");
        mainTextArea.setFont(new Font("Arial", Font.BOLD, 15));
        mainTextArea.setForeground(Color.darkGray);

        philosophyPanel = new JPanel();
        philosophyPanel.add(mainTextArea);

        //---------------------------------------------------------------
        entryPanel = new JPanel();
        entryPanel.add(philosopherButton);
        entryPanel.add(schoolsButton);

        //---------------------------------------------------------------
        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(philosophyPanel, BorderLayout.CENTER);
        frame.add(entryPanel, BorderLayout.SOUTH);

        philosopherButton.addActionListener(this);
        schoolsButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        JButton button = new JButton();
        String text = "";
        button = (JButton) (e.getSource());
        text = button.getText();

        if (text.equals("Philosophers"))
        {
            isPhilosopher = true;
            philosophyPanel.removeAll();
            ArrayList<String> names = philosopherClient.getPhilosopherNames();
            String list = "";
            for (int i = 0; i < names.size(); i++)
            {
                list += (i + 1) + ". " + names.get(i) + "\n";
            }
            philosophyPanel.add(mainTextArea);
            mainTextArea.setText(list);

            //--------------------------------------------------------------------
            setUpEntryField();
        }
        else if (text.equals("Schools"))
        {
            isSchools = true;
            philosophyPanel.removeAll();
            ArrayList<String> schools = schoolsClient.getSchoolNames();
            String list = "";
            for (int i = 0; i < schools.size(); i++)
            {
                list += (i + 1) + ". " + schools.get(i) + "\n";
            }
            philosophyPanel.add(mainTextArea);
           mainTextArea.setText(list);
            //-------------------------------------------------------------------
            setUpEntryField();
        }
        else if (text.equals("Submit"))
        {
            String number = entryField.getText();
            int choice = Integer.parseInt(number);
            if (isPhilosopher)
            {
                loadPhilosopherInfo(choice);
            }
            else if (isSchools)
            {
                loadSchoolsInfo(choice);
            }
        }
        else if (text.equals("Back"))
        {
            isPhilosopher = false;
            isSchools = false;
            philosophyPanel.removeAll();
            philosophyPanel.add(mainTextArea);
            mainTextArea.setText("Welcome to the Philosophy Database!\nPress a button to get started :)");
            frame.pack();
            entryPanel.removeAll();
            frame.pack();
            entryPanel.add(philosopherButton);
            entryPanel.add(schoolsButton);
            frame.pack();
            if(imageFrame != null) {
                imageFrame.dispose();
            }
        }
    }

    private void loadSchoolsInfo(int index)
    {
        Thoughts school = schools.get(index - 1);
        philosophyPanel.removeAll();
        String schoolInfo = "Name: " + school.getThought();
        String[] creators = school.getPhilosophers();
        schoolInfo += "\nCreators: ";
        for (String creator : creators)
        {
            schoolInfo += "\n" + creator;
        }
        philosophyPanel.add(mainTextArea);
        mainTextArea.setText(schoolInfo);
        frame.pack();
    }

    private void loadPhilosopherInfo(int index)
    {
        Philosopher philosopher = philosophers.get(index - 1);
        philosophyPanel.removeAll();
        String philosopherInfo = "Name: " + philosopher.getName() +
                                "\nEra: " + philosopher.getEra() +
                                "\nQuote: " + philosopher.getRandomIdea();
        String[] thoughts = philosopher.getSchoolsOfThought();
        philosopherInfo += "\nSchools: ";
        for (String thought : thoughts)
        {
            philosopherInfo += "\n" + thought;
        }
        philosophyPanel.add(mainTextArea);
        mainTextArea.setText(philosopherInfo);
        frame.pack();

        try {
            URL imageURL = new URL(philosopher.getPhoto());
            BufferedImage image = ImageIO.read(imageURL);
            imageFrame = new JFrame("Portrait of " + philosopher.getName());
            imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ImageIcon currentImage = new ImageIcon(image);
            Image imageData = currentImage.getImage();
            Image scaledImage = imageData.getScaledInstance(500, -1, Image.SCALE_SMOOTH);
            currentImage = new ImageIcon(scaledImage);
            JLabel philosopherImage = new JLabel(currentImage);
            imageFrame.add(philosopherImage);
            imageFrame.pack();
            imageFrame.setVisible(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setUpEntryField()
    {
        entryPanel.removeAll();
        JLabel chooseLabel = new JLabel("Enter a number: ");
        entryField = new JTextField(5);
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");
        entryPanel.add(chooseLabel);
        entryPanel.add(entryField);
        entryPanel.add(submitButton);
        entryPanel.add(backButton);
        frame.pack();

        submitButton.addActionListener(this);
        backButton.addActionListener(this);
    }





}
