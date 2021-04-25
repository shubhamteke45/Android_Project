package com.example.android_project;

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

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    public Context mContext;
    public List<Upload> mUploads;

    onItemClickListener mListener;

    public ImageAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent, false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Upload uploadCurrent = mUploads.get(position);

        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewPrice.setText(uploadCurrent.getPrice());
        Picasso.get().load(uploadCurrent.getImageUrl()).fit().centerCrop().into(holder.imageView);
        //Toast.makeText(mContext, "com.google.android.gms.tasks.zzu@dd8e276", Toast.LENGTH_SHORT).show();
        holder.textViewQuantity.setText(uploadCurrent.getQuantity());


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public void filterList(ArrayList<Upload> filteredList){
        mUploads = filteredList;
        notifyDataSetChanged();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewName, textViewPrice, textViewQuantity;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.cardProductName);
            textViewPrice = itemView.findViewById(R.id.cardProductPrice);
            textViewQuantity = itemView.findViewById(R.id.cardProductQuantity);
            imageView = itemView.findViewById(R.id.cardProductImage);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mListener!=null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
//            MenuItem doWhatever = menu.add(Menu.NONE, 1,1,"UPDATE" );
            MenuItem delete = menu.add(Menu.NONE,1,1,"DELETE");

//            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if(mListener!=null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (item.getItemId())   {
//                        case 1:
//                            mListener.onWhatEverClick(position);
//                            return true;
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;

                    }
                }
            }


            return false;
        }
    }


    public interface onItemClickListener{
        void onItemClick(int position);
        void onWhatEverClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){

        mListener = listener;

    }



}

