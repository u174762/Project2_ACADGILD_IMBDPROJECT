package in.movies.niranjan.com.movieproj.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class Cast {

    @SerializedName("character")
    public String character;

    @SerializedName("profile_path")
    public String profilePath;

    @SerializedName("name")
    public String actorName;

    /*@SerializedName("credit_id")
    public String creditId;

    @SerializedName("cast_id")
    public int castId;

    @SerializedName("id")
    public int movieId;

    @SerializedName("order")
    public int popularity;*/

}
