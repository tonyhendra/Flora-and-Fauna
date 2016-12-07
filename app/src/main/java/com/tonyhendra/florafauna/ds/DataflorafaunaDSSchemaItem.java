
package com.tonyhendra.florafauna.ds;

import ibmmobileappbuilder.mvp.model.MutableIdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class DataflorafaunaDSSchemaItem implements Parcelable, MutableIdentifiableBean {

    private transient String cloudantIdentifiableId;

    @SerializedName("name") public String name;
    @SerializedName("scientific_name") public String scientific_name;
    @SerializedName("lifespan") public String lifespan;
    @SerializedName("rank") public String rank;
    @SerializedName("higher_classification") public String higher_classification;
    @SerializedName("height") public String height;
    @SerializedName("mass") public String mass;
    @SerializedName("type") public String type;
    @SerializedName("image") public String image;
    @SerializedName("speech") public String speech;

    @Override
    public void setIdentifiableId(String id) {
        this.cloudantIdentifiableId = id;
    }

    @Override
    public String getIdentifiableId() {
        return cloudantIdentifiableId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cloudantIdentifiableId);
        dest.writeString(name);
        dest.writeString(scientific_name);
        dest.writeString(lifespan);
        dest.writeString(rank);
        dest.writeString(higher_classification);
        dest.writeString(height);
        dest.writeString(mass);
        dest.writeString(type);
        dest.writeString(image);
        dest.writeString(speech);
    }

    public static final Creator<DataflorafaunaDSSchemaItem> CREATOR = new Creator<DataflorafaunaDSSchemaItem>() {
        @Override
        public DataflorafaunaDSSchemaItem createFromParcel(Parcel in) {
            DataflorafaunaDSSchemaItem item = new DataflorafaunaDSSchemaItem();
            item.cloudantIdentifiableId = in.readString();

            item.name = in.readString();
            item.scientific_name = in.readString();
            item.lifespan = in.readString();
            item.rank = in.readString();
            item.higher_classification = in.readString();
            item.height = in.readString();
            item.mass = in.readString();
            item.type = in.readString();
            item.image = in.readString();
            item.speech = in.readString();
            return item;
        }

        @Override
        public DataflorafaunaDSSchemaItem[] newArray(int size) {
            return new DataflorafaunaDSSchemaItem[size];
        }
    };
}

