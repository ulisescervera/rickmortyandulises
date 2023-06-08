package com.gmail.uli153.rickmortyandulises.data

import androidx.room.TypeConverter
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterLocation
import com.gmail.uli153.rickmortyandulises.data.entities.CharacterOrigin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun originToString(origin: CharacterOrigin): String {
        return gson.toJson(origin)
    }

    @TypeConverter
    fun stringToCharacterOrigin(json: String): CharacterOrigin {
        return gson.fromJson(json, CharacterOrigin::class.java)
    }

    @TypeConverter
    fun locationToString(location: CharacterLocation): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun stringToCharacterLocation(json: String): CharacterLocation {
        return gson.fromJson(json, CharacterLocation::class.java)
    }

    @TypeConverter
    fun stringListToString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToStringList(json: String): List<String> {
        val type = object: TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }


}