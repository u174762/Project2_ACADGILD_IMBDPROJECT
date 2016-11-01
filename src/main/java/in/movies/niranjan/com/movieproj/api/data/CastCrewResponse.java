package in.movies.niranjan.com.movieproj.api.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.movies.niranjan.com.movieproj.models.Cast;
import in.movies.niranjan.com.movieproj.models.Crew;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class CastCrewResponse {

    @SerializedName("id")
    public int movieId;

    @SerializedName("cast")
    public List<Cast> cast;

    @SerializedName("crew")
    public List<Crew> crew;
}
