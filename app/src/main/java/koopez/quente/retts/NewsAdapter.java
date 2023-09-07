package koopez.quente.retts;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<SportNews> {


    public NewsAdapter(Context context, ArrayList<SportNews> newsArrayList) {
        super(context, 0, newsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_card, parent, false);
        }

        SportNews sportNews = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.news_title);
        title.setText(sportNews.getTitle());

        TextView description = (TextView) listItemView.findViewById(R.id.news_description);
        description.setText(sportNews.getDescription());

        ImageView newsImage = (ImageView) listItemView.findViewById(R.id.news_image);
        Picasso.get().load(sportNews.getUrlToImage()).into(newsImage);

        return listItemView;
    }
}
