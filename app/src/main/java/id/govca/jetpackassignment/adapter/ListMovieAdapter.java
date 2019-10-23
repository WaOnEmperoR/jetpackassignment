package id.govca.jetpackassignment.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.govca.jetpackassignment.R;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.rest.Constants;

public class ListMovieAdapter extends PagedListAdapter<Movie, ListMovieAdapter.ListViewHolder> {
    private ArrayList<Movie> listMovie = new ArrayList<>();
    private ArrayList<Movie> listMovieBackup = new ArrayList<>();

    private final Activity activity;

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }

    public ListMovieAdapter(Activity activity)
    {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    public ArrayList<Movie> getListMovie(){
        return listMovie;
    }

    public void setData(ArrayList<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        listMovieBackup.addAll(items);
        notifyDataSetChanged();
    }

    public void setData(List<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        listMovieBackup.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ListViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
//        Movie movie = listMovie.get(position);
        final Movie movie = getItem(position);

        Log.d("ViewHolder", String.valueOf(position));

        Glide
                .with(holder.itemView.getContext())
                .load(Constants.IMAGE_ROOT_SMALL + movie.getPoster_path())
                .into(holder.img_poster);

        holder.tv_movie_name.setText(movie.getTitle());
        holder.tv_year.setText(movie.getRelease_date());
        holder.tv_rating.setText(String.valueOf(movie.getVote_average()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovie.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView tv_rating, tv_year, tv_movie_name;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            img_poster = itemView.findViewById(R.id.imgView_poster);
            tv_rating = itemView.findViewById(R.id.tv_show_rating);
            tv_year = itemView.findViewById(R.id.tv_show_year);
            tv_movie_name = itemView.findViewById(R.id.tv_movie_title);
        }
    }

    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    Log.d("ListMovie", oldItem.getTitle());
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    Log.d("ListMovie", oldItem.getTitle());
                    return oldItem.equals(newItem);
                }
            };

}
