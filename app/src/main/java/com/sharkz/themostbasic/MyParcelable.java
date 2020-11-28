package com.sharkz.themostbasic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/26  22:51
 * 描    述
 * 修订历史：
 * ================================================
 */
public class MyParcelable implements Parcelable {

    private String paramsA;
    private int paramsB;
    private boolean paramsC;

    public MyParcelable() {

    }

    public MyParcelable(String paramsA, int paramsB, boolean paramsC) {
        this.paramsA = paramsA;
        this.paramsB = paramsB;
        this.paramsC = paramsC;
    }

    public String getParamsA() {
        return paramsA;
    }

    public void setParamsA(String paramsA) {
        this.paramsA = paramsA;
    }

    public int getParamsB() {
        return paramsB;
    }

    public void setParamsB(int paramsB) {
        this.paramsB = paramsB;
    }

    public boolean isParamsC() {
        return paramsC;
    }

    public void setParamsC(boolean paramsC) {
        this.paramsC = paramsC;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paramsA);
        dest.writeInt(this.paramsB);
        dest.writeByte(this.paramsC ? (byte) 1 : (byte) 0);
    }

    protected MyParcelable(Parcel in) {
        this.paramsA = in.readString();
        this.paramsB = in.readInt();
        this.paramsC = in.readByte() != 0;
    }

    public static final Creator<MyParcelable> CREATOR = new Creator<MyParcelable>() {
        @Override
        public MyParcelable createFromParcel(Parcel source) {
            return new MyParcelable(source);
        }

        @Override
        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };
}
