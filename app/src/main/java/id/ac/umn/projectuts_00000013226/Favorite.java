package id.ac.umn.projectuts_00000013226;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity {
    List<Book> listBook = new ArrayList<>();
    DBAdapter dbHelper;
    LinearLayout container;
    private SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        container = findViewById(R.id.content);
        dbHelper = new DBAdapter(getApplicationContext());
        dbHelper.createDatabase();


        for (Book book : listBook) {
            if(book.get_favorite() == 1){
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.activity_second2, null);
                TextView dataTitle = addView.findViewById(R.id.id_title);
                TextView dataAuthor = addView.findViewById(R.id.id_author);

                dataTitle.setText(book.get_title());
                dataAuthor.setText(book.get_author());

                container.addView(addView);

                final CardView card_view = findViewById(R.id.id_card);
                card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent details = new Intent(Favorite.this, DetailsActivity.class);

                        startActivity(details);
                    }
                });
            }
        }
    }
}
