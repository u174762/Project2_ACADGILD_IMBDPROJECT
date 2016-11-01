package in.movies.niranjan.com.movieproj.api;

import in.movies.niranjan.com.movieproj.BuildConfig;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by Niranjan on 12/29/2015.
 */
public class MovieProjApi {
    private static String SERVER_URL = "http://api.themoviedb.org/3";

    private static MovieProjApi sInstance;
    private MovieProjService mService;

    public static MovieProjService getService() {
        if (sInstance == null) {
            sInstance = new MovieProjApi();
        }

        return sInstance.mService;
    }

    private MovieProjApi() {

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setRequestInterceptor(requestInterceptor)
                .setEndpoint(SERVER_URL);

        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL).setLog(
                    new AndroidLog("RetrofitApi"));
        }

        mService = builder.build().create(MovieProjService.class);
    }

    private RequestInterceptor requestInterceptor = new RequestInterceptor() {

        @Override
        public void intercept(RequestInterceptor.RequestFacade request) {
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
        }
    };

}
