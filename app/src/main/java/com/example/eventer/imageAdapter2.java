package com.example.eventer;

import android.app.DownloadManager;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.List;

public class imageAdapter2 extends RecyclerView.Adapter<imageAdapter2.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    Button _join;

    public imageAdapter2(Context context,List<Upload> uploads){

        mContext = context;
        mUploads = uploads;

    }

    @NonNull
    @Override
    public imageAdapter2.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.c_item,parent,false);
        return new imageAdapter2.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull imageAdapter2.ImageViewHolder holder, int position) {
        Upload uploadcurrent = mUploads.get(position);
        holder._i_name.setText(uploadcurrent.getEvent_name());
        holder._i_date.setText(uploadcurrent.getDate());
        holder._i_time.setText(uploadcurrent.getTime());
        holder._i_venue.setText(uploadcurrent.getVenue());
        holder._i_entrance.setText(uploadcurrent.getEntrance());
        holder._i_organizer.setText(uploadcurrent.getOrganizer());
        holder._i_description.setText(uploadcurrent.getDescription());
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

    public class ImageViewHolder extends RecyclerView.ViewHolder implements
            CompoundButton.OnCheckedChangeListener {

        public TextView _i_name, _i_date, _i_time, _i_venue, _i_entrance, _i_organizer, _i_description, _i_contact;
        public ImageView _image_view_upload;
        public CheckBox _join;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            _i_name = itemView.findViewById(R.id.c_name);
            _i_date = itemView.findViewById(R.id.c_date);
            _i_time = itemView.findViewById(R.id.c_time);
            _i_venue = itemView.findViewById(R.id.c_venue);
            _i_entrance = itemView.findViewById(R.id.c_entrance);
            _i_organizer = itemView.findViewById(R.id.c_organizer);
            _i_description = itemView.findViewById(R.id.c_description);
            _i_contact = itemView.findViewById(R.id.c_contact);
            _image_view_upload = itemView.findViewById(R.id.c_image_view_upload);
            _join = itemView.findViewById(R.id.join);

            _join.setOnCheckedChangeListener(this);


        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                compoundButton.setChecked(false);
                return;
            }



            DatabaseReference dbFav = FirebaseDatabase.getInstance().getReference("uploads")
                    .child("acc2")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("fav");

            int position = getAdapterPosition();
            Upload u = mUploads.get(position);
            final String selectedKey = u.getKey();

            if(b){
                dbFav.child(selectedKey).setValue(u);
            }else{
                dbFav.child(selectedKey).setValue(null);
            }

        }
    }
}
