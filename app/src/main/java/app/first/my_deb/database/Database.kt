package app.first.my_deb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [GameEntity::class, GamerEntity::class], version = 4)
@TypeConverters(Convertor::class)
abstract class Database : RoomDatabase() {
    abstract fun getDao(): Dao
}