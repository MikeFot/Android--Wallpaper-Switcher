package com.michaelfotiadis.wallpaperswitcher.ui.activity.gallery;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.michaelfotiadis.wallpaperswitcher.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ThumbnailRecyclerViewAdapter extends RecyclerView.Adapter<ThumbnailRecyclerViewAdapter.ThumbnailViewHolder> {

    private static final String TAG = "ThumbnailAdapter";

    private final List<SelectablePhoto> mSelectablePhotoList;

    public ThumbnailRecyclerViewAdapter() {
        mSelectablePhotoList = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return mSelectablePhotoList.get(position).getUri().hashCode();
    }


    public List<SelectablePhoto> getItems() {
        return mSelectablePhotoList;
    }

    public void clear() {
        mSelectablePhotoList.clear();
        notifyDataSetChanged();
    }

    public void addAll(final List<SelectablePhoto> items) {
        mSelectablePhotoList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gallery, parent, false);
        return new ThumbnailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ThumbnailViewHolder holder, final int position) {
        final SelectablePhoto selectablePhoto = mSelectablePhotoList.get(position);
        Picasso.with(holder.view.getContext())
                .load(selectablePhoto.getUri())
                .resize(100, 100)
                .centerInside()
                .placeholder(R.drawable.ic_check_white_24dp)
                .into(holder.imageView);

        holder.checkBox.setChecked(selectablePhoto.isSelected());

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final boolean newValue = !selectablePhoto.isSelected();
                Log.d(TAG, "Setting " + selectablePhoto.getUri().getLastPathSegment() + " to " + newValue + " postion " + holder.getAdapterPosition());
                selectablePhoto.setSelected(newValue);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSelectablePhotoList.size();
    }

    class ThumbnailViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final ViewGroup mRoot;
        private final CheckBox checkBox;
        private final ImageView imageView;

        private ThumbnailViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            mRoot = itemView.findViewById(R.id.root);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);

        }
    }
}