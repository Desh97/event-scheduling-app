package com.example.eventer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class imageAdapter3 extends RecyclerView.Adapter<imageAdapter3.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public imageAdapter3(Context context,List<Upload> uploads){

        mContext = context;
        mUploads = uploads;

    }

    @NonNull
    @Override
    public imageAdapter3.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.search_post,parent,false);
        return new imageAdapter3.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull imageAdapter3.ImageViewHolder holder, int position) {
        Upload uploadcurrent = mUploads.get(position);
        holder._i_name.setText(uploadcurrent.getEvent_name());
        holder._i_contact.setText(uploadcurrent.getContact());

        Picasso.with(mContext)
                .load(uploadcurrent.getImageView())
                .fit()
                .centerCrop()
                .into(holder._image_view_upload);

    }




    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public void filterList(ArrayList<Upload> filterList){
        mUploads = filterList;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView _i_name, _i_contact;
        public ImageView _image_view_upload;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            _i_name = itemView.findViewById(R.id.sp_name);
            _i_contact = itemView.findViewById(R.id.sp_contact);
            _image_view_upload = itemView.findViewById(R.id.sp_image);



        }


    }
}
