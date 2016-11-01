package in.movies.niranjan.com.movieproj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.movies.niranjan.com.movieproj.Interface.RecyclerItemClickListener;
import in.movies.niranjan.com.movieproj.R;
import in.movies.niranjan.com.movieproj.models.Movie;
import in.movies.niranjan.com.movieproj.utils.AppConstants;

/**
 * Created by Niranjan on 12/30/2015.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>  {

    private RecyclerItemClickListener movieClickListener;
    List<Movie> movies;
    Context context;
    public MovieListAdapter(List<Movie> movies, RecyclerItemClickListener movieClickListener, Context context) {
        this.movieClickListener = movieClickListener;
        this.movies = movies;
        this.context = context;
    }

    public void updateMovieList(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        holder.title.setText(movie.title);
        holder.releasedDate.setText(movie.releaseDate);
        holder.movieRating.setRating(movie.popularity);
        holder.voteDesc.setText(" (" + movie.voteAverage + "/" + 10 + ") voted by " + movie.voteCount + " users.");
        if( movie.posterPath == null || movie.posterPath.equals(""))
            Picasso.with(context).load(R.mipmap.no_image).into(holder.moviePoster);
        else
            Picasso.with(context).load(AppConstants.IMAGE_BASE_URL +movie.posterPath).into(holder.moviePoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClickListener.onItemClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {

        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;
        private TextView title, releasedDate,voteDesc;
        private RatingBar movieRating;
        public ViewHolder(View itemView) {
            super(itemView);

            moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
            title = (TextView) itemView.findViewById(R.id.title);
            releasedDate = (TextView) itemView.findViewById(R.id.released_on);
            voteDesc = (TextView) itemView.findViewById(R.id.vote_description);
            movieRating = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}
