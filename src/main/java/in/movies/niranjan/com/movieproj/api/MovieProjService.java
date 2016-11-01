package in.movies.niranjan.com.movieproj.api;

import in.movies.niranjan.com.movieproj.api.data.CastCrewResponse;
import in.movies.niranjan.com.movieproj.api.data.ImagePosterResponse;
import in.movies.niranjan.com.movieproj.api.data.MoviesResponse;
import in.movies.niranjan.com.movieproj.api.data.VideoResponse;
import in.movies.niranjan.com.movieproj.models.MovieDetail;
import in.movies.niranjan.com.movieproj.models.Session;
import in.movies.niranjan.com.movieproj.utils.AppConstants;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Niranjan on 12/29/2015.
 */
public interface MovieProjService {

   // Get the latest movie in details.
    @GET("/movie/latest?api_key="+ AppConstants.API_KEY)
    Observable<MovieDetail> getLatestMovie();

    //Get the list of upcoming movies. This list refreshes every day. The maximum number of items this list will include is 100.
    @GET("/movie/upcoming?api_key="+ AppConstants.API_KEY)
    Observable<MoviesResponse> getUpComingMovies();

    //Get the list of movies playing in theatres.
    // This list refreshes every day. The maximum number of items this list will include is 100.
    @GET("/movie/now_playing?api_key="+ AppConstants.API_KEY)
    Observable<MoviesResponse> getNowPlayingMovies();

    //Get the list of popular movies on The Movie Database. This list refreshes every day.
    @GET("/movie/popular?api_key="+ AppConstants.API_KEY)
    Observable<MoviesResponse> getPopularMovies();

    // no api to get latest movies so using popular movies api
    @GET("/movie/popular?api_key="+ AppConstants.API_KEY)
    Observable<MoviesResponse> getLatestMovies();

   /* Get the list of top rated movies.
      By default, this list will only include movies that have 10 or more votes.
      This list refreshes every day.*/
    @GET("/movie/top_rated?api_key="+ AppConstants.API_KEY)
    Observable<MoviesResponse> getTopRatedMovies();

    //Get the details of a movie as per id
    @GET("/movie/{id}?api_key="+ AppConstants.API_KEY)
    Observable<MovieDetail> getMovieDetailsById(@Path("id") int movieId);

    //Get the cast and crew information for a specific movie id.
    @GET("/movie/{id}/credits?api_key="+ AppConstants.API_KEY)
    Observable<CastCrewResponse> getCastAndCrewInfoById(@Path("id") int imdbId);

    @GET("/authentication/session/new?api_key="+ AppConstants.API_KEY)
    Observable<Session> getNewSessionInfo();

    //This method lets users rate a movie. A valid session id or guest session id is required.
    @POST("/movie/{id}/rating")
    void rateTheMovie(@Path("id") int sessionId);

    @GET("/guest_session/{guest_session_id}/rated_movies?api_key="+ AppConstants.API_KEY)
    Observable<MoviesResponse> getRatedMoviesBySessionId(@Path("guest_session_id") int sessionId);

    //Get the images, posters for a specific movie id.
    @GET("/movie/{id}/images?api_key="+ AppConstants.API_KEY)
    Observable<ImagePosterResponse> getImagesAndPostersByMovieId(@Path("id") int movieId);

    //Get the videos (trailers, teasers, clips, etc...) for a specific movie id.
    @GET("/movie/{id}/videos?api_key="+ AppConstants.API_KEY)
    Observable<VideoResponse> getVideosByMovieId(@Path("id") int movieId);

}
