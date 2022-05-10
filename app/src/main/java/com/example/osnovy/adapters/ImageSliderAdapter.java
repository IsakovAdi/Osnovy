package com.example.osnovy.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.osnovy.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.Holder> {
    List<Uri> showingImages;
    Context context;


    public ImageSliderAdapter(List<Uri> showingImages, Context context) {
        this.showingImages = showingImages;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_lider_item, null);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(ImageSliderAdapter.Holder viewHolder, int position) {

        Uri sliderImageUri = showingImages.get(position);
        Glide.with(viewHolder.view)
                .load(sliderImageUri)
                .centerInside()
                .into(viewHolder.imageView);

    }

    @Override
    public int getCount() {
        return showingImages.size();
    }

    public static class Holder extends SliderViewAdapter.ViewHolder {
        View view;
        ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderItemImageView);
            this.view = itemView;
        }
    }
}
