package in.movies.niranjan.com.movieproj.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Niranjan on 12/30/2015.
 */
@Table(name = "Movies")
public class Movie extends Model {

    @SerializedName("adult")
    public boolean isAdult;

    @SerializedName("genre_ids")
    public List<Integer> genreIds;

    @Column(name = "movie_id", unique = true)
    @SerializedName("id")
    public int movieId;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("overview")
    public String overview;

    @Column(name = "popularity")
    @SerializedName("popularity")
    public float popularity;

    @Column(name = "poster_path")
    @SerializedName("poster_path")
    public String posterPath;

    @Column(name = "release_date")
    @SerializedName("release_date")
    public String releaseDate;

    @Column(name = "title")
    @SerializedName("title")
    public String title;

    @SerializedName("video")
    public boolean isVideoPresent;

    @Column(name = "vote_average")
    @SerializedName("vote_average")
    public Double voteAverage;

    @Column(name = "vote_count")
    @SerializedName("vote_count")
    public int voteCount;

    @Column(name = "backdrop_pathe")
    @SerializedName("backdrop_path")
    public String backdropPath;

    @Column(name = "is_favorite", index = true)
    public boolean isFavorite;

    @Column(name = "is_watchlist", index = true)
    public boolean isWatchlist;

    public Movie() {
        super();
    }

    public Movie(int movieId, float popularity, String posterPath, String releaseDate,
                 String title, Double voteAverage, int voteCount, String backdropPath, boolean isFavorite, boolean isWatchlist) {
        this.movieId = movieId;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
        this.isWatchlist = isWatchlist;
        this.isFavorite = isFavorite;
    }
}
