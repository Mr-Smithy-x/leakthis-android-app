package nyc.vonley.leakthis.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import nyc.vonley.leakthis.models.*

@Database(entities = [
    User::class
], version = 1, exportSchema = true)
//@TypeConverters(value = [TypeResponseConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}