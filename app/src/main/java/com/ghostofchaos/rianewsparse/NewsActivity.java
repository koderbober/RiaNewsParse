package com.ghostofchaos.rianewsparse;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.Scroller;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ghost on 16.11.2015.
 */
public class NewsActivity extends Activity {
    public Document doc = null;
    public Elements description;
    public News news;
    public TextView tvTitle;
    public TextView tvAnnounce;
    public TextView tvData;
    public TextView tvDescription;
    public DBOpenHelper dbOpenHelper;
    public ArrayList<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        news = (News) getIntent().getSerializableExtra("NEWS");
        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvAnnounce = (TextView) findViewById(R.id.textViewAnnounce);
        tvData = (TextView) findViewById(R.id.textViewData);
        tvDescription = (TextView) findViewById(R.id.textViewDescription);

        if (MainActivity.checkInternetConnection(this)) {
            parse();
        }

        tvTitle.setText(news.getTitle());
        tvAnnounce.setText(news.getAnnounce());
        tvData.setText(news.getData());
        tvDescription.setText(news.getDescription());
        Linkify.addLinks(tvDescription, Linkify.WEB_URLS);

        getNewsFromDB();

        if (!newsList.contains(news)) {
            saveNewsToDB(news);
        }
    }

    public void parse() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            doc = Jsoup.connect("http://m.ria.ru" + news.getDescriptionLink()).get();
            description = doc.select(".article .article-body");
            news.setDescription(description.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNewsFromDB() {
        dbOpenHelper = new DBOpenHelper(this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("news", null, null, null, null, null, null, null);
        newsList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    News news = new News();

                    news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    news.setAnnounce(cursor.getString(cursor.getColumnIndex("announce")));
                    news.setData(cursor.getString(cursor.getColumnIndex("data")));
                    news.setDescription(cursor.getString(cursor.getColumnIndex("description")));

                    newsList.add(news);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
    }

    public void saveNewsToDB(News news) {
        ContentValues contentValues = new ContentValues();
        dbOpenHelper = new DBOpenHelper(this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("news", null, null, null, null, null, null, null);
        cursor.moveToFirst();

        contentValues.put("title", news.getTitle());
        contentValues.put("announce", news.getAnnounce());
        contentValues.put("data", news.getData());
        contentValues.put("description", news.getDescription());
        contentValues.put("id_theme", news.getId_theme());

        Log.d("TABLE ID_THEME:", contentValues.toString());

        db.insert("news", null, contentValues);
        db.close();
    }
}
