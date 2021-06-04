package app.first.my_deb.database

import android.content.Context
import androidx.room.Room
import java.lang.ref.WeakReference

class DatabaseHelper(context: Context) {

    init {
        DatabaseHelper.context = WeakReference(context)
        createDatabase()
    }

    companion object {

        private var context: WeakReference<Context>? = null
        private const val DATABASE_NAME: String = "belote"
        private var singleton: Database? = null
        val instance: Database
            @Synchronized get() {
                if (null == singleton)
                    singleton = createDatabase()

                return singleton as Database
            }

        private fun createDatabase(): Database {
            return Room.databaseBuilder(
                    context?.get()
                            ?: throw IllegalStateException("initialize by calling constructor before calling DatabaseHelper.instance"),
                    Database::class.java,
                    DATABASE_NAME
            )
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}