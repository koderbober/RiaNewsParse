package com.ghostofchaos.rianewsparse.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ghostofchaos.rianewsparse.DBOpenHelper;
import com.ghostofchaos.rianewsparse.MainActivity;
import com.ghostofchaos.rianewsparse.News;
import com.ghostofchaos.rianewsparse.NewsActivity;
import com.ghostofchaos.rianewsparse.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ghost on 14.11.2015.
 */

public class Fragment10 extends Fragment {

    public ArrayList<News> newsList = new ArrayList<>();
    private SimpleAdapter adapter;
    private ListView listViewNews;
    private ArrayList<Map<String, String>> data;
    private DBOpenHelper dbOpenHelper;

    public Document doc = null;
    public Elements elementsTitle = null;
    public Elements elementsAnnounce = null;
    public Elements elementsData = null;
    public Elements elementsText = null;
    public Elements linkOnElementsDescription = null;

    final String ATTRIBUTE_TITLE = "title";
    final String ATTRIBUTE_ANNOUNCE = "announce";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);

        listViewNews = (ListView) rootView.findViewById(R.id.list_view_item);

        if (MainActivity.checkInternetConnection(getContext())) {
            parse();
        } else {
            getNewsFromDB();
        }

        data = new ArrayList<>();
        for (News each : newsList) {
            Map<String, String> map = new HashMap<>();
            map.put(ATTRIBUTE_TITLE, each.getTitle());
            map.put(ATTRIBUTE_ANNOUNCE, each.getAnnounce());
            data.add(map);
        }

        String[] from = {ATTRIBUTE_TITLE, ATTRIBUTE_ANNOUNCE};
        int[] to = {R.id.textViewTitle, R.id.textViewAnnounce};

        adapter = new SimpleAdapter(getActivity(), data, R.layout.news_list_item, from, to);
        listViewNews.setAdapter(adapter);

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsList.get(position);
                Intent intent = new Intent(Fragment10.this.getActivity(), NewsActivity.class);
                intent.putExtra("NEWS", news);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void getNewsFromDB() {
        dbOpenHelper = new DBOpenHelper(getContext());
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from news where id_theme = ?", new String[]{"ten"});
        newsList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    News news = new News();

                    news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    news.setAnnounce(cursor.getString(cursor.getColumnIndex("announce")));
                    news.setData(cursor.getString(cursor.getColumnIndex("data")));
                    news.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    news.setId_theme(cursor.getString(cursor.getColumnIndex("id_theme")));

                    newsList.add(news);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
    }

    public void parse() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            doc = Jsoup.connect("http://ria.ru/radio/").get();
            elementsText = doc.select(".list_item_text");
            elementsTitle = doc.select(".list_item_title");
            elementsAnnounce = doc.select(".list_item_announce");
            elementsData = doc.select(".list_item_date");
            linkOnElementsDescription = doc.select(".list_item_title a");
            for (int i = 0; i < elementsTitle.size(); i++) {
                News news = new News();

                news.setTitle(elementsTitle.get(i).text());
                news.setAnnounce(elementsAnnounce.get(i).text());
                String linkOnElementsDescriptionString = linkOnElementsDescription.get(i).attr("href");
                news.setDescriptionLink(linkOnElementsDescriptionString);
                news.setId_theme("ten");

                newsList.add(news);
            }

            int elementsDataID = 0;
            for (int i = 0; i < elementsText.size(); i++) {
                String elementsTextString = elementsText.get(i).text();
                if (elementsTextString.contains(elementsData.get(elementsDataID).text())) {
                    News news;
                    news = newsList.get(i);
                    news.setData(elementsData.get(elementsDataID).text());
                    newsList.set(i, news);
                    elementsDataID++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}