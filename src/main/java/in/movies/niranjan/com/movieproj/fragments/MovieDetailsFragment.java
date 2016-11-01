package in.movies.niranjan.com.movieproj.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.movies.niranjan.com.movieproj.BaseFragment;
import in.movies.niranjan.com.movieproj.R;
import in.movies.niranjan.com.movieproj.api.MovieProjApi;
import in.movies.niranjan.com.movieproj.api.data.CastCrewResponse;
import in.movies.niranjan.com.movieproj.api.data.ImagePosterResponse;
import in.movies.niranjan.com.movieproj.api.data.VideoResponse;
import in.movies.niranjan.com.movieproj.models.Cast;
import in.movies.niranjan.com.movieproj.models.Crew;
import in.movies.niranjan.com.movieproj.models.Image;
import in.movies.niranjan.com.movieproj.models.Movie;
import in.movies.niranjan.com.movieproj.models.MovieDetail;
import in.movies.niranjan.com.movieproj.models.Video;
import in.movies.niranjan.com.movieproj.utils.AppConstants;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class MovieDetailsFragment extends BaseFragment {
    private int movieId;
    ImageView moviePoster, myFavorite, myWatchlist;
    TextView title, tagLine, releaseDate, budget, revenue, status, voteAverage, voteCount, overView;
    RatingBar ratingBar;
    Movie movie;
    LinearLayout postersGallery, castGallery, crewGallery, trailersGallery;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieId = getArguments().getInt(AppConstants.MOVIE_ID);
        init();

        fetchMovieDetails();
        fetchPosters();
        fetchCastCrewDetails();
        fetchTrailersAndVideos();

    }

    private void fetchTrailersAndVideos() {
        showProgressBar();
        MovieProjApi.getService().getVideosByMovieId(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoResponse>() {
                    @Override
                    public void call(VideoResponse videoResponse) {
                        hideProgressBar();
                        updateVideos(videoResponse.videos);
                    }
                }, errorHandler);
    }

    private void updateVideos(List<Video> videos) {
        boolean firstTime = true;
        for(Video video:videos) {
            if(!firstTime)
                trailersGallery.addView(addImage());
            firstTime = false;
            trailersGallery.addView(getTextView(video));

        }
    }

    private TextView getTextView(final Video video) {
        TextView textView = new TextView(getActivity());
        textView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Small);
        textView.setText(video.name);
        textView.setPadding(5, 5, 5, 5);
        textView.setTextColor(getResources().getColor(R.color.blue));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.key));
                    startActivity(intent);
                }catch (ActivityNotFoundException ex){
                    Intent intent=new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v="+video.key));
                    startActivity(intent);
                }
            }
        });
        return textView;
    }

    private ImageView addImage() {
        ImageView imageView = new ImageView(getActivity());
        imageView.setPadding(10,0,10,5);
        imageView.setImageResource(R.mipmap.ic_divider);
        return imageView;
    }
    private void fetchCastCrewDetails() {
        MovieProjApi.getService().getCastAndCrewInfoById(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CastCrewResponse>() {
                    @Override
                    public void call(CastCrewResponse castCrewResponse) {
                        updateCast(castCrewResponse.cast);
                        updateCrew(castCrewResponse.crew);
                    }
                }, errorHandler);
    }

    private void updateCrew(List<Crew> crews) {
        boolean firstTime = true;
        int i = 0;
        for(Crew crew:crews) {
            i++;
            if(crew.profilePath != null) {
                crewGallery.addView(getLayoutWithImageAndDetails(crew.profilePath, crew.actorName, crew.job, firstTime));
                firstTime = false;
            }
            else
                i--;
            if(i > 10)
                break;
        }
    }

    private void updateCast(List<Cast> casts) {
        boolean firstTime = true;
        int i = 0;
        for(Cast cast:casts) {
            i++;
            if(cast.profilePath != null) {
                castGallery.addView(getLayoutWithImageAndDetails(cast.profilePath, cast.actorName, cast.character, firstTime));
                firstTime = false;
            }
            else
                i--;
            if(i > 10)
                break;
        }
    }

    private void fetchPosters() {
        showProgressBar();
        MovieProjApi.getService().getImagesAndPostersByMovieId(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ImagePosterResponse>() {
                    @Override
                    public void call(ImagePosterResponse imagePosterResponse) {
                        updatePosters(imagePosterResponse.posters);
                    }
                }, errorHandler);
    }

    private void updatePosters(List<Image> posters) {
        boolean firstTime = true;
        for(int i = 0; i < 10; i++) {
            Image poster = posters.get(i);
            postersGallery.addView(getLayoutWithImageAndDetails(poster.filePath, null, null, firstTime));
            firstTime = false;
        }
    }

    private RelativeLayout getLayoutWithImageAndDetails(String path, String name, String character, boolean firstTime) {
        View relativeLayout = LayoutInflater.from(getActivity()).inflate(R.layout.item_image_list, null);
        ImageView imageView1 = (ImageView) relativeLayout.findViewById(R.id.divider);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.image);
        TextView textView1 = (TextView) relativeLayout.findViewById(R.id.textView1);
        TextView textView2 = (TextView) relativeLayout.findViewById(R.id.textView2);
        if(path != null)
            Picasso.with(getActivity()).load(AppConstants.IMAGE_BASE_URL + path).into(imageView);
        if(name != null) {
            textView1.setVisibility(View.VISIBLE);
            textView1.setText(name);
        } else {
            textView1.setVisibility(View.GONE);
        }
        if(character != null) {
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(character);
        } else {
            textView2.setVisibility(View.GONE);
        }
        if(firstTime)
            imageView1.setVisibility(View.GONE);
        else
            imageView1.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
     return (RelativeLayout)relativeLayout;
    }

    private void init() {
        View view = getView();
        title = (TextView) view.findViewById(R.id.title);
        tagLine = (TextView) view.findViewById(R.id.tag_line);
        releaseDate = (TextView) view.findViewById(R.id.release_date);
        budget = (TextView) view.findViewById(R.id.budget);
        revenue = (TextView) view.findViewById(R.id.revenue);
        status = (TextView) view.findViewById(R.id.status);
        voteAverage = (TextView) view.findViewById(R.id.vote_average);
        voteCount = (TextView) view.findViewById(R.id.vote_num);
        overView = (TextView) view.findViewById(R.id.over_view);

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
        myFavorite = (ImageView) view.findViewById(R.id.favorite);
        myWatchlist = (ImageView) view.findViewById(R.id.watch_list);

        postersGallery = (LinearLayout) view.findViewById(R.id.posters_gallery);
        trailersGallery = (LinearLayout) view.findViewById(R.id.trailers_gallery);
        castGallery = (LinearLayout) view.findViewById(R.id.casts_gallery);
        crewGallery = (LinearLayout) view.findViewById(R.id.crew_gallery);

    }

    private void fetchMovieDetails() {
            showProgressBarWithBackground();

            MovieProjApi.getService().getMovieDetailsById(movieId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<MovieDetail>() {
                        @Override
                        public void call(MovieDetail movieDetail) {
                            hideProgressBar();
                            updateContent(movieDetail);
                        }
                    }, errorHandler);

    }

    private void updateContent(final MovieDetail movieDetail) {
        title.setText(movieDetail.title);
        tagLine.setText(movieDetail.tagLine);
        releaseDate.setText(movieDetail.releaseDate);
        budget.setText("Revenue : " + movieDetail.budget);
        revenue.setText("Budget : " + movieDetail.revenue);
        status.setText("Status : " + movieDetail.status);
        overView.setText(movieDetail.overview);
        voteAverage.setText(" (" + movieDetail.voteAverage + "/10) ");
        voteCount.setText(movieDetail.voteCount + " users");
        Picasso.with(getActivity()).load(AppConstants.IMAGE_BASE_URL + movieDetail.posterPath).into(moviePoster);
        ratingBar.setRating(movieDetail.popularity);
        movie = getMovieByIdFromDB(movieDetail.movieId);
        if(movie == null)
            movie = new Movie(movieDetail.movieId, movieDetail.popularity, movieDetail.posterPath,
                movieDetail.releaseDate,movieDetail.title, movieDetail.voteAverage,
                movieDetail.voteCount, movieDetail.backdropPath, false, false);

        myFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyFavorite();
                setMyFavoriteImage(myFavorite);
            }
        });

        myWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyWatchlist();
                setMyWatchlistImage(myWatchlist);
            }
        });

        setMyWatchlistImage(myWatchlist);
        setMyFavoriteImage(myFavorite);

    }

    public void setMyFavoriteImage(ImageView myFavorite) {
        if(movie.isFavorite)
           myFavorite.setImageResource(R.mipmap.favorite_enable_normal);
        else
            myFavorite.setImageResource(R.mipmap.favorite_disable_normal);
    }

    public void setMyWatchlistImage(ImageView myWatchlist) {
        if(movie.isWatchlist)
            myWatchlist.setImageResource(R.mipmap.watchlist_enable_normal);
        else
            myWatchlist.setImageResource(R.mipmap.watchlist_disable_normal);
    }

    public void setMyFavorite() {
        if(movie != null) {
            movie.isFavorite = !movie.isFavorite;
            movie.save();
        }
    }
    public void setMyWatchlist() {
        if(movie != null) {
            movie.isWatchlist = !movie.isWatchlist;
            movie.save();
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Show from database
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        Bundle bundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.my_favorite:
                bundle.putInt("movies_list", 0);
                break;
            case R.id.watch_list:
                bundle.putInt("movies_list", 1);
                break;

        }
        moviesListFragment.setArguments(bundle);
        launchFragment(moviesListFragment, moviesListFragment.getTag());
        return super.onOptionsItemSelected(item);
    }
}
