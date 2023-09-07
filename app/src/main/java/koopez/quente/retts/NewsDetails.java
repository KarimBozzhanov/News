package koopez.quente.retts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetails extends AppCompatActivity {

    TextView title, description, content, sourceName, publishedAt;
    ImageView image;
    Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        title = (TextView) findViewById(R.id.newsTitle);
        description = (TextView) findViewById(R.id.description);
        content = (TextView) findViewById(R.id.content);
        sourceName = (TextView) findViewById(R.id.sourceName);
        publishedAt = (TextView) findViewById(R.id.publishedAt);
        image = (ImageView) findViewById(R.id.image);
        closeButton = (Button) findViewById(R.id.closeNews);


        Bundle bundle = getIntent().getExtras();
        title.setText(bundle.getString("title"));
        description.setText(bundle.getString("description"));
        content.setText(bundle.getString("content"));
        sourceName.setText(bundle.getString("sourceName"));
        publishedAt.setText(bundle.getString("publishedAt"));
        Picasso.get().load(bundle.getString("image")).into(image);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsDetails.this, PlugActivity.class);
                startActivity(intent);
            }
        });
    }
}