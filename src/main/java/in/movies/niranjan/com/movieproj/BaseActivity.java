package in.movies.niranjan.com.movieproj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import in.movies.niranjan.com.movieproj.Interface.ActionEventListener;
import in.movies.niranjan.com.movieproj.Interface.FragmentListener;
import retrofit.RetrofitError;
import rx.functions.Action1;

public class BaseActivity extends AppCompatActivity implements FragmentListener {
    private RelativeLayout progressLayout;

    private ActionEventListener mFragment;

    protected void initLayout() {
        // set up progress layout
        progressLayout = (RelativeLayout) findViewById(R.id.progressLayout);

        if (progressLayout != null) {
            progressLayout.setOnClickListener(null);
        }
    }

    @Override
    public void setFragment(ActionEventListener fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void navigateToFragment(Fragment fragment, String tag) {
        navigateToFragment(fragment, tag, false, false);
    }

    @Override
    public void navigateToFragment(Fragment fragment, String tag, boolean noHistory) {
        navigateToFragment(fragment, tag, noHistory, false);
    }

    @Override
    public void navigateToFragment(Fragment fragment, String tag, boolean noHistory, boolean clearStack) {
        FragmentTransaction beginTransaction = getSupportFragmentManager()
                .beginTransaction();

        if (clearStack) {
            getSupportFragmentManager().popBackStack(null, FragmentManager
                    .POP_BACK_STACK_INCLUSIVE);
        }

        if (!noHistory) {
            beginTransaction.addToBackStack(tag);
        }

        beginTransaction.replace(R.id.container, fragment, tag);
        beginTransaction.commit();

        invalidateOptionsMenu();
    }

    @Override
    public void showProgressBar() {

        if (progressLayout != null) {
            progressLayout.setVisibility(View.VISIBLE);
            progressLayout.setBackgroundColor(getResources().getColor(R.color.transperant));
        }

    }
    @Override
    public void showProgressBarWithBackground() {
        if (progressLayout != null) {
            progressLayout.setVisibility(View.VISIBLE);
            progressLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
    @Override
    public void hideProgressBar() {
        if (progressLayout != null)
            progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resource) {
        showToast(getString(resource));
    }

    @Override
    public void showNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.error_internet_conn)
                .setTitle(R.string.netwrok_error)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        startActivityForResult(settings, 0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
    }

    @Override
    public void setProfileInfo() {
    }

    protected Action1<Throwable> errorHandler = new Action1<Throwable>() {

        @Override
        public void call(Throwable throwable) {
            RetrofitError error = (RetrofitError) throwable;
            hideProgressBar();
            switch (error.getKind()) {
                case NETWORK:
                    showNetworkDialog();
                    break;

                default:
                    showToast(error.getMessage());
            }
        }
    };

    @Override
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
