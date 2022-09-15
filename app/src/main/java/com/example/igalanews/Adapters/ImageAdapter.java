package com.example.igalanews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.igalanews.Models.ImageModel;
import com.example.igalanews.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ImageAdapter extends FirebaseRecyclerAdapter<ImageModel, ImageAdapter.ViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ImageAdapter(@NonNull FirebaseRecyclerOptions<ImageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ImageModel model) {

        Glide.with(holder.imageView_pic.getContext())
                .load(model.getUrl())
                .placeholder(R.drawable.imgthumb)
                .error(R.drawable.imgthumb)
                .into(holder.imageView_pic);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView_pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_pic = itemView.findViewById(R.id.pic_of_week);
        }
    }
}
