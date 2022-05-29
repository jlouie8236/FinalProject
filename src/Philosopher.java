import java.util.Arrays;

public class Philosopher {

    private String name;
    private String photo;
    private String era;
    private String[] schoolsOfThought;
    private String[] ideas;

    public Philosopher(String n, String p, String e, String[] s, String[] i)
    {
        name = n;
        photo = p;
        era = e;
        schoolsOfThought = s;
        ideas = i;
    }

    public String getName()
    {
        return name;
    }

    public String getPhoto()
    {
        return photo;
    }

    public String getEra()
    {
        return era;
    }

    public String[] getSchoolsOfThought()
    {
        return schoolsOfThought;
    }

    public String[] getIdeas()
    {
        return ideas;
    }

    public String getRandomIdea()
    {
        int random = (int) (Math.random() * ideas.length - 1);
        return ideas[random];
    }
}
