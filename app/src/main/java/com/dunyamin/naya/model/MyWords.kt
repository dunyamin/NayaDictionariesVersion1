package com.dunyamin.naya.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class MyWords(
    var engResource: String? = "",
    var turResource: String? = ""
) : Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(engResource)
        parcel.writeString(turResource)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<MyWords> {
            override fun createFromParcel(parcel: Parcel): MyWords {
                return MyWords(parcel)
            }

            override fun newArray(size: Int): Array<MyWords?> {
                return arrayOfNulls(size)
            }
        }
    }
}