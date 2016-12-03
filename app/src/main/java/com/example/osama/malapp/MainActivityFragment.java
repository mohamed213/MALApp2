package com.example.osama.malapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import com.example.osama.malapp.MovieContract.Movieelement;
/**
 * A placeholder fragment containing a simple view.
 */

public class MainActivityFragment extends Fragment {

    public String URL;
    SQLiteDatabase db;
    MovieDbhelper dbhelper;
    CustomAdapter Myadapter;
    GridView gridView;
    public static final int FAV_BUTTON = 1;
    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    ArrayList<OneMovie> Fmovies = new ArrayList<>();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.sorting, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.poplur){
            executeAsync("http://api.themoviedb.org/3/movie/popular?api_key=9de3b86bfd5a7782d572cb1abc585ab5");
        }
        if (id == R.id.action_settings){
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.refresh){

        }
        if (id == R.id.map){
            openpreferedmap();
        }
        if (id == R.id.fav){
//            dbhelper = new MovieDbhelper(this.getContext());
//            db = dbhelper.getWritableDatabase();
//            String query = "SELECT * FROM " + Movieelement.TABLE_NAME;
//            Cursor mycursor = db.rawQuery(query, null);
//            try {
//                if (mycursor.moveToFirst()) {
//                    do {
//                        Fmovies.add(new OneMovie(mycursor.getInt(0),mycursor.getString(1), mycursor.getString(2),mycursor.getString(3)
//                                , mycursor.getString(4),mycursor.getString(5)));
//                    } while (mycursor.moveToNext());
//                }
//            }finally {
//                if (db != null) {
//                    db.delete(Movieelement.TABLE_NAME, null, null);
////                    db.execSQL("DELETE FROM " + Movieelement.TABLE_NAME);
//                    db.close();
//                }

            Readdata();
            Myadapter = new CustomAdapter(getActivity(),Fmovies,1);
            gridView =(GridView)getActivity().findViewById(R.id.grid_movies);
            gridView.setAdapter(Myadapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    OneMovie moviehaschosed = Fmovies.get(position);
                    Intent intent = new Intent(getActivity(), Main2Activity.class);
                    intent.putExtra("image", moviehaschosed.getMoviethumbnail());
                    intent.putExtra("moviename", moviehaschosed.getMoviename());
                    intent.putExtra("overview", moviehaschosed.getMovieoverviw());
                    intent.putExtra("rate", moviehaschosed.getMovieRating());
                    intent.putExtra("date", moviehaschosed.getMviedate());
                    intent.putExtra("Mid", moviehaschosed.getMid());
                    intent.putExtra("favbtn", FAV_BUTTON);
                    getActivity().startActivity(intent);
                }
            });

        }



        if (id == R.id.top) {
            executeAsync("http://api.themoviedb.org/3/movie/top_rated?api_key=9de3b86bfd5a7782d572cb1abc585ab5");

        }


        return super.onOptionsItemSelected(item);
    }

    private void openpreferedmap() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString("location", "94043");
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",location).build();
        Intent intent =new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        startActivity(intent);
    }
    public void Readdata () {
        dbhelper = new MovieDbhelper(this.getContext());
        db = dbhelper.getReadableDatabase();
        String query = "SELECT * FROM " + Movieelement.TABLE_NAME;
        Cursor mycursor = db.rawQuery(query, null);
        try {
            if (mycursor.moveToFirst()) {
                do {
                    Fmovies.add(new OneMovie(mycursor.getInt(0),mycursor.getString(1), mycursor.getString(2),mycursor.getString(3)
                            , mycursor.getString(4),mycursor.getString(5)));
                } while (mycursor.moveToNext());
            }
        }finally {
//            if (db != null) {
             db.delete(Movieelement.TABLE_NAME, null, null);
//                    db.execSQL("DELETE FROM " + Movieelement.TABLE_NAME);
                db.close();
//            }

    }}
    //    public ArrayList<OneMovie> movies = new ArrayList<>();
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootview = inflater.inflate(R.layout.fragment_main, container, false);
        executeAsync("http://api.themoviedb.org/3/movie/top_rated?api_key=9de3b86bfd5a7782d572cb1abc585ab5");


        return rootview;


    }
    public void executeAsync (String url){
        background h = new background(getActivity(),rootview);
        h.execute(url);
    }

    }


class background extends AsyncTask<String,Void,ArrayList<OneMovie>> {
    Context mcontext;
    View Ownview;

    public background(Context C, View rootview) {
        mcontext = C;
        Ownview = rootview;
    }
    ArrayList<OneMovie> movies = new ArrayList<>();
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
            JSONArray results = Jsonobject.getJSONArray("results");
            for (int i=0 ; i<results.length(); i++){
                JSONObject obj = results.getJSONObject(i);
                String title = obj.getString("original_title");
                String thumbnil = obj.getString("poster_path");
                String overview = obj.getString("overview");
                String Rating = obj.getString("vote_average");
                String date = obj.getString("release_date");
                int Id = obj.getInt("id");

                movies.add(new OneMovie(Id,title,thumbnil,overview,Rating,date));
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    protected void onPostExecute(final ArrayList<OneMovie> movies) {
        super.onPostExecute(movies);
        final CustomAdapter Myadapter = new CustomAdapter(mcontext,movies,1);
        GridView gridView =(GridView)Ownview.findViewById(R.id.grid_movies);
        gridView.setAdapter(Myadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OneMovie moviehaschosed = movies.get(position);
                Intent intent= new Intent(mcontext,Main2Activity.class);
                intent.putExtra("image", moviehaschosed.getMoviethumbnail());
                intent.putExtra("moviename", moviehaschosed.getMoviename());
                intent.putExtra("overview", moviehaschosed.getMovieoverviw());
                intent.putExtra("rate", moviehaschosed.getMovieRating());
                intent.putExtra("date", moviehaschosed.getMviedate());
                intent.putExtra("Id", moviehaschosed.getMid());
                mcontext.startActivity(intent);


            }
        });

    }}
