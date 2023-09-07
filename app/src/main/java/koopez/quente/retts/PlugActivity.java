package koopez.quente.retts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlugActivity extends AppCompatActivity implements LoaderCallbacks<List<SportNews>> {

    private static final String REQUEST_URL = "https://gnews.io/api/v4/top-headlines?";

    private NewsAdapter newsAdapter;

    TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plug);

        ListView newsList = (ListView) findViewById(R.id.newsList);

        emptyText = findViewById(R.id.empty_view);
        newsList.setEmptyView(emptyText);

        newsAdapter = new NewsAdapter(this, new ArrayList<SportNews>());

        newsList.setAdapter(newsAdapter);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SportNews sportNews = newsAdapter.getItem(i);
                Intent intent = new Intent(PlugActivity.this, NewsDetails.class);
                intent.putExtra("title", sportNews.getTitle());
                intent.putExtra("description", sportNews.getDescription());
                intent.putExtra("content", sportNews.getContent());
                intent.putExtra("image", sportNews.getUrlToImage());
                intent.putExtra("publishedAt", sportNews.getPublishedAt());
                intent.putExtra("sourceName", sportNews.getSourceName());
                startActivity(intent);
            }
        });


        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
    }


    @Override
    public Loader<List<SportNews>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("country", "us");
        uriBuilder.appendQueryParameter("category", "sports");
        uriBuilder.appendQueryParameter("apikey", "4a99deef274a1e4aa969999aefe1e3e5");
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<SportNews>> loader, List<SportNews> sportNews) {
        View progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        emptyText.setText("No news found");

        if(sportNews != null && !sportNews.isEmpty()) {
            newsAdapter.addAll(sportNews);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        newsAdapter.clear();
    }

    @Override
    public void onLoaderReset(Loader<List<SportNews>> loader) {
        newsAdapter.clear();
    }

}