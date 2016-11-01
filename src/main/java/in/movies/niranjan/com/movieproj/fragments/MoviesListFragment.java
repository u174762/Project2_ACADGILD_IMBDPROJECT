package in.movies.niranjan.com.movieproj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.movies.niranjan.com.movieproj.BaseFragment;
import in.movies.niranjan.com.movieproj.Interface.RecyclerItemClickListener;
import in.movies.niranjan.com.movieproj.R;
import in.movies.niranjan.com.movieproj.adapters.MovieListAdapter;
import in.movies.niranjan.com.movieproj.api.MovieProjApi;
import in.movies.niranjan.com.movieproj.api.MovieProjService;
import in.movies.niranjan.com.movieproj.api.data.MoviesResponse;
import in.movies.niranjan.com.movieproj.models.Movie;
import in.movies.niranjan.com.movieproj.utils.AppConstants;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class MoviesListFragment extends BaseFragment implements RecyclerItemClickListener{

    RecyclerView movieListView;
    MovieListAdapter adapter;
    TextView noItemsFound;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_list, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieListView = (RecyclerView) getView().findViewById(R.id.moviesListView);
        movieListView.setHasFixedSize(true);
        movieListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        noItemsFound = (TextView) getView().findViewById(R.id.no_items);
        if(getArguments() != null) {
            if(getArguments().getInt("movies_list") == 0)
                buildMoviesList(fetchFavoriteMovies());
            else
                buildMoviesList(fetchWatchedMovies());
        } else {
            fetchMoviesList(getMovieProjService().getPopularMovies());
        }

    }

    private void fetchMoviesList(Observable<MoviesResponse> moviesResponseObservable) {
        showProgressBar();

        moviesResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MoviesResponse>() {
                    @Override
                    public void call(MoviesResponse moviesResponse) {
                        hideProgressBar();
                        buildMoviesList(moviesResponse.movies);
                    }
                }, errorHandler);

    }

    private void buildMoviesList(List<Movie> movies) {
        adapter = (MovieListAdapter) movieListView.getAdapter();
        noItemsFound.setVisibility(View.GONE);
        movieListView.setVisibility(View.VISIBLE);
        if (adapter != null && movies.size() > 0) {
            adapter.updateMovieList(movies);
        } else if (movies.size() > 0) {
            adapter = new MovieListAdapter(movies, this, getActivity());
            movieListView.setAdapter(adapter);
        } else {
            noItemsFound.setVisibility(View.VISIBLE);
            movieListView.setVisibility(View.GONE);
        }
    }

    private MovieProjService getMovieProjService() {
        return MovieProjApi.getService();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
            case R.id.popular:
                fetchMoviesList(getMovieProjService().getPopularMovies());
                break;
            case R.id.upcoming:
                fetchMoviesList(getMovieProjService().getUpComingMovies());
                break;
            case R.id.now_playing:
                fetchMoviesList(getMovieProjService().getNowPlayingMovies());
                break;
            case R.id.latest:
                fetchMoviesList(getMovieProjService().getLatestMovies());
                break;
            case R.id.top_rated:
                fetchMoviesList(getMovieProjService().getTopRatedMovies());
                break;
            case R.id.my_favorite:
                buildMoviesList(fetchFavoriteMovies());
                break;
            case R.id.watch_list:
                buildMoviesList(fetchWatchedMovies());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.MOVIE_ID, movie.movieId);
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(bundle);
        launchFragment(movieDetailsFragment, movieDetailsFragment.getTag());
    }
}
