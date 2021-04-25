package com.example.android_project;

import com.google.firebase.firestore.Exclude;

public class Upload {

    String mName;
    String mImageUrl;
    String mPrice;
    String mQuantity;

    String mKey;

    public Upload(){
        //empty constructor needed
    }

    public Upload(String name, String imageUrl, String price, String quantity){

        mName = name;
        mImageUrl = imageUrl;
        mPrice = price;
        mQuantity = quantity;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    //==============================================================================
    public String getImageUrl(){
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }

//==============================================================================

    public String getPrice(){
        return mPrice;
    }

    public void setPrice(String price){
        mPrice = price;
    }

//==============================================================================

    public String getQuantity(){
        return mQuantity;
    }

    public void setQuantity(String quantity){
        mQuantity = quantity;
    }

//==============================================================================

    @Exclude
    public String getKey(){
        return mKey;
    }
    @Exclude
    public void setKey(String key){
        mKey = key;
    }

}
