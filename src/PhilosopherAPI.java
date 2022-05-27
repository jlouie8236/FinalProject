import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.CropImageFilter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;


public class PhilosopherAPI {
    private String baseURL;

    public PhilosopherAPI()
    {
        baseURL = "https://philosophyapi.herokuapp.com/api/philosophers/";
    }

    public ArrayList<Philosopher> getPhilosophers()
    {
        String url = makeAPICall();
        ArrayList<Philosopher> philosophers = parsePhilosophers(url);
        return philosophers;
    }

    public String makeAPICall()
    {
        String url = baseURL;
        try {
            URI myUri = URI.create(url);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Philosopher> parsePhilosophers(String json)
    {
        ArrayList<Philosopher> philosophers = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(json);
        JSONArray resultArr = jsonObj.getJSONArray("results");
        for (int i = 0; i < resultArr.length(); i++)
        {
            JSONObject currentObj = resultArr.getJSONObject(i);
            String name = currentObj.getString("name");
            String photo = currentObj.getString("photo");
            String era = currentObj.getString("era");
            JSONArray schoolArr = currentObj.getJSONArray("school");
            String[] schools = new String[schoolArr.length()];
            for (int s = 0; s < schoolArr.length(); s++)
            {
                schools[s] = schoolArr.getString(s);
            }
            JSONArray ideaArr = currentObj.getJSONArray("ideas");
            String[] ideas = new String[ideaArr.length()];
            for (int d = 0; d < ideaArr.length(); d++)
            {
                ideas[d] = ideaArr.getString(d);
            }
            philosophers.add(new Philosopher(name, photo, era, schools, ideas));
        }
        return philosophers;
    }
}

