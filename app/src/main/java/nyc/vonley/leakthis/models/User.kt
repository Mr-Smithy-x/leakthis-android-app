package nyc.vonley.leakthis.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val tier: String,
    val created_at: String?,
    val updated_at: String?
) : Parcelable {

}
