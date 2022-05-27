import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.CropImageFilter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;

public class ThoughtsAPI {
    private String baseURL;

    public ThoughtsAPI()
    {
        baseURL = "https://philosophyapi.herokuapp.com/api/schools/";
    }

    public ArrayList<Thoughts> getSchools()
    {
        String url = makeAPICall();
        ArrayList<Thoughts> schools = parseSchools(url);
        return schools;
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

    public ArrayList<Thoughts> parseSchools(String json) // get page 2
    {
        ArrayList<Thoughts> thoughts = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(json);
        JSONArray resultObj = jsonObj.getJSONArray("results");
        for(int i = 0; i < resultObj.length(); i++)
        {
            JSONObject currentObj = resultObj.getJSONObject(i);
            String name = currentObj.getString("name");
            JSONArray creatorArr = currentObj.getJSONArray("philosophers");
            String[] creators = new String[creatorArr.length()];
            for (int c = 0; c < creatorArr.length(); c++)
            {
                creators[c] = creatorArr.getString(c);
            }
            thoughts.add(new Thoughts(name, creators));
        }
        return thoughts;
    }
}
