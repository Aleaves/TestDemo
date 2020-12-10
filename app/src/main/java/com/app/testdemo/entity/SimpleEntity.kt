package com.app.testdemo.entity

import android.os.Parcel
import android.os.Parcelable

data class SimpleEntity(val name: String?) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SimpleEntity> = object : Parcelable.Creator<SimpleEntity> {
            override fun createFromParcel(source: Parcel): SimpleEntity = SimpleEntity(source)
            override fun newArray(size: Int): Array<SimpleEntity?> = arrayOfNulls(size)
        }
    }
}