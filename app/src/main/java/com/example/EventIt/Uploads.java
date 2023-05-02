package com.example.EventIt;

public class Uploads {
    private String filename;
    private String mImageUrl;
    private String mCategory;
    private String mEventDescription;
    private double mLatitude ;
    private double mLongitude ;



    public Uploads(){
//empty constructor needed
    }

    public Uploads(String filename, String imageUrl, String category,
                   String EventDescription,  double latitude,
                   double longitude){

        if ( filename.trim().equals("")){
            filename = "No name";
        }

        this.filename = filename;
        this.mImageUrl = imageUrl;
        this.mCategory = category;
        this.mEventDescription = EventDescription;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }


    public String getFileName(){
        return filename;
    }
    public void SetFileName(String filename){
        filename = filename;
    }


    public String getmImageurl(){
        return mImageUrl;
    }
    public void SetImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }

    public String getmCategory(){
        return mCategory;
    }
    public void setmCategory(String category){
        mCategory = category;
    }


    public String getmEventDescription(){
        return mEventDescription;
    }
    public void setmEventDescription(String EventDescription){
        mEventDescription = EventDescription;
    }


    public Double getmLatitude(){
        return mLatitude;
    }
    public void setmLatitude(Double latitude){
        mLatitude = latitude;
    }


    public Double getmLongitude(){
        return mLongitude;
    }
    public void setmLongitude(Double longitude){
        mLongitude = longitude;
    }


}