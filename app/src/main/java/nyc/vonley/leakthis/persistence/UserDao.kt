package nyc.vonley.leakthis.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nyc.vonley.leakthis.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(s: List<User>)

    @Query("SELECT * FROM User WHERE id = :id_")
    suspend fun getUser(id_: Int): List<User>
}