package com.example.osama.malapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

/**
 * A placeholder fragment containing a simple view.
 */

public class MainActivityFragment extends Fragment {

    public String URL;
    public MainActivityFragment() {
        setHasOptionsMenu(true);

    }

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
        if (id == R.id.top) {
            executeAsync("http://api.themoviedb.org/3/movie/top_rated?api_key=9de3b86bfd5a7782d572cb1abc585ab5");

        }


        return super.onOptionsItemSelected(item);
    }

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
                int Rating = obj.getInt("vote_average");
                String date = obj.getString("release_date");

                movies.add(new OneMovie(title,thumbnil,overview,Rating,date));
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
        final CustomAdapter Myadapter = new CustomAdapter(mcontext,movies);
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
                mcontext.startActivity(intent);


            }
        });

    }}
