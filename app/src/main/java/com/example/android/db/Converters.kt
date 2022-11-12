package com.example.android.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.android.models.Source
import java.io.ByteArrayOutputStream

//Add a typeconverter to deconstruct a complex object.
//Source class is composed of two strings. Room only can use primitive data types.
//Create a Converter class for Source to return only a String in its place.
class Converters {

    //Object with Two strings (Source class)
    @TypeConverter
    fun fromSource(source: Source): String { //we only care about the name and not the id, so return the name
        return source.name
    }

    @TypeConverter
    fun toSource(name:String): Source { //just pass two names
        return Source(name, name)
    }

    //Bitmap type - will convert it to bytes
    @TypeConverter
    fun fromBitmap(bmp: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}