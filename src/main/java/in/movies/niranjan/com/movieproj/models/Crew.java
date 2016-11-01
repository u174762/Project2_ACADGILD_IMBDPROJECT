package in.movies.niranjan.com.movieproj.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class Crew {

    @SerializedName("id")
    public int movieId;

    @SerializedName("name")
    public String actorName;

    @SerializedName("job")
    public String job;

    @SerializedName("profile_path")
    public String profilePath;

   /* @SerializedName("department")
    public String department;

    @SerializedName("credit_id")
    public String creditId;*/
}
