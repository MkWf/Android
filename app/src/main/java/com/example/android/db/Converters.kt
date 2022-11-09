package com.example.android.db

import androidx.room.TypeConverter
import com.example.android.models.Source

//Add a typeconverter to deconstruct a complex object.
//Source class is composed of two strings. Room only can use primitive data types.
//Create a Converter class for Source to return only a String in its place.
class Converters {

    @TypeConverter
    fun fromSource(source: Source): String { //we only care about the name and not the id, so return the name
        return source.name
    }

    @TypeConverter
    fun toSource(name:String): Source { //just pass two names
        return Source(name, name)
    }
}