package id.ac.umn.projectuts_00000013226;

import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    List<Book> listBook = new ArrayList<>();
    DBAdapter dbHelper;
    LinearLayout container;
    TextView dataTitle, dataAuthor, dataAsin, dataGroup, dataFormat, dataPublisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        container = findViewById(R.id.content);
        dbHelper = new DBAdapter(getApplicationContext());
        dbHelper.createDatabase();

//        listBook = dbHelper.getAllBooks();
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            dataTitle = findViewById(R.id.id_title);
            dataAuthor = findViewById(R.id.id_author);
            dataAsin = findViewById(R.id.id_asin);
            dataGroup = findViewById(R.id.id_group);
            dataFormat = findViewById(R.id.id_format);
            dataPublisher = findViewById(R.id.id_publisher);

            dataTitle.setText(bundle.getString("title"));
            dataAuthor.setText(bundle.getString("author"));
            dataAsin.setText(bundle.getString("asin"));
            dataFormat.setText(bundle.getString("format"));
            dataPublisher.setText(bundle.getString("publisher"));
            dataGroup.setText(bundle.getString("group"));
        }

        else {
            Log.e("asd", "onCreate: sadsadsaddadadsazzzzzz");
        }

        final Book obj = dbHelper.getBook(dataAsin.getText().toString());
        final FloatingActionButton floatButton = findViewById(R.id.floatingActionButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                if(obj.get_favorite() == 0){
                    floatButton.setImageResource(R.drawable.ic_star_black_24dp);
                    cv.put("FAVORITE", 1);
                    obj.set_favorite(1);
                    Log.e("debug", "test klik");
                    Toast.makeText(DetailsActivity.this, "This book is now added to My Favorite", Toast.LENGTH_SHORT).show();
                }
                else{
                    floatButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                    cv.put("FAVORITE", 0);
                    obj.set_favorite(0);
                    Log.e("debug", "test klik");
                    Toast.makeText(DetailsActivity.this, "This book is now removed to My Favorite", Toast.LENGTH_SHORT).show();
                }
                dbHelper.updateDatabase(cv, obj.get_asin());
            }
        });
        if(obj.get_favorite() == 0){
            floatButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
        else{
            floatButton.setImageResource(R.drawable.ic_star_black_24dp);
        }
    }

}
