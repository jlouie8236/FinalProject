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

    private ArrayList<Philosopher> philosophers;
    private ArrayList<Thoughts> schools;
    private boolean isPhilosopher;
    private boolean isSchools;


    public PhilosopherController()
    {
        frame = new JFrame("Philosopher Database");
        currentInfo = new JLabel();
        entryField = new JTextField();
        philosopherClient = new PhilosopherAPI();
        schoolsClient = new ThoughtsAPI();
        philosophyPanel = new JPanel();
        entryPanel = new JPanel();

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
        welcomeLabel.setForeground(Color.black);

        JPanel logoWelcomePanel = new JPanel();
        logoWelcomePanel.add(welcomeLabel);
        //----------------------------------------------------------------
        JLabel philosopherLabel = new JLabel("Enter Philosopher: ");
        entryField = new JTextField(5);
        JPanel entryPanel2 = new JPanel();
        entryPanel2.add(philosopherLabel);
        entryPanel2.add(entryField);

        //----------------------------------------------------------------
        currentInfo.setText("<html>Welcome to the Philosophy Database!<br>Press a button to get started :)</html>");
        currentInfo.setFont(new Font("Arial", Font.BOLD, 15));
        currentInfo.setOpaque(false);

        philosophyPanel = new JPanel();
        philosophyPanel.add(currentInfo);

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
            String list = "<html>";
            for (int i = 0; i < names.size(); i++)
            {
                list += (i + 1) + ". " + names.get(i) + " <br>";
            }
            list += "</html>";
            philosophyPanel.add(currentInfo);
            currentInfo.setText(list);
            //--------------------------------------------------------------------
            setUpEntryField();
        }
        else if (text.equals("Schools"))
        {
            isSchools = true;
            philosophyPanel.removeAll();
            ArrayList<String> schools = schoolsClient.getSchoolNames();
            String list = "<html>";
            for (int i = 0; i < schools.size(); i++)
            {
                list += (i + 1) + ". " + schools.get(i) + " <br>";
            }
            list += "</html>";
            philosophyPanel.add(currentInfo);
            currentInfo.setText(list);
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
            philosophyPanel.add(currentInfo);
            currentInfo.setText("<html>Welcome to the Philosophy Database!<br>Press a button to get started :)</html>");
            entryPanel.removeAll();
            entryPanel.add(philosopherButton);
            entryPanel.add(schoolsButton);

        }
    }

    private void loadSchoolsInfo(int index)
    {
        Thoughts school = schools.get(index - 1);
        philosophyPanel.removeAll();
        String schoolInfo = "<html>Name: " + school.getThought();
        String[] creators = school.getPhilosophers();
        schoolInfo += "<br>Creators: ";
        for (String creator : creators)
        {
            schoolInfo += "<br>" + creator;
        }
        schoolInfo += "</html>";
        JLabel schoolInfoLabel = new JLabel(schoolInfo);
        philosophyPanel.add(schoolInfoLabel);
    }

    private void loadPhilosopherInfo(int index)
    {
        Philosopher philosopher = philosophers.get(index - 1);
        philosophyPanel.removeAll();
        String philosopherInfo = "<html> Name: " + philosopher.getName() +
                                "<br> Era: " + philosopher.getEra() +
                                "<br> Quote: " + philosopher.getRandomIdea();
        String[] thoughts = philosopher.getSchoolsOfThought();
        philosopherInfo += "<br> Schools: ";
        for (String thought : thoughts)
        {
            philosopherInfo += "<br>" + thought;
        }
        philosopherInfo += "</html>";
        JLabel philosopherInfoLabel = new JLabel(philosopherInfo);
        philosophyPanel.add(philosopherInfoLabel);

        try {
            URL imageURL = new URL(philosopher.getPhoto());
            BufferedImage image = ImageIO.read(imageURL);
            JFrame frame = new JFrame("Portrait of " + philosopher.getName());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JLabel philosopherImage = new JLabel(new ImageIcon(image));
            frame.add(philosopherImage);
            frame.pack();
            frame.setVisible(true);
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

        submitButton.addActionListener(this);
        backButton.addActionListener(this);
    }





}
