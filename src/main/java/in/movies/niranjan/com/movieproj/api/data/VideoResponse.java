package in.movies.niranjan.com.movieproj.api.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.movies.niranjan.com.movieproj.models.Video;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class VideoResponse {

    @SerializedName("id")
    public int movieId;

    @SerializedName("results")
    public List<Video> videos;
}
