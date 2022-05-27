import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Hello World!");
        System.out.println();
        PhilosopherAPI pAPI = new PhilosopherAPI();
        ArrayList<Philosopher> p = pAPI.getPhilosophers();

        ThoughtsAPI tAPI = new ThoughtsAPI();
        ArrayList<Thoughts> t = tAPI.getSchools();

        Philosopher philosopher = p.get(0);
        System.out.println(philosopher.getName());
        System.out.println(philosopher.getPhoto());
        System.out.println(philosopher.getEra());
        for (String s : philosopher.getSchoolsOfThought())
        {
            System.out.println(s);
        }

        for (String i : philosopher.getIdeas())
        {
            System.out.println(i);
        }

        System.out.println(t.size());
        Thoughts thought = t.get(0);
        System.out.println(thought.getThought());
        for (String philo : thought.getPhilosophers())
        {
            System.out.println(philo);
        }

    }
}
