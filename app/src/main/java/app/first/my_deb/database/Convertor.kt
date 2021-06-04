package app.first.my_deb.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class Convertor {

    @TypeConverter
    fun listToJson(value: List<String>) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toMutableList()
}