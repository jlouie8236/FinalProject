import java.util.Arrays;

public class Thoughts {
    private String thought;
    private String[] philosophers;

    public Thoughts(String thought, String[] philosophers)
    {
        this.thought = thought;
        this.philosophers = philosophers;
    }

    public String getThought()
    {
        return thought;
    }

    public String[] getPhilosophers()
    {
        return philosophers;
    }
}
