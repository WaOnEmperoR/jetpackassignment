package id.govca.jetpackassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.govca.jetpackassignment.R;
import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.rest.Constants;

public class ListFavoriteAdapter extends RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder>{
    private ArrayList<Favorite> listFavorite = new ArrayList<>();
    private ArrayList<Favorite> listFavoriteBackup = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Favorite data);
    }

    public ListFavoriteAdapter()
    {
    }

    public ArrayList<Favorite> getListFavorite(){
        return listFavorite;
    }

    public void setData(ArrayList<Favorite> items) {
        listFavorite.clear();
        listFavorite.addAll(items);
        listFavoriteBackup.addAll(items);
        notifyDataSetChanged();
    }

    public void setData(List<Favorite> items) {
        listFavorite.clear();
        listFavorite.addAll(items);
        listFavoriteBackup.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListFavoriteAdapter.ListViewHolder holder, int position) {
        Favorite favorite = listFavorite.get(position);

        Glide
                .with(holder.itemView.getContext())
                .load(Constants.IMAGE_ROOT_SMALL + favorite.getPoster_path())
                .into(holder.img_poster);

        holder.tv_movie_name.setText(favorite.getTitle());
        holder.tv_year.setText(favorite.getDate_available());
        holder.tv_rating.setText(String.valueOf(favorite.getVote_average()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listFavorite.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView tv_rating, tv_year, tv_movie_name;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            img_poster = itemView.findViewById(R.id.imgView_favorite_poster);
            tv_rating = itemView.findViewById(R.id.tv_favorite_rating);
            tv_year = itemView.findViewById(R.id.tv_favorite_year);
            tv_movie_name = itemView.findViewById(R.id.tv_favorite_title);
        }
    }

}
