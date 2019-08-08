package com.example.travel;

import android.os.Parcel;
import android.os.Parcelable;

public class TravelDeals implements Parcelable {

    private String id;
    private String title;
    private String description;
    private String price;

    private String imageUrl;
    private String imageName;

    public TravelDeals(){}

    public TravelDeals(String title, String description, String price, String imageUrl, String imageName) {
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setPrice(price);
        this.setImageUrl(imageUrl);
        this.setImageName(imageName);
    }

    protected TravelDeals(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        price = in.readString();
        imageUrl = in.readString();
    }


    public static final Creator<TravelDeals> CREATOR = new Creator<TravelDeals>() {
        @Override

        public TravelDeals createFromParcel(Parcel in) {
            return new TravelDeals(in);
        }

        @Override
        public TravelDeals[] newArray(int size) {
            return new TravelDeals[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(imageUrl);
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
