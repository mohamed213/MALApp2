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
    private int MovieRating;
    private String Mviedate;

    public OneMovie(String moviename, String moviethumbnail, String movieoverviw, int movieRating, String mviedate) {
        Moviename = moviename;
        Moviethumbnail = moviethumbnail;
        Movieoverviw = movieoverviw;
        MovieRating = movieRating;
        Mviedate = mviedate;
    }
    protected OneMovie (Parcel in) {
        Moviename = in.readString();
        Moviethumbnail = in.readString();
        Movieoverviw = in.readString();
        Mviedate = in.readString();
        MovieRating = in.readInt();

    }

    public String getMoviename() {
        return Moviename;
    }

    public void setMoviename(String moviename) {
        Moviename = moviename;
    }

    public int getMovieRating() {
        return MovieRating;
    }

    public void setMovieRating(int movieRating) {
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
        dest.writeInt(MovieRating);

    }
    public static final Parcelable.Creator<OneMovie> CREATOR = new Parcelable.Creator<OneMovie>() {
        @Override
        public OneMovie createFromParcel(Parcel in) {
            return new OneMovie(in);
        }

        @Override
        public OneMovie[] newArray(int size) {
            return new OneMovie[size];
        }
    };


}