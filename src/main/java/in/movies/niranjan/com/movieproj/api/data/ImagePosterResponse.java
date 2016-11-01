package in.movies.niranjan.com.movieproj.api.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.movies.niranjan.com.movieproj.models.Image;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class ImagePosterResponse {


    @SerializedName("id")
    public int movieId;

    @SerializedName("backdrops")
    public List<Image> backDrops;

    @SerializedName("posters")
    public List<Image> posters;
}
