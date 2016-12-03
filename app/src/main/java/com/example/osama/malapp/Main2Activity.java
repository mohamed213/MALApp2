package com.example.osama.malapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osama.malapp.MovieContract.Movieelement;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    OneMovie movie ;
    String moviename = "";
    String movieImage = "";
    String movieOverview = "";
    String movierate = "";
    String moviedate = "";
    int ID  ;
    int favbtn;
    SQLiteDatabase db;
    MovieDbhelper dbhelper;
    View detView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbhelper = new MovieDbhelper(this);
        db = dbhelper.getWritableDatabase();

        ImageView thumnnail = (ImageView)findViewById(R.id.thumb);
        TextView title = (TextView)findViewById(R.id.title);
        TextView overview = (TextView)findViewById(R.id.overview);
        TextView rating = (TextView)findViewById(R.id.rating);
        TextView date = (TextView)findViewById(R.id.date);
        Button favourite = (Button)findViewById(R.id.favourite);
        Button Deletefav = (Button)findViewById(R.id.deletefav);
        Button Opentrailer = (Button)findViewById(R.id.trailer);

        moviename = getIntent().getExtras().getString("moviename");
         movieImage = getIntent().getExtras().getString("image");
         movieOverview = getIntent().getExtras().getString("overview");
         movierate = getIntent().getExtras().getString("rate");
         moviedate = getIntent().getExtras().getString("date");
         favbtn = getIntent().getIntExtra("favbtn", 0);
         ID = getIntent().getExtras().getInt("Id");

        detView = this.findViewById(android.R.id.content).getRootView();
        executeAsync2("http://api.themoviedb.org/3/movie/" + ID + "/reviews?api_key=9de3b86bfd5a7782d572cb1abc585ab5");


        Toast.makeText(this, "movie Id = " + ID, Toast.LENGTH_SHORT).show();
         if (favbtn == 1){
             favourite.setVisibility(View.GONE);
             Deletefav.setVisibility(View.VISIBLE);
         }else {
             favourite.setVisibility(View.VISIBLE);
             Deletefav.setVisibility(View.GONE);
         }

        Opentrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeAsync("http://api.themoviedb.org/3/movie/"+ ID +"/videos?api_key=9de3b86bfd5a7782d572cb1abc585ab5");
            }
        });


//        Cursor cursor = db.query(Movieelement.TABLE_NAME , null, null,null
//        ,null,null,null,null);
        Deletefav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.execSQL("DELETE FROM" + Movieelement.TABLE_NAME);
//            int deleted = deletemovie(ID);
//                if (deleted > 0){
//                    Toast.makeText(Main2Activity.this, "deleted", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(Main2Activity.this, "Not deleted", Toast.LENGTH_SHORT).show();
//                }

                int deleted =   dbhelper.DeleteMovie(ID);
                if (deleted != 0){
                    Toast.makeText(Main2Activity.this, "deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Main2Activity.this, "Not deleted", Toast.LENGTH_SHORT).show();
                }}});

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ContentValues values = new ContentValues();
//                values.put(Movieelement.COLUMN_NAME, moviename);
//                values.put(Movieelement.COLUMN_IMAGE, movieImage);
//                values.put(Movieelement.COLUMN_OVERVIEW, movieOverview);
//                values.put(Movieelement.COLUMN_RATING, movierate);
//                values.put(Movieelement.COLUMN_DATE, moviedate);
//
//                long rowid;
//                rowid = db.insert(Movieelement.TABLE_NAME,null,values);
//                if(rowid == -1){
//                    Toast.makeText(Main2Activity.this,"insertionfailed", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(Main2Activity.this,"Added", Toast.LENGTH_SHORT).show();
//                }
         dbhelper.InsertMovie(moviename, movieImage, movieOverview, movierate, moviedate, Main2Activity.this);
            }});
        Picasso.with(this).load("http://image.tmdb.org/t/p/w342" +  movieImage).into(thumnnail);
        title.setText(moviename);
        overview.setText(movieOverview);
        rating.setText(movierate);
        date.setText(moviedate);


    }
    public void executeAsync (String url){
       background2 exe = new background2(Main2Activity.this, detView);
        exe.execute(url);
    }
    public void executeAsync2 (String url){
        background3 exe = new background3(Main2Activity.this, detView);
        exe.execute(url);
    }
//    public int deletemovie (String id){
//
//            return db.delete(Movieelement.TABLE_NAME, Movieelement.COLUMN_ID + " = ? ", new String[] {id});
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favourite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId() ;
        if (id == R.id.favourite) {

        }
        return super.onOptionsItemSelected(item);
    }
}
class background2 extends AsyncTask<String,Void,ArrayList<OneMovie>>{
    Context mcontext;
    View OurView;

    public background2(Context C , View ViewR) {
        mcontext = C;
        OurView = ViewR;}
        ArrayList<OneMovie> TandR = new ArrayList<>();
    @Override
    protected ArrayList<OneMovie> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String json_string;
            while ((json_string=bufferedReader.readLine())!= null){
                stringBuilder.append(json_string+"\n");
            }
            bufferedReader.close();
            inputStream.close();
            conn.disconnect();
            String Data = stringBuilder.toString().trim();
            JSONObject Jsonobject = new JSONObject(Data);
            JSONArray Results = Jsonobject.getJSONArray("results");
            for (int i = 0; i <Results.length(); i++){
                JSONObject results = Results.getJSONObject(i);
                String key = results.getString("key");
//                String author = results.getString("author");
//                String review = results.getString("content");
                TandR.add(new OneMovie(key,null,null));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();

        }
        return TandR;
    }

    @Override
    protected void onPostExecute(ArrayList<OneMovie> TandRs) {
        super.onPostExecute(TandRs);
     OneMovie trailer = TandRs.get(0);
//        ListView revlists = (ListView)OurView.findViewById(R.id.reviewlist);
//        CustomAdapter adapter = new CustomAdapter(mcontext,TandR,2);
//        revlists.setAdapter(adapter);
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
        mcontext.startActivity(intent);

    }
}
class background3 extends AsyncTask<String,Void,ArrayList<OneMovie>>{
    Context mcontext;
    View OurView;

    public background3(Context C , View ViewR) {
        mcontext = C;
        OurView = ViewR;}
    ArrayList<OneMovie> TandR = new ArrayList<>();
    @Override
    protected ArrayList<OneMovie> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String json_string;
            while ((json_string=bufferedReader.readLine())!= null){
                stringBuilder.append(json_string+"\n");
            }
            bufferedReader.close();
            inputStream.close();
            conn.disconnect();
            String Data = stringBuilder.toString().trim();
            JSONObject Jsonobject = new JSONObject(Data);
            JSONArray Results = Jsonobject.getJSONArray("results");
            for (int i = 0; i <Results.length(); i++){
                JSONObject results = Results.getJSONObject(i);
                String author = results.getString("author");
                String review = results.getString("content");
                TandR.add(new OneMovie(null,author,review));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return TandR;
    }

    @Override
    protected void onPostExecute(ArrayList<OneMovie> TandRs) {
        super.onPostExecute(TandRs);
        CustomAdapter adapter = new CustomAdapter(mcontext,TandR,2);
        ListView revlists = (ListView)OurView.findViewById(R.id.reviewlist);
        revlists.setAdapter(adapter);

    }
}