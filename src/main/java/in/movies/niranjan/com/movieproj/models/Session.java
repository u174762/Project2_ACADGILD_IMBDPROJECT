package in.movies.niranjan.com.movieproj.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class Session {
    @SerializedName("success")
    public boolean success;

    @SerializedName("guest_session_id")
    public String guestSessionId;

    @SerializedName("expires_at")
    public String expiresAt;
}
