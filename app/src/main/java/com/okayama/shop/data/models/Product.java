package com.okayama.shop.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("amount")
    @Expose
    float amount;

    @SerializedName("path_to_image")
    @Expose
    String image;

    @SerializedName("description")
    @Expose
    String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeFloat(this.amount);
        dest.writeString(this.image);
        dest.writeString(this.description);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.amount = in.readFloat();
        this.image = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
