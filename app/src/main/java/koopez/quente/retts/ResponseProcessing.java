package koopez.quente.retts;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ResponseProcessing {

    public static List<SportNews> fetchNewsData (String requestUrl) {
        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);

        } catch(IOException e) {
            Log.e("TAG", "Problem making HTTP request - " + e);
        }

        List<SportNews> newsList = extractFeaturesFromJson(jsonResponse);

        return newsList;
    }

    public static URL createURL (String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch(IOException e) {
            Log.e("TAG", "Error building url");
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else{
                Log.e("TAG", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch(IOException e) {
            Log.e("TAG", "HTTP request error");
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    public static String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    public static List<SportNews> extractFeaturesFromJson(String newsJson) {
        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }

        List<SportNews> newsList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJson);

            JSONArray articlesArray = baseJsonResponse.getJSONArray("articles");
            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject newsObject = articlesArray.getJSONObject(i);
                String title = newsObject.getString("title");
                String description = newsObject.getString("description");
                String url = newsObject.getString("url");
                String urlToImage = newsObject.getString("image");
                String publishedAt = newsObject.getString("publishedAt");
                String content = newsObject.getString("content");
                JSONObject source = newsObject.getJSONObject("source");
                String sourceName = source.getString("name");
                SportNews sportNews = new SportNews(title, description, sourceName, url, urlToImage, publishedAt, content);

                newsList.add(sportNews);
            }

        } catch (JSONException e) {
            Log.e("TAG", "Problem parsing the news JSON results - " + e);
        }

        return newsList;
    }
}
