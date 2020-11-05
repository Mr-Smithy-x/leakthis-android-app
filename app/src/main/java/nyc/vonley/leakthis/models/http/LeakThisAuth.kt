package nyc.vonley.leakthis.models.http

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import nyc.vonley.leakthis.models.LeakThisProfile
import nyc.vonley.leakthis.models.User

@Entity
@Parcelize
data class LeakThisAuth(
    val user: User,
    val profile: LeakThisProfile?
) : Parcelable