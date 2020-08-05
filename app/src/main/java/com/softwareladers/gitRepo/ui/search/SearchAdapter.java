package com.softwareladers.gitRepo.ui.search;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwareladers.gitRepo.R;
import com.softwareladers.gitRepo.ui.search.SearchModel;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SearchModel> searchArrayList = new ArrayList<SearchModel>();

    public SearchAdapter(Context context, ArrayList<SearchModel> searchArrayList) {
        this.context = context;
        this.searchArrayList = searchArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.search_adapter,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SearchModel search = searchArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        viewHolder.search_name.setText(search.getRepo_name());
        viewHolder.search_full_name.setText(search.getRepo_full_name());
        Glide.with(context)
                .load(search.getRepo_image())
                .apply(new RequestOptions()
                        //.bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 0, 0))
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate())
                .into(((RecyclerViewViewHolder) holder).search_image);

        viewHolder.search_watch.setText(search.getRepo_watch());
    }
    @Override
    public int getItemCount() {
        return searchArrayList.size();
    }
    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ImageView search_image;
        TextView search_name;
        TextView search_full_name;
        TextView search_watch;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            search_image = itemView.findViewById(R.id.search_image);
            search_name = itemView.findViewById(R.id.search_name);
            search_full_name = itemView.findViewById(R.id.search_full_name);
            search_watch = itemView.findViewById(R.id.search_watch);
        }
    }
}