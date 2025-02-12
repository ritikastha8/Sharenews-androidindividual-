package com.example.newsnewshare.model

import android.os.Parcel
import android.os.Parcelable


data class NewsModel (
    var newsidd:String="",
    var newsNamee: String="",
    var categoryNamme:String="",
    var descrription:String="",
    var imageUrl: String=""
) : Parcelable {

    constructor(parcel: Parcel):this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",

    ){

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(newsidd)
        parcel.writeString(newsNamee)
        parcel.writeString(categoryNamme)
        parcel.writeString(descrription)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
       return 0
    }
    companion object CREATOR : Parcelable.Creator<NewsModel> {
        override fun createFromParcel(parcel: Parcel): NewsModel {
            return NewsModel(parcel)
        }

        override fun newArray(size: Int): Array<NewsModel?> {
            return arrayOfNulls(size)
        }
    }


}