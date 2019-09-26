package com.example.eventer;


import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mlistener;

    public imageAdapter(Context context,List<Upload> uploads){

        mContext = context;
        mUploads = uploads;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
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

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener  {

        public TextView _i_name,_i_date,_i_time,_i_venue,_i_entrance,_i_organizer,_i_description,_i_contact;
        public ImageView _image_view_upload;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            _i_name = itemView.findViewById(R.id.i_name);
            _i_date = itemView.findViewById(R.id.i_date);
            _i_time = itemView.findViewById(R.id.i_time);
            _i_venue = itemView.findViewById(R.id.i_venue);
            _i_entrance = itemView.findViewById(R.id.i_entrance);
            _i_organizer = itemView.findViewById(R.id.i_organizer);
            _i_description = itemView.findViewById(R.id.i_description);
            _i_contact = itemView.findViewById(R.id.i_contact);
            _image_view_upload = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }


        @Override
        public void onClick(View view) {
            if(mlistener !=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    mlistener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.setHeaderTitle("Delete Your Post ");
            MenuItem delete = menu.add(Menu.NONE,1,1,"Delete");

            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mlistener !=null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){

                    switch (menuItem.getItemId()){
                        case 1 :mlistener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener ){
        mlistener = listener;
    }
}
