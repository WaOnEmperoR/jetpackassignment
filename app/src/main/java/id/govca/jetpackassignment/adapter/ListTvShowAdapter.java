package id.govca.jetpackassignment.adapter;

import android.annotation.SuppressLint;
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
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.rest.Constants;

public class ListTvShowAdapter extends PagedListAdapter<TVShow, ListTvShowAdapter.ListViewHolder> {
    private ArrayList<TVShow> listTvShow = new ArrayList<>();
    private ArrayList<TVShow> listTvShowBackup = new ArrayList<>();

    private ListTvShowAdapter.OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(ListTvShowAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public ListTvShowAdapter()
    {
        super(DIFF_CALLBACK);
    }

    public void setData(ArrayList<TVShow> items) {
        listTvShow.clear();
        listTvShow.addAll(items);
        listTvShowBackup.addAll(items);
        notifyDataSetChanged();
    }

    public void setData(List<TVShow> items) {
        listTvShow.clear();
        listTvShow.addAll(items);
        listTvShowBackup.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ListTvShowAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListTvShowAdapter.ListViewHolder holder, int position) {
        final TVShow tvShow = getItem(position);

        Glide
                .with(holder.itemView.getContext())
                .load(Constants.IMAGE_ROOT_SMALL + tvShow.getPoster_path())
                .into(holder.img_poster);

        holder.tv_movie_name.setText(tvShow.getName());
        holder.tv_year.setText(tvShow.getFirst_air_date());
        holder.tv_rating.setText(String.valueOf(tvShow.getVote_average()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(tvShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public interface OnItemClickCallback {
        void onItemClicked(TVShow data);
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

    private static DiffUtil.ItemCallback<TVShow> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TVShow>() {
                @Override
                public boolean areItemsTheSame(@NonNull TVShow oldItem, @NonNull TVShow newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull TVShow oldItem, @NonNull TVShow newItem) {
                    return oldItem.equals(newItem);
                }
            };

}
