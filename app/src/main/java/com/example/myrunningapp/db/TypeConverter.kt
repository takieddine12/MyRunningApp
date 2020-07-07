package com.example.myrunningapp.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import androidx.room.TypeConverter
import com.google.android.gms.maps.model.BitmapDescriptor
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class TypeConverter {

    @TypeConverter
    fun frombmp(bmp : Bitmap) : ByteArray{
     val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun tobmp(byte : ByteArray) : Bitmap{
        return BitmapFactory.decodeByteArray(byte,0,byte.size)
    }
}