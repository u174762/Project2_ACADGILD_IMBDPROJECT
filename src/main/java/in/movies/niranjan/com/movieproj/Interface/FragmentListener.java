package in.movies.niranjan.com.movieproj.Interface;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by Niranjan on 12/29/2015.
 */
public interface FragmentListener {
    void setFragment(ActionEventListener fragment);

    void navigateToFragment(Fragment fragment, String tag);

    void navigateToFragment(Fragment fragment, String tag, boolean noHistory);

    void navigateToFragment(Fragment fragment, String tag, boolean noHistory, boolean clearStack);

    void showProgressBar();

    void showProgressBarWithBackground();

    void hideProgressBar();

    void showNetworkDialog();

    void hideSoftKeyboard(Activity activity);

    void setProfileInfo();
}
