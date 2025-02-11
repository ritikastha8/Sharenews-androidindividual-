package com.example.newsnewshare.model

import android.os.Parcel
import android.os.Parcelable

data class CategoryModel(
   var categoryIddd:String="",
   var categoryId:Int=0,
    var categoryNamee:String="",
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readInt()?:0,
        parcel.readString()?:"",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(categoryIddd)
        parcel.writeInt(categoryId)
        parcel.writeString(categoryNamee)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryModel> {
        override fun createFromParcel(parcel: Parcel): CategoryModel {
            return CategoryModel(parcel)
        }

        override fun newArray(size: Int): Array<CategoryModel?> {
            return arrayOfNulls(size)
        }
    }

}