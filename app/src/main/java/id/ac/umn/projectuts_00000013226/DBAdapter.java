package id.ac.umn.projectuts_00000013226;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBAdapter extends SQLiteOpenHelper {
    public static String DB_PATH = "";
    public static int DB_VERSION = 1;
    public static String DATABASE_NAME = "books.db";
    public SQLiteDatabase db;
    public Context context;
    private static final String TABLE_BOOKS = "books";

    public static final String COLUMN_ASIN = "ASIN";
    public static final String COLUMN_GROUP = "GROUP";
    public static final String COLUMN_FORMAT = "FORMAT";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_PUBLISHER = "PUBLISHER";
    public static final String COLUMN_FAVORITE = "FAVORITE";

    public DBAdapter(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
        //write full path to the database of your application
        String packageName = context.getPackageName();
        DB_PATH = "/data/data/" + packageName + "/databases/";
        this.context = context;
        openDatabase();

    }

    public SQLiteDatabase openDatabase(){
        String path = DB_PATH + DATABASE_NAME;
        if(db==null){
            createDatabase();

            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
//            db.disableWriteAheadLogging();

        }
        return db;
    }

    public void createDatabase(){
        boolean dbExist = checkDatabase();
        if(!dbExist){
            this.getReadableDatabase();
            try {
                copyDatabase();
            }catch (IOException e){
                throw new Error ("Error copying database");
            }
        }else{
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    private boolean checkDatabase(){
        String path = DB_PATH + DATABASE_NAME;
        File dbFile = new File(path);
        Log.d("debug", "file exists: " + dbFile.exists());
        return dbFile.exists();
    }

    private void copyDatabase() throws IOException{

        // open a stream for reading from the ready made database
        // the stream source is located in the assets
        InputStream externalDBStream = context.getAssets().open(DATABASE_NAME);
        // path to the created empty database on your android device
        String outFileName = DB_PATH + DATABASE_NAME;
        // create a stream for waiting the database byte by byte
        OutputStream localDBStream = new FileOutputStream(outFileName);
        // copying the database
        byte[] buffer = new byte[externalDBStream.available()];
        externalDBStream.read(buffer);
        localDBStream.write(buffer);
        localDBStream.flush();
        // close the stream
        localDBStream.close();
        externalDBStream.close();
    }

    @Override
    public synchronized void close(){
        if(db!=null){
            db.close();
        }
        super.close();
    }

    public List<Book> getAllBooks(){
        List<Book> results = new ArrayList<>();
        SQLiteDatabase tempdb = this.getWritableDatabase();
        Cursor cursor;
        try{
            cursor = tempdb.query(TABLE_BOOKS ,null,null,null, null,null, null, "100");
            if(cursor == null) return null;

            cursor.moveToFirst();
            do{
                Book book = new Book(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ASIN)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_GROUP)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FORMAT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE))
                );
                results.add(book);
            }while (cursor.moveToNext());
            cursor.close();
        }
        catch (Exception e){
            Log.e(this.getClass().toString(), "Gagal");
        }
        return results;
    }

    public List<Book> getAllBooksSearchTitle(String x){
        List<Book> results = new ArrayList<>();
        SQLiteDatabase tempdb = this.getWritableDatabase();
        Cursor cursor;

        //String x ="Christian";
        try{
            cursor = tempdb.query(TABLE_BOOKS ,null,"TITLE LIKE '%"+x+"%'" ,null, null,null, null, "100");
            if(cursor == null) return null;

            cursor.moveToFirst();
            do{
                Book book = new Book(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ASIN)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_GROUP)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FORMAT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE))
                );
                results.add(book);
            }while (cursor.moveToNext());
            cursor.close();
        }
        catch (Exception e){
            Log.e(this.getClass().toString(), "Gagal");
        }
        return results;
    }

    public List<Book> getAllBooksSearchAuthor(String x){
        List<Book> results = new ArrayList<>();
        SQLiteDatabase tempdb = this.getWritableDatabase();
        Cursor cursor;
        //String x ="Christian";
        try{
            cursor = tempdb.query(TABLE_BOOKS ,null,"AUTHOR LIKE '%"+x+"%'" ,null, null,null, null, "100");
            if(cursor == null) return null;

            cursor.moveToFirst();
            do{
                Book book = new Book(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ASIN)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_GROUP)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FORMAT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE))
                );
                results.add(book);
            }while (cursor.moveToNext());
            cursor.close();
        }
        catch (Exception e){
            Log.e(this.getClass().toString(), "Gagal");
        }
        return results;
    }


    public List<Book> getAllBooksSortTitle(){
        List<Book> results = new ArrayList<>();
        SQLiteDatabase tempdb = this.getWritableDatabase();
        Cursor cursor;
        try{
            cursor = tempdb.query(TABLE_BOOKS ,null,null,null, null,null, COLUMN_TITLE+ " ASC", "100");
            if(cursor == null) return null;

            cursor.moveToFirst();
            do{
                Book book = new Book(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ASIN)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_GROUP)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FORMAT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE))
                );
                results.add(book);
            }while (cursor.moveToNext());
            cursor.close();
        }
        catch (Exception e){
            Log.e(this.getClass().toString(), "Gagal");
        }
        return results;
    }

    public List<Book> getAllBooksSortAuthor(){
        List<Book> results = new ArrayList<>();
        SQLiteDatabase tempdb = this.getWritableDatabase();
        Cursor cursor;
        try{
            cursor = tempdb.query(TABLE_BOOKS ,null,null,null, null,null, COLUMN_AUTHOR+ " ASC", "100");
            if(cursor == null) return null;

            cursor.moveToFirst();
            do{
                Book book = new Book(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ASIN)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_GROUP)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FORMAT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE))
                );
                results.add(book);
            }while (cursor.moveToNext());
            cursor.close();
        }
        catch (Exception e){
            Log.e(this.getClass().toString(), "Gagal");
        }
        return results;
    }

    public List<Book> getAllBooksFavorite(){
        List<Book> results = new ArrayList<>();
        SQLiteDatabase tempdb = this.getWritableDatabase();
        Cursor cursor;
        try{
            cursor = tempdb.query(TABLE_BOOKS ,null,COLUMN_FAVORITE+ "= 1",null, null,null, COLUMN_TITLE+ " ASC", "100");
            if(cursor == null) return null;

            cursor.moveToFirst();
            do{
                Book book = new Book(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ASIN)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_GROUP)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FORMAT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE))
                );
                results.add(book);
            }while (cursor.moveToNext());
            cursor.close();
        }
        catch (Exception e){
            Log.e(this.getClass().toString(), "Gagal");
        }
        return results;
    }

    public Book getBook(String asin){
        Book result = new Book();
        SQLiteDatabase tempdb = this.getWritableDatabase();
        Cursor cursor;
        try{
            cursor = tempdb.query(TABLE_BOOKS ,null,COLUMN_ASIN+"=?", new String[] {asin}, null,null, null, "100");
            if(cursor == null) return null;
            Log.e("asdsada", "test1: "+asin);
            cursor.moveToFirst();
            Log.e("asdsada", "test4: "+asin);
            result = new Book(
                    cursor.getString(cursor.getColumnIndex(COLUMN_ASIN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GROUP)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_FORMAT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE))
            );
            Log.e("asdsada", "test2: "+asin);
            Log.e("asdsada", "test3: "+ result.get_asin()+" "+result.get_favorite());
            cursor.close();
        }
        catch (Exception e){
            Log.e(this.getClass().toString(), "Gagal");
        }

        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    public void updateDatabase(ContentValues cv, String _asin){
        SQLiteDatabase tempdb = this.getWritableDatabase();
        tempdb.update(TABLE_BOOKS, cv, COLUMN_ASIN+" = ?", new String[] {_asin});
    }

}
