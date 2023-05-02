package com.example.EventIt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Uploads> mUploads;

    public ImageAdapter(Context context, List<Uploads> uploads)     {
        mContext = context;
        mUploads = uploads;
    }



    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return  new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uploads uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getFileName());
        //Glide.with(mContext).load(uploadCurrent.getmImageurl()).into(holder.imageView);
        Glide.with(mContext).load(uploadCurrent.getmImageurl()).into(holder.imageView);
        Toast.makeText(mContext, uploadCurrent.getmImageurl(), Toast.LENGTH_LONG).show();
//        /* Picasso.get()
//                .load(uploadCurrent.getmImageurl())
//                .fit()
//                .centerCrop()
//                .into(holder.imageView);*/

        //holder.Category.setText(uploadCurrent.getmCategory());
        holder.EventDescription.setText(uploadCurrent.getmEventDescription());
        holder.Latitude.setText(String.valueOf(uploadCurrent.getmLatitude()));
        holder.Longitude.setText(String.valueOf(uploadCurrent.getmLongitude()));


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageView;
        public TextView Category;
        public TextView EventDescription;
        public TextView Latitude;
        public TextView Longitude;



        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_View_upload);
            Category = itemView.findViewById(R.id.tvCategory);
            EventDescription = itemView.findViewById(R.id.tvEventD);
            Latitude = itemView.findViewById(R.id.tvLatitude1);
            Longitude = itemView.findViewById(R.id.tvLongitude1);

        }
    }
}