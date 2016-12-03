package com.example.osama.malapp;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by osama on 10/21/2016.
 */
public class OneMovie implements Parcelable {
    private String Moviename;
    private String Moviethumbnail;
    private String Movieoverviw;
    private String MovieRating;
    private String Mviedate;
    private String key;
    private String name;
    private String review;
    private int Mid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public OneMovie(String moviekey,String Uname, String Ureview) {
        key = moviekey;
        name = Uname;
        review = Ureview;
    }
    public OneMovie(int id ,String moviename, String moviethumbnail, String movieoverviw, String movieRating, String mviedate) {
        Mid = id;
        Moviename = moviename;
        Moviethumbnail = moviethumbnail;
        Movieoverviw = movieoverviw;
        MovieRating = movieRating;
        Mviedate = mviedate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getMoviename() {
        return Moviename;
    }

    public void setMoviename(String moviename) {
        Moviename = moviename;
    }
    public int getMid() {
        return Mid;
    }

    public void setMid(int id) {
        Mid = id;
    }

    public String getMovieRating() {
        return MovieRating;
    }

    public void setMovieRating(String movieRating) {
        MovieRating = movieRating;
    }

    public String getMovieoverviw() {
        return Movieoverviw;
    }

    public void setMovieoverviw(String movieoverviw) {
        Movieoverviw = movieoverviw;
    }

    public String getMoviethumbnail() {
        return Moviethumbnail;
    }

    public void setMoviethumbnail(String moviethumbnail) {
        Moviethumbnail = moviethumbnail;
    }

    public String getMviedate() {
        return Mviedate;
    }

    public void setMviedate(String mviedate) {
        Mviedate = mviedate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Moviename);
        dest.writeString(Moviethumbnail);
        dest.writeString(Movieoverviw);
        dest.writeString(Mviedate);

    }



}