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
    static final int REVIEW_LIST = 2;
    static final int MOVIE_LIS = 1;
    int listnum ;

    public CustomAdapter (Context mContext, ArrayList<OneMovie> movies , int Whichlist) {
        Rcontext = mContext;
        listnum = Whichlist;
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
        if (listnum == MOVIE_LIS){
        if (row == null){
            LayoutInflater layoutInflater = (LayoutInflater) Rcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_item,parent,false);
            mholder= new holder(row);
            row.setTag(mholder);

        }else {
           mholder= (holder) row.getTag();
        }
        
        Picasso.with(Rcontext).load("http://image.tmdb.org/t/p/w342"+ mymovie.getMoviethumbnail()).into(mholder.Iv);}

        else if (listnum == REVIEW_LIST){
            if (row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) Rcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.rev_item, parent, false);
                mholder= new holder(row);
                row.setTag(mholder);
            }else {
                mholder= (holder) row.getTag();
            }

            mholder.Name.setText(mymovie.getName());
            mholder.rev.setText(mymovie.getReview());


        }
        return row;
    }
    public class holder {
        ImageView Iv;
        TextView Name;
        TextView rev;
        public holder (View view) {
            Iv = (ImageView)view.findViewById(R.id.movie_image);
            Name = (TextView)view.findViewById(R.id.name);
            rev = (TextView)view.findViewById(R.id.review);

        }
        }
}
