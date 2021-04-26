package com.example.android_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConsumerImageAdapter extends RecyclerView.Adapter<ConsumerImageAdapter.ImageViewHolder> {

    public Context mContext;
    public List<Upload> mUploads;
    public int cnt = 0;

    public ConsumerImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.consumer_image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);

        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewPrice.setText(uploadCurrent.getPrice());
        Picasso.get().load(uploadCurrent.getImageUrl()).fit().centerCrop().into(holder.imageView);
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


    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, textViewPrice, textViewQuantity, cartCount;
        public ImageView imageView, removeFromCart, addToCart;
        public int cnt = 0;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.cardProductName);
            textViewPrice = itemView.findViewById(R.id.cardProductPrice);
            textViewQuantity = itemView.findViewById(R.id.cardProductQuantity);
            imageView = itemView.findViewById(R.id.cardProductImage);


            cartCount = itemView.findViewById(R.id.cartCount);
            removeFromCart = itemView.findViewById(R.id.removeFromCart);
            addToCart = itemView.findViewById(R.id.addToCart);


            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cnt+=1;
                    String game = Integer.toString(cnt);
                    cartCount.setText(game);
                }
            });


            removeFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cnt>0){
                        cnt-=1;
                        String game = Integer.toString(cnt);
                        cartCount.setText(game);
                    }
                }
            });

        }
    }
}