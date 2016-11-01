package in.movies.niranjan.com.movieproj.api.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.movies.niranjan.com.movieproj.models.Movie;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class MoviesResponse {

    @SerializedName("page")
    public int pageNum;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("dates")
    public Dates dates;

    @SerializedName("results")
    public List<Movie> movies;

    public class Dates {
        @SerializedName("minimum")
        public String minimum;

        @SerializedName("maximum")
        public String maximum;
    }

}
