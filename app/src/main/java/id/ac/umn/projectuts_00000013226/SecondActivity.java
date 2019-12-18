package id.ac.umn.projectuts_00000013226;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    List<Book> listBook = new ArrayList<>();
    DBAdapter dbHelper;
    LinearLayout container;
    private SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        container = findViewById(R.id.content);
        dbHelper = new DBAdapter(getApplicationContext());
        dbHelper.createDatabase();

        listBook = dbHelper.getAllBooks();
        showData();
    }

    @Override
    protected void onResume() {
        listBook = dbHelper.getAllBooks();
        showData();
        super.onResume();
    }

    public void showData(){
        if(container.getChildCount() > 0) container.removeAllViews();
        for (final Book book : listBook) {
            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView = inflater.inflate(R.layout.activity_second2, null);
            TextView dataTitle = addView.findViewById(R.id.id_title);
            TextView dataAuthor = addView.findViewById(R.id.id_author);

            dataTitle.setText(book.get_title());
            dataAuthor.setText(book.get_author());

            final Book obj = book;
            ImageButton btnFavorite = addView.findViewById(R.id.favorite);
            final ImageButton imgButton = btnFavorite;
            final ContentValues cv = new ContentValues();
            imgButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(obj.get_favorite() == 0){
                        imgButton.setImageResource(R.drawable.ic_star_black_24dp);
                        cv.put("FAVORITE", 1);
                        obj.set_favorite(1);
                        Log.e("debug", "test klik");
                        Toast.makeText(SecondActivity.this, "This book is now added to My Favorite", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        imgButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                        cv.put("FAVORITE", 0);
                        obj.set_favorite(0);
                        Log.e("debug", "test klik");
                        Toast.makeText(SecondActivity.this, "This book is now removed to My Favorite", Toast.LENGTH_SHORT).show();
                    }

                    dbHelper.updateDatabase(cv, obj.get_asin());
                }
            });

            if(obj.get_favorite() == 0){
                imgButton.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
            else{
                imgButton.setImageResource(R.drawable.ic_star_black_24dp);
            }
            final View card_view = addView;
            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent details = new Intent(SecondActivity.this, DetailsActivity.class);

                    Bundle bun = new Bundle();

                    bun.putString("title", book.get_title());
                    bun.putString("author", book.get_author());
                    bun.putString("asin", book.get_asin());
                    bun.putString("group", book.get_group());
                    bun.putString("format", book.get_format());
                    bun.putString("publisher", book.get_publisher());

                    details.putExtras(bun);
                    startActivity(details);

                }
            });

            container.addView(addView);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dialog, menu);

        return true;
    }

    final String TAG = MainActivity.class.getSimpleName();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_dialog_utama:
                listBook = dbHelper.getAllBooks();
                showData();

                return true;

            case R.id.menu_dialog_sortBy:
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);

                builder
                        .setTitle("Sort By")
                        /*.setMessage("Silahkan pilih pilihan Anda:")*/
                        .setPositiveButton("Title", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listBook = dbHelper.getAllBooksSortTitle();
                                showData();

                            }
                        })
                        .setNegativeButton("Author", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listBook = dbHelper.getAllBooksSortAuthor();
                                showData();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            case R.id.menu_dialog_aboutMe:
                Intent aboutMe = new Intent(SecondActivity.this, AboutMe.class);
                startActivity(aboutMe);

                return true;

            case R.id.menu_dialog_myFavorite:
                listBook = dbHelper.getAllBooksFavorite();
                showData();

                return true;

            case R.id.menu_dialog_search:
                AlertDialog.Builder search = new AlertDialog.Builder(SecondActivity.this);

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                search.setView(input);

                search
                        .setTitle("Search")
//                        .setMessage("Silahkan pilih pilihan Anda:")
                        .setPositiveButton("Title", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listBook = dbHelper.getAllBooksSearchTitle(input.getText().toString());
                                showData();

                            }
                        })
                        .setNegativeButton("Author", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listBook = dbHelper.getAllBooksSearchAuthor(input.getText().toString());
                                showData();
                            }
                        });

                AlertDialog dialogSearch = search.create();
                dialogSearch.show();

                return true;

            case R.id.menu_dialog_logout:
                Log.e(TAG, "logout0: ");
                preferenceConfig.writeLoginStatus(false);
                Log.e(TAG, "logout1: ");
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
                Log.e(TAG, "logout2: ");
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
