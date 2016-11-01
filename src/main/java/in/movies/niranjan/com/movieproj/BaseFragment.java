package in.movies.niranjan.com.movieproj;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

import in.movies.niranjan.com.movieproj.Interface.ActionEventListener;
import in.movies.niranjan.com.movieproj.Interface.FragmentListener;
import in.movies.niranjan.com.movieproj.models.Movie;
import retrofit.RetrofitError;
import rx.functions.Action1;

/**
 * Created by Niranjan on 12/29/2015.
 */
public class BaseFragment extends android.support.v4.app.Fragment implements ActionEventListener {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private FragmentListener mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivity = (FragmentListener) context;
            mActivity.setFragment(this);
        }
        catch (ClassCastException e) {
            Log.e(TAG, "ClassCastException: ", e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (mActivity != null) {
            mActivity.setFragment(null);
        }

        mActivity = null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    protected void launchFragment(Fragment fragment, String tag) {
        if (mActivity != null) {
            mActivity.navigateToFragment(fragment, tag);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resource) {
        showToast(getString(resource));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
    }

    public Movie getMovieByIdFromDB(int movieId) {
        List<Movie> movies = new Select().from(Movie.class).where("movie_id = ?", movieId).execute();
        if(movies.size() > 0)
            return movies.get(0);
        else
            return null;
    }
    public List<Movie> fetchFavoriteMovies() {
        return new Select().from(Movie.class).where("is_favorite = ?", true).execute();
    }
    public List<Movie> fetchWatchedMovies() {
         return new Select().from(Movie.class).where("is_watchlist = ?", true).execute();
    }

    protected void showProgressBar() {
        if (mActivity != null) {
            mActivity.showProgressBar();
        }
    }
    protected void showProgressBarWithBackground() {
        if (mActivity != null) {
            mActivity.showProgressBarWithBackground();
        }
    }
    protected void hideProgressBar() {
        if (mActivity != null) {
            mActivity.hideProgressBar();
        }
    }


    protected Action1<Throwable> errorHandler = new Action1<Throwable>() {

        @Override
        public void call(Throwable throwable) {
            RetrofitError error = (RetrofitError) throwable;
            hideProgressBar();
            Log.d("ERROR", "Base Fragment  " + error.getMessage() + " : " + error.getKind());
            switch (error.getKind()) {
                case NETWORK:
                    if (mActivity != null) mActivity.showNetworkDialog();
                    break;

                default:
                    showToast(R.string.generic_error);
                    error.printStackTrace();
            }
        }
    };
}
