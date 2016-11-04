package com.example.osama.malapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
    OneMovie movie ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView thumnnail = (ImageView)findViewById(R.id.thumb);
        TextView title = (TextView)findViewById(R.id.title);
        TextView overview = (TextView)findViewById(R.id.overview);
        TextView rating = (TextView)findViewById(R.id.rating);
        TextView date = (TextView)findViewById(R.id.date);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w342" + getIntent().getExtras().getString("image")).into(thumnnail);
        title.setText(getIntent().getExtras().getString("moviename"));
        overview.setText(getIntent().getExtras().getString("overview"));
        rating.setText(getIntent().getExtras().getString("rate"));
        date.setText((getIntent().getExtras().getString("date")));


    }

}
