package koopez.quente.retts;

import android.content.AsyncTaskLoader;
import android.content.Context;


import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<SportNews>> {


    private String url;


    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<SportNews> loadInBackground() {
        if(url == null) {
            return null;
        }

        List<SportNews> newsList = ResponseProcessing.fetchNewsData(url);
        return newsList;
    }
}
