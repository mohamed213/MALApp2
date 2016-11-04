package com.example.osama.malapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osama on 10/21/2016.
 */
public class CustomAdapter extends BaseAdapter {
    Context Rcontext;
    ArrayList<OneMovie> mylist = new ArrayList<>();

    public CustomAdapter(Context mContext, ArrayList<OneMovie> movies) {
        Rcontext = mContext;
        mylist = movies;
    }


    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        holder mholder;
        row= convertView;
        final OneMovie mymovie = (OneMovie) getItem(position);
        if (row == null){
            LayoutInflater layoutInflater = (LayoutInflater) Rcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_item,parent,false);
            mholder= new holder(row);
            row.setTag(mholder);

        }else {
           mholder= (holder) row.getTag();
        }
        
        Picasso.with(Rcontext).load("http://image.tmdb.org/t/p/w342"+ mymovie.getMoviethumbnail()).into(mholder.Iv);

        return row;
    }
    public class holder {
        ImageView Iv;
        public holder (View view) {
         Iv = (ImageView)view.findViewById(R.id.movie_image);
        }
        }
}
